export class ForceUtil {

    static UserForce: force = null;

    /**
     * 获取用户玩家组
     */
    static getUserForce(): force {
        if (ForceUtil.UserForce) {
            return ForceUtil.UserForce;
        }
        let force = CreateForce()
        let conditionfunc = Condition(() => {
            return GetPlayerController(GetFilterPlayer()) == MAP_CONTROL_USER
        });
        ForceEnumPlayers(force, conditionfunc)
        DestroyBoolExpr(conditionfunc)
        ForceUtil.UserForce = force;
        return force;
    }


}