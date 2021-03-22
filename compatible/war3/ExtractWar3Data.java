package org.solar.editor.core.compatible.war3;

import javafx.concurrent.Task;
import org.solar.io.FileUtil;
import org.solar.lang.StringUtil;
import org.solar.editor.core.Config;
import systems.crigges.jmpq3.JMpqEditor;
import systems.crigges.jmpq3.JMpqException;
import systems.crigges.jmpq3.MPQOpenOption;

import java.io.File;
import java.util.List;

public class ExtractWar3Data extends Task {


    @Override
    public Object call() throws Exception {
        updateProgress(0, 100);
        String path = Config.filePath_warcraft_directory_root;
        if (!FileUtil.exists(path + "War3x.mpq")){
            return null;
        }
        JMpqEditor jMpqEditor1 = new JMpqEditor(new File(path + "War3x.mpq"), MPQOpenOption.READ_ONLY);
        List<String> fileNameList1 = jMpqEditor1.getFileNames();
        JMpqEditor jMpqEditor2 = new JMpqEditor(new File(path + "War3xLocal.mpq"), MPQOpenOption.READ_ONLY);
        List<String> fileNameList2 = jMpqEditor2.getFileNames();
        JMpqEditor jMpqEditor3 = new JMpqEditor(new File(path + "War3Patch.mpq"), MPQOpenOption.READ_ONLY);
        List<String> fileNameList3 = jMpqEditor3.getFileNames();
        JMpqEditor jMpqEditor4 = new JMpqEditor(new File(path + "War3.mpq"), MPQOpenOption.READ_ONLY);
        List<String> fileNameList4 = jMpqEditor4.getFileNames();
        int index = 0;
        List<String> fileNameList = fileNameList1;
        final int count = fileNameList.size() + fileNameList2.size() + fileNameList3.size() + fileNameList4.size();
        for (int i = 0; i < fileNameList.size(); i++) {
            updateMessage("[" + index + "/" + count + "]");
            index++;
            updateProgress(index, count);
            try {
                extractWar3Data(jMpqEditor1, fileNameList.get(i));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        fileNameList = fileNameList2;
        for (int i = 0; i < fileNameList.size(); i++) {
            updateMessage("[" + index + "/" + count + "]");
            index++;
            updateProgress(index, count);
            try {
                extractWar3Data(jMpqEditor2, fileNameList.get(i));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        fileNameList = fileNameList3;
        for (int i = 0; i < fileNameList.size(); i++) {
            updateMessage("[" + index + "/" + count + "]");
            index++;
            updateProgress(index, count);
            try {
                extractWar3Data(jMpqEditor3, fileNameList.get(i));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        fileNameList = fileNameList4;
        for (int i = 0; i < fileNameList.size(); i++) {
            updateMessage("[" + index + "/" + count + "]");
            index++;
            updateProgress(index, count);
            try {
                extractWar3Data(jMpqEditor4, fileNameList.get(i));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }


    public static void extractWar3Data(JMpqEditor jMpqEditor, String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return;
        }
        if (!FileUtil.isExtension(fileName, "blp", "mdx", "tga", "slk", "txt")) {
            return;
        }
        File toFile = new File(Config.filePath_war3_mpq_files + fileName);
        if (toFile.exists()) {
            return;
        }
        FileUtil.mkParentDirs(toFile);
        try {
            jMpqEditor.extractFile(fileName, toFile);
        } catch (JMpqException e) {
            e.printStackTrace();
        }

    }

}
