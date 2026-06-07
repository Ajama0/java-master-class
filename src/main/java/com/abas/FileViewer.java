package com.abas;

import com.abas.Bookings.CarBooking;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class FileViewer {

    public static void main(String[] args) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("bookings.dat"))) {
            CarBooking[] bookings = (CarBooking[]) in.readObject();
            System.out.println("Total bookings: " + bookings.length);
            for (CarBooking booking : bookings) {
                System.out.println(booking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
