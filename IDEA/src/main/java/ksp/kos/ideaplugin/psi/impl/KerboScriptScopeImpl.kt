package ksp.kos.ideaplugin.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ksp.kos.ideaplugin.psi.KerboScriptScope
import ksp.kos.ideaplugin.reference.Cache
import ksp.kos.ideaplugin.reference.context.LocalContext

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
open class KerboScriptScopeImpl(node: ASTNode) : ASTWrapperPsiElement(node), KerboScriptScope {
    private var cache: Cache<LocalContext>? = null

    @Synchronized
    override fun getCachedScope(): LocalContext {
        val resolvedCache = cache ?: run {
            // It's possible that this triggers a re-walk of the entire file, which will itself construct a
            // LocalContext and cache for this scope. If we now overwrite that it'll be incorrect. Make sure to
            // check again if our cache is still null before charging ahead.
            val parentScope = this.scope.cachedScope
            cache ?: run {
                val newCache = Cache(this, LocalContext(parentScope))
                cache = newCache
                newCache
            }
        }
        return resolvedCache.scope
    }
}
