package org.solar.editor.core.bean;

import javafx.scene.image.WritableImage;

import java.util.Map;

public class SolarPlugin {

    private String name;
    private String fileDirPath;
    private String mainScriptCode;
    private Map packageData;
    private WritableImage iconImage;

    public boolean is_show_in_plugin_panel() {
        String propertiesName = "show_in_plugin_panel";
        if (packageData != null && packageData.containsKey(propertiesName)) {
            return (boolean)packageData.get(propertiesName);
        }
        return true;
    }

    public boolean is_show_in_unit_panel() {
        String propertiesName = "show_in_unit_panel";
        if (packageData != null && packageData.containsKey(propertiesName)) {
            return (boolean)packageData.get(propertiesName);
        }
        return false;
    }

    public boolean is_show_in_item_panel() {
        String propertiesName = "show_in_item_panel";
        if (packageData != null && packageData.containsKey(propertiesName)) {
            return (boolean)packageData.get(propertiesName);
        }
        return false;
    }

    public boolean is_show_in_ability_panel() {
        String propertiesName = "is_show_in_ability_panel";
        if (packageData != null && packageData.containsKey(propertiesName)) {
            return (boolean)packageData.get(propertiesName);
        }
        return false;
    }


    public WritableImage getIconImage() {
        return iconImage;
    }

    public void setIconImage(WritableImage iconImage) {
        this.iconImage = iconImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainScriptCode() {
        return mainScriptCode;
    }

    public void setMainScriptCode(String mainScriptCode) {
        this.mainScriptCode = mainScriptCode;
    }

    public String getFileDirPath() {
        return fileDirPath;
    }

    public void setFileDirPath(String fileDirPath) {
        this.fileDirPath = fileDirPath;
    }

    public Map getPackageData() {
        return packageData;
    }

    public void setPackageData(Map properties) {
        this.packageData = properties;
    }
}
