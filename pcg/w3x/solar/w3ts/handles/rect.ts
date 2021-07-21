/** @noSelfInFile **/

import { Handle } from "./handle";
import { Point } from "./point";

export class Rectangle extends Handle<rect> {

  constructor(minX: number, minY: number, maxX: number, maxY: number) {
    let mapMinX = GetRectMinX(bj_mapInitialPlayableArea);
    let mapMinY = GetRectMinY(bj_mapInitialPlayableArea);
    let mapMaxX = GetRectMaxX(bj_mapInitialPlayableArea);
    let mapMaxY = GetRectMaxY(bj_mapInitialPlayableArea);

    minX = math.max(minX, mapMinX);
    minY = math.max(minY, mapMinY);
    maxX = math.min(maxX, mapMaxX);
    maxY = math.min(maxY, mapMaxY);

    if (Handle.initFromHandle()) {
      super();
    } else {
      super(Rect(minX, minY, maxX, maxY));
    }
  }

  public get centerX() {
    return GetRectCenterX(this.handle);
  }

  public get centerY() {
    return GetRectCenterY(this.handle);
  }

  public get maxX() {
    return GetRectMaxX(this.handle);
  }

  public get maxY() {
    return GetRectMaxY(this.handle);
  }

  public get minX() {
    return GetRectMinX(this.handle);
  }

  public get minY() {
    return GetRectMinY(this.handle);
  }

  public destroy() {
    RemoveRect(this.handle);
  }

  public enumDestructables(filter: boolexpr | (() => boolean), actionFunc: () => void) {
    EnumDestructablesInRect(this.handle, filter, actionFunc);
  }

  public enumItems(filter: boolexpr | (() => boolean), actionFunc: () => void) {
    EnumItemsInRect(this.handle, filter, actionFunc);
  }

  public move(newCenterX: number, newCenterY: number) {
    MoveRectTo(this.handle, newCenterX, newCenterY);
  }

  public movePoint(newCenterPoint: Point) {
    MoveRectToLoc(this.handle, newCenterPoint.handle);
  }

  public setRect(minX: number, minY: number, maxX: number, maxY: number) {
    SetRect(this.handle, minX, minY, maxX, maxY);
  }

  public setRectFromPoint(min: Point, max: Point) {
    SetRectFromLoc(this.handle, min.handle, max.handle);
  }

  /**
   * 此方法会停止执行
   */
  // public static fromHandle(handle: rect): Rectangle {
  //   return this.getObject(handle);
  // }

  // public static fromPoint(min: Point, max: Point) {
  //   return this.fromHandle(RectFromLoc(min.handle, max.handle));
  // }
  //
  // // Returns full map bounds, including unplayable borders, in world coordinates
  // public static getWorldBounds() {
  //   return Rectangle.fromHandle(GetWorldBounds());
  // }

}
