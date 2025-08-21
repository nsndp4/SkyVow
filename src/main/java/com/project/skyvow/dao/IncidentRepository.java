package com.project.skyvow.dao;

import com.project.skyvow.models.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    boolean existsByTicketNumber(String ticketNumber);
}
