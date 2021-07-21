import {Trigger} from "../solar/w3ts/handles/trigger";
import {MapPlayer} from "../solar/w3ts/handles/player";
import BaseEntity from "../solar/ecs/BaseEntity";
import {Unit} from "../solar/w3ts/handles/unit";
import UnitCom from "../solar/ecs/UnitCom";
import {Item} from "../solar/w3ts/handles/item";

export default class CreateItem2UnitOnTimeByUnitTypeState {
    static config: { time: number, itemId: string, unitId: string }[] = []

    constructor() {
        this.init();
    }


    init() {
        for (let i = 0; i < CreateItem2UnitOnTimeByUnitTypeState.config.length; i++) {
            let data = CreateItem2UnitOnTimeByUnitTypeState.config[i];
            let trigger = new Trigger()
            trigger.registerTimerEvent(data.time, false)
            trigger.addAction(() => {
                let groupHandle = CreateGroup()
                GroupEnumUnitsInRect(groupHandle, bj_mapInitialPlayableArea, null)
                ForGroup(groupHandle, () => {
                    if (FourCC(data.unitId) == GetUnitTypeId(GetEnumUnit())) {
                        let unit = Unit.fromHandle(GetEnumUnit())
                        let item = new Item(FourCC(data.itemId), unit.x, unit.y)
                        unit.addItem(item)
                    }
                })
                //de
                DestroyGroup(groupHandle)
                trigger.destroy();
            })

        }


    }


}