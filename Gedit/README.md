# Gedit Syntax hilighting
This plugin enables support for kOS syntax highlighting in the Gedit text editor for Linux.
https://wiki.gnome.org/Apps/Gedit
http://ksp-kos.github.io/KOS_DOC/

## Install
To install place kerboscrip.lang in /usr/share/gtksourceview-2.0/laguage-specs or /usr/share/gtksourceview-3.0/language-specs (This requires root) depending on what version of Gedit you have installed.
You can find the Gedit version number by typing `gedit --version` in the terminal or selecting help -> about in the gedit window.

To install without root create folder in your home and install the script in the created folder.
`mkdir -p ~/.local/share/gtksourceview-3.0/language-specs/`
