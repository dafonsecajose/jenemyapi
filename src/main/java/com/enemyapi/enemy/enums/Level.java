package com.enemyapi.enemy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Level {

    A("RANK A"),
    B("RANK B"),
    C("RANK C"),
    D("RANK D"),
    S("RANK S"),
    SS("RANK SS");

    private final String description;
}
