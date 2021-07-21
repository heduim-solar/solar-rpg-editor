import {Unit} from "../w3ts/handles/unit";

export default class UnitUtil {


    static GetInventoryIndexOfItemType(unitHandle: unit, id: string): number {
        let idint = FourCC(id);

        for (let i = 0; i < 6; i++) {
            let indexItem = UnitItemInSlot(unitHandle, i)
            if (indexItem != null && GetItemTypeId(indexItem) == idint) {
                return i + 1
            }
        }
        return 0
    }

    static GetInventoryOfItemType(unitHandle: unit, idint: number): item {
        for (let i = 0; i < 6; i++) {
            let indexItem = UnitItemInSlot(unitHandle, i)
            if (indexItem != null && GetItemTypeId(indexItem) == idint) {
                return indexItem
            }
        }
        return null
    }


    static addHeroProperty(unit: Unit, key: string, addNum: number) {
        if (!unit.isHero()) {
            return
        }
        //力量
        let baseStr = unit.strength;
        if (unit.solarData['UnitUtil_addHeroProperty_strength_' + key]) {
            baseStr = baseStr - unit.solarData['UnitUtil_addHeroProperty_strength_' + key];
        }
        unit.solarData['UnitUtil_addHeroProperty_strength_' + key] = addNum
        unit.strength = baseStr + addNum
        //敏捷
        let baseAgi = unit.agility;
        if (unit.solarData['UnitUtil_addHeroProperty_agility_' + key]) {
            baseAgi = baseAgi - unit.solarData['UnitUtil_addHeroProperty_agility_' + key];
        }
        unit.solarData['UnitUtil_addHeroProperty_agility_' + key] = addNum
        unit.agility = baseAgi + addNum
        //智力
        let baseInt = unit.intelligence;
        if (unit.solarData['UnitUtil_addHeroProperty_intelligence_' + key]) {
            baseInt = baseInt - unit.solarData['UnitUtil_addHeroProperty_intelligence_' + key];
        }
        unit.solarData['UnitUtil_addHeroProperty_intelligence_' + key] = addNum
        unit.intelligence = baseInt + addNum
        //
    }

    static addHeroPropertyByRate(unit: Unit, key: string, addRate: number) {
        if (!unit.isHero()) {
            return
        }
        let addNum = 0
        //力量
        let baseStr = unit.strength;
        if (unit.solarData['UnitUtil_addHeroPropertyByRate_strength_' + key]) {
            baseStr = baseStr - unit.solarData['UnitUtil_addHeroPropertyByRate_strength_' + key];
        }
        addNum = baseStr * addRate
        unit.solarData['UnitUtil_addHeroPropertyByRate_strength_' + key] = addNum
        unit.strength = baseStr + addNum
        //敏捷
        let baseAgi = unit.agility;
        if (unit.solarData['UnitUtil_addHeroPropertyByRate_agility_' + key]) {
            baseAgi = baseAgi - unit.solarData['UnitUtil_addHeroPropertyByRate_agility_' + key];
        }
        addNum = baseAgi * addRate
        unit.solarData['UnitUtil_addHeroPropertyByRate_agility_' + key] = addNum
        unit.agility = baseAgi + addNum
        //智力
        let baseInt = unit.intelligence;
        if (unit.solarData['UnitUtil_addHeroPropertyByRate_intelligence_' + key]) {
            baseInt = baseInt - unit.solarData['UnitUtil_addHeroPropertyByRate_intelligence_' + key];
        }
        addNum = baseInt * addRate
        unit.solarData['UnitUtil_addHeroPropertyByRate_intelligence_' + key] = addNum
        unit.intelligence = baseInt + addNum
        //
    }

}