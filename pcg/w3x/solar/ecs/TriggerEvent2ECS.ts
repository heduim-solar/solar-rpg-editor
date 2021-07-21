import ECS from "./Ecs";
import {Trigger} from "../w3ts/handles/trigger";
import BaseEntity from "./BaseEntity";
import {UnitAttackedEventCom} from "./UnitAttackedEventCom";
import {Unit} from "../w3ts/handles/unit";
import EntityDecaySystem from "./EntityDecaySystem";
import UpdateCom from "./UpdateCom";
import UnitCom from "./UnitCom";
import UnitSystem from "./UnitSystem";

export default class TriggerEvent2ECS {

    static init(world: ECS) {
        let baseEntity = new BaseEntity()
        baseEntity.add(new UpdateCom({}))
        world.addEntity(baseEntity)

        //one case
        this.addUnitAttackedEvent(world);
        this.addUnitEnterMap(world);
        world.addSystem(new UnitSystem())
        world.addSystem(new EntityDecaySystem())

    }


    static addUnitAttackedEvent(world: ECS) {
        let trigger = new Trigger();
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_ATTACKED)
        trigger.addAction(() => {
            let baseEntity = new BaseEntity(10000)
            baseEntity.add(new UnitAttackedEventCom({
                attacker: Unit.fromHandle(GetAttacker()),
                attackedUnit: Unit.fromHandle(GetTriggerUnit())
            }))
            world.addEntity(baseEntity)
        })
    }

    static addUnitEnterMap(world: ECS) {
        let trigger = new Trigger();
        let rectRegion: region = CreateRegion()
        RegionAddRect(rectRegion, bj_mapInitialPlayableArea)
        trigger.registerEnterRegion(rectRegion, null)
        trigger.addAction(() => {
            let baseEntity = new BaseEntity()
            let unit = Unit.fromHandle(GetTriggerUnit())
            baseEntity.add(new UnitCom({
                value: unit
            }))
            unit.entity = baseEntity
            world.addEntity(baseEntity)
        })
        //add on
        let onTimeTrigger = new Trigger();
        onTimeTrigger.registerTimerEvent(0, false)
        onTimeTrigger.addAction(() => {
            let groupHandle = CreateGroup()
            GroupEnumUnitsInRect(groupHandle, bj_mapInitialPlayableArea, null)
            ForGroup(groupHandle, () => {
                let baseEntity = new BaseEntity()
                let unit = Unit.fromHandle(GetEnumUnit())
                baseEntity.add(new UnitCom({
                    value: unit
                }))
                unit.entity = baseEntity
                world.addEntity(baseEntity)
            })
            //de
            DestroyGroup(groupHandle)
            onTimeTrigger.destroy();
        });


    }


}