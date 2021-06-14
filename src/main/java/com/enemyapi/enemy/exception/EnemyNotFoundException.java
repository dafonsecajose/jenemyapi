package com.enemyapi.enemy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnemyNotFoundException extends Exception{
    public EnemyNotFoundException(Long id) { super("Enemy not Found with ID " + id); }
}
