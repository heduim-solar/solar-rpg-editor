package org.solar.editor.core.compatible.war3.model;

import com.jme3.math.Matrix4f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.texture.Texture2D;
import com.jme3.util.BufferUtils;
import de.wc3data.mdx.*;
import org.solar.image.ImageUtil;
import org.solar.io.FileUtil;
import org.solar.lang.StringUtil;
import org.solar.solar3d.plugins.mdx.MdxGeometryUtil;
import org.solar.solar3d.plugins.mdx.MdxMaterialUtil;
import org.solar.solar3d.scene.TextureUtil;
import org.solar.util.PingYinUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.*;

public class MdxModelFileUtil {

    public static File createNewFile(String path) {
        MdxModel mdxModel = new MdxModel();
        File file = new File(path);
        try {
            FileUtil.mkParentDirs(file);
            BlizzardDataOutputStream blizzardDataOutputStream = new BlizzardDataOutputStream(file);
            mdxModel.save(blizzardDataOutputStream);
            blizzardDataOutputStream.flush();
            blizzardDataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @param modelNode
     * @return
     */
    public static boolean getModelGeometryList(List<Geometry> geometryList, Node modelNode) {
        if ("TransformToolNode".equals(modelNode.getName())) {
            return false;
        }
        if ("SelectiveFlag".equals(modelNode.getName())) {
            return false;
        }
        modelNode.getChildren().forEach(spatial -> {
            if (spatial instanceof Geometry) {
                geometryList.add((Geometry) spatial);
            } else if (spatial instanceof Node) {
                getModelGeometryList(geometryList, (Node) spatial);
            }
        });
        return true;
    }

    public static MdxModel node2MdxModel(Node modelNode, File saveFile) {
        MdxModel mdxModel = new MdxModel();
        GeosetChunk geosetChunk = new GeosetChunk();
        mdxModel.geosetChunk = geosetChunk;
        List<Geometry> geometryList = new ArrayList<>();
        getModelGeometryList(geometryList, modelNode);
        List<GeosetChunk.Geoset> geosetList = new ArrayList<>();
        String texPath = "ReplaceableTextures\\TeamColor\\TeamColor01.blp";
        for (Geometry spatial : geometryList) {
            GeosetChunk.Geoset geoset = geometry2MdxModel(mdxModel, spatial);
            geosetList.add(geoset);
            String tempTexPath = spatial.getUserData("texPath");
            if (StringUtil.isNotEmpty(tempTexPath)) {
                texPath = tempTexPath;
            }
        }

        //geoset s
        geosetChunk.geoset = new GeosetChunk.Geoset[geosetList.size()];
        for (int i = 0; i < geosetList.size(); i++) {
            geosetChunk.geoset[i] = geosetList.get(i);
        }
        //
        mdxModel.materialChunk = new MaterialChunk();
        MdxModelGeneratorUtil.createMaterial(mdxModel.materialChunk);

        //Create the Texture
        mdxModel.textureChunk = new TextureChunk();
//        MdxModelGeneratorUtil.createTexture(mdxModel.textureChunk, "environment\\Sky\\LordaeronSummerSky\\LordaeronSummerSky.blp");

        //
        File texPathFile = new File(texPath);
        String newTexPath = texPath;
        if (texPathFile.exists()) {
            newTexPath = FileUtil.removeNameExtension(texPathFile.getName());
            newTexPath = PingYinUtil.chinese2pingyin(newTexPath, "_");
            newTexPath = newTexPath + ".blp";
            BufferedImage texBufferedImage = ImageUtil.read(texPathFile);
            ImageUtil.write(texBufferedImage, saveFile.getParent() + "/" + newTexPath);
        }
        //
        MdxModelGeneratorUtil.createTexture(mdxModel.textureChunk, newTexPath);

        //This step is important so that the model can be displayed correctly
        //Generates the modelChunk, containing:
        // BlendTime, MinimumExtent, MaximumExtent, BoundRadius
        //Make sure that you set the geoset befor using this function
        ModelUtils.generateModelChunk(mdxModel, "jme_node");
        //
        return mdxModel;
    }

    public static short[] getShortArray(ShortBuffer buff) {
        if (buff == null) {
            return null;
        }
        buff.clear();
        short[] inds = new short[buff.limit()];
        for (int x = 0; x < inds.length; x++) {
            inds[x] = buff.get();
        }
        return inds;
    }

    public static GeosetChunk.Geoset geometry2MdxModel(MdxModel mdxModel, Geometry geometry) {
        GeosetChunk.Geoset geoset = mdxModel.geosetChunk.new Geoset();
        Mesh mesh = geometry.getMesh();
        //pos
        VertexBuffer posVertexBuffer = mesh.getBuffer(VertexBuffer.Type.Position);
        FloatBuffer posFloatBuffer = (FloatBuffer) posVertexBuffer.getData();

        Matrix4f worldMatrix = geometry.getWorldMatrix();
        //one
        posFloatBuffer.clear();
        Vector3f tempVector3f = new Vector3f();
        geoset.vertexPositions = new float[posFloatBuffer.limit()];
        for (int i = 0; i < posFloatBuffer.limit() / 3; i++) {
            tempVector3f.set(posFloatBuffer.get(), posFloatBuffer.get(), posFloatBuffer.get());
            worldMatrix.mult(tempVector3f, tempVector3f);
            geoset.vertexPositions[i * 3] = tempVector3f.x;
            geoset.vertexPositions[i * 3 + 1] = tempVector3f.y;
            geoset.vertexPositions[i * 3 + 2] = tempVector3f.z;

        }
//        geoset.vertexPositions = BufferUtils.getFloatArray(posFloatBuffer);
//        geoset.vertexNormals = BufferUtils.getFloatArray(posFloatBuffer);

        //face
        VertexBuffer facesVertexBuffer = mesh.getBuffer(VertexBuffer.Type.Index);
        ShortBuffer facesBufferData = (ShortBuffer) facesVertexBuffer.getData();
        geoset.faces = getShortArray(facesBufferData);
        //face
        VertexBuffer texCoordVertexBuffer = mesh.getBuffer(VertexBuffer.Type.TexCoord);
        if (texCoordVertexBuffer != null) {
//            FloatBuffer texCoordBufferData = (FloatBuffer) texCoordVertexBuffer.getData();
//            float[] texCoords = new float[geoset.faces.length / 3 * 2];
//            texCoordBufferData.clear();
//            for (int x = 0; x < texCoords.length; x++) {
//                texCoords[x] = texCoordBufferData.get();
//            }
            geoset.vertexTexturePositions = BufferUtils.getFloatArray((FloatBuffer) texCoordVertexBuffer.getData());

        } else {
//            geoset.vertexTexturePositions = new float[geoset.vertexPositions.length / 3 * 2];
//            for (int i = 0; i < geoset.vertexPositions.length / 3; i++) {
//                geoset.vertexTexturePositions[i * 2] = (float) Math.random();
//                geoset.vertexTexturePositions[i * 2 + 1] = (float) Math.random();
//            }
        }
        //
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

    public static File saveMdxFile(Node modelNode, File saveFile) {
        MdxModel mdxModel = node2MdxModel(modelNode, saveFile);
        try {
            BlizzardDataOutputStream blizzardDataOutputStream = new BlizzardDataOutputStream(saveFile);
            mdxModel.save(blizzardDataOutputStream);
            blizzardDataOutputStream.flush();
            blizzardDataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveFile;
    }


    public static Map getMdxModelInfo(long fileLength, MdxModel mdxModel) {
        Map info = new LinkedHashMap();
        info.put("模型大小", FileUtil.formatLength2SizeInfo(fileLength));
        int texture_length = 0;
        if (mdxModel.textureChunk != null && mdxModel.textureChunk.texture != null) {
            texture_length = mdxModel.textureChunk.texture.length;
        }
//        int sequence_length = 0;
//        if (mdxModel.sequenceChunk != null && mdxModel.sequenceChunk.sequence != null) {
//            sequence_length = mdxModel.sequenceChunk.sequence.length;
//        }
        int geoset_length = 0;
        int triangle_length = 0;
        if (mdxModel.geosetChunk != null && mdxModel.geosetChunk.geoset != null) {
            geoset_length = mdxModel.geosetChunk.geoset.length;
            for (int i = 0; i < mdxModel.geosetChunk.geoset.length; i++) {
                GeosetChunk.Geoset geoset = mdxModel.geosetChunk.geoset[i];
                triangle_length = triangle_length + (geoset.faces.length / 3);
            }
        }
        info.put("贴图数量", "" + texture_length);
//        info.put("动画数量", "" + sequence_length);
        info.put("几何体数量", "" + geoset_length);
        info.put("三角面数量", "" + triangle_length);
        return info;
    }


}
