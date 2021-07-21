import {Trigger} from "../solar/w3ts/handles/trigger";
import {ForceUtil} from "../solar/util/ForceUtil";
import {MapPlayer} from "../solar/w3ts/handles/player";
import {Unit} from "../solar/w3ts/handles/unit";
import BaseUtil from "../solar/util/BaseUtil";
import UnitUtil from "../solar/util/UnitUtil";
import DebugUtil from "../solar/util/DebugUtil";

/**
 */
export default class PokerFetterIncreaseState {
    static config: {
        abilityIds: string[],//52张扑克技能id
        baseIncrease: number

    } = {
        abilityIds: [],
        baseIncrease: 1
    }


    constructor() {
        let trigger = new Trigger()

        trigger.registerTimerEvent(7, true)
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_CONSTRUCT_FINISH)
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_UPGRADE_FINISH)
        trigger.addAction(this.action)
        //当选择单位时设置激活信息给技能
        let refreshInfoTrigger = new Trigger()
        refreshInfoTrigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_SELECTED)
        refreshInfoTrigger.addAction(() => {
            let unit = Unit.fromEvent()
            if (unit.solarData.PokerIndex) {
                //
                let abilityId = PokerFetterIncreaseState.config.abilityIds[unit.solarData.PokerIndex as number];
                let pokerFetterTips = unit.solarData.PokerFetterIncreaseState_pokerFetterTips;
                if (pokerFetterTips) {
                    EXSetAbilityString(FourCC(abilityId), 1, 218, pokerFetterTips)
                }
            }
        })

    }

    static actionLastExecTime = 0

    action(this: void) {
        //内置执行cd
        if (BaseUtil.getGameTime() - PokerFetterIncreaseState.actionLastExecTime < 2) {
            return
        }
        PokerFetterIncreaseState.actionLastExecTime = BaseUtil.getGameTime();
        ForForce(ForceUtil.getUserForce(), () => {
            let allUnitGroupHandle = MapPlayer.fromEnum().getAllUnits()
            let playerAllPokerIndexs: { [index: number]: boolean } = {}
            ForGroup(allUnitGroupHandle, () => {
                let unit = Unit.fromEnum()
                if (!unit.solarData.PokerIndexInit) {
                    PokerFetterIncreaseState.initPokerIndex2Unit(unit)
                }
                if (unit.solarData.PokerIndex) {
                    playerAllPokerIndexs[unit.solarData.PokerIndex] = true
                }
            })
            ForGroup(allUnitGroupHandle, () => {
                let unit = Unit.fromEnum()
                PokerFetterIncreaseState.refreshIncrease(unit, playerAllPokerIndexs)
            })
            DestroyGroup(allUnitGroupHandle)
        })
    }

    static initPokerIndex2Unit(unit: Unit) {
        unit.solarData.PokerIndexInit = true;
        for (let i = 0; i < PokerFetterIncreaseState.config.abilityIds.length; i++) {
            let abilityId = PokerFetterIncreaseState.config.abilityIds[i];
            if (unit.hasAbility(abilityId)) {
                unit.solarData.PokerIndex = i;
                return
            }
        }
    }

    static refreshIncrease(unit: Unit, playerAllPokerIndexs: { [index: number]: boolean }) {
        let pokerIndex = unit.solarData.PokerIndex
        if (!pokerIndex) {
            return
        }
        let pokerFetterTips = "激活信息:|n"
        let totalIncrease = 0;
        let increaseTemp = 0
        //相同牌值
        let sameTypeCount = this.getSameValueCount(pokerIndex, playerAllPokerIndexs);
        if (sameTypeCount >= 4) {//激活4个
            increaseTemp = 4 * this.config.baseIncrease
            pokerFetterTips += "炸弹[x" + increaseTemp + "]|n"
            totalIncrease += increaseTemp
        } else if (sameTypeCount >= 3) {//激活3个
            increaseTemp = 3 * this.config.baseIncrease
            pokerFetterTips += "3张[x" + increaseTemp + "]|n"
            totalIncrease += increaseTemp
        } else if (sameTypeCount >= 2) {//激活对子
            increaseTemp = 2 * this.config.baseIncrease
            pokerFetterTips += "对子[x" + increaseTemp + "]|n"
            totalIncrease += increaseTemp
        }
        //同花
        let sameColorTypeCount = this.getSameColorTypeCount(pokerIndex, playerAllPokerIndexs);
        if (sameColorTypeCount >= 3) {//
            increaseTemp = this.config.baseIncrease * sameColorTypeCount * 0.5
            pokerFetterTips += "同花[x" + increaseTemp + "]|n"
            totalIncrease += increaseTemp
        }
        //金花
        let sameColorCount = this.getSameColorCount(pokerIndex, playerAllPokerIndexs);
        if (sameColorCount >= 3) {//
            increaseTemp = this.config.baseIncrease * sameColorCount * 0.2
            pokerFetterTips += "金花[x" + increaseTemp + "]|n"
            totalIncrease += increaseTemp
        }
        //同花顺
        let sameColorTypeStraightCount = this.getSameColorTypeStraightCount(pokerIndex, playerAllPokerIndexs);
        if (sameColorTypeStraightCount >= 3) {//
            increaseTemp = this.config.baseIncrease * sameColorTypeStraightCount * 1.2
            pokerFetterTips += "同花顺[x" + increaseTemp + "]|n"
            totalIncrease += increaseTemp
        }
        // 顺金
        let sameColorStraightCount = this.getSameColorStraightCount(pokerIndex, playerAllPokerIndexs);
        if (sameColorStraightCount >= 3) {//
            increaseTemp = this.config.baseIncrease * sameColorStraightCount * 0.8
            pokerFetterTips += "顺金[x" + increaseTemp + "]|n"
            totalIncrease += increaseTemp
        }
        // 顺子
        let straightCount = this.getStraightCount(pokerIndex, playerAllPokerIndexs);
        if (straightCount >= 3) {//
            increaseTemp = this.config.baseIncrease * straightCount * 0.6
            pokerFetterTips += "顺子[x" + increaseTemp + "]|n"
            totalIncrease += increaseTemp
        }
        // 重对顺子
        let straightPairsCount = this.getStraightPairsCount(pokerIndex, playerAllPokerIndexs);
        if (straightPairsCount >= 3) {//
            increaseTemp = this.config.baseIncrease * straightPairsCount * 0.8
            pokerFetterTips += "连对[x" + increaseTemp + "]|n"
            totalIncrease += increaseTemp
        }
        //2加成
        if (totalIncrease > 0 && pokerIndex >= 4 && pokerIndex < 8) {
            increaseTemp = this.config.baseIncrease * 10
            pokerFetterTips += "2豹子[x" + increaseTemp + "]|n"
            totalIncrease = totalIncrease + increaseTemp;//2加成
        }
        if (totalIncrease > 0) {
            pokerFetterTips = pokerFetterTips + "总计增幅[x" + totalIncrease + "]"
        }
        //设置奖励
        if (!unit.solarData.PokerFetterIncreaseState_totalIncrease || unit.solarData.PokerFetterIncreaseState_totalIncrease != totalIncrease) {
            //基础伤害
            let baseD = GetUnitState(GetTriggerUnit(), ConvertUnitState(0x12));
            if (unit.solarData.PokerFetterIncreaseState_add_dmg) {
                baseD = baseD - unit.solarData.PokerFetterIncreaseState_add_dmg;
            }
            unit.solarData.PokerFetterIncreaseState_add_dmg = baseD * totalIncrease
            SetUnitState(unit.handle, ConvertUnitState(0x12), baseD + unit.solarData.PokerFetterIncreaseState_add_dmg)
            //英雄三维
            UnitUtil.addHeroPropertyByRate(unit, "PokerFetterIncreaseState_add_Property", totalIncrease)
        }
        unit.solarData.PokerFetterIncreaseState_totalIncrease = totalIncrease;
        unit.solarData.PokerFetterIncreaseState_pokerFetterTips = pokerFetterTips;
    }

    /**
     *  poker = 52张牌的index 包括了牌值 与牌的颜色
     *  pokerValue =牌值 1-13; 1=3 12=A 13=2
     */
    //获取相同牌值的张数 判断是对子 还是3个 还是4个(炸弹)
    static getSameValueCount(pokerIndex: number, playerAllPokerIndexs: { [index: number]: boolean }) {
        let pokerColorTypeIndex = pokerIndex % 4;//黑桃 红心 梅花 方块
        let sameTypeCount = 0;
        for (let i = pokerIndex - pokerColorTypeIndex; i < pokerIndex - pokerColorTypeIndex + 4; i++) {
            if (playerAllPokerIndexs[i]) {
                sameTypeCount++;
            }
        }
        return sameTypeCount;
    }

    //获取相同颜色类型的张数 判断同花数量
    static getSameColorTypeCount(pokerIndex: number, playerAllPokerIndexs: { [index: number]: boolean }) {
        let pokerColorTypeIndex = pokerIndex % 4;//黑桃 红心 梅花 方块
        let count = 0;
        for (let i = 0; i < 13; i++) {
            if (playerAllPokerIndexs[i * 4 + pokerColorTypeIndex]) {
                count++;
            }
        }
        return count;
    }

    //获取相同颜色的张数 判断金花数量
    static getSameColorCount(pokerIndex: number, playerAllPokerIndexs: { [index: number]: boolean }) {
        let pokerColorTypeIndex = pokerIndex % 2;//黑色 红色
        let count = 0;
        for (let i = 0; i < 26; i++) {
            if (playerAllPokerIndexs[i * 2 + pokerColorTypeIndex]) {
                count++;
            }
        }
        return count;
    }


    //获取同花顺数量
    public static getSameColorTypeStraightCount(pokerIndex: number, playerAllPokerIndexs: { [index: number]: boolean }): number {
        let pokerColorTypeIndex = pokerIndex % 4;//黑桃 红心 梅花 方块
        let lineIndex = Math.floor(pokerIndex / 4);
        if (lineIndex == 1) {
            return 0//2没有顺子
        }

        let count = 1;
        for (let i = lineIndex + 1; i < 14; i++) {
            if (i == 13) {
                i = 0;//A在第一行
                if (playerAllPokerIndexs[i * 4 + pokerColorTypeIndex]) {
                    count++;
                }
                break
            }
            if (playerAllPokerIndexs[i * 4 + pokerColorTypeIndex]) {
                count++;
            } else {
                break
            }
        }
        for (let i = lineIndex - 1; i > 1; i--) {
            if (playerAllPokerIndexs[i * 4 + pokerColorTypeIndex]) {
                count++;
            } else {
                break
            }
        }
        return count;
    }

    //获取顺金数量
    public static getSameColorStraightCount(pokerIndex: number, playerAllPokerIndexs: { [index: number]: boolean }): number {
        let pokerColorTypeIndex = pokerIndex % 2;//黑桃 红心 梅花 方块
        let lineIndex = Math.floor(pokerIndex / 4);
        if (lineIndex == 1) {
            return 0//2没有顺子
        }

        let count = 1;
        for (let i = lineIndex + 1; i < 14; i++) {
            if (i == 13) {
                i = 0;//A在第一行
                if (playerAllPokerIndexs[i * 4 + pokerColorTypeIndex] || playerAllPokerIndexs[i * 4 + pokerColorTypeIndex + 2]) {
                    count++;
                }
                break
            }
            if (playerAllPokerIndexs[i * 4 + pokerColorTypeIndex] || playerAllPokerIndexs[i * 4 + pokerColorTypeIndex + 2]) {
                count++;
            } else {
                break
            }
        }
        for (let i = lineIndex - 1; i > 1; i--) {
            if (playerAllPokerIndexs[i * 4 + pokerColorTypeIndex] || playerAllPokerIndexs[i * 4 + pokerColorTypeIndex + 2]) {
                count++;
            } else {
                break
            }
        }
        return count;
    }

    //获取顺子数量
    public static getStraightCount(pokerIndex: number, playerAllPokerIndexs: { [index: number]: boolean }): number {
        let lineIndex = Math.floor(pokerIndex / 4);
        if (lineIndex == 1) {
            return 0//2没有顺子
        }

        let count = 1;
        for (let i = lineIndex + 1; i < 14; i++) {
            if (i == 13) {
                i = 0;//A在第一行
                if (playerAllPokerIndexs[i * 4] || playerAllPokerIndexs[i * 4 + 1] || playerAllPokerIndexs[i * 4 + 2] || playerAllPokerIndexs[i * 4 + 3]) {
                    count++;
                }
                break
            }
            if (playerAllPokerIndexs[i * 4] || playerAllPokerIndexs[i * 4 + 1] || playerAllPokerIndexs[i * 4 + 2] || playerAllPokerIndexs[i * 4 + 3]) {
                count++;
            } else {
                break
            }
        }
        for (let i = lineIndex - 1; i > 1; i--) {
            if (playerAllPokerIndexs[i * 4] || playerAllPokerIndexs[i * 4 + 1] || playerAllPokerIndexs[i * 4 + 2] || playerAllPokerIndexs[i * 4 + 3]) {
                count++;
            } else {
                break
            }
        }
        return count;
    }

    //获取重对顺子数量
    public static getStraightPairsCount(pokerIndex: number, playerAllPokerIndexs: { [index: number]: boolean }): number {
        let lineIndex = Math.floor(pokerIndex / 4);
        if (lineIndex == 1) {
            return 0//2没有顺子
        }
        if (this.getSameValueCount(pokerIndex, playerAllPokerIndexs) < 2) {
            return 0;
        }

        let count = 1;
        for (let i = lineIndex + 1; i < 14; i++) {
            if (i == 13) {
                i = 0;//A在第一行
                if (this.getSameValueCount(i * 4, playerAllPokerIndexs) >= 2) {
                    count++;
                }
                break
            }
            if (this.getSameValueCount(i * 4, playerAllPokerIndexs) >= 2) {
                count++;
            } else {
                break
            }
        }
        for (let i = lineIndex - 1; i > 1; i--) {
            if (this.getSameValueCount(i * 4, playerAllPokerIndexs) >= 2) {
                count++;
            } else {
                break
            }
        }
        return count;
    }

}