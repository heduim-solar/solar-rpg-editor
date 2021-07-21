import {Trigger} from "../solar/w3ts/handles/trigger";
import BaseUtil from "../solar/util/BaseUtil";
import {Unit} from "../solar/w3ts/handles/unit";
import UnitUtil from "../solar/util/UnitUtil";

/**
 * 太阳事件调度
 */
export default class SolarEventDispatchState {
    /**
     * 配置id和回调脚本路径
     */
    static config: {
        onAttackByItem: { [id: string]: string },
        onAttackByAbility: { [id: string]: string },
        onUnderAttackByItem: { [id: string]: string },
        onUnderAttackByAbility: { [id: string]: string },
        onKillByItem: { [id: string]: string },
        onKillByAbility: { [id: string]: string },
        onUseItem: { [id: string]: string },
        onCastAbility: { [id: string]: string },
    } = {
        onAttackByItem: {},
        onAttackByAbility: {},
        onUnderAttackByItem: {},
        onUnderAttackByAbility: {},
        onKillByItem: {},
        onKillByAbility: {},
        onUseItem: {},
        onCastAbility: {},
    }

    constructor() {
        this.register_attacked()
        this.register_death()
        this.register_use_item()
        this.register_spell_effect()
    }

    //被攻击
    register_attacked() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_ATTACKED)
        trigger.addAction(this.onUnitAttackedAction)
    }

    onUnitAttackedAction(this: void) {
        let attackUnitHandle = GetAttacker();
        let underAttackUnitHandle = GetTriggerUnit();
        //判断技能  单位攻击时
        for (let abilityIdStr in SolarEventDispatchState.config.onAttackByAbility) {
            if (GetUnitAbilityLevel(attackUnitHandle, FourCC(abilityIdStr)) > 0) {
                let scriptPath = SolarEventDispatchState.config.onAttackByAbility[abilityIdStr]
                SolarEventDispatchState.ExecScriptIfNotEmpty(scriptPath)
            }
        }
        //判断技能 单位被攻击时
        for (let abilityIdStr in SolarEventDispatchState.config.onUnderAttackByAbility) {
            if (GetUnitAbilityLevel(underAttackUnitHandle, FourCC(abilityIdStr)) > 0) {
                let scriptPath = SolarEventDispatchState.config.onUnderAttackByAbility[abilityIdStr]
                SolarEventDispatchState.ExecScriptIfNotEmpty(scriptPath)
            }
        }
        //判断物品 单位攻击时
        for (let itemIdStr in SolarEventDispatchState.config.onAttackByItem) {
            if (UnitUtil.GetInventoryOfItemType(attackUnitHandle, FourCC(itemIdStr))) {
                let scriptPath = SolarEventDispatchState.config.onAttackByItem[itemIdStr]
                SolarEventDispatchState.ExecScriptIfNotEmpty(scriptPath)
            }
        }
        //判断物品 单位被攻击时
        for (let itemIdStr in SolarEventDispatchState.config.onUnderAttackByItem) {
            if (UnitUtil.GetInventoryOfItemType(underAttackUnitHandle, FourCC(itemIdStr))) {
                let scriptPath = SolarEventDispatchState.config.onUnderAttackByItem[itemIdStr]
                SolarEventDispatchState.ExecScriptIfNotEmpty(scriptPath)
            }
        }
    }


    //被攻击
    register_death() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_DEATH)
        trigger.addAction(() => {
            let unitHandle = GetKillingUnit();
            //判断技能  单位杀敌时
            for (let abilityIdStr in SolarEventDispatchState.config.onKillByAbility) {
                if (GetUnitAbilityLevel(unitHandle, FourCC(abilityIdStr)) > 0) {
                    let scriptPath = SolarEventDispatchState.config.onKillByAbility[abilityIdStr]
                    SolarEventDispatchState.ExecScriptIfNotEmpty(scriptPath)
                }
            }
            //判断物品 单位杀敌时
            for (let itemIdStr in SolarEventDispatchState.config.onKillByItem) {
                if (UnitUtil.GetInventoryOfItemType(unitHandle, FourCC(itemIdStr))) {
                    let scriptPath = SolarEventDispatchState.config.onKillByItem[itemIdStr]
                    SolarEventDispatchState.ExecScriptIfNotEmpty(scriptPath)
                }
            }
        })
    }

    //使用物品
    register_use_item() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_USE_ITEM)
        trigger.addAction(() => {
            let itemHandle = GetManipulatedItem();
            let scriptPath = SolarEventDispatchState.config.onUseItem[id2string(GetItemTypeId(itemHandle))]
            SolarEventDispatchState.ExecScriptIfNotEmpty(scriptPath)
        })
    }

    //发动技能效果
    register_spell_effect() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_SPELL_EFFECT)
        trigger.addAction(() => {
            let abilityId = GetSpellAbilityId();
            let scriptPath = SolarEventDispatchState.config.onCastAbility[id2string(abilityId)]
            SolarEventDispatchState.ExecScriptIfNotEmpty(scriptPath)
        })
    }

    static ExecScriptIfNotEmpty(scriptPath: string) {
        if (scriptPath && scriptPath.length > 1) {
            let script = require(scriptPath);
            if (script) {
                script.default();
            }
        }
    }

}