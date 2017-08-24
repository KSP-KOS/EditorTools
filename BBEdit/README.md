# BBEdit Syntax Highlighting

This Codeless Language Module for BBEdit provides basic syntax highlighting for KOS:

 - commands & built in functions show as predefined symbols
 - bound variables show as language keywords
 - strings are highlighted
 - comments are highlighted
 - blocks can be folded (blocks are delimited with braces)
 - function names are added to the function drop down

Suffixes are not covered (so ship:altitude will be coloured because altitude is a bound variable, but ship:orbit:longitudeofascendingnode will not be coloured).

## Pre-Release

**This CLM is currently a pre-release**. It should work just fine, but be prepared to find some things don't quite work like you want. Please contact the maintainer regarding any issues.

Note that BBEdit CLMs can not do context-aware colouring such as recognising suffixes. Only reserved words are highlighted — both function and variable names.

## Installation

For a normal BBEdit install:

 - copy KOS.plist to: `~/Library/Application Support/BBEdit/Language Modules`
 - restart BBEdit

If you use a modified installation for syncing preferences between machines (e.g. via Dropbox), you'll need to find the Language Modules folder yourself.

## Screenshot

![BBEdit KOS Syntax Highlighting](BBEditKOSSyntaxHighlighting.png "Screenshot showing syntax highlighting, block folding and function list populated")

## Maintainer

Please tag @MaraRinn on any BBEdit CLM issues.
