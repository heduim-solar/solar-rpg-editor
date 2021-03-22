package org.solar.editor.core.compatible.war3;

import org.solar.war3.bean.Wc3ObjectMap;

import java.util.ArrayList;
import java.util.List;

public class MapStatistics {
    public static String triggerLmlsString = "";
    public static String war3mapjString = "";
    //    public static List<Wc3ObjectMap> unitBaseListW3oMap;
//    public static List<Wc3ObjectMap> abilityBaseListW3oMap;
//    public static List<Wc3ObjectMap> doodadBaseListW3oMap;
//    public static List<Wc3ObjectMap> itemBaseListW3oMap;
//    public static List<Wc3ObjectMap> buffBaseListW3oMap;
//    public static List<Wc3ObjectMap> upgradeBaseListW3oMap;
    public static List<String> mapIdLIst;

    static {

//        unitBaseListW3oMap = GlobalVariable.unitBaseListW3oMap;
//        abilityBaseListW3oMap = GlobalVariable.abilityBaseListW3oMap;
//        itemBaseListW3oMap = GlobalVariable.itemBaseListW3oMap;
//        buffBaseListW3oMap = GlobalVariable.buffBaseListW3oMap;
//        upgradeBaseListW3oMap = GlobalVariable.upgradeBaseListW3oMap;
//        doodadBaseListW3oMap = GlobalVariable.doodadBaseListW3oMap;
        //统一用小写的id 计算 （32位）
        refreshMapIdLIst();
    }

    public static void refreshMapIdLIst() {
        mapIdLIst = extractMapId2List(true);
    }


    public static int getIdMaxInt10(String idPrefix) {
        String idmax = getIdMax(mapIdLIst, idPrefix);
        return id2int(idmax);
    }

    public static String getIdMax(String idPrefix) {
        String idmax = getIdMax(mapIdLIst, idPrefix);
        return idmax;
    }


    public static int id2int(String id) {
        String sortId = id.substring(1, 4);
        int id10 = Integer.parseInt(sortId, 32);
        return id10;
    }

    public static String getIdMax(List<String> idList, String idPrefix) {
        String idmax = "0000";
        int idmax10 = 0;
        for (int i = 0; i < idList.size(); i++) {
            String id = idList.get(i);
            if (!id.startsWith(idPrefix)) {
                continue;
            }
            if (id.startsWith(idPrefix + "z")) {//idPrefix+z开头的id为自定义全局id 需要排除在id start 外
                continue;
            }
            if (id.startsWith(idPrefix + "w") ||
                    id.startsWith(idPrefix + "x") ||
                    id.startsWith(idPrefix + "y")) {
                continue;
            }

            int id10 = id2int(id);
            if (id10 > idmax10) {
                idmax = id;
                idmax10 = id10;
            }
        }
        return idmax;
    }

    public static List filteIDLIst(List<String> idList, String idPrefix) {
        List list = new ArrayList();
        for (int i = 0; i < idList.size(); i++) {
            String id = idList.get(i);
            if (id.startsWith(idPrefix)) {
                list.add(id);
            }
        }
        return list;
    }

    public static List extractMapId2List(boolean toLowerCase) {
        List list = new ArrayList();
        List temp = null;
//        temp = extractIdByWc3ObjectMapList(GlobalVariable.unitBaseListW3oMap, toLowerCase);
//        list.addAll(temp);
//        temp = extractIdByWc3ObjectMapList(GlobalVariable.abilityBaseListW3oMap, toLowerCase);
//        list.addAll(temp);
//        temp = extractIdByWc3ObjectMapList(GlobalVariable.itemBaseListW3oMap, toLowerCase);
//        list.addAll(temp);
//        temp = extractIdByWc3ObjectMapList(GlobalVariable.buffBaseListW3oMap, toLowerCase);
//        list.addAll(temp);
//        temp = extractIdByWc3ObjectMapList(GlobalVariable.upgradeBaseListW3oMap, toLowerCase);
//        list.addAll(temp);
//        temp = extractIdByWc3ObjectMapList(GlobalVariable.doodadBaseListW3oMap, toLowerCase);
//        list.addAll(temp);
//        temp = extractIdByWc3ObjectMapList(GlobalVariable.destructableBaseListW3oMap, toLowerCase);
//        list.addAll(temp);
        return list;
    }


    public static List extractIdByWc3ObjectMapList(List<Wc3ObjectMap> w3oList, boolean toLowerCase) {
        List list = new ArrayList();
        for (int i = 0; i < w3oList.size(); i++) {
            Wc3ObjectMap w3oMap = w3oList.get(i);
            String id = w3oMap.getID();
            if (toLowerCase) {
                id = id.toLowerCase();
            }
            list.add(id);
        }
        return list;
    }


    public static int calculateMapCanGetMoney(int score) {
        if (score < 20) {
            return 0;
        }
        if (score < 40) {
            return score;
        }
        if (score < 60) {
            return score * 10;
        }

        if (score < 70) {
            int temp = score - 50;
            return temp * temp * 10;
        }
        if (score < 80) {
            int temp = score - 50;
            return temp * temp * 20;
        }
        if (score < 90) {
            int temp = score - 50;
            return temp * temp * 50;
        }
        int temp = score - 50;
        return temp * temp * 100;
    }

//    public static int calculateMapQualityScore() {
//        double unitScore = 10d * ((GlobalVariable.unitBaseListW3oMap.size() + 1d) / 500d);
//        double itemScore = 20d * ((GlobalVariable.itemBaseListW3oMap.size() + 1d) / 1000d);
//        double abilityScore = 20d * ((GlobalVariable.abilityBaseListW3oMap.size() + 1d) / 1000d);
//        double doodadScore = 10d * ((GlobalVariable.doodadBaseListW3oMap.size() + 1d) / 300d);
//        double buffScore = 5d * ((GlobalVariable.doodadBaseListW3oMap.size() + 1d) / 100d);
//        double upgradeScore = 5d * ((GlobalVariable.upgradeBaseListW3oMap.size() + 1d) / 100d);
//        double triggerScore = 10d * ((GlobalVariable.triggerBaseList.size() + 1d) / 500d);
//        int resFileSize = 0;
//        if (FileUtil.exists(Configs.filePath_lni_directory_resource)) {
//            String[] fileNames = new File(Configs.filePath_lni_directory_resource).list();
//            resFileSize = fileNames.length;
//        }
//        double resFileScore = 20d * ((resFileSize + 1d) / 1000d);
//
//        double mapScore = unitScore + itemScore + abilityScore + doodadScore + buffScore + upgradeScore + triggerScore + resFileScore;
//        if (mapScore > 100) {
//            mapScore = 100 + ((mapScore - 100) / 10);
//        }
//        return (int) mapScore;
//    }
//
//    public static int getResourceFileCount() {
//        int resFileSize = 0;
//        if (FileUtil.exists(Configs.filePath_lni_directory_resource)) {
//            List files = FileUtil.getAllFiles(Configs.filePath_lni_directory_resource, new ArrayList<>(), true, null);
//            resFileSize = files.size();
//        }
//        return resFileSize;
//    }

}
