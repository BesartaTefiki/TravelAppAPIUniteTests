package com.example.helloworld.ServiceTests;

import com.example.helloworld.pojo.Planner;
import com.example.helloworld.repository.PlannerRepository;
import com.example.helloworld.service.PlannerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PlannerServiceTest {

    @Mock
    private PlannerRepository plannerRepository;

    @InjectMocks
    private PlannerService plannerService;

    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    void testFindAllPlans() {
        when(plannerRepository.findAll()).thenReturn(Arrays.asList(new Planner(), new Planner()));

        List<Planner> plans = plannerService.findAllPlans();

        assertNotNull(plans);
        assertEquals(2, plans.size());
        verify(plannerRepository, times(1)).findAll();
    }

    @Test
    void testFindAllPlansEmpty() {
        when(plannerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Planner> plans = plannerService.findAllPlans();

        assertNotNull(plans);
        assertTrue(plans.isEmpty());
        verify(plannerRepository, times(1)).findAll();
    }

    @Test
    void testFindByDestination() {
        Planner planner = new Planner();
        planner.setPlannedDestination("Paris");

        when(plannerRepository.findByPlannedDestination("Paris")).thenReturn(Optional.of(planner));

        Optional<Planner> result = plannerService.findByDestination("Paris");

        assertTrue(result.isPresent());
        assertEquals("Paris", result.get().getPlannedDestination());
        verify(plannerRepository, times(1)).findByPlannedDestination("Paris");
    }

    @Test
    void testFindByDestinationNotFound() {
        when(plannerRepository.findByPlannedDestination("Paris")).thenReturn(Optional.empty());

        Optional<Planner> result = plannerService.findByDestination("Paris");

        assertFalse(result.isPresent());
        verify(plannerRepository, times(1)).findByPlannedDestination("Paris");
    }

    @Test
    void testDeleteByDestination() {
        String destination = "Rome";
        Planner planner = new Planner();
        planner.setPlannedDestination(destination);

        when(plannerRepository.findByPlannedDestination(destination)).thenReturn(Optional.of(planner));
        doNothing().when(plannerRepository).delete(planner);

        Optional<Boolean> result = plannerService.deleteByDestination(destination);

        assertTrue(result.isPresent());
        assertTrue(result.get());
        verify(plannerRepository, times(1)).findByPlannedDestination(destination);
        verify(plannerRepository, times(1)).delete(planner);
    }

    @Test
    void testDeleteByDestinationNotFound() {
        String destination = "Rome";

        when(plannerRepository.findByPlannedDestination(destination)).thenReturn(Optional.empty());

        Optional<Boolean> result = plannerService.deleteByDestination(destination);

        assertFalse(result.isPresent());
        verify(plannerRepository, times(1)).findByPlannedDestination(destination);
        verify(plannerRepository, times(0)).delete(any(Planner.class));
    }

    @Test
    void testUpdateByDestination() {
        String destination = "London";
        Planner existingPlanner = new Planner();
        Planner updatedPlanner = new Planner();
        updatedPlanner.setPlannedDestination(destination);
        updatedPlanner.setPlannedBudget(1000);
        updatedPlanner.setPlannedStops("Stop1, Stop2");
        updatedPlanner.setPlannedStartDate(convertToDate(LocalDate.now().plusDays(1)));
        updatedPlanner.setPlannedEndDate(convertToDate(LocalDate.now().plusDays(2)));

        when(plannerRepository.findByPlannedDestination(destination)).thenReturn(Optional.of(existingPlanner));
        when(plannerRepository.save(any(Planner.class))).thenReturn(updatedPlanner);

        Optional<Planner> result = plannerService.updateByDestination(destination, updatedPlanner);

        assertTrue(result.isPresent());
        assertEquals(1000, result.get().getPlannedBudget());
        assertEquals("Stop1, Stop2", result.get().getPlannedStops());
        assertEquals(updatedPlanner.getPlannedStartDate(), result.get().getPlannedStartDate());
        assertEquals(updatedPlanner.getPlannedEndDate(), result.get().getPlannedEndDate());
        verify(plannerRepository, times(1)).findByPlannedDestination(destination);
        verify(plannerRepository, times(1)).save(any(Planner.class));
    }

    @Test
    void testUpdateByDestinationNotFound() {
        String destination = "London";
        Planner updatedPlanner = new Planner();
        updatedPlanner.setPlannedDestination(destination);

        when(plannerRepository.findByPlannedDestination(destination)).thenReturn(Optional.empty());

        Optional<Planner> result = plannerService.updateByDestination(destination, updatedPlanner);

        assertFalse(result.isPresent());
        verify(plannerRepository, times(1)).findByPlannedDestination(destination);
        verify(plannerRepository, times(0)).save(any(Planner.class));
    }

    @Test
    void testCreatePlanner() {
        Planner newPlanner = new Planner();
        newPlanner.setPlannedDestination("New York");

        when(plannerRepository.save(any(Planner.class))).thenReturn(newPlanner);

        Planner result = plannerService.createPlanner(newPlanner);

        assertNotNull(result);
        assertEquals("New York", result.getPlannedDestination());
        verify(plannerRepository, times(1)).save(any(Planner.class));
    }

    @Test
    void testFindPlansByDateRange() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);

        when(plannerRepository.findByPlannedStartDateBetween(startDate, endDate))
                .thenReturn(Arrays.asList(new Planner(), new Planner()));

        List<Planner> plans = plannerService.findPlansByDateRange(startDate, endDate);

        assertNotNull(plans);
        assertEquals(2, plans.size());
        verify(plannerRepository, times(1)).findByPlannedStartDateBetween(startDate, endDate);
    }

    @Test
    void testFindPlansByDateRangeEmpty() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);

        when(plannerRepository.findByPlannedStartDateBetween(startDate, endDate))
                .thenReturn(Collections.emptyList());

        List<Planner> plans = plannerService.findPlansByDateRange(startDate, endDate);

        assertNotNull(plans);
        assertTrue(plans.isEmpty());
        verify(plannerRepository, times(1)).findByPlannedStartDateBetween(startDate, endDate);
    }

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
