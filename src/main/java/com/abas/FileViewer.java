package com.abas;

import com.abas.Bookings.CarBooking;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class FileViewer {

    public static void main(String[] args) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("bookings.dat"))) {
            List<CarBooking> bookings = (List<CarBooking>) in.readObject();
            //System.out.println("Total bookings: " + bookings.size());
            for (CarBooking booking : bookings) {
               // System.out.println(booking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
