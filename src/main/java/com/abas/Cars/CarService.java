package com.abas.Cars;

import com.abas.Exceptions.CarNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarService {


    private final CarDAO carDAO;

    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public List<Car> allElectricCars(){
        List<Car> cars = findAll();

        return cars.stream().filter(Car::getElectric).toList();

    }

    public Car findById(UUID id) {

        return carDAO.findAll().stream().filter(car -> car.getId().equals(id)).findFirst().
                orElseThrow(() -> new CarNotFoundException("Car with id " + id + " not found"));

    }


    public List<Car> findAll(){
        return carDAO.findAll();
    }
}
