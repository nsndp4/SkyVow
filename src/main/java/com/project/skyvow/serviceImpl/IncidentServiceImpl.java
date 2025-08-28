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

        if (incident.getId() == null) {
            // ---------- CREATE ----------
            // Generate unique ticket number
            String ticketNumber;
            do {
                int rand = 10000000 + random.nextInt(90000000);
                ticketNumber = "INC" + rand;
            } while (incidentRepository.existsByTicketNumber(ticketNumber));
            incident.setTicketNumber(ticketNumber);

            // Always start as New on create
            incident.setStatus("New");

            // Defaults / fallbacks
            if (incident.getCreatedBy() == null || incident.getCreatedBy().isBlank()) {
                incident.setCreatedBy("SYSTEM");
            }
            if (incident.getAssignedGroup() == null || incident.getAssignedGroup().isBlank()) {
                incident.setAssignedGroup("Unassigned");
            }
            if (incident.getAssignedTo() == null) {
                incident.setAssignedTo(""); // never null; can be blank on create
            }

            // Required business fields
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
        } else {
            // ---------- UPDATE ----------
            Incident existing = incidentRepository.findById(incident.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Incident not found"));

            String from = existing.getStatus();
            String to   = incident.getStatus();

            if (to == null || to.isBlank()) {
                to = from; // if client didn’t send a new status, treat as no status change
                incident.setStatus(to);
            }

            // No changes allowed after Canceled
            if ("Canceled".equals(from) && !"Canceled".equals(to)) {
                throw new IllegalStateException("Canceled tickets cannot change status");
            }

            switch (from) {
                case "New":
                    if (!"InProgress".equals(to) && !"New".equals(to)) {
                        throw new IllegalStateException("From New you can only stay New or go to InProgress");
                    }
                    break;

                case "InProgress":
                    if (!"OnHold".equals(to) && !"Resolved".equals(to) && !"Canceled".equals(to) && !"InProgress".equals(to)) {
                        throw new IllegalStateException("From InProgress you can only go to OnHold, Resolved, Canceled, or stay InProgress");
                    }
                    break;

                case "OnHold":
                    if (!"InProgress".equals(to) && !"New".equals(to) && !"OnHold".equals(to)) {
                        throw new IllegalStateException("From OnHold you can only go to InProgress, New, or stay OnHold");
                    }
                    break;

                case "Resolved":
                    if (!"Closed".equals(to)) {
                        throw new IllegalStateException("From Resolved you can only go to Closed or stay Resolved");
                    }
                    break;

                case "Closed":
                    if (!"InProgress".equals(to) && !"Closed".equals(to)) {
                        throw new IllegalStateException("From Closed you can only Reopen to InProgress or stay Closed");
                    }
                    break;

                case "Canceled":
                    // handled above (frozen)
                    break;

                default:
                    throw new IllegalStateException("Invalid current status: " + from);
            }

            // Preserve immutable fields
            incident.setTicketNumber(existing.getTicketNumber());
            incident.setCreatedDate(existing.getCreatedDate());
            incident.setCreatedBy(existing.getCreatedBy());

            // Ensure non-null assignedTo for DB constraint
            if (incident.getAssignedTo() == null) {
                incident.setAssignedTo("");
            }

            return incidentRepository.save(incident);
        }
    }


    @Override
    public List<Incident> getAllIncidentTickets() {
        return incidentRepository.findAll();
    }

}
