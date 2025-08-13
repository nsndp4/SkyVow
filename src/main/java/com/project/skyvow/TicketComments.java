package com.project.skyvow;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_comments")
public class TicketComments {

    // Surrogate key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Key")
    private Long key;

    // FK -> Ticket.tickets_ids
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "Foreign_Key",                   // column in ticket_comments
            referencedColumnName = "tickets_ids",   // PK column in ticket
            nullable = false
    )
    private Ticket ticket;

    @Column(name = "Comment", nullable = false, columnDefinition = "TEXT")
    private String comment;

    @Column(name = "Comment_Creation_Date", nullable = false)
    private LocalDateTime commentCreationDate;

    public TicketComments() {}

    @PrePersist
    public void onCreate() {
        if (commentCreationDate == null) {
            commentCreationDate = LocalDateTime.now();
        }
    }

    // --- getters & setters ---

    public Long getKey() { return key; }
    public void setKey(Long key) { this.key = key; }

    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCommentCreationDate() { return commentCreationDate; }
    public void setCommentCreationDate(LocalDateTime commentCreationDate) { this.commentCreationDate = commentCreationDate; }
}
