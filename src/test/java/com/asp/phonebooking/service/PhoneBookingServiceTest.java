package com.asp.phonebooking.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.asp.phonebooking.entity.Phone;
import com.asp.phonebooking.entity.PhoneBooking;
import com.asp.phonebooking.exception.AlreadyBookedException;
import com.asp.phonebooking.exception.InvalidBookingException;
import com.asp.phonebooking.exception.NotFoundException;
import com.asp.phonebooking.repository.PhoneBookingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

class PhoneBookingServiceTest {

    private final Instant now = Instant.now();

    private PhoneBookingService phoneBookingService;
    private PhoneBookingRepository phoneBookingRepository;
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        entityManager = mock(EntityManager.class);
        phoneBookingRepository = mock(PhoneBookingRepository.class);

        var transactionTemplate = mock(TransactionTemplate.class);
        //noinspection unchecked
        when(transactionTemplate.execute(any(TransactionCallback.class)))
                .then(invocation -> invocation
                        .getArgument(0, TransactionCallback.class)
                        .doInTransaction(mock(TransactionStatus.class))
                );

        phoneBookingService = new PhoneBookingService(phoneBookingRepository, entityManager, transactionTemplate);
    }

    @Test
    void create_inThePast() {
        var phoneBooking = mock(PhoneBooking.class);
        when(phoneBooking.getStartpoint()).thenReturn(now.minusSeconds(5));
        when(phoneBooking.getEndpoint()).thenReturn(now.minusSeconds(1));

        assertThatThrownBy(() -> phoneBookingService.create(phoneBooking))
                .isInstanceOf(InvalidBookingException.class);
        verify(phoneBookingRepository, never()).save(phoneBooking);
    }

    @Test
    void create_invalidDates() {
        var phoneBooking = mock(PhoneBooking.class);
        when(phoneBooking.getStartpoint()).thenReturn(now.plusSeconds(5));
        when(phoneBooking.getEndpoint()).thenReturn(now.plusSeconds(1));

        assertThatThrownBy(() -> phoneBookingService.create(phoneBooking))
                .isInstanceOf(InvalidBookingException.class);
        verify(phoneBookingRepository, never()).save(phoneBooking);
    }

    @Test
    void create_phoneNotFound() {
        var phoneBooking = mock(PhoneBooking.class);
        when(phoneBooking.getPhoneId()).thenReturn(1L);
        when(phoneBooking.getStartpoint()).thenReturn(now.plusSeconds(5));
        when(phoneBooking.getEndpoint()).thenReturn(now.plusSeconds(10));

        assertThatThrownBy(() -> phoneBookingService.create(phoneBooking))
                .isInstanceOf(NotFoundException.class);
        verify(phoneBookingRepository, never()).save(phoneBooking);
    }

    @Test
    void create_alreadyBooked() {
        long phoneId = 1;
        var phoneBooking = mock(PhoneBooking.class);
        when(phoneBooking.getPhoneId()).thenReturn(phoneId);
        when(phoneBooking.getStartpoint()).thenReturn(now.plusSeconds(5));
        when(phoneBooking.getEndpoint()).thenReturn(now.plusSeconds(10));

        when(entityManager.find(eq(Phone.class), eq(phoneId), eq(LockModeType.PESSIMISTIC_WRITE), anyMap()))
                .thenReturn(mock(Phone.class));

        when(phoneBookingRepository.getAlreadyBooked(phoneId, phoneBooking.getStartpoint(), phoneBooking.getEndpoint()))
                .thenReturn(List.of(mock(PhoneBooking.class)));

        assertThatThrownBy(() -> phoneBookingService.create(phoneBooking))
                .isInstanceOf(AlreadyBookedException.class);
        verify(phoneBookingRepository, never()).save(phoneBooking);
    }

    @Test
    void create() {
        long phoneId = 1;
        var phoneBooking = mock(PhoneBooking.class);
        when(phoneBooking.getPhoneId()).thenReturn(phoneId);
        when(phoneBooking.getStartpoint()).thenReturn(now.plusSeconds(5));
        when(phoneBooking.getEndpoint()).thenReturn(now.plusSeconds(10));

        when(entityManager.find(eq(Phone.class), eq(phoneId), eq(LockModeType.PESSIMISTIC_WRITE), anyMap()))
                .thenReturn(mock(Phone.class));

        when(phoneBookingRepository.getAlreadyBooked(phoneId, phoneBooking.getStartpoint(), phoneBooking.getEndpoint()))
                .thenReturn(List.of());

        phoneBookingService.create(phoneBooking);
        verify(phoneBookingRepository).save(phoneBooking);
    }
}