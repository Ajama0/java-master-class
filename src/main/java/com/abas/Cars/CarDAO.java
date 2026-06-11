package com.abas.Cars;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CarDAO {


    boolean carExists(UUID id);


    List<Car> findAll ();




}
