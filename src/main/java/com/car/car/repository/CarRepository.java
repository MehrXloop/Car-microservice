package com.car.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.car.model.Car;

public interface CarRepository extends JpaRepository<Car,Long>{
    
}
