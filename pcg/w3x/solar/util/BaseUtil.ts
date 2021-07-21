/** @noSelf **/
export default class BaseUtil {


    static calculateDamageByPropertySet(unitHandle: unit, dmgPropertySet: {
        strDamageRate?: number,
        agiDamageRate?: number,
        intDamageRate?: number,
        hpDamageRate?: number,
        manaDamageRate?: number,
    }): number {
        let damage = 0;
        if (dmgPropertySet.strDamageRate) {
            damage = GetHeroStr(unitHandle, true) * dmgPropertySet.strDamageRate + damage;
        }
        if (dmgPropertySet.agiDamageRate) {
            damage = GetHeroAgi(unitHandle, true) * dmgPropertySet.agiDamageRate + damage;
        }
        if (dmgPropertySet.intDamageRate) {
            damage = GetHeroInt(unitHandle, true) * dmgPropertySet.intDamageRate + damage;
        }
        if (dmgPropertySet.hpDamageRate) {
            damage = GetUnitState(unitHandle, UNIT_STATE_LIFE) * dmgPropertySet.hpDamageRate + damage;
        }
        if (dmgPropertySet.manaDamageRate) {
            damage = GetUnitState(unitHandle, UNIT_STATE_MANA) * dmgPropertySet.hpDamageRate + damage;
        }
        return damage;
    }

    static runLater(timeOut: number, handlerFunc: () => void) {
        TimerStart(CreateTimer(), timeOut, false, () => {
            handlerFunc()
            DestroyTimer(GetExpiredTimer())
        })
    }

    static centerTimer: timer = null;

    static getGameTime(): number {
        return TimerGetElapsed(this.centerTimer);
    }

    static init() {
        BaseUtil.centerTimer = CreateTimer();
        TimerStart(BaseUtil.centerTimer, 999999, false, () => {
            DestroyTimer(GetExpiredTimer())
        })
    }

}

