export default class DataBase {
    private static dataBaseContext: { [id: string]: any } = {}


    public static getData(type: string, handle: handle): any {
        let key = type + GetHandleId(handle)
        let data = this.dataBaseContext[key]
        if (!data) {
            data = {}
            this.dataBaseContext[key] = data
        }
        return data;
    }

    public static clearData(type: string, handle: handle): any {
        let key = type + GetHandleId(handle)
        this.dataBaseContext[key] = null
    }


}