package com.abas.Bookings;

import com.abas.Cars.Brand;
import com.abas.Cars.Car;
import com.abas.Cars.CarDAO;
import com.abas.Users.User;
import com.abas.Users.UserDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
        verify(bookingDAO).save(carBooking.capture());
        CarBooking carBookingValue = carBooking.getValue();

        assert(carBookingValue.getCar().getBrand().equals(Brand.MERCEDES));
        assert(carBookingValue.getUser().getName().equals("Abas"));
        assert(carBookingValue.getStartDate().equals(startDate));
        assert(carBookingValue.getEndDate().equals(endDate));

    }

    @Test
    void CanCancelBooking() {

        //given
        UUID uuid = UUID.randomUUID();
        when(bookingDAO.findAllBookings()).thenReturn(List.of(new CarBooking(uuid,
                new User(UUID.randomUUID(), "Abas"), new Car(UUID.randomUUID(), Brand.MERCEDES,
                BigDecimal.valueOf(500.0), true), LocalDate.now(), LocalDate.of(2026, 8,8),
                LocalDateTime.now(),
                BookingStatus.ACTIVE)));

        //when
        CarBooking booking = underTest.cancelBooking(uuid);

        //then
        verify(bookingDAO).cancelBooking(any());
        assert(booking.getId().equals(uuid));


    }

    @Test
    void getAllBookings() {

        //when
        underTest.getAllBookings();

        //then
        verify(bookingDAO, times(1)).findAllBookings();
    }


}