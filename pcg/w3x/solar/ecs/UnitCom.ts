import {Component} from "./Ecs";
import {Unit} from "../w3ts/handles/unit";


export type UnitDataType = {
    value: Unit;
}


export default  Component.register<UnitDataType>();