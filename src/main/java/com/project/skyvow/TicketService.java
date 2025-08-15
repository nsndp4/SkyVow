package com.project.skyvow;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketCommentRepository ticketCommentRepository;

    public TicketService(TicketRepository ticketRepository,
                         TicketCommentRepository ticketCommentRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketCommentRepository = ticketCommentRepository;
    }

    public Ticket createNewTicket(Ticket ticket) {
        if (ticket == null) throw new IllegalArgumentException("Ticket body can't be null");

        // Generate unique primary key: INC + 5 digits
        String generatedId;
        do {
            int n = ThreadLocalRandom.current().nextInt(10000, 100000);
            generatedId = "INC" + n;
        } while (ticketRepository.existsById(generatedId));
        ticket.setTicketsIds(generatedId);

        // Defaults for required fields
        if (ticket.getStatus() == null || ticket.getStatus().isBlank()) ticket.setStatus("OPEN");
        if (ticket.getPriority() == null || ticket.getPriority().isBlank()) ticket.setPriority("MEDIUM");
        if (ticket.getSeverity() == null || ticket.getSeverity().isBlank()) ticket.setSeverity("SEV3");
        if (ticket.getShortDescription() == null || ticket.getShortDescription().isBlank()) ticket.setShortDescription("N/A");
        if (ticket.getDescription() == null || ticket.getDescription().isBlank()) ticket.setDescription(ticket.getShortDescription());
        if (ticket.getConfigurationItem() == null || ticket.getConfigurationItem().isBlank()) ticket.setConfigurationItem("UNKNOWN");
        if (ticket.getAssignedTo() == null || ticket.getAssignedTo().isBlank()) ticket.setAssignedTo("UNASSIGNED");
        if (ticket.getReportedBy() == null || ticket.getReportedBy().isBlank()) ticket.setReportedBy("SYSTEM");
        if (ticket.getCreatedDate() == null) ticket.setCreatedDate(LocalDateTime.now());

        // Link incoming comments (if any)
        if (ticket.getComments() == null) {
            ticket.setComments(new ArrayList<>());
        } else {
            for (TicketComments c : ticket.getComments()) {
                c.setTicket(ticket);
                if (c.getCommentCreationDate() == null) c.setCommentCreationDate(LocalDateTime.now());
            }
        }

        return ticketRepository.save(ticket);
    }

    // Return full rows shaped exactly for your frontend
    @Transactional(readOnly = true)
    public List<TicketView> getAllTicketsForView() {
        return ticketRepository.findAll().stream()
                .map(TicketService::toView)
                .toList();
    }

    private static TicketView toView(Ticket t) {
        List<String> commentTexts = t.getComments() == null
                ? List.of()
                : t.getComments().stream().map(TicketComments::getComment).toList();

        TicketView v = new TicketView();
        v.setTicketsIds(t.getTicketsIds());
        v.setCreatedDate(t.getCreatedDate());
        v.setReportedBy(t.getReportedBy());
        v.setShortDescription(t.getShortDescription());
        v.setDescription(t.getDescription());
        v.setPriority(t.getPriority());
        v.setSeverity(t.getSeverity());
        v.setStatus(t.getStatus());
        v.setAssignedTo(t.getAssignedTo());
        v.setComments(commentTexts);
        v.setConfigurationItem(t.getConfigurationItem());
        return v;
    }

    // DTO that matches your table columns and keeps comments as strings
    public static class TicketView {
        private String ticketsIds;
        private LocalDateTime createdDate;
        private String reportedBy;
        private String shortDescription;
        private String description;
        private String priority;
        private String severity;
        private String status;
        private String assignedTo;
        private List<String> comments;
        private String configurationItem;

        // getters/setters
        public String getTicketsIds() { return ticketsIds; }
        public void setTicketsIds(String ticketsIds) { this.ticketsIds = ticketsIds; }
        public LocalDateTime getCreatedDate() { return createdDate; }
        public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
        public String getReportedBy() { return reportedBy; }
        public void setReportedBy(String reportedBy) { this.reportedBy = reportedBy; }
        public String getShortDescription() { return shortDescription; }
        public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getAssignedTo() { return assignedTo; }
        public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
        public List<String> getComments() { return comments; }
        public void setComments(List<String> comments) { this.comments = comments; }
        public String getConfigurationItem() { return configurationItem; }
        public void setConfigurationItem(String configurationItem) { this.configurationItem = configurationItem; }
    }
}