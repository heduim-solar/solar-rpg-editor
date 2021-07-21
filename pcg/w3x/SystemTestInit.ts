import ECS from "./solar/ecs/Ecs";
import AddGoldOnTimerSystem from "./system/AddGoldOnTimerSystem";
import AddGoldOnAttackSystemConfig from "./system/AddGoldOnAttackSystemConfig";
import AddGoldOnAttackSystem from "./system/AddGoldOnAttackSystem";
import DebugUtil from "./solar/util/DebugUtil";
import {Unit} from "./solar/w3ts/handles/unit";
import AddLumberOnTimerSystem from "./system/AddLumberOnTimerSystem";
import AddItemOnTimerSystem from "./system/AddItemOnTimerSystem";
import PickupAroundItemOnTimerSystem from "./system/PickupAroundItemOnTimerSystem";

export default function SystemTestInit(world: ECS) {

    // //base
    // world.addSystem(new AddGoldOnAttackSystem())
    // AddGoldOnAttackSystemConfig();
    // //test
    // DebugUtil.onTime(3,()=>{
    //     DisplayTimedTextToPlayer(Player(0), 0, 0, 60, 'SystemTestInit onTime！！！');
    //     let unit = new Unit(0, FourCC("Hpal"), 0, 0, 0)
    //     new Unit(0, FourCC("Hpal"), 0, 0, 0)
    //     new Unit(0, FourCC("Hpal"), 0, 0, 0)
    //     new Unit(0, FourCC("Hpal"), 0, 0, 0)
    //     new Unit(0, FourCC("Hpal"), 0, 0, 0)
    // });
    // world.addSystem(new AddGoldOnTimerSystem())
    // AddGoldOnTimerSystem.abilityAddCount.AHhb = 66//神圣之光
    // AddGoldOnTimerSystem.unitAddCount.Hpal = 3//
    // AddGoldOnTimerSystem.unitAddCount.hpea = 5//
    // AddGoldOnTimerSystem.unitAddCount.hfoo = 0//
    // //
    // world.addSystem(new AddItemOnTimerSystem())
    //
    // AddItemOnTimerSystem.config.Hpal = {id:"afac",addCount:2}//
    // AddItemOnTimerSystem.config.hpea = {id:"ssil",addCount:1}//
    // AddItemOnTimerSystem.config.hfoo = {id:"stel",addCount:3}//
    // //one case
    // // world.addSystem(new AddLumberOnTimerSystem())
    // // AddLumberOnTimerSystem.abilityAddCount.AHhb = 66//神圣之光
    // // AddLumberOnTimerSystem.unitAddCount.Hpal = 6//
    // // AddLumberOnTimerSystem.unitAddCount.hpea = 7//
    // // AddLumberOnTimerSystem.unitAddCount.hfoo = 11//
    // world.addSystem(new PickupAroundItemOnTimerSystem())
    // PickupAroundItemOnTimerSystem.config.Hpal = true;

}


