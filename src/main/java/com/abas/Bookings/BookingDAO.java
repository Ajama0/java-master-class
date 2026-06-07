package com.abas.Bookings;

import java.util.UUID;

public interface BookingDAO {

    CarBooking[] findAllBookings();

    void cancelBooking(UUID id);

    void save(CarBooking booking);

    CarBooking findById(UUID id);
}
