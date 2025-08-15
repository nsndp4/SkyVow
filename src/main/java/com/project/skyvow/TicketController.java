package com.project.skyvow;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Create
    @PostMapping("/createNewTicket")
    public ResponseEntity<Ticket> createNewTicket(@RequestBody Ticket ticket) {
        Ticket created = ticketService.createNewTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Your frontend calls this and expects full rows, not just IDs
    @GetMapping("/displaysCertainNumberOfTicketsBasedOnPageNumber")
    public ResponseEntity<List<TicketService.TicketView>> getAllTicketsForView() {
        return ResponseEntity.ok(ticketService.getAllTicketsForView());
    }
}