package com.asp.phonebooking.repository;

import com.asp.phonebooking.entity.PhoneModel;
import org.springframework.data.repository.CrudRepository;

public interface PhoneModelRepository extends CrudRepository<PhoneModel, Long> {

    boolean existsByName(String name);
}
