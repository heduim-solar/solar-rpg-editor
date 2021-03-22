package org.solar.editor.core.compatible.war3;

import net.moonlightflower.wc3libs.port.NotFoundException;
import net.moonlightflower.wc3libs.port.win.WinGameDirFinder;
import org.solar.cache.Cache;
import org.solar.cache.CacheImpl;
import org.solar.concurrent.ThreadUtil;
import org.solar.editor.core.Config;
import org.solar.io.DataBase;
import org.solar.io.FileUtil;
import org.solar.lang.StringUtil;
import org.solar.log.Log;

public class War3Util {
    public static final Cache cache = new CacheImpl();

    public static void checkExtractWar3Data() {
        String isok = DataBase.get(ExtractWar3Data.class.getCanonicalName() + ":isok");
        if (StringUtil.isEmpty(isok) || !FileUtil.exists(Config.filePath_war3_mpq_files)) {
            ExtractWar3Data task = new ExtractWar3Data();
            Log.info("ExtractWar3Data");
            task.setOnSucceeded((e) -> {
                DataBase.save(ExtractWar3Data.class.getCanonicalName() + ":isok", "ok");
                Log.info("ExtractWar3Data Ok");
            });
            task.setOnFailed((e) -> {
                task.getException().printStackTrace();
            });
            ThreadUtil.execute(task);
        }
    }

    public static String findWar3GameRoot() {
        WinGameDirFinder winGameDirFinder = new WinGameDirFinder();
        try {
            Object obj = winGameDirFinder.get();
            if (obj == null) {
                return null;
            }
            return FileUtil.formatPath(obj + "/");
        } catch (NotFoundException e) {
            System.err.println("未读取到魔兽注册表信息！");
        }
        return null;
    }





}
