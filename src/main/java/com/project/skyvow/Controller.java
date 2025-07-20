package com.project.skyvow;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static java.time.Clock.tick;

@RestController
public class Controller {

    List<Ticket> ticketList = new ArrayList<>();
    Ticket ticket = new Ticket();


   @PostMapping("/api/v1/tickets/different")
   public List<Ticket> createDifferent(@RequestBody List<Ticket> createTickets){
       for(Ticket createticket:createTickets){
           Ticket ticket=new Ticket();
           String prefix="";
           if("incident".equalsIgnoreCase(createticket.getIds())){
               prefix="INC";
           }
           else if("problem".equalsIgnoreCase(createticket.getIds())){
               prefix="PRB";
           }
           else if("change".equalsIgnoreCase(createticket.getIds())){
               prefix="CHG";
           }
           else{
               prefix="GEN";
           }
            int randomNumber = new Random().nextInt(90000)+10000;
           ticket.setIds(prefix+randomNumber);

           ticket.setName(createticket.getName());
           ticket.setStatus(createticket.getStatus());
           ticket.setAssignee(createticket.getAssignee());
           ticket.setReporter(createticket.getReporter());
           ticket.setDesc(createticket.getDesc());
           ticket.setPriority(createticket.getPriority());
           if(createticket.getComment()!=null){
               ticket.setComment(new ArrayList<>(createticket.getComment()));
           }
           else{
               ticket.setComment(new ArrayList<>());
           }
           ticketList.add(ticket);
       }
        return ticketList;
   }

   /*
   @PostMapping("/api/v1/tickets/statusChange")
   public static String changeStatus(@PathVariable String ids,@RequestBody TicketStatus statusChange){
           String currentStatus="";
           String futureState="";
           for(TicketStatus status:TicketStatus.values()){
               if(ts.getIds().equals(ids)){
                   currentStatus=ts.getStatus();
                   //futureState=ts.setStatus(statusChange.getStatus());
                   futureState=statusChange;
                   //if(currentStatus.val)
                   swich(TicketStatus)                   }
                   TicketStatus.OPEN.getCode();
               }
           }
*/


    @PostMapping("/api/v1/tickets")
    public String createMultipleTicket(@RequestBody List<Ticket> createTickets) {
        for (Ticket createticket : createTickets) {
            Ticket ticket = new Ticket();
        ticket.setId(createticket.getId());
        ticket.setName(createticket.getName());
        ticket.setStatus(createticket.getStatus());
        ticket.setAssignee(createticket.getAssignee());
        ticket.setReporter(createticket.getReporter());
        ticket.setDesc(createticket.getDesc());
        ticket.setPriority(createticket.getPriority());

        if (createticket.getComment() != null) {
            ticket.setComment(new ArrayList<>(createticket.getComment()));
        } else {
            ticket.setComment(new ArrayList<>());
        }

        ticketList.add(ticket);
    }
        return "Tickets created successfully"+createTickets.size();
    }

    @GetMapping("/api/v1/ticket/{id}")
    public String getTicketById(@PathVariable int id) {
        for (Ticket ts : ticketList) {
            if (ts.getId() == id) {
                System.out.println("The ticket:" + ts.getName());
                return ts.getName() + " " + ts.getDesc() + " " + ts.getPriority();
            }
        }
        return "Not found";
    }

    @GetMapping("/api/v1/tickets/{id}")
    public Ticket getById(@PathVariable int id) {
        for (Ticket ts : ticketList) {
            if (ts.getId() == id) {
                System.out.println("The ticket:" + ts.getName());
                return ts;
            }
        }
        return null;
    }


    @GetMapping("/api/v1/tickets")
    public List<Ticket> getFilteredAndPagedTickets(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String reporter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Ticket> filtered = new ArrayList<>();

        for (Ticket ts : ticketList) {
            boolean matches = true;

            if (status != null && !ts.getStatus().equalsIgnoreCase(status)) {
                matches = false;
            }
            if (assignee != null && !ts.getAssignee().equalsIgnoreCase(assignee)) {
                matches = false;
            }
            if (reporter != null && !ts.getReporter().equalsIgnoreCase(reporter)) {
                matches = false;
            }

            if (matches) {
                filtered.add(ts);
            }
        }

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, filtered.size());
        if (fromIndex > filtered.size()) {
            return new ArrayList<>(); // return empty if page is out of range
        }

        return filtered.subList(fromIndex, toIndex);
    }

    @PutMapping("api/v1/tickets/{id}")
    public String updates(@PathVariable int id, @RequestBody Ticket updatedticket) {
        for (Ticket ts : ticketList) {
            if (ts.getId() == id) {
                ts.setDesc(updatedticket.getDesc());
                ts.setPriority(updatedticket.getPriority());
                System.out.println("Updated ticket desc: " + ts.getDesc() + "priority: " + ts.getPriority());
                return "ticket updated successfully";
            }
        }
        return "ticket not found";
    }

    @PutMapping("api/v1/tickets/{id}/assign")
    public String assign(@PathVariable int id, @RequestBody Ticket assigner) {
        for (Ticket ts : ticketList) {
            if (ts.getId() == id) {
                ts.setAssignee(assigner.getAssignee());
                System.out.println("The ticket ID: " + ts.getId() + "is assigned to the user: " + ts.getAssignee());
                return "Ticket assigned successfully";
            }
        }
        return "not found";
    }

    @PutMapping("/api/v1/tickets/{id}/status")
    public String statusUpdate(@PathVariable int id, @RequestBody Ticket upStat) {
        for (Ticket ts : ticketList) {
            if (ts.getId() == id) {
                ts.setStatus(upStat.getStatus());
                System.out.println("Updated status: " + ts.getStatus());
                return "Status updated successfully";
            }
        }
        return "not found";
    }

    @PostMapping("/api/v1/tickets/{id}/comments")
    public List<String> newComm(@PathVariable int id, @RequestBody String newComment) {
        for (Ticket ts : ticketList) {
            if (ts.getId() == id) {
                ts.getComment().add(newComment);
                System.out.println("Comment is updated" + ts.getComment());
                return ts.getComment();
            }
        }
        return new ArrayList<>();
    }


    @GetMapping("/api/v1/tickets/{id}/comments")
    public List<String> getAllComments(@PathVariable int id) {
        for (Ticket ts : ticketList) {
            if (ts.getId() == id) {
                if (ts.getComment() != null) {
                    return ts.getComment();
                } else {
                    return new ArrayList<>();
                }
            }
        }
        return new ArrayList<>();
    }


    @DeleteMapping("/api/v1/tickets/{id}")
    public String deleteTicket(@PathVariable int id) {
       Iterator<Ticket> iterator=ticketList.iterator();
       while(iterator.hasNext()){
           Ticket ts=iterator.next();
           if(ts.getId()==id){
               iterator.remove();
               return "Ticket with ID: "+id+" is deleted";
           }
       }
       return "Ticket with ID: "+id+" is not found";
    }
}