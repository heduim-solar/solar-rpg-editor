import {Trigger} from "../solar/w3ts/handles/trigger";

/**
 * Targetattach5 = 目标附加点6
 *  把技能的目标附加点6作为脚本执行
 */
export default class ExecTargetattach5ScriptOnSpellState {


    constructor() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_SPELL_EFFECT)
        trigger.addAction(this.action)
    }

    action(this: void) {
        //one case
        let spellAbilityIdStr = id2string(GetSpellAbilityId())
        //目标附加点6
        let Targetattach5 = ability[spellAbilityIdStr].Targetattach5
        if (Targetattach5 && Targetattach5.length > 1) {
            let script = require(Targetattach5);
            if (script) {
                script.default();
            }
        }
    }


}