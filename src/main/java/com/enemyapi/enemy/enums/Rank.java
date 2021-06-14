package com.enemyapi.enemy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rank {

    GENIN("NINJA GENIN"),
    CHUNIN("NINJA CHUNIN"),
    JOUNIN("NINJA JOUNIN"),
    ANBU("NINJA DO ESQUADRÃO ANBU"),
    SANIN("NINJA LENDARIO SANIN");

    private final String description;
}
