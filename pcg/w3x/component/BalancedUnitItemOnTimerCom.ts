import {Component} from "../solar/ecs/Ecs";
import {Unit} from "../solar/w3ts/handles/unit";




export default Component.register<{
    unit: Unit;
}>();