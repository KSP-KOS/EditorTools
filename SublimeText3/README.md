# Sublime Text 3 - kOS Syntax Highlighting

This syntax definition was ported from [xeger's Atom package](https://github.com/KSP-KOS/EditorTools/tree/develop/Atom).  

To install the kOS definition as is, place the `kOS.tmlLanguage` file your **user** package directory.  his directory by opening Sublime Text and selecting the following:

> Preferences > Browse Packages

The User folder will be among the listed directories.


### Additional Coverage

The original syntax highlighting package written by xeger was intended for Atom.  While a direct, unedited port does the job, updates are ongoing to bring highlighting in line with sublime defaults.  This isn't a commentary on the original, it was just written for a different editor. Additions primarily include coverage for additional keywords, along with added support for kOS features not available when the original Atom package was released.


## Supported features

Changes and new additions to the kOS syntax for ST3 are highlighted here. Check the commit log for additional details.     

**Changes include**:

  - Certain punctuation/delimiters were stripped of highlighting to improve readability and provide contrast where needed.  Suffix colons, for example, are no longer highlighted with the suffix to better help identify breaks in long `STRUCTURE:SUFFIX` chains
  - User-defined functions are now supported. Initial definitions vs. calls to those functions receive different highlighting. 
  - Contextual distinction of reserved words; for example `SHIP:HEADING` vs `HEADING(90,90)`.  In this case, `HEADING`'s highlighting will differentiate based on whether its used as a structure suffix, or as a traditional function call
  - Delegate highlighting (similar to how Python decorators are highlighted)
  - Extended action group support.  


**Upcoming/Ongoing changes include**:

  - Additional keyword coverage (check commit log)
  - STRUCTURE:SUFFIX validation, which prevents highlighting if an invalid suffix is used with a structure, or vice versa.  This is supported on most newly added member groups, but updating old groups is an ongoing process.  
  - Anonymous function support.
  - Highlighting for multiple parameters declared on a single line.  


### Suggestions and Requests

If you feel a current feature could be improved, or if you would like a new feature entirely, I'm always open suggestions and requests. 


### Customizations

If you wish to make alternations or additions to the definition, you can use [PackageDev](https://github.com/SublimeText/PackageDev) along with the `kOS.YAML-tmlLanguage` file to create your own version.  An overview of creating syntax definition in Sublime Text can be [found here](http://docs.sublimetext.info/en/latest/extensibility/syntaxdefs.html).