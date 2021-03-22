package org.solar.editor.core.compatible.war3.common;

import org.solar.editor.core.Config;
import org.solar.io.FileUtil;

import java.io.File;

public class War3ResourceUtil {

//    public static String war3dataDir = "D:/game/war3data/";
//    public static String war3dataxDir = "D:/game/war3datax/";
//    public static String wa3datapatchDir = "D:/game/wa3datapatch/";


    public static File getResource(String fileName) {
        if (FileUtil.isAbsolutePath(fileName)) {
            return new File(fileName);
        }
        String path = Config.filePath_game_project_root + fileName;
        if (FileUtil.exists(path)) {
            return new File(path);
        }
        path = Config.filePath_war3_mpq_files + fileName;
        if (FileUtil.exists(path)) {
            return new File(path);
        }
//        fileName = FileUtil.formatPath(fileName);
        if (fileName != null && FileUtil.isExtension(fileName, "mdl")) {
            fileName = FileUtil.removeNameExtension(fileName) + ".mdx";
        }
        if (!fileName.contains(".")){
            fileName = fileName + ".mdx";
        }
        path = Config.filePath_game_project_root + fileName;
        if (FileUtil.exists(path)) {
            return new File(path);
        }
        path = Config.filePath_war3_mpq_files + fileName;
        if (FileUtil.exists(path)) {
            return new File(path);
        }
        return null;
    }

//    public static InputStream getResourceInputStream(String fileName) {
//        if (fileName.startsWith("C:") || fileName.startsWith("D:") || fileName.startsWith("E:")) {
//            try {
//                return new FileInputStream(fileName);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        String path = Config.filePath_lni_directory_resource + fileName;
//        if (FileUtil.exists(path)) {
//            try {
//                return new FileInputStream(path);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        InputStream inputStream = getInputStreamFromMpq(fileName);
//        return inputStream;
//    }
//
//    static JMpqEditor jMpqEditor_war3 = null;
//    static JMpqEditor jMpqEditor_War3Patch = null;
//    static JMpqEditor jMpqEditor_War3x = null;
//    static JMpqEditor jMpqEditor_War3xLocal = null;
//
//    public static synchronized void initMpqEditors() {
//        if (jMpqEditor_war3 == null) {
//            try {
//                String mpqRootPath = Config.filePath_warcraft_directory_root;
//                jMpqEditor_war3 = new JMpqEditor(new File(mpqRootPath + "war3.mpq"), MPQOpenOption.READ_ONLY);
//                jMpqEditor_War3Patch = new JMpqEditor(new File(mpqRootPath + "War3Patch.mpq"), MPQOpenOption.READ_ONLY);
//                jMpqEditor_War3x = new JMpqEditor(new File(mpqRootPath + "War3x.mpq"), MPQOpenOption.READ_ONLY);
//                jMpqEditor_War3xLocal = new JMpqEditor(new File(mpqRootPath + "War3xLocal.mpq"), MPQOpenOption.READ_ONLY);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static InputStream getInputStreamFromMpq(String fileName) {
//        try {
//            if (jMpqEditor_war3 == null) {
//                System.out.println("initMpqEditors");
//                initMpqEditors();
//            }
//            fileName = FileUtil.formatPath(fileName);
//            fileName = fileName.replace("/", "\\");
//            JMpqEditor jMpqEditor = null;
//            if (jMpqEditor_war3.hasFile(fileName)) {
//                jMpqEditor = jMpqEditor_war3;
//            } else if (jMpqEditor_War3Patch.hasFile(fileName)) {
//                jMpqEditor = jMpqEditor_War3Patch;
//            } else if (jMpqEditor_War3x.hasFile(fileName)) {
//                jMpqEditor = jMpqEditor_War3x;
//            } else if (jMpqEditor_War3xLocal.hasFile(fileName)) {
//                jMpqEditor = jMpqEditor_War3xLocal;
//            } else {
//                return null;
//            }
//            byte[] bytes = jMpqEditor.extractFileAsBytes(fileName);
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//            return byteArrayInputStream;
//        } catch (Exception e) {
////            System.out.println(e.getMessage());
////            e.printStackTrace();
//        }
//        return null;
//    }

}
