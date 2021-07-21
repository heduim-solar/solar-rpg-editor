import {Trigger} from "../solar/w3ts/handles/trigger";
import BaseUtil from "../solar/util/BaseUtil";
import MathUtil from "../solar/util/MathUtil";
import LeapUtil from "../solar/util/LeapUtil";

/**
 * 施放技能时冲锋特效
 */
export default class LeapEffectOnSpellState {
    /**
     * modelName : 模型路径
     * angleType :  UA=单位到技能施放点的角度 UF=单位面向角度 UT = 单位到施放目标单位位置的角度
     */
    static config: {
        [id: string]: {
            modelName: string,
            angleType: string,
            lifeTime: number,
            strDamageRate?: number,
            agiDamageRate?: number,
            intDamageRate?: number,
            hpDamageRate?: number,
            manaDamageRate?: number,
            isMagic: boolean,
            range: number
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
        let configData = LeapEffectOnSpellState.config[spellAbilityIdStr];
        if (!configData) {
            return
        }
        let angle = 0;
        let triggerUnit = GetTriggerUnit()
        switch (configData.angleType) {
            case "UA":
                angle = MathUtil.angleBetweenCoords(GetUnitX(triggerUnit), GetUnitY(triggerUnit), GetSpellTargetX(), GetSpellTargetY())
                break;
            case "UF":
                angle = GetUnitFacing(GetTriggerUnit())
                break;
            case "UT":
                let spellTargetUnit = GetSpellTargetUnit()
                angle = MathUtil.angleBetweenCoords(GetUnitX(triggerUnit), GetUnitY(triggerUnit),
                    GetUnitX(spellTargetUnit), GetUnitY(spellTargetUnit))
                break;
        }
        let damage = BaseUtil.calculateDamageByPropertySet(GetTriggerUnit(), configData)
        let damagetype = DAMAGE_TYPE_NORMAL
        if (configData.isMagic) {
            damagetype = DAMAGE_TYPE_MAGIC
        }
        LeapUtil.leap(GetTriggerUnit(), angle,
            configData.range, configData.lifeTime, damage, damagetype, configData.modelName
        )

    }


}