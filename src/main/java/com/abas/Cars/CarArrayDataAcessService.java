package com.abas.Cars;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CarArrayDataAcessService implements CarDAO{

    static List<Car> cars = new ArrayList<>();

    static{
        cars.add(new Car(UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890") ,
                Brand.AUDI,new BigDecimal("100.56"), false ));
        cars.add(new Car(UUID.fromString("9f8e7d6c-5b4a-3210-fedc-ba9876543210"),
                Brand.MERCEDES,new BigDecimal("40.56"), true ));


        System.out.println(cars);
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
    public List<Car> findAll() {
        return cars;
    }
}
