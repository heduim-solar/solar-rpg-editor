import {Trigger} from "../../solar/w3ts/handles/trigger";
import {MapPlayer} from "../../solar/w3ts/handles/player";
import {Unit} from "../../solar/w3ts/handles/unit";
import BaseUtil from "../../solar/util/BaseUtil";
import TextTagUtil from "../../solar/util/TextTagUtil";
import MathUtil from "../../solar/util/MathUtil";
import LeapUtil from "../../solar/util/LeapUtil";

/**
 *  按D时向鼠标方向移动
 */
export default class BlinkOnTypeDState {
    /**
     * abilityId
     */
    static config: {
        [id: string]: {
            costMana: number
            maxRange: number
        }
    } = {}

    constructor() {
        let trigger = new Trigger()
        //68 = d
        DzTriggerRegisterKeyEvent(trigger.handle, 68, 1, true, null)
        trigger.addAction(this.action)
    }

    action(this: void) {
        //one case
        let player = MapPlayer.fromHandle(DzGetTriggerKeyPlayer());
        let selectedUnitHandle = player.getSelectedUnit();
        if (!selectedUnitHandle) {
            return
        }
        let selectedUnit: Unit = Unit.fromHandle(selectedUnitHandle);
        for (let abilityIdStr in BlinkOnTypeDState.config) {
            if (!selectedUnit.hasAbility(abilityIdStr)) {
                continue
            }
            let configData = BlinkOnTypeDState.config[abilityIdStr]
            if (selectedUnit.costMana(configData.costMana)) {
                let tx = DzGetMouseTerrainX()
                let ty = DzGetMouseTerrainY()
                let distance = MathUtil.distanceBetweenPoints(selectedUnit.x, selectedUnit.y, tx, ty)
                let angle = MathUtil.angleBetweenCoords(selectedUnit.x, selectedUnit.y, tx, ty)
                LeapUtil.leap(selectedUnit.handle, angle, Math.min(distance, configData.maxRange), 0.2)
            } else {
                selectedUnit.textTag("魔法不足")
            }
            return;
        }


    }


}