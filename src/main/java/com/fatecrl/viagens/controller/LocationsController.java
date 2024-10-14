package com.fatecrl.viagens.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatecrl.viagens.model.Location;
import com.fatecrl.viagens.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationsController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> getAll(){
        return ResponseEntity.ok(locationService.findAll());
    }
}