package org.solar.editor.core.compatible.war3;

import net.moonlightflower.wc3libs.bin.ObjMod;
import net.moonlightflower.wc3libs.dataTypes.DataType;
import net.moonlightflower.wc3libs.dataTypes.app.War3Int;
import net.moonlightflower.wc3libs.dataTypes.app.War3Real;
import net.moonlightflower.wc3libs.dataTypes.app.War3String;
import net.moonlightflower.wc3libs.misc.MetaFieldId;
import net.moonlightflower.wc3libs.misc.ObjId;
import org.solar.editor.core.compatible.ContentTypeMap;
import org.solar.lang.JsonUtil;
import org.solar.lang.StringUtil;
import org.solar.war3.constant.MetaData;
import org.solar.war3.constant.War3BaseObjectData;
import org.solar.editor.core.bean.ObjMap;

import java.util.*;

public class ObjModUtil {

    public static void main(String[] args) {

    }


    public static List obj2Map(List<ObjMod.Obj> objList) {
        List<ObjMap> resultList = new ArrayList();
        objList.forEach(obj -> {
            ObjMap wc3ObjectMap = obj2Map(obj);
            resultList.add(wc3ObjectMap);
        });
        return resultList;
    }

    public static ObjMap obj2Map(ObjMod.Obj obj) {
        ObjMap objMap = new ObjMap();
        objMap.contentType = obj.getClass().getSimpleName().toLowerCase();
        Map<MetaFieldId, List<ObjMod.Obj.Mod>> modListMap = obj.getModsMapByField();

        modListMap.forEach((fieldId, modList) -> {
            String key = MetaData.fieldId2field(fieldId.toString());
            Object vals = null;
//            System.out.println(fieldId+"->"+key);
            //one case
            if (obj.isExtended()) {

                Map map = new TreeMap();
                //one case
                int index = 0;
                String val = null;
                for (int i = 0; i < modList.size(); i++) {
                    ObjMod.Obj.ExtendedMod mod = (ObjMod.Obj.ExtendedMod) modList.get(i);
                    index = mod.getLevel();
//                    System.out.println("mod.getId()=" + mod.getId().toString());
//                    System.out.println("mod.index()=" + index);
//                    System.out.println("mod.getDataPt()=" + mod.getDataPt());

                    if (mod.getValType() == ObjMod.ValType.STRING) {
                        String str = String.valueOf(mod.getVal());
                        val = str;
                        if (str.startsWith("TRIGSTR_")) {
                            val = W3xDirUtil.getRealStringByWts(str);
                        }
                    } else {
                        val = String.valueOf(mod.getVal().toTXTVal());
                    }
                    map.put(index, val);
//                    System.out.println("val=" + val);
                }

                // one case
                if (map.size() == 1 && index <= 1) {
                    vals = val;
                } else {
                    vals = JsonUtil.toJSONString(map);
                }
//                System.out.println("vals="+vals);
            } else {
                ObjMod.Obj.Mod mod = modList.get(0);
                Object val = null;
                if (mod.getValType() == ObjMod.ValType.STRING) {
                    String str = String.valueOf(mod.getVal());
                    val = str;
                    if (str.startsWith("TRIGSTR_")) {
                        val = W3xDirUtil.getRealStringByWts(str);
                    }
                } else {
                    val = mod.getVal().toTXTVal();
                }
                vals = val;
            }
            objMap.put(key, vals);
        });
        objMap.setID(obj.getId().toString());
        objMap.set_parent(obj.getBaseId().toString());
        return objMap;
    }


    public static <T> T getModDataByField(ObjMod.Obj obj, String key) {
        String fieldId = MetaData.field2fieldId(obj.getClass().getSimpleName().toLowerCase(), key, obj.getBaseId().toString());
        DataType data = obj.get(MetaFieldId.valueOf(fieldId));
        if (data == null) {
            return null;
        }
        Object val = data.toTXTVal();
        if (val instanceof String) {
            val = W3xDirUtil.getRealStringByWts((String) val);
        }
        return (T) val;
    }


    /**
     * 从魔兽的自带文件获得默认属性 值
     */
    public static <T> T getModDataByField(ObjMod.Obj obj, String key, boolean getFromBaseIfnull) {
        T t = getModDataByField(obj, key);
        if (t == null && getFromBaseIfnull) {
            t = (T) War3BaseObjectData.getW3oBaseData(obj.getBaseId().toString(), key);
        }
        return t;
    }


    /**
     * 写文件
     */
    public static void addObjMap2ObjMod(ObjMod objModf, ObjMap objMap) {
        String id = objMap.getID();
        String baseId = objMap.get_parent();
        ObjId baseObjId = null;
        if (!id.equals(baseId)) {
            baseObjId = ObjId.valueOf(baseId);
        }
        ObjMod.Obj obj = objModf.addObj(ObjId.valueOf(id), baseObjId);
        objMap.forEach((k, v) -> {
            try {
                if ("id".equals(k) || "_parent".equals(k) || k == null || v == null) {
                    return;
                }
                String fieldId = MetaData.field2fieldId(ContentTypeMap.getContentType(objModf.getClass().getSimpleName()), (String) k, baseId);
//                System.out.println("fieldId=" + fieldId + " v=" + v);
//                String field = War3BaseDataUtil.getSlkVal(War3BaseDataUtil.getAllMetaData_slk(), fieldId, "field");
//                if (field == null || "null".equalsIgnoreCase(field)) {
//                    System.out.println(fieldId + "->" + field);
//                    return;
//                }
                if (fieldId == null || "null".equalsIgnoreCase(fieldId)) {
                    System.err.println("fieldId=null field=" + k + " baseId=" + baseId + " id=" + id);
                    return;
                }
                String valStr = String.valueOf(v);
                String dataTypeStr = War3BaseDataUtil.getDataTypeByFieldId(fieldId);
                if (dataTypeStr == null) {
                    dataTypeStr = "Data";
                }
                final String dataTypeStrf = dataTypeStr;
//                DataType data = new War3String(valStr);
                if (obj.isExtended()) {
                    int dataPt = getData(fieldId);
                    ObjMod.ValType valType = getValType(v, dataTypeStr);
                    ObjMod.ValType valTypef = valType;
                    if (valStr != null && valStr.startsWith("{") && valStr.endsWith("}") && valStr.contains(":")) {
                        Map map = JsonUtil.parseObject(valStr);
                        map.forEach((mk, mv) -> {
                            DataType emData = getObjModData(mv, dataTypeStrf);
                            ObjMod.Obj.ExtendedMod mod = new ObjMod.Obj.ExtendedMod(
                                    MetaFieldId.valueOf(fieldId),
                                    valTypef,
                                    emData, Integer.valueOf(String.valueOf(mk)), dataPt
                            );
                            obj.addMod(mod);
                        });
                    } else {
                        valType = getValType(v, dataTypeStr);
                        DataType data = getObjModData(v, dataTypeStr);
                        int level = 0;
                        if (dataPt > 0) {
                            level = 1;
                        }
                        ObjMod.Obj.ExtendedMod mod = new ObjMod.Obj.ExtendedMod(
                                MetaFieldId.valueOf(fieldId),
                                valType,
                                data, level, dataPt
                        );
                        obj.addMod(mod);
                    }


                } else {
                    ObjMod.ValType valType = getValType(v, dataTypeStr);
                    DataType data = getObjModData(v, dataTypeStr);
                    ObjMod.Obj.Mod mod = new ObjMod.Obj.Mod(
                            MetaFieldId.valueOf(fieldId),
                            valType,
                            data
                    );
                    obj.addMod(mod);

                }


            } catch (Exception e) {
                System.err.println(k + "->" + v);
                e.printStackTrace();
            }

        });
    }


    /**
     *
     */
    public static DataType getObjModData(Object v, String dataTypeStr) {
        DataType data = null;
//        ObjMod.ValType valType = ObjMod.ValType.STRING;
        if (v instanceof Integer || "int".equals(dataTypeStr)) {
            double idv = Double.valueOf(String.valueOf(v));
            data = War3Int.valueOf((int) idv);
//            valType = ObjMod.ValType.INT;
        } else if ("unreal".equals(dataTypeStr)) {
            data = War3Real.valueOf(v);
//            valType = ObjMod.ValType.UNREAL;
        } else if ("real".equals(dataTypeStr) || v instanceof Double || v instanceof Float) {
            data = War3Real.valueOf(v);
//            valType = ObjMod.ValType.REAL;
        } else {
            data = War3String.valueOf(v);
        }
        return data;
    }

    public static ObjMod.ValType getValType(Object v, String dataTypeStr) {
        //
        ObjMod.ValType valType = null;
        if (v instanceof Integer || "int".equals(dataTypeStr)) {
//            double idv = Double.valueOf(String.valueOf(v));
            valType = ObjMod.ValType.INT;
        } else if ("unreal".equals(dataTypeStr)) {
            valType = ObjMod.ValType.UNREAL;
        } else if ("real".equals(dataTypeStr) || v instanceof Double || v instanceof Float) {
            valType = ObjMod.ValType.REAL;
        } else {
            valType = ObjMod.ValType.STRING;
        }
        return valType;
    }

    public static int getData(String fieldId) {
        Integer fieldIdData = MetaData.getData(fieldId);
        int dataPt = 0;
        if (fieldIdData != null) {
            dataPt = fieldIdData;
        }
        return dataPt;
    }


}
