package com.enemyapi.enemy.repository;

import com.enemyapi.enemy.entity.Enemy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnemyRepository extends JpaRepository<Enemy, Long> {
}
