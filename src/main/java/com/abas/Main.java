package com.abas;
// TODO 1. create a new branch called initial-implementation
// TODO 2. create a package with your name. i.e com.franco and move this file inside the new package
// TODO 3. implement https://amigoscode.com/learn/java-cli-build/lectures/3a83ecf3-e837-4ae5-85a8-f8ae3f60f7f5


import com.abas.Bookings.*;
import com.abas.Cars.Car;
import com.abas.Cars.CarArrayDataAcessService;
import com.abas.Cars.CarDAO;
import com.abas.Cars.CarService;
import com.abas.Users.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    private static final String filePath = "bookings.dat";

    /// implementations
    private static final UserDAO userArrayDAO = new UserArrayDataAccessService();
    private static final CarDAO carDao = new CarArrayDataAcessService();
    private static final BookingDAO bookingDao = new CarBookingArrayDataAccessService();
    private static final BookingDAO fileBookingDao = new CarBookingFileDataAccessService(filePath);
    private static final UserDAO fakerDAO = new UserFakerDataAccessService();

    /// services
    private static final BookingService bookingService = new BookingService(fakerDAO, carDao, bookingDao);
    private static final CarService carService = new CarService(carDao);
    private static final UserService userService = new UserService(fakerDAO);


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Java Master Class");



        boolean running = true;

        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> handleBookCar();
                case 2 -> handleDeleteBooking();
                case 3 -> handleViewUserBookings();
                case 4 -> handleViewAllBookings();
                case 5 -> handleViewAvailableCars();
                case 6 -> handleViewElectricCars();
                case 7 -> handleViewAllUsers();
                case 8 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice");
            }
        }

        scanner.close();
    }



    private static void printMenu() {
        System.out.println("\n=== Car Rental ===");
        System.out.println("1. Book a Car");
        System.out.println("2. Delete Booking");
        System.out.println("3. View User Bookings");
        System.out.println("4. View All Bookings");
        System.out.println("5. View Available Cars");
        System.out.println("6. View Electric Cars");
        System.out.println("7. View All Users");
        System.out.println("8. Exit");
        System.out.print("Choose: ");

    }

    private static void handleBookCar() {
        try {
            System.out.print("Enter user ID: ");
            UUID userId = UUID.fromString(scanner.nextLine());
            System.out.println("the uuid being passed in" + userId);
            User user = userService.findById(userId);

            System.out.print("Enter car ID: ");
            UUID carId = UUID.fromString(scanner.nextLine());
            Car car = carService.findById(carId);

            System.out.print("Start date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());

            System.out.print("End date (YYYY-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());

            bookingService.createBooking(user, car, startDate, endDate);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleDeleteBooking() {
        try {
            System.out.print("Enter booking ID: ");
            UUID bookingId = UUID.fromString(scanner.nextLine());

            CarBooking booking = bookingService.cancelBooking(bookingId);
            System.out.println("Booking" + booking + " has been cancelled successfully ");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void handleViewUserBookings() {
        try {
            System.out.print("Enter user ID: ");
            UUID userId = UUID.fromString(scanner.nextLine());

            List<CarBooking> bookings = bookingService.getAllBookingsByUser(userId);

            if (bookings.isEmpty()) {
                System.out.println("No bookings found for this user");
                return;
            }

            System.out.println("=== User Bookings ===");
            for (CarBooking booking : bookings) {
                System.out.println(booking);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleViewAllBookings() {
        List<CarBooking> bookings = bookingService.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("No bookings in the system");
            return;
        }

        System.out.println("=== All Bookings ===");
        for (CarBooking booking : bookings) {
            System.out.println(booking);
        }
    }

    private static void handleViewAvailableCars() {
        List<Car> cars = carService.findAll();

        if (cars.isEmpty()) {
            System.out.println("No cars available");
            return;
        }

        System.out.println("=== Available Cars ===");
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    private static void handleViewElectricCars() {
        List<Car> cars =  carService.allElectricCars();

        if (cars.isEmpty()) {
            System.out.println("No electric cars available");
            return;
        }

        System.out.println("=== Electric Cars ===");
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    private static void handleViewAllUsers() {
        List<User> users = userService.findAll();

        System.out.println("=== All Users ===");
        for (User user : users) {
            if (user != null) {
                System.out.println(user);
            }
        }
    }

    private static void handleExit() {
        System.out.println("Thanks for using Car Rental. Goodbye!");
    }











}
