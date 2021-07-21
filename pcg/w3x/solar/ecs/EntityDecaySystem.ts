import {Entity, System} from "./Ecs";
import EntityDecayCom from "./EntityDecayCom";

export default class EntityDecaySystem extends System {

    constructor() {
        super([
            EntityDecayCom.type
        ]);
    }

    update(time: number, delta: number, entity: Entity): void {
        let entityDecayCom = EntityDecayCom.oneFrom(entity);
        entityDecayCom.data.lifeTime = entityDecayCom.data.lifeTime - delta;
        if (entityDecayCom.data.lifeTime <= 0) {
            this.world.removeEntity(entity)
        }
    }
}

