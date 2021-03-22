package org.solar.editor.core.compatible;

import org.solar.io.FileUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class ContentTypeMap {
    public static final String Type_unit = "unit";
    public static final String Type_item = "item";
    public static final String Type_ability = "ability";
    public static final String Type_upgrade = "upgrade";
    public static final String Type_doodad = "doodad";
    public static final String Type_destructable = "destructable";
    public static final String Type_buff = "buff";
    /**
     * //one case
     * nameRemarkMap.put("war3map.w3e", "地形");
     * //one case
     * nameRemarkMap.put("war3map.w3u", "单位");
     * nameRemarkMap.put("war3map.w3t", "物品");
     * nameRemarkMap.put("war3map.w3b", "可破坏物");
     * nameRemarkMap.put("war3map.w3d", "地形装饰物");
     * nameRemarkMap.put("war3map.w3a", "技能");
     * nameRemarkMap.put("war3map.w3h", "魔法效果");
     * nameRemarkMap.put("war3map.w3q", "科技");
     */
    public static final Map<String, String> data = new LinkedHashMap();

    static {
        data.put("w3u", Type_unit);
        data.put("w3t", Type_item);
        data.put("w3b", Type_destructable);
        data.put("w3d", Type_doodad);
        data.put("w3a", Type_ability);
        data.put("w3h", Type_buff);
        data.put("w3q", Type_upgrade);
    }


    public static String getContentType(String type) {
        type = type.toLowerCase();
        if (data.containsKey(type)) {
            return data.get(type);
        }
        if (type.contains("_.xlsx")) {
            type = type.replace("_.xlsx", "");
        }
        type = FileUtil.getNameExtension(type);
        if (data.containsKey(type)) {
            return data.get(type);
        }
        return type;
    }


}
