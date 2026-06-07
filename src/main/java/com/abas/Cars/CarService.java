package com.abas.Cars;

import java.math.BigDecimal;
import java.util.UUID;

public class CarService {


    private final CarDAO carDAO;

    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public Car[] allElectricCars(){
        Car[] cars = findAll();
        //tells us the size of the array of electric cars
        int count = 0;
        for(Car car : cars){
            if(car!=  null && car.getElectric()){
                count++;
            }
        }

        // size of the array will be the size of the electric cars therefore we can loop over it
        Car[] electricCars = new Car[count];

        int index = 0;
        for(Car car : cars){
            if(car!= null && car.getElectric()){
                electricCars[index] = car;
                index++;
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

    public Car[] findAll(){
        return carDAO.findAll();
    }
}
