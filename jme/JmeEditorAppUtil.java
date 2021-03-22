package org.solar.editor.core.jme;

import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.shadow.DirectionalLightShadowFilter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class JmeEditorAppUtil {

    public static JmeEditorStateBase lastJmeEditorState = null;
    public static SSAOFilter ssaoFilter = new SSAOFilter();
    public static BooleanProperty ssaoFilterBooleanProperty = new SimpleBooleanProperty();
    //
    static final int SHADOWMAP_SIZE = 1024;
    public static DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(JmeEditorApp.app.getAssetManager(), SHADOWMAP_SIZE, 3);

    static {
        dlsf.setLight(JmeEditorApp.app.directionalLight);
        dlsf.setEnabled(true);
        ssaoFilterBooleanProperty.addListener((observable, oldValue, newValue) -> {
            switchSSAOFilter(newValue);
        });
    }

    public static void changeEditorState(JmeEditorStateBase newJmeEditorState) {
        enqueue(() -> {
            if (lastJmeEditorState != null) {
                JmeEditorApp.app.getStateManager().detach(lastJmeEditorState);
            }
            JmeEditorApp.app.getStateManager().attach(newJmeEditorState);
            lastJmeEditorState = newJmeEditorState;
        });
    }

    public static void enqueue(Runnable runnable) {
        JmeEditorApp.app.enqueue(runnable);
    }

    public static void switchSSAOFilter(boolean isOpen) {
        enqueue(() -> {
            FilterPostProcessor fpp = JmeEditorApp.app.getPostProcessor();
            if (isOpen) {
                if (fpp.getFilter(SSAOFilter.class) == null) {
                    fpp.addFilter(ssaoFilter);
                }
                if (fpp.getFilter(DirectionalLightShadowFilter.class) == null) {
                    fpp.addFilter(dlsf);
                }
            } else {
                fpp.removeFilter(ssaoFilter);
                fpp.removeFilter(dlsf);
            }
        });


    }


}
