package com.car.car.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.car.model.Car;
import com.car.car.repository.CarRepository;


@CrossOrigin("*")
@RestController
@RequestMapping("/cars")
public class CarController {
     
    
    @Autowired
    private CarRepository carRepository;

    // for posting car information

    @PostMapping("/add")
    public void addCars(@RequestBody Car car){
        carRepository.save(car);
    }


    //for getting list of cars

    @GetMapping("/all")
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }


    //for getting a car with id

    @GetMapping("/{id}")
    public Car getACar(@PathVariable Long id){
        return carRepository.findById(id).orElse(null);
    }
}
