/** @noSelf **/
import {Trigger} from "./solar/w3ts/handles/trigger";
import {MapPlayer} from "./solar/w3ts/handles/player";
import DestructableUtil from "./solar/util/DestructableUtil";
import DebugUtil from "./solar/util/DebugUtil";
import BaseUtil from "./solar/util/BaseUtil";
import {Unit} from "./solar/w3ts/handles/unit";


export default class AppTest {


    start() {
        let trigger = new Trigger()
        trigger.registerPlayerChatEvent(MapPlayer.fromIndex(0), "1", true)
        trigger.addAction(() => {

            DebugUtil.showText(BaseUtil.getGameTime() + "")
            // DestructableUtil.hasDestructableInRect(0,0,123456789,"1234")
            let unit = new Unit(0, FourCC("hpea"), 0, 0, 0)
            SetUnitState(unit.handle, ConvertUnitState(0x12), 500 + GetUnitState(GetTriggerUnit(), ConvertUnitState(0x12)) * 100)

        })
        //
        // let triggerTest2 = new Trigger()
        // triggerTest2.registerAnyUnitDamagedEvent()
        // triggerTest2.addAction(() => {
        //     DebugUtil.showText(BaseUtil.getGameTime() +
        //         ": 伤害来源:" + Unit.fromHandle(GetEventDamageSource()).name +
        //         ": 受害单位:" + Unit.fromHandle(GetTriggerUnit()).name +
        //         ": 伤害值:" + GetEventDamage()
        //     )
        //
        // })

    }


}
