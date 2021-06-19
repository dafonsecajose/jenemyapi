package com.enemyapi.enemy.service;

import com.enemyapi.enemy.builder.EnemyDTOBuilder;
import com.enemyapi.enemy.dto.request.EnemyDTO;
import com.enemyapi.enemy.entity.Enemy;
import com.enemyapi.enemy.exception.EnemyAlreadyRegisteredException;
import com.enemyapi.enemy.exception.EnemyNotFoundException;
import com.enemyapi.enemy.mapper.EnemyMapper;
import com.enemyapi.enemy.repository.EnemyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnemyServiceTest {

    private static final long INVALID_ENEMY_ID = 1L;

    @Mock
    private EnemyRepository enemyRepository;

    private EnemyMapper enemyMapper = EnemyMapper.INSTANCE;

    @InjectMocks
    private EnemyService enemyService;

    @Test
    void whenEnemyInformedThenItShouldBeCreated() throws EnemyAlreadyRegisteredException {
        //given
        EnemyDTO expectedEnemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();
        Enemy expectedSavedEnemy = enemyMapper.toModel(expectedEnemyDTO);

        //when
        when(enemyRepository.findByName(expectedEnemyDTO.getName())).thenReturn(Optional.empty());
        when(enemyRepository.save(expectedSavedEnemy)).thenReturn(expectedSavedEnemy);

        //then
        EnemyDTO createdEnemyDTO = enemyService.createEnemy(expectedEnemyDTO);

        assertThat(createdEnemyDTO.getId(), is(equalTo(expectedEnemyDTO.getId())));
        assertThat(createdEnemyDTO.getName(), is(equalTo(expectedEnemyDTO.getName())));
        assertThat(createdEnemyDTO.getRank(), is(equalTo(expectedEnemyDTO.getRank())));
        assertThat(createdEnemyDTO.getLevel(), is(equalTo(expectedEnemyDTO.getLevel())));
        assertThat(createdEnemyDTO.getAffiliations(), is(equalTo(expectedEnemyDTO.getAffiliations())));
        assertThat(createdEnemyDTO.getDescription(), is(equalTo(expectedEnemyDTO.getDescription())));
    }

    @Test
    void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown() {
        //given
        EnemyDTO expectedEnemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();
        Enemy duplicatedEnemy = enemyMapper.toModel(expectedEnemyDTO);

        //when
        when(enemyRepository.findByName(expectedEnemyDTO.getName())).thenReturn(Optional.of(duplicatedEnemy));

        //then
        assertThrows(EnemyAlreadyRegisteredException.class, () -> enemyService.createEnemy(expectedEnemyDTO));
    }

    @Test
    void whenEnemyInformedAndValidIdThenItShouldBeUpdated() throws EnemyNotFoundException {
        //given
        EnemyDTO expectedEnemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();
        Enemy expectedUpdatedEnemy = enemyMapper.toModel(expectedEnemyDTO);

        //when
        when(enemyRepository.findById(expectedEnemyDTO.getId())).thenReturn(Optional.of(expectedUpdatedEnemy));
        when(enemyRepository.save(expectedUpdatedEnemy)).thenReturn(expectedUpdatedEnemy);

        //then
        EnemyDTO updatedEnemyDTO = enemyService.updateById(expectedEnemyDTO.getId(), expectedEnemyDTO);

        assertThat(updatedEnemyDTO.getId(), is(equalTo(expectedEnemyDTO.getId())));
        assertThat(updatedEnemyDTO.getName(), is(equalTo(expectedEnemyDTO.getName())));
        assertThat(updatedEnemyDTO.getRank(), is(equalTo(expectedEnemyDTO.getRank())));
        assertThat(updatedEnemyDTO.getLevel(), is(equalTo(expectedEnemyDTO.getLevel())));
        assertThat(updatedEnemyDTO.getAffiliations(), is(equalTo(expectedEnemyDTO.getAffiliations())));
        assertThat(updatedEnemyDTO.getDescription(), is(equalTo(expectedEnemyDTO.getDescription())));
    }

    @Test
    void whenValidEnemyIdIsGivenThenReturnAEnemy() throws EnemyNotFoundException {
        //given
        EnemyDTO expectedFoundEnemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();
        Enemy expectedFoundEnemy = enemyMapper.toModel(expectedFoundEnemyDTO);

        //when
        when(enemyRepository.findById(expectedFoundEnemy.getId())).thenReturn(Optional.of(expectedFoundEnemy));

        //then
        EnemyDTO foundEnemyDTO = enemyService.findById(expectedFoundEnemyDTO.getId());

        assertThat(foundEnemyDTO, is(equalTo(expectedFoundEnemyDTO)));
    }

    @Test
    void whenNotRegisteredEnemyIdIsGivenThenThrowAnException() {
        //given
        EnemyDTO expectedFoundEnemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();

        //when
        when(enemyRepository.findById(expectedFoundEnemyDTO.getId())).thenReturn(Optional.empty());

        //then
        assertThrows(EnemyNotFoundException.class, () -> enemyService.findById(expectedFoundEnemyDTO.getId()));
    }

    @Test
    void whenListEnemyIsCalledThenReturnAListOfEnemies() {
        //given
        EnemyDTO expectedFoundEnemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();
        Enemy expectedFoundEnemy = enemyMapper.toModel(expectedFoundEnemyDTO);

        //when
        when(enemyRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundEnemy));

        //then
        List<EnemyDTO> foundListEnemyDTO = enemyService.listAll();

        assertThat(foundListEnemyDTO, is(not(empty())));
        assertThat(foundListEnemyDTO.get(0), is(equalTo(expectedFoundEnemyDTO)));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAEnemyShouldBeDeleted() throws EnemyNotFoundException {
        //given
        EnemyDTO expectedDeletedEnemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();
        Enemy expectedDeletedEnemy = enemyMapper.toModel(expectedDeletedEnemyDTO);

        //when
        when(enemyRepository.findById(expectedDeletedEnemyDTO.getId())).thenReturn(Optional.of(expectedDeletedEnemy));
        doNothing().when(enemyRepository).deleteById(expectedDeletedEnemyDTO.getId());

        //then
        enemyService.deleteById(expectedDeletedEnemyDTO.getId());

        verify(enemyRepository, times(1)).findById(expectedDeletedEnemyDTO.getId());
        verify(enemyRepository, times(1)).deleteById(expectedDeletedEnemyDTO.getId());
    }

    @Test
    void whenExclusionIsCalledWithoutValidIdThenIsGivenThenThrowAnException() {
        //given
        EnemyDTO expectedDeletedEnemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();

        //when
        when(enemyRepository.findById(expectedDeletedEnemyDTO.getId())).thenReturn(Optional.empty());

        //then
        assertThrows(EnemyNotFoundException.class, () -> enemyService.findById(expectedDeletedEnemyDTO.getId()));
    }

}
