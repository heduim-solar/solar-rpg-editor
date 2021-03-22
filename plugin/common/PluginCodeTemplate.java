package org.solar.editor.plugin.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class PluginCodeTemplate {

    public static final Map keyTemplateMap = new LinkedHashMap();

    //one case
    static {
        keyTemplateMap.put("空白", "//欢迎使用插件编辑器 QQ群:941442872");
    }

    //one case
    static {
        keyTemplateMap.put("基础模板",

                "\n" +
                "//设置输入变量\n" +
                "vars = new SolarMap()\n" +
                "vars.太阳编辑器QQ群 = \"941442872\"\n" +
                "\n" +
                "onClick = {\n" +
                "\n" +
                "\n" +
                "    SEPUtil.importPluginAllGameScript(this)\n" +
                "    \n" +
                "}\n" +
                "\n" +
                "\n" +
                "SE.showDialog(sep.name, vars, onClick,\"\"\"\n" +
                "\n" +
                "\"\"\");\n" +
                "\n" +
                "\n");

    }


//    //one case
//    static {
//        keyTemplateMap.put("ECS系统模板", "import heduim.fastwar.core.plugin.SWE\n" +
//                "import org.solar.lang.SolarMap\n" +
//                "import org.solar.war3.bean.Trigger\n" +
//                "\n" +
//                "//设置输入变量\n" +
//                "                vars = new SolarMap()\n" +
//                "        vars.单位id = SWE.getCheckedWar3ObjectIds()\n" +
//                "//\n" +
//                "        SWE.addUnitSelectMenu(vars, \"单位id\")\n" +
//                "\n" +
//                "        onClick = {\n" +
//                "                //如果地图没有此ecs系统则导入ecs系统触发\n" +
//                "                Trigger ecs_system = SWE.importTriggerIfNotExist(this, \"ecs_system_xxx\")\n" +
//                "        if (ecs_system) {\n" +
//                "            ecs_system.setCategory(\"ecs_system\")\n" +
//                "        } else {\n" +
//                "            println \"地图内已存在此系统触发！\"\n" +
//                "        }\n" +
//                "        //如果组件数据为空则只导入系统\n" +
//                "        if (!vars.单位id) {\n" +
//                "            return\n" +
//                "        }\n" +
//                "\n" +
//                "        //生成组件\n" +
//                "        //item_ids = SWE.getIdArray(vars.物品ids)\n" +
//                "        Trigger ecs_component = SWE.trigger(binding.getVariables(), new File(sep.dir + \"ecs_component_xxx\"))\n" +
//                "        ecs_component.setCategory(\"ecs_component\")\n" +
//                "        ecs_component.setTriggerName(ecs_component.getTriggerName() + \"_\" + SWE.shortUUID())\n" +
//                "\n" +
//                "}\n" +
//                "\n" +
//                "        SWE.showDialog(sep.name, vars, onClick, \"\"\"\n" +
//                "\n" +
//                "                \"\"\");\n");
//
//
//    }
//
//    //one case
//    static {
//        keyTemplateMap.put("物体编辑模板", "import heduim.fastwar.core.plugin.SWE\n" +
//                "import org.solar.lang.SolarMap\n" +
//                "\n" +
//                "//设置输入变量\n" +
//                "                vars = new SolarMap()\n" +
//                "        vars.单位ids = SWE.getCheckedWar3ObjectIds()\n" +
//                "        SWE.addUnitSelectMenu(vars, \"单位ids\")\n" +
//                "\n" +
//                "        onClick = {\n" +
//                "\n" +
//                "\n" +
//                "        }\n" +
//                "\n" +
//                "\n" +
//                "        if (vars.单位ids) {\n" +
//                "            onClick.run()\n" +
//                "            SWE.showDialogTip(sep.name + \"-执行完毕\");\n" +
//                "        } else {\n" +
//                "            SWE.showDialog(sep.name, vars, onClick);\n" +
//                "        }\n");
//
//
//    }
//
//    //one case
//    static {
//        keyTemplateMap.put("主动技能库模板", "import heduim.fastwar.core.bean.PropertySet\n" +
//                "import heduim.fastwar.core.plugin.SWE\n" +
//                "import heduim.fastwar.core.service.code_generator.AbilityService\n" +
//                "import heduim.fastwar.core.service.plugin.AbilityImportService\n" +
//                "import org.solar.lang.SolarMap\n" +
//                "import org.solar.lang.StringUtil\n" +
//                "\n" +
//                "//设置输入变量\n" +
//                "                vars = new SolarMap()\n" +
//                "        vars.名字 = sep.name\n" +
//                "        vars.图标 = new File(sep.dir + \"sep.png\")\n" +
//                "        vars.快捷键 = \"Q\"\n" +
//                "        vars.伤害附加系数 = 0.5\n" +
//                "        vars.伤害附加力量 = true\n" +
//                "        vars.伤害附加敏捷 = true\n" +
//                "        vars.伤害附加智力 = true\n" +
//                "        vars.伤害附加生命值 = false\n" +
//                "        vars.冷却时间 = 15\n" +
//                "        SWE.addMenuData(vars, \"快捷键\",[\n" +
//                "                \"Q\":{\n" +
//                "            vars.快捷键 = \"Q\"\n" +
//                "        },\n" +
//                "        \"W\":{\n" +
//                "            vars.快捷键 = \"W\"\n" +
//                "        },\n" +
//                "        \"E\":{\n" +
//                "            vars.快捷键 = \"E\"\n" +
//                "        },\n" +
//                "        \"R\":{\n" +
//                "            vars.快捷键 = \"R\"\n" +
//                "        },\n" +
//                "])\n" +
//                "\n" +
//                "        onClick = {\n" +
//                "                //伤害设置\n" +
//                "                ability_number = vars.伤害附加系数\n" +
//                "                propertySet = new PropertySet(vars.伤害附加力量, vars.伤害附加敏捷,\n" +
//                "                        vars.伤害附加智力, vars.伤害附加生命值, )\n" +
//                "                //生成技能\n" +
//                "                ability = AbilityService.active_noTarget(vars.名字 + \"(${vars.快捷键})\")\n" +
//                "                ability.setHotkey(vars.快捷键)\n" +
//                "                ability.setCost(50)\n" +
//                "                ability.setCool(vars.冷却时间)\n" +
//                "                ability.setArt(SWE.icon(vars.图标.getAbsolutePath()))\n" +
//                "                def tip = \"\"\"\n" +
//                "                |cff99ccff伤害公式 ${propertySet.getPropertySetInfo()}*${vars.伤害附加系数}\n" +
//                "                \"\"\"\n" +
//                "                tip = StringUtil.replaceLinefeed(tip, \"|n\")\n" +
//                "                ability.setResearchubertip(tip)\n" +
//                "                ability.setUbertip(tip)\n" +
//                "                println\"${vars.名字} id = ${ability.getID()}\"\n" +
//                "\n" +
//                "                AbilityImportService abilityImportService = new AbilityImportService(sep.dir, binding.getVariables());\n" +
//                "        abilityImportService.importAll()\n" +
//                "\n" +
//                "}\n" +
//                "\n" +
//                "        SWE.showDialog(sep.name, vars, onClick, \"\"\"\n" +
//                "\n" +
//                "                \"\"\");\n" +
//                "\n");
//
//
//    }
//
//
//    //one case
//    static {
//        keyTemplateMap.put("被动技能库模板", "import heduim.fastwar.core.bean.PropertySet\n" +
//                "import heduim.fastwar.core.plugin.SWE\n" +
//                "import heduim.fastwar.core.service.code_generator.AbilityService\n" +
//                "import heduim.fastwar.core.service.plugin.AbilityImportService\n" +
//                "import org.solar.lang.SolarMap\n" +
//                "import org.solar.lang.StringUtil\n" +
//                "\n" +
//                "//设置输入变量\n" +
//                "                vars = new SolarMap()\n" +
//                "        vars.名字 = sep.name\n" +
//                "        vars.图标 = new File(sep.dir + \"sep.png\")\n" +
//                "        vars.触发几率 = 40\n" +
//                "        vars.伤害附加系数 = 0.5\n" +
//                "        vars.伤害附加力量 = true\n" +
//                "        vars.伤害附加敏捷 = true\n" +
//                "        vars.伤害附加智力 = true\n" +
//                "        vars.伤害附加生命值 = false\n" +
//                "\n" +
//                "        onClick = {\n" +
//                "                //伤害设置\n" +
//                "                ability_number = vars.伤害附加系数\n" +
//                "                propertySet = new PropertySet(vars.伤害附加力量, vars.伤害附加敏捷,\n" +
//                "                        vars.伤害附加智力, vars.伤害附加生命值, )\n" +
//                "                //生成技能\n" +
//                "                ability = AbilityService.unactive(vars.名字)\n" +
//                "                ability.setArt(SWE.icon(vars.图标.getAbsolutePath()))\n" +
//                "                def tip = \"\"\"\n" +
//                "                对一条直线单位造成伤害|n内置cd 1秒|n触发概率${vars.触发几率}%\n" +
//                "                |cff99ccff伤害公式 ${propertySet.getPropertySetInfo()}*${vars.伤害附加系数}\n" +
//                "                \"\"\"\n" +
//                "                tip = StringUtil.replaceLinefeed(tip, \"|n\")\n" +
//                "                ability.setResearchubertip(tip)\n" +
//                "                ability.setUbertip(tip)\n" +
//                "                println\"${vars.名字} id = ${ability.getID()}\"\n" +
//                "\n" +
//                "                AbilityImportService abilityImportService = new AbilityImportService(sep.dir, binding.getVariables());\n" +
//                "        abilityImportService.importAll()\n" +
//                "\n" +
//                "}\n" +
//                "\n" +
//                "        SWE.showDialog(sep.name, vars, onClick, \"\"\"\n" +
//                "\n" +
//                "                \"\"\");\n" +
//                "\n");
//
//
//    }
//
//
//    //one case
//    static {
//        keyTemplateMap.put("被动技能模板", "import heduim.fastwar.core.plugin.SWE\n" +
//                "import heduim.fastwar.core.service.code_generator.AbilityService\n" +
//                "import heduim.fastwar.core.service.plugin.TriggerImportService\n" +
//                "import org.solar.lang.SolarMap\n" +
//                "import org.solar.lang.StringUtil\n" +
//                "\n" +
//                "//设置输入变量\n" +
//                "                vars = new SolarMap()\n" +
//                "        vars.名字 = sep.name\n" +
//                "        vars.图标 = new File(sep.dir + \"sep.png\")\n" +
//                "        vars.溢出的每点几率转换伤害值 = 5\n" +
//                "\n" +
//                "        onClick = {\n" +
//                "                //生成技能\n" +
//                "                ability = AbilityService.unactive(vars.名字)\n" +
//                "                ability.setArt(SWE.icon(vars.图标.getAbsolutePath()))\n" +
//                "                def tip = \"\"\"\n" +
//                "                暴击几率溢出时会转化为暴击伤害\n" +
//                "                转化率为每1%暴击几率转化${vars.溢出的每点几率转换伤害值}%暴击伤害\n" +
//                "                \"\"\"\n" +
//                "                tip = StringUtil.replaceLinefeed(tip, \"|n\")\n" +
//                "                ability.setResearchubertip(tip)\n" +
//                "                ability.setUbertip(tip)\n" +
//                "                println\"${vars.名字} id = ${ability.getID()}\"\n" +
//                "\n" +
//                "                TriggerImportService importService = new TriggerImportService(sep.dir, binding.getVariables());\n" +
//                "        importService.importAll()\n" +
//                "\n" +
//                "}\n" +
//                "\n" +
//                "        SWE.showDialog(sep.name, vars, onClick, \"\"\"\n" +
//                "\n" +
//                "                \"\"\");");
//    }

}
