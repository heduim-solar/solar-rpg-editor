import {Component} from "./Ecs";

// export default class EcsComponents extends Component<any>{
//
// }

export type Box = {
    width: number;
    height: number;
    depth: number;
}


export const EcsComponents = Component.register<Box>();