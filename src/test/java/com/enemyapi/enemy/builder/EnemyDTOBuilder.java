package com.enemyapi.enemy.builder;

import com.enemyapi.enemy.dto.request.EnemyDTO;
import com.enemyapi.enemy.enums.Affiliation;
import com.enemyapi.enemy.enums.Level;
import com.enemyapi.enemy.enums.Rank;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class EnemyDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Zabuza Momochi";

    @Builder.Default
    private Rank rank = Rank.ANBU;

    @Builder.Default
    private Level level = Level.A;

    @Builder.Default
    private List<Affiliation> affiliations = new ArrayList<>(){{
        add(Affiliation.KIRIGAKURE);
    }};

    @Builder.Default
    private String description = "test description";

    public EnemyDTO toEnemyDTO() {
        return new EnemyDTO(
                id,
                name,
                rank,
                level,
                affiliations,
                description
        );
    }



}
