package ksp.kos.ideaplugin.actions;

/**
 * Created on 24/01/16.
 *
 * @author ptasha
 */
public class ActionFailedException extends Exception {
    public ActionFailedException(String message) {
        super(message);
    }

    public ActionFailedException(Throwable cause) {
        super(cause);
    }
}
