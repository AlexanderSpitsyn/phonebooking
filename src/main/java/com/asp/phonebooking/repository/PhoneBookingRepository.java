package com.asp.phonebooking.repository;

import com.asp.phonebooking.entity.PhoneBooking;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PhoneBookingRepository extends CrudRepository<PhoneBooking, Long> {

    @Query("select pb from PhoneBooking pb "
            + "where pb.phoneId = :phoneId "
            + " and (pb.startpoint <= :startpoint and pb.endpoint > :startpoint "
            + "   or pb.startpoint < :endpoint    and pb.endpoint >= :endpoint "
            + "   or pb.startpoint >= :startpoint and pb.endpoint <= :endpoint)")
    List<PhoneBooking> getAlreadyBooked(@Param("phoneId") Long phoneId,
                                        @Param("startpoint") Instant startpoint,
                                        @Param("endpoint") Instant endpoint);

    List<PhoneBooking> getAllByPhoneIdAndEndpointGreaterThan(Long phoneId, Instant endpoint);
}
