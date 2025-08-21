package com.project.skyvow.serviceImpl;

import com.project.skyvow.models.Incident;
import com.project.skyvow.dao.IncidentRepository;
import com.project.skyvow.services.IncidentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final Random random = new Random();

    public IncidentServiceImpl(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Override
    public Incident createIncidentTicket(Incident incident) {

        // Generate unique ticket number in the same method
        if (incident.getTicketNumber() == null || incident.getTicketNumber().isEmpty()) {
            String ticketNumber;
            do {
                int rand = 10000000 + random.nextInt(90000000); // 8-digit random number
                ticketNumber = "INC-" + rand;
            } while (incidentRepository.existsByTicketNumber(ticketNumber));
            incident.setTicketNumber(ticketNumber);
        }

        // Set default status if not present
        if (incident.getStatus() == null || incident.getStatus().isEmpty()) {
            incident.setStatus("New");
        }

        if (incident.getCreatedBy() == null || incident.getCreatedBy().isBlank()) {
            incident.setCreatedBy("SYSTEM"); // or pull from SecurityContext
        }
        if (incident.getAssignedGroup() == null || incident.getAssignedGroup().isBlank()) {
            incident.setAssignedGroup("Unassigned");
        }
        if (incident.getAssignedTo() == null) {
            incident.setAssignedTo(""); // NotBlank forbids null; use empty or enforce a real value
        }
        if (incident.getConfigurationItem() == null || incident.getConfigurationItem().isBlank()) {
            throw new IllegalArgumentException("configurationItem is required");
        }
        if (incident.getShortDescription() == null || incident.getShortDescription().isBlank()) {
            throw new IllegalArgumentException("shortDescription is required");
        }
        if (incident.getOnBehalfOf() == null || incident.getOnBehalfOf().isBlank()) {
            throw new IllegalArgumentException("onBehalfOf is required");
        }
        if (incident.getCategory() == null || incident.getCategory().isBlank()) {
            throw new IllegalArgumentException("category is required");
        }
        if (incident.getSubcategory() == null || incident.getSubcategory().isBlank()) {
            throw new IllegalArgumentException("subcategory is required");
        }


        return incidentRepository.save(incident);
    }

    @Override
    public List<Incident> getAllIncidentTickets() {
        return incidentRepository.findAll();
    }
}
