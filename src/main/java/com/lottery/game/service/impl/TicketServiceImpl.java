package com.lottery.game.service.impl;

import com.lottery.game.exception.TicketNotAmendableException;
import com.lottery.game.exception.TicketNotFoundException;
import com.lottery.game.model.Number;
import com.lottery.game.model.Ticket;
import com.lottery.game.repo.TicketRepository;
import com.lottery.game.service.TicketService;
import com.lottery.game.utils.CommonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public Ticket createTicket(Long size){
		List<Number> numbers = new ArrayList();
		for(int i=0;i<size;i++){
			numbers.add(CommonUtility.generateLine());
		}

		return ticketRepository.save(Ticket.builder().numbers(numbers).build());
	}

	@Override
	public Ticket getTicket(Long id){
		return ticketRepository.findById(id).orElseThrow(() -> {
			throw new TicketNotFoundException("Ticket not found.");
		});
	}

	@Override
	public List<Ticket> getTickets() {
		List<Ticket> ticketList = ticketRepository.findAll();
		ticketList.forEach(n -> n.getNumbers().sort(Comparator.comparing(Number::getResult).reversed()));
		return ticketList;
	}

	@Override
	public Boolean getTicketStatus(Long id) {
		return this.getTicket(id).isChecked();
	}

	@Override
	public Ticket amendTicket(Long id, Long size) {
		Ticket ticket = this.getTicket(id);
		if(ticket.isChecked()){
			throw new TicketNotAmendableException("Ticket is not amendable");
		}

		ticket = this.getUpdateObject(size, ticket);

		return ticketRepository.save(ticket);
	}


	private Ticket getUpdateObject(Long size, Ticket ticket) {
		for(int i=0;i<size;i++){
			ticket.getNumbers().add(CommonUtility.generateLine());
		}
		ticket.setNumbers(ticket.getNumbers());
		ticket.setChecked(true);

		return ticket;
	}

}
