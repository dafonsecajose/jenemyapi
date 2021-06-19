package com.enemyapi.enemy.controller;

import com.enemyapi.enemy.builder.EnemyDTOBuilder;
import com.enemyapi.enemy.dto.request.EnemyDTO;
import com.enemyapi.enemy.exception.EnemyNotFoundException;
import com.enemyapi.enemy.service.EnemyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.stream.Collectors;

import static com.enemyapi.enemy.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EnemyControllerTest {

    private static final String ENEMY_API_URL_PATH = "/api/v1/enemy";
    private static final long VALID_ENEMY_ID = 1L;
    private static final long INVALID_ENEMY_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private EnemyService enemyService;

    @InjectMocks
    private EnemyController enemyController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(enemyController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAEnemyIsCreated() throws Exception {
        //given
        EnemyDTO enemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();

        //when
        when(enemyService.createEnemy(enemyDTO)).thenReturn(enemyDTO);

        //then
        mockMvc.perform(post(ENEMY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(enemyDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(enemyDTO.getName())))
                .andExpect(jsonPath("$.rank", is(enemyDTO.getRank().toString())))
                .andExpect(jsonPath("$.level", is(enemyDTO.getLevel().toString())))
                .andExpect(jsonPath("$.affiliations", is(enemyDTO.getAffiliations()
                        .stream().map(affiliation -> affiliation.toString())
                        .collect(Collectors.toList()))))
                .andExpect(jsonPath("$.description", is(enemyDTO.getDescription())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        //given
        EnemyDTO enemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();
        enemyDTO.setName(null);

        //then
        mockMvc.perform(post(ENEMY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(enemyDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        //given
        EnemyDTO enemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();

        //when
        when(enemyService.findById(enemyDTO.getId())).thenReturn(enemyDTO);

        //then
        mockMvc.perform(get(ENEMY_API_URL_PATH + "/" + enemyDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(enemyDTO.getName())))
                .andExpect(jsonPath("$.rank", is(enemyDTO.getRank().toString())))
                .andExpect(jsonPath("$.level", is(enemyDTO.getLevel().toString())))
                .andExpect(jsonPath("$.affiliations", is(enemyDTO.getAffiliations()
                        .stream().map(affiliation -> affiliation.toString())
                        .collect(Collectors.toList()))))
                .andExpect(jsonPath("$.description", is(enemyDTO.getDescription())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        //given
        EnemyDTO enemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();

        //when
        when(enemyService.findById(enemyDTO.getId())).thenThrow(EnemyNotFoundException.class);

        //then
        mockMvc.perform(get(ENEMY_API_URL_PATH + "/" + enemyDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithEnemiesIsCalledThenOkStatusIsReturned() throws Exception {
        //given
        EnemyDTO enemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();

        //when
        when(enemyService.listAll()).thenReturn(Collections.singletonList(enemyDTO));

        //then
        mockMvc.perform(get(ENEMY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(enemyDTO.getName())))
                .andExpect(jsonPath("$[0].rank", is(enemyDTO.getRank().toString())))
                .andExpect(jsonPath("$[0].level", is(enemyDTO.getLevel().toString())))
                .andExpect(jsonPath("$[0].affiliations", is(enemyDTO.getAffiliations()
                        .stream().map(affiliation -> affiliation.toString())
                        .collect(Collectors.toList()))))
                .andExpect(jsonPath("$[0].description", is(enemyDTO.getDescription())));
    }

    @Test
    void whenGETListWithoutEnemiesIsCalledThenOkStatusIsReturned() throws Exception {
        //given
        EnemyDTO enemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();

        //when
        when(enemyService.listAll()).thenReturn(Collections.singletonList(null));

        //then
        mockMvc.perform(get(ENEMY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        //given
        EnemyDTO enemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();

        //when
        doNothing().when(enemyService).deleteById(enemyDTO.getId());

        //then
        mockMvc.perform(delete(ENEMY_API_URL_PATH + "/" + enemyDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(EnemyNotFoundException.class).when(enemyService).deleteById(INVALID_ENEMY_ID);

        //then
        mockMvc.perform(delete(ENEMY_API_URL_PATH + "/" + INVALID_ENEMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTIsCalledThenEnemyIsUpdated() throws Exception {
        //given
        EnemyDTO enemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();

        //when
        when(enemyService.updateById(VALID_ENEMY_ID, enemyDTO)).thenReturn(enemyDTO);

        //then
        mockMvc.perform(put(ENEMY_API_URL_PATH + "/" + VALID_ENEMY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(enemyDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(enemyDTO.getName())))
                .andExpect(jsonPath("$.rank", is(enemyDTO.getRank().toString())))
                .andExpect(jsonPath("$.level", is(enemyDTO.getLevel().toString())))
                .andExpect(jsonPath("$.affiliations", is(enemyDTO.getAffiliations()
                        .stream().map(affiliation -> affiliation.toString())
                        .collect(Collectors.toList()))))
                .andExpect(jsonPath("$.description", is(enemyDTO.getDescription())));

    }

    @Test
    void whenPUTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        //given
        EnemyDTO enemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();
        enemyDTO.setName(null);

        //then
        mockMvc.perform(put(ENEMY_API_URL_PATH + "/" + VALID_ENEMY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(enemyDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPUTIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //given
        EnemyDTO enemyDTO = EnemyDTOBuilder.builder().build().toEnemyDTO();

        //when
        doThrow(EnemyNotFoundException.class).when(enemyService).updateById(INVALID_ENEMY_ID, enemyDTO);

        //then
        mockMvc.perform(put(ENEMY_API_URL_PATH + "/" + INVALID_ENEMY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(enemyDTO)))
                .andExpect(status().isNotFound());
    }
}
