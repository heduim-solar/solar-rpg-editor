package org.solar.editor.core.compatible.common;

import org.solar.concurrent.ThreadUtil;
import org.solar.editor.core.bean.ObjMap;
import org.solar.editor.SE;
import org.solar.jfx.app.SolarFxConfig;
import org.solar.lang.MapUtil;
import org.solar.lang.StringUtil;
import org.solar.util.ExcelUtil;

import java.io.File;
import java.util.*;

/**
 * keyVisibleMap.put(SolarFxConfig.getLanguage("id"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("_parent"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("Name"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("Tip"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("Ubertip"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("goldcost"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("lumbercost"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("Art"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("file"), true);
 * //        new Wc3ObjectMap().setAbilList()
 * keyVisibleMap.put(SolarFxConfig.getLanguage("abilList"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("heroAbilList"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("Description"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("cool1"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("dmgplus1"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("def"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("defUp"), true);
 * keyVisibleMap.put(SolarFxConfig.getLanguage("HP"), true);
 */
public class ObjMapExcelUtil {

    static List<String> unitBaseIds = new ArrayList();

    static {
        unitBaseIds.addAll(Arrays.asList("id", "_parent", "Name", "Tip", "Ubertip", "HP", "abilList", "heroAbilList", "cool1", "dmgplus1",
                "def", "defUp", "goldcost", "lumbercost", "Art", "file"));
    }

    static List<String> itemBaseIds = new ArrayList();

    static {
        itemBaseIds.addAll(Arrays.asList("id", "_parent", "Name", "Tip", "Ubertip", "abilList", "Description", "goldcost", "lumbercost",
                "Art", "usable", "lumbercost", "uses", "class", "HP"));
    }

    static List<String> abilityBaseIds = new ArrayList();

    static {
        abilityBaseIds.addAll(Arrays.asList("id", "_parent", "Name", "Tip", "Ubertip", "Art", "Buttonpos_1", "Buttonpos_2"));
    }

    static List<String> upgradeBaseIds = new ArrayList();

    static {
        upgradeBaseIds.addAll(Arrays.asList("id", "_parent", "Name", "Tip", "Ubertip", "Requires", "maxlevel", "Art"));
    }

    static List<String> buffBaseIds = new ArrayList();

    static {
        buffBaseIds.addAll(Arrays.asList("id", "_parent", "Bufftip", "Buffubertip", "EditorName"));
    }

    static List<String> doodadBaseIds = new ArrayList();

    static {
        doodadBaseIds.addAll(Arrays.asList("id", "_parent", "Name", "defScale", "file", "maxScale", "minScale", "pathTex", "tilesets", "showInMM"
        ));
    }

    static List<String> destructableBaseIds = new ArrayList();

    static {
        destructableBaseIds.addAll(Arrays.asList("id", "_parent", "Name", "file"));
    }

    static List<String> baseIds = new ArrayList();

    static {
        baseIds.addAll(Arrays.asList("id", "_parent"));
    }


    public static Map getBaseTitleMap(List<String> idList) {
        Map map = new LinkedHashMap();
        idList.forEach(it -> {
            map.put(it, SolarFxConfig.getLanguage(it));
        });
        return map;
    }

    public static final int keyRowIndex = 1;
    public static final int valueStartIndex = 0;

    public static List<ObjMap> excel2ObjMapList(File file) {
        List resultList = new ArrayList<>();
        Map<String, List<List>> sheetNameRowListMap = ExcelUtil.parseExcelAllSheets(file);
        if (sheetNameRowListMap == null || sheetNameRowListMap.size() == 0) {
            return resultList;
        }
        Map.Entry<String, List<List>> entry = sheetNameRowListMap.entrySet().iterator().next();
        //one case
        addRowList2MapList(resultList, entry.getValue());
        return resultList;
    }


    public static int[] addRowList2MapList(List<ObjMap> baseListW3oMap, List<List> rowList) {
        int addCount = 0;
        int updateCount = 0;
        if (rowList == null || rowList.size() < 3) {
            return new int[]{updateCount, addCount};
        }
        List<Map> unitW3oMapList = ExcelUtil.kvLists2MapList(rowList, keyRowIndex, valueStartIndex, ObjMap.class);
        for (int i = 0; i < unitW3oMapList.size(); i++) {
            ObjMap addW3oMap = (ObjMap) unitW3oMapList.get(i);
            MapUtil.removeByValue(addW3oMap, "#");
            MapUtil.removeNullValue(addW3oMap);
            //无id的生成id
            String id = addW3oMap.getID();
            if (StringUtil.isEmpty(id)) {
                addW3oMap.setID(SE.nextId());
                baseListW3oMap.add(addW3oMap);
                addCount++;
                continue;
            }
            if ("x".equalsIgnoreCase(id)) {
                addW3oMap.setID(SE.nextHeroId());
                baseListW3oMap.add(addW3oMap);
                addCount++;
                continue;
            }
            //移除此id的旧数据
            Iterator<ObjMap> iterator = baseListW3oMap.iterator();
            while (iterator.hasNext()) {
                ObjMap baseW3oMap = iterator.next();
                if (id.equals(baseW3oMap.getID())) {
                    iterator.remove();
                    updateCount++;
                    addCount--;
                    break;
                }
            }
            addCount++;
            baseListW3oMap.add(addW3oMap);
        }
        return new int[]{updateCount, addCount};
    }


    public static byte[] objMapList2Excel(List<Map> objMapList, String sheetName) {
        Map sheetNameRowListMap = new LinkedHashMap();
        //one case
        List<List> unitRowList = getExcelRowList(objMapList, getBaseTitleMap(baseIds));
        sheetNameRowListMap.put(sheetName, unitRowList);
        byte[] excelDats = ExcelUtil.toExcel(sheetNameRowListMap);
        return excelDats;
    }


    public static List getExcelRowList(List<Map> objMapList, Map baseTitle) {
        List<List> rowList = new ArrayList<>();
        Map titlesMap = getTitlesMap(objMapList, baseTitle);
        //title
        List idNameList = new ArrayList();
        List idList = new ArrayList();
        titlesMap.forEach((k, v) -> {
            idList.add(k);
            idNameList.add(v);
        });
        rowList.add(idNameList);
        rowList.add(idList);
        //datas
        objMapList.forEach((it) -> {
            Map objMap = it;
            List list = new ArrayList();
            for (int i = 0; i < idList.size(); i++) {
                String id = (String) idList.get(i);
                String value = null;
                if (objMap.containsKey(id) && objMap.get(id) != null) {
                    value = String.valueOf(objMap.get(id));
                }
                list.add(value);
            }
            rowList.add(list);
        });
        return rowList;
    }

    public static Map getTitlesMap(List<Map> objMapList, Map baseTitle) {
        if (objMapList == null || objMapList.size() == 0) {
            return baseTitle;
        }

        for (Map objMap : objMapList) {
            if (objMap == null) {
                continue;
            }
            objMap.forEach((k, v) -> {
                baseTitle.put(k, getObjKeyCNName((String) k));
            });
        }
        return baseTitle;
    }

    public static String getObjKeyCNName(String key) {
        return SolarFxConfig.getLanguage(key);
    }


}
