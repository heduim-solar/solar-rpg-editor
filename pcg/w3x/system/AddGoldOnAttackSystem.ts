import {Entity, System} from "../solar/ecs/Ecs";
import {UnitAttackedEventCom} from "../solar/ecs/UnitAttackedEventCom";
import {TextTag} from "../solar/w3ts/handles/texttag";
import {Unit} from "../solar/w3ts/handles/unit";

export default class AddGoldOnAttackSystem extends System {
    static abilityAddCount: { [id: string]: number } = {}
    static unitAddCount: { [id: string]: number } = {}


    constructor() {
        super([
            UnitAttackedEventCom.type
        ]);
    }


    enter(entity: Entity) {
        let unitAttackedComponent = UnitAttackedEventCom.oneFrom(entity);
        let attackerUnit = unitAttackedComponent.data.attacker

        //one case
        for (const id in AddGoldOnAttackSystem.abilityAddCount) {
            if (attackerUnit.getAbilityLevel(FourCC(id)) > 0) {
                this.doWork(attackerUnit, AddGoldOnAttackSystem.abilityAddCount[id])
            }
        }

        //one case
        for (const id in AddGoldOnAttackSystem.unitAddCount) {
            if (attackerUnit.typeId == FourCC(id)) {
                this.doWork(attackerUnit, AddGoldOnAttackSystem.unitAddCount[id])
            }
        }


    }

    doWork(unit: Unit, addCount: number) {
        unit.owner.setState(PLAYER_STATE_RESOURCE_GOLD, unit.owner.getState(PLAYER_STATE_RESOURCE_GOLD) + addCount)
        this.showTextTag(unit, "+" + addCount);
    }


    showTextTag(unit: Unit, text: string) {
        let textTag = new TextTag();
        textTag.setColor(205,127,50, 255)
        textTag.setText(text, 15, true)
        textTag.setPosUnit(unit, 100)
        textTag.setVelocity(0, 0.02)
        textTag.setPermanent(false)
        textTag.setLifespan(1)
    }


}

