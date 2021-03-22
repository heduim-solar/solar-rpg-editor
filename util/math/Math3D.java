package org.solar.editor.core.util.math;

import javafx.geometry.Point3D;
import org.joml.Vector3d;
import org.joml.primitives.Intersectiond;
import org.joml.primitives.Planed;
import org.joml.primitives.Rayd;


public class Math3D {

    public static final float FLT_EPSILON = 1.1920928955078125E-7f;


    /**
     * @param p
     * @return true if the ray collides with the given Plane
     */
    public static Vector3d intersectsWherePlane(Rayd ray, Planed p) {
        double ratio = Intersectiond.intersectRayPlane(ray, p, FLT_EPSILON);
        if (ratio < FLT_EPSILON) {
            return null; // intersects behind origin
        }
        Vector3d result = new Vector3d(ray.dX, ray.dY, ray.dZ);
        result.mul(ratio).add(ray.oX, ray.oY, ray.oZ);
        return result;
    }


}
