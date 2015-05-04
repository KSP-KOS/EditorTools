" Vim syntax file
" Language:	kerboscript
" Maintainer:	Tom van der Lee <t0m.vd.l33@gmail.com>

if exists("b:current_syntax")
	finish
endif

syn keyword	kerboscriptConditional	if else when then on and or not 
syn keyword	kerboscriptRepeat	for until
syn keyword	kerboscriptStatement	wait break preserve
syn keyword 	kerboscriptBoolean 	true false

syn keyword	kerboscriptTodo		contained TODO

syn match	kerboscriptComment	"//.*" contains=kerboscriptTodo

syn region 	kerboscriptString 	start=+"+ end=+"+

hi def link kerboscriptConditional	Conditional
hi def link kerboscriptRepeat		Repeat
hi def link kerboscriptStatement	Statement
hi def link kerboscriptTodo		Todo
hi def link kerboscriptComment		Comment
hi def link kerboscriptString		String
hi def link kerboscriptBoolean 		Boolean

let b:current_syntax = "kerboscript"
" vim: set ts=8 sw=8 tw=78 noet :
