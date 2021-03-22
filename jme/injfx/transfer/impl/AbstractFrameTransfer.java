package org.solar.editor.core.jme.injfx.transfer.impl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;
import com.jme3.util.BufferUtils;
import org.solar.editor.core.jme.injfx.JfxPlatform;
import org.solar.editor.core.jme.injfx.transfer.FrameTransfer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.solar.editor.core.jme.injfx.processor.FrameTransferSceneProcessor.TransferMode;

/**
 * The base implementation of a frame transfer.
 *
 * @param <T> the destination's type.
 * @author JavaSaBr
 */
public abstract class AbstractFrameTransfer<T> implements FrameTransfer {

    protected static final int RUNNING_STATE = 1;
    protected static final int WAITING_STATE = 2;
    protected static final int DISPOSING_STATE = 3;
    protected static final int DISPOSED_STATE = 4;

    /**
     * The Frame state.
     */
    @NotNull
    protected final AtomicInteger frameState;

    /**
     * The Image state.
     */
    @NotNull
    protected final AtomicInteger imageState;

    /**
     * The Frame buffer.
     */
    @NotNull
    protected final FrameBuffer frameBuffer;

    /**
     * The Pixel writer.
     */
    @NotNull
    protected final PixelWriter pixelWriter;

    /**
     * The Frame byte buffer.
     */
    @NotNull
    protected final ByteBuffer frameByteBuffer;

    /**
     * The transfer mode.
     */
    @NotNull
    protected final TransferMode transferMode;

    /**
     * The byte buffer.
     */
    @NotNull
    protected final byte[] byteBuffer;

    /**
     * The image byte buffer.
     */
    @NotNull
    protected final byte[] imageByteBuffer;

    /**
     * The prev image byte buffer.
     */
    @NotNull
    protected final byte[] prevImageByteBuffer;

    /**
     * How many frames need to write else.
     */
    protected int frameCount;

    /**
     * The width.
     */
    private final int width;

    /**
     * The height.
     */
    private final int height;

    public AbstractFrameTransfer(@NotNull T destination, int width, int height, @NotNull TransferMode transferMode) {
        this(destination, transferMode, null, width, height);
    }

    public AbstractFrameTransfer(
            @NotNull T destination,
            @NotNull TransferMode transferMode,
            @Nullable FrameBuffer frameBuffer,
            int width,
            int height
    ) {
        this.transferMode = transferMode;
        this.frameState = new AtomicInteger(WAITING_STATE);
        this.imageState = new AtomicInteger(WAITING_STATE);
        this.width = frameBuffer != null ? frameBuffer.getWidth() : width;
        this.height = frameBuffer != null ? frameBuffer.getHeight() : height;
        this.frameCount = 0;

        if (frameBuffer != null) {
            this.frameBuffer = frameBuffer;
        } else {
            this.frameBuffer = new FrameBuffer(width, height, 1);
            this.frameBuffer.setDepthBuffer(Image.Format.Depth);
            this.frameBuffer.setColorBuffer(Image.Format.RGBA8);
            this.frameBuffer.setSrgb(true);
        }

        frameByteBuffer = BufferUtils.createByteBuffer(getWidth() * getHeight() * 4);
        byteBuffer = new byte[getWidth() * getHeight() * 4];
        prevImageByteBuffer = new byte[getWidth() * getHeight() * 4];
        imageByteBuffer = new byte[getWidth() * getHeight() * 4];
        pixelWriter = getPixelWriter(destination, this.frameBuffer, width, height);
    }

    @Override
    public void initFor(@NotNull Renderer renderer, boolean main) {
        if (main) {
            renderer.setMainFrameBufferOverride(frameBuffer);
        }
    }

    /**
     * Get the pixel writer.
     *
     * @param destination the destination.
     * @param frameBuffer the frame buffer.
     * @param width       the width.
     * @param height      the height.
     * @return the pixel writer.
     */
    protected PixelWriter getPixelWriter(@NotNull T destination, @NotNull FrameBuffer frameBuffer, int width, int height) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void copyFrameBufferToImage(@NotNull RenderManager renderManager) {

        while (!frameState.compareAndSet(WAITING_STATE, RUNNING_STATE)) {
            if (frameState.get() == DISPOSED_STATE) {
                return;
            }
        }

        // Convert screenshot.
        try {

            frameByteBuffer.clear();

            var renderer = renderManager.getRenderer();
            renderer.readFrameBufferWithFormat(frameBuffer, frameByteBuffer, Image.Format.RGBA8);

        } finally {
            if (!frameState.compareAndSet(RUNNING_STATE, WAITING_STATE)) {
                throw new RuntimeException("unknown problem with the frame state");
            }
        }

        synchronized (byteBuffer) {
            frameByteBuffer.get(byteBuffer);

            if (transferMode == TransferMode.ON_CHANGES) {

                final byte[] prevBuffer = getPrevImageByteBuffer();

                if (Arrays.equals(prevBuffer, byteBuffer)) {
                    if (frameCount == 0) return;
                } else {
                    frameCount = 2;
                    System.arraycopy(byteBuffer, 0, prevBuffer, 0, byteBuffer.length);
                }

                frameByteBuffer.position(0);
                frameCount--;
            }
        }

        JfxPlatform.runInFxThread(this::writeFrame);
    }

    /**
     * Write content to image.
     */
    protected void writeFrame() {

        while (!imageState.compareAndSet(WAITING_STATE, RUNNING_STATE)) {
            if (imageState.get() == DISPOSED_STATE) {
                return;
            }
        }

        try {

            var imageByteBuffer = getImageByteBuffer();

            synchronized (byteBuffer) {
                System.arraycopy(byteBuffer, 0, imageByteBuffer, 0, byteBuffer.length);
            }

            for (int i = 0, length = width * height * 4; i < length; i += 4) {
                byte r = imageByteBuffer[i];
                byte g = imageByteBuffer[i + 1];
                byte b = imageByteBuffer[i + 2];
                byte a = imageByteBuffer[i + 3];
                imageByteBuffer[i] = b;
                imageByteBuffer[i + 1] = g;
                imageByteBuffer[i + 2] = r;
                imageByteBuffer[i + 3] = a;
            }

            var pixelFormat = PixelFormat.getByteBgraInstance();

            pixelWriter.setPixels(0, 0, width, height, pixelFormat, imageByteBuffer, 0, width * 4);

        } finally {
            if (!imageState.compareAndSet(RUNNING_STATE, WAITING_STATE)) {
                throw new RuntimeException("unknown problem with the image state");
            }
        }
    }

    /**
     * Get the image byte buffer.
     *
     * @return the image byte buffer.
     */
    protected @NotNull byte[] getImageByteBuffer() {
        return imageByteBuffer;
    }

    /**
     * Get the prev image byte buffer.
     *
     * @return the prev image byte buffer.
     */
    protected @NotNull byte[] getPrevImageByteBuffer() {
        return prevImageByteBuffer;
    }

    @Override
    public void dispose() {
        while (!frameState.compareAndSet(WAITING_STATE, DISPOSING_STATE)) ;
        while (!imageState.compareAndSet(WAITING_STATE, DISPOSING_STATE)) ;
        disposeImpl();
        frameState.compareAndSet(DISPOSING_STATE, DISPOSED_STATE);
        imageState.compareAndSet(DISPOSING_STATE, DISPOSED_STATE);
    }

    /**
     * Dispose.
     */
    protected void disposeImpl() {
        frameBuffer.dispose();
        BufferUtils.destroyDirectBuffer(frameByteBuffer);
    }
}
