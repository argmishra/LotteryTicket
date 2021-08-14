package com.lottery.game.controller;

import com.lottery.game.model.Ticket;
import com.lottery.game.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ticket")
@Slf4j
@Validated
public class TicketController {

  @Autowired
  private TicketService ticketService;

  @PostMapping
  public ResponseEntity<Ticket> createTicket(@RequestParam(name = "size", required = true) Long size) {
    log.info("Create ticket with {} size", size);
    return new ResponseEntity(ticketService.createTicket(size), HttpStatus.CREATED);
  }

  @GetMapping("{id}")
  public ResponseEntity<Ticket> getTicket(@PathVariable (required = true, name = "id")  Long id) {
    log.info("Get ticket by id {}", id);
    return new ResponseEntity(ticketService.getTicket(id), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Ticket>> getTickets() {
    log.info("Get all tickets");
    return new ResponseEntity(ticketService.getTickets(), HttpStatus.OK);
  }

  @GetMapping("{id}/status")
  public ResponseEntity<Boolean> getTicketStatus(@PathVariable (required = true, name = "id")  Long id) {
    log.info("Get ticket status by id {}", id);
    return new ResponseEntity(ticketService.getTicketStatus(id), HttpStatus.OK);
  }

  @PutMapping("{id}")
  public ResponseEntity<Ticket> amendTicket(@PathVariable (required = true, name = "id")  Long id,
                            @RequestParam(name = "size", required = true) Long size) {
    log.info("Amend ticket id {} with size {}", id, size);
    return new ResponseEntity(ticketService.amendTicket(id, size), HttpStatus.OK);
  }


}
