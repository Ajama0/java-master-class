package com.abas.Bookings;

import java.util.UUID;

public interface BookingDAO {

    CarBooking[] findAllBookings();

    CarBooking[] cancelBooking(UUID id);

    CarBooking save(CarBooking booking);

    CarBooking findById(UUID id);
}
