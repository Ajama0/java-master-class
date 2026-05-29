package com.abas.Cars;

import java.math.BigDecimal;
import java.util.UUID;

public class CarDAO {
    private static Car[] cars = new Car[3];

    static{
        cars[0] = new Car(UUID.randomUUID(), Brand.AUDI,new BigDecimal("100.56"), false );
        cars[1] = new Car(UUID.randomUUID(), Brand.MERCEDES,new BigDecimal("40.56"), true );
    }

    public boolean carExists(UUID id) {
        for (Car car : cars) {
            if (car != null && car.getId().equals(id)) {
                return true;
            }
        }
        return false;

    }


        public Car[] findAll () {
            return cars;
        }





}
