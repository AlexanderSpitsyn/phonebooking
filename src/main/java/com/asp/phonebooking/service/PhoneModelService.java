package com.asp.phonebooking.service;

import com.asp.phonebooking.config.PhoneBookingProperties;
import com.asp.phonebooking.entity.PhoneModel;
import com.asp.phonebooking.exception.NotFoundException;
import com.asp.phonebooking.external.PhoneModelExternal;
import com.asp.phonebooking.external.PhoneModelExternalSource;
import com.asp.phonebooking.repository.PhoneModelRepository;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class PhoneModelService {

    private static final Logger log = LoggerFactory.getLogger(PhoneModelService.class);

    private final PhoneModelRepository phoneModelRepository;
    private final TaskScheduler externalUpdateScheduler;
    private final List<PhoneModelExternalSource> phoneModelExternalSources;
    private final PhoneBookingProperties.PhoneModel phoneModelProperties;

    public PhoneModelService(PhoneModelRepository phoneModelRepository,
                             TaskScheduler externalUpdateScheduler,
                             List<PhoneModelExternalSource> phoneModelExternalSources,
                             PhoneBookingProperties phoneBookingProperties) {
        this.phoneModelRepository = phoneModelRepository;
        this.externalUpdateScheduler = externalUpdateScheduler;
        this.phoneModelExternalSources = phoneModelExternalSources;
        this.phoneModelProperties = phoneBookingProperties.getPhoneModel();
    }

    @PostConstruct
    private void scheduleUpdateWithExternal() {
        if (phoneModelProperties.getUpdateWithExternalRunPeriodSeconds() <= 0) {
            log.info("Updating with external values is disabled");
            return;
        }
        if (phoneModelExternalSources.isEmpty()) {
            log.info("There are no PhoneModelExternalSources initialized");
            return;
        }

        externalUpdateScheduler.scheduleAtFixedRate(
                this::updatePhoneModelsWithExternal,
                Instant.now(),
                Duration.ofSeconds(phoneModelProperties.getUpdateWithExternalRunPeriodSeconds())
        );
    }

    private void updatePhoneModelsWithExternal() {
        if (phoneModelExternalSources.isEmpty()) {
            log.info("There are no PhoneModelExternalSources initialized");
            return;
        }

        var beforeTimestamp = Instant.now().minusSeconds(phoneModelProperties.getUpdateWithExternalValidityTimeSeconds());
        for (var phoneModel : phoneModelRepository.getAllUpdateWithExternalRequired(beforeTimestamp)) {
            for (var phoneModelExternalSource : phoneModelExternalSources) {
                Optional<PhoneModelExternal> phoneModelExternal = Optional.empty();
                try {
                    phoneModelExternal = phoneModelExternalSource.findPhoneModelExternal(phoneModel.getBrand(), phoneModel.getDevice());
                } catch (Exception e) {
                    log.error("Unable to find phone model using " + phoneModelExternalSource.getClass().getSimpleName(), e);
                }
                if (phoneModelExternal.isPresent()) {
                    var phoneModelUpdate = phoneModel.withExternal(phoneModelExternal.get());
                    update(phoneModelUpdate);
                    break;
                }
            }
        }
    }

    public PhoneModel create(PhoneModel phoneModel) {
        if (phoneModel.getId() != null) {
            throw new IllegalArgumentException("phoneModel.id must be null");
        }
        return phoneModelRepository.save(phoneModel);
    }

    public PhoneModel update(PhoneModel phoneModel) {
        if (phoneModel.getId() == null) {
            throw new IllegalArgumentException("phoneModel.id must not be null");
        }
        if (!existsById(phoneModel.getId())) {
            throw new NotFoundException("Phone model doesn't exist");
        }
        return phoneModelRepository.save(phoneModel);
    }

    public PhoneModel get(Long id) {
        return phoneModelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Phone model doesn't exist"));
    }

    public Iterable<PhoneModel> findAll() {
        return phoneModelRepository.findAll();
    }

    public void delete(Long id) {
        phoneModelRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return phoneModelRepository.existsById(id);
    }
}
