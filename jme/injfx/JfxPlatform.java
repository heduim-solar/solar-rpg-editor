package org.solar.editor.core.jme.injfx;

import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

/**
 * The class with additional utility methods for JavaFX Platform.
 *
 * @author JavaSaBr
 */
public class JfxPlatform {

    /**
     * Execute the task in JavaFX thread.
     *
     * @param task the task.
     */
    public static void runInFxThread(@NotNull Runnable task) {
        if (Platform.isFxApplicationThread()) {
            task.run();
        } else {
            Platform.runLater(task);
        }
    }
}
