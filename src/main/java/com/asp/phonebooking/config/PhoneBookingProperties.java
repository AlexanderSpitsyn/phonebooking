package com.asp.phonebooking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "phonebooking")
public class PhoneBookingProperties {

    private PhoneModel phoneModel;

    public PhoneModel getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(PhoneModel phoneModel) {
        this.phoneModel = phoneModel;
    }

    public static class PhoneModel {
        /**
         * How long a phone model info is valid. If expires, need to be updated again from an external source.
         * Is not used if {@link PhoneModel#updateWithExternalRunPeriodSeconds} <= 0.
         * 1 day by default
         */
        private long updateWithExternalValidityTimeSeconds = 60L * 60 * 24;

        /**
         * if value <= 0, scheduling is disabled
         */
        private long updateWithExternalRunPeriodSeconds = 0;

        public long getUpdateWithExternalValidityTimeSeconds() {
            return updateWithExternalValidityTimeSeconds;
        }

        public void setUpdateWithExternalValidityTimeSeconds(long updateWithExternalValidityTimeSeconds) {
            this.updateWithExternalValidityTimeSeconds = updateWithExternalValidityTimeSeconds;
        }

        public long getUpdateWithExternalRunPeriodSeconds() {
            return updateWithExternalRunPeriodSeconds;
        }

        public void setUpdateWithExternalRunPeriodSeconds(long updateWithExternalRunPeriodSeconds) {
            this.updateWithExternalRunPeriodSeconds = updateWithExternalRunPeriodSeconds;
        }
    }
}
