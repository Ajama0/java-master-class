package com.abas.Bookings;

import com.abas.Cars.Brand;
import com.abas.Cars.Car;
import com.abas.Users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarBookingFileDataAccessServiceTest {

    private CarBookingFileDataAccessService underTest;
    private User user;
    private Car car;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("bookings.dat").toString();
        underTest = new CarBookingFileDataAccessService(filePath);
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
    void findAllBookingsReturnsEmptyListWhenFileDoesNotExist() {
        List<CarBooking> bookings = underTest.findAllBookings();

        assertThat(bookings).isEmpty();
    }

    @Test
    void saveWritesBookingToFileAndReturnsItsId() {
        //given
        CarBooking booking = newBooking();

        //when
        UUID returnedId = underTest.save(booking);

        //then
        assertThat(returnedId).isEqualTo(booking.getId());
        assertThat(underTest.findAllBookings()).extracting(CarBooking::getId)
                .containsExactly(booking.getId());
    }

    @Test
    void saveThrowsWhenBookingIsNull() {
        assertThatThrownBy(() -> underTest.save(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be null");
    }

    @Test
    void findByIdReturnsBookingWhenExists() {
        //given
        CarBooking booking = newBooking();
        underTest.save(booking);

        //when
        CarBooking result = underTest.findById(booking.getId());

        //then
        assertThat(result.getId()).isEqualTo(booking.getId());
    }

    @Test
    void findByIdThrowsWhenBookingDoesNotExist() {
        assertThatThrownBy(() -> underTest.findById(UUID.randomUUID()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void cancelBookingSetsStatusToCancelledAndPersistsIt() {
        //given
        CarBooking booking = newBooking();
        underTest.save(booking);

        //when
        CarBooking cancelledBooking = underTest.cancelBooking(booking);


        assertThat(cancelledBooking.getBookingStatus()).isEqualTo(BookingStatus.CANCELLED);
    }
}