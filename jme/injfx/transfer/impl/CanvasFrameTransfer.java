package org.solar.editor.core.jme.injfx.transfer.impl;

import com.jme3.texture.FrameBuffer;
import org.solar.editor.core.jme.injfx.processor.FrameTransferSceneProcessor.TransferMode;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import org.jetbrains.annotations.NotNull;

/**
 * The class for transferring content from the jME to {@link Canvas}.
 *
 * @author JavaSaBr
 */
public class CanvasFrameTransfer extends AbstractFrameTransfer<Canvas> {

    public CanvasFrameTransfer(  Canvas canvas,  TransferMode transferMode, int width, int height) {
        this(canvas, transferMode, null, width, height);
    }

    public CanvasFrameTransfer(
          Canvas canvas,
             TransferMode transferMode,
            FrameBuffer frameBuffer,
            int width,
            int height
    ) {
        super(canvas, transferMode, frameBuffer, width, height);
    }

    @Override
    protected PixelWriter getPixelWriter(
            @NotNull Canvas destination,
            @NotNull FrameBuffer frameBuffer,
            int width,
            int height
    ) {
        return destination.getGraphicsContext2D().getPixelWriter();
    }
}
