package org.solar.editor.core.service.gui_action;

import javafx.stage.FileChooser;
import org.solar.editor.core.BaseConfig;
import org.solar.editor.core.Config;
import org.solar.io.FileUtil;

import java.io.File;

public class GaNewProjectService {


    public static void newW3xFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Config.filePath_windows_Desktop));
        fileChooser.setInitialFileName("test.w3x");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            BaseConfig.filePath_game = FileUtil.formatPath(file.getAbsolutePath());
            BaseConfig.save2DB(BaseConfig.class);
            FileUtil.copy(Config.filePath_app_data + "war3/base.w3x",
                    Config.filePath_game
            );
            GaOpenFileService.openW3xFileAndRefreshState(new File(Config.filePath_game));
        }

    }


}
