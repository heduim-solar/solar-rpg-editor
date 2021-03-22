package org.solar.editor.core.jme;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.system.AppSettings;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import org.solar.editor.core.Config;
import org.solar.editor.core.util.SolarLog;
import org.solar.editor.core.jme.common.SolarFlyCamAppState;
import org.solar.editor.core.jme.injfx.EditorFxImageView;
import org.solar.editor.core.jme.injfx.JmeOffscreenSurfaceContext;
import org.solar.editor.core.jme.injfx.JmeToJfxIntegrator;
import org.solar.editor.core.jme.injfx.SolarJmeToJfxApplication;
import org.solar.editor.core.jme.injfx.input.JfxMouseInput;
import org.solar.editor.core.jme.injfx.processor.FrameTransferSceneProcessor;
import org.solar.editor.core.jme.injfx.processor.ImageViewFrameTransferSceneProcessor;
import org.solar.solar3d.plugins.FileIgnoreCaseLocator;
import org.solar.solar3d.plugins.mdx.MSModel;

import java.util.List;

public class JmeEditorApp extends SolarJmeToJfxApplication {
    public static ImageViewFrameTransferSceneProcessor sceneProcessor = new ImageViewFrameTransferSceneProcessor();

    static {
        //
        MSModel.hwSkinningDesired = false;
    }

    public JmeEditorApp() {

    }

    public static void main(String[] args) {
        new JmeEditorApp().start();
    }

    AmbientLight ambient;
    DirectionalLight directionalLight;

    @Override
    public void preInitApp() {
        super.preInitApp();
//        assetManager.registerLocator(Config.getAppHomePath(), FileIgnoreCaseLocator.class);
        assetManager.registerLocator(Config.filePath_game_project_root, FileIgnoreCaseLocator.class);
        assetManager.registerLocator(Config.filePath_war3_mpq_files, FileIgnoreCaseLocator.class);
//        assetManager.registerLocator(Config.filePath_project_resources, FileIgnoreCaseLocator.class);
    }

    @Override
    public void initApp() {
        cam.setParallelProjection(true);
        float camL = 50000;
        float camH = 300;
        float aspect = (float) cam.getWidth() / cam.getHeight();
        float camW = camH * aspect;
        cam.setFrustum(-camL, camL, -camW, camW, camH, -camH);


        FlyCamAppState flyCamAppState = stateManager.getState(FlyCamAppState.class);
        stateManager.detach(flyCamAppState);

        ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.75f));
        //directionalLight
        directionalLight = new DirectionalLight();
        Vector3f direction = new Vector3f(-0.5f, -0.5f, -0.1f);
        directionalLight.setDirection(direction);
        directionalLight.setColor(new ColorRGBA(1f, 1f, 1f, 1f).mult(0.25f));
        rootNode.addLight(directionalLight);
        rootNode.addLight(ambient);
//        setEd(new DefaultEntityData());

        //one case
//        ScreenshotAppState screenshotAppState = new ScreenshotAppState();
        ScreenshotAppState screenshotAppState = stateManager.getState(ScreenshotAppState.class);
        stateManager.attach(screenshotAppState);
        screenshotAppState.setIsNumbered(false);
        //one case
        stateManager.attach(new SolarFlyCamAppState());
        //
        cam.setRotation(new Quaternion(0.58831465f, -0.5834533f, -0.39425284f, 0.39753342f));
        //
    }


    @Override
    public void update() {
        super.update();
    }

    //one case
    public static JmeEditorApp app = null;
    public static ImageView imageView = null;

    public static void startJme3App() {
        // creates jME application
        AppSettings settings = JmeToJfxIntegrator.prepareSettings(new AppSettings(true), 60);
        app = new JmeEditorApp();
        app.setSettings(settings);
        app.setShowSettings(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                app.start();
            }
        }).start();
    }

    public static void initJme3ImageView(Scene scene) {
        EditorFxImageView imageView = new EditorFxImageView();
        JmeEditorApp.imageView = imageView;
        imageView.getProperties().put(JfxMouseInput.PROP_USE_LOCAL_COORDS, true);
        imageView.setFocusTraversable(true);
        imageView.setOnMouseClicked(event -> imageView.requestFocus());
        app.EXECUTOR.addToExecute(() -> {
            List<ViewPort> vps = app.getRenderManager().getPostViews();
            sceneProcessor.bind(JmeEditorApp.imageView, app, vps.get(vps.size() - 1));
            //
            var context = (JmeOffscreenSurfaceContext) app.getContext();
            context.getMouseInput().bind(JmeEditorApp.imageView);
            context.getKeyInput().bind(JmeEditorApp.imageView, scene);
            SolarLog.systemSay("3D环境初始化完毕！");
        });
        sceneProcessor.setEnabled(true);
        sceneProcessor.setTransferMode(FrameTransferSceneProcessor.TransferMode.ON_CHANGES);
    }


    public static ImageView getImageView() {
        return imageView;
    }


}
