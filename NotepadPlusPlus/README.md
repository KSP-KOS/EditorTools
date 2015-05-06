# kOS-IDEs
This plugin enables support for kOS syntax highlighting in Notepad++ for Windows.

**Download Notepad++** - http://notepad-plus-plus.org/

**kOS Documentation** - http://ksp-kos.github.io/KOS_DOC/

*****

**Notes**

* Because the recognized keywords get turned black, this will not work on a very dark or black background. If you prefer to use a black background, you can change the color of the recognized text in the Define Your Language tool's keyword tab. Each group of keywords has its own styler that allows you to choose a lighter color.

* This setup is incomplete and you'll probably run into a few keywords that aren't recognized and show up as grey instead of black. If this really bugs you, you can open the *Define Your Language* tool and manually add them in the keyword list tab. If you're feeling uppity, you can either fork it and make the edit yourself or you can also shoot me [an email](mailto:space-is-hard@users.noreply.github.com) or [a PM on reddit](http://www.reddit.com/message/compose/?to=space_is_hard) and I'll throw it in.

* I've been experimenting with way to highlight the end-of-line periods something bright so that you don't miss them, but the *Define Your Language* tool isn't set up to accomodate this, so it ends up screwing up other things (for instance, lines that end with a number `SET X TO 10.` end up having the blue highlight of the number 10 disabled). For now, you'll just have to rely on your eyeballs to make sure you don't forget a period.

*****

###INSTALLATION###

* Download the Kerboscript.xml to a directory of your choice.
* Open N++, then go to the *Language* menu
* Click *Define your language...*
* Click *Import*
* Find the .xml and click *Import*
* Make sure to click *Save As* and name it Kerboscript or something similar

It should now show up at the bottom of the *Language* menu when you open N++.

Be sure to make Notepad++ your default editor for .ks files!