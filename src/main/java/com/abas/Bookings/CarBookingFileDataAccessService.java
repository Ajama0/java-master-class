package com.abas.Bookings;

import com.abas.Cars.Car;

import java.io.*;
import java.util.UUID;

public class CarBookingFileDataAccessService implements BookingDAO{

    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CarBooking cancelBooking(UUID id) {

        CarBooking[] existing = findAllBookings();
        CarBooking cancelledBooking = null;


        /// find the booking and update its status
        boolean found = false;
        for (CarBooking booking : existing) {
            if (booking.getId().equals(id)) {
                cancelledBooking = booking;
                booking.setBookingStatus(BookingStatus.CANCELLED);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Booking not found with id: " + id);
        }

        /// write the updated array back
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(existing);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save bookings", e);
        }
        return cancelledBooking;


    }



    @Override
    public UUID save(CarBooking booking) {

        CarBooking[] existing = findAllBookings();

        /// because we know the size of the array lets add one more index for the array we are appending
        CarBooking[] updated = new CarBooking[existing.length + 1];

        /// copy everything into a new array
        for (int i = 0; i < existing.length; i++) {
            updated[i] = existing[i];
        }

        /// accessing the last index
        updated[existing.length] = booking;


        /// write to the file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(updated);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save bookings", e);
        }

        System.out.println("Booking saved: " + booking.getId());
        return booking.getId();

    }



    @Override
    public CarBooking findById(UUID id) {
        CarBooking[] bookings = findAllBookings();

        for (CarBooking booking : bookings) {
            if (booking != null && booking.getId().equals(id)) {
                return booking;
            }
        }

        return null; // not found
    }


    @Override
    public CarBooking[] findAllBookings() {

        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new CarBooking[0];
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
                return (CarBooking[]) inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Failed to read bookings", e);
            }
        }

}
