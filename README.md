# Kerboscript Developer Tools

EditorTools features code editor syntax definitions, IDE plugins, and general utilities centered around the kerboscript language found in the Kerbal Operating System mod for *Kerbal Space Program*.

## Syntax Definitions

Most of the available tools come in the form of kerboscript syntax definitions.  All editor specific definition files are maintained separately by their respective creators or current maintainer.  Because definition files were created (and are maintained) independently of each other, syntax highlighting style and kerboscript language coverage may differ across editors.

Syntax definitions are available for the following editors/IDEs:
  * [Atom](https://github.com/KSP-KOS/EditorTools/tree/develop/Atom)
  * [BBEdit](https://github.com/KSP-KOS/EditorTools/tree/develop/BBEdit)
  * [gedit](https://github.com/KSP-KOS/EditorTools/tree/develop/Gedit)
  * [IntelliJ IDEA](https://github.com/KSP-KOS/EditorTools/tree/develop/IDEA)
  * [nano](https://github.com/KSP-KOS/EditorTools/tree/develop/Nano)
  * [Notepad++](https://github.com/KSP-KOS/EditorTools/tree/develop/NotepadPlusPlus)
  * [Sublime Text 3](https://github.com/KSP-KOS/EditorTools/tree/develop/SublimeText3)
  * [Vim](https://github.com/KSP-KOS/EditorTools/tree/develop/VIM)

## Utilities

Projects listed here reflect kerboscript developer utilities that are independent of any specific code editor or IDE.

Available utilities include:
 * [KOS Minimiser](https://github.com/KSP-KOS/EditorTools/tree/develop/kOSMinimiser)
   * Standalone tool that provides the script minimiser utility found in the kerboscript IDE [Kode](https://github.com/TN-1/Kode_Desktop).  The CLI tool will remove comments, blank lines, and extra spaces from `.ks` files in order to reduce the amount of disk space the kerboscript file will occupy when loaded into a vehicle control system.

## Updates, Bugs, and New Additions

While EditorTools is a sub-module of the KSP-KOS repository, in respect to functionality, the tools found here are independent of the current version of kOS.  Like kOS, EditorTools is a community supported endeavor, and although most of the available tools operate as expected across different versions of kOS, the current state of a specific module may not reflect the current state of kOS itself.

Because EditorTools primarily provides editor specific kerboscript syntax support, issues are *typically* isolated to missing or incorrect syntax highlighting for reserved words. If you discover any errors or bugs, submitting an issue ticket with the appropriate documentation is both helpful and appreciated.

New additions to EditorTools are always welcome.  Regardless of size or scope, if you have a kerboscript related tool you would like to include, please submit a PR with all project files and a `README` that contains an overview of your module, and if applicable, installation and usage instructions.


