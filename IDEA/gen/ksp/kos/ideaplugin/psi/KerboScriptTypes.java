// This is a generated file. Not intended for manual editing.
package ksp.kos.ideaplugin.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import ksp.kos.ideaplugin.psi.impl.*;

public interface KerboScriptTypes {

  IElementType ADD_STMT = new KerboScriptElementType("ADD_STMT");
  IElementType AND_EXPR = new KerboScriptElementType("AND_EXPR");
  IElementType ARGLIST = new KerboScriptElementType("ARGLIST");
  IElementType ARITH_EXPR = new KerboScriptElementType("ARITH_EXPR");
  IElementType ARRAY_TRAILER = new KerboScriptElementType("ARRAY_TRAILER");
  IElementType ATOM = new KerboScriptElementType("ATOM");
  IElementType BREAK_STMT = new KerboScriptElementType("BREAK_STMT");
  IElementType CLEAR_STMT = new KerboScriptElementType("CLEAR_STMT");
  IElementType COMPARE_EXPR = new KerboScriptElementType("COMPARE_EXPR");
  IElementType COMPILE_STMT = new KerboScriptElementType("COMPILE_STMT");
  IElementType COPY_STMT = new KerboScriptElementType("COPY_STMT");
  IElementType DECLARE_FUNCTION_CLAUSE = new KerboScriptElementType("DECLARE_FUNCTION_CLAUSE");
  IElementType DECLARE_IDENTIFIER_CLAUSE = new KerboScriptElementType("DECLARE_IDENTIFIER_CLAUSE");
  IElementType DECLARE_LOCK_CLAUSE = new KerboScriptElementType("DECLARE_LOCK_CLAUSE");
  IElementType DECLARE_PARAMETER_CLAUSE = new KerboScriptElementType("DECLARE_PARAMETER_CLAUSE");
  IElementType DECLARE_STMT = new KerboScriptElementType("DECLARE_STMT");
  IElementType DELETE_STMT = new KerboScriptElementType("DELETE_STMT");
  IElementType DIRECTIVE = new KerboScriptElementType("DIRECTIVE");
  IElementType EDIT_STMT = new KerboScriptElementType("EDIT_STMT");
  IElementType EXPR = new KerboScriptElementType("EXPR");
  IElementType FACTOR = new KerboScriptElementType("FACTOR");
  IElementType FOR_STMT = new KerboScriptElementType("FOR_STMT");
  IElementType FROMLOOP_STMT = new KerboScriptElementType("FROMLOOP_STMT");
  IElementType FUNCTION_TRAILER = new KerboScriptElementType("FUNCTION_TRAILER");
  IElementType IDENTIFIER_LED_EXPR = new KerboScriptElementType("IDENTIFIER_LED_EXPR");
  IElementType IDENTIFIER_LED_STMT = new KerboScriptElementType("IDENTIFIER_LED_STMT");
  IElementType IF_STMT = new KerboScriptElementType("IF_STMT");
  IElementType INSTRUCTION = new KerboScriptElementType("INSTRUCTION");
  IElementType INSTRUCTION_BLOCK = new KerboScriptElementType("INSTRUCTION_BLOCK");
  IElementType LAZYGLOBAL_DIRECTIVE = new KerboScriptElementType("LAZYGLOBAL_DIRECTIVE");
  IElementType LIST_STMT = new KerboScriptElementType("LIST_STMT");
  IElementType LOG_STMT = new KerboScriptElementType("LOG_STMT");
  IElementType MULTDIV_EXPR = new KerboScriptElementType("MULTDIV_EXPR");
  IElementType NUMBER = new KerboScriptElementType("NUMBER");
  IElementType ONOFF_STMT = new KerboScriptElementType("ONOFF_STMT");
  IElementType ONOFF_TRAILER = new KerboScriptElementType("ONOFF_TRAILER");
  IElementType ON_STMT = new KerboScriptElementType("ON_STMT");
  IElementType PRESERVE_STMT = new KerboScriptElementType("PRESERVE_STMT");
  IElementType PRINT_STMT = new KerboScriptElementType("PRINT_STMT");
  IElementType REBOOT_STMT = new KerboScriptElementType("REBOOT_STMT");
  IElementType REMOVE_STMT = new KerboScriptElementType("REMOVE_STMT");
  IElementType RENAME_STMT = new KerboScriptElementType("RENAME_STMT");
  IElementType RETURN_STMT = new KerboScriptElementType("RETURN_STMT");
  IElementType RUN_STMT = new KerboScriptElementType("RUN_STMT");
  IElementType SCI_NUMBER = new KerboScriptElementType("SCI_NUMBER");
  IElementType SET_STMT = new KerboScriptElementType("SET_STMT");
  IElementType SHUTDOWN_STMT = new KerboScriptElementType("SHUTDOWN_STMT");
  IElementType STAGE_STMT = new KerboScriptElementType("STAGE_STMT");
  IElementType SUFFIX = new KerboScriptElementType("SUFFIX");
  IElementType SUFFIXTERM = new KerboScriptElementType("SUFFIXTERM");
  IElementType SUFFIXTERM_TRAILER = new KerboScriptElementType("SUFFIXTERM_TRAILER");
  IElementType SUFFIX_TRAILER = new KerboScriptElementType("SUFFIX_TRAILER");
  IElementType SWITCH_STMT = new KerboScriptElementType("SWITCH_STMT");
  IElementType TOGGLE_STMT = new KerboScriptElementType("TOGGLE_STMT");
  IElementType UNARY_EXPR = new KerboScriptElementType("UNARY_EXPR");
  IElementType UNLOCK_STMT = new KerboScriptElementType("UNLOCK_STMT");
  IElementType UNSET_STMT = new KerboScriptElementType("UNSET_STMT");
  IElementType UNTIL_STMT = new KerboScriptElementType("UNTIL_STMT");
  IElementType VARIDENTIFIER = new KerboScriptElementType("VARIDENTIFIER");
  IElementType WAIT_STMT = new KerboScriptElementType("WAIT_STMT");
  IElementType WHEN_STMT = new KerboScriptElementType("WHEN_STMT");

  IElementType ADD = new KerboScriptTokenType("ADD");
  IElementType ALL = new KerboScriptTokenType("ALL");
  IElementType AND = new KerboScriptTokenType("AND");
  IElementType ARRAYINDEX = new KerboScriptTokenType("ARRAYINDEX");
  IElementType AT = new KerboScriptTokenType("AT");
  IElementType ATSIGN = new KerboScriptTokenType("ATSIGN");
  IElementType BRACKETCLOSE = new KerboScriptTokenType("BRACKETCLOSE");
  IElementType BRACKETOPEN = new KerboScriptTokenType("BRACKETOPEN");
  IElementType BREAK = new KerboScriptTokenType("BREAK");
  IElementType CLEARSCREEN = new KerboScriptTokenType("CLEARSCREEN");
  IElementType COLON = new KerboScriptTokenType("COLON");
  IElementType COMMA = new KerboScriptTokenType("COMMA");
  IElementType COMPARATOR = new KerboScriptTokenType("COMPARATOR");
  IElementType COMPILE = new KerboScriptTokenType("COMPILE");
  IElementType COPY = new KerboScriptTokenType("COPY");
  IElementType CURLYCLOSE = new KerboScriptTokenType("CURLYCLOSE");
  IElementType CURLYOPEN = new KerboScriptTokenType("CURLYOPEN");
  IElementType DECLARE = new KerboScriptTokenType("DECLARE");
  IElementType DEFINED = new KerboScriptTokenType("DEFINED");
  IElementType DELETE = new KerboScriptTokenType("DELETE");
  IElementType DIV = new KerboScriptTokenType("DIV");
  IElementType DO = new KerboScriptTokenType("DO");
  IElementType DOUBLE = new KerboScriptTokenType("DOUBLE");
  IElementType E = new KerboScriptTokenType("E");
  IElementType EDIT = new KerboScriptTokenType("EDIT");
  IElementType ELSE = new KerboScriptTokenType("ELSE");
  IElementType EOI = new KerboScriptTokenType("EOI");
  IElementType FILE = new KerboScriptTokenType("FILE");
  IElementType FILEIDENT = new KerboScriptTokenType("FILEIDENT");
  IElementType FOR = new KerboScriptTokenType("FOR");
  IElementType FROM = new KerboScriptTokenType("FROM");
  IElementType FUNCTION = new KerboScriptTokenType("FUNCTION");
  IElementType GLOBAL = new KerboScriptTokenType("GLOBAL");
  IElementType IDENTIFIER = new KerboScriptTokenType("IDENTIFIER");
  IElementType IF = new KerboScriptTokenType("IF");
  IElementType IN = new KerboScriptTokenType("IN");
  IElementType INTEGER = new KerboScriptTokenType("INTEGER");
  IElementType IS = new KerboScriptTokenType("IS");
  IElementType LAZYGLOBAL = new KerboScriptTokenType("LAZYGLOBAL");
  IElementType LIST = new KerboScriptTokenType("LIST");
  IElementType LOCAL = new KerboScriptTokenType("LOCAL");
  IElementType LOCK = new KerboScriptTokenType("LOCK");
  IElementType LOG = new KerboScriptTokenType("LOG");
  IElementType MULT = new KerboScriptTokenType("MULT");
  IElementType NOT = new KerboScriptTokenType("NOT");
  IElementType OFF = new KerboScriptTokenType("OFF");
  IElementType ON = new KerboScriptTokenType("ON");
  IElementType ONCE = new KerboScriptTokenType("ONCE");
  IElementType OR = new KerboScriptTokenType("OR");
  IElementType PARAMETER = new KerboScriptTokenType("PARAMETER");
  IElementType PLUSMINUS = new KerboScriptTokenType("PLUSMINUS");
  IElementType POWER = new KerboScriptTokenType("POWER");
  IElementType PRESERVE = new KerboScriptTokenType("PRESERVE");
  IElementType PRINT = new KerboScriptTokenType("PRINT");
  IElementType REBOOT = new KerboScriptTokenType("REBOOT");
  IElementType REMOVE = new KerboScriptTokenType("REMOVE");
  IElementType RENAME = new KerboScriptTokenType("RENAME");
  IElementType RETURN = new KerboScriptTokenType("RETURN");
  IElementType RUN = new KerboScriptTokenType("RUN");
  IElementType SET = new KerboScriptTokenType("SET");
  IElementType SHUTDOWN = new KerboScriptTokenType("SHUTDOWN");
  IElementType SQUARECLOSE = new KerboScriptTokenType("SQUARECLOSE");
  IElementType SQUAREOPEN = new KerboScriptTokenType("SQUAREOPEN");
  IElementType STAGE = new KerboScriptTokenType("STAGE");
  IElementType STEP = new KerboScriptTokenType("STEP");
  IElementType STRING = new KerboScriptTokenType("STRING");
  IElementType SWITCH = new KerboScriptTokenType("SWITCH");
  IElementType THEN = new KerboScriptTokenType("THEN");
  IElementType TO = new KerboScriptTokenType("TO");
  IElementType TOGGLE = new KerboScriptTokenType("TOGGLE");
  IElementType TRUEFALSE = new KerboScriptTokenType("TRUEFALSE");
  IElementType UNLOCK = new KerboScriptTokenType("UNLOCK");
  IElementType UNSET = new KerboScriptTokenType("UNSET");
  IElementType UNTIL = new KerboScriptTokenType("UNTIL");
  IElementType VOLUME = new KerboScriptTokenType("VOLUME");
  IElementType WAIT = new KerboScriptTokenType("WAIT");
  IElementType WHEN = new KerboScriptTokenType("WHEN");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == ADD_STMT) {
        return new KerboScriptAddStmtImpl(node);
      }
      else if (type == AND_EXPR) {
        return new KerboScriptAndExprImpl(node);
      }
      else if (type == ARGLIST) {
        return new KerboScriptArglistImpl(node);
      }
      else if (type == ARITH_EXPR) {
        return new KerboScriptArithExprImpl(node);
      }
      else if (type == ARRAY_TRAILER) {
        return new KerboScriptArrayTrailerImpl(node);
      }
      else if (type == ATOM) {
        return new KerboScriptAtomImpl(node);
      }
      else if (type == BREAK_STMT) {
        return new KerboScriptBreakStmtImpl(node);
      }
      else if (type == CLEAR_STMT) {
        return new KerboScriptClearStmtImpl(node);
      }
      else if (type == COMPARE_EXPR) {
        return new KerboScriptCompareExprImpl(node);
      }
      else if (type == COMPILE_STMT) {
        return new KerboScriptCompileStmtImpl(node);
      }
      else if (type == COPY_STMT) {
        return new KerboScriptCopyStmtImpl(node);
      }
      else if (type == DECLARE_FUNCTION_CLAUSE) {
        return new KerboScriptDeclareFunctionClauseImpl(node);
      }
      else if (type == DECLARE_IDENTIFIER_CLAUSE) {
        return new KerboScriptDeclareIdentifierClauseImpl(node);
      }
      else if (type == DECLARE_LOCK_CLAUSE) {
        return new KerboScriptDeclareLockClauseImpl(node);
      }
      else if (type == DECLARE_PARAMETER_CLAUSE) {
        return new KerboScriptDeclareParameterClauseImpl(node);
      }
      else if (type == DECLARE_STMT) {
        return new KerboScriptDeclareStmtImpl(node);
      }
      else if (type == DELETE_STMT) {
        return new KerboScriptDeleteStmtImpl(node);
      }
      else if (type == DIRECTIVE) {
        return new KerboScriptDirectiveImpl(node);
      }
      else if (type == EDIT_STMT) {
        return new KerboScriptEditStmtImpl(node);
      }
      else if (type == EXPR) {
        return new KerboScriptExprImpl(node);
      }
      else if (type == FACTOR) {
        return new KerboScriptFactorImpl(node);
      }
      else if (type == FOR_STMT) {
        return new KerboScriptForStmtImpl(node);
      }
      else if (type == FROMLOOP_STMT) {
        return new KerboScriptFromloopStmtImpl(node);
      }
      else if (type == FUNCTION_TRAILER) {
        return new KerboScriptFunctionTrailerImpl(node);
      }
      else if (type == IDENTIFIER_LED_EXPR) {
        return new KerboScriptIdentifierLedExprImpl(node);
      }
      else if (type == IDENTIFIER_LED_STMT) {
        return new KerboScriptIdentifierLedStmtImpl(node);
      }
      else if (type == IF_STMT) {
        return new KerboScriptIfStmtImpl(node);
      }
      else if (type == INSTRUCTION) {
        return new KerboScriptInstructionImpl(node);
      }
      else if (type == INSTRUCTION_BLOCK) {
        return new KerboScriptInstructionBlockImpl(node);
      }
      else if (type == LAZYGLOBAL_DIRECTIVE) {
        return new KerboScriptLazyglobalDirectiveImpl(node);
      }
      else if (type == LIST_STMT) {
        return new KerboScriptListStmtImpl(node);
      }
      else if (type == LOG_STMT) {
        return new KerboScriptLogStmtImpl(node);
      }
      else if (type == MULTDIV_EXPR) {
        return new KerboScriptMultdivExprImpl(node);
      }
      else if (type == NUMBER) {
        return new KerboScriptNumberImpl(node);
      }
      else if (type == ONOFF_STMT) {
        return new KerboScriptOnoffStmtImpl(node);
      }
      else if (type == ONOFF_TRAILER) {
        return new KerboScriptOnoffTrailerImpl(node);
      }
      else if (type == ON_STMT) {
        return new KerboScriptOnStmtImpl(node);
      }
      else if (type == PRESERVE_STMT) {
        return new KerboScriptPreserveStmtImpl(node);
      }
      else if (type == PRINT_STMT) {
        return new KerboScriptPrintStmtImpl(node);
      }
      else if (type == REBOOT_STMT) {
        return new KerboScriptRebootStmtImpl(node);
      }
      else if (type == REMOVE_STMT) {
        return new KerboScriptRemoveStmtImpl(node);
      }
      else if (type == RENAME_STMT) {
        return new KerboScriptRenameStmtImpl(node);
      }
      else if (type == RETURN_STMT) {
        return new KerboScriptReturnStmtImpl(node);
      }
      else if (type == RUN_STMT) {
        return new KerboScriptRunStmtImpl(node);
      }
      else if (type == SCI_NUMBER) {
        return new KerboScriptSciNumberImpl(node);
      }
      else if (type == SET_STMT) {
        return new KerboScriptSetStmtImpl(node);
      }
      else if (type == SHUTDOWN_STMT) {
        return new KerboScriptShutdownStmtImpl(node);
      }
      else if (type == STAGE_STMT) {
        return new KerboScriptStageStmtImpl(node);
      }
      else if (type == SUFFIX) {
        return new KerboScriptSuffixImpl(node);
      }
      else if (type == SUFFIXTERM) {
        return new KerboScriptSuffixtermImpl(node);
      }
      else if (type == SUFFIXTERM_TRAILER) {
        return new KerboScriptSuffixtermTrailerImpl(node);
      }
      else if (type == SUFFIX_TRAILER) {
        return new KerboScriptSuffixTrailerImpl(node);
      }
      else if (type == SWITCH_STMT) {
        return new KerboScriptSwitchStmtImpl(node);
      }
      else if (type == TOGGLE_STMT) {
        return new KerboScriptToggleStmtImpl(node);
      }
      else if (type == UNARY_EXPR) {
        return new KerboScriptUnaryExprImpl(node);
      }
      else if (type == UNLOCK_STMT) {
        return new KerboScriptUnlockStmtImpl(node);
      }
      else if (type == UNSET_STMT) {
        return new KerboScriptUnsetStmtImpl(node);
      }
      else if (type == UNTIL_STMT) {
        return new KerboScriptUntilStmtImpl(node);
      }
      else if (type == VARIDENTIFIER) {
        return new KerboScriptVaridentifierImpl(node);
      }
      else if (type == WAIT_STMT) {
        return new KerboScriptWaitStmtImpl(node);
      }
      else if (type == WHEN_STMT) {
        return new KerboScriptWhenStmtImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
