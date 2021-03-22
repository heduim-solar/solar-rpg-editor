package org.solar.editor.core.compatible.war3;

import org.solar.editor.core.util.SolarLog;
import org.solar.io.FileUtil;
import org.solar.editor.core.Config;
import org.solar.editor.core.util.common.ExeCmdUtil;

public class War3PreBuild {


    public static void run() {

        if (FileUtil.exists(Config.filePath_game_project_root + "/src/App.ts")) {
            SolarLog.systemSay("正在编译TS工程到lua... 入口代码为:call Cheat(\"exec-lua: App\")");
//            ExeCmdUtil.tstl("--outDir", Config.filePath_game_project_root, Config.filePath_game_project_root + "/src/App.ts");
            ExeCmdUtil.tstlByTsConfig(Config.filePath_game_project_root + "/src/");
            //
            addCode2JassMain();
        }


    }


    public static void addCode2JassMain() {
        String war3map_j_filePath = Config.filePath_game_project_root + "war3map.j";
        String jassText = FileUtil.getStringFromFile(war3map_j_filePath);
        if (jassText.contains("Cheat(\"exec-lua: App\")")) {
            return;
        }
        String target = "    call InitGlobals()";
        if (jassText.contains(target)) {
            String replacement = "\r\ncall InitGlobals()\r\n" +
                    "call Cheat(\"exec-lua: App\")\r\n";
            jassText = jassText.replace(target, replacement);
            FileUtil.writeToFile(jassText, war3map_j_filePath);
            SolarLog.systemSay("正在对war3map.j main方法中添加call Cheat(\"exec-lua: App\")...");
        }

    }

}
