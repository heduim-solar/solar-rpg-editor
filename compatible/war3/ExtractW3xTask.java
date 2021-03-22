package org.solar.editor.core.compatible.war3;

import javafx.concurrent.Task;
import org.solar.concurrent.ThreadUtil;
import org.solar.editor.SE;

import org.solar.editor.core.bean.ObjMap;
import org.solar.editor.core.compatible.ObjMapFileUtil;
import org.solar.io.FileUtil;
import org.solar.lang.StringUtil;
import systems.crigges.jmpq3.JMpqEditor;
import systems.crigges.jmpq3.MPQOpenOption;
import systems.crigges.jmpq3.MpqFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExtractW3xTask extends Task {

    /**
     * static
     */
    private static final List<String> objFileNameList = new ArrayList();

    static {
        objFileNameList.add("war3map.wts");
        objFileNameList.add("war3map.w3u");
        objFileNameList.add("war3map.w3t");
        objFileNameList.add("war3map.w3b");
        objFileNameList.add("war3map.w3d");
        objFileNameList.add("war3map.w3a");
        objFileNameList.add("war3map.w3h");
        objFileNameList.add("war3map.w3q");
    }

    /**
     * o
     */
    File w3xFile;
    String toDir;

    public ExtractW3xTask(File w3xFile, String toDir) {
        this.w3xFile = w3xFile;
        this.toDir = toDir + "/";

    }

    @Override
    public Object call() throws Exception {
        System.gc();
//        ThreadUtil.refreshStartTime();
        FileUtil.deleteDirectory(this.toDir);
        updateProgress(0, 100);
        JMpqEditor jMpqEditor1 = new JMpqEditor(w3xFile, MPQOpenOption.READ_ONLY);
        List<String> fileNameList = jMpqEditor1.getFileNames();
        final int count = fileNameList.size();
        updateMessage("解压物编文件...");

        objFileNameList.forEach(fileName -> {
            updateMessage("解压物编文件:" + fileName);
            try {
                if (jMpqEditor1.hasFile(fileName)) {
                    extractWar3Data(jMpqEditor1, fileName);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        });
        //解压游戏对象到excel
        objModFile2ExcelAll();

        updateMessage("解压文件...");
        for (int i = 0; i < fileNameList.size(); i++) {
            updateMessage("[" + i + "/" + (count + 1) + "]");
            updateProgress(i, count);
            try {
                String fileName = fileNameList.get(i);
                if (!objFileNameList.contains(fileName)) {
                    extractWar3Data(jMpqEditor1, fileName);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return null;
    }

    public void objModFile2ExcelAll() {
        //解压游戏对象到excel
        SE.addBackgroundTask(new Task() {
            @Override
            protected Object call() throws Exception {
                updateMessage("转换" + "物编" + "到src/(*)_.xlsx...");
                List<Thread> threadList = new ArrayList<>();
                ThreadUtil.refreshStartTime();
                threadList.add(objModFile2Excel(toDir + "war3map.w3h"));
                threadList.add(objModFile2Excel(toDir + "war3map.w3b"));
                threadList.add(objModFile2Excel(toDir + "war3map.w3d"));
                threadList.add(objModFile2Excel(toDir + "war3map.w3q"));
                threadList.add(objModFile2Excel(toDir + "war3map.w3t"));
                threadList.add(objModFile2Excel(toDir + "war3map.w3u"));
                threadList.add(objModFile2Excel(toDir + "war3map.w3a"));
                threadList.forEach((thread -> {
                    if (thread != null) {
                        thread.start();
                    }
                }));
                threadList.forEach((thread -> {
                    try {
                        if (thread == null) {
                            return;
                        }
                        updateMessage("转换" + "物编" + "到src/(*)_.xlsx...[" + thread.getName() + "]");
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
                ThreadUtil.printUseTimeInfo("解压对象到excel");
                SE.refreshFileTreeView();
                return null;
            }
        });
    }

    ;

    public Thread objModFile2Excel(String filePath) {
        //解压游戏对象到excel
        File objModFile = new File(filePath);
        if (!objModFile.exists()) {
            return null;
        }
        Thread thread = new Thread(() -> {
            ThreadUtil.refreshStartTime();
            List<ObjMap> objMapList = ObjMapFileUtil.parseObjMapList(objModFile);
            File xlsxFile = new File(objModFile.getParent() + "/src/" + objModFile.getName() + "_.xlsx");
            ObjMapFileUtil.writeObjMapList(objMapList, xlsxFile);
            if (ThreadUtil.getUseTime() > 5000) {
                ThreadUtil.printUseTimeInfo("解析" + objModFile.getName());
            }
        });
        thread.setName(objModFile.getName());
        return thread;
    }


    public void extractWar3Data(JMpqEditor jMpqEditor, String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return;
        }
        try {
            MpqFile mpqFile = jMpqEditor.getMpqFile(fileName);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mpqFile.extractToOutputStream(byteArrayOutputStream);
            ThreadUtil.execute(() -> {
                File toFile = new File(toDir + fileName);
                if (toFile.exists()) {
                    return;
                }
                FileUtil.mkParentDirs(toFile);
                try {
                    byte[] fileBytes = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    FileUtil.writeToFile(fileBytes, toFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FileUtil.cacheLastModified(toFile);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
