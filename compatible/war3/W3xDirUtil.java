package org.solar.editor.core.compatible.war3;

import net.moonlightflower.wc3libs.bin.ObjMod;
import net.moonlightflower.wc3libs.txt.WTS;
import org.solar.cache.Cache;
import org.solar.cache.CacheImpl;
import org.solar.editor.core.util.FilePathUtil;
import org.solar.editor.core.Config;

import java.io.File;

public class W3xDirUtil {
    private static final Cache cache = new CacheImpl();

    public static WTS getWar3map_wts() {
        File file = new File(FilePathUtil.game_root_war3map_wts());
        return cache.get(getCacheKey("getWar3map_wts", file.lastModified() + ""), () -> {
            try {
                WTS wts = new WTS(file);
                return wts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new WTS();
        });
    }

    public static String getRealStringByWts(ObjMod.Obj.Mod data) {
        Object val = data.getVal();
        if (data.getValType() == ObjMod.ValType.STRING) {
            String str = String.valueOf(data.getVal());
            if (str.startsWith("TRIGSTR_")) {
                return getRealStringByWts(str);
            }
        }
        return String.valueOf(val);
    }

    public static String getRealStringByWts(String name) {
        return cache.get(getCacheKey("getWar3map_wts_string", name), () -> {
            WTS wts = getWar3map_wts();
            String realStr = wts.getNamedEntries().get(name);
            if (realStr == null) {
                return name;
            }
            return realStr;
        });


    }

    public static String getCacheKey(String... args) {
        String cacheKey = Config.filePath_game + ":";
        for (int i = 0; i < args.length; i++) {
            cacheKey = cacheKey + args[i] + ":";
        }
        return cacheKey;
    }

}
