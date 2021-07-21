export default class RandomUtil {


    static nextInt(min: number, max: number): number {
        return GetRandomInt(min, max);
    }

    static nextReal(min: number, max: number): number {
        return GetRandomReal(min, max);
    }


    static getRandomKeyByWeight(objAndWeight: { [key: string]: number }): string {
        let max = 0;
        for (let objAndWeightKey in objAndWeight) {
            max = max + objAndWeight[objAndWeightKey];
        }
        let ri = RandomUtil.nextReal(0, max);
        max = 0;
        for (let objAndWeightKey in objAndWeight) {
            max = max + objAndWeight[objAndWeightKey];
            if (ri <= max) {
                return objAndWeightKey;
            }
        }
        return null;
    }


}