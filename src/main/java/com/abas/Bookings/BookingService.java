package com.abas.Bookings;

import com.abas.Cars.Car;
import com.abas.Cars.CarDAO;
import com.abas.Users.User;
import com.abas.Users.UserDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class BookingService {

    public UserDAO userDAO;
    public CarDAO carDAO;
    public BookingDAO bookingDAO;

    public BookingService() {
        this.userDAO = new UserDAO();
        this.carDAO = new CarDAO();
        this.bookingDAO = new BookingDAO();
    }


    public CarBooking bookCar(User user, Car car, LocalDate startDate, LocalDate endDate) {
        // ensure user exists and car exists

        if(!userDAO.UserExists(user)){
            throw new IllegalArgumentException("User does not exist");
        }
        if(car!= null && !carDAO.carExists(car.getId())){
            throw new IllegalArgumentException("Car does not exist");
        }

        if(startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("dates must not be in the past");
        }

        // start before end
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        // check the booking hasn't been taken
        CarBooking[] bookings = bookingDAO.findAllBookings();

        for (CarBooking booking : bookings) {
            if (booking.getCar().equals(car) && booking.getBookingStatus() == BookingStatus.Active){
                throw new IllegalArgumentException("car is not available");
            }
        }

        //we can now calculate the price of the car for the number of days the user wants it for
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal totalRentalPrice = car.getRentalPricePerDay().multiply(new BigDecimal(days));
        System.out.println("Total rental price: " + totalRentalPrice);

        // create a new booking and save it
        CarBooking bookedCar = new CarBooking(UUID.randomUUID(), user ,car, startDate, endDate,
                LocalDateTime.now(),BookingStatus.Active);

        return bookingDAO.save(bookedCar);

    }

}
