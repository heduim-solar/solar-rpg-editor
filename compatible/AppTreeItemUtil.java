package org.solar.editor.core.compatible;

import javafx.scene.control.TreeItem;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.solar.io.FileUtil;
import org.solar.jfx.util.IconUtil;
import org.solar.lang.ListUtil;
import org.solar.editor.core.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppTreeItemUtil {


    public static List<TreeItem> getTreeItemByDirAndGameType(File dirFile) {
        switch (Config.gameFileTypeProperty.get()) {
            case common:
                break;
            case war3:
                return getTreeItemByDirAndGameType_War3(dirFile);
            case dota2:
                break;
        }
        return null;
    }

    public static List<TreeItem> getTreeItemByDirAndGameType_War3(File dirFile) {
        List<TreeItem> resultList = new ArrayList<>();
        List treeItemList = getTreeItemByDir(dirFile);
        if (treeItemList == null) {
            return resultList;
        }

        //Source src
        TreeItem mdxTreeItem = new TreeItem();
        mdxTreeItem.setGraphic(IconUtil.getIcon(FontAwesome.CONNECTDEVELOP, 14, "#7DB339"));
        mdxTreeItem.setValue("mdx");
        TreeItem blpTreeItem = new TreeItem();
        blpTreeItem.setGraphic(IconUtil.getIcon(FontAwesome.FLICKR, 14, "#7DB339"));
        blpTreeItem.setValue("blp");

        Iterator<TreeItem> iterator = treeItemList.iterator();
        while (iterator.hasNext()) {
            TreeItem temp = iterator.next();
            Object obj = temp.getValue();
            if (isSrcDir(obj)) {
                temp.setExpanded(true);
                temp.setGraphic(IconUtil.getIcon(FontAwesome.FOLDER, 14, "#3E86A0"));
                iterator.remove();
                resultList.add(temp);
            } else if (obj instanceof File) {
                File file = (File) obj;
                if (FileUtil.isExtension(file.getName(), "mdx")) {
                    iterator.remove();
                    mdxTreeItem.getChildren().add(temp);
                } else if (FileUtil.isExtension(file.getName(), "blp")) {
                    iterator.remove();
                    blpTreeItem.getChildren().add(temp);
                }
            }
        }
        TreeItem resourcesTreeItem = new TreeItem();
        resourcesTreeItem.setValue("resources");
        resourcesTreeItem.setGraphic(IconUtil.getIcon(FontAwesome.FOLDER, 14, "#7E8E99"));
        resourcesTreeItem.getChildren().addAll(treeItemList);

        if (mdxTreeItem.getChildren().size() > 0) {
            resultList.add(mdxTreeItem);
        }
        if (blpTreeItem.getChildren().size() > 0) {
            resultList.add(blpTreeItem);
        }

        resultList.add(resourcesTreeItem);

        return resultList;
    }

    public static boolean isSrcDir(Object obj) {
        if ("src".equals(obj)) {
            return true;
        }
        if (obj instanceof File) {
            return "src".equals(((File) obj).getName());
        }
        return false;
    }


    public static List<TreeItem<File>> getTreeItemByDir(File dirFile) {
        if (!dirFile.exists()) {
            return null;
        }
        File[] files = dirFile.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        List<TreeItem<File>> resultList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            TreeItem<File> treeItem = new TreeItem<File>();
            treeItem.setValue(file);
            if (file.isDirectory()) {
                List<TreeItem<File>> childrenList = getTreeItemByDir(file);
                if (ListUtil.isNotEmpty(childrenList)) {
                    treeItem.getChildren().addAll(childrenList);
                }
            }
            resultList.add(treeItem);
        }
        return resultList;
    }


}
