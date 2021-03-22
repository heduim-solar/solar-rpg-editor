package org.solar.editor.core.jme.injfx;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.post.FilterPostProcessor;
import org.jetbrains.annotations.Nullable;

/**
 * The base implementation of {@link Application} for using in the JavaFX.
 *
 * @author JavaSaBr.
 */
public class BaseJmeToJfxApplication extends SimpleApplication implements JmeToJfxApplication {


    /**
     * The post filter processor.
     */
    @Nullable
    protected FilterPostProcessor postProcessor;
    protected boolean initialized = false;

    public BaseJmeToJfxApplication() {
    }

    @Override
    public void update() {
        EXECUTOR.execute();
        super.update();
    }

    @Override
    public void simpleInitApp() {
        postProcessor = new FilterPostProcessor(assetManager);
        postProcessor.initialize(renderManager, viewPort);
        viewPort.addProcessor(postProcessor);
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }


    public void initApp() {

    }

    /**
     * Get the post filter processor.
     *
     * @return the post filter processor.
     */
    protected FilterPostProcessor getPostProcessor() {
        return postProcessor;
    }
}
