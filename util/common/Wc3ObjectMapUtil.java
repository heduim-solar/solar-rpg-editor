//package org.solar.editor.core.util.common;
//
//import org.solar.lang.ListUtil;
//import org.solar.lang.StringUtil;
//import org.solar.log.Log;
//import org.solar.war3.bean.Wc3ObjectMap;
//import org.solar.war3.util.ColorsUtil;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//public class Wc3ObjectMapUtil {
//
//    public static void addAbilList(Wc3ObjectMap unit, String... abilityIds) {
//        if (unit == null) {
//            return;
//        }
//        String abilityIdsStr = containsIds(Arrays.asList(abilityIds));
//        String unitAbilList = unit.getAbilList();
//        if (StringUtil.isEmpty(unitAbilList)) {
//            unit.setAbilList(abilityIdsStr);
//            return;
//        } else if (unitAbilList.trim().endsWith(",")) {
//            unit.setAbilList(unitAbilList + abilityIdsStr);
//        } else {
//            unit.setAbilList(unitAbilList + "," + abilityIdsStr);
//        }
//    }
//
//    public static void addSellitems(Wc3ObjectMap unit, String... sellitems) {
//        String idsStr = containsIds(Arrays.asList(sellitems));
//        String oIdsStr = unit.getSellitems();
//        if (StringUtil.isEmpty(oIdsStr)) {
//            unit.setSellitems(idsStr);
//            return;
//        } else if (oIdsStr.trim().endsWith(",")) {
//            unit.setSellitems(oIdsStr + idsStr);
//        } else {
//            unit.setSellitems(oIdsStr + "," + idsStr);
//        }
//    }
//
//
//    public static String containsIds(String... ids) {
//
//        return StringUtil.containsWithSeparator(",", ids);
//    }
//
//    public static String containsIds(Wc3ObjectMap... w3oMaps) {
//
//        return containsIds(Arrays.asList(w3oMaps));
//    }
//
//    public static String containsIds(List w3oMaps) {
//        String result = "";
//        int length = w3oMaps.size();
//        for (int i = 0; i < length; i++) {
//            Object obj = w3oMaps.get(i);
//            if (obj instanceof Wc3ObjectMap) {
//                obj = ((Wc3ObjectMap) obj).getID();
//            }
//            result = result + obj;
//            if (i != (length - 1)) {
//                result = result + ",";
//            }
//        }
//        return result;
//    }
//
//    public static String getId(Object obj) {
//        if (obj instanceof String) {
//            return (String) obj;
//        } else if (obj instanceof Wc3ObjectMap) {
//            return ((Wc3ObjectMap) obj).getID();
//        }
//        return String.valueOf(obj);
//    }
//
//    public static String[] getIdArray(List<Wc3ObjectMap> w3oMaps) {
//        return ListUtil.toStringArray(getIdList(w3oMaps));
//    }
//
//    public static String[] getIdArray(String idsStr) {
//        if (idsStr == null) {
//            return null;
//        }
//        String[] strs = idsStr.split(",");
//        if (strs == null) {
//            return null;
//        }
//        List list = new ArrayList();
//        for (int i = 0; i < strs.length; ++i) {
//            if (StringUtil.isNotEmpty(strs[i]) && strs[i].length() == 4) {
//                list.add(strs[i]);
//            }
//        }
//        String[] new_strs = new String[list.size()];
//        list.toArray(new_strs);
//        return new_strs;
//    }
//
//    public static List getIdList(String str) {
//        return ListUtil.asList(getIdArray(str));
//    }
//
//    public static List<String> getIdList(List<Wc3ObjectMap> w3oMaps) {
//        List<String> result = new ArrayList();
//        int length = w3oMaps.size();
//        for (int i = 0; i < length; i++) {
//            Wc3ObjectMap wc3ObjectMap = w3oMaps.get(i);
//            if (wc3ObjectMap == null) {
//                continue;
//            }
//            String id = wc3ObjectMap.getID();
//            result.add(id);
//        }
//        return result;
//    }
//
//
//    public static double setUnitColorAndModelScale(Wc3ObjectMap unitW3oMap, double model_scale_max, int i, int cgMax) {
//        double model_scale_min = 0.8;
//        if (model_scale_min > model_scale_max) {
//            model_scale_min = model_scale_max - 0.2;
//        }
//        return setUnitColorAndModelScale(unitW3oMap, model_scale_min, model_scale_max, i, cgMax);
//    }
//
//    public static double setUnitColorAndModelScale(Wc3ObjectMap unitW3oMap, double model_scale_min, double model_scale_max, int i, int cgMax) {
//        ColorsUtil.setUnitColor(unitW3oMap, i, cgMax);
//
//        return setUnitModelScale(unitW3oMap, model_scale_min, model_scale_max, i, cgMax);
//    }
//
//    public static double setUnitColorAndModelScale(Wc3ObjectMap unitW3oMap, int i, int cgMax) {
//        ColorsUtil.setUnitColor(unitW3oMap, i, cgMax);
//        return setUnitModelScale(unitW3oMap, i, cgMax);
//    }
//
//    public static double setUnitColor(Wc3ObjectMap unitW3oMap, int i, int cgMax) {
//        ColorsUtil.setUnitColor(unitW3oMap, i, cgMax);
//        return 0;
//    }
//
//    //单位模型缩放
//    public static double setUnitModelScale(Wc3ObjectMap unitW3oMap, int i, int cgMax) {
//        return setUnitModelScale(unitW3oMap, 0.8, 1.2, i, cgMax);
//    }
//
//    public static double setUnitModelScale(Wc3ObjectMap unitW3oMap, double model_scale_min, double model_scale_max, int i, int cgMax) {
//        double modelScale = (model_scale_max - model_scale_min) * i / cgMax;
//        modelScale = model_scale_min + modelScale;
//        unitW3oMap.setModelScale(modelScale);
//        return modelScale;
//    }
//
//    /**
//     * item
//     */
//    public static double setItemColorAndModelScale(Wc3ObjectMap itemW3oMap, int i, int cgMax) {
//        setItemColor(itemW3oMap, i, cgMax);
//        return setItemModelScale(itemW3oMap, 0.9, 1.8, i, cgMax);
//    }
//
//
//    public static double setItemModelScale(Wc3ObjectMap itemW3oMap, double model_scale_min, double model_scale_max, int i, int cgMax) {
//        double modelScale = (model_scale_max - model_scale_min) * i / cgMax;
//        modelScale = model_scale_min + modelScale;
//        itemW3oMap.setScale(modelScale);
//        return modelScale;
//    }
//
//    public static void setItemRandomColor(Wc3ObjectMap itemW3oMap) {
//        itemW3oMap.setColorR(new Random().nextInt(25) * 10);
//        itemW3oMap.setColorG(new Random().nextInt(25) * 10);
//        itemW3oMap.setColorB(new Random().nextInt(25) * 10);
//    }
//
//    public static void setItemColor(Wc3ObjectMap itemW3oMap, String colorStr) {
//        colorStr = colorStr.toLowerCase();
//        if (colorStr.startsWith("|cff")) {
//            colorStr = colorStr.replace("|cff", "#");
//        }
//        Color color = Color.decode(colorStr);
//        itemW3oMap.setColorR(color.getRed());
//        itemW3oMap.setColorG(color.getGreen());
//        itemW3oMap.setColorB(color.getBlue());
//    }
//
//    public static void setItemColor(Wc3ObjectMap itemW3oMap, int level, int cgMax) {
//        ColorsUtil.interval = 255 * 3 / cgMax;
//        itemW3oMap.setColorR(ColorsUtil.red(cgMax - level + 1));
//        itemW3oMap.setColorG(ColorsUtil.green(cgMax - level + 1));
//        itemW3oMap.setColorB(ColorsUtil.blue(cgMax - level + 1));
//    }
//
//    //
//    public static String getHeroPrimaryByAbilityInfo(Wc3ObjectMap abilityW3oMap) {
//        String[] primarys = new String[]{"STR", "AGI", "INT"};
//        String primary = primarys[new Random().nextInt(2)];
//        if (abilityW3oMap == null) {
//            return primary;
//        }
//        String abilityUbertip = abilityW3oMap.getUbertip();
//        if (!abilityUbertip.contains("智力") &&
//                !abilityUbertip.contains("敏捷") &&
//                !abilityUbertip.contains("力量")
//        ) {
////            System.out.println(abilityW3oMap.getName() + " 技能无单属性伤害");
//            return primary;
//        }
//        if (abilityUbertip.contains("全属性")) {
////            System.out.println(abilityW3oMap.getName() + " 技能为全属性伤害");
//            return primary;
//        }
//        if (abilityUbertip.contains("力量")) {
//            return "STR";
//        } else if (abilityUbertip.contains("敏捷")) {
//            return "AGI";
//        } else if (abilityUbertip.contains("智力")) {
//            return "INT";
//        }
//        return primary;
//    }
//
//
//    public static void setUnitNullModel(Wc3ObjectMap w3oMap) {
//        w3oMap.setFile("");
//        w3oMap.setMovetp("");
//        w3oMap.setModelScale(0.3);
//        w3oMap.setScale(0.0);//选择缩放
//        w3oMap.setCollision(0.0);//碰撞体积
//        w3oMap.setUnitShadow("");
//        w3oMap.setPathTex("");
//        w3oMap.setBuildingShadow("");
//    }
//
//
//    public static int compare(Wc3ObjectMap w3oMap1, Wc3ObjectMap w3oMap2, String compareKey) {
//        if (w3oMap1 == null || w3oMap2 == null) {
//            return 0;
//        }
//        if (StringUtil.isEmpty(compareKey)) {
//            return 0;
//        }
//        String w3oMap1Val = String.valueOf(w3oMap1.get(compareKey));
//        String w3oMap2Val = String.valueOf(w3oMap2.get(compareKey));
//        if (StringUtil.isRealNumber(w3oMap1Val)) {
//            try {
//                int result = Integer.valueOf(w3oMap1Val).compareTo(Integer.valueOf(w3oMap2Val));
//                return result;
//            } catch (Exception e) {
//            }
//        }
//        return w3oMap1Val.compareTo(w3oMap2Val);
//    }
//
//
//    public static Wc3ObjectMap getW3oMap(List<Wc3ObjectMap> wc3ObjectMapList, String id) {
//        for (Wc3ObjectMap w3oMap : wc3ObjectMapList) {
//            if (id.equals(w3oMap.getID())) {
//                return w3oMap;
//            }
//        }
//        return null;
//    }
//
//    public static List<Wc3ObjectMap> getW3oMapByIds(String ids, List<Wc3ObjectMap>... wc3ObjectMapLists) {
//        String[] idsArray = Wc3ObjectMapUtil.getIdArray(ids);
//        List<Wc3ObjectMap> resultW3oList = new ArrayList<>();
//        for (int i = 0; i < idsArray.length; i++) {
//            boolean flag = true;
//            for (int j = 0; j < wc3ObjectMapLists.length; j++) {
//                List<Wc3ObjectMap> wc3ObjectMapList = wc3ObjectMapLists[j];
//                Wc3ObjectMap w3oMap = getW3oMap(wc3ObjectMapList, idsArray[i]);
//                if (w3oMap != null) {
//                    resultW3oList.add(w3oMap);
//                    flag = false;
//                    break;
//                }
//            }
//            if (flag) {
//                Log.info("w3oMap = null. id is " + idsArray[i]);
//            }
//        }
//        return resultW3oList;
//    }
//
//
//}
