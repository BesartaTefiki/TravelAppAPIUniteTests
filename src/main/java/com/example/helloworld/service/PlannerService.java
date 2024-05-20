package com.example.helloworld.service;

import com.example.helloworld.pojo.Planner;
import com.example.helloworld.repository.PlannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PlannerService {

    private final PlannerRepository plannerRepository;

    @Autowired
    public PlannerService(PlannerRepository plannerRepository) {
        this.plannerRepository = plannerRepository;
    }

    public List<Planner> findAllPlans (){
        return plannerRepository.findAll();
    }

    public Optional<Planner> findByDestination(String plannedDestination) {
        return plannerRepository.findByPlannedDestination(plannedDestination);
    }

    public Optional deleteByDestination(String plannedDestination) {
        Optional<Planner> existingPlannerOpt=plannerRepository.findByPlannedDestination(plannedDestination);

        if(existingPlannerOpt.isPresent()) {
            Planner existingPlanner=existingPlannerOpt.get();
            plannerRepository.delete(existingPlanner);
            return Optional.of(true);
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<Planner> updateByDestination(String plannedDestination, Planner updatedPlanner) {
        Optional<Planner> existingPlannerOpt=plannerRepository.findByPlannedDestination(plannedDestination);

        if (existingPlannerOpt.isPresent()){
            Planner existingPlanner=existingPlannerOpt.get();

            existingPlanner.setPlannedBudget(updatedPlanner.getPlannedBudget());
            existingPlanner.setPlannedStops(updatedPlanner.getPlannedStops());
            existingPlanner.setPlannedStartDate(updatedPlanner.getPlannedStartDate());
            existingPlanner.setPlannedEndDate(updatedPlanner.getPlannedEndDate());

            return Optional.of(plannerRepository.save(existingPlanner));
        }

        else {
            return Optional.empty();
        }

    }

    public Planner createPlanner(Planner planner) {
        return plannerRepository.save(planner);
    }
    public List<Planner> findPlansByDateRange(LocalDate startDate, LocalDate endDate) {
        return plannerRepository.findByPlannedStartDateBetween(startDate, endDate);
    }



}
