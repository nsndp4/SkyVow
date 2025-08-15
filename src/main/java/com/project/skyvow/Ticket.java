package com.project.skyvow;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket")
public class Ticket {

    // Primary key (e.g., INC12345)
    @Id
    @Column(name = "tickets_ids", nullable = false, unique = true)
    private String ticketsIds;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "reported_by", nullable = false)
    private String reportedBy;

    @Column(name = "short_description", nullable = false)
    private String shortDescription;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "priority", nullable = false)
    private String priority;

    @Column(name = "severity", nullable = false)
    private String severity;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "assigned_to", nullable = false)
    private String assignedTo;

    @Column(name = "configuration_item", nullable = false)
    private String configurationItem;

    // ⬇️ IMPORTANT: comments are now a OneToMany of entities (not List<String>)
    @OneToMany(
            mappedBy = "ticket",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TicketComments> comments = new ArrayList<>();

    public Ticket() {}

    @PrePersist
    public void prePersist() {
        if (this.createdDate == null) this.createdDate = LocalDateTime.now();
    }

    // --- getters & setters ---

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

    public String getConfigurationItem() { return configurationItem; }
    public void setConfigurationItem(String configurationItem) { this.configurationItem = configurationItem; }

    public List<TicketComments> getComments() { return comments; }
    public void setComments(List<TicketComments> comments) { this.comments = comments; }

    // helpers (optional)
    public void addComment(TicketComments c) {
        comments.add(c);
        c.setTicket(this);
    }
    public void removeComment(TicketComments c) {
        comments.remove(c);
        c.setTicket(null);
    }
}