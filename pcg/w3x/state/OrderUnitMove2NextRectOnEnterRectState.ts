import {Trigger} from "../solar/w3ts/handles/trigger";
import {MapPlayer} from "../solar/w3ts/handles/player";
import {Unit} from "../solar/w3ts/handles/unit";

export default class OrderUnitMove2NextRectOnEnterRectState {
    //order = "move" attack
    static config: { [rectValName: string]: { nextRectValName: string, order: string } } = {}

    constructor() {
        this.init();
    }


    init() {
        for (let rectValName in OrderUnitMove2NextRectOnEnterRectState.config) {
            let data = OrderUnitMove2NextRectOnEnterRectState.config[rectValName];
            let trigger = new Trigger()
            let rectRegion: region = CreateRegion()
            RegionAddRect(rectRegion, (_G as any)[rectValName])
            trigger.registerEnterRegion(rectRegion, null);
            trigger.addAction(() => {
                let unit = Unit.fromEvent();
                //是玩家1的敌人
                if (unit.isEnemy(MapPlayer.fromIndex(0))) {
                    let rectHandle = (_G as any)[data.nextRectValName]
                    let nextX = GetRectCenterX(rectHandle)
                    let nextY = GetRectCenterY(rectHandle)
                    unit.issueOrderAt(data.order, nextX, nextY);
                    (unit as any).OrderUnitMove2NextRectOnEnterRectState_nextX = nextX;
                    (unit as any).OrderUnitMove2NextRectOnEnterRectState_nextY = nextY;
                }

            })

        }


    }


}