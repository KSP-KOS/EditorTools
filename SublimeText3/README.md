# Sublime Text 3 - kOS Syntax Highlighting

This syntax definition is a fork of [xeger's Atom package](https://github.com/KSP-KOS/EditorTools/tree/develop/Atom).  This is not a direct port, but an updated syntax definition to reflect changes since the original release of the Atom package two years ago.

In addition to general updates, various features have been added to bring syntax highlighting in line with Sublime defaults.  xeger provided an excellent foundation that made adding new updates and functionality much easier, and my thanks goes out to him for sharing his work.  

## Installation

Download the `kOS.tmlLanguage` and `kOS.tmlPreferences` files, and place them in the your `User` package directory, which can be found by opening Sublime Text and selecting the following:

> Preferences > Browse Packages > User

If you just want the path information, you can find the default Packages directory for Windows, Linux, and OSX [here](https://www.sublimetext.com/docs/3/revert.html)

Those looking to modify the syntax definition to suit their preferences can find more information in the [Customization](https://github.com/KSP-KOS/EditorTools/tree/develop/SublimeText3#customizations) section.

## Supported Features

A summary of features and updates to the kOS syntax for ST3 are highlighted here. Check the commit logs for additional details. 

**Changes Include**:

  - Delimiter Contrast
    - Certain punctuation/delimiters were stripped of highlighting to improve readability and provide contrast where needed.  For example: colons, when used to access structure fields, are no longer highlighted with the attached member, which helps distinguish breaks in long `STRUCTURE:MEMBER` chains.

  - User-Defined and General Function Support
    - User function definitions, and calls to those functions, are highlighted like function calls and definitions in C and Python.  Delegate/function references are also now supported; the delegate identifier `@`, along with delegate's name, receive different highlighting. 

  - Contextual Distinction 
    - Keywords usable in multiple contexts are highlighted based on that context. For example, `SHIP:HEADING` vs `HEADING(90,90)`.  Highlighting applied to `HEADING` differs based on whether its used as a structure member, or as a traditional function call.  The same is true for parameters that can be used as structures.

  - Extended Action Groups
    - Action Groups 0-250 are now supported; anything outside that range won't be highlighted.

  - Comment Toggling
    - The keyboard shortcut for toggling comments (Default: `CTRL + /`) now works with all `.ks` files.  


**Upcoming/Ongoing Changes Include**:

  - Additional Keyword Coverage 
    - Check commit logs for all updates
  - Partial STRUCTURE:MEMBER Validation 
    - Highlighting for some STRUCTURE:MEMBER combinations break if an incorrect parameter is applied. This is only implemented for structures that can't be set to another reference, else some parameters would never receive highlighting if their parent structure is being referenced by a variable.
  - Anonymous function distinction.
  - Parameter Declarations
    - Lines containing single parameter definitions is currently supported.  Instances where multiple parameters are declared on the **same line** are not supported, although development of this feature is in progress.  


**Updated Syntax Support**

The following items in this section represent support for structures, parameters, and methods that were not available when the original Atom package was released.  This list doesn't represent all updates, just entirely new structure additions.  

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
![Monokai](https://github.com/KSP-KOS/EditorTools/blob/develop/SublimeText3/ExampleImages/MonokaiSnippet.png)

> Solarized Dark
![Solarized Dark](https://github.com/KSP-KOS/EditorTools/blob/develop/SublimeText3/ExampleImages/SolarizedDarkSnippet.png)

> Solarized Light
![Solarized Light](https://github.com/KSP-KOS/EditorTools/blob/develop/SublimeText3/ExampleImages/SolarizedLightSnippet.png)

> Twilight
![Twilight](https://github.com/KSP-KOS/EditorTools/blob/develop/SublimeText3/ExampleImages/TwilightSnippet.png)

---

### Customization

If you wish to make alternations or additions to the definition, you can use [PackageDev](https://github.com/SublimeText/PackageDev) along with the `kOS.YAML-tmlLanguage` file in the [src directory](https://github.com/KSP-KOS/EditorTools/blob/develop/SublimeText3/src) to create your own version. While you can edit the `.tmlLanguage` file directly and skip the PackageDev route, the `.YAML-tmlLanguage` file is more readable, and easier to manipulate.

An overview of creating syntax definitions in Sublime Text can be [found here](http://docs.sublimetext.info/en/latest/extensibility/syntaxdefs.html).

### Suggestions and Requests

If you feel a current feature could be improved, or if you would like a new feature entirely, I'm always open suggestions and requests. 
