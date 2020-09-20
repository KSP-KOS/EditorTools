package ksp.kos.ideaplugin.reference.context

import ksp.kos.ideaplugin.KerboScriptFile
import ksp.kos.ideaplugin.dataflow.ReferenceFlow
import ksp.kos.ideaplugin.reference.ReferableType
import ksp.kos.ideaplugin.reference.Reference

/**
 * Created on 08/10/16.
 *
 * @author ptasha
 */
abstract class FileContext protected constructor(
    parent: LocalContext?,
    private val name: String,
    resolvers: List<ReferenceResolver<LocalContext>>,
) : LocalContext(parent, resolvers), ReferenceFlow<FileContext>, FileDuality {

    constructor(parent: LocalContext?, name: String, fileResolver: FileContextResolver)
            : this(parent, name, createResolvers(fileResolver))

    override fun getKingdom(): LocalContext = this

    override fun getReferableType(): ReferableType = ReferableType.FILE

    override fun getName(): String = name

    val imports: Map<String, Duality>
        get() = getDeclarations(ReferableType.FILE)

    override fun registerUnknown(type: ReferableType, name: String, element: Duality) {
        if (type == ReferableType.FILE) {
            addDefinition(type, KerboScriptFile.stripExtension(name), element)
        } else {
            super.registerUnknown(type, name, element)
        }
    }

    class FileResolver(private val fileContextResolver: FileContextResolver) : ReferenceResolver<LocalContext> {
        override fun resolve(context: LocalContext, reference: Reference, createAllowed: Boolean): Duality? =
            if (reference.referableType == ReferableType.FILE) {
                fileContextResolver.resolveFile(reference.name)
            } else {
                null
            }
    }

    override fun getSemantics(): FileContext = this

    class ImportsResolver(private val fileContextResolver: FileContextResolver) : ReferenceResolver<LocalContext> {
        override fun resolve(context: LocalContext, reference: Reference, createAllowed: Boolean): Duality? =
            context.getDeclarations(ReferableType.FILE)
                .values
                .mapNotNull { run ->
                    fileContextResolver.resolveFile(run.name)?.semantics?.findLocalDeclaration(reference)
                }
                .firstOrNull()
    }

    private class VirtualResolver : ParentResolver() {
        override fun resolve(context: LocalContext, reference: Reference, createAllowed: Boolean): Duality? =
            if (!createAllowed) null else super.resolve(context, reference, createAllowed)
    }

    companion object {
        @JvmStatic
        fun createResolvers(fileResolver: FileContextResolver): MutableList<ReferenceResolver<LocalContext>> =
            mutableListOf(
                FileResolver(fileResolver),
                LocalResolver(),
                VirtualResolver(),
                ImportsResolver(fileResolver),
            )
    }
}
