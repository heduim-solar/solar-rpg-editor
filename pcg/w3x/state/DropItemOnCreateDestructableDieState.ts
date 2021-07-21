import {Trigger} from "../solar/w3ts/handles/trigger";
import {Destructable} from "../solar/w3ts/handles/destructable";
import RandomUtil from "../solar/util/RandomUtil";
import {Item} from "../solar/w3ts/handles/item";

/**
 *
 */
export default class DropItemOnCreateDestructableDieState {
    static config: {
        destructableId: string,
        x: number, y: number, z?: number, face?: number, scale?: number,
        itemIds: { [itemId: string]: number }
    }[] = []

    constructor() {
        this.init();
    }


    init() {
        let trigger = new Trigger()
        trigger.registerTimerEvent(0.1, false)
        trigger.addAction(() => {
            for (let element of DropItemOnCreateDestructableDieState.config) {
                let face = 0;
                if (element.face) {
                    face = element.face
                }
                let scale = 1;
                if (element.scale) {
                    scale = element.scale
                }

                let z = 0;
                if (element.z) {
                    z = element.z
                }

                let destructable: Destructable = new Destructable(FourCC(element.destructableId)
                    , element.x, element.y, z, face, scale, 0);
                let destructableDieTrigger = new Trigger()
                destructableDieTrigger.registerDeathEvent(destructable)
                destructableDieTrigger.addAction(() => {
                    let dropItemId = RandomUtil.getRandomKeyByWeight(element.itemIds)
                    let item = new Item(FourCC(dropItemId), destructable.x, destructable.y)

                })


            }


        })


    }


}