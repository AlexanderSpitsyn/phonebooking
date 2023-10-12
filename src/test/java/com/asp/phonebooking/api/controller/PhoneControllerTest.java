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

import com.asp.phonebooking.api.dto.CreatePhoneDTO;
import com.asp.phonebooking.api.dto.UpdatePhoneDTO;
import com.asp.phonebooking.entity.Phone;
import com.asp.phonebooking.service.PhoneService;
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
@WebMvcTest(PhoneController.class)
class PhoneControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PhoneService phoneService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(phoneService.create(any(Phone.class))).then(invocation -> {
            var argument = invocation.getArgument(0, Phone.class);
            var phone = mock(Phone.class);
            when(phone.getId()).thenReturn(1L);
            when(phone.getName()).thenReturn(argument.getName());
            when(phone.getPhoneModelId()).thenReturn(argument.getPhoneModelId());
            return phone;
        });

        var createPhoneDTO = new CreatePhoneDTO("testPhone", 2L);

        mockMvc.perform(post("/phone")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPhoneDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(createPhoneDTO.getName()))
                .andExpect(jsonPath("$.phoneModelId").value(createPhoneDTO.getPhoneModelId()));

        verify(phoneService).create(argThat(argument ->
                argument.getId() == null
                        && argument.getName().equals(createPhoneDTO.getName())
                        && argument.getPhoneModelId().equals(createPhoneDTO.getPhoneModelId())
        ));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        long phoneId = 1;
        when(phoneService.existsById(phoneId)).thenReturn(true);
        when(phoneService.update(any(Phone.class))).then(invocation -> {
            var argument = invocation.getArgument(0, Phone.class);
            var phone = mock(Phone.class);
            when(phone.getId()).thenReturn(phoneId);
            when(phone.getName()).thenReturn(argument.getName());
            when(phone.getPhoneModelId()).thenReturn(argument.getPhoneModelId());
            return phone;
        });

        var updatePhoneDTO = new UpdatePhoneDTO("testPhone", 2L);

        mockMvc.perform(put("/phone/" + phoneId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePhoneDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(phoneId))
                .andExpect(jsonPath("$.name").value(updatePhoneDTO.getName()))
                .andExpect(jsonPath("$.phoneModelId").value(updatePhoneDTO.getPhoneModelId()));

        verify(phoneService).update(argThat(argument ->
                argument.getId() == phoneId
                        && argument.getName().equals(updatePhoneDTO.getName())
                        && argument.getPhoneModelId().equals(updatePhoneDTO.getPhoneModelId())
        ));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete() throws Exception {
        long phoneId = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete("/phone/" + phoneId))
                .andExpect(status().isOk());

        verify(phoneService).delete(phoneId);
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAll() throws Exception {
        var phone1 = mock(Phone.class);
        when(phone1.getId()).thenReturn(1L);
        when(phone1.getName()).thenReturn("phone 1");
        when(phone1.getPhoneModelId()).thenReturn(11L);

        var phone2 = mock(Phone.class);
        when(phone2.getId()).thenReturn(2L);
        when(phone2.getName()).thenReturn("phone 2");
        when(phone2.getPhoneModelId()).thenReturn(22L);

        when(phoneService.findAll()).thenReturn(List.of(phone1, phone2));

        mockMvc.perform(get("/phone"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.[0].id").value(phone1.getId()))
                .andExpect(jsonPath("$.[0].name").value(phone1.getName()))
                .andExpect(jsonPath("$.[0].phoneModelId").value(phone1.getPhoneModelId()))

                .andExpect(jsonPath("$.[1].id").value(phone2.getId()))
                .andExpect(jsonPath("$.[1].name").value(phone2.getName()))
                .andExpect(jsonPath("$.[1].phoneModelId").value(phone2.getPhoneModelId()))

                .andExpect(jsonPath("$.[2]").doesNotExist());
    }
}