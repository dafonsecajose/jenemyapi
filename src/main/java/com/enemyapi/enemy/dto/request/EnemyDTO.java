package com.enemyapi.enemy.dto.request;

import com.enemyapi.enemy.enums.Affiliation;
import com.enemyapi.enemy.enums.Level;
import com.enemyapi.enemy.enums.Rank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnemyDTO {

    private Long id;

    @NotNull
    @Size(min = 4, max = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    @Enumerated(EnumType.STRING)
    private Level level;

    @ElementCollection(targetClass = Affiliation.class)
    private List<Affiliation> affiliations;

    @NotNull
    @Size(min = 4, max = 150)
    private String description;
}
