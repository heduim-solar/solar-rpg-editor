package org.solar.editor.core.service.gui_action;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import org.solar.editor.core.BaseConfig;
import org.solar.editor.core.GameFileType;
import org.solar.editor.core.util.SolarLog;
import org.solar.jfx.util.DialogUtil;
import org.solar.jfx.util.FxUtil;
import org.solar.log.Log;
import org.solar.editor.core.Config;
import org.solar.editor.core.compatible.war3.ExtractW3xTask;
import org.solar.editor.gui.AppState;
import org.solar.editor.gui.common.SolarLeftPaneContainer;
import org.solar.editor.gui.file_tree_view.FileTreeViewPane;

import java.io.File;

public class GaOpenFileService {

    public static void onOpenFileChooser() {
        FileChooser fileChooser = new FileChooser();
        File initialDirectory = new File(Config.filePath_windows_Desktop);
        if (initialDirectory.exists()) {
            fileChooser.setInitialDirectory(initialDirectory);
        } else {
            File desktopFile = new File(Config.filePath_windows_Desktop);
            if (desktopFile.exists()) {
                fileChooser.setInitialDirectory(desktopFile);
            } else {
                Log.error("desktopFile is not exists " + Config.filePath_windows_Desktop);
            }
        }
        fileChooser.getExtensionFilters().
                add(new FileChooser.ExtensionFilter("w3x", "*.w3x"));
        fileChooser.getExtensionFilters().
                add(new FileChooser.ExtensionFilter("w3m", "*.w3m"));
        fileChooser.getExtensionFilters().
                add(new FileChooser.ExtensionFilter("所有", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            System.gc();
            openW3xFileAndRefreshState(file);
        }
    }


    public static void openW3xFile(File w3xFile) {
        String toDir = Config.filePath_game_project_root;
        ExtractW3xTask extractMpqTask = new ExtractW3xTask(w3xFile, toDir);
        extractMpqTask.setOnSucceeded((event -> {
            FileTreeViewPane fileTreeViewLeftPane = FileTreeViewPane.of(Config.filePath_game_project_root);
            SolarLeftPaneContainer.set(fileTreeViewLeftPane);
            SolarLog.systemSay("打开文件完成!");
        }));
        extractMpqTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("        extractMpqTask.setOnFailed(new EventHandler<WorkerStateEvent>() {");
                extractMpqTask.getException().printStackTrace();
            }
        });
        //
        FxUtil.runLater(() -> {
            DialogUtil.executeTaskWithProgressDialog(extractMpqTask);
        });


    }


    public static void openW3xFileAndRefreshState(File file) {
        //第一步是切换编辑器 编辑的游戏文件类型
        Config.gameFileTypeProperty.set(GameFileType.war3);
        openW3xFile(file);
        //one case
        BaseConfig.filePath_game = file.getAbsolutePath();
        AppState.titleProperty.setValue(Config.AppName+" " + BaseConfig.filePath_game);
        BaseConfig.save2DB();
    }

}
