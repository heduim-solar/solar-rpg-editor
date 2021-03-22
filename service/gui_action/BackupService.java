package org.solar.editor.core.service.gui_action;

import javafx.concurrent.Task;
import org.solar.io.FileUtil;
import org.solar.jfx.util.DialogUtil;
import org.solar.jfx.util.FxUtil;
import org.solar.jfx.util.NotifyUtil;
import org.solar.lang.ListUtil;
import org.solar.lang.StringUtil;
import org.solar.log.Log;
import org.solar.util.DateUtil;
import org.solar.editor.core.Config;

import java.io.File;
import java.util.Date;
import java.util.List;

public class BackupService {

    public static String last_backupW3x_file_name = null;


    public static void backupW3x() {
        if (!FileUtil.exists(Config.filePath_game)) {
            return;
        }
        File file = new File(Config.filePath_game);
        String fileName = FileUtil.removeNameExtension(file.getName());
        FileUtil.mkdirs(Config.filePath_workspace + fileName + "_solar_bak/");
        String toPath = Config.filePath_workspace + fileName + "_solar_bak/";
        String timeStr = DateUtil.format(new Date(), "MM月dd日_HH点mm分ss秒");
        last_backupW3x_file_name = fileName + "_bak_" + timeStr + ".w3x";
        toPath = toPath + last_backupW3x_file_name;
        Log.info("备份到:" + fileName + "_bak_" + timeStr + ".w3x");
        FileUtil.copy(Config.filePath_game, toPath);
        System.gc();
    }


    public static void resumeW3xByBakAndReOpenFile() {
        File file = new File(Config.filePath_game);
        String fileName = FileUtil.removeNameExtension(file.getName());
        List<File> w3xFiles = FileUtil.getAllFiles(Config.filePath_workspace + fileName + "_solar_bak/", "w3x");
        if (ListUtil.isEmpty(w3xFiles)) {
            FxUtil.runLater(() -> {
//                DialogUtil.tip("请先备份文件!");
                NotifyUtil.showInfo("请先备份文件!");
            });
            Log.info("请先备份文件!");
            return;
        }
        Task task = TaskFactoryService.resumeW3xByBakTask();
        DialogUtil.executeTaskWithProgressDialog(task);
    }


    public static void resumeW3xByBak() {
        File filePath_gameFile = new File(Config.filePath_game);
        String fileName = FileUtil.removeNameExtension(filePath_gameFile.getName());
        List<File> w3xFiles = FileUtil.getAllFiles(Config.filePath_workspace + fileName + "_solar_bak/", "w3x");
        if (ListUtil.isEmpty(w3xFiles)) {
            return;
        }
        w3xFiles = FileUtil.sortByLastModified(w3xFiles);
        File file = w3xFiles.get(0);
        String fromPath = file.getPath();
        String fromFileName = file.getName();
        FileUtil.copy(fromPath, Config.filePath_game);
        Log.info("resumeW3xByBak:fromPath: \r\n" + fromPath);
        if (StringUtil.isNotEmpty(last_backupW3x_file_name)) {
            if (!fromFileName.equals(last_backupW3x_file_name)) {
                for (int i = 0; i < 6; i++) {
                    System.out.println("警告:本次恢复的文件不是最后一次备份的文件!");
                }
            }
        }
    }


}
