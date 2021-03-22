let runtime: NoSelf = require('jass.runtime')
let debug: NoSelf = require('jass.debug')

export default class Develop {


    static open() {
        //test
        (runtime as any).debugger = 4279;
        (runtime as any).console = true;
        print("runtime.version" + ((runtime as any)["version"]));
        (runtime as any).error_handle = function (this: void, msg: any) {
            print("---------------------------------------")
            print("              LUA ERROR!!              ")
            print("---------------------------------------")
            print(tostring(msg) + "\n")
            // @ts-ignore
            print(debug.traceback())
            print("---------------------------------------")
        }
    }


}