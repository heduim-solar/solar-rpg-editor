import TestSystem from "./solar/ecs/TestSystem";
import {EcsComponents} from "./solar/ecs/EcsComponents";
import ECS from "./solar/ecs/Ecs";
import BaseEntity from "./solar/ecs/BaseEntity";

/** @noSelf **/
export default class AppTest {


    start() {


        // @ts-ignore
        DisplayTimedTextToPlayer(Player(0), 0, 0, 60, 'AppTest start！！！');


        //one case
        let world = new ECS();

        world.addSystem(new TestSystem());
        for (let i = 0; i < 3; i++) {
            let tE = new BaseEntity()
            tE.add(new EcsComponents({width: 1, height: 2, depth: 3}));
            world.addEntity(tE);
        }
        for (let i = 0; i < 3; i++) {
            world.update()
        }
        DisplayTimedTextToPlayer(Player(0), 0, 0, 60, 'AppTest end！！！')
    }


}
