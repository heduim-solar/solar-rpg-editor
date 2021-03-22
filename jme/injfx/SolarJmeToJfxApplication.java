package org.solar.editor.core.jme.injfx;

import com.jme3.app.Application;
import com.jme3.post.FilterPostProcessor;
import org.jetbrains.annotations.Nullable;
import org.solar.solar3d.app.SolarDebugApplication;

/**
 * The base implementation of {@link Application} for using in the JavaFX.
 *
 * @author JavaSaBr.
 */
public class SolarJmeToJfxApplication extends SolarDebugApplication implements JmeToJfxApplication {


    /**
     * The post filter processor.
     */
    @Nullable
    protected FilterPostProcessor postProcessor;

    public SolarJmeToJfxApplication() {


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
//        postProcessor.setNumSamples(4);
        viewPort.addProcessor(postProcessor);
        super.simpleInitApp();
    }



    public void initApp() {

    }

    /**
     * Get the post filter processor.
     *
     * @return the post filter processor.
     */
    public FilterPostProcessor getPostProcessor() {
        return postProcessor;
    }
}
