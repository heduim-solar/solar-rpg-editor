package org.solar.editor.plugin.util;

import javafx.scene.image.WritableImage;
import org.solar.bean.Tree;
import org.solar.concurrent.ThreadUtil;
import org.solar.editor.core.Config;
import org.solar.editor.core.bean.SolarPlugin;
import org.solar.image.ImageUtil;
import org.solar.io.FileUtil;
import org.solar.jfx.util.FxUtil;
import org.solar.lang.PropertiesUtil;
import org.solar.system.DynamicLoadJar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLClassLoader;
import java.util.*;

public class SolarPluginUtil {
    public static final Tree solarPluginTree = new Tree(new ArrayList<>());
    public static final Tree solarPluginTree_plugin_panel = new Tree(new ArrayList<>());
    public static final Tree solarPluginTree_trigger_panel = new Tree(new ArrayList<>());
    public static final Tree solarPluginTree_res_panel = new Tree(new ArrayList<>());
    public static final Tree solarPluginTree_unit_panel = new Tree(new ArrayList<>());
    public static final Tree solarPluginTree_item_panel = new Tree(new ArrayList<>());
    public static final Tree solarPluginTree_ability_panel = new Tree(new ArrayList<>());
    public static final Tree solarPluginTree_upgrade_panel = new Tree(new ArrayList<>());
    public static final Tree solarPluginTree_doodad_panel = new Tree(new ArrayList<>());
    public static final Tree solarPluginTree_destructable_panel = new Tree(new ArrayList<>());
    public static final Tree solarPluginTree_buff_panel = new Tree(new ArrayList<>());
    public static boolean isLoading = false;
    public static boolean isLoad = false;
    public static final URLClassLoader classLoader = DynamicLoadJar.loadJar(Config.filePath_plugin, true);

    public static void load() {
        if (isLoad || isLoading) {
            return;
        }
        isLoading = true;
        ThreadUtil.refreshStartTime();
        add_plugins(solarPluginTree, new File(Config.filePath_plugin));
        removeNoDataTree(solarPluginTree);
        select_plugins_by_show_key(solarPluginTree, solarPluginTree_plugin_panel, "show_in_plugin_panel", true);
        select_plugins_by_show_key(solarPluginTree, solarPluginTree_trigger_panel, "show_in_trigger_panel", false);
        select_plugins_by_show_key(solarPluginTree, solarPluginTree_res_panel, "show_in_res_panel", false);
        select_plugins_by_show_key(solarPluginTree, solarPluginTree_unit_panel, "show_in_unit_panel", false);
        select_plugins_by_show_key(solarPluginTree, solarPluginTree_item_panel, "show_in_item_panel", false);
        select_plugins_by_show_key(solarPluginTree, solarPluginTree_ability_panel, "show_in_ability_panel", false);
        select_plugins_by_show_key(solarPluginTree, solarPluginTree_upgrade_panel, "show_in_upgrade_panel", false);
        select_plugins_by_show_key(solarPluginTree, solarPluginTree_doodad_panel, "show_in_doodad_panel", false);
        select_plugins_by_show_key(solarPluginTree, solarPluginTree_destructable_panel, "show_in_destructable_panel", false);
        select_plugins_by_show_key(solarPluginTree, solarPluginTree_buff_panel, "show_in_buff_panel", false);
        removeNoDataTree(solarPluginTree_plugin_panel);
        removeNoDataTree(solarPluginTree_trigger_panel);
        removeNoDataTree(solarPluginTree_res_panel);
        removeNoDataTree(solarPluginTree_unit_panel);
        removeNoDataTree(solarPluginTree_item_panel);
        removeNoDataTree(solarPluginTree_ability_panel);
        removeNoDataTree(solarPluginTree_upgrade_panel);
        removeNoDataTree(solarPluginTree_doodad_panel);
        removeNoDataTree(solarPluginTree_destructable_panel);
        removeNoDataTree(solarPluginTree_buff_panel);
        isLoading = false;
        isLoad = true;
        ThreadUtil.printUseTimeInfo("插件加载完毕!");
    }


    public static void removeNoDataTree(Tree tree) {
        List<Tree> child = tree.getChild();
        if (child == null || child.size() == 0) {
            return;
        }
        Iterator<Tree> iterator = child.iterator();
        while (iterator.hasNext()) {
            Tree tree2 = iterator.next();
            if (tree2.getData() != null) {
                continue;
            }
            List allChildDataList = tree2.getAllChildData();

            if (allChildDataList == null || allChildDataList.size() == 0) {
                iterator.remove();
            } else {
                removeNoDataTree(tree2);
            }
        }


    }


    public static void select_plugins_by_show_key(Tree sourceTree, Tree tree, String show_key, boolean default_value) {
        SolarPlugin solarPlugin = sourceTree.getData();
        if (solarPlugin != null) {
            Map properties = solarPlugin.getPackageData();
            boolean show = default_value;
            if (properties != null && properties.containsKey(show_key)) {
                show = (boolean) properties.get(show_key);
            }
            if (show) {
                tree.setData(solarPlugin);
            }
        }
        tree.setName(sourceTree.getName());
        tree.setCode(sourceTree.getCode());
        List<Tree> child = sourceTree.getChild();
        if (child != null && child.size() > 0) {
            for (Tree sourceTree2 : child) {
                Tree tree2 = new Tree(new ArrayList<>());
                tree.getChild().add(tree2);
                select_plugins_by_show_key(sourceTree2, tree2, show_key, default_value);
            }
        }
    }

    /**
     * add plugin
     */
    public static void add_plugins(Tree tree, File dirFile) {
        if (dirFile == null) {
            return;
        }
        File[] files = dirFile.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (!file.isDirectory()) {
                continue;
            }
            String fileName = file.getName();
            if (file.getName().endsWith("_sep")) {
                fileName = fileName.substring(0, fileName.length() - 4);
                SolarPlugin solarPlugin = new SolarPlugin();
                solarPlugin.setName(fileName);
                String fileDirPath = FileUtil.formatPath(file.getAbsolutePath() + "/");
                solarPlugin.setFileDirPath(fileDirPath);
                String mainScriptCode = FileUtil.getStringFromFile(fileDirPath + fileName + ".groovy");
                solarPlugin.setMainScriptCode(mainScriptCode);
                Map properties = PluginPackageJsonUtil.readOrDefault(fileDirPath + "package.json");
                solarPlugin.setPackageData(properties);
                //icon
                try {
                    if (FileUtil.exists(fileDirPath + "sep.png")) {
                        BufferedImage bufferedImage = ImageUtil.read(fileDirPath + "sep.png");
                        WritableImage writableImage = FxUtil.toFXImage(bufferedImage);
                        solarPlugin.setIconImage(writableImage);
                    } else if (FileUtil.exists(fileDirPath + "sep.jpg")) {
                        BufferedImage bufferedImage = ImageUtil.read(fileDirPath + "sep.jpg");
                        WritableImage writableImage = FxUtil.toFXImage(bufferedImage);
                        solarPlugin.setIconImage(writableImage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Tree
                Tree solarTree = new Tree();
                solarTree.setName(fileName);
                solarTree.setData(solarPlugin);
                tree.getChild().add(solarTree);
                tree.setCode("sep");
            } else {
                Tree solarTree = new Tree(new ArrayList<>());
                solarTree.setName(fileName);
                solarTree.setCode("dir");
                tree.getChild().add(solarTree);
                add_plugins(solarTree, file);
            }
        }
    }
}
