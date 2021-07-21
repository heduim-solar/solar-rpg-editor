import {Component} from "./Ecs";


export type EntityDecayDataType = {
    lifeTime: number;//生命周期
}


export default Component.register<EntityDecayDataType>()