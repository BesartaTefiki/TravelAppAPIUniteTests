package com.example.helloworld.repository;

import com.example.helloworld.pojo.Booking;
import com.example.helloworld.pojo.BoughtTicket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository

public interface BoughtTicketRepository extends MongoRepository<BoughtTicket,String> {


}
