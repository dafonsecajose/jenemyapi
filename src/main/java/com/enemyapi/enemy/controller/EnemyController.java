package com.enemyapi.enemy.controller;

import com.enemyapi.enemy.dto.request.EnemyDTO;
import com.enemyapi.enemy.exception.EnemyAlreadyRegisteredException;
import com.enemyapi.enemy.exception.EnemyNotFoundException;
import com.enemyapi.enemy.service.EnemyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/enemy")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EnemyController implements EnemyControllerDocs {

    private EnemyService enemyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnemyDTO createEnemy(@RequestBody @Valid EnemyDTO enemyDTO) throws EnemyAlreadyRegisteredException {
        return enemyService.createEnemy(enemyDTO);
    }

    @GetMapping
    public List<EnemyDTO> listAll() { return enemyService.listAll(); }

    @GetMapping("/{id}")
    public EnemyDTO findById(@PathVariable Long id) throws EnemyNotFoundException {
        return enemyService.findById(id);
    }

    @PutMapping("/{id}")
    public EnemyDTO updateById(@PathVariable Long id, @RequestBody @Valid EnemyDTO enemyDTO) throws EnemyNotFoundException {
        return enemyService.updateById(id, enemyDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws EnemyNotFoundException {
        enemyService.delete(id);
    }

}
