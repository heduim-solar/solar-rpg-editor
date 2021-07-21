//base
import Develop from "./solar/common/Develop";

Develop.open();
import GlobalVars from "./solar/common/GlobalVars";

GlobalVars.init()
import BaseUtil from "./solar/util/BaseUtil";

BaseUtil.init()
// app imports
import AppTest from "./AppTest";
import StateInit from "./StateInit";
import StateConfigInit from "./StateConfigInit";


export default class App {


    start() {

        //show text for we know app is starting
        DisplayTimedTextToPlayer(Player(0), 0, 0, 60, 'TS:App.start!');

        //state init
        StateConfigInit();
        StateInit();

        //ecs init
        // SolarEcs.init();
        //just test
        new AppTest().start()

    }

}

new App().start()