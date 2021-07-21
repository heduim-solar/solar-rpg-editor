import {Trigger} from "../w3ts/handles/trigger";

export default class DebugUtil {
    static noDebug = false;

    static showText(text: string) {
        if (this.noDebug){
            return
        }
        DisplayTimedTextToPlayer(Player(0), 0, 0, 30,
            text)
    }


    static onTime(time: number, actionFunc: (this: void) => void) {
        let trigger = new Trigger();
        trigger.registerTimerEvent(time, false)
        trigger.addAction(actionFunc)
    }


    static limitTimeCache: { [id: string]: number } = {}

    static isLimitTime(key: string, time: number): boolean {
        if (!DebugUtil.limitTimeCache[key]) {
            DebugUtil.limitTimeCache[key] = 1;
        }
        if (DebugUtil.limitTimeCache[key] > time) {
            return true;
        }
        DebugUtil.limitTimeCache[key] = DebugUtil.limitTimeCache[key] + 1;
        return false;
    }

}