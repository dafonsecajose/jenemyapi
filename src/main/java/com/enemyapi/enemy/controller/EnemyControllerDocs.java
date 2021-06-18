package com.enemyapi.enemy.controller;

import com.enemyapi.enemy.dto.request.EnemyDTO;
import com.enemyapi.enemy.exception.EnemyNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api("Manages enemy registration")
public interface EnemyControllerDocs {

    @ApiOperation(value = "Operation to create enemy")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success enemy creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    EnemyDTO createEnemy(EnemyDTO enemyDTO) throws EnemyAlreadyRegisteredException;

    @ApiOperation(value = "Returns enemy found by a given ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success enemy found in the system"),
            @ApiResponse(code = 404, message = "Enemy with given ID not found.")
    })
    EnemyDTO findById(@PathVariable Long id) throws EnemyNotFoundException;

    @ApiOperation(value = "Operation to upgrade enemy")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success enemy updated"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value."),
            @ApiResponse(code = 404, message = "Enemy with given ID not found.")
    })
    EnemyDTO updateById(@PathVariable Long id, EnemyDTO enemyDTO) throws EnemyNotFoundException;

    @ApiOperation(value = "Returns a list of all enemies registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all enemies registered in the system"),
    })
    List<EnemyDTO> listAll();

    @ApiOperation(value = "Delete a enemy by a given valid ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success enemy deleted in the system"),
            @ApiResponse(code = 404, message = "Enemy with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws EnemyNotFoundException;
}
