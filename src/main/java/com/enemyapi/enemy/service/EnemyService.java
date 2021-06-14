package com.enemyapi.enemy.service;

import com.enemyapi.enemy.dto.reponse.MessageReponseDTO;
import com.enemyapi.enemy.dto.request.EnemyDTO;
import com.enemyapi.enemy.entity.Enemy;
import com.enemyapi.enemy.exception.EnemyNotFoundException;
import com.enemyapi.enemy.mapper.EnemyMapper;
import com.enemyapi.enemy.repository.EnemyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor =  @__(@Autowired))
public class EnemyService {

    private EnemyRepository enemyRepository;

    private final EnemyMapper enemyMapper = EnemyMapper.INSTANCE;

    public MessageReponseDTO createEnemy(EnemyDTO enemyDTO) {
        Enemy enemyToSave = enemyMapper.toModel(enemyDTO);

        Enemy savedEnemy = enemyRepository.save(enemyToSave);
        return createMessage(savedEnemy.getId(), "Created enemy with ID ");
    }

    public List<EnemyDTO> listAll(){
        List<Enemy> allEnemy = enemyRepository.findAll();
        return allEnemy.stream()
                .map(enemyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EnemyDTO findById(Long id) throws EnemyNotFoundException {
        Enemy enemy = verifyIfExists(id);
        return enemyMapper.toDTO(enemy);
    }

    public MessageReponseDTO updateById(Long id, EnemyDTO enemyDTO) throws EnemyNotFoundException {
        verifyIfExists(id);

        Enemy enemyToUpdate = enemyMapper.toModel(enemyDTO);
        Enemy updatedEnemy = enemyRepository.save(enemyToUpdate);
        return createMessage(updatedEnemy.getId(), "Updated enemy with ID ");
    }

    public void delete(Long id) throws EnemyNotFoundException {
        verifyIfExists(id);
        enemyRepository.deleteById(id);
    }

    private Enemy verifyIfExists(Long id) throws EnemyNotFoundException {
        return enemyRepository.findById(id)
                .orElseThrow(() -> new EnemyNotFoundException(id));
    }

    private MessageReponseDTO createMessage(Long id, String message) {
        return MessageReponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}
