package ksp.kos.ideaplugin.expressions

import ksp.kos.ideaplugin.psi.*

/**
 * Created on 28/01/16.
 *
 * @author ptasha
 */
abstract class Atom : Expression() {
    open val isAddition: Boolean
        get() = false

    companion object {
        @Throws(SyntaxException::class)
        fun parse(expr: KerboScriptExpr): Atom =
            Expression.parse(expr) as? Atom
                ?: throw SyntaxException("Atom is expected: found " + expr + ": " + expr.text)

        @JvmStatic
        @Throws(SyntaxException::class)
        fun parse(atom: KerboScriptAtom): Atom {
            val identifier = atom.children.firstOrNull { it is KerboScriptIdent }
            val expr = atom.expr
            return when {
                identifier != null -> Variable(identifier.text)
                atom.node.findChildByType(KerboScriptTypes.BRACKETOPEN) != null -> {
                    Escaped(parse(expr))
                }
                expr is KerboScriptNumber -> Number(expr)
                expr is KerboScriptSciNumber -> Number(expr)
                else -> throw SyntaxException("Invalid atom: " + atom.text)
            }

        }

        @JvmStatic
        fun toAtom(expression: Expression?): Atom = expression as? Atom ?: Escaped(expression)
    }
}
