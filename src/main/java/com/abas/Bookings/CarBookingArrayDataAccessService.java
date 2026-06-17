package com.abas.Bookings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements BookingDAO {

    private final List<CarBooking> bookingList = new ArrayList<>();

    public UUID save(CarBooking cb) {
        if (cb != null) {
            bookingList.add(cb);
            return cb.getId();

        }

        throw new RuntimeException("Booking is null");

    }

    public List<CarBooking> findAllBookings() {
        /// bookings happen at run time not pre-seed.
        return bookingList;

    }

    public CarBooking cancelBooking(CarBooking cb) {
        // to delete the booking we can just the booking status to cancelled so it can be available again
        cb.setBookingStatus(BookingStatus.CANCELLED);
        return cb;
    }


    @Override
    public CarBooking findById(UUID id) {
        List<CarBooking> bookings = findAllBookings();

        return bookings.stream().filter(b -> b.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Booking with id " + id + " not found"));
    }


}



