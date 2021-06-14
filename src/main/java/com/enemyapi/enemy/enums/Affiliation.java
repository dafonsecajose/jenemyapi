package com.enemyapi.enemy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Affiliation {

    AMEGAKURE("Vila Oculta da Chuva"),
    GETSUGAKARE("Vila Oculta da Lua"),
    HOSHIGAKURE("Vila Oculta da Estrela"),
    IWAGAKURE("VIla Oculta da Névoa"),
    KIRIGAKURE("Vila Oculta da Névoa"),
    KONOHAGAKURE("Vila Oculta da Folha"),
    KUMOGAKURE("Vila Oculta da Nuvem"),
    KUSAGAKURE("Vila Oculta da Grama"),
    OTOGAKURE("Vila Oculta do Som"),
    SUNAGAKURE("Vila Oculta da Areia"),
    TAKIGAKURE("Vila Oculta da Cachoeira"),
    UZUSHIOGAKURE("Vila oculta do Redemoinho"),
    YUGAKURE("Vila Oculta das Fontes Termais"),
    YUKIGARE("Vila Oculta Entre a Neve"),
    YUMEGAKURE("Vila Oculta Entre Sonhos"),
    AKATSUKI("Akatsuki");

    private final String description;
}
