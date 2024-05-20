package com.example.helloworld.repository;

import com.example.helloworld.pojo.Planner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlannerRepository extends MongoRepository <Planner,String> {
    Optional<Planner> findByPlannedDestination(String plannedDestination);
    List<Planner> findByPlannedStartDateBetween(LocalDate startDate, LocalDate endDate);

}
