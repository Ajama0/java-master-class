package com.abas.Bookings;

import com.abas.Cars.Car;

public class BookingDAO {

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
        return bookings;
    }
}
