package org.solar.editor.core.compatible;

import org.solar.concurrent.ThreadUtil;
import org.solar.editor.core.GameFileType;
import org.solar.editor.core.compatible.war3.MpqUtil;
import org.solar.editor.core.util.SolarLog;
import org.solar.editor.workpane.WorkPaneFileEditorUtil;
import org.solar.io.FileUtil;
import org.solar.editor.core.Config;
import org.solar.editor.core.bean.ObjMap;
import org.solar.editor.core.compatible.war3.War3PreBuild;
import org.solar.editor.core.util.common.AppSystemUtil;

import java.io.File;
import java.util.List;

public class ProjectSaveService {


    /**
     * 保存编辑器 编辑后的文件
     * 将内存数据保存到 游戏文件夹对应的文件中
     * 将缓存的游戏文件夹 打包为目标文件(如w3x)
     */
    public static void buildDistFiles() {
        Config.buildConfig.setDistModel();
        build();
    }

    public static void saveWorkFiles() {
        Config.buildConfig.setSaveModel();
        build();
    }


    private static void build() {
        SolarLog.systemSay("保存中...");
        Thread saveThread = ThreadUtil.start(() -> {
            WorkPaneFileEditorUtil.OpenFileEditorList_save();
            _xlsx2FromOriginalFile();
        });


        if (Config.gameFileTypeProperty.get() == GameFileType.war3) {
            File file = new File(Config.filePath_game);
            if (!file.renameTo(file)) {
                AppSystemUtil.closeWorldeditydwe();
            }
            War3PreBuild.run();
            try {
                saveThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MpqUtil.compressDir2Mpq(Config.filePath_game_project_root,
                    Config.filePath_game
            );
        }

        SolarLog.systemSay("保存完毕!");

    }


    //original
    public static void _xlsx2FromOriginalFile() {
        //
        String rootPath = FileUtil.formatPath(Config.filePath_game_project_root + "/");
        String srcPath = rootPath + "src/";
        List<File> xlsxFileList = FileUtil.getAllFiles(srcPath, "xlsx");
        xlsxFileList.forEach(file -> {
            String fileName = file.getName();
            if (fileName.endsWith("_.xlsx") && !fileName.startsWith("~$")) {
                String originalFileName = fileName.replace("_.xlsx", "");
                String relativePathForRoot = FileUtil.formatPath(file.getParent() + "/").replace(srcPath, "");
                List<ObjMap> objMapList = ObjMapFileUtil.parseObjMapList(file);
                ObjMapFileUtil.writeObjMapList(objMapList, new File(rootPath + relativePathForRoot + "/" + originalFileName));
            }
        });


    }


}
