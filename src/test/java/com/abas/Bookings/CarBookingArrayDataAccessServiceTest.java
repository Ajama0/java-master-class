package com.abas.Bookings;

import com.abas.Cars.Brand;
import com.abas.Cars.Car;
import com.abas.Users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarBookingArrayDataAccessServiceTest {

    private CarBookingArrayDataAccessService underTest;
    private User user;
    private Car car;

    @BeforeEach
    void setUp() {
        underTest = new CarBookingArrayDataAccessService();
        user = new User(UUID.randomUUID(), "Abas");
        car = new Car(UUID.randomUUID(), Brand.MERCEDES, BigDecimal.valueOf(150.00), false);
    }

    private CarBooking newBooking() {
        return new CarBooking(
                UUID.randomUUID(),
                user,
                car,
                LocalDate.of(2026, 8, 8),
                LocalDate.of(2026, 9, 9),
                LocalDateTime.now(),
                BookingStatus.ACTIVE
        );
    }

    @Test
    void saveAddsBookingAndReturnsItsId() {
        //given
        CarBooking booking = newBooking();

        //when
        UUID returnedId = underTest.save(booking);

        //then
        assertThat(returnedId).isEqualTo(booking.getId());
        assertThat(underTest.findAllBookings()).containsExactly(booking);
    }

    @Test
    void saveThrowsWhenBookingIsNull() {
        assertThatThrownBy(() -> underTest.save(null))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Booking is null");
    }

    @Test
    void findAllBookingsReturnsEmptyListWhenNoneSaved() {
        //when
        List<CarBooking> bookings = underTest.findAllBookings();

        //then
        assertThat(bookings).isEmpty();
    }

    @Test
    void findAllBookingsReturnsAllSavedBookings() {
        //given
        CarBooking booking1 = newBooking();
        CarBooking booking2 = newBooking();
        underTest.save(booking1);
        underTest.save(booking2);

        //when
        List<CarBooking> bookings = underTest.findAllBookings();

        //then
        assertThat(bookings).containsExactlyInAnyOrder(booking1, booking2);
    }

    @Test
    void cancelBookingSetsStatusToCancelled() {
        //given
        CarBooking booking = newBooking();
        underTest.save(booking);

        //when
        CarBooking cancelled = underTest.cancelBooking(booking);

        //then
        assertThat(cancelled.getBookingStatus()).isEqualTo(BookingStatus.CANCELLED);
        // confirm it mutated the same object held in the list, not a copy
        assertThat(underTest.findAllBookings().get(0).getBookingStatus())
                .isEqualTo(BookingStatus.CANCELLED);
    }

    @Test
    void findByIdReturnsBookingWhenExists() {
        //given
        CarBooking booking = newBooking();
        underTest.save(booking);

        //when
        CarBooking result = underTest.findById(booking.getId());

        //then
        assertThat(result).isEqualTo(booking);
    }

    @Test
    void findByIdThrowsWhenBookingDoesNotExist() {
        //given
        UUID nonExistentId = UUID.randomUUID();

        //when + then
        assertThatThrownBy(() -> underTest.findById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }
}