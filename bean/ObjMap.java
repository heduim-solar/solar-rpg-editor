package org.solar.editor.core.bean;


import org.solar.war3.constant.War3BaseObjectData;
import org.solar.lang.SolarMap;
import org.solar.lang.StringUtil;

import java.math.BigDecimal;
import java.util.List;

public class ObjMap extends SolarMap {

    public String contentType = "DOTAUnits";
    public boolean Preprocess_removeQuotesWhenGet = true;
    public boolean Preprocess_KeyToLowerCase = false;


    //Override put 底层存储只存字符串 或者List

//    public Object put(Object key, Object value) {
//        if (Preprocess_KeyToLowerCase) {
//            key = key.toString().toLowerCase();
//        }
//        if (value == null) {
//            return super.put(key, value);
//        }
//        if (value instanceof List) {
//            return super.put(key, value);
//        }
//        //Double 保留3位小数
//        if (value instanceof Double) {
//            BigDecimal bigDecimal = new BigDecimal((Double) value);
//            bigDecimal = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP);
//            value = bigDecimal.toString();
//        }
//        return super.put(key, String.valueOf(value));
//    }


//    public Object get(Object key) {
//        if (Preprocess_KeyToLowerCase) {
//            key = key.toString().toLowerCase();
//        }
//        Object value = super.get(key);
//        if (value == null) {
//            String _parent = (String) super.get("_parent");
//            if (StringUtil.isEmpty(_parent)) {
//                return null;
//            }
//            _parent = _parent.replace("\"", "");
//            value = War3BaseObjectData.getW3oBaseData(_parent, key);
//        }
//        if (value == null) {
//            return null;
//        }
//        if (value instanceof List) {
//            return value;
//        }
//        String valueString = String.valueOf(value);
//        if (Preprocess_removeQuotesWhenGet && valueString != null && !valueString.startsWith("{")) {
//            value = StringUtil.remove(valueString, "\"");
//        }
//        return value;
//    }

    public String getString(Object key) {
        Object value = get(key);
        if (value==null){
            return null;
        }
        return String.valueOf(value);
    }

    public Integer getInteger(Object key) {
        Object value = get(key);
        if (StringUtil.isNotEmpty(value) && !"null".equals(value)) {
            String valueStr = StringUtil.remove(String.valueOf(value), "\"");
            if (StringUtil.isNotEmpty(valueStr) && valueStr.contains("{")) {
                valueStr = valueStr.split(",")[0];
                valueStr = StringUtil.remove(valueStr, "{", "}");
            }
            double numd = Double.valueOf(valueStr);//英雄提升属性为实数
            return (int) numd;
        }
        return null;
    }

    public Double getDouble(Object key) {
        Object value = get(key);
        if (StringUtil.isNotEmpty(value)) {
            String valueStr = StringUtil.remove(String.valueOf(value), "\"");
            if (valueStr.contains(",")) {
                valueStr = StringUtil.remove(valueStr, "{");
                valueStr = StringUtil.remove(valueStr, "}");
                valueStr = valueStr.split(",")[0];
            }
            return Double.valueOf(valueStr);
        }
        return null;
    }

    //temp 变量 程序内部做数据传输使用 不会输出到lni物编
    public Object setTempInt(Object value) {
        return super.put("tempInt", value);
    }

    public Integer getTempInt() {
        return (Integer) super.getOrDefault("tempInt", 0);
    }

    public Object setTempString(Object value) {
        return super.put("tempString", value);
    }

    public String getTempString() {

        return (String) super.getOrDefault("tempString", "");
    }

    public Object setID(Object value) {
        //sectionName 这里不用加[]
        return put("id", value);
//        return put("sectionName", value);
    }

    public String getID() {
//        String sectionName = getSectionName();
        return (String) get("id");
    }

    public Object setSectionName(Object value) {
        return setID(value);
    }

    public String getSectionName() {
        return (String) getID();
    }

    //missile
    public Object setAtkType1(Object value) {
        return put("atkType1", value);
    }

    public String getAtkType1() {
        return (String) get("atkType1");
    }

    public Object setRequires(Object value) {
        return put("Requires", value);
    }

    public String getRequires() {
        return (String) get("Requires");
    }

    //Requiresamount 需求值是字符串（数字列表）
    public Object setRequiresamount(String value) {
        if (StringUtil.isNotEmpty(value) && !value.startsWith("\"")) {
            value = "\"" + value + "\"";
        }
        return put("Requiresamount", value);
    }

    public String getRequiresamount() {
        return (String) get("Requiresamount");
    }

    public Object setResearchtip(Object value) {
        String str = String.valueOf(value);
        if (StringUtil.isNotEmpty(value) && str.startsWith("{") && str.endsWith("}")) {
            return null;
        }
        return put("Researchtip", value);
    }

    public String getResearchtip() {
        return (String) get("Researchtip");
    }

    public Object setResearchubertip(Object value) {
        String str = String.valueOf(value);
        if (StringUtil.isNotEmpty(value) && str.startsWith("{") && str.endsWith("}")) {
            return null;
        }
        return put("Researchubertip", value);
    }

    public String getResearchubertip() {
        return (String) get("Researchubertip");
    }

    public Object setHotkey(Object value) {
        return put("Hotkey", value);
    }

    public String getHotkey() {
        return (String) get("Hotkey");
    }

    public Object setEditorSuffix(Object value) {
        return put("EditorSuffix", value);
    }

    public String getEditorSuffix() {
        return (String) get("EditorSuffix");
    }

    //技能 施法时间
    public Object setCast(Object value) {
        return put("cast", value);
    }

    public Double getCast() {
        return getDouble("cast");
    }

    //单位 施法 前摇
    public Object setCastpt(Object value) {
        return put("castpt", value);
    }

    public Double getCastpt() {
        return getDouble("castpt");
    }

    //单位 施法 后摇 0.001
    public Object setCastbsw(Object value) {
        return put("castbsw", value);
    }

    public Double getCastbsw() {
        return getDouble("castbsw");
    }

    //
    public Object setCasterArt(Object value) {
        return put("CasterArt", value);
    }

    public String getCasterArt() {
        return (String) get("CasterArt");
    }

    //物品 CD 间隔组 （技能id）
    public Object setCooldownID(Object value) {
        return put("cooldownID", value);
    }

    public String getCooldownID() {
        return (String) get("cooldownID");
    }

    //物品分类
    public Object setclass(Object value) {
        return put("class", value);
    }

    public String getclass() {
        return (String) get("class");
    }

    //amph=两栖 fly foot
    public Object setMovetp(Object value) {
        return put("movetp", value);
    }

    public String getMovetp() {
        return (String) get("movetp");
    }

    //放置要求 unbuildable=可建造低面 blighted=非荒芜之地,unbuildable,unwalkable,unfloat,unflyable,unamph
    public Object setPreventPlace(Object value) {
        return put("preventPlace", value);
    }

    public String getPreventPlace() {
        return (String) get("preventPlace");
    }

    public Object setName(Object value) {
        return put("Name", value);
    }

    public String getName() {
        return (String) get("Name");
    }

    //魔法效果 的 名字
    public Object setEditorName(Object value) {
        return put("EditorName", value);
    }

    public String getEditorName() {
        return (String) get("EditorName");
    }

    //-- 效果 - 目标点 (技能 通魔)
    public Object setEffectArt(Object value) {
        return put("EffectArt", value);
    }

    public String getEffectArt() {
        return (String) get("EffectArt");
    }

    public Object set_parent(Object value) {
        return put("_parent", value);
    }

    public String get_parent() {
        String _parent = (String) get("_parent");
        if (_parent == null) {
            return "";
        }
        return _parent;
    }

    //建筑升级
    public Object setUpgrade(Object value) {
        return put("Upgrade", value);
    }

    public String getUpgrade() {
        return (String) get("Upgrade");
    }

    //使用科技
    public Object setUpgrades(Object value) {
        return put("upgrades", value);
    }

    public String getUpgrades() {
        return (String) get("upgrades");
    }

    //可建造 建筑物
    public Object setBuilds(Object value) {
        return put("Builds", value);
    }

    public String getBuilds() {
        return (String) get("Builds");
    }

    //技能 光环 状态 魔法效果
    public Object setBuffID(Object value) {
        return put("BuffID", value);
    }

    public String getBuffID() {
        return (String) get("BuffID");
    }

    //buff 状态栏显示的图标
    public Object setBuffart(Object value) {
        return put("Buffart", value);
    }

    public String getBuffart() {
        return (String) get("Buffart");
    }

    //buff 状态栏显示的提示 名字
    public Object setBufftip(Object value) {
        return put("Bufftip", value);
    }

    public String getBufftip() {
        return (String) get("Bufftip");
    }

    //buff 状态栏显示的提示 说明
    public Object setBuffubertip(Object value) {
        return put("Buffubertip", value);
    }

    public String getBuffubertip() {
        return (String) get("Buffubertip");
    }

    public Object setDescription(Object value) {
        return put("Description", value);
    }

    public String getDescription() {
        return (String) get("Description");
    }

    //英雄 主要属性 力量/敏捷/智力 STR/AGI/INT
    public Object setPrimary(Object value) {
        return put("Primary", value);
    }

    public String getPrimary() {
        return (String) get("Primary");
    }

    //英雄称谓
    public Object setPropernames(Object value) {
        return put("Propernames", value);
    }

    public String getPropernames() {
        return (String) get("Propernames");
    }

    public Object setTip(Object value) {
        if (get_parent().equals("ANcl") || get_parent().equals("AHab") || get_parent().equals("Asth")) {
            setResearchtip(value);
        }
        return put("Tip", value);
    }

    public String getTip() {
        return (String) get("Tip");
    }

    //关闭提示
    public Object setUntip(Object value) {
        return put("Untip", value);
    }

    public String getUntip() {
        return (String) get("Untip");
    }

    //关闭提示扩展
    public Object setUnubertip(Object value) {
        return put("Unubertip", value);
    }

    public String getUnubertip() {
        return (String) get("Unubertip");
    }

    public Object setAwakentip(Object value) {
        return put("Awakentip", value);
    }

    public String getAwakentip() {
        return (String) get("Awakentip");
    }

    public Object setRevivetip(Object value) {
        return put("Revivetip", value);
    }

    public String getRevivetip() {
        return (String) get("Revivetip");
    }

    //类别 peon=工人 mechanical,standon=机械类 可通行
    public Object setType(Object value) {
        return put("type", value);
    }

    public String getType() {
        return (String) get("type");
    }

    //作为目标类型  ground,mechanical = 地面 机械类
    public Object setTargType(Object value) {
        return put("targType", value);
    }

    public String getTargType() {
        return (String) get("targType");
    }

    //目标允许 （技能）
    public Object setTargs(Object value) {
        return put("targs", value);
    }

    public String getTargs() {
        return (String) get("targs");
    }

    //目标1允许 （单位有2个攻击目标允许）
    public Object setTargs1(Object value) {
        return put("targs1", value);
    }

    public String getTargs1() {
        return (String) get("targs1");
    }

    //训练单位
    public Object setTrains(Object value) {
        return put("Trains", value);
    }

    public String getTrains() {
        return (String) get("Trains");
    }

    //
    public Object setTargetArt(Object value) {
        return put("TargetArt", value);
    }

    public String getTargetArt() {
        return (String) get("TargetArt");
    }

    //目标附近点
    //weapon head 头部    overhead 头顶    chest 胸部    origin 脚底
    public Object setTargetattach(Object value) {
        return put("Targetattach", value);
    }

    public String getTargetattach() {
        return (String) get("Targetattach");
    }

    public Object setUnitID(Object value) {
        return put("UnitID", value);
    }

    public String getUnitID() {
        return (String) get("UnitID");
    }

    //武器类型1 msplash=溅射 instant=立即
    public Object setWeapTp1(Object value) {
        return put("weapTp1", value);
    }

    public String getWeapTp1() {
        return (String) get("weapTp1");
    }

    //全伤害范围 溅射 弹射 范围
    public Object setFarea1(Object value) {
        return put("Farea1", value);
    }

    public Integer getFarea1() {
        return getInteger("Farea1");
    }

    public Object setDefType(Object value) {
        return put("defType", value);
    }

    public String getDefType() {
        return (String) get("defType");
    }

    public Object setUbertip(Object value) {
        if (get_parent().equals("ANcl") || get_parent().equals("AHab") || get_parent().equals("Asth")) {
            setResearchubertip(value);
        }
        return put("Ubertip", value);
    }

    public String getUbertip() {
        return (String) get("Ubertip");
    }

    public Object setArt(String value) {
        if (value.contains("\\") && !value.contains("\\\\")) {
            value = value.replace("\\", "\\\\");
        }
        if (containsKey("_parent")) {
            if (get_parent().equals("ANcl") || get_parent().equals("AHab") || get_parent().equals("Asth")) {
                setResearchArt(value);
            }
        }
        return put("Art", value);
    }

    public String getArt() {
        return (String) get("Art");
    }

    public Object setResearchArt(Object value) {
        return put("ResearchArt", value);
    }

    public String getResearchArt() {
        return (String) get("ResearchArt");
    }

    //关闭按钮 图标
    public Object setUnart(Object value) {
        return put("Unart", value);
    }

    public String getUnart() {
        return (String) get("Unart");
    }


    // 攻击 1 - 投射物图像
    public Object setMissileart_1(Object value) {
        return put("Missileart_1", value);
    }

    public String getMissileart_1() {
        return (String) get("Missileart_1");
    }

    //投射物影像
    public Object setMissileart(Object value) {
        return put("Missileart", value);
    }

    public String getMissileart() {
        return (String) get("Missileart");
    }

    public Object setScoreScreenIcon(Object value) {
        return put("ScoreScreenIcon", value);
    }

    public String getScoreScreenIcon() {
        return (String) get("ScoreScreenIcon");
    }

    //单位 模型
    public Object setFile(Object val) {
        String value = String.valueOf(val);
        if (value.contains("\\") && !value.contains("\\\\")) {
            value = value.replace("\\", "\\\\");
        }
        if (!value.contains(".")) {
            value = value + ".mdx";
        }
        return put("file", value);
    }

    public String getFile() {
        return (String) get("file");
    }

    //样式总数
    public Object setNumVar(Object value) {
        return put("numVar", value);
    }

    public Integer getNumVar() {
        return getInteger("numVar");
    }

    //替换纹理文件id
    public Object setTexID(Object value) {
        return put("texID", value);
    }

    public Integer getTexID() {
        return getInteger("texID");
    }

    //替换纹理文件
    public Object setTexFile(Object value) {
        return put("texFile", value);
    }

    public String getTexFile() {
        return (String) get("texFile");
    }

    //单位 要求动画名
    public Object setAnimProps(Object value) {
        return put("animProps", value);
    }

    public String getAnimProps() {
        return (String) get("animProps");
    }

    //单位 -- 建筑地面纹理 ""=没有
    public Object setUberSplat(Object value) {
        return put("uberSplat", value);
    }

    public String getUberSplat() {
        return (String) get("uberSplat");
    }

    //单位 路径纹理
    public Object setPathTex(Object value) {
        return put("pathTex", value);
    }

    public String getPathTex() {
        return (String) get("pathTex");
    }

    //种族 human
    public Object setRace(Object value) {
        return put("race", value);
    }

    public String getRace() {
        return (String) get("race");
    }


    //单位声音
    public Object setUnitSound(Object value) {
        return put("unitSound", value);
    }

    public String getUnitSound() {
        return (String) get("unitSound");
    }

    //出售单位
    public Object setSellunits(Object value) {
        return put("Sellunits", value);
    }

    public String getSellunits() {
        return (String) get("Sellunits");
    }

    //出售物品
    public Object setSellitems(Object value) {
        return put("Sellitems", value);
    }

    public String getSellitems() {
        return (String) get("Sellitems");
    }

    //制造物品
    public Object setMakeitems(Object value) {
        return put("Makeitems", value);
    }

    public String getMakeitems() {
        return (String) get("Makeitems");
    }

    //单位阴影 ""=没有
    public Object setUnitShadow(Object value) {
        return put("unitShadow", value);
    }

    public String getUnitShadow() {
        return (String) get("unitShadow");
    }

    //建筑阴影 ""=没有
    public Object setBuildingShadow(Object value) {
        return put("buildingShadow", value);
    }

    public String getBuildingShadow() {
        return (String) get("buildingShadow");
    }

    public Object setShadowOnWater(Object value) {
        return put("shadowOnWater", value);
    }

    public Integer getShadowOnWater() {
        return getInteger("shadowOnWater");
    }

    public Object setHideOnMinimap(Object value) {
        return put("hideOnMinimap", value);
    }

    public Integer getHideOnMinimap() {
        return getInteger("hideOnMinimap");
    }

    public Object setHeroAbilList(Object value) {
        return put("heroAbilList", value);
    }

    public String getHeroAbilList() {
        return (String) get("heroAbilList");
    }


    //物品的主动技能要放到第一个 不然不会有冷却显示
    public Object setAbilList(Object value) {
        return put("abilList", value);
    }

    public String getAbilList() {
        return (String) get("abilList");
    }

    public Object setGoldcost(Object value) {
        put("goldRep", value);//???? item 未测试 todo
        return put("goldcost", value);
    }

    public Integer getGoldcost() {
        return getInteger("goldcost");
    }

    //维修需要的金币
    public Object setGoldRep(Object value) {
        return put("goldRep", value);
    }

    public Integer getGoldRep() {
        return getInteger("goldRep");
    }

    //0||1 是否是一个建筑
    public Object setIsbldg(Object value) {
        return put("isbldg", value);
    }

    public Integer getIsbldg() {
        return getInteger("isbldg");
    }

    //建造时间
    public Object setBldtm(Object value) {
        put("reptm ", value);//修理时间
        return put("bldtm", value);
    }

    public Integer getBldtm() {
        return getInteger("bldtm");
    }


    //0||1 可以被丢弃 （物品）
    public Object setDroppable(Object value) {
        return put("droppable", value);
    }

    public Integer getDroppable() {
        return getInteger("droppable");
    }

    //0||1 可以被抵押 （物品）
    public Object setPawnable(Object value) {
        return put("pawnable", value);
    }

    public Integer getPawnable() {
        return getInteger("pawnable");
    }

    //技能 -- 按钮位置 - 普通 (X)0-3 0 -11可以隐藏图标
    public Object setButtonpos0_11() {
        setButtonpos_1(0);
        return setButtonpos_2(-11);
    }

    public Object setButtonpos_1(Object value) {
        return put("Buttonpos_1", value);
    }

    public Integer getButtonpos_1() {
        return getInteger("Buttonpos_1");
    }

    //技能 -- 按钮位置 - 普通 (Y)0-2
    public Object setButtonpos_2(Object value) {
        return put("Buttonpos_2", value);
    }

    public Integer getButtonpos_2() {
        return getInteger("Buttonpos_2");
    }

    public Object setFmade(Object value) {
        return put("fmade", value);
    }

    public Integer getFmade() {
        return getInteger("fmade");
    }

    public Object setLumbercost(Object value) {
        put("lumberRep", value);
        return put("lumbercost", value);
    }

    public Integer getLumbercost() {
        return getInteger("lumbercost");
    }


    public Object setDmgplus1(Integer value) {
        return put("dmgplus1", value);
    }

    //    //  -- 攻击 1 - 基础伤害;
    public Integer getDmgplus1() {
        String value = (String) get("dmgplus1");
        if (StringUtil.isNotEmpty(value)) {
            value = StringUtil.remove(value, "\"");
            return Integer.valueOf(value);
        }
        return null;
    }

    //    //  -- 攻击 1 伤害升级奖励
    public Object setDmgUp1(Integer value) {
        return put("dmgUp1", value);
    }

    public Integer getDmgUp1() {
        return getInteger("dmgUp1");
    }

    //   攻击 1 弹射 最大目标数
    public Object setTargCount1(Integer value) {
        return put("targCount1", value);
    }

    public Integer getTargCount1() {
        return getInteger("targCount1");
    }

    // -- 攻击 1 伤害骰子面数
    public Object setSides1(Integer value) {
        return put("sides1", value);
    }

    public Integer getSides1() {
        return getInteger("sides1");
    }


    //  -- 碰撞体积
    public Object setCollision(Object value) {
        return put("collision", value);
    }

    public Double getCollision() {
        return getDouble("collision");
    }

    public Object setBlend(Object value) {
        return put("blend", value);
    }

    //  -- 动画 - 混合时间(秒)
    public Double getBlend() {
        return getDouble("blend");
    }

    //  -- 主动攻击范围
    public Object setAcquire(Object value) {
        return put("acquire", value);
    }

    public Double getAcquire() {
        return getDouble("acquire");
    }

    public Object setRangeN1(Integer value) {
        if (value < 300) {
            setAcquire(500);
        } else {
            setAcquire(value * 2);
        }
        return put("rangeN1", value);
    }

    //  -- 攻击 1 -攻击范围;
    public Integer getRangeN1() {
        return getInteger("rangeN1");
    }

    //  --施法距离
    public Object setRng(Object value) {
        return put("Rng", value);
    }

    //  --施法距离
    public Double getRng() {
        return getDouble("Rng");
    }

    public Object setHP(Integer value) {
        if (value < 10) {

//            System.err.println("生命值过低 会造成无法建造等bug setHP=" + value + " auto set 10");
//            System.err.print("HP=" + value + " auto set 10");
            value = 10;
        }
        return put("HP", value);
    }

    public Integer getHP() {
        return getInteger("HP");
    }

    //生命回复
    public Object setRegenHP(Integer value) {
        return put("regenHP", value);
    }

    public Integer getRegenHP() {
        return getInteger("regenHP");
    }

    //生命回复类型 none=无 always=总是
    public Object setRegenType(String value) {
        return put("regenType", value);
    }

    public String getRegenType() {
        return (String) get("regenType");
    }

    //护甲
    public Object setDef(Integer value) {
        return put("def", value);
    }

    public Double getDef() {
        return getDouble("def");
    }

    //死亡类型 0-3 0=无法召唤，不会腐化
    public Object setDeathType(Integer value) {
        return put("deathType", value);
    }

    public Integer getDeathType() {
        return getInteger("deathType");
    }

    public Object setDefUp(Integer value) {
        return put("defUp", value);
    }

    public Double getDefUp() {
        return getDouble("defUp");
    }

    //设置 持续时间
    public Object setDur(Object value) {
        return put("Dur", value);
    }

    public Double getDur() {
        return getDouble("Dur");
    }

    //设置 持续时间
    public Object setHeroDur(Object value) {
        return put("HeroDur", value);
    }

    public Double getHeroDur() {
        return getDouble("HeroDur");
    }

    //   攻击1间隔 攻击间隔
    public Object setCool1(Object value) {
        return put("cool1", value);
    }

    public Double getCool1() {
        return getDouble("cool1");
    }

    //   技能 魔法CD
    public Object setCool(Object value) {
        return put("cool", value);
    }

    public Double getCool() {
        return getDouble("cool");
    }

    //   魔法消耗
    public Object setCost(Object value) {
        return put("Cost", value);
    }

    public Integer getCost() {
        return getInteger("Cost");
    }

    //   魔法初始数量
    public Object setMana0(Object value) {
        return put("mana0", value);
    }

    public Integer getMana0() {
        return getInteger("mana0");
    }

    //   魔法最大值
    public Object setManaN(int value) {
        setMana0(value / 2);
        setRegenMana(1 + value / 120);
        return put("manaN", value);
    }

    public Object setManaN(Object value) {
        return put("manaN", value);
    }

    public Integer getManaN() {
        return getInteger("manaN");
    }

    //   魔法回复数量
    public Object setRegenMana(Object value) {
        return put("regenMana", value);
    }

    public Double getRegenMana() {

        return getDouble("regenMana");
    }


    //-- 允许攻击模式 0=无
    public Object setWeapsOn(Object value) {
        return put("weapsOn", value);
    }

    public Integer getWeapsOn() {
        return getInteger("weapsOn");
    }

    //占用人口
    public Object setFused(Integer value) {
        return put("fused", value);
    }

    public Integer getFused() {
        return getInteger("fused");
    }

    //maxlevel 科技的最大等级
    public Object setMaxlevel(Integer value) {
        return put("maxlevel", value);
    }

    public Double getMaxlevel() {
        return getDouble("maxlevel");
    }

    public Object setMoveHeight(Integer value) {
        return put("moveHeight", value);
    }

    public Double getMoveHeight() {
        return getDouble("moveHeight");
    }


    public Object setDataA(Object value) {
        return put("DataA", value);
    }

    public Double getDataA() {
        return getDouble("DataA");
    }

    public Object setDataB(Object value) {
        return put("DataB", value);
    }

    public Double getDataB() {
        return getDouble("DataB");
    }

    public Object setDataC(Object value) {
        return put("DataC", value);
    }

    public Double getDataC() {
        return getDouble("DataC");
    }

    public Object setDataD(Object value) {
        return put("DataD", value);
    }

    public Double getDataD() {
        return getDouble("DataD");
    }

    public Object setDataE(Object value) {
        return put("DataE", value);
    }

    public Double getDataE() {
        return getDouble("DataE");
    }

    //通魔的 命令字符串
    public Object setDataF(Object value) {
        return put("DataF", value);
    }

    public String getDataF() {
        return (String) get("DataF");
    }

    //魔法吸吮的生命吸取参数
    public Object setDataG(Object value) {
        return put("DataG", value);
    }

    public Double getDataG() {
        return getDouble("DataG");
    }

    //光环 影响区域 范围
    public Object setArea(Object value) {
        return put("Area", value);
    }

    public Double getArea() {
        return getDouble("Area");
    }


    //可研究项目
    public Object setResearches(Object value) {
        return put("Researches", value);
    }

    public String getResearches() {
        return (String) get("Researches");
    }


    public Object setDataA1(Integer value) {
        return put("DataA1", value);
    }

    //    //  -- 攻击 1 - 基础伤害;
    public Double getDataA1() {
        return getDouble("DataA1");
    }


    //  -- 黄金奖励-基础值
    public Object setBountyplus(Object value) {
        return put("bountyplus", value);
    }

    public Integer getBountyplus() {
        return getInteger("bountyplus");
    }

    //  -- 黄金奖励-骰子面数
    public Object setBountysides(Object value) {
        return put("bountysides", value);
    }

    public Integer getBountysides() {
        return getInteger("bountysides");
    }

    //  -- 黄金奖励-骰子数量
    public Object setBountydice(Object value) {
        return put("bountydice", value);
    }

    public Integer getBountydice() {
        return getInteger("bountydice");
    }


    //  -- 是否是英雄技能 1=true 0=false
    public Integer getHero() {
        return getInteger("hero");
    }

    public Object setHero(Object value) {
        return put("hero", value);
    }

    //  -- 是否是物品技能 1=true 0=false
    public Integer getItem() {
        return getInteger("item");
    }

    public Object setItem(Object value) {
        return put("item", value);
    }

    //  -- 技能等级
    public Integer getLevels() {
        return getInteger("levels");
    }

    public Object setLevels(Object value) {
        return put("levels", value);
    }

    //  -- 单位等级
    public Integer getlevel() {
        return getInteger("level");
    }

    public Object setlevel(Object value) {
        return put("level", value);
    }

    //  -- 物品等级
    public Integer getLevel() {
        return getInteger("Level");
    }

    public Object setLevel(Object value) {
        return put("Level", value);
    }

    //  -- 技能学习 要求等级
    public Integer getReqLevel() {
        return getInteger("reqLevel");
    }

    public Object setReqLevel(Object value) {
        return put("reqLevel", value);
    }

    //  -- 物品 优先权
    public Integer getPrio() {
        return getInteger("prio");
    }

    public Object setPrio(Object value) {
        return put("prio", value);
    }

    //  -- 单位模型颜色
    public Integer getRed() {
        return getInteger("red");
    }

    public Object setRed(Object value) {
        return put("red", value);
    }

    public Integer getGreen() {
        return getInteger("green");
    }

    public Object setGreen(Object value) {
        return put("green", value);
    }

    public Integer getBlue() {
        return getInteger("blue");
    }

    public Object setBlue(Object value) {
        return put("blue", value);
    }


    //  -- 物品模型颜色
    public Integer getColorR() {
        return getInteger("colorR");
    }

    public Object setColorR(Object value) {
        return put("colorR", value);
    }

    public Integer getColorG() {
        return getInteger("colorG");
    }

    public Object setColorG(Object value) {
        return put("colorG", value);
    }

    public Integer getColorB() {
        return getInteger("colorB");
    }

    public Object setColorB(Object value) {
        return put("colorB", value);
    }

    //购买开始时间
    public Integer getStockStart() {
        return getInteger("stockStart");
    }

    public Object setStockStart(Object value) {
        return put("stockStart", value);
    }

    //购买间隔
    public Integer getStockRegen() {
        return getInteger("stockRegen");
    }

    public Object setStockRegen(Object value) {
        return put("stockRegen", value);
    }

    //最大库存量
    public Integer getStockMax() {
        return getInteger("stockMax");
    }

    public Object setStockMax(Object value) {
        return put("stockMax", value);
    }


    //0 1 是否可以使用
    public Integer getUsable() {
        return getInteger("usable");
    }

    public Object setUsable(Object value) {
        return put("usable", value);
    }

    //使用次数 0=无限
    public Integer getUses() {
        return getInteger("uses");
    }

    public Object setUses(Object value) {
        return put("uses", value);
    }

    //使用完会消失
    public Integer getPerishable() {
        return getInteger("perishable");
    }

    public Object setPerishable(Object value) {
        return put("perishable", value);
    }

    //基础速度
    public Integer getSpd() {
        return getInteger("spd");
    }

    public Object setSpd(Object value) {
        return put("spd", value);
    }

    //单位最小速度 0=使用默认最小速度（高级-游戏平衡性参数设置里的速度）
    public Integer getMinSpd() {
        return getInteger("minSpd");
    }

    public Object setMinSpd(Object value) {
        return put("minSpd", value);
    }

    //射弹速度
    public Integer getMissilespeed() {
        return getInteger("Missilespeed");
    }

    public Object setMissilespeed(Object value) {
        return put("Missilespeed", value);
    }

    //射弹弧度
    public Double getMissilearc() {
        return getDouble("Missilearc");
    }

    public Object setMissilearc(Object value) {
        return put("Missilearc", value);
    }

    //英雄 - 初始敏捷
    public Integer getAGI() {
        return getInteger("AGI");
    }

    public Object setAGI(Object value) {
        return put("AGI", value);
    }

    //英雄 - 初始智力
    public Integer getINT() {
        return getInteger("INT");
    }

    public Object setINT(Object value) {
        return put("INT", value);
    }

    //英雄 - 初始力量
    public Integer getSTR() {
        return getInteger("STR");
    }

    public Object setSTR(Object value) {
        return put("STR", value);
    }

    //-- 英雄 - 每等级提升力量
    public Integer getSTRplus() {
        return getInteger("STRplus");
    }

    public Object setSTRplus(Object value) {
        return put("STRplus", value);
    }

    //-- 英雄 - 每等级提升敏捷
    public Object setAGIplus(Object value) {
        return put("AGIplus", value);
    }

    public Integer getAGIplus() {
        return getInteger("AGIplus");
    }


    //-- 英雄 - 每等级提升智力
    public Object setINTplus(Object value) {
        return put("INTplus", value);
    }

    public Integer getINTplus() {
        return getInteger("INTplus");
    }


    public Double getNsight() {
        return getDouble("nsight");
    }

    public Object setNsight(Object value) {
        return put("nsight", value);
    }

    public Double getSight() {
        return getDouble("sight");
    }

    public Object setSight(Object value) {
        return put("sight", value);
    }

    //默认比例 装饰物的默认比例
    public Double getDefScale() {
        return getDouble("defScale");
    }

    public Object setDefScale(Object value) {
        return put("defScale", value);
    }

    //缩放/ 单位的选择缩放 物品的模型缩放
    public Double getScale() {
        return getDouble("scale");
    }

    public Object setScale(Object value) {
        return put("scale", value);
    }

    //单位模型缩放
    public Double getModelScale() {
        return getDouble("modelScale");
    }

    public Object setModelScale(Object value) {
        return put("modelScale", value);
    }

    //-- 最小高度
    public Double getMoveFloor() {
        return getDouble("moveFloor");
    }

    public Object setMoveFloor(Object value) {
        return put("moveFloor", value);
    }

    //-- 转身速度
    public Double getTurnRate() {
        return getDouble("turnRate");
    }

    public Object setTurnRate(Object value) {
        return put("turnRate", value);
    }

    //-- 动画 - 转向补正
    public Double getOrientInterp() {
        return getDouble("orientInterp");
    }

    public Object setOrientInterp(Object value) {
        return put("orientInterp", value);
    }

    //-- 动画 - 转向角度
    public Double getPropWin() {
        return getDouble("propWin");
    }

    public Object setPropWin(Object value) {
        return put("propWin", value);
    }

    //-- 物品-捡取时自动使用 0/1
    public Integer getPowerup() {
        return getInteger("powerup");
    }

    public Object setPowerup(Object value) {
        return put("powerup", value);
    }

    /**
     * 科技专属字段
     */
    //-- 科技基础黄金消耗
    public Integer getGoldbase() {
        return getInteger("goldbase");
    }

    public Object setGoldbase(Object value) {
        return put("goldbase", value);
    }

    //-- 科技基础木材消耗
    public Integer getLumberbase() {
        return getInteger("lumberbase");
    }

    public Object setLumberbase(Object value) {
        return put("lumberbase", value);
    }

    //-- 科技基础时间消耗
    public Integer getTimebase() {
        return getInteger("timebase");
    }

    public Object setTimebase(Object value) {
        return put("timebase", value);
    }

    //-- 科技每级增加黄金消耗
    public Integer getGoldmod() {
        return getInteger("goldmod");
    }

    public Object setGoldmod(Object value) {
        return put("goldmod", value);
    }

    //-- 科技每级增加木材消耗
    public Integer getLumbermod() {
        return getInteger("lumbermod");
    }

    public Object setLumbermod(Object value) {
        return put("lumbermod", value);
    }

    //-- 科技每级增加时间消耗
    public Integer getTimemod() {
        return getInteger("timemod");
    }

    public Object setTimemod(Object value) {
        return put("timemod", value);
    }

    /**
     * 不常用
     */
    //-- 英雄 - 隐藏英雄栏图标
    public Integer getHideHeroBar() {

        return getInteger("hideHeroBar");
    }

    //
    public Object setHideHeroBar(Object value) {

        return put("hideHeroBar", value);
    }

    //-- 英雄 - 隐藏英雄死亡信息
    public Integer getHideHeroDeathMsg() {

        return getInteger("hideHeroDeathMsg");
    }

    public Object setHideHeroDeathMsg(Object value) {

        return put("hideHeroDeathMsg", value);
    }

    //-- 英雄 - 隐藏小地图英雄显示 0/1
    public Integer getHideHeroMinimap() {
        return getInteger("hideHeroMinimap");
    }

    public Object setHideHeroMinimap(Object value) {
        return put("hideHeroMinimap", value);
    }

    //-- 默认为1 可在编辑器放置   0/1
    public Integer getInEditor() {

        return getInteger("inEditor");
    }

    public Object setInEditor(Object value) {

        return put("inEditor", value);
    }
    //-- 默认为1 可在设置死亡掉落物品属性   0/1

    public Object setDropItems(Integer value) {
        return put("dropItems", value);
    }

    public Integer getDropItems() {
        return getInteger("dropItems");
    }

}
