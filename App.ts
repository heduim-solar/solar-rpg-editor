import AppTest from "./AppTest";
import GlobalVars from "./solar/common/GlobalVars";
import Develop from "./solar/common/Develop";
import {Unit} from "./solar/w3ts/handles/unit";

Develop.open();
GlobalVars.init()

/** @noSelf **/
export default class App {

    start() {

        
        DisplayTimedTextToPlayer(Player(0), 0, 0, 60, 'App.start():');
        let unit = new Unit(0, FourCC("hpea"), 0, 0, 0)
        DisplayTimedTextToPlayer(Player(0), 0, 0, 60, 'unit.acquireRange=' + unit.acquireRange);

        //just test
        // new AppTest().start()
    }

}

new App().start()
