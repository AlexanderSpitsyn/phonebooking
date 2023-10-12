package com.asp.phonebooking.service;

import com.asp.phonebooking.entity.Phone;
import com.asp.phonebooking.exception.AlreadyExistsException;
import com.asp.phonebooking.exception.NotFoundException;
import com.asp.phonebooking.repository.PhoneRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;
    private final PhoneModelService phoneModelService;

    public PhoneService(PhoneRepository phoneRepository,
                        PhoneModelService phoneModelService) {
        this.phoneRepository = phoneRepository;
        this.phoneModelService = phoneModelService;
    }

    public Phone create(Phone phone) {
        if (phone.getId() != null) {
            throw new IllegalArgumentException("phone.id must be null");
        }
        if (!phoneModelService.existsById(phone.getPhoneModelId())) {
            throw new NotFoundException("Phone model doesn't exist");
        }
        try {
            return phoneRepository.save(phone);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Phone with the same name already exists");
        }
    }

    public Phone update(Phone phone) {
        if (phone.getId() == null) {
            throw new IllegalArgumentException("phone.id must not be null");
        }
        if (!existsById(phone.getId())) {
            throw new NotFoundException("Phone doesn't exist");
        }
        if (!phoneModelService.existsById(phone.getPhoneModelId())) {
            throw new NotFoundException("Phone model doesn't exist");
        }
        return phoneRepository.save(phone);
    }

    public Phone get(Long id) {
        return phoneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Phone doesn't exist"));
    }

    public Iterable<Phone> findAll() {
        return phoneRepository.findAll();
    }

    public void delete(Long id) {
        phoneRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return phoneRepository.existsById(id);
    }
}
