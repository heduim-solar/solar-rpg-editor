let runtime: NoSelf = require('jass.runtime')
let jDebug: NoSelf = require('jass.debug')

export default class Develop {


    static open() {
        //test
        (runtime as any).debugger = 4279;
        (runtime as any).console = true;
        //de 0
        print("runtime.version" + ((runtime as any)["version"]));
        (runtime as any).error_handle = function (this: void, msg: any) {
            print("---------------------------------------")
            print("              LUA ERROR!!              ")
            print("---------------------------------------")
            print(tostring(msg) + "\n")
            let tb = debug.traceback();
            print(tb);
            // print(debug.traceback())
            print("---------------------------------------")
        }
    }


}