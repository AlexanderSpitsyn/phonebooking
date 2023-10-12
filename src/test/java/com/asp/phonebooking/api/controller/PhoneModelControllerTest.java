package com.asp.phonebooking.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.asp.phonebooking.api.dto.CreatePhoneModelDTO;
import com.asp.phonebooking.api.dto.UpdatePhoneModelDTO;
import com.asp.phonebooking.entity.PhoneModel;
import com.asp.phonebooking.service.PhoneModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * TODO Add more tests (check permissions, validation, etc.)
 */
@ComponentScan("com.asp.phonebooking")
@WebMvcTest(PhoneModelController.class)
class PhoneModelControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PhoneModelService phoneModelService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(phoneModelService.create(any(PhoneModel.class))).then(invocation -> {
            var argument = invocation.getArgument(0, PhoneModel.class);
            var phoneModel = mock(PhoneModel.class);
            when(phoneModel.getId()).thenReturn(1L);
            when(phoneModel.getName()).thenReturn(argument.getName());
            return phoneModel;
        });

        var createPhoneModelDTO = new CreatePhoneModelDTO("testPhoneModel");

        mockMvc.perform(post("/phone_model")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPhoneModelDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(createPhoneModelDTO.getName()));

        verify(phoneModelService).create(argThat(argument ->
                argument.getId() == null
                        && argument.getName().equals(createPhoneModelDTO.getName())
        ));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        long phoneModelId = 1;
        when(phoneModelService.existsById(phoneModelId)).thenReturn(true);
        when(phoneModelService.update(any(PhoneModel.class))).then(invocation -> {
            var argument = invocation.getArgument(0, PhoneModel.class);
            var phoneModel = mock(PhoneModel.class);
            when(phoneModel.getId()).thenReturn(phoneModelId);
            when(phoneModel.getName()).thenReturn(argument.getName());
            return phoneModel;
        });

        var updatePhoneModelDTO = new UpdatePhoneModelDTO("testPhoneModel");

        mockMvc.perform(put("/phone_model/" + phoneModelId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePhoneModelDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(phoneModelId))
                .andExpect(jsonPath("$.name").value(updatePhoneModelDTO.getName()));

        verify(phoneModelService).update(argThat(argument ->
                argument.getId() == phoneModelId
                        && argument.getName().equals(updatePhoneModelDTO.getName())
        ));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete() throws Exception {
        long phoneModelId = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete("/phone_model/" + phoneModelId))
                .andExpect(status().isOk());

        verify(phoneModelService).delete(phoneModelId);
    }

    @Test
    @WithMockUser(roles = "USER")
    void getPhoneModel() throws Exception {
        long phoneModelId = 1;
        var phoneModel = mock(PhoneModel.class);
        when(phoneModel.getId()).thenReturn(phoneModelId);
        when(phoneModel.getName()).thenReturn("phoneModel 1");

        when(phoneModelService.get(phoneModelId)).thenReturn(phoneModel);

        mockMvc.perform(get("/phone_model/" + phoneModelId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(phoneModel.getId()))
                .andExpect(jsonPath("$.name").value(phoneModel.getName()));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAll() throws Exception {
        var phoneModel1 = mock(PhoneModel.class);
        when(phoneModel1.getId()).thenReturn(1L);
        when(phoneModel1.getName()).thenReturn("phoneModel 1");

        var phoneModel2 = mock(PhoneModel.class);
        when(phoneModel2.getId()).thenReturn(2L);
        when(phoneModel2.getName()).thenReturn("phoneModel 2");

        when(phoneModelService.findAll()).thenReturn(List.of(phoneModel1, phoneModel2));

        mockMvc.perform(get("/phone_model"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.[0].id").value(phoneModel1.getId()))
                .andExpect(jsonPath("$.[0].name").value(phoneModel1.getName()))

                .andExpect(jsonPath("$.[1].id").value(phoneModel2.getId()))
                .andExpect(jsonPath("$.[1].name").value(phoneModel2.getName()))

                .andExpect(jsonPath("$.[2]").doesNotExist());
    }
}