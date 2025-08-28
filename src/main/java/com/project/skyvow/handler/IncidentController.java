package com.project.skyvow.handler;

import com.project.skyvow.models.Incident;
import com.project.skyvow.services.IncidentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;
    private static final Logger log = LoggerFactory.getLogger(IncidentController.class);

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    // POST - Create Incident Ticket
    @PostMapping("/createIncidentTicket")
    public ResponseEntity<Incident> createIncidentTicket(@Valid @RequestBody Incident incident) {
        log.info("Creating Incident Ticket: {}", incident);
        Incident createdIncident = incidentService.createIncidentTicket(incident);
        return ResponseEntity.ok(createdIncident);
    }

    // GET - Fetch All Incident Tickets
    @GetMapping("/getAllIncidentTickets")
    public ResponseEntity<List<Incident>> getAllIncidentTickets() {
        List<Incident> incidents = incidentService.getAllIncidentTickets();
        return ResponseEntity.ok(incidents);
    }
}
