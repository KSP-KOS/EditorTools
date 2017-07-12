package ksp.kos.tpg2bnf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created on 06/03/16.
 *
 * @author ptasha
 */
public class GrammarTransformer {
    private static final String TPG_PATH = "grammar/kRISC.tpg";
    private static final String FLEX_PATH = "grammar/KerboScript.flex";
    private static final String BNF_PATH = "grammar/KerboScript.bnf";
    private String tpg;
    private String flex;
    private String bnf;

    public static void main(String[] args) throws IOException {
        new GrammarTransformer().transform();
    }

    private void transform() throws IOException {
        tpg = new String(Files.readAllBytes(Paths.get(TPG_PATH)));
        createFlex();
        createBnf();
    }

    private void createFlex() throws IOException {
        flex = tpg.replaceAll("(?s)" +
                        ".*(" +
                        "// Terminals\n" +
                        "// ===================================================\n" +
                        ".*" +
                        ")" +
                        "// Rules\n.*",
                "$1");
        flex = flex.replaceAll("->", "=");
        flex = flex.replaceAll("@\"(.*)\";", "$1");
        flex = flex.replaceAll("\\(\\?i\\)", "");
        flex = flex.replaceAll("\\\\b", "");
        flex = flex.replaceAll("\\\\\"\"", "\\\\\"");
        flex = flex.replaceAll("a-z", "a-zA-Z");
        flex = flex.replaceAll("=(.+?)//", "=$1\\\\/\\\\/");
        flex = flex.replaceAll("=([^\\\\\n]+?)/", "=$1\\\\/");
        flex = flex.replaceAll("(\\[Skip\\])", "//$1");
        flex = flex.replaceAll("(EOF)", "//$1");

        Files.write(Paths.get(FLEX_PATH), flex.getBytes());
    }

    private void createBnf() throws IOException {
        bnf = tpg.replaceAll("(?s)" +
                        ".*(" +
                        "// Rules" +
                        ".*" +
                        ")",
                "$1");
        bnf = bnf.replaceAll("->", "::=");
        bnf = bnf.replaceAll(";", "");
        bnf = bnf.replaceAll("EOF", "");

        Files.write(Paths.get(BNF_PATH), bnf.getBytes());
    }
}
