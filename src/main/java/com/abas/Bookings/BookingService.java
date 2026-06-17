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
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

        if (!userDAO.userExists(user)) {
            throw new IllegalArgumentException("User does not exist");
        }
        if (!carDAO.carExists(car.getId())) {
            throw new IllegalArgumentException("Car does not exist");
        }

        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("dates must not be in the past");
        }

        // start before end
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        // check the booking hasn't been taken
        List<CarBooking> bookings = bookingDAO.findAllBookings();

        bookings.stream().filter(booking-> booking.getCar().equals(car) &&
                booking.getBookingStatus().equals(BookingStatus.ACTIVE)).findFirst().ifPresent(booking -> {
                    throw new IllegalArgumentException("Booking already exists");
        });


        //we can now calculate the price of the car for the number of days the user wants it for
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal totalRentalPrice = car.getRentalPricePerDay().multiply(new BigDecimal(days));
        System.out.println("Total rental price: " + totalRentalPrice);

        // create a new booking and save it
        CarBooking bookedCar = new CarBooking(UUID.randomUUID(), user, car, startDate, endDate,
                LocalDateTime.now(), BookingStatus.ACTIVE);

        return bookingDAO.save(bookedCar);

    }

    public CarBooking cancelBooking(UUID bookingId) {

        List<CarBooking> bookings = bookingDAO.findAllBookings();

        CarBooking bookingFound = bookings.stream().filter(
                        booking -> booking.getId().equals(bookingId)
                                && booking.getBookingStatus().equals(BookingStatus.ACTIVE)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        bookingDAO.cancelBooking(bookingFound);
        return bookingFound;




    }

    public List<CarBooking> getAllBookings() {
        return bookingDAO.findAllBookings();
    }


    public List<CarBooking> getAllBookingsByUser(UUID userId) {
        List<CarBooking> bookings = bookingDAO.findAllBookings();

        return bookings.stream().filter(b -> b.getUser().getId().equals(userId)).toList();

    }

    public List<Car> getAllAvailableCars() {
        List<Car> allCars = carDAO.findAll();
        List<CarBooking> bookings = bookingDAO.findAllBookings();

        return allCars.stream().filter(car -> car != null &&
                        !isCarActivelyBooked(car, bookings))
                .toList();


    }


    private boolean isCarActivelyBooked(Car car, List<CarBooking> bookings) {
        Predicate<CarBooking> checkForBooking = (booking) -> {
            if (booking != null && booking.getCar().equals(car) && booking.getBookingStatus() == BookingStatus.ACTIVE) {
                return true;
            } else {
                return false;
            }
        };
        return bookings.stream().anyMatch(checkForBooking);


    }


}
