package com.example.demo;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @GetMapping
    public Iterable<CarModel> cars() {
        return Arrays.asList(new CarModel("Fiat"), new CarModel("Ford"));
    }

}

@NoArgsConstructor
@Data
@AllArgsConstructor
class CarModel {
    private String name;
}

