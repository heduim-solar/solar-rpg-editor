import {Trigger} from "../solar/w3ts/handles/trigger";
import BaseUtil from "../solar/util/BaseUtil";

/**
 * 施放技能时创建特效
 */
export default class CreateEffectOnSpellState {
    /**
     * modelName : 模型路径
     * locationType :  A=技能施放点 U=单位位置 TU = 施放目标单位位置
     */
    static config: { [id: string]: { modelName: string, locationType: string, lifeTime: number } } = {}

    constructor() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_SPELL_EFFECT)
        trigger.addAction(this.action)
    }

    action(this: void) {
        //one case
        let spellAbilityIdStr = id2string(GetSpellAbilityId())
        let configData = CreateEffectOnSpellState.config[spellAbilityIdStr];
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
        let effectHandle = AddSpecialEffect(configData.modelName, x, y)
        BaseUtil.runLater(configData.lifeTime, () => {
            DestroyEffect(effectHandle)
        })
    }


}