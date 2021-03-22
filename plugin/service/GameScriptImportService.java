package org.solar.editor.plugin.service;

import org.solar.editor.core.bean.GameScript;
import org.solar.editor.plugin.SEPUtil;
import org.solar.io.FileUtil;

import java.io.File;
import java.util.List;
import java.util.Map;


public class GameScriptImportService {

    String pluginDirPath;
    String triggerCategory;
    File pluginDir;
    Map pluginBinding;


    public GameScriptImportService(String pluginDirPath, Map pluginBinding) {
        this.pluginDirPath = pluginDirPath;
        this.pluginBinding = pluginBinding;
        //
        this.pluginDir = new File(pluginDirPath);
        triggerCategory = pluginDir.getName().replace("_sep", "");
    }


    public void importAll() {
        //生成触发
        List<File> lmlFileList = FileUtil.getAllFiles(pluginDirPath, "ts");
        lmlFileList.forEach(file -> {
            String triggerFileName = file.getName();
            GameScript trigger = SEPUtil.gameScript(file, pluginBinding);
            trigger.setCategory(triggerCategory);
        });
    }

}
