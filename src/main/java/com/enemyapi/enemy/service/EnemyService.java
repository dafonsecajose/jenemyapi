package com.enemyapi.enemy.service;

import com.enemyapi.enemy.dto.request.EnemyDTO;
import com.enemyapi.enemy.entity.Enemy;
import com.enemyapi.enemy.exception.EnemyAlreadyRegisteredException;
import com.enemyapi.enemy.exception.EnemyNotFoundException;
import com.enemyapi.enemy.mapper.EnemyMapper;
import com.enemyapi.enemy.repository.EnemyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor =  @__(@Autowired))
public class EnemyService {

    private final EnemyRepository enemyRepository;
    private final EnemyMapper enemyMapper = EnemyMapper.INSTANCE;

    public EnemyDTO createEnemy(EnemyDTO enemyDTO) throws EnemyAlreadyRegisteredException {
        verifyIfAlreadyRegistered(enemyDTO.getName());
        Enemy enemy = enemyMapper.toModel(enemyDTO);
        Enemy savedEnemy = enemyRepository.save(enemy);
        return enemyMapper.toDTO(savedEnemy);
    }

    public List<EnemyDTO> listAll(){
        return enemyRepository.findAll()
                .stream()
                .map(enemyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EnemyDTO findById(Long id) throws EnemyNotFoundException {
        Enemy enemy = verifyIfExists(id);
        return enemyMapper.toDTO(enemy);
    }

    public EnemyDTO updateById(Long id, EnemyDTO enemyDTO) throws EnemyNotFoundException {
        verifyIfExists(id);

        Enemy enemy = enemyMapper.toModel(enemyDTO);
        Enemy updatedEnemy = enemyRepository.save(enemy);
        return enemyMapper.toDTO(updatedEnemy);
    }

    public void deleteById(Long id) throws EnemyNotFoundException {
        verifyIfExists(id);
        enemyRepository.deleteById(id);
    }

    private Enemy verifyIfExists(Long id) throws EnemyNotFoundException {
        return enemyRepository.findById(id)
                .orElseThrow(() -> new EnemyNotFoundException(id));
    }

    private void verifyIfAlreadyRegistered(String name) throws EnemyAlreadyRegisteredException{
        Optional<Enemy> optSavedEnemy = enemyRepository.findByName(name);
        if(optSavedEnemy.isPresent()) {
            throw new EnemyAlreadyRegisteredException(name);
        }
    }
}
