" Vim syntax file
" Language:	kerboscript
" Maintainer:	Tom van der Lee <t0m.vd.l33@gmail.com>

if exists("b:current_syntax")
	finish
endif

syn keyword	kerboscriptBoolean	true false
syn keyword	kerboscriptConditional	if else when then on off and or not
syn keyword	kerboscriptConstant	pi e g
syn keyword	kerboscriptFunction	add remove stage clearscreen log copy rename delete edit run compile reboot shutdown batch deploy print
syn keyword	kerboscriptKeyword	set to lock unlock declare parameter toggle return
syn keyword	kerboscriptOperator	abs ceiling floor ln log10 mod min max round sqrt sin cos tan arcsin arccos arctan arctan2
syn keyword	kerboscriptRepeat	for until
syn keyword	kerboscriptStatement	wait break preserve
syn keyword	kerboscriptStorageClass	global local
syn keyword	kerboscriptType		function v vector direction latlng parameter
syn keyword	kerboscriptTodo		contained TODO

syn match	kerboscriptComment	"//.*" contains=kerboscriptTodo
syn match	kerboscriptFloat	"\.\d\+\>"
syn match	kerboscriptFloat	"\<\d\+\.\d*\>"
syn match	kerboscriptNumber	"\<\d\+\>"

syn region	kerboscriptString	start=+"+ end=+"+

hi def link kerboscriptBoolean		Boolean
hi def link kerboscriptComment		Comment
hi def link kerboscriptConditional	Conditional
hi def link kerboscriptConstant		Constant
hi def link kerboscriptFloat		Float
hi def link kerboscriptFunction		Function
hi def link kerboscriptKeyword		Keyword
hi def link kerboscriptNumber		Number
hi def link kerboscriptOperator		Operator
hi def link kerboscriptRepeat		Repeat
hi def link kerboscriptStatement	Statement
hi def link kerboscriptStorageClass	StorageClass
hi def link kerboscriptString		String
hi def link kerboscriptTodo		Todo
hi def link kerboscriptType		Type

let b:current_syntax = "kerboscript"
" vim: set ts=8 sw=8 tw=78 noet :
