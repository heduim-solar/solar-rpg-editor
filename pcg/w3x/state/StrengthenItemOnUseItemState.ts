import {Trigger} from "../solar/w3ts/handles/trigger";
import BaseUtil from "../solar/util/BaseUtil";
import {Unit} from "../solar/w3ts/handles/unit";
import ItemSimulationAttributeState, {ItemSimulationAttribute} from "./ItemSimulationAttributeState";
import {Item} from "../solar/w3ts/handles/item";
import {MapPlayer} from "../solar/w3ts/handles/player";
import TextTagUtil from "../solar/util/TextTagUtil";

/**
 * 使用物品时消耗资源强化物品
 * 依赖ItemSimulationAttributeState.ts
 */
export default class StrengthenItemOnUseItemState {
    /**
     */
    static config: {
        [id: string]: {
            strengthen_gold_cost?: number,
            strengthen_lumber_cost?: number,
            strengthen_rate?: number,
            strengthen_max_level?: number,
        }
    } = {}

    constructor() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_USE_ITEM)
        trigger.addAction(this.action)
    }

    action(this: void) {
        //one case
        let itemHandle = GetManipulatedItem();
        let itemId = GetItemTypeId(itemHandle);
        let itemIdStr = id2string(itemId);

        let configData = StrengthenItemOnUseItemState.config[itemIdStr];
        if (!configData) {
            return
        }
        //
        let unit = Unit.fromEvent();
        let player = unit.owner;
        //强化逻辑
        let itemObj = Item.fromHandle(itemHandle);
        let solarData = itemObj.solarData;
        if (configData.strengthen_max_level && solarData.StrengthenItemOnUseItemState_lv && solarData.StrengthenItemOnUseItemState_lv >= configData.strengthen_max_level) {
            let info = "|cffff0000当前强化等级: Lv" + solarData.StrengthenItemOnUseItemState_lv + itemObj.name
            info += "|n装备属性增强: " + ((configData.strengthen_rate * solarData.StrengthenItemOnUseItemState_lv) * 100) + "%"
            TextTagUtil.textOnUnit(unit.handle, "|cffff0000已强化到最大等级:" + configData.strengthen_max_level +"|n"+info)
            return
        }

        if (configData.strengthen_gold_cost && !player.hasGold(configData.strengthen_gold_cost)) {
            player.displayText("|cffff0000金币不足:" + configData.strengthen_gold_cost)
            return;
        }
        if (configData.strengthen_lumber_cost && !player.hasLumber(configData.strengthen_lumber_cost)) {
            player.displayText("|cffff0000木材不足:" + configData.strengthen_lumber_cost)
            return;
        }
        //强化
        if (configData.strengthen_gold_cost) {
            player.addGoldState(-configData.strengthen_gold_cost)
        }
        if (configData.strengthen_lumber_cost) {
            player.addLumberState(-configData.strengthen_lumber_cost)
        }
        //
        let itemSimulationAttribute: ItemSimulationAttribute = ItemSimulationAttributeState.config[itemIdStr]
        if (!itemSimulationAttribute) {
            player.displayText("|cffff0000没有基础属性可强化")
            return;
        }

        solarData.StrengthenItemOnUseItemState_lv = (solarData.StrengthenItemOnUseItemState_lv ? solarData.StrengthenItemOnUseItemState_lv : 0) + 1
        itemSimulationAttribute = Object.assign({}, itemSimulationAttribute)
        StrengthenItemOnUseItemState.strengthenItemSimulationAttribute(itemSimulationAttribute, configData.strengthen_rate * solarData.StrengthenItemOnUseItemState_lv)
        solarData.ItemSimulationAttribute = itemSimulationAttribute
        ItemSimulationAttributeState.refreshUnitItemSimulationAttribute(unit.handle)
        //设置物品信息
        //4 = 提示
        //3 = 提示（扩展）
        let info = "|cffff0000【强化成功】强化等级: Lv" + solarData.StrengthenItemOnUseItemState_lv + itemObj.name
        info += "|n装备属性增强: " + ((configData.strengthen_rate * solarData.StrengthenItemOnUseItemState_lv) * 100) + "%"
        player.displayText(info)
        TextTagUtil.textOnUnit(unit.handle, info)
        // if (player.isLocalPlayer()) {
        //     EXSetItemDataString(itemId, 4, item[itemIdStr].Tip + " +" + solarData.StrengthenItemOnUseItemState_lv)
        //     EXSetItemDataString(itemId, 3, item[itemIdStr].Ubertip + "|n|n" + info)
        // }
    }

    static strengthenItemSimulationAttribute(context: ItemSimulationAttribute, strengthen_rate: number) {
        if (context.attack) {
            context.attack *= strengthen_rate
        }
        if (context.attack_p) {
            context.attack_p *= strengthen_rate
        }
        if (context.life) {
            context.life *= strengthen_rate
        }
        if (context.life_p) {
            context.life_p *= strengthen_rate
        }
        if (context.mana) {
            context.mana *= strengthen_rate
        }
        if (context.mana_p) {
            context.mana_p *= strengthen_rate
        }
        if (context.def) {
            context.def *= strengthen_rate
        }
        if (context.def_p) {
            context.def_p *= strengthen_rate
        }
        if (context.strength) {
            context.strength *= strengthen_rate
        }
        if (context.strength_p) {
            context.strength_p *= strengthen_rate
        }
        if (context.agility) {
            context.agility *= strengthen_rate
        }
        if (context.agility_p) {
            context.agility_p *= strengthen_rate
        }
        if (context.intelligence) {
            context.intelligence *= strengthen_rate
        }
        if (context.intelligence_p) {
            context.intelligence_p *= strengthen_rate
        }
        if (context.attackSpd_p) {
            context.attackSpd_p *= strengthen_rate
        }
        if (context.physical_critical_chance) {
            context.physical_critical_chance *= strengthen_rate
        }
        if (context.physical_critical_damage) {
            context.physical_critical_damage *= strengthen_rate
        }
        if (context.physical_damage_increased) {
            context.physical_damage_increased *= strengthen_rate
        }
        if (context.magic_critical_chance) {
            context.magic_critical_chance *= strengthen_rate
        }
        if (context.magic_critical_damage) {
            context.magic_critical_damage *= strengthen_rate
        }
        if (context.magic_damage_increased) {
            context.magic_damage_increased *= strengthen_rate
        }
        if (context.damage_increased) {
            context.damage_increased *= strengthen_rate
        }
        if (context.damage_reduction) {
            context.damage_reduction *= strengthen_rate
        }
        if (context.blood_sucking) {
            context.blood_sucking *= strengthen_rate
        }

    }

}