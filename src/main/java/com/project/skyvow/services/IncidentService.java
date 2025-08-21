package com.project.skyvow.services;

import com.project.skyvow.models.Incident;
import java.util.List;

public interface IncidentService {
    Incident createIncidentTicket(Incident incident);
    List<Incident> getAllIncidentTickets();
}
