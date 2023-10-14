package com.asp.phonebooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class PhoneBookingConfiguration {

    @Bean
    TaskScheduler externalUpdateScheduler() {
        var threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("external-update-");
        threadPoolTaskScheduler.setDaemon(true);
        return threadPoolTaskScheduler;
    }
}
