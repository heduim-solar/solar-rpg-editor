package org.solar.editor.core.jme.injfx;

import com.jme3.app.Application;

/**
 * The base implementation of {@link Application} for using in the JavaFX.
 *
 * @author JavaSaBr.
 */
//public class JmeToJfxApplication extends SimpleApplication {
public interface JmeToJfxApplication {

    static final ApplicationThreadExecutor EXECUTOR = ApplicationThreadExecutor.getInstance();


}
