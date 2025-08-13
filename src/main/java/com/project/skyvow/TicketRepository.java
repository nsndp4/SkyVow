package com.project.skyvow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

    // return only the primary keys (ticketsIds) quickly
    @Query("select t.ticketsIds from Ticket t")
    List<String> findAllTicketIds();
}
