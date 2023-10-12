package com.asp.phonebooking.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.asp.phonebooking.api.dto.CreatePhoneBookingDTO;
import com.asp.phonebooking.entity.PhoneBooking;
import com.asp.phonebooking.service.PhoneBookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Optional;
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
@WebMvcTest(PhoneBookingController.class)
class PhoneBookingControllerTest {

    private final Instant now = Instant.now();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PhoneBookingService phoneBookingService;

    @Test
    @WithMockUser(roles = "USER")
    void create() throws Exception {
        long phoneId = 2;
        var startpoint = now.plusSeconds(5);
        var endpoint = now.plusSeconds(10);
        when(phoneBookingService.create(any(PhoneBooking.class))).then(invocation -> {
            var argument = invocation.getArgument(0, PhoneBooking.class);
            var phoneBooking = mock(PhoneBooking.class);
            when(phoneBooking.getId()).thenReturn(1L);
            when(phoneBooking.getPhoneId()).thenReturn(argument.getPhoneId());
            when(phoneBooking.getStartpoint()).thenReturn(argument.getStartpoint());
            when(phoneBooking.getEndpoint()).thenReturn(argument.getEndpoint());
            return phoneBooking;
        });

        var createPhoneBookingDTO = new CreatePhoneBookingDTO(phoneId, startpoint, endpoint);

        mockMvc.perform(post("/phone_booking")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPhoneBookingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.phoneId").value(phoneId))
                .andExpect(jsonPath("$.startpoint").value(startpoint.toString()))
                .andExpect(jsonPath("$.endpoint").value(endpoint.toString()));

        verify(phoneBookingService).create(argThat(argument ->
                argument.getId() == null
                        && argument.getPhoneId().equals(createPhoneBookingDTO.getPhoneId())
                        && argument.getStartpoint().equals(createPhoneBookingDTO.getStartpoint())
                        && argument.getEndpoint().equals(createPhoneBookingDTO.getEndpoint())
        ));
    }

    @Test
    @WithMockUser(username = "test_delete", roles = "USER")
    void delete() throws Exception {
        long phoneBookingId = 1;
        var phoneBooking = mock(PhoneBooking.class);
        when(phoneBooking.getUsername()).thenReturn("test_delete");
        when(phoneBookingService.find(phoneBookingId)).thenReturn(Optional.of(phoneBooking));

        mockMvc.perform(MockMvcRequestBuilders.delete("/phone_booking/" + phoneBookingId))
                .andExpect(status().isOk());

        verify(phoneBookingService).delete(phoneBookingId);
    }

    @Test
    @WithMockUser(username = "test_delete", roles = "USER")
    void delete_wrongUser() throws Exception {
        long phoneBookingId = 1;
        var phoneBooking = mock(PhoneBooking.class);
        when(phoneBooking.getUsername()).thenReturn("wrong_user");
        when(phoneBookingService.find(phoneBookingId)).thenReturn(Optional.of(phoneBooking));

        mockMvc.perform(MockMvcRequestBuilders.delete("/phone_booking/" + phoneBookingId))
                .andExpect(status().isOk());

        verify(phoneBookingService, never()).delete(phoneBookingId);
    }
}