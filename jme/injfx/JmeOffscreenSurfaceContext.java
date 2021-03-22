package org.solar.editor.core.jme.injfx;

import com.jme3.input.JoyInput;
import com.jme3.input.TouchInput;
import com.jme3.opencl.Context;
import com.jme3.renderer.Renderer;
import com.jme3.system.*;
import com.ss.rlib.common.util.ObjectUtils;
import org.solar.editor.core.jme.injfx.input.JfxKeyInput;
import org.solar.editor.core.jme.injfx.input.JfxMouseInput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The implementation of the {@link JmeContext} for integrating to JavaFX.
 *
 * @author empirephoenix
 */
public class JmeOffscreenSurfaceContext implements JmeContext {

    /**
     * The settings.
     */
    @NotNull
    protected final AppSettings settings;

    /**
     * The key input.
     */
    @NotNull
    protected final JfxKeyInput keyInput;

    /**
     * The mouse input.
     */
    @NotNull
    protected final JfxMouseInput mouseInput;

    /**
     * The current width.
     */
    private volatile int width;

    /**
     * The current height.
     */
    private volatile int height;

    /**
     * The background context.
     */
    @Nullable
    protected JmeContext backgroundContext;

    public JmeOffscreenSurfaceContext() {
        this.keyInput = new JfxKeyInput(this);
        this.mouseInput = new JfxMouseInput(this);
        this.settings = createSettings();
        this.backgroundContext = createBackgroundContext();
        this.height = 1;
        this.width = 1;
    }

    /**
     * Gets the current height.
     *
     * @return the current height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the current height.
     *
     * @param height the current height.
     */
    public void setHeight(final int height) {
        this.height = height;
    }

    /**
     * Gets the current width.
     *
     * @return the current width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the current width.
     *
     * @param width the current width.
     */
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * Creates a new app settings.
     *
     * @return the new app settings.
     */
    protected @NotNull AppSettings createSettings() {
        var settings = new AppSettings(true);
        settings.setRenderer(AppSettings.LWJGL_OPENGL3);
        return settings;
    }

    /**
     * Creates a new background jme context.
     *
     * @return the new background jme context.
     */
    protected @NotNull JmeContext createBackgroundContext() {
        return JmeSystem.newContext(settings, Type.OffscreenSurface);
    }

    /**
     * Gets the current background context.
     *
     * @return the current background context.
     */
    protected @Nullable JmeContext getBackgroundContext() {
        return backgroundContext;
    }

    @Override
    public @NotNull Type getType() {
        return Type.OffscreenSurface;
    }

    @Override
    public void setSettings(@NotNull AppSettings settings) {
        this.settings.copyFrom(settings);
        this.settings.setRenderer(AppSettings.LWJGL_OPENGL3);

        ObjectUtils.notNull(getBackgroundContext())
                .setSettings(settings);
    }

    @Override
    public void setSystemListener(@NotNull SystemListener listener) {
        ObjectUtils.notNull(getBackgroundContext())
                .setSystemListener(listener);
    }

    @Override
    public @NotNull AppSettings getSettings() {
        return settings;
    }

    @Override
    public @NotNull Renderer getRenderer() {
        return ObjectUtils.notNull(getBackgroundContext())
                .getRenderer();
    }

    @Override
    public @Nullable Context getOpenCLContext() {
        return null;
    }

    @Override
    public @NotNull JfxMouseInput getMouseInput() {
        return mouseInput;
    }

    @Override
    public @NotNull JfxKeyInput getKeyInput() {
        return keyInput;
    }

    @Override
    public @Nullable JoyInput getJoyInput() {
        return null;
    }

    @Override
    public @Nullable TouchInput getTouchInput() {
        return null;
    }

    @Override
    public @NotNull Timer getTimer() {
        return ObjectUtils.notNull(getBackgroundContext())
                .getTimer();
    }

    @Override
    public void setTitle(@NotNull String title) {
    }

    @Override
    public boolean isCreated() {
        return backgroundContext != null && backgroundContext.isCreated();
    }

    @Override
    public boolean isRenderable() {
        return backgroundContext != null && backgroundContext.isRenderable();
    }

    @Override
    public void setAutoFlushFrames(boolean enabled) {
        // TODO Auto-generated method stub
    }

    @Override
    public void create(boolean waitFor) {
        var render = System.getProperty("jfx.background.render", AppSettings.LWJGL_OPENGL3);
        var backgroundContext = ObjectUtils.notNull(getBackgroundContext());
        backgroundContext.getSettings().setRenderer(render);
        backgroundContext.create(waitFor);
    }

    @Override
    public void restart() {
    }

    @Override
    public void destroy(boolean waitFor) {

        if (backgroundContext == null) {
            throw new IllegalStateException("Not created");
        }

        // destroy wrapped context
        backgroundContext.destroy(waitFor);
    }
}