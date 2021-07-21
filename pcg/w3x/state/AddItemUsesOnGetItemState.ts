import {Trigger} from "../solar/w3ts/handles/trigger";

/**
 * 当获得物品时叠加物品使用次数
 * 可叠加使用次数的物品的实现
 */
export default class AddItemUsesOnGetItemState {
    static config: { [id: string]: boolean } = {}

    constructor() {
        this.init();
    }


    init() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_PICKUP_ITEM)
        trigger.addAction(() => {
            let item = GetManipulatedItem()
            let itemTypeId = GetItemTypeId(GetManipulatedItem())
            let itemTypeIdStr = id2string(itemTypeId);
            let flag = AddItemUsesOnGetItemState.config[itemTypeIdStr];
            if (!flag) {
                return
            }
            //one case
            let triggerUnitHandle = GetTriggerUnit()
            for (let i = 0; i < 6; i++) {
                let unitSlotItem = UnitItemInSlot(triggerUnitHandle, i);
                let unitSlotItemTypeId = GetItemTypeId(unitSlotItem)
                if (item != unitSlotItem && unitSlotItemTypeId == itemTypeId) {
                    SetItemCharges(unitSlotItem, GetItemCharges(unitSlotItem) + math.max(GetItemCharges(item),1))
                    RemoveItem(GetManipulatedItem())
                }
            }
        })


    }


}