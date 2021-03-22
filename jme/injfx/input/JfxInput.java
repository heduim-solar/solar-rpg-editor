package org.solar.editor.core.jme.injfx.input;

import com.jme3.input.Input;
import com.jme3.input.RawInputListener;
import org.solar.editor.core.jme.injfx.JmeOffscreenSurfaceContext;
import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solar.editor.core.jme.injfx.ApplicationThreadExecutor;

import static com.ss.rlib.common.util.ObjectUtils.notNull;

/**
 * The base implementation of the {@link Input} for using in the ImageView.
 *
 * @author JavaSaBr
 */
public class JfxInput implements Input {

    protected static final ApplicationThreadExecutor EXECUTOR = ApplicationThreadExecutor.getInstance();

    /**
     * The context.
     */
    @NotNull
    protected final JmeOffscreenSurfaceContext context;

    /**
     * The raw listener.
     */
    @Nullable
    protected RawInputListener listener;

    /**
     * The input node.
     */
    @Nullable
    protected Node node;

    /**
     * The scene.
     */
//    @Nullable
//    protected Scene scene;

    /**
     * The flag of initializing this.
     */
    protected boolean initialized;

    public JfxInput(@NotNull JmeOffscreenSurfaceContext context) {
        this.context = context;
    }

    /**
     * Checks of existing the node.
     *
     * @return true if the node is exist.
     */
    protected boolean hasNode() {
        return node != null;
    }

    /**
     * Gets the bound node.
     *
     * @return the bound node.
     */
    protected @NotNull Node getNode() {
        return notNull(node);
    }

    /**
     * Gets the raw listener.
     *
     * @return the raw listener.
     */
    protected @NotNull RawInputListener getListener() {
        return notNull(listener);
    }

    /**
     * Bind this input to the node.
     *
     * @param node the node.
     */
    public void bind(@NotNull Node node) {
        this.node = node;
//        this.scene = notNull(node.getScene());
    }

    /**
     * Unbind.
     */
    public void unbind() {
        this.node = null;
//        this.scene = null;
    }

    @Override
    public void initialize() {
        if (isInitialized()) return;
        initializeImpl();
        initialized = true;
    }

    /**
     * Initialize.
     */
    protected void initializeImpl() {
    }

    @Override
    public void update() {
        if (!context.isRenderable()) return;
        updateImpl();
    }

    /**
     * Update.
     */
    protected void updateImpl() {
    }

    @Override
    public void destroy() {
        unbind();
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void setInputListener(@NotNull RawInputListener listener) {
        this.listener = listener;
    }

    @Override
    public long getInputTimeNanos() {
        return System.nanoTime();
    }
}
