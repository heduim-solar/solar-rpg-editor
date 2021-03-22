package org.solar.editor.core.jme.injfx.transfer.impl;

import com.jme3.texture.FrameBuffer;
import org.solar.editor.core.jme.injfx.JfxPlatform;
import org.solar.editor.core.jme.injfx.processor.FrameTransferSceneProcessor.TransferMode;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The class for transferring a frame from jME to {@link ImageView}.
 *
 * @author JavaSaBr
 */
public class ImageFrameTransfer extends AbstractFrameTransfer<ImageView> {

    @Nullable
    private WritableImage writableImage;

    public ImageFrameTransfer(@NotNull ImageView imageView, @NotNull TransferMode transferMode, int width, int height) {
        this(imageView, transferMode, null, width, height);
    }

    public ImageFrameTransfer(
            @NotNull ImageView imageView,
            @NotNull TransferMode transferMode,
            @Nullable FrameBuffer frameBuffer,
            int width,
            int height
    ) {
        super(imageView, transferMode, frameBuffer, width, height);
        JfxPlatform.runInFxThread(() -> imageView.setImage(writableImage));
    }

    @Override
    protected PixelWriter getPixelWriter(
            @NotNull ImageView destination,
            @NotNull FrameBuffer frameBuffer,
            int width,
            int height
    ) {
        writableImage = new WritableImage(width, height);
        return writableImage.getPixelWriter();
    }
}
