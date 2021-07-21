import RectUtil from "./RectUtil";
import {Rectangle} from "../w3ts/handles/rect";

export default class DestructableUtil {


    static hasDestructableInRect(centerX: number, centerY: number, radius: number, destructableId: string): boolean {
        let r: Rectangle = RectUtil.GetRectFromCircle(centerX, centerY, radius)
        let flag = false;
        let destructableId_num = FourCC(destructableId)
        r.enumDestructables(Filter(() => {
            if (GetDestructableTypeId(GetFilterDestructable()) == destructableId_num) {
                return true;
            }
            return false
        }), () => {
            flag = true
        })
        return flag
    }


}