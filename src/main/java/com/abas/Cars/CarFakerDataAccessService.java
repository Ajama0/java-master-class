package com.abas.Cars;

import com.abas.Exceptions.CarNotFoundException;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarFakerDataAccessService implements CarDAO {

    List<Car> fakerCars = new ArrayList<Car>();


    public CarFakerDataAccessService() {
        Faker faker = new Faker();

        for (int i = 0; i < 10; i++) {
            fakerCars.add(new Car(UUID.randomUUID(), Brand.MERCEDES,
                    BigDecimal.valueOf(faker.number().randomDouble(2, 20, 200)),
                    faker.bool().bool()));
        }
    }

    @Override
    public List<Car> findAll() {
        return fakerCars;

    }

    @Override
    public boolean carExists(UUID id) {
        fakerCars.stream().filter((car) -> car.getId().equals(id)).findFirst().orElseThrow(() ->
                new CarNotFoundException("Car with id " + id + " not found"));
}
