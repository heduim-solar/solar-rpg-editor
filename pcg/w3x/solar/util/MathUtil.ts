import {Group} from "../w3ts/handles/group";

const PI: number = 3.14159
const E: number = 2.71828
const CELLWIDTH: number = 128.0
const CLIFFHEIGHT: number = 128.0
const UNIT_FACING: number = 270.0
const RADTODEG: number = 180.0 / PI
const DEGTORAD: number = PI / 180.0
/** @noSelf **/
export default class BaseUtil {


    static distanceBetweenPoints(x1: number, y1: number, x2: number, y2: number): number {
        let dx = x2 - x1
        let dy = y2 - y1
        return SquareRoot(dx * dx + dy * dy)
    }

    //极坐标位移 获得指定方向的x,y
    static polarProjection(x1: number, y1: number, dist: number, angle: number): { x: number, y: number } {
        let x = x1 + dist * Cos(angle * DEGTORAD)
        let y = y1 + dist * Sin(angle * DEGTORAD)
        return {x: x, y: y}
    }

    //与用户交互的 使用角度便于理解和编辑
    static angleBetweenCoords(x1: number, y1: number, x2: number, y2: number) {
        return RADTODEG * Atan2(y2 - y1, x2 - x1)
    }

    //大多数底层计算函数都是用这个 弧度
    static radianBetweenCoords(x1: number, y1: number, x2: number, y2: number) {
        return Atan2(y2 - y1, x2 - x1)
    }

    //弧度转角度
    static radian2angle(radian: number) {
        return RADTODEG * radian
    }

    //角度转弧度
    static angle2radian(angle: number) {
        return DEGTORAD * angle
    }




}

