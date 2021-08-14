package com.lottery.game.service;

import com.lottery.game.model.Ticket;

import java.util.List;

public interface TicketService {

  Ticket createTicket(Long size);

  Ticket getTicket(Long id);

  List<Ticket> getTickets();

  Boolean getTicketStatus(Long id);

  Ticket amendTicket(Long id, Long size);
}
