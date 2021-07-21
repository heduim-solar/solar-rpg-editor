export default class Base {
    static ids1: any = {}
    static ids2: any = {}

    static _id(a: number) {
        // @ts-ignore
        let r = '>I4'.pack(a);
        this.ids1[a] = r
        this.ids2[r] = a
        return r
    }

    static id2string(this: void,a: number) {
        let str = Base.ids1[a];
        if (str) {
            return str;
        }
        return Base._id(a)
    }

    static __id2(a: string) {
        // @ts-ignore
        let r = ('>I4').unpack(a)
        this.ids2[a] = r
        this.ids1[r] = a
        return r
    }

    static string2id(this: void, a: string) {

        let str = Base.ids2[a];
        if (str) {
            return str;
        }
        return Base.__id2(a)
    }


}