GLOBAL imported_global_1 TO 1.

// Do some extra tests on the first one
LOCAL imported_local TO 1.

FUNCTION imported_global_func {
    FUNCTION imported_local_func {}
    GLOBAL imported_inner_global TO 1.
}
