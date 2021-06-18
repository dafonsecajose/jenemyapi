package com.enemyapi.enemy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EnemyAlreadyRegisteredException extends Exception{

    public EnemyAlreadyRegisteredException(String enemyName) {
        super(String.format("Enemy with name %s already registered in the system.", enemyName));
    }
}
