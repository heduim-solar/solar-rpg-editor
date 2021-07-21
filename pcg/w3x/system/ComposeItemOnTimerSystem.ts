import {Entity, System} from "../solar/ecs/Ecs";
import UnitCom from "../solar/ecs/UnitCom";
import {Item} from "../solar/w3ts/handles/item";

/**
 * 每秒合成物品
 */
export default class ComposeItemOnTimerSystem extends System {
    static config: {
        [id: string]: {
            result: string,
            resultCount: number,
            material: { [id: string]: number }
        }
    } = {}


    constructor() {
        super([
            UnitCom.type,
        ], 2000);
    }


    update(time: number, delta: number, entity: Entity): void {
        let unit = UnitCom.oneFrom(entity).data.value;
        //one case
        let data = ComposeItemOnTimerSystem.config[unit.typeIdString]
        if (!data) {
            return
        }
        //检查是否满足材料
        for (const materialId in data.material) {
            let item = unit.getItemByItemType(FourCC(materialId))
            if (item == null || item.charges <= data.resultCount * data.material[materialId]) {
                return;
            }
        }
        //ok
        for (const materialId in data.material) {
            let item = unit.getItemByItemType(FourCC(materialId))
            item.charges = item.charges - (data.resultCount * data.material[materialId])
        }
        //create item
        let resultItem = new Item(FourCC(data.result), unit.x, unit.y)
        resultItem.charges = data.resultCount

    }


}

