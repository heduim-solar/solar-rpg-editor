package org.solar.editor.core.jme.injfx.transfer;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import org.jetbrains.annotations.NotNull;

/**
 * The class for transferring content from a jME frame buffer to somewhere.
 *
 * @author JavaSaBr
 */
public interface FrameTransfer {

    /**
     * Init this transfer for the render.
     *
     * @param renderer the render.
     * @param main     true if this transfer is main.
     */
    default void initFor(@NotNull Renderer renderer, boolean main) {
    }

    /**
     * Gets the width.
     *
     * @return the width.
     */
    int getWidth();

    /**
     * Gets the height.
     *
     * @return the height.
     */
    int getHeight();

    /**
     * Copy the content from render to the frameByteBuffer and write this content to javaFX.
     *
     * @param renderManager the render manager.
     */
    void copyFrameBufferToImage(@NotNull RenderManager renderManager);

    /**
     * Dispose this transfer.
     */
    void dispose();
}
