package ksp.kos.ideaplugin.refactoring;

import com.intellij.lang.refactoring.InlineHandler;
import com.intellij.refactoring.RefactoringBundle;

/**
 * Created on 06/03/16.
 *
 * @author ptasha
 */
public class InlineSettings implements InlineHandler.Settings {
    public static final String REFACTORING_NAME = RefactoringBundle.message("inline.title");

    private boolean onlyReference;

    public InlineSettings(boolean onlyReference) {
        this.onlyReference = onlyReference;
    }

    @Override
    public boolean isOnlyOneReferenceToInline() {
        return onlyReference;
    }
}
