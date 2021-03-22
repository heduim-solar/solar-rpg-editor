package org.solar.editor.core.jme.injfx;


import javafx.scene.image.ImageView;

/**
 * The resizable image view.
 *
 * @author JavaSaBr
 * @author jayfella
 */
public class EditorFxImageView extends ImageView {
    @Override
    public double minHeight(double width) {
        return 64;
    }

    @Override
    public double maxHeight(double width) {
        return 2000;
    }

    @Override
    public double prefHeight(double width) {
        return minHeight(width);
    }

    @Override
    public double minWidth(double height) {
        return 0;
    }

    @Override
    public double maxWidth(double height) {
        return 10000;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public void resize(double width, double height) {
        if (getFitWidth() != width) {
            super.setFitWidth(width);
        }
        if (getFitHeight() != height) {
            super.setFitHeight(height);
        }
    }
}
