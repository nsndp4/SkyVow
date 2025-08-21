package com.project.skyvow.handler;

import com.project.skyvow.models.Incident;
import com.project.skyvow.services.IncidentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    //or
/*    @Autowired
    private IncidentService incidentService;*/

    // POST - Create Incident Ticket
    @PostMapping("/createIncidentTicket")
    public ResponseEntity<Incident> createIncidentTicket(@Valid @RequestBody Incident incident) {
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
