package org.solar.editor.plugin.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.solar.io.FileUtil;
import org.solar.lang.JsonUtil;
import org.solar.lang.StringUtil;
import org.solar.math.SerialNoGenerater;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class PluginPackageJsonUtil {

    public static Map newDefaultPackageJson() {
        Map map = new LinkedHashMap();
        map.put("id", SerialNoGenerater.getNextNo());
//        map.put("插件按钮颜色", new SolarColor("#409EFF", "#67C23A", "#909399", "#E6A23C",
//                "#F56C6C", "#337AB7", "#007bff", "#6c757d", "#28a745", "#17a2b8", "#ffc107", "#dc3545", "#f8f9fa"));
        map.put("show_in_plugin_panel", true);
//        map.put("show_in_trigger_panel", false);
        map.put("show_in_res_panel", false);
        map.put("show_in_unit_panel", false);
        map.put("show_in_item_panel", false);
        map.put("show_in_ability_panel", false);
        map.put("show_in_upgrade_panel", false);
        map.put("show_in_doodad_panel", false);
        map.put("show_in_destructable_panel", false);
        map.put("show_in_buff_panel", false);
        return map;
    }


    public static void saveToFile(Map packageData, String savePath) {
        String jsonStr = JsonUtil.toJSONString(packageData, SerializerFeature.PrettyFormat);
        FileUtil.writeToFile(jsonStr, savePath);
    }

    //package.json
    public static Map readOrDefault(String jsonPath) {
        Map map = read(jsonPath);
        if (map == null) {
            return newDefaultPackageJson();
        }
        return map;
    }

    public static Map read(String jsonPath) {
        if (StringUtil.isEmpty(jsonPath)) {
            return null;
        }
        File file = new File(jsonPath);
        if (!file.exists()) {
            return null;
        }
        String jsonStr = FileUtil.getStringFromFile(file);
        if (StringUtil.isEmpty(jsonStr)) {
            return null;
        }
//        Map map = JsonUtil.parseObject(jsonStr);
        Map map = JSONObject.parseObject(jsonStr, Feature.OrderedField);
        return map;
    }


}
