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

        }throw new RuntimeException("Booking is null");

    }

        public List<CarBooking> findAllBookings () {
            /// bookings happen at run time not pre-seed.
            return bookingList;

        }

        public CarBooking cancelBooking (UUID id){
            // to delete the booking we can just the booking status to cancelled so it can be available again
            for (CarBooking booking : bookingList) {
                if (booking!= null && booking.getId().equals(id)) {
                    booking.setBookingStatus(BookingStatus.CANCELLED);
                    System.out.println("Booking with id:" + id + "successfully cancelled");
                    return booking;

                }
            }
            throw new RuntimeException("Booking with id:" + id + " not found");
        }


        @Override
        public CarBooking findById (UUID id){
            for (CarBooking booking : bookingList) {
                if (booking != null && booking.getId().equals(id)) {
                    return booking;
                }

            }
            throw new IllegalStateException("Booking not found");
        }
    }



