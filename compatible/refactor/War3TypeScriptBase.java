package org.solar.editor.core.compatible.refactor;

import org.solar.editor.core.Config;
import org.solar.io.FileUtil;

public class War3TypeScriptBase {


    public static void addTypeScriptBaseFiles2Dir(String dir) {

        FileUtil.copy(Config.filePath_app_data + "code_lib/war3", Config.filePath_game_project_root + "src/");


//        FileUtil.copy();
    }


}
