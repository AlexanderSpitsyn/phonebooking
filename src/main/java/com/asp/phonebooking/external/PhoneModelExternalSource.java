package com.asp.phonebooking.external;

import java.util.Optional;

public interface PhoneModelExternalSource {

    Optional<PhoneModelExternal> findPhoneModelExternal(String brand, String device);
}
