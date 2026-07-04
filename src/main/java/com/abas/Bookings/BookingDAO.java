package com.abas.Bookings;

import java.util.List;
import java.util.UUID;

public interface BookingDAO {

    List<CarBooking> findAllBookings();

    CarBooking cancelBooking(CarBooking booking);

    UUID save(CarBooking booking);

    CarBooking findById(UUID id);
}
