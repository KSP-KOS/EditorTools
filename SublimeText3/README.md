# Sublime Text 3 - kOS Syntax Highlighting

This syntax definition is a fork of [xeger's Atom package](https://github.com/KSP-KOS/EditorTools/tree/develop/Atom).  

To install the kOS definition as is, place the `kOS.tmlLanguage` file your **user** package directory.  his directory by opening Sublime Text and selecting the following:

> Preferences > Browse Packages

The User folder will be among the listed directories.


### Additional Coverage

The original syntax highlighting package written by xeger was intended for Atom.  While a direct, unedited port does the job, updates are ongoing to bring highlighting in line with sublime defaults.  This isn't a commentary on the original, it was just written for a different editor. Additions primarily include coverage for additional keywords, along with added support for kOS features that were not available when the original Atom package was released.


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


**Updated Syntax Support**

The following item in this section represent highlight support for structures, and their parameters and methods, that were not available at the time of the original packages release.  This list doesn't represent all updates, just entirely new additions.  

  - [Connection](https://ksp-kos.github.io/KOS/structures/communication/connection.html)
  - [kOS Pseudo Action Groups](https://ksp-kos.github.io/KOS/commands/flight/systems.html)
  - [Message](https://ksp-kos.github.io/KOS/structures/communication/message.html)
  - [MessageQueue](https://ksp-kos.github.io/KOS/structures/communication/message_queue.html)
  - [ScienceData](https://ksp-kos.github.io/KOS/structures/vessels/sciencedatavalue.html)
  - [ScienceExperimentModule](https://ksp-kos.github.io/KOS/structures/vessels/scienceexperiment.html)
  - [PIDLoop](https://ksp-kos.github.io/KOS/structures/misc/pidloop.html)
  - [TimeSpan](https://ksp-kos.github.io/KOS/structures/misc/time.html)
    - TIME support added as well

## Examples
> Monokai
![Monokai](https://github.com/jim-hart/EditorTools/blob/develop/SublimeText3/ExampleImages/MonokaiSnippet.png)

> Solarized Dark
![Solarized Dark](https://github.com/jim-hart/EditorTools/blob/develop/SublimeText3/ExampleImages/SolarizedDarkSnippet.png)

> Solarized Light
![Solarized Light](https://github.com/jim-hart/EditorTools/blob/develop/SublimeText3/ExampleImages/SolarizedLightSnippet.png)

> Twilight
![Twilight](https://github.com/jim-hart/EditorTools/blob/develop/SublimeText3/ExampleImages/TwilightSnippet.png)

---


### Suggestions and Requests

If you feel a current feature could be improved, or if you would like a new feature entirely, I'm always open suggestions and requests. 


### Customizations

If you wish to make alternations or additions to the definition, you can use [PackageDev](https://github.com/SublimeText/PackageDev) along with the `kOS.YAML-tmlLanguage` file to create your own version.  An overview of creating syntax definition in Sublime Text can be [found here](http://docs.sublimetext.info/en/latest/extensibility/syntaxdefs.html).