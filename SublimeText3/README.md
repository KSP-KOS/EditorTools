# Sublime Text 3 - kOS Syntax Highlighting

This syntax definition was ported from [xeger's Atom package](https://github.com/KSP-KOS/EditorTools/tree/develop/Atom).  

To install the kOS definition as is, place the `kOS.tmlLanguage` file your **user** package directory.  his directory by opening Sublime Text and selecting the following:

> Preferences > Browse Packages

The User folder will be among the listed directories.


## Customizations

If you wish to make alternations or additions to the definition, you can use [PackageDev](https://github.com/SublimeText/PackageDev) along with the `kOS.YAML-tmlLanguage` file to create your own version.  An overview of creating syntax definition in Sublime Text can be [found here](http://docs.sublimetext.info/en/latest/extensibility/syntaxdefs.html).


## Additional Coverage

The original syntax highlighting package written by xeger was intended for Atom.  While a direct, unedited port does the job, alterations were made to bring highlighting in line with sublime defaults.  This isn't a commentary on the original, it was just written for a different editor. Additions mostly include coverage for new keywords, methods, and structures not available in kOS when the original Atom package was released.  New additions are ongoing.



### Functional changes

Most functional changes involve taking away highlighting to add contrast to longer lines of code, or to account for contextual usage.  For example `SHIP:HEADING` vs `HEADING(90,90)`.  In this case, `HEADING` can be used as a structure suffix, or like a traditional function call.    

**Changes include**:

  - Parenthesis `( )` were stripped of highlighting (they were showing up as reserved keywords)
  - Suffix colons are no longer highlighted with the suffix to better help identify breaks in long `STRUCTURE:SUFFIX` chains


**Upcoming/Ongoing changes include**:

  - Additional keyword coverage
  - STRUCTURE:SUFFIX validation, which either adds error highlighting, or  simply removes highlighting if a invalid suffix is used with a    structure, or vice versa.  This is more time consuming than difficult as it involves correctly accounting for all possible structure/suffix combinations.  


### Suggestions and Requests

If you feel a current feature could be improved, or if you would like a new feature entirely, I'm always open suggestions and requests. 