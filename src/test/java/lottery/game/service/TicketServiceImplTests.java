package lottery.game.service;

import com.lottery.game.LotteryTicketApplication;
import com.lottery.game.exception.TicketNotAmendableException;
import com.lottery.game.exception.TicketNotFoundException;
import com.lottery.game.model.Number;
import com.lottery.game.model.Ticket;
import com.lottery.game.repo.TicketRepository;
import com.lottery.game.service.TicketService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ContextConfiguration(classes = TicketService.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { LotteryTicketApplication.class })
public class TicketServiceImplTests {

  @MockBean
  private TicketRepository ticketRepository;

  @Autowired
  private TicketService ticketService;

  private Long size = 1L;

  private Ticket ticket;

  private List<Number> numberList;

  private Number number;

  private Long ticketId=1L;

  @BeforeEach
  public void setup() {
    number = new Number();
    number.setId(1L);
    number.setResult(10);
    number.setNumber(List.of(0,1,0));

    numberList = new ArrayList<>();
    numberList.add(number);

    ticket = Ticket.builder().numbers(numberList).checked(false).id(ticketId).build();
  }

  @Test
  public void createTicket_success() {
    Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

    Ticket ticket = ticketService.createTicket(size);
    Assert.assertSame(ticket.getId(), 1L);
  }

  @Test
  public void getTicket_success() {
    Mockito.when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

    Ticket ticket = ticketService.getTicket(ticketId);
    Assert.assertSame(ticket.getId(), 1L);
  }

  @Test
  public void getTicket_fail() {
    Mockito.when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

    assertThrows(TicketNotFoundException.class, () -> {
      ticketService.getTicket(ticketId);
    });
  }

  @Test
  public void getTickets_success() {
    Mockito.when(ticketRepository.findAll()).thenReturn(List.of(ticket));

    List<Ticket> ticketList = ticketService.getTickets();
    Assert.assertSame(ticketList.size(), 1);
  }

  @Test
  public void getTicketStatus_success() {
    Mockito.when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

    Boolean status = ticketService.getTicketStatus(ticketId);
    Assert.assertFalse(status);
  }

  @Test
  public void amendTicket_fail() {
    ticket = Ticket.builder().numbers(numberList).checked(true).id(ticketId).build();
    Mockito.when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

    assertThrows(TicketNotAmendableException.class, () -> {
      ticketService.amendTicket(ticketId, size);
    });
  }

  @Test
  public void amendTicket_success() {
    Mockito.when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

    ticket = Ticket.builder().numbers(numberList).checked(true).id(ticketId).build();
    Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

    Ticket ticket = ticketService.amendTicket(ticketId, size);
    Assert.assertTrue(ticket.isChecked());
  }


}
