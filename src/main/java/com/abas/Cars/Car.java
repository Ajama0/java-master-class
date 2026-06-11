package com.abas.Cars;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class Car implements Serializable {

    private UUID id;
    private Brand brand;
    private BigDecimal rentalPricePerDay;
    private Boolean isElectric;


    public Car(UUID id, Brand brand, BigDecimal rentalPricePerDay, Boolean isElectric) {
        this.id = id;
        this.brand = brand;
        this.rentalPricePerDay = rentalPricePerDay;
        this.isElectric = isElectric;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public BigDecimal getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public void setRentalPricePerDay(BigDecimal rentalPricePerDay) {
        this.rentalPricePerDay = rentalPricePerDay;
    }

    public Boolean getElectric() {
        return isElectric;
    }

    public void setElectric(Boolean electric) {
        isElectric = electric;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand=" + brand +
                ", rentalPricePerDay=" + rentalPricePerDay +
                ", isElectric=" + isElectric +
                '}';
    }
}
