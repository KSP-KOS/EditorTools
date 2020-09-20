package ksp.kos.ideaplugin.reference.context

import ksp.kos.ideaplugin.psi.KerboScriptNamedElement
import ksp.kos.ideaplugin.reference.ReferableType
import ksp.kos.ideaplugin.reference.Reference
import java.util.*
import kotlin.collections.HashMap

/**
 * Created on 04/10/16.
 *
 * @author ptasha
 */
open class LocalContext protected constructor(
    val parent: LocalContext?,
    private val resolvers: List<ReferenceResolver<LocalContext>>,
) {
    private val declarations: MutableMap<ReferableType, MutableMap<String, Duality>> = HashMap()

    constructor(parent: LocalContext?) : this(parent, createResolvers())

    val functions: Map<String, Duality>
        get() = getDeclarations(ReferableType.FUNCTION)

    fun findDeclaration(reference: Reference): Duality? = resolve(reference, false)

    open fun findLocalDeclaration(reference: Reference): Duality? =
        getDeclarations(reference.referableType)[reference.name]?.let { declaration ->
            if (reference.matches(declaration)) declaration else null
        }

    fun resolve(reference: Reference): Duality? = resolve(reference, true)

    protected open fun resolve(reference: Reference, createAllowed: Boolean): Duality? =
        resolvers.mapNotNull { resolver -> resolver.resolve(this, reference, createAllowed) }.firstOrNull()

    open fun clear() = declarations.clear()

    fun register(element: Duality) {
        val name = element.name
        if (name != null) {
            when (val type = element.referableType) {
                ReferableType.FUNCTION, ReferableType.VARIABLE -> addDefinition(type, name, element)
                else -> registerUnknown(type, name, element)
            }
        }
    }

    fun register(psi: KerboScriptNamedElement) = register(PsiDuality(psi))

    protected open fun registerUnknown(type: ReferableType, name: String, element: Duality) {}

    protected fun addDefinition(type: ReferableType, name: String, element: Duality) {
        getDeclarations(type)[name] = element
    }

    fun getDeclarations(type: ReferableType): MutableMap<String, Duality> = declarations.getOrPut(type, ::ScopeMap)

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
