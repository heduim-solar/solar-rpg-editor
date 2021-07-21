import {Component} from "../solar/ecs/Ecs";
import {Unit} from "../solar/w3ts/handles/unit";


export type TransferItem2UnitDataType = {
    slot: number;
    count: number;
    unit: Unit;
}


export default Component.register<TransferItem2UnitDataType>();