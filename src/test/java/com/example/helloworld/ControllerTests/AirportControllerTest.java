package com.example.helloworld.ControllerTests;

import com.example.helloworld.controller.AirportController;
import com.example.helloworld.pojo.Airport;
import com.example.helloworld.service.AirportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirportController.class)
public class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;

    private List<Airport> airportList;

    @BeforeEach
    public void setUp() {
        Airport airport1 = new Airport("1", "Airport One", "City One", "Country One", "Timezone One");
        Airport airport2 = new Airport("2", "Airport Two", "City Two", "Country Two", "Timezone Two");

        airportList = Arrays.asList(airport1, airport2);

        mockMvc = MockMvcBuilders.standaloneSetup(new AirportController(airportService)).build();
    }

    @Test
    public void listAllAirports() throws Exception {
        when(airportService.listAllAirports()).thenReturn(airportList);

        mockMvc.perform(get("/airports/allAirports"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].airportId").value("1"))
                .andExpect(jsonPath("$[0].name").value("Airport One"))
                .andExpect(jsonPath("$[0].city").value("City One"))
                .andExpect(jsonPath("$[0].country").value("Country One"))
                .andExpect(jsonPath("$[0].timezone").value("Timezone One"))
                .andExpect(jsonPath("$[1].airportId").value("2"))
                .andExpect(jsonPath("$[1].name").value("Airport Two"))
                .andExpect(jsonPath("$[1].city").value("City Two"))
                .andExpect(jsonPath("$[1].country").value("Country Two"))
                .andExpect(jsonPath("$[1].timezone").value("Timezone Two"));

        verify(airportService, times(1)).listAllAirports();
    }

    @Test
    public void findAirportsByLocation() throws Exception {
        when(airportService.findAirportsByLocation("City One", "Country One")).thenReturn(Arrays.asList(airportList.get(0)));

        mockMvc.perform(get("/airports/search")
                        .param("city", "City One")
                        .param("country", "Country One"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].airportId").value("1"))
                .andExpect(jsonPath("$[0].name").value("Airport One"))
                .andExpect(jsonPath("$[0].city").value("City One"))
                .andExpect(jsonPath("$[0].country").value("Country One"))
                .andExpect(jsonPath("$[0].timezone").value("Timezone One"));

        verify(airportService, times(1)).findAirportsByLocation("City One", "Country One");
    }

    @Test
    public void getAirportDetails() throws Exception {
        Airport airport = airportList.get(0);
        when(airportService.getAirportDetails("1")).thenReturn(airport);

        mockMvc.perform(get("/airports/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.airportId").value("1"))
                .andExpect(jsonPath("$.name").value("Airport One"))
                .andExpect(jsonPath("$.city").value("City One"))
                .andExpect(jsonPath("$.country").value("Country One"))
                .andExpect(jsonPath("$.timezone").value("Timezone One"));

        verify(airportService, times(1)).getAirportDetails("1");
    }

    @Test
    public void addAirport() throws Exception {
        Airport newAirport = new Airport("3", "Airport Three", "City Three", "Country Three", "Timezone Three");
        when(airportService.addAirport(Mockito.any(Airport.class))).thenReturn(newAirport);

        mockMvc.perform(post("/airports/addAirport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newAirport)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.airportId").value("3"))
                .andExpect(jsonPath("$.name").value("Airport Three"))
                .andExpect(jsonPath("$.city").value("City Three"))
                .andExpect(jsonPath("$.country").value("Country Three"))
                .andExpect(jsonPath("$.timezone").value("Timezone Three"));

        verify(airportService, times(1)).addAirport(Mockito.any(Airport.class));
    }
}
