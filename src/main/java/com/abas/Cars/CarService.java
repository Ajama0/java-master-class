package com.abas.Cars;

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

        List<Car> electricCars = new ArrayList<>();

        for (Car car : cars) {
            if(car!= null && car.getElectric()){
                electricCars.add(car);

            }
        }
        return electricCars;

    }

    public Car findById(UUID id){
        for(Car car : carDAO.findAll()){
            if( car!= null && car.getId().equals(id)){
                return car;
            }
        }
        throw new IllegalArgumentException("Car not found with id: " + id);
    }

    public List<Car> findAll(){
        return carDAO.findAll();
    }
}
