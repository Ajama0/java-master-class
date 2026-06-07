package com.abas.Bookings;

import com.abas.Cars.Car;
import com.abas.Users.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CarBooking implements Serializable {

    private UUID id;
    private User user;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime bookedAt;

    private BookingStatus bookingStatus;

    public CarBooking(UUID id, User user, Car car,
                      LocalDate startDate, LocalDate endDate,
                      LocalDateTime bookedAt, BookingStatus bookingStatus) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookedAt = bookedAt;
        this.bookingStatus = bookingStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "CarBooking{" +
                "id=" + id +
                ", user=" + user +
                ", car=" + car +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", bookedAt=" + bookedAt +
                ", bookingStatus=" + bookingStatus +
                '}';
    }
}
