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


    public CarBooking createBooking(User user, Car car, LocalDate startDate, LocalDate endDate) {
        // ensure user exists and car exists

        if(!userDAO.userExists(user)){
            throw new IllegalArgumentException("User does not exist");
        }
        if(!carDAO.carExists(car.getId())){
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
            if (booking.getCar().equals(car) && booking.getBookingStatus() == BookingStatus.ACTIVE){
                throw new IllegalArgumentException("car is not available");
            }
        }

        //we can now calculate the price of the car for the number of days the user wants it for
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal totalRentalPrice = car.getRentalPricePerDay().multiply(new BigDecimal(days));
        System.out.println("Total rental price: " + totalRentalPrice);

        // create a new booking and save it
        CarBooking bookedCar = new CarBooking(UUID.randomUUID(), user ,car, startDate, endDate,
                LocalDateTime.now(),BookingStatus.ACTIVE);

        return bookingDAO.save(bookedCar);

    }

    public CarBooking[] deleteBooking(CarBooking booking){
        if(booking.getBookingStatus() == BookingStatus.ACTIVE){
            throw new IllegalArgumentException("active booking can not be deleted");
        }

        //also if a booking is already null, then we cant delete it.
        return bookingDAO.deleteBooking(booking);
    }

    public CarBooking[] getAllBookings(){
        return bookingDAO.findAllBookings();
    }


    public CarBooking[] getAllBookingsByUser(UUID userId){
        CarBooking[] bookings = bookingDAO.findAllBookings();

        // we need to know how many bookings the user has first.
        int count = 0;
        for(CarBooking booking : bookings){
            if(booking.getUser().getId().equals(userId)){
                count++;
            }
        }

        int index = 0;
        CarBooking[] userBookings = new CarBooking[count];
        for(CarBooking booking : bookings){
            if(booking.getUser().getId().equals(userId)){
                userBookings[index++] = booking;
            }
        }
        return userBookings;
    }

    public Car[] getAllAvailableCars(){
        CarBooking[] bookings = bookingDAO.findAllBookings();

        // if the booking is cancelled or completed it means the car is available

        int count = 0;
        for(CarBooking booking : bookings){
            if(booking.getBookingStatus() != BookingStatus.ACTIVE){
                count++;
            }
        }
        int index = 0;
        Car[] availableCars = new Car[count];
        for(CarBooking booking : bookings){
            if(booking.getBookingStatus() != BookingStatus.ACTIVE){
                availableCars[index++] = booking.getCar();
            }
        }
        return availableCars;

    }

}
