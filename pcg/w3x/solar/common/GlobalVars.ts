import Base from "./Base";

let CJ = require('jass.common')
let globals = require('jass.globals')
let japi = require('jass.japi')
let ai = require('jass.ai')
let slk = require('jass.slk')
let runtime = require('jass.runtime')
let debug = require('jass.debug')

export default class GlobalVars {


    static init() {
        (runtime as any).handle_level = 2
        //init cj 2 _G
        for (const cjKey in CJ) {
            // @ts-ignore
            _G[cjKey] = CJ[cjKey];
        }
        //init ai 2 _G
        for (const cjKey in ai) {
            // @ts-ignore
            _G[cjKey] = ai[cjKey];
        }
        //init globals 2 _G
        for (const gk in globals) {
            // @ts-ignore
            _G[gk] = globals[gk];
        }
        //init japi 2 _G
        for (const gk in japi) {
            // @ts-ignore
            _G[gk] = japi[gk];
        }
        //init slk 2 _G
        for (const gk in slk) {
            // @ts-ignore
            _G[gk] = slk[gk];
        }
        //init FourCC 2 _G
        _G.FourCC = Base.string2id
        _G.id2string = Base.id2string
        // @ts-ignore
        _G.globals = globals
    }


}