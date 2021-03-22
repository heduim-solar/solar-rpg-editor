package org.solar.editor.core.service.gui_action;

import javafx.concurrent.Task;
import org.solar.editor.core.util.common.AppSystemUtil;
import org.solar.io.FileUtil;
import org.solar.jfx.util.FxUtil;
import org.solar.editor.core.Config;

import java.io.File;

public class TaskFactoryService {


    public static Task resumeW3xByBakTask() {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                updateProgress(1, 10);
                updateProgress(2, 10);
                updateMessage("正在删除w3x");
                File file = new File(Config.filePath_game);
                if (!file.renameTo(file)) {
                    AppSystemUtil.closeWorldeditydwe();
                }
                FileUtil.delete(Config.filePath_game);
                updateProgress(3, 10);
                updateMessage("从备份拷贝到当前工作文件");
                BackupService.resumeW3xByBak();
                updateProgress(5, 10);
                updateMessage("重新读取地图数据");
                FxUtil.runLater(() -> {
                    GaOpenFileService.openW3xFileAndRefreshState(new File(Config.filePath_game));
                });
                updateProgress(9, 10);
                return null;
            }
        };
    }


}
