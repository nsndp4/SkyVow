package com.project.skyvow.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_number", nullable = false, unique = true)
    private String ticketNumber;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @NotBlank
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @PrePersist
    protected void onCreate() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }
    }

    @NotBlank
    @Column(name = "assigned_to", nullable = false)
    private String assignedTo;

    @NotBlank
    @Column(name = "assigned_group", nullable = false)
    private String assignedGroup;

    @NotBlank
    @Column(name = "configuration_item", nullable = false)
    private String configurationItem;

    @NotBlank
    @Size(max = 500)
    @Column(name = "short_description", nullable = false, length = 500)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;
}
