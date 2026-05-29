package com.abas.Cars;

public class CarService {

    private final CarDAO carDAO;

    public CarService() {
        this.carDAO = new CarDAO();
    }

    public Car[] allElectricCars(){
        Car[] cars = carDAO.findAll();
        //tells us the size of the array of electric cars
        int count = 0;
        for(Car car : cars){
            if(car!=  null & car.getElectric()){
                count++;
            }
        }

        // size of the array will be the size of the electric cars therefore we can loop over it
        Car[] electricCars = new Car[count];

        int index = 0;
        for(Car car : cars){
            if(car!= null & car.getElectric()){
                electricCars[index] = car;
                index++;
            }
        }
        return electricCars;
    }




}
