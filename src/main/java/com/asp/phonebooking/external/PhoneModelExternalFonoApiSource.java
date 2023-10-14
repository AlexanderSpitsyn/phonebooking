package com.asp.phonebooking.external;

import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * TODO implement
 */
@Service
public class PhoneModelExternalFonoApiSource implements PhoneModelExternalSource {

    @Override
    public Optional<PhoneModelExternal> findPhoneModelExternal(String brand, String device) {
        return Optional.empty();
    }
}
