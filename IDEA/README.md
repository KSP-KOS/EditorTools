# kOS-IDEs
This plugin enables support for kOS KerboScript in [IntelliJ IDEA](https://www.jetbrains.com/idea/) IDE.

Current kOS version supported: 0.20.1

###INSTALL###
0. Install Intellij IDEA
1. Install [KerboScript(kOS)](https://www.jetbrains.com/idea/help/installing-updating-and-uninstalling-repository-plugins.html)

###COMPILE###
0. Follow [prerequisites steps](http://www.jetbrains.org/intellij/sdk/docs/tutorials/custom_language_support/prerequisites.html)
1. Choose IDEA folder for the new project folder
2. Open KerboScript.bnf file and generate grammar parser (Ctrl + Shift + G)
3. Open KerboScript.flex file and generate lexer (Ctrl + Shift + G). First time IDEA may ask you to choose place for
JFlex libraries. Choose any directory, e.g. project directory.
4. Mark gen folder as source or generated source folder
5. Run the plugin