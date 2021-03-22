import Base from "./Base";

export const CJ = require('jass.common')

export default class GlobalVars {


    static init() {
        //init cj 2 _G
        for (const cjKey in CJ) {
            // @ts-ignore
            _G[cjKey] = CJ[cjKey];
        }
        //init FourCC 2 _G
        _G.FourCC = Base.string2id
    }


}