package org.solar.editor.core;

import org.solar.editor.core.bean.ObjMap;
import org.solar.editor.core.compatible.ObjMapFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameDataUtil {
    private static String gameVar_src_dir = Config.filePath_game_project_root + "/src/";

    /**
     * @return
     */
    public static List<ObjMap> addUnitObjMaps(Object... objs) {
        List<ObjMap> objMapList = getUnitObjMapList();
        addObjMaps(objMapList, objs);
        saveUnitObjMapList(objMapList);
        return objMapList;
    }

    public static List<ObjMap> addItemObjMaps(Object... objs) {
        List<ObjMap> objMapList = getItemObjMapList();
        addObjMaps(objMapList, objs);
        saveItemObjMapList(objMapList);
        return objMapList;
    }

    public static List<ObjMap> addAbilityObjMaps(Object... objs) {
        List<ObjMap> objMapList = getAbilityObjMapList();
        addObjMaps(objMapList, objs);
        saveAbilityObjMapList(objMapList);
        return objMapList;
    }

    public static List<ObjMap> addBuffObjMaps(Object... objs) {
        List<ObjMap> objMapList = getBuffObjMapList();
        addObjMaps(objMapList, objs);
        saveBuffObjMapList(objMapList);
        return objMapList;
    }

    public static List<ObjMap> addUpgradeObjMaps(Object... objs) {
        List<ObjMap> objMapList = getUpgradeObjMapList();
        addObjMaps(objMapList, objs);
        saveUpgradeObjMapList(objMapList);
        return objMapList;
    }

    public static List<ObjMap> addDoodadObjMaps(Object... objs) {
        List<ObjMap> objMapList = getDoodadObjMapList();
        addObjMaps(objMapList, objs);
        saveDoodadObjMapList(objMapList);
        return objMapList;
    }

    public static List<ObjMap> addDestructableObjMaps(Object... objs) {
        List<ObjMap> objMapList = getDestructableObjMapList();
        addObjMaps(objMapList, objs);
        saveDestructableObjMapList(objMapList);
        return objMapList;
    }

    //one case
    public static List<ObjMap> addObjMaps(List<ObjMap> storeObjMapList, Object... objs) {
        if (storeObjMapList == null) {
            storeObjMapList = new ArrayList<>();
        }
        if (objs == null || objs.length == 0) {
            return storeObjMapList;
        }
        //
        for (int i = 0; i < objs.length; i++) {
            Object addObj = objs[i];
            if (addObj instanceof ObjMap) {
                storeObjMapList.add((ObjMap) addObj);
            } else if (addObj instanceof List) {
                storeObjMapList.addAll((List) addObj);
            }
        }
        return storeObjMapList;
    }


    /**
     * @return
     */
    public static List<ObjMap> getUnitObjMapList() {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                return ObjMapFileUtil.parseObjMapList(new File(gameVar_src_dir + "war3map.w3u_.xlsx"));
            case dota2:
                break;
        }
        return null;
    }

    public static List<ObjMap> getItemObjMapList() {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                return ObjMapFileUtil.parseObjMapList(new File(gameVar_src_dir + "war3map.w3t_.xlsx"));
            case dota2:
                break;
        }
        return null;
    }

    public static List<ObjMap> getAbilityObjMapList() {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                return ObjMapFileUtil.parseObjMapList(new File(gameVar_src_dir + "war3map.w3a_.xlsx"));
            case dota2:
                break;
        }
        return null;
    }

    public static List<ObjMap> getBuffObjMapList() {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                return ObjMapFileUtil.parseObjMapList(new File(gameVar_src_dir + "war3map.w3h_.xlsx"));
            case dota2:
                break;
        }
        return null;
    }

    public static List<ObjMap> getUpgradeObjMapList() {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                return ObjMapFileUtil.parseObjMapList(new File(gameVar_src_dir + "war3map.w3q_.xlsx"));
            case dota2:
                break;
        }
        return null;
    }

    public static List<ObjMap> getDoodadObjMapList() {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                return ObjMapFileUtil.parseObjMapList(new File(gameVar_src_dir + "war3map.w3d_.xlsx"));
            case dota2:
                break;
        }
        return null;
    }

    public static List<ObjMap> getDestructableObjMapList() {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                return ObjMapFileUtil.parseObjMapList(new File(gameVar_src_dir + "war3map.w3b_.xlsx"));
            case dota2:
                break;
        }
        return null;
    }


    /**
     * save
     *
     * @return
     */
    public static void saveUnitObjMapList(List<ObjMap> objMapList) {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                ObjMapFileUtil.writeObjMapList(objMapList, new File(gameVar_src_dir + "war3map.w3u_.xlsx"));
            case dota2:
                break;
        }

    }

    public static void saveItemObjMapList(List<ObjMap> objMapList) {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                ObjMapFileUtil.writeObjMapList(objMapList, new File(gameVar_src_dir + "war3map.w3t_.xlsx"));
            case dota2:
                break;
        }

    }

    public static void saveAbilityObjMapList(List<ObjMap> objMapList) {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                ObjMapFileUtil.writeObjMapList(objMapList, new File(gameVar_src_dir + "war3map.w3a_.xlsx"));
            case dota2:
                break;
        }

    }

    public static void saveBuffObjMapList(List<ObjMap> objMapList) {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                ObjMapFileUtil.writeObjMapList(objMapList, new File(gameVar_src_dir + "war3map.w3h_.xlsx"));
            case dota2:
                break;
        }

    }

    public static void saveUpgradeObjMapList(List<ObjMap> objMapList) {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                ObjMapFileUtil.writeObjMapList(objMapList, new File(gameVar_src_dir + "war3map.w3q_.xlsx"));
            case dota2:
                break;
        }

    }

    public static void saveDoodadObjMapList(List<ObjMap> objMapList) {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                ObjMapFileUtil.writeObjMapList(objMapList, new File(gameVar_src_dir + "war3map.w3d_.xlsx"));
            case dota2:
                break;
        }

    }

    public static void saveDestructableObjMapList(List<ObjMap> objMapList) {
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                ObjMapFileUtil.writeObjMapList(objMapList, new File(gameVar_src_dir + "war3map.w3b_.xlsx"));
            case dota2:
                break;
        }

    }


}
