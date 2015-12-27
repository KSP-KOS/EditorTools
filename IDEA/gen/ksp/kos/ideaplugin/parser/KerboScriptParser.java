// This is a generated file. Not intended for manual editing.
package ksp.kos.ideaplugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static ksp.kos.ideaplugin.psi.KerboScriptTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class KerboScriptParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == ADD_STMT) {
      r = add_stmt(b, 0);
    }
    else if (t == AND_EXPR) {
      r = and_expr(b, 0);
    }
    else if (t == ARGLIST) {
      r = arglist(b, 0);
    }
    else if (t == ARITH_EXPR) {
      r = arith_expr(b, 0);
    }
    else if (t == ARRAY_TRAILER) {
      r = array_trailer(b, 0);
    }
    else if (t == ATOM) {
      r = atom(b, 0);
    }
    else if (t == BREAK_STMT) {
      r = break_stmt(b, 0);
    }
    else if (t == CLEAR_STMT) {
      r = clear_stmt(b, 0);
    }
    else if (t == COMPARE_EXPR) {
      r = compare_expr(b, 0);
    }
    else if (t == COMPILE_STMT) {
      r = compile_stmt(b, 0);
    }
    else if (t == COPY_STMT) {
      r = copy_stmt(b, 0);
    }
    else if (t == DECLARE_FUNCTION_CLAUSE) {
      r = declare_function_clause(b, 0);
    }
    else if (t == DECLARE_IDENTIFIER_CLAUSE) {
      r = declare_identifier_clause(b, 0);
    }
    else if (t == DECLARE_LOCK_CLAUSE) {
      r = declare_lock_clause(b, 0);
    }
    else if (t == DECLARE_PARAMETER_CLAUSE) {
      r = declare_parameter_clause(b, 0);
    }
    else if (t == DECLARE_STMT) {
      r = declare_stmt(b, 0);
    }
    else if (t == DELETE_STMT) {
      r = delete_stmt(b, 0);
    }
    else if (t == DIRECTIVE) {
      r = directive(b, 0);
    }
    else if (t == EDIT_STMT) {
      r = edit_stmt(b, 0);
    }
    else if (t == EXPR) {
      r = expr(b, 0);
    }
    else if (t == FACTOR) {
      r = factor(b, 0);
    }
    else if (t == FOR_STMT) {
      r = for_stmt(b, 0);
    }
    else if (t == FROMLOOP_STMT) {
      r = fromloop_stmt(b, 0);
    }
    else if (t == FUNCTION_TRAILER) {
      r = function_trailer(b, 0);
    }
    else if (t == IDENTIFIER_LED_EXPR) {
      r = identifier_led_expr(b, 0);
    }
    else if (t == IDENTIFIER_LED_STMT) {
      r = identifier_led_stmt(b, 0);
    }
    else if (t == IF_STMT) {
      r = if_stmt(b, 0);
    }
    else if (t == INSTRUCTION) {
      r = instruction(b, 0);
    }
    else if (t == INSTRUCTION_BLOCK) {
      r = instruction_block(b, 0);
    }
    else if (t == LAZYGLOBAL_DIRECTIVE) {
      r = lazyglobal_directive(b, 0);
    }
    else if (t == LIST_STMT) {
      r = list_stmt(b, 0);
    }
    else if (t == LOG_STMT) {
      r = log_stmt(b, 0);
    }
    else if (t == MULTDIV_EXPR) {
      r = multdiv_expr(b, 0);
    }
    else if (t == NUMBER) {
      r = number(b, 0);
    }
    else if (t == ON_STMT) {
      r = on_stmt(b, 0);
    }
    else if (t == ONOFF_STMT) {
      r = onoff_stmt(b, 0);
    }
    else if (t == ONOFF_TRAILER) {
      r = onoff_trailer(b, 0);
    }
    else if (t == PRESERVE_STMT) {
      r = preserve_stmt(b, 0);
    }
    else if (t == PRINT_STMT) {
      r = print_stmt(b, 0);
    }
    else if (t == REBOOT_STMT) {
      r = reboot_stmt(b, 0);
    }
    else if (t == REMOVE_STMT) {
      r = remove_stmt(b, 0);
    }
    else if (t == RENAME_STMT) {
      r = rename_stmt(b, 0);
    }
    else if (t == RETURN_STMT) {
      r = return_stmt(b, 0);
    }
    else if (t == RUN_STMT) {
      r = run_stmt(b, 0);
    }
    else if (t == SCI_NUMBER) {
      r = sci_number(b, 0);
    }
    else if (t == SET_STMT) {
      r = set_stmt(b, 0);
    }
    else if (t == SHUTDOWN_STMT) {
      r = shutdown_stmt(b, 0);
    }
    else if (t == STAGE_STMT) {
      r = stage_stmt(b, 0);
    }
    else if (t == SUFFIX) {
      r = suffix(b, 0);
    }
    else if (t == SUFFIX_TRAILER) {
      r = suffix_trailer(b, 0);
    }
    else if (t == SUFFIXTERM) {
      r = suffixterm(b, 0);
    }
    else if (t == SUFFIXTERM_TRAILER) {
      r = suffixterm_trailer(b, 0);
    }
    else if (t == SWITCH_STMT) {
      r = switch_stmt(b, 0);
    }
    else if (t == TOGGLE_STMT) {
      r = toggle_stmt(b, 0);
    }
    else if (t == UNARY_EXPR) {
      r = unary_expr(b, 0);
    }
    else if (t == UNLOCK_STMT) {
      r = unlock_stmt(b, 0);
    }
    else if (t == UNSET_STMT) {
      r = unset_stmt(b, 0);
    }
    else if (t == UNTIL_STMT) {
      r = until_stmt(b, 0);
    }
    else if (t == VARIDENTIFIER) {
      r = varidentifier(b, 0);
    }
    else if (t == WAIT_STMT) {
      r = wait_stmt(b, 0);
    }
    else if (t == WHEN_STMT) {
      r = when_stmt(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return Start(b, l + 1);
  }

  /* ********************************************************** */
  // (instruction)*
  static boolean Start(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Start")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Start_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Start", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (instruction)
  private static boolean Start_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Start_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = instruction(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ADD expr EOI
  public static boolean add_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_stmt")) return false;
    if (!nextTokenIs(b, ADD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ADD);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, ADD_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // compare_expr (AND compare_expr)*
  public static boolean and_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "and_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<and expr>");
    r = compare_expr(b, l + 1);
    r = r && and_expr_1(b, l + 1);
    exit_section_(b, l, m, AND_EXPR, r, false, null);
    return r;
  }

  // (AND compare_expr)*
  private static boolean and_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "and_expr_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!and_expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "and_expr_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // AND compare_expr
  private static boolean and_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "and_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, AND);
    r = r && compare_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expr (COMMA expr)*
  public static boolean arglist(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arglist")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<arglist>");
    r = expr(b, l + 1);
    r = r && arglist_1(b, l + 1);
    exit_section_(b, l, m, ARGLIST, r, false, null);
    return r;
  }

  // (COMMA expr)*
  private static boolean arglist_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arglist_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!arglist_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "arglist_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA expr
  private static boolean arglist_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arglist_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // multdiv_expr (PLUSMINUS multdiv_expr)*
  public static boolean arith_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arith_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<arith expr>");
    r = multdiv_expr(b, l + 1);
    r = r && arith_expr_1(b, l + 1);
    exit_section_(b, l, m, ARITH_EXPR, r, false, null);
    return r;
  }

  // (PLUSMINUS multdiv_expr)*
  private static boolean arith_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arith_expr_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!arith_expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "arith_expr_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // PLUSMINUS multdiv_expr
  private static boolean arith_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arith_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PLUSMINUS);
    r = r && multdiv_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (ARRAYINDEX (IDENTIFIER | INTEGER)) | (SQUAREOPEN expr SQUARECLOSE)
  public static boolean array_trailer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_trailer")) return false;
    if (!nextTokenIs(b, "<array trailer>", ARRAYINDEX, SQUAREOPEN)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<array trailer>");
    r = array_trailer_0(b, l + 1);
    if (!r) r = array_trailer_1(b, l + 1);
    exit_section_(b, l, m, ARRAY_TRAILER, r, false, null);
    return r;
  }

  // ARRAYINDEX (IDENTIFIER | INTEGER)
  private static boolean array_trailer_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_trailer_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ARRAYINDEX);
    r = r && array_trailer_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // IDENTIFIER | INTEGER
  private static boolean array_trailer_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_trailer_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, INTEGER);
    exit_section_(b, m, null, r);
    return r;
  }

  // SQUAREOPEN expr SQUARECLOSE
  private static boolean array_trailer_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_trailer_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SQUAREOPEN);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, SQUARECLOSE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ( sci_number |
  //                         TRUEFALSE |
  //                         IDENTIFIER |
  //                         FILEIDENT |
  //                         BRACKETOPEN expr BRACKETCLOSE
  //                       ) | STRING
  public static boolean atom(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atom")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<atom>");
    r = atom_0(b, l + 1);
    if (!r) r = consumeToken(b, STRING);
    exit_section_(b, l, m, ATOM, r, false, null);
    return r;
  }

  // sci_number |
  //                         TRUEFALSE |
  //                         IDENTIFIER |
  //                         FILEIDENT |
  //                         BRACKETOPEN expr BRACKETCLOSE
  private static boolean atom_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atom_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sci_number(b, l + 1);
    if (!r) r = consumeToken(b, TRUEFALSE);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, FILEIDENT);
    if (!r) r = atom_0_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // BRACKETOPEN expr BRACKETCLOSE
  private static boolean atom_0_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atom_0_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BRACKETOPEN);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, BRACKETCLOSE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // BREAK EOI
  public static boolean break_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "break_stmt")) return false;
    if (!nextTokenIs(b, BREAK)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, BREAK, EOI);
    exit_section_(b, m, BREAK_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // CLEARSCREEN EOI
  public static boolean clear_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "clear_stmt")) return false;
    if (!nextTokenIs(b, CLEARSCREEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CLEARSCREEN, EOI);
    exit_section_(b, m, CLEAR_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // arith_expr (COMPARATOR arith_expr)*
  public static boolean compare_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compare_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<compare expr>");
    r = arith_expr(b, l + 1);
    r = r && compare_expr_1(b, l + 1);
    exit_section_(b, l, m, COMPARE_EXPR, r, false, null);
    return r;
  }

  // (COMPARATOR arith_expr)*
  private static boolean compare_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compare_expr_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!compare_expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "compare_expr_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMPARATOR arith_expr
  private static boolean compare_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compare_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMPARATOR);
    r = r && arith_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // COMPILE expr (TO expr)? EOI
  public static boolean compile_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compile_stmt")) return false;
    if (!nextTokenIs(b, COMPILE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMPILE);
    r = r && expr(b, l + 1);
    r = r && compile_stmt_2(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, COMPILE_STMT, r);
    return r;
  }

  // (TO expr)?
  private static boolean compile_stmt_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compile_stmt_2")) return false;
    compile_stmt_2_0(b, l + 1);
    return true;
  }

  // TO expr
  private static boolean compile_stmt_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compile_stmt_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TO);
    r = r && expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // COPY expr (FROM | TO) expr EOI
  public static boolean copy_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "copy_stmt")) return false;
    if (!nextTokenIs(b, COPY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COPY);
    r = r && expr(b, l + 1);
    r = r && copy_stmt_2(b, l + 1);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, COPY_STMT, r);
    return r;
  }

  // FROM | TO
  private static boolean copy_stmt_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "copy_stmt_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FROM);
    if (!r) r = consumeToken(b, TO);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // FUNCTION IDENTIFIER instruction_block EOI?
  public static boolean declare_function_clause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_function_clause")) return false;
    if (!nextTokenIs(b, FUNCTION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, FUNCTION, IDENTIFIER);
    r = r && instruction_block(b, l + 1);
    r = r && declare_function_clause_3(b, l + 1);
    exit_section_(b, m, DECLARE_FUNCTION_CLAUSE, r);
    return r;
  }

  // EOI?
  private static boolean declare_function_clause_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_function_clause_3")) return false;
    consumeToken(b, EOI);
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER (TO|IS) expr EOI
  public static boolean declare_identifier_clause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_identifier_clause")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && declare_identifier_clause_1(b, l + 1);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, DECLARE_IDENTIFIER_CLAUSE, r);
    return r;
  }

  // TO|IS
  private static boolean declare_identifier_clause_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_identifier_clause_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TO);
    if (!r) r = consumeToken(b, IS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LOCK IDENTIFIER TO expr EOI
  public static boolean declare_lock_clause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_lock_clause")) return false;
    if (!nextTokenIs(b, LOCK)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LOCK, IDENTIFIER, TO);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, DECLARE_LOCK_CLAUSE, r);
    return r;
  }

  /* ********************************************************** */
  // PARAMETER IDENTIFIER ((TO|IS) expr)? (COMMA IDENTIFIER ((TO|IS) expr)?)* EOI
  public static boolean declare_parameter_clause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_parameter_clause")) return false;
    if (!nextTokenIs(b, PARAMETER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PARAMETER, IDENTIFIER);
    r = r && declare_parameter_clause_2(b, l + 1);
    r = r && declare_parameter_clause_3(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, DECLARE_PARAMETER_CLAUSE, r);
    return r;
  }

  // ((TO|IS) expr)?
  private static boolean declare_parameter_clause_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_parameter_clause_2")) return false;
    declare_parameter_clause_2_0(b, l + 1);
    return true;
  }

  // (TO|IS) expr
  private static boolean declare_parameter_clause_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_parameter_clause_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = declare_parameter_clause_2_0_0(b, l + 1);
    r = r && expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // TO|IS
  private static boolean declare_parameter_clause_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_parameter_clause_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TO);
    if (!r) r = consumeToken(b, IS);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA IDENTIFIER ((TO|IS) expr)?)*
  private static boolean declare_parameter_clause_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_parameter_clause_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!declare_parameter_clause_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "declare_parameter_clause_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA IDENTIFIER ((TO|IS) expr)?
  private static boolean declare_parameter_clause_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_parameter_clause_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, IDENTIFIER);
    r = r && declare_parameter_clause_3_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ((TO|IS) expr)?
  private static boolean declare_parameter_clause_3_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_parameter_clause_3_0_2")) return false;
    declare_parameter_clause_3_0_2_0(b, l + 1);
    return true;
  }

  // (TO|IS) expr
  private static boolean declare_parameter_clause_3_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_parameter_clause_3_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = declare_parameter_clause_3_0_2_0_0(b, l + 1);
    r = r && expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // TO|IS
  private static boolean declare_parameter_clause_3_0_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_parameter_clause_3_0_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TO);
    if (!r) r = consumeToken(b, IS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // declare_parameter_clause |
  //                       declare_function_clause |
  //                       declare_lock_clause |
  //                       (
  //                           (
  //                               (DECLARE (LOCAL|GLOBAL)?) |
  //                               (LOCAL|GLOBAL)
  //                           )
  //                           (
  //                               declare_parameter_clause |
  //                               declare_function_clause |
  //                               declare_identifier_clause |
  //                               declare_lock_clause
  //                           )
  //                       )
  public static boolean declare_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_stmt")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<declare stmt>");
    r = declare_parameter_clause(b, l + 1);
    if (!r) r = declare_function_clause(b, l + 1);
    if (!r) r = declare_lock_clause(b, l + 1);
    if (!r) r = declare_stmt_3(b, l + 1);
    exit_section_(b, l, m, DECLARE_STMT, r, false, null);
    return r;
  }

  // (
  //                               (DECLARE (LOCAL|GLOBAL)?) |
  //                               (LOCAL|GLOBAL)
  //                           )
  //                           (
  //                               declare_parameter_clause |
  //                               declare_function_clause |
  //                               declare_identifier_clause |
  //                               declare_lock_clause
  //                           )
  private static boolean declare_stmt_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_stmt_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = declare_stmt_3_0(b, l + 1);
    r = r && declare_stmt_3_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (DECLARE (LOCAL|GLOBAL)?) |
  //                               (LOCAL|GLOBAL)
  private static boolean declare_stmt_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_stmt_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = declare_stmt_3_0_0(b, l + 1);
    if (!r) r = declare_stmt_3_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // DECLARE (LOCAL|GLOBAL)?
  private static boolean declare_stmt_3_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_stmt_3_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DECLARE);
    r = r && declare_stmt_3_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (LOCAL|GLOBAL)?
  private static boolean declare_stmt_3_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_stmt_3_0_0_1")) return false;
    declare_stmt_3_0_0_1_0(b, l + 1);
    return true;
  }

  // LOCAL|GLOBAL
  private static boolean declare_stmt_3_0_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_stmt_3_0_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LOCAL);
    if (!r) r = consumeToken(b, GLOBAL);
    exit_section_(b, m, null, r);
    return r;
  }

  // LOCAL|GLOBAL
  private static boolean declare_stmt_3_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_stmt_3_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LOCAL);
    if (!r) r = consumeToken(b, GLOBAL);
    exit_section_(b, m, null, r);
    return r;
  }

  // declare_parameter_clause |
  //                               declare_function_clause |
  //                               declare_identifier_clause |
  //                               declare_lock_clause
  private static boolean declare_stmt_3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declare_stmt_3_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = declare_parameter_clause(b, l + 1);
    if (!r) r = declare_function_clause(b, l + 1);
    if (!r) r = declare_identifier_clause(b, l + 1);
    if (!r) r = declare_lock_clause(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // DELETE expr (FROM expr)? EOI
  public static boolean delete_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "delete_stmt")) return false;
    if (!nextTokenIs(b, DELETE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DELETE);
    r = r && expr(b, l + 1);
    r = r && delete_stmt_2(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, DELETE_STMT, r);
    return r;
  }

  // (FROM expr)?
  private static boolean delete_stmt_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "delete_stmt_2")) return false;
    delete_stmt_2_0(b, l + 1);
    return true;
  }

  // FROM expr
  private static boolean delete_stmt_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "delete_stmt_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FROM);
    r = r && expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // lazyglobal_directive
  public static boolean directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "directive")) return false;
    if (!nextTokenIs(b, ATSIGN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = lazyglobal_directive(b, l + 1);
    exit_section_(b, m, DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // EDIT expr EOI
  public static boolean edit_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "edit_stmt")) return false;
    if (!nextTokenIs(b, EDIT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EDIT);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, EDIT_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // and_expr (OR and_expr)*
  public static boolean expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<expr>");
    r = and_expr(b, l + 1);
    r = r && expr_1(b, l + 1);
    exit_section_(b, l, m, EXPR, r, false, null);
    return r;
  }

  // (OR and_expr)*
  private static boolean expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expr_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "expr_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // OR and_expr
  private static boolean expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OR);
    r = r && and_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // suffix (POWER suffix)*
  public static boolean factor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "factor")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<factor>");
    r = suffix(b, l + 1);
    r = r && factor_1(b, l + 1);
    exit_section_(b, l, m, FACTOR, r, false, null);
    return r;
  }

  // (POWER suffix)*
  private static boolean factor_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "factor_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!factor_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "factor_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // POWER suffix
  private static boolean factor_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "factor_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, POWER);
    r = r && suffix(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // FOR IDENTIFIER IN varidentifier instruction EOI?
  public static boolean for_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_stmt")) return false;
    if (!nextTokenIs(b, FOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, FOR, IDENTIFIER, IN);
    r = r && varidentifier(b, l + 1);
    r = r && instruction(b, l + 1);
    r = r && for_stmt_5(b, l + 1);
    exit_section_(b, m, FOR_STMT, r);
    return r;
  }

  // EOI?
  private static boolean for_stmt_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_stmt_5")) return false;
    consumeToken(b, EOI);
    return true;
  }

  /* ********************************************************** */
  // FROM instruction_block UNTIL expr STEP instruction_block DO instruction EOI?
  public static boolean fromloop_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fromloop_stmt")) return false;
    if (!nextTokenIs(b, FROM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FROM);
    r = r && instruction_block(b, l + 1);
    r = r && consumeToken(b, UNTIL);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, STEP);
    r = r && instruction_block(b, l + 1);
    r = r && consumeToken(b, DO);
    r = r && instruction(b, l + 1);
    r = r && fromloop_stmt_8(b, l + 1);
    exit_section_(b, m, FROMLOOP_STMT, r);
    return r;
  }

  // EOI?
  private static boolean fromloop_stmt_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fromloop_stmt_8")) return false;
    consumeToken(b, EOI);
    return true;
  }

  /* ********************************************************** */
  // BRACKETOPEN arglist? BRACKETCLOSE
  public static boolean function_trailer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_trailer")) return false;
    if (!nextTokenIs(b, BRACKETOPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BRACKETOPEN);
    r = r && function_trailer_1(b, l + 1);
    r = r && consumeToken(b, BRACKETCLOSE);
    exit_section_(b, m, FUNCTION_TRAILER, r);
    return r;
  }

  // arglist?
  private static boolean function_trailer_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_trailer_1")) return false;
    arglist(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // suffix (onoff_trailer)?
  public static boolean identifier_led_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier_led_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<identifier led expr>");
    r = suffix(b, l + 1);
    r = r && identifier_led_expr_1(b, l + 1);
    exit_section_(b, l, m, IDENTIFIER_LED_EXPR, r, false, null);
    return r;
  }

  // (onoff_trailer)?
  private static boolean identifier_led_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier_led_expr_1")) return false;
    identifier_led_expr_1_0(b, l + 1);
    return true;
  }

  // (onoff_trailer)
  private static boolean identifier_led_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier_led_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = onoff_trailer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier_led_expr EOI
  public static boolean identifier_led_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier_led_stmt")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<identifier led stmt>");
    r = identifier_led_expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, l, m, IDENTIFIER_LED_STMT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // IF expr instruction (ELSE instruction)? EOI?
  public static boolean if_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_stmt")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IF);
    r = r && expr(b, l + 1);
    r = r && instruction(b, l + 1);
    r = r && if_stmt_3(b, l + 1);
    r = r && if_stmt_4(b, l + 1);
    exit_section_(b, m, IF_STMT, r);
    return r;
  }

  // (ELSE instruction)?
  private static boolean if_stmt_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_stmt_3")) return false;
    if_stmt_3_0(b, l + 1);
    return true;
  }

  // ELSE instruction
  private static boolean if_stmt_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_stmt_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSE);
    r = r && instruction(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // EOI?
  private static boolean if_stmt_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_stmt_4")) return false;
    consumeToken(b, EOI);
    return true;
  }

  /* ********************************************************** */
  // set_stmt | 
  //                if_stmt |
  //                until_stmt |
  //                fromloop_stmt |
  //                unlock_stmt |
  //                print_stmt |
  //                on_stmt |
  //                toggle_stmt |
  //                wait_stmt |
  //                when_stmt |
  //                stage_stmt |
  //                clear_stmt |
  //                add_stmt |
  //                remove_stmt |
  //                log_stmt |
  //                break_stmt |
  //                preserve_stmt |
  //                declare_stmt |
  //                return_stmt |
  //                switch_stmt |
  //                copy_stmt |
  //                rename_stmt |
  //                delete_stmt |
  //                edit_stmt |
  //                run_stmt |
  //                compile_stmt |
  //                list_stmt |
  //                reboot_stmt |
  //                shutdown_stmt |
  //                for_stmt |
  //                unset_stmt |
  //                instruction_block |
  //                identifier_led_stmt | // any statement that starts with an identifier.
  //                directive
  public static boolean instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "instruction")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<instruction>");
    r = set_stmt(b, l + 1);
    if (!r) r = if_stmt(b, l + 1);
    if (!r) r = until_stmt(b, l + 1);
    if (!r) r = fromloop_stmt(b, l + 1);
    if (!r) r = unlock_stmt(b, l + 1);
    if (!r) r = print_stmt(b, l + 1);
    if (!r) r = on_stmt(b, l + 1);
    if (!r) r = toggle_stmt(b, l + 1);
    if (!r) r = wait_stmt(b, l + 1);
    if (!r) r = when_stmt(b, l + 1);
    if (!r) r = stage_stmt(b, l + 1);
    if (!r) r = clear_stmt(b, l + 1);
    if (!r) r = add_stmt(b, l + 1);
    if (!r) r = remove_stmt(b, l + 1);
    if (!r) r = log_stmt(b, l + 1);
    if (!r) r = break_stmt(b, l + 1);
    if (!r) r = preserve_stmt(b, l + 1);
    if (!r) r = declare_stmt(b, l + 1);
    if (!r) r = return_stmt(b, l + 1);
    if (!r) r = switch_stmt(b, l + 1);
    if (!r) r = copy_stmt(b, l + 1);
    if (!r) r = rename_stmt(b, l + 1);
    if (!r) r = delete_stmt(b, l + 1);
    if (!r) r = edit_stmt(b, l + 1);
    if (!r) r = run_stmt(b, l + 1);
    if (!r) r = compile_stmt(b, l + 1);
    if (!r) r = list_stmt(b, l + 1);
    if (!r) r = reboot_stmt(b, l + 1);
    if (!r) r = shutdown_stmt(b, l + 1);
    if (!r) r = for_stmt(b, l + 1);
    if (!r) r = unset_stmt(b, l + 1);
    if (!r) r = instruction_block(b, l + 1);
    if (!r) r = identifier_led_stmt(b, l + 1);
    if (!r) r = directive(b, l + 1);
    exit_section_(b, l, m, INSTRUCTION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // CURLYOPEN instruction* CURLYCLOSE EOI?
  public static boolean instruction_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "instruction_block")) return false;
    if (!nextTokenIs(b, CURLYOPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CURLYOPEN);
    r = r && instruction_block_1(b, l + 1);
    r = r && consumeToken(b, CURLYCLOSE);
    r = r && instruction_block_3(b, l + 1);
    exit_section_(b, m, INSTRUCTION_BLOCK, r);
    return r;
  }

  // instruction*
  private static boolean instruction_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "instruction_block_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!instruction(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "instruction_block_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // EOI?
  private static boolean instruction_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "instruction_block_3")) return false;
    consumeToken(b, EOI);
    return true;
  }

  /* ********************************************************** */
  // ATSIGN LAZYGLOBAL onoff_trailer EOI
  public static boolean lazyglobal_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lazyglobal_directive")) return false;
    if (!nextTokenIs(b, ATSIGN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ATSIGN, LAZYGLOBAL);
    r = r && onoff_trailer(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, LAZYGLOBAL_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // LIST (IDENTIFIER (IN IDENTIFIER)?)? EOI
  public static boolean list_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_stmt")) return false;
    if (!nextTokenIs(b, LIST)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LIST);
    r = r && list_stmt_1(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, LIST_STMT, r);
    return r;
  }

  // (IDENTIFIER (IN IDENTIFIER)?)?
  private static boolean list_stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_stmt_1")) return false;
    list_stmt_1_0(b, l + 1);
    return true;
  }

  // IDENTIFIER (IN IDENTIFIER)?
  private static boolean list_stmt_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_stmt_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && list_stmt_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (IN IDENTIFIER)?
  private static boolean list_stmt_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_stmt_1_0_1")) return false;
    list_stmt_1_0_1_0(b, l + 1);
    return true;
  }

  // IN IDENTIFIER
  private static boolean list_stmt_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_stmt_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IN, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LOG expr TO expr EOI
  public static boolean log_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "log_stmt")) return false;
    if (!nextTokenIs(b, LOG)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LOG);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, TO);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, LOG_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // unary_expr ((MULT|DIV) unary_expr)*
  public static boolean multdiv_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multdiv_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<multdiv expr>");
    r = unary_expr(b, l + 1);
    r = r && multdiv_expr_1(b, l + 1);
    exit_section_(b, l, m, MULTDIV_EXPR, r, false, null);
    return r;
  }

  // ((MULT|DIV) unary_expr)*
  private static boolean multdiv_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multdiv_expr_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!multdiv_expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "multdiv_expr_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (MULT|DIV) unary_expr
  private static boolean multdiv_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multdiv_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = multdiv_expr_1_0_0(b, l + 1);
    r = r && unary_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // MULT|DIV
  private static boolean multdiv_expr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multdiv_expr_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MULT);
    if (!r) r = consumeToken(b, DIV);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // INTEGER | DOUBLE
  public static boolean number(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "number")) return false;
    if (!nextTokenIs(b, "<number>", DOUBLE, INTEGER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<number>");
    r = consumeToken(b, INTEGER);
    if (!r) r = consumeToken(b, DOUBLE);
    exit_section_(b, l, m, NUMBER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ON varidentifier instruction EOI?
  public static boolean on_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "on_stmt")) return false;
    if (!nextTokenIs(b, ON)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ON);
    r = r && varidentifier(b, l + 1);
    r = r && instruction(b, l + 1);
    r = r && on_stmt_3(b, l + 1);
    exit_section_(b, m, ON_STMT, r);
    return r;
  }

  // EOI?
  private static boolean on_stmt_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "on_stmt_3")) return false;
    consumeToken(b, EOI);
    return true;
  }

  /* ********************************************************** */
  // varidentifier onoff_trailer EOI
  public static boolean onoff_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "onoff_stmt")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<onoff stmt>");
    r = varidentifier(b, l + 1);
    r = r && onoff_trailer(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, l, m, ONOFF_STMT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ON | OFF
  public static boolean onoff_trailer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "onoff_trailer")) return false;
    if (!nextTokenIs(b, "<onoff trailer>", OFF, ON)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<onoff trailer>");
    r = consumeToken(b, ON);
    if (!r) r = consumeToken(b, OFF);
    exit_section_(b, l, m, ONOFF_TRAILER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PRESERVE EOI
  public static boolean preserve_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preserve_stmt")) return false;
    if (!nextTokenIs(b, PRESERVE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PRESERVE, EOI);
    exit_section_(b, m, PRESERVE_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // PRINT expr (AT BRACKETOPEN expr COMMA expr BRACKETCLOSE)? EOI
  public static boolean print_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "print_stmt")) return false;
    if (!nextTokenIs(b, PRINT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PRINT);
    r = r && expr(b, l + 1);
    r = r && print_stmt_2(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, PRINT_STMT, r);
    return r;
  }

  // (AT BRACKETOPEN expr COMMA expr BRACKETCLOSE)?
  private static boolean print_stmt_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "print_stmt_2")) return false;
    print_stmt_2_0(b, l + 1);
    return true;
  }

  // AT BRACKETOPEN expr COMMA expr BRACKETCLOSE
  private static boolean print_stmt_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "print_stmt_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, AT, BRACKETOPEN);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, BRACKETCLOSE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // REBOOT EOI
  public static boolean reboot_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reboot_stmt")) return false;
    if (!nextTokenIs(b, REBOOT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, REBOOT, EOI);
    exit_section_(b, m, REBOOT_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // REMOVE expr EOI
  public static boolean remove_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "remove_stmt")) return false;
    if (!nextTokenIs(b, REMOVE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, REMOVE);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, REMOVE_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // RENAME (VOLUME | FILE)? expr TO expr EOI
  public static boolean rename_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rename_stmt")) return false;
    if (!nextTokenIs(b, RENAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RENAME);
    r = r && rename_stmt_1(b, l + 1);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, TO);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, RENAME_STMT, r);
    return r;
  }

  // (VOLUME | FILE)?
  private static boolean rename_stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rename_stmt_1")) return false;
    rename_stmt_1_0(b, l + 1);
    return true;
  }

  // VOLUME | FILE
  private static boolean rename_stmt_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rename_stmt_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VOLUME);
    if (!r) r = consumeToken(b, FILE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // RETURN expr? EOI
  public static boolean return_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_stmt")) return false;
    if (!nextTokenIs(b, RETURN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RETURN);
    r = r && return_stmt_1(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, RETURN_STMT, r);
    return r;
  }

  // expr?
  private static boolean return_stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_stmt_1")) return false;
    expr(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // RUN (ONCE)? (FILEIDENT|IDENTIFIER) (BRACKETOPEN arglist BRACKETCLOSE)? (ON expr)? EOI
  public static boolean run_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "run_stmt")) return false;
    if (!nextTokenIs(b, RUN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RUN);
    r = r && run_stmt_1(b, l + 1);
    r = r && run_stmt_2(b, l + 1);
    r = r && run_stmt_3(b, l + 1);
    r = r && run_stmt_4(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, RUN_STMT, r);
    return r;
  }

  // (ONCE)?
  private static boolean run_stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "run_stmt_1")) return false;
    consumeToken(b, ONCE);
    return true;
  }

  // FILEIDENT|IDENTIFIER
  private static boolean run_stmt_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "run_stmt_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FILEIDENT);
    if (!r) r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  // (BRACKETOPEN arglist BRACKETCLOSE)?
  private static boolean run_stmt_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "run_stmt_3")) return false;
    run_stmt_3_0(b, l + 1);
    return true;
  }

  // BRACKETOPEN arglist BRACKETCLOSE
  private static boolean run_stmt_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "run_stmt_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BRACKETOPEN);
    r = r && arglist(b, l + 1);
    r = r && consumeToken(b, BRACKETCLOSE);
    exit_section_(b, m, null, r);
    return r;
  }

  // (ON expr)?
  private static boolean run_stmt_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "run_stmt_4")) return false;
    run_stmt_4_0(b, l + 1);
    return true;
  }

  // ON expr
  private static boolean run_stmt_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "run_stmt_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ON);
    r = r && expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // number (E PLUSMINUS? INTEGER)?
  public static boolean sci_number(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sci_number")) return false;
    if (!nextTokenIs(b, "<sci number>", DOUBLE, INTEGER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<sci number>");
    r = number(b, l + 1);
    r = r && sci_number_1(b, l + 1);
    exit_section_(b, l, m, SCI_NUMBER, r, false, null);
    return r;
  }

  // (E PLUSMINUS? INTEGER)?
  private static boolean sci_number_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sci_number_1")) return false;
    sci_number_1_0(b, l + 1);
    return true;
  }

  // E PLUSMINUS? INTEGER
  private static boolean sci_number_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sci_number_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, E);
    r = r && sci_number_1_0_1(b, l + 1);
    r = r && consumeToken(b, INTEGER);
    exit_section_(b, m, null, r);
    return r;
  }

  // PLUSMINUS?
  private static boolean sci_number_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sci_number_1_0_1")) return false;
    consumeToken(b, PLUSMINUS);
    return true;
  }

  /* ********************************************************** */
  // SET varidentifier TO expr EOI
  public static boolean set_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_stmt")) return false;
    if (!nextTokenIs(b, SET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SET);
    r = r && varidentifier(b, l + 1);
    r = r && consumeToken(b, TO);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, SET_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // SHUTDOWN EOI
  public static boolean shutdown_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shutdown_stmt")) return false;
    if (!nextTokenIs(b, SHUTDOWN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SHUTDOWN, EOI);
    exit_section_(b, m, SHUTDOWN_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // STAGE EOI
  public static boolean stage_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stage_stmt")) return false;
    if (!nextTokenIs(b, STAGE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, STAGE, EOI);
    exit_section_(b, m, STAGE_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // suffixterm (suffix_trailer)*
  public static boolean suffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "suffix")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<suffix>");
    r = suffixterm(b, l + 1);
    r = r && suffix_1(b, l + 1);
    exit_section_(b, l, m, SUFFIX, r, false, null);
    return r;
  }

  // (suffix_trailer)*
  private static boolean suffix_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "suffix_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!suffix_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "suffix_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (suffix_trailer)
  private static boolean suffix_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "suffix_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = suffix_trailer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // COLON suffixterm
  public static boolean suffix_trailer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "suffix_trailer")) return false;
    if (!nextTokenIs(b, COLON)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && suffixterm(b, l + 1);
    exit_section_(b, m, SUFFIX_TRAILER, r);
    return r;
  }

  /* ********************************************************** */
  // atom suffixterm_trailer*
  public static boolean suffixterm(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "suffixterm")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<suffixterm>");
    r = atom(b, l + 1);
    r = r && suffixterm_1(b, l + 1);
    exit_section_(b, l, m, SUFFIXTERM, r, false, null);
    return r;
  }

  // suffixterm_trailer*
  private static boolean suffixterm_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "suffixterm_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!suffixterm_trailer(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "suffixterm_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // function_trailer | array_trailer
  public static boolean suffixterm_trailer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "suffixterm_trailer")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<suffixterm trailer>");
    r = function_trailer(b, l + 1);
    if (!r) r = array_trailer(b, l + 1);
    exit_section_(b, l, m, SUFFIXTERM_TRAILER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // SWITCH TO expr EOI
  public static boolean switch_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_stmt")) return false;
    if (!nextTokenIs(b, SWITCH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SWITCH, TO);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, SWITCH_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // TOGGLE varidentifier EOI
  public static boolean toggle_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "toggle_stmt")) return false;
    if (!nextTokenIs(b, TOGGLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TOGGLE);
    r = r && varidentifier(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, TOGGLE_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // (PLUSMINUS|NOT|DEFINED)? factor
  public static boolean unary_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<unary expr>");
    r = unary_expr_0(b, l + 1);
    r = r && factor(b, l + 1);
    exit_section_(b, l, m, UNARY_EXPR, r, false, null);
    return r;
  }

  // (PLUSMINUS|NOT|DEFINED)?
  private static boolean unary_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_expr_0")) return false;
    unary_expr_0_0(b, l + 1);
    return true;
  }

  // PLUSMINUS|NOT|DEFINED
  private static boolean unary_expr_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_expr_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PLUSMINUS);
    if (!r) r = consumeToken(b, NOT);
    if (!r) r = consumeToken(b, DEFINED);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // UNLOCK (IDENTIFIER | ALL) EOI
  public static boolean unlock_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unlock_stmt")) return false;
    if (!nextTokenIs(b, UNLOCK)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, UNLOCK);
    r = r && unlock_stmt_1(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, UNLOCK_STMT, r);
    return r;
  }

  // IDENTIFIER | ALL
  private static boolean unlock_stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unlock_stmt_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, ALL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // UNSET (IDENTIFIER | ALL) EOI
  public static boolean unset_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unset_stmt")) return false;
    if (!nextTokenIs(b, UNSET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, UNSET);
    r = r && unset_stmt_1(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, UNSET_STMT, r);
    return r;
  }

  // IDENTIFIER | ALL
  private static boolean unset_stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unset_stmt_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, ALL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // UNTIL expr instruction EOI?
  public static boolean until_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "until_stmt")) return false;
    if (!nextTokenIs(b, UNTIL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, UNTIL);
    r = r && expr(b, l + 1);
    r = r && instruction(b, l + 1);
    r = r && until_stmt_3(b, l + 1);
    exit_section_(b, m, UNTIL_STMT, r);
    return r;
  }

  // EOI?
  private static boolean until_stmt_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "until_stmt_3")) return false;
    consumeToken(b, EOI);
    return true;
  }

  /* ********************************************************** */
  // suffix
  public static boolean varidentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "varidentifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<varidentifier>");
    r = suffix(b, l + 1);
    exit_section_(b, l, m, VARIDENTIFIER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // WAIT UNTIL? expr EOI
  public static boolean wait_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "wait_stmt")) return false;
    if (!nextTokenIs(b, WAIT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, WAIT);
    r = r && wait_stmt_1(b, l + 1);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, EOI);
    exit_section_(b, m, WAIT_STMT, r);
    return r;
  }

  // UNTIL?
  private static boolean wait_stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "wait_stmt_1")) return false;
    consumeToken(b, UNTIL);
    return true;
  }

  /* ********************************************************** */
  // WHEN expr THEN instruction EOI?
  public static boolean when_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "when_stmt")) return false;
    if (!nextTokenIs(b, WHEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, WHEN);
    r = r && expr(b, l + 1);
    r = r && consumeToken(b, THEN);
    r = r && instruction(b, l + 1);
    r = r && when_stmt_4(b, l + 1);
    exit_section_(b, m, WHEN_STMT, r);
    return r;
  }

  // EOI?
  private static boolean when_stmt_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "when_stmt_4")) return false;
    consumeToken(b, EOI);
    return true;
  }

}
