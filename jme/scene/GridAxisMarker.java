package org.solar.editor.core.jme.scene;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;
import org.solar.editor.core.jme.JmeEditorApp;
import org.solar.solar3d.debug.DebuggerUtil;
import org.solar.solar3d.scene.MaterialUtil;

public class GridAxisMarker extends Node {


    {

        Grid grid = new Grid(7, 7, 100);

        Material mat = MaterialUtil.newUnshadedMaterial(JmeEditorApp.app, ColorRGBA.White);
//        mat.getAdditionalRenderState().setDepthTest(false);
        mat.getAdditionalRenderState().setPolyOffset(-1, -1);
        Geometry geometry = new Geometry("grid", grid);

        geometry.setMaterial(mat);
        geometry.rotate(FastMath.HALF_PI, 0, 0);
        geometry.center();
        this.attachChild(geometry);
        //one case
        Node markerNode = DebuggerUtil.createAxisMarker(JmeEditorApp.app, 360);
        markerNode.setLocalTranslation(0, 0, 1);
        this.attachChild(markerNode);

    }


}
