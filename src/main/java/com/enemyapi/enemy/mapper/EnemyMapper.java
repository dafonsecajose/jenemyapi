package com.enemyapi.enemy.mapper;

import com.enemyapi.enemy.dto.request.EnemyDTO;
import com.enemyapi.enemy.entity.Enemy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EnemyMapper {

    EnemyMapper INSTANCE = Mappers.getMapper(EnemyMapper.class);

    Enemy toModel(EnemyDTO enemyDTO);

    EnemyDTO toDTO(Enemy enemy);
}
