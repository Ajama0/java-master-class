package com.abas.Cars;

import com.abas.Exceptions.CarNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarDAO carDAO;


    @InjectMocks
    private CarService underTest;//



    @Test
    void EnsureOnlyElectricCarsAreReturned() {

        //given
        Car electricCar = new Car(UUID.randomUUID(), Brand.TESLA, BigDecimal.valueOf(200.00), true);
        Car nonElectricCar = new Car(UUID.randomUUID(), Brand.MERCEDES, BigDecimal.valueOf(150.00), false);

        when(carDAO.findAll()).thenReturn(List.of(electricCar, nonElectricCar));

        //when
        List<Car> cars = underTest.allElectricCars();

        //then
        assertThat(cars).hasSize(1);
        assertThat(cars).containsExactly(electricCar);
        assertThat(cars).doesNotContain(nonElectricCar);

    }

    @Test
    void findByIdWhenCarExists() {

        //given
        UUID id = UUID.randomUUID();
        when(carDAO.findAll()).thenReturn(List.of(new Car(id, Brand.TESLA, BigDecimal.TEN, false)));

        //when
        Car car = underTest.findById(id);


        //then
        assertThat(car.getId()).isEqualTo(id);
        assertThat(car).isNotNull();
        assertDoesNotThrow(()-> new CarNotFoundException("\"Car with id \" + id + \" not found\""));
    }

    @Test
    void findByIdThrowsExceptionWhenCarDoesNotExist() {

        //given
        UUID id = UUID.randomUUID();
        when(carDAO.findAll()).thenReturn(List.of());


        //when & then
        assertThatThrownBy(()-> underTest.findById(id)).isInstanceOf(CarNotFoundException.class).
                hasMessageContaining("Car with id " + id + " not found");
    }

    @Test
    void findAll() {

        when(carDAO.findAll()).thenReturn(List.of(new Car(UUID.randomUUID(), Brand.TESLA, BigDecimal.TEN, false)));

         underTest.findAll();

        verify(carDAO, times(1)).findAll();
        verifyNoMoreInteractions(carDAO);
    }
}