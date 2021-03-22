package org.solar.editor.core;

public enum GameFileType {

    common("基础通用"), war3("魔兽争霸3"), dota2("Dota2"), cocos("cocos");
    // 成员变量
    private String name;

    // 构造方法
    private GameFileType(String name) {
        this.name = name;
    }

}
