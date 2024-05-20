package com.example.helloworld.ServiceTests;
import com.example.helloworld.controller.AirportController;
import com.example.helloworld.pojo.Airport;
import com.example.helloworld.repository.AirportRepository;
import com.example.helloworld.service.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class AirportServiceTest {

    private MockMvc mockMvc;

    @Mock
    private AirportRepository airportRepository;



    @InjectMocks
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(airportController).build();
    }

    @Test
    void testListAllAirports() {
        when(airportRepository.findAll()).thenReturn(Arrays.asList(new Airport(), new Airport()));

        List<Airport> airports = airportService.listAllAirports();

        assertNotNull(airports);
        assertEquals(2, airports.size());
    }

    @Test
    void testFindAirportsByLocation() {
        String city = "London";
        String country = "UK";
        when(airportRepository.findByCityAndCountry(city, country))
                .thenReturn(Arrays.asList(new Airport(), new Airport()));

        List<Airport> airports = airportService.findAirportsByLocation(city, country);

        assertNotNull(airports);
        assertEquals(2, airports.size());
    }


    @Test
    void testGetAirportDetails() {
        String airportId = "airport123";
        Airport airport = new Airport();
        airport.setAirportId(airportId);

        when(airportRepository.findById(airportId)).thenReturn(Optional.of(airport));

        Airport result = airportService.getAirportDetails(airportId);

        assertNotNull(result);
        assertEquals(airportId, result.getAirportId());
    }

    @Test
    void testGetAirportDetailsNotFound() {
        String airportId = "airport123";

        when(airportRepository.findById(airportId)).thenReturn(Optional.empty());

        Airport result = airportService.getAirportDetails(airportId);

        assertNull(result);
    }


    @Test
    void testAddAirport() {
        Airport newAirport = new Airport();
        newAirport.setAirportId("airport123");

        when(airportRepository.save(any(Airport.class))).thenReturn(newAirport);

        Airport result = airportService.addAirport(newAirport);

        assertNotNull(result);
        assertEquals("airport123", result.getAirportId());
    }


}
