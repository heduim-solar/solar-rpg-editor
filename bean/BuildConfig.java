package org.solar.editor.core.bean;

public class BuildConfig {

    public boolean dist = false;
    public boolean slk = false;
    public boolean obfuscateScripts = false;
    public boolean optimizationModel = false;


    public void setSaveModel() {
        dist = false;
        slk = false;
        obfuscateScripts = false;
        optimizationModel = false;
    }

    public void setDistModel() {
        dist = true;
        slk = false;
        obfuscateScripts = false;
        optimizationModel = false;
    }

    public void setTestModel() {
        dist = true;
        slk = false;
        obfuscateScripts = false;
        optimizationModel = false;
    }


}
