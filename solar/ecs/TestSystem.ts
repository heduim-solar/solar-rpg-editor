
import {EcsComponents} from "./EcsComponents";
import {Entity, System} from "./Ecs";

export default class TestSystem extends System {

    constructor() {
        super([
            EcsComponents.type
        ]);
    }

    update(time: number, delta: number, entity: Entity): void {
        // let object3D = EcsComponents.oneFrom(entity).data;

        // Iterate through all the entities on the query
        DisplayTimedTextToPlayer(Player(0), 0, 0, 60,
            'TestSystem_update()！！！time=+'+time+" delta="+delta)
    }
}

