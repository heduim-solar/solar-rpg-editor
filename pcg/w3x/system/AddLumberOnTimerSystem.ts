import {Entity, System} from "../solar/ecs/Ecs";
import {TextTag} from "../solar/w3ts/handles/texttag";
import {Unit} from "../solar/w3ts/handles/unit";
import UnitCom from "../solar/ecs/UnitCom";

export default class AddLumberOnTimerSystem extends System {
    static abilityAddCount: { [id: string]: number } = {}
    static unitAddCount: { [id: string]: number } = {}


    constructor() {
        super([
            UnitCom.type
        ],1000);

    }

    update(time: number, delta: number, entity: Entity): void {
        let addGoldUnit = UnitCom.oneFrom(entity).data.value;
        //one case
        for (const id in AddLumberOnTimerSystem.abilityAddCount) {
            if (addGoldUnit.getAbilityLevel(FourCC(id)) > 0) {
                this.doWork(addGoldUnit, AddLumberOnTimerSystem.abilityAddCount[id])
            }
        }
        //one case
        for (const id in AddLumberOnTimerSystem.unitAddCount) {
            if (addGoldUnit.typeId == FourCC(id)) {
                this.doWork(addGoldUnit, AddLumberOnTimerSystem.unitAddCount[id])
            }
        }
    }

    doWork(unit: Unit, addCount: number) {
        unit.owner.addLumberState(addCount)
        this.showTextTag(unit, "+" + addCount);
    }


    showTextTag(unit: Unit, text: string) {
        let textTag = new TextTag();
        textTag.setColor(0, 127, 50, 255)
        textTag.setText(text, 15, true)
        textTag.setPosUnit(unit, 100)
        textTag.setVelocity(0, 0.06)
        textTag.setPermanent(false)
        textTag.setLifespan(1)
    }


}

