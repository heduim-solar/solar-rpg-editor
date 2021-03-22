package org.solar.editor.core.compatible;

import de.wc3data.mdx.BlizzardDataInputStream;
import de.wc3data.mdx.MdxModel;
import org.solar.cache.Cache;
import org.solar.cache.CacheImpl;
import org.solar.editor.core.jme.JmeEditorApp;
import org.solar.solar3d.app.SolarApplication;
import org.solar.solar3d.plugins.mdx.MSModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileLoder {

    public static SolarApplication app = JmeEditorApp.app;
    public static Cache cache = new CacheImpl();


    public static MSModel loadMdxModel(File workFile) {
        return cache.get("loadMdxModel:" + workFile.getAbsolutePath() + ":" + workFile.lastModified(), () -> {
            //test
            MdxModel mdxModel = new MdxModel();
            try {
                mdxModel.load(new BlizzardDataInputStream(new FileInputStream(workFile.getAbsolutePath())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String mdxModePath = workFile.getParent() + "/";
            MSModel msModel = new MSModel(app, mdxModel, mdxModePath);
            return msModel;
        });
    }

}
