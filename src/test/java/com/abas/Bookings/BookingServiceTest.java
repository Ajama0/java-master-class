package com.abas.Bookings;

import com.abas.Cars.Brand;
import com.abas.Cars.Car;
import com.abas.Cars.CarDAO;
import com.abas.Users.User;
import com.abas.Users.UserDAO;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class BookingServiceTest {


    @Mock
    private UserDAO userDAO;

    @Mock
    private CarDAO carDAO;

    @Mock
    private BookingDAO bookingDAO;


    @InjectMocks
    private BookingService underTest;

    @Test
    void canCreateBookingSuccessfully() {
        //given
        User user = new User(UUID.randomUUID(), "Abas");
        Car car = new Car(UUID.randomUUID(), Brand.MERCEDES, BigDecimal.valueOf(150.00), true);
        LocalDate startDate = LocalDate.of(2026,8,8);
        LocalDate endDate = LocalDate.of(2026,9,9);



        when(userDAO.userExists(any())).thenReturn(true);
        when(carDAO.carExists(any())).thenReturn(true);
        when(bookingDAO.findAllBookings()).thenReturn(List.of());


        //when
        UUID booking = underTest.createBooking(user, car, startDate, endDate);


        //then
        ArgumentCaptor<CarBooking> carBooking = ArgumentCaptor.forClass(CarBooking.class);
        verify(bookingDAO.save(carBooking.capture()));
        CarBooking carBookingValue = carBooking.getValue();

        assertNotNull(booking);
        assert(carBookingValue.getCar().getBrand().equals(Brand.MERCEDES));
        assert(carBookingValue.getUser().getName().equals("Abas"));
        assert(carBookingValue.getStartDate().equals(startDate));
        assert(carBookingValue.getEndDate().equals(endDate));

    }

    @Test
    void cancelBooking() {
    }

    @Test
    void getAllBookings() {
    }

    @Test
    void getAllBookingsByUser() {
    }

    @Test
    void getAllAvailableCars() {
    }
}