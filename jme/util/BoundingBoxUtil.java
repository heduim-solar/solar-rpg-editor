package org.solar.editor.core.jme.util;

import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.util.TempVars;

public class BoundingBoxUtil {


    public static float getMaxLen(BoundingVolume boundingVolume) {
        if (boundingVolume == null) {
            return 0;
        }
        if (boundingVolume instanceof BoundingBox) {
            BoundingBox boundingBox = (BoundingBox) boundingVolume;
            TempVars tempVars = TempVars.get();
            float len = 0;
            Vector3f min = boundingBox.getMin(tempVars.vect1);
            Vector3f max = boundingBox.getMin(tempVars.vect2);
            //one case
//            len = Math.max(len, Math.abs(min.x));
//            len = Math.max(len, Math.abs(min.y));
//            len = Math.max(len, Math.abs(min.z));
//            //one case
//            len = Math.max(len, Math.abs(max.x));
//            len = Math.max(len, Math.abs(max.y));
//            len = Math.max(len, Math.abs(max.z));
            //one case
            len = Math.max(len, boundingBox.getXExtent());
            len = Math.max(len, boundingBox.getYExtent());
            len = Math.max(len, boundingBox.getZExtent());
            tempVars.release();
            return len;
        }
        return 0;
    }


}
