package org.solar.editor.core.jme.injfx.processor;

import com.jme3.app.SimpleApplication;
import com.jme3.post.SceneProcessor;
import com.jme3.texture.FrameBuffer;
import org.solar.editor.core.jme.injfx.transfer.FrameTransfer;
import org.solar.editor.core.jme.injfx.transfer.impl.CanvasFrameTransfer;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of the {@link SceneProcessor} for transferring content between jME and Canvas.
 *
 * @author JavaSaBr
 */
public class CanvasFrameTransferSceneProcessor extends AbstractFrameTransferSceneProcessor<Canvas> {

    @Override
    protected int getDestinationWidth() {
        return (int) getDestination().getWidth();
    }

    @Override
    protected int getDestinationHeight() {
        return (int) getDestination().getHeight();
    }

    @Override
    protected boolean isPreserveRatio() {
        return false;
    }

    @Override
    protected void bindDestination(
            @NotNull SimpleApplication application,
            @NotNull Canvas destination,
            @NotNull Node inputNode
    ) {
        super.bindDestination(application, destination, inputNode);
        destination.setScaleY(-1.0);
    }

    @Override
    protected void bindListeners() {
        var destination = getDestination();
        destination.widthProperty().addListener(widthListener);
        destination.heightProperty().addListener(heightListener);
        super.bindListeners();
    }

    @Override
    protected void unbindListeners() {
        var destination = getDestination();
        destination.widthProperty().removeListener(widthListener);
        destination.heightProperty().removeListener(heightListener);
        super.unbindListeners();
    }

    @Override
    protected @NotNull FrameTransfer createFrameTransfer( FrameBuffer frameBuffer, int width, int height) {
        return new CanvasFrameTransfer(getDestination(), getTransferMode(), isMain() ? null : frameBuffer, width, height);
    }
}