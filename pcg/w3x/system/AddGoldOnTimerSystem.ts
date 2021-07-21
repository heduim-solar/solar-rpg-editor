import {Entity, System} from "../solar/ecs/Ecs";
import {TextTag} from "../solar/w3ts/handles/texttag";
import {Unit} from "../solar/w3ts/handles/unit";
import UnitCom from "../solar/ecs/UnitCom";
import AddGoldOnTimerCom from "../component/AddGoldOnTimerCom";

export default class AddGoldOnTimerSystem extends System {
    static abilityAddCount: { [id: string]: number } = {}
    static unitAddCount: { [id: string]: number } = {}


    constructor() {
        super([
            UnitCom.type
        ], 1000);
    }


    update(time: number, delta: number, entity: Entity): void {
        let addGoldUnit = UnitCom.oneFrom(entity).data.value;
        //one case
        for (const id in AddGoldOnTimerSystem.abilityAddCount) {
            if (addGoldUnit.getAbilityLevel(FourCC(id)) > 0) {
                this.doWork(addGoldUnit, AddGoldOnTimerSystem.abilityAddCount[id])
            }
        }
        //one case
        let addCount = AddGoldOnTimerSystem.unitAddCount[addGoldUnit.typeIdString]
        if (addCount && addCount > 0) {
            this.doWork(addGoldUnit, addCount)
        }
    }

    doWork(unit: Unit, addCount: number) {
        unit.owner.addGoldState(addCount);
        this.showTextTag(unit, "+" + addCount);
    }


    showTextTag(unit: Unit, text: string) {
        let textTag = new TextTag();
        textTag.setColor(205, 127, 50, 255)
        textTag.setText(text, 15, true)
        textTag.setPosUnit(unit, 100)
        textTag.setVelocity(0, 0.06)
        textTag.setPermanent(false)
        textTag.setLifespan(1)
    }


}

