package org.solar.editor.core.compatible.war3;

import net.moonlightflower.wc3libs.bin.app.IMP;
import org.solar.io.FileUtil;
import org.solar.editor.core.Config;
import org.solar.editor.core.util.FilePathUtil;
import systems.crigges.jmpq3.JMpqEditor;
import systems.crigges.jmpq3.MPQOpenOption;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MpqUtil {
//    public static final Cache cache = new CacheImpl();


    public static void extractAllFiles(File w3xFile, String toDir) {
        try {
            JMpqEditor jMpqEditor = new JMpqEditor(w3xFile, MPQOpenOption.READ_ONLY);
            FileUtil.deleteAndRemkDir(toDir);
            File toDirFile = new File(toDir);
            jMpqEditor.extractAllFiles(toDirFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void insertFile(String mpqFilePath, File file) {
        try {
            JMpqEditor jMpqEditor = new JMpqEditor(new File(mpqFilePath), MPQOpenOption.FORCE_V0);
            jMpqEditor.insertFile(file.getName(), file, false);
            jMpqEditor.close(true, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllFiles(String mpqFilePath) {
        try {
            JMpqEditor jMpqEditor = new JMpqEditor(new File(mpqFilePath), MPQOpenOption.FORCE_V0);
            System.out.println(jMpqEditor.getFileNames());
            List<String> fileNames = jMpqEditor.getFileNames();
            fileNames.forEach((fileName) -> {
                if (jMpqEditor.hasFile(fileName) && !"solar_mpq_demo".equals(fileName)) {
                    System.out.println("rm : " + fileName);
                    jMpqEditor.deleteFile(fileName);
                }
            });
            jMpqEditor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * aMpqFile 是一个已经存在的Mpq文件
     * 此方法会自动清空Mpq文件已存在的文件 然后把目录里的所有文件打包进Mpq
     *
     * @param dirPath
     * @param aMpqFile
     */
    public static void compressDir2Mpq(String dirPath, String aMpqFile) {
        dirPath = FileUtil.formatPath(dirPath);
        if (!dirPath.endsWith("/")) {
            dirPath = dirPath + "/";
        }
        File toFile = new File(aMpqFile);
        try {
            JMpqEditor jMpqEditor = new JMpqEditor(toFile, MPQOpenOption.FORCE_V0);
            List<File> fileList = FileUtil.getAllFiles(dirPath, true);
            List<String> fileMpqNameList = new ArrayList<>();

            IMP imp = new IMP();
            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                String fileAbsolutePath = FileUtil.formatPath(file.getAbsolutePath());
                String fileMpqName = fileAbsolutePath.replace(dirPath, "");
                fileMpqName = fileMpqName.replace("/", "\\");
                if (Config.buildConfig.dist) {
                    if (FileUtil.isExtension(fileMpqName, "xlsx", "ts", "js")) {
                        continue;
                    }
                }
//                System.out.println("War3MapIMPUtil.needImp(fileMpqName)="+War3MapIMPUtil.needImp(fileMpqName));
                if (War3MapIMPUtil.needImp(fileMpqName)) {
                    IMP.Obj impObj = new IMP.Obj();
                    impObj.setPath(fileMpqName);
                    imp.addObj(impObj);
                }
                fileMpqNameList.add(fileMpqName);
                if (!FileUtil.isModified(file)) {
                    continue;
                }
                FileUtil.cacheLastModified(file);
                if (jMpqEditor.hasFile(fileMpqName)) {
                    jMpqEditor.deleteFile(fileMpqName);
                }
                jMpqEditor.insertFile(fileMpqName, file, false);
            }

            //one case
            List<String> fileNames = jMpqEditor.getFileNames();
            fileNames.forEach((fileName) -> {
                if (jMpqEditor.hasFile(fileName) && !fileMpqNameList.contains(fileName)) {
                    jMpqEditor.deleteFile(fileName);
                }
            });

            //
            File war3map_impFile = new File(FilePathUtil.game_root_war3map_imp());
            imp.write(war3map_impFile);
            if (jMpqEditor.hasFile("war3map.imp")) {
                jMpqEditor.deleteFile("war3map.imp");
            }
            jMpqEditor.insertFile("war3map.imp", war3map_impFile, false);
            jMpqEditor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
