import {Group} from "../w3ts/handles/group";
import BaseUtil from "./BaseUtil";
import MathUtil from "./MathUtil";

/**
 * 冲锋
 */
export default class LeapUtil {

    static leap(damage_src_handle: unit, angle: number, range: number, time: number, damage: number = 0, dmgType: damagetype = DAMAGE_TYPE_NORMAL,
                effectPath?: string, startX: number = GetUnitX(damage_src_handle), startY: number = GetUnitY(damage_src_handle)) {
        let execCount = time * 25
        let dis = range / execCount;
        //
        angle = MathUtil.angle2radian(angle)
        let effectHandle: effect = null
        if (effectPath && effectPath.length > 0) {
            effectHandle = AddSpecialEffect(effectPath, startX, startY)
        }

        let damagedUnitGroup: Group = null;
        let tempGroup: Group = null;
        let filter: filterfunc = null;
        if (damage > 0) {
            damagedUnitGroup = new Group();
            tempGroup = new Group();
            filter = Filter(() => {
                //说明已经伤害过了
                if (IsUnitInGroup(GetFilterUnit(), damagedUnitGroup.handle)) {
                    return false
                }
                if (IsUnitEnemy(GetFilterUnit(), GetOwningPlayer(damage_src_handle))) {
                    return true
                }
                return false
            });
        }
        TimerStart(CreateTimer(), 0.04, true, () => {
            if (effectHandle) {
                let x = dis * Math.cos(angle) + EXGetEffectX(effectHandle);
                let y = dis * Math.sin(angle) + EXGetEffectY(effectHandle)
                EXSetEffectXY(effectHandle, x, y);
                if (damage > 0) {
                    tempGroup.enumUnitsInRange(x, y, 128, filter)
                }

            } else {
                let x = dis * Math.cos(angle) + GetUnitX(damage_src_handle);
                let y = dis * Math.sin(angle) + GetUnitY(damage_src_handle)
                SetUnitX(damage_src_handle, x);
                SetUnitY(damage_src_handle, y);
                if (damage > 0) {
                    tempGroup.enumUnitsInRange(x, y, 128, filter)
                }
            }
            if (damage > 0) {
                tempGroup.for(() => {
                    GroupAddUnit(damagedUnitGroup.handle, GetEnumUnit());
                    UnitDamageTarget(damage_src_handle, GetEnumUnit(), damage, false, false,
                        ATTACK_TYPE_NORMAL, dmgType, WEAPON_TYPE_WHOKNOWS)
                })
            }
            //DestroyTimer
            execCount = execCount - 1;
            if (execCount < 1) {
                if (damagedUnitGroup) {
                    damagedUnitGroup.destroy();
                }
                if (tempGroup) {
                    tempGroup.destroy()
                }
                if (filter) {
                    DestroyFilter(filter)
                }
                if (effectHandle) {
                    DestroyEffect(effectHandle)
                }
                DestroyTimer(GetExpiredTimer())
            }
        })
    }


}