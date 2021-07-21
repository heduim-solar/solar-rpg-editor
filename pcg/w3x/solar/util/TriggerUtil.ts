export default class TriggerUtil {

    private static DamageEventTrigger: trigger = null

    private static DAMAGE_EVENT_SWAP_TIMEOUT = 600  // 每隔这个时间(秒), DamageEventTrigger 会被移入销毁队列
    private static DAMAGE_EVENT_SWAP_ENABLE = true  // 若为 false 则不启用销毁机制
    private static DamageEventTriggerToDestory: trigger = null

    private static DamageEventQueue: trigger[] = []
    private static DamageEventNumber: number = 0


    public static SystemAnyUnitDamagedRegistTrigger(trg: trigger): void {
        if (trg == null) {
            return
        }
        if (TriggerUtil.DamageEventNumber == 0) {
            TriggerUtil.DamageEventTrigger = CreateTrigger()
            TriggerAddAction(TriggerUtil.DamageEventTrigger, TriggerUtil.AnyUnitDamagedTriggerAction)
            TriggerUtil.AnyUnitDamagedEnumUnit()
            TriggerUtil.AnyUnitDamagedRegistTriggerUnitEnter()
            if (TriggerUtil.DAMAGE_EVENT_SWAP_ENABLE) {
                // 每隔 DAMAGE_EVENT_SWAP_TIMEOUT 秒, 将正在使用的 DamageEventTrigger 移入销毁队列
                TimerStart(CreateTimer(), TriggerUtil.DAMAGE_EVENT_SWAP_TIMEOUT, true, TriggerUtil.SyStemAnyUnitDamagedSwap)
            }
        }
        TriggerUtil.DamageEventQueue[TriggerUtil.DamageEventNumber] = trg
        TriggerUtil.DamageEventNumber = TriggerUtil.DamageEventNumber + 1
    }


    private static AnyUnitDamagedTriggerAction(this: void): void {
        let i = 0

        while (i < TriggerUtil.DamageEventNumber) {
            if (TriggerUtil.DamageEventQueue[i] != null && IsTriggerEnabled(TriggerUtil.DamageEventQueue[i]) && TriggerEvaluate(TriggerUtil.DamageEventQueue[i])) {
                TriggerExecute(TriggerUtil.DamageEventQueue[i])
            }
            i = i + 1
        }

    }

    private static AnyUnitDamagedFilter(this: void): boolean {
        if (GetUnitAbilityLevel(GetFilterUnit(), FourCC('Aloc')) <= 0) {
            TriggerRegisterUnitEvent(TriggerUtil.DamageEventTrigger, GetFilterUnit(), EVENT_UNIT_DAMAGED)
        }
        return false
    }


    private static AnyUnitDamagedEnumUnit(this: void): void {
        let g: group = CreateGroup()
        let i = 0
        while (i < 16) {
            GroupEnumUnitsOfPlayer(g, Player(i), Condition(TriggerUtil.AnyUnitDamagedFilter))
            i = i + 1
        }
        DestroyGroup(g)
    }

    private static AnyUnitDamagedRegistTriggerUnitEnter(this: void): void {
        let t: trigger = CreateTrigger()
        let r: region = CreateRegion()
        let world: rect = GetWorldBounds()
        RegionAddRect(r, world)
        TriggerRegisterEnterRegion(t, r, Condition(TriggerUtil.AnyUnitDamagedFilter))
        RemoveRect(world)
    }

    // 将 DamageEventTrigger 移入销毁队列, 从而排泄触发器事件
    private static SyStemAnyUnitDamagedSwap(this: void): void {
        let isEnabled: boolean = IsTriggerEnabled(TriggerUtil.DamageEventTrigger)

        DisableTrigger(TriggerUtil.DamageEventTrigger)
        if (TriggerUtil.DamageEventTriggerToDestory != null) {
            DestroyTrigger(TriggerUtil.DamageEventTriggerToDestory)
        }
        TriggerUtil.DamageEventTriggerToDestory = TriggerUtil.DamageEventTrigger
        TriggerUtil.DamageEventTrigger = CreateTrigger()
        if (!isEnabled) {
            DisableTrigger(TriggerUtil.DamageEventTrigger)
        }

        TriggerAddAction(TriggerUtil.DamageEventTrigger, TriggerUtil.AnyUnitDamagedTriggerAction)
        TriggerUtil.AnyUnitDamagedEnumUnit()
    }


}