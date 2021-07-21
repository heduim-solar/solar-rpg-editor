import AddItemUsesOnGetItemState from "./state/AddItemUsesOnGetItemState";
import ExecTargetattach5ScriptOnSpellState from "./state/ExecTargetattach5ScriptOnSpellState";
import ItemSimulationAttributeState from "./state/ItemSimulationAttributeState";
import StrengthenItemOnUseItemState from "./state/StrengthenItemOnUseItemState";
import SolarDamageState from "./state/SolarDamageState";
import BlinkOnTypeDState from "./state/ability/BlinkOnTypeDState";

/**
 * azhr = 埃苏尼之心
 * gmfr = 宝石碎片
 * @constructor
 */
export default function StateTestInit() {
    new SolarDamageState();
    // AddItemUsesOnGetItemState.config.afac = true
    // AddItemUsesOnGetItemState.config.ssil = true
    // AddItemUsesOnGetItemState.config.stel = true
    // // new AddItemUsesOnGetItemState();
    // new ExecTargetattach5ScriptOnSpellState();
    ItemSimulationAttributeState.config.azhr = {attack: 100, damage_increased: 5.5}
    StrengthenItemOnUseItemState.config.azhr = {
        strengthen_gold_cost: 100,
        strengthen_lumber_cost: 200,
        strengthen_rate: 2
    }
    ItemSimulationAttributeState.config.gmfr = {
        attack: 200,
        physical_critical_damage: 10.5,
        physical_critical_chance: 0.3
    }
    StrengthenItemOnUseItemState.config.gmfr = {
        strengthen_gold_cost: 1000,
        strengthen_lumber_cost: 2000,
        strengthen_rate: 1,
        strengthen_max_level: 5
    }
    new ItemSimulationAttributeState();
    new StrengthenItemOnUseItemState();
    BlinkOnTypeDState.config.AIha = {costMana:10,maxRange:600}
    new BlinkOnTypeDState();

}