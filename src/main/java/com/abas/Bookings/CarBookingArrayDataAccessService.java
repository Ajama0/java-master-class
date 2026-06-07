package com.abas.Bookings;

import java.util.UUID;

public class CarBookingArrayDataAccessService implements BookingDAO{

    private int capacity = 100;
    private CarBooking[] bookings = new CarBooking[capacity];
    private int count = 0;


    public CarBooking save(CarBooking cb) {
        // if count is greater than capacity then there is no more space for bookings
        if(count >= capacity) {
            throw new IllegalStateException("Booking capacity exceeded");
        }
        bookings[count++] = cb;
        return cb;

    }

    public CarBooking[] findAllBookings() {
        //we know the size of the non null array
        int count = 0;
        for(CarBooking cb : bookings) {
            if(cb != null) {
                count++;
            }

        }
        CarBooking[] res = new CarBooking[count];
        int index = 0;
        for(CarBooking cb : bookings) {
            if(cb != null) {
                res[index++] = cb;
            }
        }
        return res;


    }

    public CarBooking[] cancelBooking(UUID id) {
        // to delete the booking we can just set the index of where that booking is to null?
        for (int i = 0; i < bookings.length; i++) {
            if(bookings[i]!=null && bookings[i].getId().equals(id) ) {
                bookings[i] = null;
            }
        }
        return bookings;


    }


    @Override
    public CarBooking findById(UUID id) {
        for (CarBooking booking : bookings) {
            if(booking.getId().equals(id)) {
                return booking;
            }

        }
        throw new IllegalStateException("Booking not found");
    }
}
