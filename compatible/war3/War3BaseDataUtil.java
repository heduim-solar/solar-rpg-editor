package org.solar.editor.core.compatible.war3;

import net.moonlightflower.wc3libs.misc.FieldId;
import net.moonlightflower.wc3libs.misc.ObjId;
import net.moonlightflower.wc3libs.slk.RawSLK;
import net.moonlightflower.wc3libs.slk.SLK;
import net.moonlightflower.wc3libs.txt.TXT;
import net.moonlightflower.wc3libs.txt.TXTSectionId;
import org.solar.cache.Cache;
import org.solar.cache.CacheImpl;
import org.solar.lang.StringUtil;
import org.solar.editor.core.Config;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class War3BaseDataUtil {
    private static final Cache cache = new CacheImpl();

    public static void main(String[] args) {
        System.out.println(fieldId2field("Crs"));

    }


    public static SLK getMetaData_slk(String objType) {
        return cache.get("getMetaData_slk:" + objType, () -> {
            SLK slk = null;
            switch (objType) {
                case "w3u":
                    slk = new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/UnitMetaData.slk"));
                    break;
                case "w3t":
                    slk = new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/UnitMetaData.slk"));
                    break;
                case "w3b":
                    slk = new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/DestructableMetaData.slk"));
                    break;
                case "w3d":
                    slk = new RawSLK(new File(Config.filePath_war3_mpq_files + "Doodads/DoodadMetaData.slk"));
                    break;
                case "w3a":
                    slk = new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/AbilityMetaData.slk"));
                    break;
                case "w3h":
                    slk = new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/AbilityBuffMetaData.slk"));
                    break;
                case "w3q":
                    slk = new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/UpgradeMetaData.slk"));
                    slk.merge(new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/UpgradeEffectMetaData.slk")));
                    break;
            }
            return slk;
        });

    }

    public static SLK getAllMetaData_slk() {
        return cache.get("getAllMetaData_slk", () -> {
            SLK slk = new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/UnitMetaData.slk"));
            slk.merge(new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/MiscMetaData.slk")));
            slk.merge(new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/AbilityMetaData.slk")));
            slk.merge(new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/AbilityBuffMetaData.slk")));
            slk.merge(new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/DestructableMetaData.slk")));
            slk.merge(new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/UpgradeEffectMetaData.slk")));
            slk.merge(new RawSLK(new File(Config.filePath_war3_mpq_files + "Units/UpgradeMetaData.slk")));
            slk.merge(new RawSLK(new File(Config.filePath_war3_mpq_files + "Doodads/DoodadMetaData.slk")));
            return slk;
        });
    }

    public static TXT.Section getWorldEditStrings_txt_WorldEditStrings_Section() {
        return cache.get("getWorldEditStrings_txt_WorldEditStrings_Section", () -> {
            TXT txt = new TXT(new File(Config.filePath_war3_mpq_files + "UI/WorldEditStrings.txt"));
            TXT worldEditGameStringsTxt = new TXT(new File(Config.filePath_war3_mpq_files + "UI/WorldEditGameStrings.txt"));
            TXT.Section section = txt.getSection(TXTSectionId.valueOf("WorldEditStrings"));
            TXT.Section section2 = worldEditGameStringsTxt.getSection(TXTSectionId.valueOf("WorldEditStrings"));
            section.merge(section2,false);
            return section;
        });
    }

    public static String getMetaFieldId_DisplayName(String fieldId) {
        return cache.get("getMetaFieldId_DisplayName:" + fieldId, () -> {
            String displayName = getSlkVal(getAllMetaData_slk(), fieldId, "displayName");
            return getWorldEditStrings(displayName);
        });
    }

    /**
     * fieldId //unam 属性id
     * field //Name 属性
     *
     * @param fieldId
     * @return
     */
    public static final String _ABCDEFGHIJKLMNOPQ = "_ABCDEFGHIJKLMNOPQ";

    public static String fieldId2field(String fieldId) {
        if ("Ytip".equals(fieldId)) {
            return "YDWEtip";
        }
        String fieldIdf = fieldId.replace("\0", "");
        return cache.get("fieldId2field:" + fieldIdf, () -> {
            String field = getSlkVal(getAllMetaData_slk(), fieldIdf, "field");
            if ("Data".equals(field)) {
                String dataInt = getSlkVal(getAllMetaData_slk(), fieldIdf, "data");
                return "Data" + _ABCDEFGHIJKLMNOPQ.charAt(Integer.valueOf(dataInt));
            }
            if (StringUtil.isEmpty(field)) {
//                throw new RuntimeException("field is null ! fieldId=" + fieldIdf);
//                System.err.println("field is null ! fieldId=" + fieldIdf);
                //如果找不到field 则返回id
                return fieldId;
            }
            return field;
        });
    }

    public static String getDataTypeByFieldId(String fieldId) {
        return cache.get("getDataTypeByFieldId:" + fieldId, () -> {
            String type = getSlkVal(getAllMetaData_slk(), fieldId, "type");
            return type;
        });
    }

    public static String field2fieldId(String objType, String field, String objBaseId) {
        if ("w3a".equals(objType) && field.startsWith("Data")) {
            String fieldId = objBaseId.replace("\0", "");
            fieldId = fieldId.substring(fieldId.length() - 3);
            char indexChar = field.charAt(4);
            for (int i = 0; i < _ABCDEFGHIJKLMNOPQ.length(); i++) {
                if (indexChar == _ABCDEFGHIJKLMNOPQ.charAt(i)) {
                    fieldId = fieldId + i;
                    String dataStr = War3BaseDataUtil.getSlkVal(War3BaseDataUtil.getAllMetaData_slk(), fieldId, "data");
                    if (dataStr == null || "null".equals(dataStr)) {
                        return StringUtil.toUpperCaseFirstOne(fieldId);
                    }
                    return fieldId;
                }
            }
            System.err.println("fieldId is null ! field=" + field);

            return field;
//            throw new RuntimeException("fieldId is null ! field=" + field);
        }

        return cache.get("field2fieldId:" + objType + ":" + field, () -> {
            return getFieldAndFieldIdMap(objType).get(field);
        });
    }

    public static String getWorldEditStrings(String displayName) {
        return cache.get("getWorldEditStrings:" + displayName, () -> {
            TXT.Section section = getWorldEditStrings_txt_WorldEditStrings_Section();
            String newDisplayName = null;
            try {
                newDisplayName = String.valueOf(section.get(FieldId.valueOf(displayName)));
            } catch (TXT.Section.FieldDoesNotExistException e) {
            }
            if (newDisplayName == null) {
                return displayName;
            }
            return newDisplayName;
        });
    }

    public static String getSlkVal(SLK slk, String objId, String key) {
        SLK.Obj obj = slk.getObj(ObjId.valueOf(objId));
        if (obj == null) {
            return null;
        }
        String value = String.valueOf(obj.get(FieldId.valueOf(key)));
        return value;
    }

    public static Map<String, String> getFieldAndFieldIdMap(String objType) {
        return cache.get("getFieldAndFieldIdMap:" + objType, () -> {
//            System.out.println("objType="+objType);
            Map<ObjId, SLK.Obj> slkObjs = getMetaData_slk(objType).getObjs();
            Map<String, String> fieldAndFieldIdMap = new LinkedHashMap<>();
            FieldId fieldId = FieldId.valueOf("field");
            slkObjs.forEach((id, obj) -> {
                String value = String.valueOf(obj.get(fieldId));
                fieldAndFieldIdMap.put(value, String.valueOf(id));
            });
            return fieldAndFieldIdMap;
        });
    }


    /**
     *
     */


}
