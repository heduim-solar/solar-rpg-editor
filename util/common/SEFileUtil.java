package org.solar.editor.core.util.common;

import org.solar.archive.SarEntry;
import org.solar.archive.SarFileUtil;
import org.solar.archive.SarOutputStream;
import org.solar.coder.Md5Util;
import org.solar.editor.core.Config;
import org.solar.io.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SEFileUtil {
    public static String mm = "solar_world_editor";

    static {
        mm = Md5Util.getMd5Hex(mm) + "s" + mm.substring(1, 3);
        mm = Md5Util.getMd5Hex(mm);
    }

    private static final String desktop = Config.filePath_windows_Desktop;
    private static final String sarFilePath = desktop + "太阳世界编辑器插件.seps";

    public static void main(String[] args) {
//        SarFile sarFile = new SarFile(sarFilePath,mm);

//        file2Sar();
        SarFileUtil.sar2File(sarFilePath, desktop + "太阳世界编辑器插件/", mm);
    }


    public static void exportPlugIn(String export2FilePath, List<File> plugInFileList) {
        if (plugInFileList == null || plugInFileList.size() == 0) {
            return;
        }
        List<File> needOutList = new ArrayList<>();
        for (int i = 0; i < plugInFileList.size(); i++) {
            File file = plugInFileList.get(i);
            File dirFile = file.getParentFile();
            List<File> tempList = FileUtil.getAllFiles(dirFile.getAbsolutePath() + "/", new ArrayList<>(), true, null);
            needOutList.addAll(tempList);
        }
        try {
            file2Sar(Config.filePath_plugin, needOutList, new FileOutputStream(export2FilePath), mm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void file2Sar() {
        SarFileUtil.file2Sar(
                desktop + "wa3datapatch/", sarFilePath, mm);
    }

    public static void file2Sar(String removePath, List<File> fileList, OutputStream out, String password) {
        SarOutputStream sarOutputStream = null;
        try {
            sarOutputStream = new SarOutputStream(out);
            SarEntry[] sarEntries = new SarEntry[fileList.size()];
            long index = 0;
            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                SarEntry sarEntry = new SarEntry();
                sarEntry.setIndex(index);
                String name = file.getCanonicalPath();
                name = FileUtil.formatPath(name);
                name = name.replace(removePath, "");
                sarEntry.setName(name);

                if (file.isDirectory()) {
                    sarEntry.setType(SarEntry.TYPE_Directory);
                    sarEntry.setSize(0);
                } else {
                    sarEntry.setType(SarEntry.TYPE_FILE);
                    sarEntry.setSize(file.length());
                }
                sarEntry.setLastModified(file.lastModified());
                sarEntries[i] = sarEntry;
                index = index + sarEntry.getSize();
            }
            sarOutputStream.putEntrys(password, sarEntries);
            sarOutputStream.writeData(removePath, sarEntries);
            sarOutputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sarOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
