import {Trigger} from "../solar/w3ts/handles/trigger"
import {Unit} from "../solar/w3ts/handles/unit"
import {ItemSimulationAttribute} from "./ItemSimulationAttributeState"
import TextTagUtil from "../solar/util/TextTagUtil";

const EVENT_DAMAGE_DATA_VAILD = 0
const EVENT_DAMAGE_DATA_IS_PHYSICAL = 1
const EVENT_DAMAGE_DATA_IS_ATTACK = 2
const EVENT_DAMAGE_DATA_IS_RANGED = 3
const EVENT_DAMAGE_DATA_DAMAGE_TYPE = 4
const EVENT_DAMAGE_DATA_WEAPON_TYPE = 5
const EVENT_DAMAGE_DATA_ATTACK_TYPE = 6

export default class SolarDamageState {


    constructor() {
        let trigger = new Trigger();
        trigger.registerAnyUnitDamagedEvent()
        trigger.addCondition(Condition(() => {
            return ((IsUnitAlly(GetTriggerUnit(), GetOwningPlayer(GetEventDamageSource())) == false) && (GetEventDamage() > 0))
        }))
        trigger.addAction(this.action)
    }


    action(this: void) {
        // 需要单位自定义数据（全部为实数类型 1  = 100%）
        // ////设置变量
        let unit0 = GetTriggerUnit()
        let unit1 = GetEventDamageSource()
        let unit0Data: ItemSimulationAttribute = Unit.fromHandle(unit1).solarData;
        let unit1Data: ItemSimulationAttribute = Unit.fromHandle(unit1).solarData;
        let new_dmg = GetEventDamage()
        // ////最终增伤
        let dmg_di = unit1Data.damage_increased ? unit1Data.damage_increased : 0
        new_dmg = new_dmg * (dmg_di + 1)
        // ////伤害减免Damage Reduction
        let dmg_dr = unit0Data.damage_reduction ? unit1Data.damage_reduction : 0
        new_dmg = new_dmg * (1 - Math.min(dmg_dr, 1))
        // ////判断伤害类型(计算物理法术增伤与暴击伤害)
        if (0 != EXGetEventDamageData(EVENT_DAMAGE_DATA_IS_PHYSICAL)) {
            // ////物理增伤
            let dmg_pdi = unit1Data.physical_damage_increased ? unit1Data.physical_damage_increased : 0
            new_dmg = new_dmg * (dmg_pdi + 1)
            // ////物理暴击
            let dmg_pcc = unit1Data.physical_critical_chance ? unit1Data.physical_critical_chance : 0
            if ((GetRandomReal(0, 1) < (dmg_pcc))) {
                // ////Critical damage 暴击伤害
                let dmg_pcd = unit1Data.physical_critical_damage ? unit1Data.physical_critical_damage : 0
                new_dmg *= dmg_pcd;
                // ////漂浮文字
                TextTagUtil.textOnUnit(unit0, "暴击:" + Math.floor(new_dmg), 200, 20, 200)
            }
        } else {
            // ////法术增伤
            let dmg_mdi = unit1Data.magic_damage_increased ? unit1Data.magic_damage_increased : 0
            new_dmg *= (dmg_mdi + 1)
            // ////判断暴击
            let dmg_mcc = unit1Data.magic_critical_chance ? unit1Data.magic_critical_chance : 0
            if ((GetRandomReal(0, 1) < dmg_mcc)) {
                // ////Critical damage 暴击伤害
                let dmg_mcd = unit1Data.magic_critical_damage ? unit1Data.magic_critical_damage : 0
                new_dmg *= dmg_mcd
                // ////漂浮文字
                TextTagUtil.textOnUnit(unit0, "法术暴击:" + Math.floor(new_dmg), 200, 20, 200)
            }
        }
        // //// 攻击吸血
        // if (0 != EXGetEventDamageData(EVENT_DAMAGE_DATA_IS_ATTACK)) {
        // }
        // //// 吸血
        let dmg_abs = unit1Data.blood_sucking ? unit1Data.blood_sucking : 0
        if (dmg_abs > 0) {
            let add_hp = new_dmg * dmg_abs
            SetUnitState(unit1, UNIT_STATE_LIFE, Math.max(0, GetUnitState(unit1, UNIT_STATE_LIFE) + add_hp))
        }
        // ////设置伤害值
        EXSetEventDamage(new_dmg);
    }

}