package org.solar.editor.plugin;

import groovy.lang.Script;

import org.solar.editor.core.bean.GameScript;
import org.solar.editor.plugin.service.GameScriptImportService;
import org.solar.io.FileUtil;
import org.solar.war3.util.Wc3ObjectMapUtil;

import java.io.File;
import java.util.Map;

public class SEPUtil {

    public static GameScript gameScript(File file, Object dataModel) {
        GameScript gameScript = new GameScript(file, dataModel);
//        GlobalGameVar.gameScriptList.add(gameScript);
        return gameScript;
    }

    /**
     * common fun
     */
    public static String[] getIdArray(String idsStr) {
        return Wc3ObjectMapUtil.getIdArray(idsStr);
    }

    public static void importPluginAllGameScript(Script pluginScript) {
        Map pluginBinding = pluginScript.getBinding().getVariables();
        Map swep = (Map) pluginBinding.get("sep");
        String pluginDirPath = (String) swep.get("dir");
        new GameScriptImportService(pluginDirPath, pluginBinding).importAll();

    }

    public static GameScript importGameScriptIfNotExist(Script pluginScript, String fileName) {
        String triggerName = FileUtil.removeNameExtension(fileName);
//        for (GameScript trigger : GlobalGameVar.gameScriptList) {
//            if (triggerName.equals(trigger.getFileName())) {
//                return null;
//            }
//        }
        Map pluginBinding = pluginScript.getBinding().getVariables();
        Map swep = (Map) pluginBinding.get("sep");
        String pluginDirPath = (String) swep.get("dir");
        //执行导入触发
        return gameScript(new File(pluginDirPath + fileName), pluginBinding);

    }


}
