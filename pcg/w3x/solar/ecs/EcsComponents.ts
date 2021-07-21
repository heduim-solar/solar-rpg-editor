import {Component} from "./Ecs";


export type Box = {
    width: number;
    height: number;
    depth: number;
}


export const EcsComponents = Component.register<Box>();