package org.solar.editor.core;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import org.solar.io.FileUtil;
import org.solar.util.SolarConfig;
import org.solar.editor.core.compatible.war3.War3Util;
import org.solar.editor.gui.AppState;

public class BaseConfig extends SolarConfig {
    public static final String AppName = "太阳RPG编辑器V1.00";
    public static final SimpleObjectProperty<GameFileType> gameFileTypeProperty = new SimpleObjectProperty<GameFileType>(GameFileType.war3);
    public static final BooleanBinding isCommonGameFileTypeProperty = gameFileTypeProperty.isEqualTo(GameFileType.common);
    public static final BooleanBinding isWar3GameFileTypeProperty = gameFileTypeProperty.isEqualTo(GameFileType.war3);
    public static final BooleanBinding isDota2GameFileTypeProperty = gameFileTypeProperty.isEqualTo(GameFileType.dota2);
    public static final BooleanBinding isCocosGameFileTypeProperty = gameFileTypeProperty.isEqualTo(GameFileType.cocos);


    public static volatile String filePath_game = filePath_windows_Desktop + "fastwar.w3x";
    public static String filePath_warcraft_directory_root = "D:/请选择魔兽根目录";
    public static String filePath_world_editor_root = "D:/YDWE/";
    public static String filePath_app_data = filePath_user_dir + "data/";
    public static String filePath_plugin = getAppHomePath() + "plugin/";
    public static volatile String 激活码 = "";

    static {
        setAppName("solar_rpg_editor");
        loadFromDB();
        if (!FileUtil.exists(filePath_app_data)) {
            filePath_app_data = filePath_user_dir + "data/";
        }
        if (!FileUtil.exists(filePath_warcraft_directory_root)) {
            filePath_warcraft_directory_root = War3Util.findWar3GameRoot();
            if (filePath_warcraft_directory_root == null || filePath_warcraft_directory_root.length() < 4) {
                filePath_warcraft_directory_root = "D:/请选择魔兽根目录";
            }
        }
        if (!FileUtil.exists(filePath_plugin)) {
            filePath_plugin = getAppHomePath() + "plugin/";
            if (!FileUtil.exists(filePath_plugin)) {
                FileUtil.mkdirs(filePath_plugin);
                FileUtil.copyDirectory(filePath_app_data + "plugin/", filePath_plugin);
            }
        }
        if (!FileUtil.exists(filePath_windows_Desktop)) {
            System.out.println("默认桌面路径不存在:" + filePath_windows_Desktop);
            System.out.println("已自动创建路径:" + filePath_windows_Desktop);
            FileUtil.mkdirs(filePath_windows_Desktop);
        }
        AppState.titleProperty.setValue(AppName + " " + filePath_game);
    }

    public static void loadFromDB() {
        loadFromDB(BaseConfig.class);
        formatPath();
    }

    public static void save2DB() {
        save2DB(BaseConfig.class);
    }


    public static String getAppHomePath() {
        return SolarConfig.getAppHomePath();
    }


    public static void formatPath() {
        filePath_warcraft_directory_root = FileUtil.formatPath(filePath_warcraft_directory_root);
        if (!filePath_warcraft_directory_root.endsWith("/")) {
            filePath_warcraft_directory_root = filePath_warcraft_directory_root + "/";
        }
        filePath_plugin = FileUtil.formatPath(filePath_plugin);
        if (!filePath_plugin.endsWith("/")) {
            filePath_plugin = filePath_plugin + "/";
        }
        filePath_world_editor_root = FileUtil.formatPath(filePath_world_editor_root);
        if (!filePath_world_editor_root.endsWith("/")) {
            filePath_world_editor_root = filePath_world_editor_root + "/";
        }
        filePath_app_data = FileUtil.formatPath(filePath_app_data);
        if (!filePath_app_data.endsWith("/")) {
            filePath_app_data = filePath_app_data + "/";
        }
    }

}
