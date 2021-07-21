import {Entity, System} from "../solar/ecs/Ecs";
import {UnitAttackedEventCom} from "../solar/ecs/UnitAttackedEventCom";
import {TextTag} from "../solar/w3ts/handles/texttag";
import {Unit} from "../solar/w3ts/handles/unit";
import {Item} from "../solar/w3ts/handles/item";

export default class ConsumeItemOnAttackSystem extends System {
    static config: { [id: string]: number } = {}


    constructor() {
        super([
            UnitAttackedEventCom.type
        ]);
    }


    enter(entity: Entity) {
        let unitAttackedComponent = UnitAttackedEventCom.oneFrom(entity);
        let data = unitAttackedComponent.data
        let itemHandle = UnitItemInSlot(data.attacker.handle, 0)
        if (!itemHandle) {
            itemHandle = UnitItemInSlot(data.attacker.handle, 1)
        }
        if (!itemHandle) {
            return
        }
        let item = Item.fromHandle(itemHandle);
        let consumeCount = ConsumeItemOnAttackSystem.config[item.typeIdString]
        if (consumeCount && consumeCount > 0) {
            if (item.charges > consumeCount) {
                item.charges = item.charges - consumeCount;
            } else {
                item.destroy()
            }
        }
        //
    }


}

