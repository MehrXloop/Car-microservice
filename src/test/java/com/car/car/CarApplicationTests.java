package com.car.car;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.car.car.controller.CarController;
import com.car.car.model.Car;
import com.car.car.repository.CarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
class CarApplicationTests {

	private MockMvc mvc;
	@Mock
	private CarRepository carRepository;

	@InjectMocks
	private CarController carController;

	private JacksonTester<Car> jsonCar;

	private JacksonTester<Collection<Car>> jsonCars;

	@BeforeEach
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper());
		mvc = MockMvcBuilders.standaloneSetup(carController).build();
	}

	// adding car information
	@Test
	public void canAddACar() throws Exception {
		Car car = new Car(1L, "Aston Martin DB12", "It is a short Desc", "It is a Long Desc",
				"https://raw.githubusercontent.com/jeff-lent/roadrunnercars/main/AstonMartinDB12.png", 200000);
		when(carRepository.save(car)).thenReturn((car));
		mvc.perform(MockMvcRequestBuilders
				.post("/cars/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonCar.write(car).getJson()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// for getting car list
	@Test
	public void canGetAllCars() throws Exception {
		Car car1 = new Car(1L, "Aston Martin DB12", "It is a short Desc", "It is a Long Desc",
				"https://raw.githubusercontent.com/jeff-lent/roadrunnercars/main/AstonMartinDB12.png", 200000);
		Car car2 = new Car(2L, "Lamborghini Huracán Sterrato", "It is a short Desc", "It is a Long Desc",
				"https://raw.githubusercontent.com/jeff-lent/roadrunnercars/main/AstonMartinDB12.png", 200000);

		List<Car> listOfCars = new ArrayList<>();
		listOfCars.add(car1);
		listOfCars.add(car2);

		when(carRepository.findAll()).thenReturn(listOfCars);

		mvc.perform(MockMvcRequestBuilders
				.get("/cars/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.content().json(jsonCars.write(listOfCars).getJson()));
	}
    //getting one car with id
	@Test
	public void canGetACar() throws Exception {
		Car car = new Car(1L, "Aston Martin DB12", "It is a short Desc", "It is a Long Desc",
				"https://raw.githubusercontent.com/jeff-lent/roadrunnercars/main/AstonMartinDB12.png", 200000);
		when(carRepository.findById(1L)).thenReturn(Optional.of(car));
		mvc.perform(MockMvcRequestBuilders.get("/cars/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(jsonCar.write(car).getJson()));
	}

}
