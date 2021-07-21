import {Entity, System} from "../solar/ecs/Ecs";
import {Unit} from "../solar/w3ts/handles/unit";
import UnitCom from "../solar/ecs/UnitCom";
import {Item} from "../solar/w3ts/handles/item";
import BalancedUnitItemOnTimerCom from "../component/BalancedUnitItemOnTimerCom";
import {Trigger} from "../solar/w3ts/handles/trigger";

export default class BalancedUnitItemOnTimerSystem extends System {
    static config: { [id: string]: boolean } = {}


    constructor() {
        super([
            UnitCom.type,
            BalancedUnitItemOnTimerCom.type,
        ], 1000);
        this.init();
    }

    init() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_SPELL_EFFECT)
        trigger.addAction(() => {
            //one case
            let data = BalancedUnitItemOnTimerSystem.config[id2string(GetSpellAbilityId())]
            if (data) {
                let unit = Unit.fromEvent()
                let entity = unit.entity;
                if (entity) {
                    this.destroyEntityLightning(entity)
                    let addCom = new BalancedUnitItemOnTimerCom({
                        unit: Unit.fromHandle(GetSpellTargetUnit())
                    });
                    entity.set(addCom)
                    this.addLightning(entity)
                }
            }

        })

    }


    update(time: number, delta: number, entity: Entity): void {
        let unit = UnitCom.oneFrom(entity).data.value;
        let transferItem2UnitCom = BalancedUnitItemOnTimerCom.oneFrom(entity);
        let data = transferItem2UnitCom.data;
        //one case
        if (data) {
            if (!data.unit.isAlive()) {
                entity.remove(transferItem2UnitCom)
                this.destroyEntityLightning(entity)
                return
            }

            let item = unit.getItemInSlot(0);
            let item2 = data.unit.getItemInSlot(0);
            if (item!=null&&item2!=null&&item.typeId!=item2.typeId){
                return;
            }
            let totalCharges = 0;
            if (item != null) {
                totalCharges = totalCharges + item.charges
            }
            if (item2 != null) {
                totalCharges = totalCharges + item2.charges
            }
            if (totalCharges < 2) {
                return;
            }

            let charges1 = math.floor(totalCharges / 2);
            if (item == null) {
                item = new Item(item2.typeId, unit.x, unit.y)
                unit.addItem(item)
            }
            item.charges = charges1;
            if (item2 == null) {
                item2 = new Item(item.typeId, data.unit.x, data.unit.y)
                data.unit.addItem(item2)
            }
            item2.charges = totalCharges - charges1;

        }
    }

    //one case
    exit(entity: Entity): void {
        this.destroyEntityLightning(entity)
    }

    addLightning(entity: Entity): void {
        let unit = UnitCom.oneFrom(entity).data.value;
        let transferItem2UnitCom = BalancedUnitItemOnTimerCom.oneFrom(entity);
        let data = transferItem2UnitCom.data;
        let l: lightning = AddLightningEx("HWPB", true, unit.x, unit.y, 200,
            data.unit.x, data.unit.y, 200);
        (entity as any).BalancedUnitItemOnTimerSystem_lightning = l;
    }

    destroyEntityLightning(entity: Entity) {
        let l: lightning = (entity as any).BalancedUnitItemOnTimerSystem_lightning
        if (l) {
            DestroyLightning(l)
        }
    }
}

