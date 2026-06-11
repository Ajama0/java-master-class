package com.abas.Bookings;

import com.abas.Cars.Car;
import com.abas.Cars.CarDAO;
import com.abas.Users.User;
import com.abas.Users.UserDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingService {

     private final UserDAO userDAO;
     private final CarDAO carDAO;
     private final BookingDAO bookingDAO;

    public BookingService(UserDAO userDAO, CarDAO carDAO, BookingDAO bookingDAO) {
        this.userDAO = userDAO;
        this.carDAO = carDAO;
        this.bookingDAO = bookingDAO;
    }


    public UUID createBooking(User user, Car car, LocalDate startDate, LocalDate endDate) {
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
        List<CarBooking> bookings = bookingDAO.findAllBookings();

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

    public CarBooking cancelBooking(UUID bookingId) {
        for (CarBooking booking : bookingDAO.findAllBookings()) {
            if (booking.getId().equals(bookingId) && booking.getBookingStatus() == BookingStatus.ACTIVE) {
                return bookingDAO.cancelBooking(bookingId);
            }
        }
        throw new IllegalArgumentException("Booking does not exist or may be active");

    }

    public List<CarBooking> getAllBookings(){
        return bookingDAO.findAllBookings();
    }


    public List<CarBooking> getAllBookingsByUser(UUID userId){
        List<CarBooking> bookings = bookingDAO.findAllBookings();
        List<CarBooking> userBookings = new ArrayList<>();

        for (CarBooking booking : bookings) {
            if(booking!=null && booking.getUser().getId().equals(userId)){
                userBookings.add(booking);
            }
        }
        return userBookings;
    }

    public List<Car> getAllAvailableCars() {
        List<Car> allCars = carDAO.findAll();
        List<CarBooking> bookings = bookingDAO.findAllBookings();
        List<Car> availableCars = new ArrayList<>();

        for (Car car : allCars) {
            if(car!=null && !isCarActivelyBooked(car, bookings)){
                availableCars.add(car);
            }

        }return availableCars;

        }



    private boolean isCarActivelyBooked(Car car, List<CarBooking> bookings) {
        for (CarBooking booking : bookings) {
            if (booking != null
                    && booking.getCar().equals(car)
                    && booking.getBookingStatus() == BookingStatus.ACTIVE) {
                return true;
            }
        }
        return false;
    }

}
