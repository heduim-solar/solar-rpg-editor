import {Trigger} from "../solar/w3ts/handles/trigger";
import {Destructable} from "../solar/w3ts/handles/destructable";
import RandomUtil from "../solar/util/RandomUtil";
import {Item} from "../solar/w3ts/handles/item";

/**
 *
 */
export default class DropItemOnBummerDieState {
    static config: {
        count: number
        itemIds: { [itemId: string]: number }
    } = {
        count: 1,
        itemIds: {}
    }

    constructor() {
        this.init();
    }


    init() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_DEATH)
        trigger.addAction(() => {
            let triggerUnit = GetTriggerUnit();
            if (GetUnitAbilityLevel(triggerUnit, FourCC("Awan")) > 0) {
                let dropItemId = RandomUtil.getRandomKeyByWeight(DropItemOnBummerDieState.config.itemIds)
                let item = new Item(FourCC(dropItemId), GetUnitX(triggerUnit), GetUnitY(triggerUnit))
            }


        })


    }


}