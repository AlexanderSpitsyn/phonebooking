package com.asp.phonebooking.repository;

import com.asp.phonebooking.entity.Phone;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<Phone, Long> {

    boolean existsByName(String name);
}
