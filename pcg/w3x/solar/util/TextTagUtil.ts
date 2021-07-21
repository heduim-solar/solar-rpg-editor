import {Unit} from "../w3ts/handles/unit";
import {TextTag} from "../w3ts/handles/texttag";

/** @noSelfInFile **/
export default class TextTagUtil {


    static textOnUnit(unit: unit, text: string, red: number = 255, green: number = 255, blue: number = 255): TextTag {
        let textTag = new TextTag();
        textTag.setColor(red, green, blue, 255)
        textTag.setText(text, 15, true)
        SetTextTagPosUnit(textTag.handle, unit, 100);
        textTag.setVelocity(0, 0.02)
        textTag.setPermanent(false)
        textTag.setLifespan(text.length)
        return textTag
    }


}