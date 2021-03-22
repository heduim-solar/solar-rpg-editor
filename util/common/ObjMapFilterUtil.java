package org.solar.editor.core.util.common;


import org.solar.editor.core.bean.ObjMap;
import org.solar.lang.StringUtil;

import java.util.Map;


public class ObjMapFilterUtil {
/**

 params.名字包括 = ""
 params.名字不包括 = ""
 params.判断值 = new SolarEnum("lumbercost", "goldcost", "Requires", "abilList", "file", "Requiresamount", "fused",
 "bldtm", "inEditor", "rangeN1", "upgrades")
 params.判断值比较方式 = new SolarEnum("大于", "小于", "等于", "包括", "不包括")
 params.比较值 = ""
 params.判断值2 = params.判断值.valueOf("goldcost")
 params.判断值比较方式2 = params.判断值比较方式.valueOf("小于")
 params.比较值2 = ""
 params.判断值3 = params.判断值.valueOf("goldcost")
 params.判断值比较方式3 = params.判断值比较方式.valueOf("大于")
 params.比较值3 = ""


 */

    /**
     * 是否满足过滤参数
     * params = 过滤参数
     */
    public static boolean filter(Map params, ObjMap w3oMap) {
        if (w3oMap == null) {
            return false;
        }
        if (params == null) {
            return true;
        }
        //
        String id = w3oMap.getID();
        String name = id + w3oMap.getName();
        String abilList = w3oMap.getAbilList();
        //
//        String[] namebks = null;
//        String namebk = params.名字包括
//        if (namebk != null && namebk.contains(" ")) {
//            namebks = namebk.split(" ")
//        }
        //
        if (StringUtil.isNotEmpty(params.get("名字包括"))) {
            if (!isContains(name, params.get("名字包括"))) {
                return false;
            }
        }


        //
        if (StringUtil.isNotEmpty(params.get("名字不包括"))) {
            if (isContains(w3oMap.getName(), params.get("名字不包括"))) {
                return false;
            }
        }
//        if (StringUtil.isNotEmpty(params.名字不包括) && w3oMap.getName().contains(params.名字不包括 as String)) {
//            return false;
//        }

        if (StringUtil.isNotEmpty(params.get("比较值")) &&
                !compare(params.get("比较值").toString(), params.get("判断值比较方式").toString(), w3oMap.get(params.get("判断值").toString()))
        ) {
            return false;
        }
        if (StringUtil.isNotEmpty(params.get("比较值2")) &&
                !compare(params.get("比较值2").toString(), params.get("判断值比较方式2").toString(), w3oMap.get(params.get("判断值2").toString()))
        ) {
            return false;
        }
        if (StringUtil.isNotEmpty(params.get("比较值3")) &&
                !compare(params.get("比较值3").toString(), params.get("判断值比较方式3").toString(), w3oMap.get(params.get("判断值3").toString()))
        ) {
            return false;
        }


        //
        return true;
    }


    public static boolean compare(String compareVal, String compareMethod, Object w3oValue) {
        if (StringUtil.isNotEmpty(compareVal)) {
            Object value = w3oValue;
            if (value == null) {
                return false;
            }
            String bjz = compareVal;
            switch (compareMethod) {
                case "大于":
                    Double aDouble = Double.valueOf(String.valueOf(value));
                    Double bjzDouble = Double.valueOf(bjz);
                    if (!(aDouble > bjzDouble)) {
                        return false;
                    }
                    break;
                case "小于":
                    aDouble = Double.valueOf(String.valueOf(value));
                    bjzDouble = Double.valueOf(bjz);
                    if (!(aDouble < bjzDouble)) {
                        return false;
                    }
                    break;
                case "等于":
                    String aDoubleStr = String.valueOf(value);
                    String bjzDoubleStr = String.valueOf(bjz);
                    if (!aDoubleStr.equals(bjzDoubleStr)) {
                        return false;
                    }
                    break;
//                    Double aDouble = Double.valueOf(String.valueOf(value));
//                    Double bjzDouble = Double.valueOf(bjz);
//                    if (!(Math.abs(aDouble - bjzDouble) < 0.1)) {
//                        return false
//                    }
//                    break
                case "不等于":
                    aDouble = Double.valueOf(String.valueOf(value));
                    bjzDouble = Double.valueOf(bjz);
                    if ((Math.abs(aDouble - bjzDouble) < 0.1)) {
                        return false;
                    }
                    break;
                case "包括":
                    if (!isContains(String.valueOf(value), bjz)) {
                        return false;
                    }
                    break;
                case "不包括":
                    if (isContains(String.valueOf(value), bjz)) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }


    public static boolean isContains(String text, Object p) {
        if (StringUtil.isEmpty(text)) {
            return false;
        }
        if (StringUtil.isEmpty(p)) {
            return true;
        }
        String namebk = String.valueOf(p);
        String[] namebks = null;
        if (namebk != null && namebk.contains(" ")) {
            namebks = namebk.split(" ");
        }
        if (namebks != null) {
            boolean isContains = false;
            for (int j = 0; j < namebks.length; j++) {
                String temp = namebks[j];
                if (text.contains(temp)) {
                    isContains = true;
                }
            }
            if (!isContains) {
                return false;
            }
        } else if (!text.contains(namebk)) {
            return false;
        }
        return true;
    }

}
