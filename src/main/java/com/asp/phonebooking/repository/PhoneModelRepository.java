package com.asp.phonebooking.repository;

import com.asp.phonebooking.entity.PhoneModel;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PhoneModelRepository extends CrudRepository<PhoneModel, Long> {

    @Query("select pm from PhoneModel pm "
            + "where pm.updateWithExternal = true "
            + "  and (pm.updatedWithExternalAt is null or pm.updatedWithExternalAt < :timePoint)")
    List<PhoneModel> getAllUpdateWithExternalRequired(@Param("timePoint") Instant timePoint);
}
