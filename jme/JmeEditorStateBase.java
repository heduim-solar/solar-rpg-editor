package org.solar.editor.core.jme;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.solar.editor.core.jme.common.SolarFlyByCamera;
import org.solar.editor.core.jme.common.SolarFlyCamAppState;
import org.solar.solar3d.state.BaseAppState;

import java.util.ArrayList;
import java.util.List;

public class JmeEditorStateBase extends BaseAppState {
    public Node scene = new Node("scene");
    public Node guiScene = new Node("guiScene");
    public SolarFlyCamAppState solarFlyCamAppState;
    public SolarFlyByCamera solarFlyByCamera;
    public float zoomSpeed = 1f;
    public float moveSpeed = 100f;
    //cam
    public Vector3f camLocation = new Vector3f();
    public Quaternion camRotation = new Quaternion(0.58831465f, -0.5834533f, -0.39425284f, 0.39753342f);
    public List<AppState> stateList = new ArrayList<>();
    public static int id = 0;
    //xx
    public float frustumNear;
    public float frustumFar;
    public float frustumLeft;
    public float frustumRight;
    public float frustumTop;
    public float frustumBottom;

    //code
    {
        id++;
        setId("id_" + id);
        float camL = 50000;
        float camH = 300;
        float aspect = (float) 640 / (float) 480;
        float camW = camH * aspect;
        frustumNear = -camL;
        frustumFar = camL;
        frustumLeft = -camW;
        frustumRight = camW;
        frustumTop = camH;
        frustumBottom = -camH;
    }

    @Override
    public void initialize() {
        app.getRootNode().attachChild(scene);
        app.getGuiNode().attachChild(guiScene);
        cam.setLocation(camLocation);
        cam.setRotation(camRotation);
        //one case
        cam.setFrustum(frustumNear, frustumFar, frustumLeft, frustumRight, frustumTop, frustumBottom);
        solarFlyCamAppState = stateManager.getState(SolarFlyCamAppState.class);
        solarFlyByCamera = solarFlyCamAppState.getCamera();
        solarFlyByCamera.setZoomSpeed(zoomSpeed);
        solarFlyByCamera.setMoveSpeed(moveSpeed);

        //one case
        stateManager.attachAll(stateList);
    }


    @Override
    public void update(float v) {

    }


    @Override
    public void cleanup(Application app) {
        this.app.getRootNode().detachChild(scene);
        this.app.getGuiNode().detachChild(guiScene);
        camLocation.set(cam.getLocation());
        camRotation.set(cam.getRotation());
        frustumNear = cam.getFrustumNear();
        frustumFar = cam.getFrustumFar();
        frustumLeft = cam.getFrustumLeft();
        frustumRight = cam.getFrustumRight();
        frustumTop = cam.getFrustumTop();
        frustumBottom = cam.getFrustumBottom();
        zoomSpeed = solarFlyByCamera.getZoomSpeed();
        moveSpeed = solarFlyByCamera.getMoveSpeed();
        stateList.forEach(appState -> {
            stateManager.detach(appState);
        });
        super.cleanup(app);
    }

}
