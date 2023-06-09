package ksp.kos.ideaplugin.parser;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import ksp.kos.ideaplugin.psi.KerboScriptTypes;
import com.intellij.psi.TokenType;

%%

%class KerboScriptLexer
%implements FlexLexer
%unicode
%caseless
%ignorecase
%function advance
%type IElementType
%eof{  return;
%eof}

// Terminals
// ===================================================
//Math
PLUSMINUS    = (\+|-)
MULT         = \*
DIV          = \/
POWER        = \^
E            = e
//Logic
NOT          = not
AND          = and
OR           = or
TRUEFALSE    = true|false
COMPARATOR   = <>|>=|<=|=|>|<
//Instructions tokens
SET          = set
TO           = to
IS           = is
IF           = if
ELSE         = else
UNTIL        = until
STEP         = step
DO           = do
LOCK         = lock
UNLOCK       = unlock
PRINT        = print
AT           = at
ON           = on
TOGGLE       = toggle
WAIT         = wait
WHEN         = when
THEN         = then
OFF          = off
STAGE        = stage
CLEARSCREEN  = clearscreen
ADD          = add
REMOVE       = remove
LOG          = log
BREAK        = break
PRESERVE     = preserve
DECLARE      = declare
DEFINED      = defined
LOCAL        = local
GLOBAL       = global
PARAMETER    = parameter
FUNCTION     = function
RETURN       = return
SWITCH       = switch
COPY         = copy
FROM         = from
RENAME       = rename
VOLUME       = volume
FILE         = file
DELETE       = delete
EDIT         = edit
RUN          = run
RUNPATH      = runpath
RUNONCEPATH  = runoncepath
ONCE         = once
COMPILE      = compile
LIST         = list
REBOOT       = reboot
SHUTDOWN     = shutdown
FOR          = for
UNSET        = unset
CHOOSE       = choose

//Generic
BRACKETOPEN  = \(
BRACKETCLOSE = \)
CURLYOPEN    = \{
CURLYCLOSE   = \}
SQUAREOPEN   = \[
SQUARECLOSE  = \]
COMMA        = ,
COLON        = :
IN           = in
ARRAYINDEX   = #
ALL          = all
IDENTIFIER   = [a-zA-Z_][a-zA-Z0-9_]*
FILEIDENT    = [a-zA-Z_][a-zA-Z0-9_]*(\.[a-zA-Z0-9_][a-zA-Z0-9_]*)*
INTEGER      = [0-9]+
DOUBLE       = [0-9]*\.[0-9]+
STRING       = @?\"(\"\"|[^\"])*\"
EOI          = \.
//Compiler Directives
ATSIGN       = @
LAZYGLOBAL   = lazyglobal
CLOBBERBUILTINS = clobberbuiltins

//Special
//EOF          = ^$
//[Skip]
WHITESPACE   = [\ \n\r\t]+
//[Skip]
COMMENTLINE  = \/\/[^\n]*

%xstate E

%%

{PLUSMINUS}    {return KerboScriptTypes.PLUSMINUS;}
{MULT}    {return KerboScriptTypes.MULT;}
{DIV}    {return KerboScriptTypes.DIV;}
{POWER}    {return KerboScriptTypes.POWER;}
{INTEGER}/{E}    {yybegin(E); return KerboScriptTypes.INTEGER;}
{DOUBLE}/{E}    {yybegin(E); return KerboScriptTypes.DOUBLE;}
<E> {E}    {yybegin(YYINITIAL); return KerboScriptTypes.E;}
//Logic
{NOT}    {return KerboScriptTypes.NOT;}
{AND}    {return KerboScriptTypes.AND;}
{OR}    {return KerboScriptTypes.OR;}
{TRUEFALSE}    {return KerboScriptTypes.TRUEFALSE;}
{COMPARATOR}    {return KerboScriptTypes.COMPARATOR;}
//Instructions tokens
{SET}    {return KerboScriptTypes.SET;}
{TO}    {return KerboScriptTypes.TO;}
{IS}    {return KerboScriptTypes.IS;}
{IF}    {return KerboScriptTypes.IF;}
{ELSE}    {return KerboScriptTypes.ELSE;}
{UNTIL}    {return KerboScriptTypes.UNTIL;}
{STEP}    {return KerboScriptTypes.STEP;}
{DO}    {return KerboScriptTypes.DO;}
{LOCK}    {return KerboScriptTypes.LOCK;}
{UNLOCK}    {return KerboScriptTypes.UNLOCK;}
{PRINT}    {return KerboScriptTypes.PRINT;}
{AT}    {return KerboScriptTypes.AT;}
{ON}    {return KerboScriptTypes.ON;}
{TOGGLE}    {return KerboScriptTypes.TOGGLE;}
{WAIT}    {return KerboScriptTypes.WAIT;}
{WHEN}    {return KerboScriptTypes.WHEN;}
{THEN}    {return KerboScriptTypes.THEN;}
{OFF}    {return KerboScriptTypes.OFF;}
{STAGE}    {return KerboScriptTypes.STAGE;}
{CLEARSCREEN}    {return KerboScriptTypes.CLEARSCREEN;}
{ADD}    {return KerboScriptTypes.ADD;}
{REMOVE}    {return KerboScriptTypes.REMOVE;}
{LOG}    {return KerboScriptTypes.LOG;}
{BREAK}    {return KerboScriptTypes.BREAK;}
{PRESERVE}    {return KerboScriptTypes.PRESERVE;}
{DECLARE}    {return KerboScriptTypes.DECLARE;}
{DEFINED}    {return KerboScriptTypes.DEFINED;}
{LOCAL}    { return KerboScriptTypes.LOCAL;}
{GLOBAL}    {return KerboScriptTypes.GLOBAL;}
{PARAMETER}    {return KerboScriptTypes.PARAMETER;}
{FUNCTION}    {return KerboScriptTypes.FUNCTION;}
{RETURN}    {return KerboScriptTypes.RETURN;}
{SWITCH}    {return KerboScriptTypes.SWITCH;}
{COPY}    {return KerboScriptTypes.COPY;}
{FROM}    {return KerboScriptTypes.FROM;}
{RENAME}    {return KerboScriptTypes.RENAME;}
{VOLUME}    {return KerboScriptTypes.VOLUME;}
{FILE}    {return KerboScriptTypes.FILE;}
{DELETE}    {return KerboScriptTypes.DELETE;}
{EDIT}    {return KerboScriptTypes.EDIT;}
{RUN}    {return KerboScriptTypes.RUN;}
{RUNPATH}    {return KerboScriptTypes.RUNPATH;}
{RUNONCEPATH}    {return KerboScriptTypes.RUNONCEPATH;}
{ONCE}    {return KerboScriptTypes.ONCE;}
{COMPILE}    {return KerboScriptTypes.COMPILE;}
{LIST}    {return KerboScriptTypes.LIST;}
{REBOOT}    {return KerboScriptTypes.REBOOT;}
{SHUTDOWN}    {return KerboScriptTypes.SHUTDOWN;}
{FOR}    {return KerboScriptTypes.FOR;}
{UNSET}    {return KerboScriptTypes.UNSET;}
{CHOOSE}    {return KerboScriptTypes.CHOOSE;}

//Generic
{BRACKETOPEN}    {return KerboScriptTypes.BRACKETOPEN;}
{BRACKETCLOSE}    {return KerboScriptTypes.BRACKETCLOSE;}
{CURLYOPEN}    {return KerboScriptTypes.CURLYOPEN;}
{CURLYCLOSE}    {return KerboScriptTypes.CURLYCLOSE;}
{SQUAREOPEN}    {return KerboScriptTypes.SQUAREOPEN;}
{SQUARECLOSE}    {return KerboScriptTypes.SQUARECLOSE;}
{COMMA}    {return KerboScriptTypes.COMMA;}
{COLON}    {return KerboScriptTypes.COLON;}
{IN}    {return KerboScriptTypes.IN;}
{ARRAYINDEX}    {return KerboScriptTypes.ARRAYINDEX;}
{ALL}    {return KerboScriptTypes.ALL;}
//Compiler Directives
{ATSIGN}    {return KerboScriptTypes.ATSIGN;}
{LAZYGLOBAL}    {return KerboScriptTypes.LAZYGLOBAL;}
{CLOBBERBUILTINS}    {return KerboScriptTypes.CLOBBERBUILTINS;}

{IDENTIFIER}    { return KerboScriptTypes.IDENTIFIER;}
{FILEIDENT}    {return KerboScriptTypes.FILEIDENT;}
{INTEGER}    {return KerboScriptTypes.INTEGER;}
{DOUBLE}    {return KerboScriptTypes.DOUBLE;}
{STRING}    {return KerboScriptTypes.STRING;}
{EOI}    {return KerboScriptTypes.EOI;}
//Special
{WHITESPACE}    {return TokenType.WHITE_SPACE;}
{COMMENTLINE}    {return KerboScriptTypes.COMMENTLINE;}

[^]                { return TokenType.BAD_CHARACTER; }