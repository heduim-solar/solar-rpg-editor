package org.solar.editor.core.compatible;

import org.solar.io.FileUtil;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpecialFileName {

    public static final Map<String, String> nameRemarkMap = new LinkedHashMap<>();

    static {

        //one case
        nameRemarkMap.put("war3map.w3e", "地形");
        //one case
        nameRemarkMap.put("war3map.w3u", "单位");
        nameRemarkMap.put("war3map.w3t", "物品");
        nameRemarkMap.put("war3map.w3b", "可破坏物");
        nameRemarkMap.put("war3map.w3d", "地形装饰物");
        nameRemarkMap.put("war3map.w3a", "技能");
        nameRemarkMap.put("war3map.w3h", "魔法效果");
        nameRemarkMap.put("war3map.w3q", "科技");


    }


    public static String getRemark(String fileName) {
        if (nameRemarkMap.containsKey(fileName)) {
            return nameRemarkMap.get(fileName);
        }
        if (fileName.contains("_.xlsx")) {
            String fromFileName = fileName.replace("_.xlsx", "");
            if (nameRemarkMap.containsKey(fromFileName)) {
                return nameRemarkMap.get(fromFileName);
            }
            return "";
        }
        fileName = FileUtil.removeNameExtension(fileName);
        if (nameRemarkMap.containsKey(fileName)) {
            return nameRemarkMap.get(fileName);
        }
        return "";
    }

    public static boolean isSpecialFile(String fileName) {

        if (FileUtil.isExtension(fileName, "xls", "xlsx")) {
            return true;
        }
        return false;
//        return nameRemarkMap.containsKey(fileName);
    }

    public static boolean isSpecialFile(File file) {
        return isSpecialFile(file.getName());
    }

    public static boolean isSpecialFile(Object fileObj) {
        if (fileObj instanceof File) {
            return isSpecialFile(((File) fileObj).getName());
        }
        return false;
    }


}
