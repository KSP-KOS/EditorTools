package ksp.kos.ideaplugin.reference.context

import ksp.kos.ideaplugin.psi.KerboScriptNamedElement
import ksp.kos.ideaplugin.reference.OccurrenceType
import ksp.kos.ideaplugin.reference.ReferableType
import ksp.kos.ideaplugin.reference.Reference
import java.util.EnumMap
import java.util.Locale

/**
 * Created on 04/10/16.
 *
 * @author ptasha
 */
open class LocalContext @JvmOverloads constructor(
    val parent: LocalContext?,
    private val resolvers: List<ReferenceResolver> = createResolvers(),
) {
    private val declarations: MutableMap<ReferableType, ScopeMap<Duality>> =
        EnumMap(ksp.kos.ideaplugin.reference.ReferableType::class.java)

    val functions: Map<String, Duality>
        get() = getDeclarations(ReferableType.FUNCTION)

    fun findDeclaration(reference: Reference): Duality? = resolve(reference, createAllowed = false)

    @JvmOverloads
    open fun findLocalDeclaration(reference: Reference, occurrenceTypeFilter: OccurrenceType? = null): Duality? {
        val referableTypes = mutableListOf(reference.referableType)
        // Functions can look like variables (eg parameters)
        if (reference.referableType == ReferableType.FUNCTION) {
            referableTypes.add(ReferableType.VARIABLE)
        }
        return referableTypes
            .mapNotNull { referableType -> getDeclarations(referableType)[reference.name] }
            .firstOrNull { declaration ->
                (occurrenceTypeFilter == null || declaration.syntax?.type?.occurrenceType == occurrenceTypeFilter)
                        && reference.matches(declaration)
            }
    }

    fun resolve(reference: Reference): Duality? = resolve(reference, createAllowed = true)

    protected open fun resolve(reference: Reference, createAllowed: Boolean): Duality? =
        resolvers.mapNotNull { resolver -> resolver.resolve(this, reference, createAllowed) }.firstOrNull()

    open fun clear() = declarations.clear()

    fun register(element: Duality) {
        val name = element.name
        if (name != null) {
            when (val type = element.referableType) {
                ReferableType.FUNCTION, ReferableType.VARIABLE, ReferableType.FILE -> addDefinition(type, name, element)
                else -> registerUnknown(type, name, element)
            }
        }
    }

    fun register(psi: KerboScriptNamedElement) = register(PsiDuality(psi))

    protected open fun registerUnknown(type: ReferableType, name: String, element: Duality) {}

    protected fun addDefinition(type: ReferableType, name: String, element: Duality) {
        declarations.getOrPut(type, ::ScopeMap)[name] = element
        // Make sure we can refer back to functions when they're used later in situations that work with variables.
        if (type == ReferableType.FUNCTION) {
            declarations.getOrPut(ReferableType.VARIABLE, ::ScopeMap)[name] = element
        }
        // Propagate globals back up so parent scopes can see them. Since the only way that imports can affect the
        // current file's scope is via globals, we also pass those up.
        // TODO - Maybe just put these on the file context?
        if (element.syntax?.type?.occurrenceType == OccurrenceType.GLOBAL || type == ReferableType.FILE) {
            parent?.addDefinition(type, name, element)
        }
    }

    fun getDeclarations(type: ReferableType): Map<String, Duality> = declarations.getOrPut(type, ::ScopeMap)

    val fileContext: FileContext?
        get() = (this as? FileContext) ?: parent?.fileContext

    class ScopeMap<T>(
        private val delegate: MutableMap<String, T> = LinkedHashMap()
    ) : MutableMap<String, T> by delegate {
        private fun String.normalize(): String = this.lowercase(Locale.getDefault())

        override fun put(key: String, value: T): T? = delegate.put(key.normalize(), value)

        override fun get(key: String): T? = delegate[key.normalize()]

        override fun containsKey(key: String): Boolean = delegate.containsKey(key.normalize())

        override fun getOrDefault(key: String, defaultValue: T) = delegate.getOrDefault(key.normalize(), defaultValue)

        override fun remove(key: String): T? = delegate.remove(key.normalize())

        override fun remove(key: String, value: T): Boolean = delegate.remove(key.normalize(), value)
    }

    /**
     * Created on 10/10/16.
     *
     * @author ptasha
     */
    class LocalResolver : ReferenceResolver {
        override fun resolve(context: LocalContext, reference: Reference, createAllowed: Boolean): Duality? =
            context.findLocalDeclaration(reference)
    }

    /**
     * Created on 10/10/16.
     *
     * @author ptasha
     */
    open class ParentResolver : ReferenceResolver {
        override fun resolve(context: LocalContext, reference: Reference, createAllowed: Boolean): Duality? =
            context.parent?.resolve(reference, createAllowed)
    }

    companion object {
        fun createResolvers(): List<ReferenceResolver> =
            listOf(
                LocalResolver(),
                ParentResolver(),
            )
    }
}
