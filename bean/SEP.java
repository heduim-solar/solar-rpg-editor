package org.solar.editor.core.bean;

import org.solar.editor.core.Config;
import org.solar.lang.SolarMap;

public class SEP extends SolarMap {
    {
        put("root_dir", Config.filePath_plugin);
        put("lni_dir", Config.filePath_game_project_root);

    }

    public void setName(String name) {
        put("name", name);
    }

    public void setDir(String dir) {
        put("dir", dir);
    }

    public void setCategory(String category) {
        put("category", category);
    }

}
