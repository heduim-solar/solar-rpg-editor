package org.solar.editor.core.compatible;

import org.solar.editor.core.Config;
import org.solar.editor.core.util.common.ExeCmdUtil;

public class ProjectRunService {


    public static void run() {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                ExeCmdUtil.nircmd_elevate(
                        Config.filePath_world_editor_root + "\\bin\\YDWEConfig.exe",
                        "-launchwar3",
                        "-loadfile",
                        Config.filePath_game);
                break;
        }
    }


}
