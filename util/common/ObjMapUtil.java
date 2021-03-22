package org.solar.editor.core.util.common;

import org.solar.lang.JsonUtil;
import org.solar.lang.ListUtil;
import org.solar.lang.StringUtil;
import org.solar.war3.constant.War3BaseObjectData;
import org.solar.editor.core.Config;
import org.solar.editor.core.bean.ObjMap;
import org.solar.editor.core.compatible.war3.War3BaseDataUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class ObjMapUtil {

    public static ObjMap getById(List<ObjMap> objMapList, String id) {
        if (objMapList == null || id == null) {
            return null;
        }
        for (ObjMap objMap : objMapList) {
            if (id.equals(objMap.getID())) {
                return objMap;
            }
        }
        return null;
    }


    public static String getName(ObjMap objMap) {
        String name = getValueWithParentData(objMap, "Name");
        if (name == null) {
            name = getValueWithParentData(objMap, "EditorName");
        }
        if (name == null) {
            name = getValueWithParentData(objMap, "Bufftip");
        }
        if (name == null) {
            return null;
        }
        if (name.startsWith("{") && name.endsWith("}")) {
            if (name.contains(":")) {
                Map map = JsonUtil.parseObject(name);
                name = String.valueOf(map.values().iterator().next());
            } else {
                name = StringUtil.remove(name, "\"", "{", "}");
                name = name.split("\\.")[0];
            }
        }
        if (name != null && name.startsWith("WESTRING_")) {
            name = War3BaseDataUtil.getWorldEditStrings(name);
        }

        return name;
    }

    public static String getValueWithParentData(ObjMap objMap, String key) {
        Object value = objMap.get(key);
        if (value != null) {
            return String.valueOf(value);
        }
        switch (Config.gameFileTypeProperty.get()) {
            case war3:
                if (value == null) {
                    String _parent = (String) objMap.get("_parent");
                    if (StringUtil.isEmpty(_parent)) {
                        return null;
                    }
                    _parent = _parent.replace("\"", "");
                    value = War3BaseObjectData.getW3oBaseData(_parent, key.toLowerCase());
                }
                if (value == null) {
                    return null;
                }
                String valueString = String.valueOf(value);
                if (valueString != null && !valueString.startsWith("{")) {
                    value = StringUtil.remove(valueString, "\"");
                }
                if (value == null) {
                    return null;
                }
                return String.valueOf(value);
            case dota2:
                break;
        }
        return null;
    }

    /**
     *
     */
    public static void addAbilList(ObjMap unit, String... abilityIds) {
        if (unit == null) {
            return;
        }
        String abilityIdsStr = containsIds(Arrays.asList(abilityIds));
        String unitAbilList = unit.getAbilList();
        if (StringUtil.isEmpty(unitAbilList)) {
            unit.setAbilList(abilityIdsStr);
            return;
        } else if (unitAbilList.trim().endsWith(",")) {
            unit.setAbilList(unitAbilList + abilityIdsStr);
        } else {
            unit.setAbilList(unitAbilList + "," + abilityIdsStr);
        }
    }

    public static void addSellitems(ObjMap unit, String... sellitems) {
        String idsStr = containsIds(Arrays.asList(sellitems));
        String oIdsStr = unit.getSellitems();
        if (StringUtil.isEmpty(oIdsStr)) {
            unit.setSellitems(idsStr);
            return;
        } else if (oIdsStr.trim().endsWith(",")) {
            unit.setSellitems(oIdsStr + idsStr);
        } else {
            unit.setSellitems(oIdsStr + "," + idsStr);
        }
    }


    public static String containsIds(String... ids) {

        return StringUtil.containsWithSeparator(",", ids);
    }

    public static String containsIds(ObjMap... w3oMaps) {

        return containsIds(Arrays.asList(w3oMaps));
    }

    public static String containsIds(List w3oMaps) {
        String result = "";
        int length = w3oMaps.size();
        for (int i = 0; i < length; i++) {
            Object obj = w3oMaps.get(i);
            if (obj instanceof ObjMap) {
                obj = ((ObjMap) obj).getID();
            }
            result = result + obj;
            if (i != (length - 1)) {
                result = result + ",";
            }
        }
        return result;
    }

    public static String getId(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof ObjMap) {
            return ((ObjMap) obj).getID();
        }
        return String.valueOf(obj);
    }

    public static String[] getIdArray(List<ObjMap> w3oMaps) {
        return ListUtil.toStringArray(getIdList(w3oMaps));
    }

    public static String[] getIdArray(String idsStr) {
        if (idsStr == null) {
            return null;
        }
        String[] strs = idsStr.split(",");
        if (strs == null) {
            return null;
        }
        List list = new ArrayList();
        for (int i = 0; i < strs.length; ++i) {
            if (StringUtil.isNotEmpty(strs[i]) && strs[i].length() == 4) {
                list.add(strs[i]);
            }
        }
        String[] new_strs = new String[list.size()];
        list.toArray(new_strs);
        return new_strs;
    }

    public static List getIdList(String str) {
        return ListUtil.asList(getIdArray(str));
    }

    public static List<String> getIdList(List<ObjMap> w3oMaps) {
        List<String> result = new ArrayList();
        int length = w3oMaps.size();
        for (int i = 0; i < length; i++) {
            ObjMap ObjMap = w3oMaps.get(i);
            if (ObjMap == null) {
                continue;
            }
            String id = ObjMap.getID();
            result.add(id);
        }
        return result;
    }

    /**
     *
     */
    public static int compare(ObjMap w3oMap1, ObjMap w3oMap2, String compareKey) {
        if (w3oMap1 == null || w3oMap2 == null) {
            return 0;
        }
        if (StringUtil.isEmpty(compareKey)) {
            return 0;
        }
        String w3oMap1Val = String.valueOf(w3oMap1.get(compareKey));
        String w3oMap2Val = String.valueOf(w3oMap2.get(compareKey));
        if (StringUtil.isRealNumber(w3oMap1Val)) {
            try {
                int result = Integer.valueOf(w3oMap1Val).compareTo(Integer.valueOf(w3oMap2Val));
                return result;
            } catch (Exception e) {
            }
        }
        return w3oMap1Val.compareTo(w3oMap2Val);
    }

}
