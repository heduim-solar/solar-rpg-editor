import {Trigger} from "../solar/w3ts/handles/trigger";
import {Group} from "../solar/w3ts/handles/group";
import {Unit} from "../solar/w3ts/handles/unit";
import BaseUtil from "../solar/util/BaseUtil";
import {Item} from "../solar/w3ts/handles/item";

/**
 * 攻击使用默认物编 AItg 增加攻击力的物品(+1)
 * 物品模拟属性状态
 * 幂等运算
 */
// spd?: number,//移动速度
// spd_p?: number,
// lifeSpd?: number, //生命恢复速度
// lifeSpd_p?: number,
// manaSpd?: number,
// manaSpd_p?: number,
// cool?: number, //冷却减少时间
// cool_p?: number, //冷却减少百分比
export type ItemSimulationAttribute = {
    attack?: number, //攻击
    attack_p?: number,
    life?: number,
    life_p?: number,
    mana?: number, //魔法
    mana_p?: number,
    def?: number,//护甲
    def_p?: number,
    strength?: number,
    strength_p?: number,
    agility?: number,
    agility_p?: number,
    intelligence?: number,
    intelligence_p?: number,
    attackSpd_p?: number,//攻击速度
    physical_critical_chance?: number,
    physical_critical_damage?: number,
    physical_damage_increased?: number,
    magic_critical_chance?: number,
    magic_critical_damage?: number,
    magic_damage_increased?: number,
    damage_increased?: number,//伤害增幅
    damage_reduction?: number,//伤害减免
    blood_sucking?: number,//伤害吸血
    [key: string]: any,

}
export default class ItemSimulationAttributeState {
    static config: { [id: string]: ItemSimulationAttribute } = {}

    constructor() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_PICKUP_ITEM)
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_DROP_ITEM)
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_PAWN_ITEM)
        trigger.addAction(() => {
            let unitHandle = GetTriggerUnit()
            //不要在此触发直接判断 因为丢弃物品触发执行时 物品还在物品栏里
            BaseUtil.runLater(0.1, () => {
                ItemSimulationAttributeState.refreshUnitItemSimulationAttribute(unitHandle)
            })
        })
        //
        let triggerTimer = new Trigger()
        triggerTimer.registerTimerEvent(10, true)
        triggerTimer.addAction(() => {
            let allUnitGroup: Group = new Group();
            allUnitGroup.enumUnitsInRect(bj_mapInitialPlayableArea, null)
            allUnitGroup.for(() => {
                ItemSimulationAttributeState.refreshUnitItemSimulationAttribute(GetEnumUnit())
            })
            allUnitGroup.destroy()
        })
    }


    static refreshUnitItemSimulationAttribute(unitHandle: unit) {
        //

        let unit = Unit.fromHandle(unitHandle)
        //统计装备总计数值
        let totalSAttr: ItemSimulationAttribute = {}
        for (let i = 0; i < 6; i++) {
            let itemHandle = UnitItemInSlot(unitHandle, i);
            if (!itemHandle) {
                continue
            }
            let itemTypeId = GetItemTypeId(itemHandle)
            let itemTypeIdStr = id2string(itemTypeId);
            //solarData 数据
            let itemSimulationAttribute: ItemSimulationAttribute = Item.fromHandle(itemHandle).solarData.ItemSimulationAttribute
            if (itemSimulationAttribute) {
                ItemSimulationAttributeState.addItemSimulationAttribute(totalSAttr, itemSimulationAttribute)
            }
            //type 数据
            let configData = ItemSimulationAttributeState.config[itemTypeIdStr];
            if (!configData) {
                continue
            }
            //计算数值
            ItemSimulationAttributeState.addItemSimulationAttribute(totalSAttr, configData)
        }
        //刷新装备总计数值到单位
        //AItg
        let unitOldItemSAttr: ItemSimulationAttribute = unit.solarData.ItemSimulationAttributeState_unitOldItemSimulationAttribute;
        if (!unitOldItemSAttr) {
            unitOldItemSAttr = {}
        }
        //108 = dataA
        //one case
        if (unitOldItemSAttr.attack || totalSAttr.attack) {
            let abilcode: number = FourCC("AItg")//攻击之爪+1
            let val = totalSAttr.attack ? totalSAttr.attack : 0
            ItemSimulationAttributeState.refreshUnitAbilityData(unitHandle, abilcode, 108, val)
        }
        if (unitOldItemSAttr.attack_p || totalSAttr.attack_p) {
            let abilcode: number = FourCC("AIth")//攻击之爪+2
            let base = unit.getState(ConvertUnitState(0x12))//基础伤害不包括绿字 和附加伤害
            let val = (totalSAttr.attack_p ? totalSAttr.attack : 0) * base
            ItemSimulationAttributeState.refreshUnitAbilityData(unitHandle, abilcode, 108, val)
        }
        //one case
        if (unitOldItemSAttr.life || totalSAttr.life) {
            let abilcode: number = FourCC("AIlz")//能增加生命值的物品 50
            let val = totalSAttr.life ? totalSAttr.life : 0
            ItemSimulationAttributeState.refreshUnitAbilityData(unitHandle, abilcode, 108, val)
        }
        if (unitOldItemSAttr.life_p || totalSAttr.life_p) {
            let abilcode: number = FourCC("AIlf")//能增加生命值的物品 150
            let base = unit.getState(UNIT_STATE_MAX_LIFE) - (unitOldItemSAttr.life_p_temp ? unitOldItemSAttr.life_p_temp : 0)
            let val = (totalSAttr.life_p ? totalSAttr.life_p : 0) * base
            totalSAttr.life_p_temp = val
            ItemSimulationAttributeState.refreshUnitAbilityData(unitHandle, abilcode, 108, val)
        }
        //one case
        if (unitOldItemSAttr.mana || totalSAttr.mana) {
            let abilcode: number = FourCC("AImv")//能增加魔法值的物品(75)
            let val = totalSAttr.mana ? totalSAttr.mana : 0
            ItemSimulationAttributeState.refreshUnitAbilityData(unitHandle, abilcode, 108, val)
        }
        if (unitOldItemSAttr.mana_p || totalSAttr.mana_p) {
            let abilcode: number = FourCC("AImz")//能增加魔法值的物品(100)
            let base = unit.getState(UNIT_STATE_MAX_MANA) - (unitOldItemSAttr.mana_p_temp ? unitOldItemSAttr.mana_p_temp : 0)
            let val = (totalSAttr.mana_p ? totalSAttr.mana_p : 0) * base
            totalSAttr.mana_p_temp = val
            ItemSimulationAttributeState.refreshUnitAbilityData(unitHandle, abilcode, 108, val)
        }
        //one case
        if (unitOldItemSAttr.def || totalSAttr.def) {
            let abilcode: number = FourCC("AId1")//能提高护甲的物品(1)
            let val = totalSAttr.def ? totalSAttr.def : 0
            ItemSimulationAttributeState.refreshUnitAbilityData(unitHandle, abilcode, 108, val)
        }
        if (unitOldItemSAttr.def_p || totalSAttr.def_p) {
            let abilcode: number = FourCC("AId2")//能提高护甲的物品(2)
            let base = unit.getState(UNIT_STATE_MAX_MANA) - (unitOldItemSAttr.def_p_temp ? unitOldItemSAttr.def_p_temp : 0)
            let val = (totalSAttr.def_p ? totalSAttr.def_p : 0) * base
            totalSAttr.def_p_temp = val
            ItemSimulationAttributeState.refreshUnitAbilityData(unitHandle, abilcode, 108, val)
        }
        //one case
        if (unitOldItemSAttr.attackSpd_p || totalSAttr.attackSpd_p) {
            let abilcode: number = FourCC("AIs2")//能提高进攻速度的物品
            let val = totalSAttr.attackSpd_p ? totalSAttr.attackSpd_p : 0
            ItemSimulationAttributeState.refreshUnitAbilityData(unitHandle, abilcode, 108, val)
        }
        //one case 属性附加
        let strength = 0
        let agility = 0
        let intelligence = 0
        if (totalSAttr.strength) {
            strength += totalSAttr.strength
        }
        if (totalSAttr.strength_p) {
            let base = unit.strength
            let val = (totalSAttr.strength_p ? totalSAttr.strength_p : 0) * base
            strength += val
        }
        if (totalSAttr.agility) {
            agility += totalSAttr.agility
        }
        if (totalSAttr.agility_p) {
            let base = unit.agility
            let val = (totalSAttr.agility_p ? totalSAttr.agility_p : 0) * base
            agility += val
        }
        if (totalSAttr.intelligence) {
            intelligence += totalSAttr.intelligence
        }
        if (totalSAttr.intelligence_p) {
            let base = unit.intelligence
            let val = (totalSAttr.intelligence_p ? totalSAttr.intelligence_p : 0) * base
            intelligence += val
        }
        let abilcode = FourCC("Aamk")
        if (GetUnitAbilityLevel(unitHandle, abilcode) <= 0) {
            UnitAddAbility(unitHandle, abilcode)
            let ability = EXGetUnitAbility(unitHandle, abilcode)
            EXSetAbilityDataReal(ability, 1, 111, 1)//dataD 隐藏图标
        }
        let ability = EXGetUnitAbility(unitHandle, abilcode)
        IncUnitAbilityLevel(unitHandle, abilcode)
        EXSetAbilityDataReal(ability, 1, 108, agility)
        EXSetAbilityDataReal(ability, 1, 109, intelligence)
        EXSetAbilityDataReal(ability, 1, 110, strength)
        DecUnitAbilityLevel(unitHandle, abilcode)
        /**
         dmg system
         */
        if (unitOldItemSAttr.physical_critical_chance || totalSAttr.physical_critical_chance) {
            let base = unit.solarData.physical_critical_chance ? unit.solarData.physical_critical_chance : 0
            base = base - (unitOldItemSAttr.physical_critical_chance_temp ? unitOldItemSAttr.physical_critical_chance_temp : 0)
            let val = base + (totalSAttr.physical_critical_chance ? totalSAttr.physical_critical_chance : 0)
            totalSAttr.physical_critical_chance_temp = val
            unit.solarData.physical_critical_chance = val
        }
        if (unitOldItemSAttr.physical_critical_damage || totalSAttr.physical_critical_damage) {
            let base = unit.solarData.physical_critical_damage ? unit.solarData.physical_critical_damage : 0
            base = base - (unitOldItemSAttr.physical_critical_damage_temp ? unitOldItemSAttr.physical_critical_damage_temp : 0)
            let val = base + (totalSAttr.physical_critical_damage ? totalSAttr.physical_critical_damage : 0)
            totalSAttr.physical_critical_damage_temp = val
            unit.solarData.physical_critical_damage = val
        }
        if (unitOldItemSAttr.physical_damage_increased || totalSAttr.physical_damage_increased) {
            let base = unit.solarData.physical_damage_increased ? unit.solarData.physical_damage_increased : 0
            base = base - (unitOldItemSAttr.physical_damage_increased_temp ? unitOldItemSAttr.physical_damage_increased_temp : 0)
            let val = base + (totalSAttr.physical_damage_increased ? totalSAttr.physical_damage_increased : 0)
            totalSAttr.physical_damage_increased_temp = val
            unit.solarData.physical_damage_increased = val
        }
        if (unitOldItemSAttr.magic_critical_chance || totalSAttr.magic_critical_chance) {
            let base = unit.solarData.magic_critical_chance ? unit.solarData.magic_critical_chance : 0
            base = base - (unitOldItemSAttr.magic_critical_chance_temp ? unitOldItemSAttr.magic_critical_chance_temp : 0)
            let val = base + (totalSAttr.magic_critical_chance ? totalSAttr.magic_critical_chance : 0)
            totalSAttr.magic_critical_chance_temp = val
            unit.solarData.magic_critical_chance = val
        }
        if (unitOldItemSAttr.magic_critical_damage || totalSAttr.magic_critical_damage) {
            let base = unit.solarData.magic_critical_damage ? unit.solarData.magic_critical_damage : 0
            base = base - (unitOldItemSAttr.magic_critical_damage_temp ? unitOldItemSAttr.magic_critical_damage_temp : 0)
            let val = base + (totalSAttr.magic_critical_damage ? totalSAttr.magic_critical_damage : 0)
            totalSAttr.magic_critical_damage_temp = val
            unit.solarData.magic_critical_damage = val
        }
        if (unitOldItemSAttr.magic_damage_increased || totalSAttr.magic_damage_increased) {
            let base = unit.solarData.magic_damage_increased ? unit.solarData.magic_damage_increased : 0
            base = base - (unitOldItemSAttr.magic_damage_increased_temp ? unitOldItemSAttr.magic_damage_increased_temp : 0)
            let val = base + (totalSAttr.magic_damage_increased ? totalSAttr.magic_damage_increased : 0)
            totalSAttr.magic_damage_increased_temp = val
            unit.solarData.magic_damage_increased = val
        }
        if (unitOldItemSAttr.damage_increased || totalSAttr.damage_increased) {
            let base = unit.solarData.damage_increased ? unit.solarData.damage_increased : 0
            base = base - (unitOldItemSAttr.damage_increased_temp ? unitOldItemSAttr.damage_increased_temp : 0)
            let val = base + (totalSAttr.damage_increased ? totalSAttr.damage_increased : 0)
            totalSAttr.damage_increased_temp = val
            unit.solarData.damage_increased = val
        }
        if (unitOldItemSAttr.damage_reduction || totalSAttr.damage_reduction) {
            let base = unit.solarData.damage_reduction ? unit.solarData.damage_reduction : 0
            base = base - (unitOldItemSAttr.damage_reduction_temp ? unitOldItemSAttr.damage_reduction_temp : 0)
            let val = base + (totalSAttr.damage_reduction ? totalSAttr.damage_reduction : 0)
            totalSAttr.damage_reduction_temp = val
            unit.solarData.damage_reduction = val
        }
        if (unitOldItemSAttr.blood_sucking || totalSAttr.blood_sucking) {
            let base = unit.solarData.magic_damage_increased ? unit.solarData.blood_sucking : 0
            base = base - (unitOldItemSAttr.blood_sucking_temp ? unitOldItemSAttr.blood_sucking_temp : 0)
            let val = base + (totalSAttr.blood_sucking ? totalSAttr.blood_sucking : 0)
            totalSAttr.blood_sucking_temp = val
            unit.solarData.blood_sucking = val
        }
        unit.solarData.ItemSimulationAttributeState_unitOldItemSimulationAttribute = totalSAttr;
    }

    static refreshUnitAbilityData(unitHandle: unit, abilcode: number, data_type: number, value: number) {
        if (GetUnitAbilityLevel(unitHandle, abilcode) <= 0) {
            UnitAddAbility(unitHandle, abilcode)
        }
        let ability = EXGetUnitAbility(unitHandle, abilcode)
        IncUnitAbilityLevel(unitHandle, abilcode)
        EXSetAbilityDataReal(ability, 1, data_type, value)
        DecUnitAbilityLevel(unitHandle, abilcode)
    }

    static addItemSimulationAttribute(context: ItemSimulationAttribute, summand: ItemSimulationAttribute) {
        if (summand.attack) {
            context.attack = context.attack ? context.attack : 0
            context.attack += summand.attack
        }
        if (summand.attack_p) {
            context.attack_p = context.attack_p ? context.attack_p : 0
            context.attack_p += summand.attack_p
        }
        if (summand.life) {
            context.life = context.life ? context.life : 0
            context.life += summand.life
        }
        if (summand.life_p) {
            context.life_p = context.life_p ? context.life_p : 0
            context.life_p += summand.life_p
        }
        if (summand.mana) {
            context.mana = context.mana ? context.mana : 0
            context.mana += summand.mana
        }
        if (summand.mana_p) {
            context.mana_p = context.mana_p ? context.mana_p : 0
            context.mana_p += summand.mana_p
        }
        if (summand.def) {
            context.def = context.def ? context.def : 0
            context.def += summand.def
        }
        if (summand.def_p) {
            context.def_p = context.def_p ? context.def_p : 0
            context.def_p += summand.def_p
        }
        if (summand.strength) {
            context.strength = context.strength ? context.strength : 0
            context.strength += summand.strength
        }
        if (summand.strength_p) {
            context.strength_p = context.strength_p ? context.strength_p : 0
            context.strength_p += summand.strength_p
        }
        if (summand.agility) {
            context.agility = context.agility ? context.agility : 0
            context.agility += summand.agility
        }
        if (summand.agility_p) {
            context.agility_p = context.agility_p ? context.agility_p : 0
            context.agility_p += summand.agility_p
        }
        if (summand.intelligence) {
            context.intelligence = context.intelligence ? context.intelligence : 0
            context.intelligence += summand.intelligence
        }
        if (summand.intelligence_p) {
            context.intelligence_p = context.intelligence_p ? context.intelligence_p : 0
            context.intelligence_p += summand.intelligence_p
        }
        if (summand.attackSpd_p) {
            context.attackSpd_p = context.attackSpd_p ? context.attackSpd_p : 0
            context.attackSpd_p += summand.attackSpd_p
        }
        if (summand.physical_critical_chance) {
            context.physical_critical_chance = context.physical_critical_chance ? context.physical_critical_chance : 0
            context.physical_critical_chance += summand.physical_critical_chance
        }
        if (summand.physical_critical_damage) {
            context.physical_critical_damage = context.physical_critical_damage ? context.physical_critical_damage : 0
            context.physical_critical_damage += summand.physical_critical_damage
        }
        if (summand.physical_damage_increased) {
            context.physical_damage_increased = context.physical_damage_increased ? context.physical_damage_increased : 0
            context.physical_damage_increased += summand.physical_damage_increased
        }
        if (summand.magic_critical_chance) {
            context.magic_critical_chance = context.magic_critical_chance ? context.magic_critical_chance : 0
            context.magic_critical_chance += summand.magic_critical_chance
        }
        if (summand.magic_critical_damage) {
            context.magic_critical_damage = context.magic_critical_damage ? context.magic_critical_damage : 0
            context.magic_critical_damage += summand.magic_critical_damage
        }
        if (summand.magic_damage_increased) {
            context.magic_damage_increased = context.magic_damage_increased ? context.magic_damage_increased : 0
            context.magic_damage_increased += summand.magic_damage_increased
        }
        if (summand.damage_increased) {
            context.damage_increased = context.damage_increased ? context.damage_increased : 0
            context.damage_increased += summand.damage_increased
        }
        if (summand.damage_reduction) {
            context.damage_reduction = context.damage_reduction ? context.damage_reduction : 0
            context.damage_reduction += summand.damage_reduction
        }
        if (summand.blood_sucking) {
            context.blood_sucking = context.blood_sucking ? context.blood_sucking : 0
            context.blood_sucking += summand.blood_sucking
        }

    }

}