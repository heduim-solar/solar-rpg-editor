import {Entity, System} from "../solar/ecs/Ecs";
import {TextTag} from "../solar/w3ts/handles/texttag";
import {Unit} from "../solar/w3ts/handles/unit";
import UnitCom from "../solar/ecs/UnitCom";
import {Item} from "../solar/w3ts/handles/item";

export default class AddItemOnTimerSystem extends System {
    static config: { [id: string]: { id: string, addCount: number } } = {}


    constructor() {
        super([
            UnitCom.type,
        ], 1000);
    }


    update(time: number, delta: number, entity: Entity): void {
        let addGoldUnit = UnitCom.oneFrom(entity).data.value;
        //one case
        let addData = AddItemOnTimerSystem.config[addGoldUnit.typeIdString]
        if (addData) {
            this.doWork(addGoldUnit, addData.id, addData.addCount)
        }
    }

    doWork(unit: Unit, id: string, addCount: number) {
        let item = new Item(FourCC(id), unit.x, unit.y)
        item.charges = addCount;
        unit.addItem(item)

        this.showTextTag(unit, item.name + "+" + addCount);
    }


    showTextTag(unit: Unit, text: string) {
        let textTag = new TextTag();
        textTag.setColor(100, 80, 150, 255)
        textTag.setText(text, 10, true)
        textTag.setPosUnit(unit, 100)
        textTag.setVelocity(0, 0.06)
        textTag.setPermanent(false)
        textTag.setLifespan(1)
    }


}

