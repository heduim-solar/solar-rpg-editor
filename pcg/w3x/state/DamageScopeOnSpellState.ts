import {Trigger} from "../solar/w3ts/handles/trigger";
import {Unit} from "../solar/w3ts/handles/unit";
import BaseUtil from "../solar/util/BaseUtil";

/**
 * 施放技能时创建特效
 */
export default class DamageScopeOnSpellState {
    /**
     * modelName : 模型路径
     * locationType :  A=技能施放点 U=单位位置 TU = 施放目标单位位置
     * **DamageRate : 属性伤害系数  strDamageRate =2   造成力量*2的伤害
     * isAttack : 是否攻击伤害
     * isRanged : 是否远程伤害
     * isMagic : 是否法术伤害(默认是普通伤害)
     */
    static config: {
        [id: string]: {
            locationType?: string,
            strDamageRate?: number,
            agiDamageRate?: number,
            intDamageRate?: number,
            hpDamageRate?: number,
            manaDamageRate?: number,
            isAttack: boolean,
            isRanged: boolean,
            isMagic: boolean,
            range: number,
        }
    } = {}

    constructor() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_SPELL_EFFECT)
        trigger.addAction(this.action)
    }

    action(this: void) {
        //one case
        let spellAbilityIdStr = id2string(GetSpellAbilityId())
        let configData = DamageScopeOnSpellState.config[spellAbilityIdStr];
        if (!configData) {
            return
        }
        let x = 0, y = 0;
        switch (configData.locationType) {
            case "A":
                x = GetSpellTargetX();
                y = GetSpellTargetY();
                break;
            case "U":
                let triggerUnitHandle = GetTriggerUnit()
                x = GetUnitX(triggerUnitHandle);
                y = GetUnitY(triggerUnitHandle);
                break;
            case "TU":
                let spellTargetUnitHandle = GetSpellTargetUnit()
                x = GetUnitX(spellTargetUnitHandle);
                y = GetUnitY(spellTargetUnitHandle);
                break;
        }
        //计算伤害值
        let triggerUnitHandle = GetTriggerUnit()
        let damage = BaseUtil.calculateDamageByPropertySet(triggerUnitHandle,configData)

        let damagetype = DAMAGE_TYPE_NORMAL
        if (configData.isMagic) {
            damagetype = DAMAGE_TYPE_MAGIC
        }
        //选取范围内的敌人
        let group = CreateGroup()

        let triggerUnitPlayerHandle = GetOwningPlayer(GetTriggerUnit())
        let filter = Filter(() => {
            if (IsUnitEnemy(GetFilterUnit(), triggerUnitPlayerHandle)) {
                return true
            }
            return false
        });
        GroupEnumUnitsInRange(group, x, y, configData.range, filter);
        DestroyFilter(filter)

        ForGroup(group, () => {
            UnitDamageTarget(triggerUnitHandle, GetEnumUnit(), damage, configData.isAttack, configData.isRanged,
                ATTACK_TYPE_NORMAL, damagetype, WEAPON_TYPE_WHOKNOWS)
        });
        DestroyGroup(group)

    }


}