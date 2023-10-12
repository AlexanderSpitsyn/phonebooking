package com.asp.phonebooking.service;

import com.asp.phonebooking.entity.PhoneModel;
import com.asp.phonebooking.exception.NotFoundException;
import com.asp.phonebooking.repository.PhoneModelRepository;
import org.springframework.stereotype.Service;

@Service
public class PhoneModelService {

    private final PhoneModelRepository phoneModelRepository;

    public PhoneModelService(PhoneModelRepository phoneModelRepository) {
        this.phoneModelRepository = phoneModelRepository;
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
