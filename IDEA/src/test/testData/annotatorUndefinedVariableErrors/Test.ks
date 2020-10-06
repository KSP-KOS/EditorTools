// SET global_top_level_a TO 1. // TODO - Make lazyglobal work
DECLARE GLOBAL global_top_level_b TO 1.
DECLARE GLOBAL global_top_level_c IS 1.
GLOBAL global_top_level_d TO 1.
GLOBAL global_top_level_e IS 1.

LOCAL local_top_level_a TO 1.
LOCAL local_top_level_b IS 1.

LOCK lock_a TO 1.

RUN Imported1.
// TODO - Handle RUN statements with strings and directories
// RUN "imported_inner/Imported2".
RUN ONCE Imported3.
// RUN ONCE "imported_inner/Imported4".

FUNCTION func_a {
    DECLARE PARAMETER param_should_not_show_up_1.
    PARAMETER param_should_not_show_up_2.

    LOCAL local_should_not_show_up TO 1.
    // Make sure this isn't interpreted as global
    SET local_should_not_show_up TO 1.
    IF TRUE {
        SET local_should_not_show_up TO 1.
    }

    FUNCTION func_default_visibility_should_not_show_up {}
    LOCAL FUNCTION func_local_should_not_show_up {}
    GLOBAL FUNCTION func_global_in_func {}

    LOCK lock_default_visibility_in_func to 1.
    LOCAL LOCK lock_local_should_not_show_up to 1.
    GLOBAL LOCK lock_global_in_func to 1.

    // SET global_in_func_a TO 1. // TODO - Make lazyglobal work
    DECLARE GLOBAL global_in_func_b TO 1.
    DECLARE GLOBAL global_in_func_c IS 1.
    GLOBAL global_in_func_d TO 1.
    GLOBAL global_in_func_e IS 1.

    RUN Imported5.
}

FUNCTION func_b {
    DECLARE PARAMETER param_a.
    PARAMETER param_b.
    PARAMETER param_c TO { TRUE. }.

    DECLARE local_a TO 1.
    DECLARE local_b IS 1.
    DECLARE LOCAL local_c TO 1.
    DECLARE LOCAL local_d IS 1.
    LOCAL local_e TO 1.
    LOCAL local_f IS 1.

    RUN Imported6.

    FROM { LOCAL from_var IS 1. PRINT <error descr="Unknown identifier `step_var`">step_var</error>. }
    UNTIL from_var = <error descr="Unknown identifier `step_var`">step_var</error>
    STEP { SET from_var TO <error descr="Unknown identifier `step_var`">step_var</error> - 1. LOCAL step_var TO from_var. }
    DO {
        PRINT from_var.
        PRINT step_var.
    }
    PRINT <error descr="Unknown identifier `from_var`">from_var</error>.
    PRINT <error descr="Unknown identifier `step_var`">step_var</error>.

    FOR for_var IN <error descr="Unknown identifier `fake_list`">fake_list</error> {
        PRINT for_var.
    }
    PRINT <error descr="Unknown identifier `for_var`">for_var</error>.

    IF TRUE {
        // Should all be okay
        // PRINT global_top_level_a. // TODO - Make lazyglobal work
        PRINT global_top_level_b.
        PRINT global_top_level_c.
        PRINT global_top_level_d.
        PRINT global_top_level_e.
        PRINT local_top_level_a.
        PRINT local_top_level_b.
        PRINT lock_a.
        PRINT imported_global_1.
        PRINT imported_global_3.
        PRINT imported_global_5.
        PRINT imported_global_6.
        PRINT imported_global_func.
        PRINT imported_inner_global.
        PRINT func_a.
        PRINT func_a().
        PRINT func_a@.
        PRINT func_b.
        PRINT func_b().
        PRINT func_b@.
        // PRINT global_in_func_a. // TODO - Make lazyglobal work
        PRINT global_in_func_b.
        PRINT global_in_func_c.
        PRINT global_in_func_d.
        PRINT global_in_func_e.
        PRINT func_global_in_func.
        PRINT func_global_in_func().
        PRINT func_global_in_func@.
        PRINT lock_default_visibility_in_func.
        PRINT lock_global_in_func.
        PRINT param_a.
        PRINT param_b.
        PRINT param_c.
        PRINT param_c().
        PRINT param_c@.
        PRINT local_a.
        PRINT local_b.
        PRINT local_c.
        PRINT local_d.
        PRINT local_e.
        PRINT local_f.
        PRINT global_after_caret.
        PRINT local_after_function.
        PRINT global_func_after.
        // Some builtin globals
        PRINT TIME.
        PRINT SHIP.
        // Should not be okay
        PRINT <error descr="Unknown identifier `imported_local`">imported_local</error>.
        PRINT <error descr="Unknown identifier `imported_local_func`">imported_local_func</error>.
        // TODO - Handle RUN statements with strings and directories
        PRINT <error descr="Unknown identifier `Imported2`">Imported2</error>.
        PRINT <error descr="Unknown identifier `Imported4`">Imported4</error>.
        PRINT <error descr="Unknown identifier `param_should_not_show_up_1`">param_should_not_show_up_1</error>.
        PRINT <error descr="Unknown identifier `param_should_not_show_up_2`">param_should_not_show_up_2</error>.
        PRINT <error descr="Unknown identifier `local_should_not_show_up`">local_should_not_show_up</error>.
        PRINT <error descr="Unknown identifier `func_default_visibility_should_not_show_up`">func_default_visibility_should_not_show_up</error>.
        PRINT <error descr="Unknown identifier `func_default_visibility_should_not_show_up`">func_default_visibility_should_not_show_up</error>().
        PRINT <error descr="Unknown identifier `func_default_visibility_should_not_show_up`">func_default_visibility_should_not_show_up</error>@.
        PRINT <error descr="Unknown identifier `func_local_should_not_show_up`">func_local_should_not_show_up</error>.
        PRINT <error descr="Unknown identifier `func_local_should_not_show_up`">func_local_should_not_show_up</error>().
        PRINT <error descr="Unknown identifier `func_local_should_not_show_up`">func_local_should_not_show_up</error>@.
        PRINT <error descr="Unknown identifier `lock_local_should_not_show_up`">lock_local_should_not_show_up</error>.
        PRINT <error descr="Unknown identifier `local_after_caret_should_not_show_up`">local_after_caret_should_not_show_up</error>.
        PRINT <error descr="Unknown identifier `local_after_block_should_not_show_up`">local_after_block_should_not_show_up</error>.
        // Complete garbage names
        PRINT <error descr="Unknown identifier `foobar`">foobar</error>.
        PRINT <error descr="Unknown identifier `NEXTNODEE`">NEXTNODEE</error>.


        LOCAL local_after_caret_should_not_show_up IS 1.
        GLOBAL global_after_caret IS 1.
    }

    LOCAL local_after_block_should_not_show_up IS 1.
}

LOCAL local_after_function IS 1.

FUNCTION global_func_after {}
