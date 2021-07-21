import {Trigger} from "../solar/w3ts/handles/trigger";
import {MapPlayer} from "../solar/w3ts/handles/player";

export default class GameUnitAmountLimitState {
    static config: { [id: string]: number } = {}

    constructor() {
        this.init();
    }


    init() {
        let trigger = new Trigger()
        trigger.registerTimerEvent(1, false)
        trigger.addAction(() => {
            for (let i = 0; i < 12; i++) {
                let mapPlayer = MapPlayer.fromIndex(i)
                for (const id in GameUnitAmountLimitState.config) {
                    mapPlayer.setTechMaxAllowed(FourCC(id), GameUnitAmountLimitState.config[id])
                }
            }
            trigger.destroy();
        })


    }


}