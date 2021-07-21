import {Component} from "./Ecs";
import {Unit} from "../w3ts/handles/unit";



export type UnitAttackedDataType = {
    attacker: Unit;//攻击者
    attackedUnit: Unit;//被攻击者
}


export const UnitAttackedEventCom = Component.register<UnitAttackedDataType>()