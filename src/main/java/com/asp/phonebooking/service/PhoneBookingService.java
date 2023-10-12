package com.asp.phonebooking.service;

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
import java.util.Map;
import java.util.Optional;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class PhoneBookingService {

    private static final int LOCK_TIMEOUT = 5000;

    private final PhoneBookingRepository phoneBookingRepository;
    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;

    public PhoneBookingService(PhoneBookingRepository phoneBookingRepository,
                               EntityManager entityManager,
                               TransactionTemplate transactionTemplate) {
        this.phoneBookingRepository = phoneBookingRepository;
        this.entityManager = entityManager;
        this.transactionTemplate = transactionTemplate;
    }

    public PhoneBooking create(PhoneBooking phoneBooking) {
        if (!Instant.now().isBefore(phoneBooking.getEndpoint())) {
            throw new InvalidBookingException("Can not create a booking in the past");
        }
        if (!phoneBooking.getEndpoint().isAfter(phoneBooking.getStartpoint())) {
            throw new InvalidBookingException("Endpoint must be greater than the startpoint");
        }

        return transactionTemplate.execute(status -> {
            //Lock the phone to prevent booking conflicts
            var phone = entityManager.find(
                    Phone.class,
                    phoneBooking.getPhoneId(),
                    LockModeType.PESSIMISTIC_WRITE,
                    Map.of(AvailableSettings.JAKARTA_LOCK_TIMEOUT, LOCK_TIMEOUT)
            );
            if (phone == null) {
                throw new NotFoundException("Phone doesn't exist");
            }

            var conflictBookings = phoneBookingRepository.getAlreadyBooked(
                    phoneBooking.getPhoneId(),
                    phoneBooking.getStartpoint(),
                    phoneBooking.getEndpoint()
            );
            if (!conflictBookings.isEmpty()) {
                throw new AlreadyBookedException("The phone has been already booked at the selected timeslot");
            }
            return phoneBookingRepository.save(phoneBooking);
        });
    }

    public List<PhoneBooking> getAllRelevantByPhoneId(Long phoneId) {
        return phoneBookingRepository.getAllByPhoneIdAndEndpointGreaterThan(phoneId, Instant.now());
    }

    public Optional<PhoneBooking> find(Long id) {
        return phoneBookingRepository.findById(id);
    }

    public void delete(Long id) {
        phoneBookingRepository.deleteById(id);
    }
}
