package org.solar.editor.core.compatible.war3;

import org.solar.io.FileUtil;
import org.solar.lang.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class War3MapIMPUtil {

    public static final List banList = new ArrayList<>();

    static {
        banList.add("war3mapMap.blp");
        banList.add("war3mapMisc.txt");
        banList.add("war3mapUnits.doo");

    }


    public static boolean needImp(String fileMpqName) {
        if (StringUtil.isEmpty(fileMpqName)) {
            return false;
        }
        if (banList.contains(fileMpqName)) {
            return false;
        }
        if (fileMpqName.startsWith("war3map.")) {
            return false;
        }
        if (fileMpqName.startsWith(".")) {
            return false;
        }
        if (fileMpqName.startsWith("src\\")) {
            return false;
        }
        if (FileUtil.isExtension(fileMpqName, "xls", "xlsx", "json", "ts", "j")) {
            return false;
        }
        return true;
    }


}
