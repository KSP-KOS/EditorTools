package ksp.kos.ideaplugin.reference.context

import ksp.kos.ideaplugin.psi.KerboScriptNamedElement
import ksp.kos.ideaplugin.reference.OccurrenceType
import ksp.kos.ideaplugin.reference.ReferableType
import ksp.kos.ideaplugin.reference.Reference
import java.util.*

/**
 * Created on 04/10/16.
 *
 * @author ptasha
 */
open class LocalContext @JvmOverloads constructor(
    val parent: LocalContext?,
    private val resolvers: List<ReferenceResolver<LocalContext>> = createResolvers(),
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

    class ScopeMap<T> : LinkedHashMap<String, T>() {
        private fun String.normalize(): String = this.toLowerCase()

        override fun put(key: String, value: T): T? = super.put(key.normalize(), value)

        override fun get(key: String): T? = super.get(key.normalize())
    }

    /**
     * Created on 10/10/16.
     *
     * @author ptasha
     */
    class LocalResolver : ReferenceResolver<LocalContext> {
        override fun resolve(context: LocalContext, reference: Reference, createAllowed: Boolean): Duality? =
            context.findLocalDeclaration(reference)
    }

    /**
     * Created on 10/10/16.
     *
     * @author ptasha
     */
    open class ParentResolver : ReferenceResolver<LocalContext> {
        override fun resolve(context: LocalContext, reference: Reference, createAllowed: Boolean): Duality? =
            context.parent?.resolve(reference, createAllowed)
    }

    companion object {
        fun createResolvers(): List<ReferenceResolver<LocalContext>> =
            listOf(
                LocalResolver(),
                ParentResolver(),
            )
    }
}
