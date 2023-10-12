package com.asp.phonebooking.config;

import static org.mockito.Mockito.mock;

import com.asp.phonebooking.repository.PhoneBookingRepository;
import com.asp.phonebooking.repository.PhoneModelRepository;
import com.asp.phonebooking.repository.PhoneRepository;
import jakarta.persistence.EntityManager;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.support.TransactionTemplate;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {

    @Bean
    public DataSource dataSource() {
        return mock(DataSource.class);
    }

    @Bean
    public EntityManager entityManager() {
        return mock(EntityManager.class);
    }

    @Bean
    public TransactionTemplate transactionTemplate() {
        return mock(TransactionTemplate.class);
    }

    @Bean
    public PhoneBookingRepository phoneBookingRepository() {
        return mock(PhoneBookingRepository.class);
    }

    @Bean
    public PhoneModelRepository phoneModelRepository() {
        return mock(PhoneModelRepository.class);
    }

    @Bean
    public PhoneRepository phoneRepository() {
        return mock(PhoneRepository.class);
    }
}
