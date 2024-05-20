package com.example.helloworld.service;

import com.example.helloworld.pojo.Airport;
import com.example.helloworld.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
    public class AirportService {
        private final AirportRepository airportRepository;

        @Autowired
        public AirportService(AirportRepository airportRepository) {
            this.airportRepository = airportRepository;
        }

        public List<Airport> listAllAirports() {
            return airportRepository.findAll();
        }

        public List<Airport> findAirportsByLocation(String city, String country) {
            return airportRepository.findByCityAndCountry(city, country);
        }

        public Airport getAirportDetails(String id) {
            Optional<Airport> airport = airportRepository.findById(id);
            return airport.orElse(null);
        }

        public Airport addAirport(Airport airport) {
        return airportRepository.save(airport);
        }

}
