# kOS-IDEs
This plugin enables support for kOS syntax highlighting in Notepad++ for Windows.

**Download Notepad++** - http://notepad-plus-plus.org/

*****

###Dark Version:

![Dark Version](https://github.com/space-is-hard/EditorTools/blob/develop/NotepadPlusPlus/preview/preview_KerboScript_dark.png "Dark Version")

###Light Version:

![Light Version](https://github.com/space-is-hard/EditorTools/blob/develop/NotepadPlusPlus/preview/preview_KerboScript_light.png "Light Version")

*****

###INSTALLATION###

* Download the KerboScript_dark.xml or KerboScript_light.xml to a directory of your choice.
* Open N++, then go to the **Language** menu
* Click **Define your language...**
* Click **Import**
* Find the .xml and click **Import**
* Make sure to click **Save As** and name it *KerboScript Dark* or *KerboScript Light* or something similar

It should now show up at the bottom of the **Language** menu when you open N++.

Be sure to make Notepad++ your default editor for .ks files!

*****

**Notes**

* I've been experimenting with way to highlight the end-of-line periods something bright so that you don't miss them, but the **Define Your Language** tool isn't set up to accomodate this, so it ends up screwing up other things (for instance, lines that end with a number, for example: `SET X TO 10.` end up having the blue highlight of the number 10 disabled). For now, you'll just have to rely on your eyeballs to make sure you don't forget a period.