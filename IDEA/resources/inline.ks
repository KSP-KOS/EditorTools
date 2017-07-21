function sqrt_ {
    parameter x.
    return 1/2/sqrt(x).
}

function log_ {
    parameter x.
    return 1/x.
}

function sin_ {
    parameter x.
    return cos(x)*constant:degtorad.
}

function cos_ {
    parameter x.
    return -sin(x)*constant:degtorad.
}

function vang_ { // TODO it should understand it by itself
    return 0.
}