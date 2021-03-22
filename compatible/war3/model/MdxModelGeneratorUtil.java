package org.solar.editor.core.compatible.war3.model;


import de.wc3data.mdx.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oger-Lord
 */
public class MdxModelGeneratorUtil {


    public static MdxModel createBoxModel(float size, float height) {
        return createBoxModel(size, height, "environment\\Sky\\LordaeronSummerSky\\LordaeronSummerSky.blp");
    }

    public static MdxModel createBoxModel(float size, float height, String texturePath) {
        MdxModel model = new MdxModel();

        //Create the Geoset
        GeosetChunk geosetChunk = new GeosetChunk();
        model.geosetChunk = geosetChunk;
        geosetChunk.geoset = new GeosetChunk.Geoset[1];
        geosetChunk.geoset[0] = createBoxGeoset(model.geosetChunk, size, height);
        //Create the Material
        model.materialChunk = new MaterialChunk();
        createMaterial(model.materialChunk);

        //Create the Texture
        model.textureChunk = new TextureChunk();
        createTexture(model.textureChunk, texturePath);

        //This step is important so that the model can be displayed correctly
        //Generates the modelChunk, containing:
        // BlendTime, MinimumExtent, MaximumExtent, BoundRadius
        //Make sure that you set the geoset befor using this function
        ModelUtils.generateModelChunk(model, "Box");

        return model;
    }

    public static void createMaterial(MaterialChunk materialChunk) {
        MaterialChunk.Material material = materialChunk.new Material();

        material.layerChunk = new LayerChunk();
        LayerChunk.Layer layer = material.layerChunk.new Layer();

        layer.textureId = 0; //id of the texture
        layer.filterMode = 1; //constant color
        layer.alpha = 1; //no Transparency

        layer.materialAlpha = null;
        layer.textureAnimationId = -1; //No Texture Animation

        material.layerChunk.layer = new LayerChunk.Layer[1];
        material.layerChunk.layer[0] = layer;

        materialChunk.material = new MaterialChunk.Material[1];
        materialChunk.material[0] = material;
    }

    public static void createTexture(TextureChunk textureChunk, String texturePath) {
        TextureChunk.Texture texture = textureChunk.new Texture();

//        texture.fileName = "environment\\Sky\\LordaeronSummerSky\\LordaeronSummerSky.blp";
        texture.fileName = texturePath;

        textureChunk.texture = new TextureChunk.Texture[1];
        textureChunk.texture[0] = texture;
    }

    public static GeosetChunk.Geoset createBoxGeoset(GeosetChunk geosetChunk, float size, float height) {
        return createBoxGeoset(geosetChunk, size, height, 0, 0);
    }

    public static GeosetChunk.Geoset createBoxGeoset(GeosetChunk geosetChunk, float size,
                                                     float height,
                                                     float offsetX,
                                                     float offsetY) {
        return createBoxGeoset(geosetChunk, size, height, offsetX, offsetY, 0);
    }

    public static GeosetChunk.Geoset createBoxGeoset(GeosetChunk geosetChunk, float size,
                                                     float height,
                                                     float offsetX,
                                                     float offsetY,
                                                     float offsetZ) {

        GeosetChunk.Geoset geoset = geosetChunk.new Geoset();

        //Create all 8 vertex points of the Box
        size = size / 2;
        geoset.vertexPositions = new float[]{
                -size, -size, 0f,
                size, -size, 0f,
                size, size, 0f,
                -size, size, 0f,
                -size, -size, height,
                size, -size, height,
                size, size, height,
                -size, size, height};
        for (int i = 0; i < geoset.vertexPositions.length - 2; i = i + 3) {
            geoset.vertexPositions[i] = geoset.vertexPositions[i] + offsetX;
            geoset.vertexPositions[i + 1] = geoset.vertexPositions[i + 1] + offsetY;
            geoset.vertexPositions[i + 2] = geoset.vertexPositions[i + 2] + offsetZ;
        }

        //Position of the vertices on the texture
        geoset.vertexTexturePositions = new float[]{
                0f, 0f,
                1f, 0f,
                1f, 1f,
                0f, 1f,
                0f, 0f,
                1f, 0f,
                1f, 1f,
                0f, 1f
        };

        //Create all 6 Sides
        List<Integer> faces = new ArrayList();

        createRectangle(faces, 0, 4, 7, 3); //Front
        createRectangle(faces, 1, 2, 6, 5); //Back

        createRectangle(faces, 4, 5, 6, 7); //Top
        createRectangle(faces, 3, 2, 1, 0); //Bottom

        createRectangle(faces, 0, 1, 5, 4); //Left
        createRectangle(faces, 3, 7, 6, 2); //Right

        geoset.faces = ModelUtils.convert2ShortArrayI(faces);

        //Set MaterialId for texture
        geoset.materialId = 0;

        //Calculate the extents/bounds
        ModelUtils.calculateExtent(geoset);

        //Calculate the normals
        ModelUtils.calculateNormals(geoset);

        //generate face and vertex groups
        ModelUtils.calculateVertexGroup(geoset);

        return geoset;
    }


    private static void createRectangle(List<Integer> faces, int iA, int iB, int iC, int iD) {
        //Add first triangle
        faces.add(iA);
        faces.add(iB);
        faces.add(iD);

        //Add second triangle
        faces.add(iB);
        faces.add(iC);
        faces.add(iD);

    }
}