package org.solar.editor.core.bean;

import org.solar.editor.core.util.common.TemplateUtil;
import org.solar.io.FileUtil;

import java.io.File;

public class GameScript {

    private String type = "ts";
    private String category = "";
    private String fileName = "";
    private String fileContent = "";
    private String comment = "";


    public GameScript() {

    }

    public GameScript(File file, Object dataModel) {
        String fileName = file.getName();
        setType(FileUtil.getNameExtension(fileName));
        setFileName(FileUtil.removeNameExtension(fileName));
        String fileContent = FileUtil.getStringFromFile(file);
        fileContent = TemplateUtil.processStringTemplate(fileContent, dataModel);
        setFileContent(fileContent);
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
