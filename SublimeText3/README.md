# Sublime Text 3 - kOS Syntax Highlighting

This syntax definition was ported from [xeger's Atom package](https://github.com/KSP-KOS/EditorTools/tree/develop/Atom).  

To install the kOS definition as is, place the `kOS.tmlLanguage` file your **user** package directory.  his directory by opening Sublime Text and selecting the following:

> Preferences > Browse Packages

The User folder will be among the listed directories.


## Customizations

If you wish to make alternations or additions to the definition, you can use [PackageDev](https://github.com/SublimeText/PackageDev) along with the `kOS.YAML-tmlLanguage` file to create your own version.  An overview of creating syntax definition in Sublime Text can be [found here](http://docs.sublimetext.info/en/latest/extensibility/syntaxdefs.html).


## Additional Coverage

xeger provided the ground work for this package, so all credit goes to him.  However, I've added additional coverage for keywords, methods, and structures not included in the original.  New additions are ongoing.  Some changes to the original package were made to add contrast in cases where the same word could be used in different contexts.

For example, `SHIP:HEADING` vs `HEADING(90,90)`.  In this case, `HEADING` can be used as a field, or like a traditional function call.  While highlighting of fields remains unchanged from xeger's version, using it like a function call will result in different syntax highlighting in ST3.  




