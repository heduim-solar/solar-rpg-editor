import ECS from "./solar/ecs/Ecs";
import SystemInit from "./SystemInit";
import {Trigger} from "./solar/w3ts/handles/trigger";
import TriggerEvent2ECS from "./solar/ecs/TriggerEvent2ECS";
import SystemConfigInit from "./SystemConfigInit";

export default class SolarEcs {
    static world = new ECS();


    static init() {

        TriggerEvent2ECS.init(this.world)
        //
        let trigger = new Trigger();
        trigger.registerTimerEvent(0.1, true)
        trigger.addAction(() => {
            //WorldTime是毫秒单位
            ECS.WorldTime = ECS.WorldTime + 100;
            SolarEcs.world.update();
        });
        //
        SystemConfigInit();
        SystemInit(SolarEcs.world);

    }


}