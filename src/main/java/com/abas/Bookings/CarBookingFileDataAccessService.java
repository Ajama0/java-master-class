package com.abas.Bookings;

import com.abas.Cars.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CarBookingFileDataAccessService implements BookingDAO{

    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CarBooking cancelBooking(CarBooking booking) {
        List<CarBooking> existing = findAllBookings();

        CarBooking bookingToCancel = existing.stream()
                .filter(b -> b.getId().equals(booking.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Booking with id " + booking.getId() + " not found"));

        bookingToCancel.setBookingStatus(BookingStatus.CANCELLED);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(existing);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save bookings", e);
        }

        return bookingToCancel;
    }



    @Override
    public UUID save(CarBooking booking) {
        if(booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        /// rewriting the entire file back
        List<CarBooking> bookings = findAllBookings();
        bookings.add(booking);

        /// write to the file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(bookings);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save bookings", e);
        }

        System.out.println("Booking saved: " + booking.getId());
        return booking.getId();

    }



    @Override
    public CarBooking findById(UUID id) {
        List<CarBooking> bookings = findAllBookings();

        return bookings.stream().filter(b -> b.getId().equals(id)).findFirst()
                .orElseThrow(()->new RuntimeException("Booking with id " + id + " not found"));


    };



    @Override
    public List<CarBooking> findAllBookings() {

        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            ///  return empty list
            return new ArrayList<>();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
               Object o =  inputStream.readObject();
               return (List<CarBooking>) o;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Failed to read bookings", e);
            }
        }

}
