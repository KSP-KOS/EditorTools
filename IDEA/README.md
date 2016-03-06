# kOS-IDEs
This plugin enables support for kOS KerboScript in IntelliJ IDEA IDE.
Current kOS version supported: 0.19.0
https://www.jetbrains.com/idea/

###INSTALL###
0. Install Intellij IDEA
1. Install "KerboScript(kOS)" plugin
https://www.jetbrains.com/idea/help/installing-updating-and-uninstalling-repository-plugins.html

###COMPILE###
0. Follow prerequisities step
http://www.jetbrains.org/intellij/sdk/docs/tutorials/custom_language_support/prerequisites.html
1. Choose IDEA folder for the new project folder
2. Open KerboScript.bnf file and generate grammar parser (Ctrl + Shift + G)
3. Open KerboScript.flex file and generate lexer (Ctrl + Shift + G)
4. Mark gen folder as source or generated source folder
5. Run the plugin