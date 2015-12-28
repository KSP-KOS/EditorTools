package ksp.kos.ideaplugin;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 26/12/15.
 *
 * @author ptasha
 */
public class KerboScriptFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(KerboScriptFileType.INSTANCE, "ks");
    }
}
