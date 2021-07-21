import SolarEventDispatchState from "./state/SolarEventDispatchState";

export default class XlsxDataUtil {

    public static init() {
        let itemXlsxDataArray: { [id: string]: any }[] = require("xxxx").default
        this.init_SolarEventDispatchState(itemXlsxDataArray);
        // let abilityXlsxDataArray: { [id: string]: any }[] = require("jineng")
        // this.init_SolarEventDispatchState(abilityXlsxDataArray);


    }

    private static init_SolarEventDispatchState(xlsxDataArray: { [id: string]: any }[]) {
        for (let data of xlsxDataArray) {
            if (data.onAttackByItem) {
                SolarEventDispatchState.config.onAttackByItem[data.id] = data.onAttackByItem
            }
            if (data.onUnderAttackByItem) {
                SolarEventDispatchState.config.onUnderAttackByItem[data.id] = data.onUnderAttackByItem
            }
            if (data.onKillByItem) {
                SolarEventDispatchState.config.onKillByItem[data.id] = data.onKillByItem
            }
            if (data.onUseItem) {
                SolarEventDispatchState.config.onUseItem[data.id] = data.onUseItem
            }
            if (data.onAttackByAbility) {
                SolarEventDispatchState.config.onAttackByAbility[data.id] = data.onAttackByAbility
            }
            if (data.onUnderAttackByAbility) {
                SolarEventDispatchState.config.onUnderAttackByAbility[data.id] = data.onUnderAttackByAbility
            }
            if (data.onKillByAbility) {
                SolarEventDispatchState.config.onKillByAbility[data.id] = data.onKillByAbility
            }
            if (data.onCastAbility) {
                SolarEventDispatchState.config.onCastAbility[data.id] = data.onCastAbility
            }
        }

    }

}

