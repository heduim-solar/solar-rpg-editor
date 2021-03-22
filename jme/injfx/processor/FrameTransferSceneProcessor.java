package org.solar.editor.core.jme.injfx.processor;

import com.jme3.post.SceneProcessor;
import org.jetbrains.annotations.NotNull;

/**
 * The interface for implementing frame transfer processor.
 *
 * @author JavaSaBr
 */
public interface FrameTransferSceneProcessor extends SceneProcessor {

    enum TransferMode {
        ALWAYS,
        ON_CHANGES
    }

    /**
     * Is main boolean.
     *
     * @return if this processor is main.
     */
    boolean isMain();

    /**
     * Is enabled boolean.
     *
     * @return true if this processor is enabled.
     */
    boolean isEnabled();

    /**
     * Sets enabled.
     *
     * @param enabled true if this processor is enabled.
     */
    void setEnabled(boolean enabled);

    /**
     * Reshape a screen.
     */
    void reshape();

    /**
     * Set the transfer mode.
     *
     * @return the transfer mode.
     */
    @NotNull TransferMode getTransferMode();

    /**
     * Get the transfer mode.
     *
     * @param transferMode the transfer mode.
     */
    void setTransferMode(@NotNull TransferMode transferMode);
}
