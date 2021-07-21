export type FrameUi = {
    name: string;
    type: string;
    x: number;
    y: number;
    w: number;
    h: number;
    visible: boolean;
    parent?: string;
    switchVisible?: string;//开关指定name的 显示隐藏

}

export default class FrameUiState {

    static config: {
        tocPaths: string[]
        frameUis: FrameUi[]
    } = {
        tocPaths: [],
        frameUis: []
    }
    //ui name 与 初始后的frame handle
    static data: {
        [name: string]: {
            handle: number,
            visible: boolean
        }
    } = {}

    constructor() {
        for (let str of FrameUiState.config.tocPaths) {
            DzLoadToc(str)
        }
        //
        for (let frameUi of FrameUiState.config.frameUis) {
            this.initFrameUi(frameUi)
        }
    }


    initFrameUi(frameUi: FrameUi) {
        //
        let parent: number = DzGetGameUI();
        if (frameUi.parent) {
            parent = FrameUiState.data[frameUi.parent].handle;
        }

        let frameUiHandle: number = DzCreateFrameByTagName(frameUi.type, frameUi.name, parent, frameUi.name, 0)
        DzFrameSetPoint(frameUiHandle, 6, parent, 6, frameUi.x, frameUi.y)
        DzFrameSetSize(frameUiHandle, frameUi.w, frameUi.h)
        DzFrameShow(frameUiHandle, frameUi.visible)
        FrameUiState.data[frameUi.name] = {handle: frameUiHandle, visible: frameUi.visible};
        //开关指定name的 显示隐藏
        if (frameUi.switchVisible) {
            //1=点击时
            DzFrameSetScriptByCode(frameUiHandle, 1, () => {
                FrameUiState.data[frameUi.switchVisible].visible = !FrameUiState.data[frameUi.switchVisible].visible;
                DzFrameShow(FrameUiState.data[frameUi.switchVisible].handle, FrameUiState.data[frameUi.switchVisible].visible)
            }, false)
        }
    }


}