import {Entity, System} from "./Ecs";
import UnitCom from "./UnitCom";

export default class UnitSystem extends System {

    constructor() {
        super([
            UnitCom.type
        ], 1000);
    }

    update(time: number, delta: number, entity: Entity): void {
        let u = UnitCom.oneFrom(entity).data.value;
        if (!u.isHero() && !u.isAlive()) {
            u.destroy()
            this.world.removeEntity(entity)
        }
    }
}

