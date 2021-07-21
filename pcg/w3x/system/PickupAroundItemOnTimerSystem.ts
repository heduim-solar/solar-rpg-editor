import {Entity, System} from "../solar/ecs/Ecs";
import {Unit} from "../solar/w3ts/handles/unit";
import UnitCom from "../solar/ecs/UnitCom";
import {Rectangle} from "../solar/w3ts/handles/rect";
import RectUtil from "../solar/util/RectUtil";

export default class PickupAroundItemOnTimerSystem extends System {
    static config: { [id: string]: boolean } = {}


    constructor() {
        super([
            UnitCom.type,
        ], 2000);
    }


    update(time: number, delta: number, entity: Entity): void {
        let addGoldUnit = UnitCom.oneFrom(entity).data.value;
        //one case
        let data = PickupAroundItemOnTimerSystem.config[addGoldUnit.typeIdString]
        if (data) {
            this.doWork(addGoldUnit)
        }
    }

    doWork(unit: Unit) {

        let rect: Rectangle = RectUtil.GetRectFromCircle(unit.x, unit.y, 300)
        rect.enumItems(null, () => {
            UnitAddItem(unit.handle, GetEnumItem());
        })
        rect.destroy()

    }


}

