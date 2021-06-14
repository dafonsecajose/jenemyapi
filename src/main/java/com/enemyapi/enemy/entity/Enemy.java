package com.enemyapi.enemy.entity;

import com.enemyapi.enemy.enums.Affiliation;
import com.enemyapi.enemy.enums.Level;
import com.enemyapi.enemy.enums.Rank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enemy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rank rank;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @ElementCollection(targetClass = Affiliation.class)
    @CollectionTable(
            name = "affiliation_enemy",
            joinColumns = @JoinColumn(name = "enemy_id"))
    private List<Affiliation> affiliations;

    @Column(nullable = false)
    private String description;
}
