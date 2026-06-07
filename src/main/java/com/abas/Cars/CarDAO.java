package com.abas.Cars;

import java.math.BigDecimal;
import java.util.UUID;

public interface CarDAO {


    boolean carExists(UUID id);


    Car[] findAll ();




}
