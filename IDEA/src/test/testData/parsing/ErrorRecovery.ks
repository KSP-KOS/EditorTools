// Testing instruction recovery
PRINT "abc"
LOCAL bar TO 0.
lots of gibberish that's definitely not an instruction
LOCAL foo TO 1.
LOCAL baz TO
IF foo {
    PRINT foo.
    PRINT
}
LOCAL baz TO 0.
