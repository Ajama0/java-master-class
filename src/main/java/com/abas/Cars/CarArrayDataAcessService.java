package com.abas.Cars;

import java.math.BigDecimal;
import java.util.UUID;

public class CarArrayDataAcessService implements CarDAO{

    private final static Car[] cars = new Car[3];

    static{
        cars[0] = new Car(UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890") ,
                Brand.AUDI,new BigDecimal("100.56"), false );
        cars[1] = new Car(UUID.fromString("9f8e7d6c-5b4a-3210-fedc-ba9876543210"),
                Brand.MERCEDES,new BigDecimal("40.56"), true );
    }


    @Override
    public boolean carExists(UUID id) {
        for (Car car : cars) {
            if (car != null && car.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Car[] findAll() {
        return new Car[0];
    }
}
