import {Entity, System} from "../solar/ecs/Ecs";
import {Unit} from "../solar/w3ts/handles/unit";
import UnitCom from "../solar/ecs/UnitCom";
import {Item} from "../solar/w3ts/handles/item";
import TransferItem2UnitCom from "../component/TransferItem2UnitCom";
import {Trigger} from "../solar/w3ts/handles/trigger";

export default class TransferItem2UnitSystem extends System {
    static config: {
        [id: string]: {
            slot: number;
            count: number;
        }
    } = {}


    constructor() {
        super([
            UnitCom.type,
            TransferItem2UnitCom.type,
        ], 1000);
        this.init();
    }

    init() {
        let trigger = new Trigger()
        trigger.registerAnyUnitEvent(EVENT_PLAYER_UNIT_SPELL_EFFECT)
        trigger.addAction(() => {
            //one case
            let data = TransferItem2UnitSystem.config[id2string(GetSpellAbilityId())]
            if (data) {
                let unit = Unit.fromEvent()
                let entity = unit.entity;
                if (entity) {
                    this.destroyEntityLightning(entity)
                    let addCom = new TransferItem2UnitCom({
                        slot: data.slot,
                        count: data.count,
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
        let transferItem2UnitCom = TransferItem2UnitCom.oneFrom(entity);
        let data = transferItem2UnitCom.data;
        //one case
        if (data) {
            if (!data.unit.isAlive()) {
                entity.remove(transferItem2UnitCom)
                this.destroyEntityLightning(entity)
                return
            }

            let item = unit.getItemInSlot(data.slot);
            if (item.charges > data.count) {
                item.charges = item.charges - data.count;
                let addItem = new Item(item.typeId, data.unit.x, data.unit.y)
                addItem.charges = data.count;
                data.unit.addItem(addItem)
            }

        }
    }

    //one case
    exit(entity: Entity): void {
        this.destroyEntityLightning(entity)
    }

    addLightning(entity: Entity): void {
        let unit = UnitCom.oneFrom(entity).data.value;
        let transferItem2UnitCom = TransferItem2UnitCom.oneFrom(entity);
        let data = transferItem2UnitCom.data;
        let l: lightning = AddLightningEx("CLSB", true, unit.x, unit.y, 200,
            data.unit.x, data.unit.y, 200);
        (entity as any).lightning = l;
    }

    destroyEntityLightning(entity: Entity) {
        let l: lightning = (entity as any).lightning
        if (l) {
            DestroyLightning(l)
        }
    }
}

