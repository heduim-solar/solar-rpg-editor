package org.solar.editor.core.compatible;

import net.moonlightflower.wc3libs.bin.ObjMod;
import net.moonlightflower.wc3libs.bin.Wc3BinOutputStream;
import net.moonlightflower.wc3libs.bin.app.objMod.*;
import org.solar.cache.Cache;
import org.solar.cache.CacheImpl;
import org.solar.editor.core.bean.ObjMap;
import org.solar.editor.core.compatible.war3.ObjModUtil;
import org.solar.io.FileUtil;
import org.solar.editor.core.compatible.common.ObjMapExcelUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ObjMapFileUtil {

    public static final Cache cache = new CacheImpl();


    public static List<ObjMap> parseObjMapList(File file) {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        String cacheKey = "parseObjMapList:" + file.getAbsolutePath() + ":" + file.lastModified();
        return cache.get(cacheKey, () -> {
            String fileName = file.getName();
            if (FileUtil.isExtension(fileName, "xls", "xlsx")) {
                return ObjMapExcelUtil.excel2ObjMapList(file);
            } else if (fileName.startsWith("war3map.w3")) {
                return parseWar3mapObjMapList(file);
            }
            return new ArrayList<>();
        });
    }

    public static List<ObjMap> parseWar3mapObjMapList(File file) {
        String fileName = file.getName();
        ObjMod objMod = null;
        String fileExtension = FileUtil.getNameExtension(fileName);
        try {
            if ("w3u".equalsIgnoreCase(fileExtension)) {
                objMod = new W3U(file);
            } else if ("w3t".equalsIgnoreCase(fileExtension)) {
                objMod = new W3T(file);
            } else if ("w3b".equalsIgnoreCase(fileExtension)) {
                objMod = new W3B(file);
            } else if ("w3d".equalsIgnoreCase(fileExtension)) {
                objMod = new W3D(file);
            } else if ("w3a".equalsIgnoreCase(fileExtension)) {
                objMod = new W3A(file);
            } else if ("w3h".equalsIgnoreCase(fileExtension)) {
                objMod = new W3H(file);
            } else if ("w3q".equalsIgnoreCase(fileExtension)) {
                objMod = new W3Q(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List unitList = objMod.getObjsList();
        List<ObjMap> resultList = ObjModUtil.obj2Map(unitList);
        return resultList;
    }

    public static void main(String[] args) {

    }


    public static void writeObjMapList(List<ObjMap> objMapList, File file) {
        String fileName = file.getName();
        if (fileName.endsWith("_.xlsx")) {
            String originalFileName = fileName.replace("_.xlsx", "");
            byte[] bytes = ObjMapExcelUtil.objMapList2Excel((List) objMapList, originalFileName);
            FileUtil.writeToFile(bytes, file);
        } else {
            writeObjMapList2war3ObjMod(objMapList, file);
        }
        String cacheKey = "parseObjMapList:" + file.getAbsolutePath() + ":" + file.lastModified();
        cache.put(cacheKey, objMapList);
    }

    public static void writeObjMapList2war3ObjMod(List<ObjMap> objMapList, File file) {
        String fileName = file.getName();
        try {
            ObjMod objMod = null;
            String fileExtension = FileUtil.getNameExtension(fileName);
            try {
                if ("w3u".equalsIgnoreCase(fileExtension)) {
                    objMod = new W3U();
                } else if ("w3t".equalsIgnoreCase(fileExtension)) {
                    objMod = new W3T();
                } else if ("w3b".equalsIgnoreCase(fileExtension)) {
                    objMod = new W3B();
                } else if ("w3d".equalsIgnoreCase(fileExtension)) {
                    objMod = new W3D();
                } else if ("w3a".equalsIgnoreCase(fileExtension)) {
                    objMod = new W3A();
                } else if ("w3h".equalsIgnoreCase(fileExtension)) {
                    objMod = new W3H();
                } else if ("w3q".equalsIgnoreCase(fileExtension)) {
                    objMod = new W3Q();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            objMod.clearObjs();
            ObjMod objModf = objMod;
            objMapList.forEach(objMap -> {
                ObjModUtil.addObjMap2ObjMod(objModf, objMap);
            });
            Wc3BinOutputStream wc3BinOutputStream = new Wc3BinOutputStream(file);
            objMod.write(wc3BinOutputStream);
            wc3BinOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
