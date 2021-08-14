package lottery.game.controller;

import com.lottery.game.LotteryTicketApplication;
import com.lottery.game.controller.TicketController;
import com.lottery.game.model.Number;
import com.lottery.game.model.Ticket;
import com.lottery.game.service.TicketService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = TicketController.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { LotteryTicketApplication.class })
public class TicketControllerTests {

  @MockBean
  private TicketService ticketService;

  @Autowired
  private TicketController ticketController;

  private Long size=1L;

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

    ticket = new Ticket();
    ticket.setId(ticketId);
    ticket.setChecked(false);
    ticket.setNumbers(numberList);
  }

  @Test
  public void createTicket() {
    Mockito.when(ticketService.createTicket(size)).thenReturn(ticket);

    ResponseEntity<Ticket> ticket = ticketController.createTicket(size);

    Assert.assertEquals(ticket.getBody().getNumbers().size(), 1);
  }

  @Test
  public void getTicket() {
    Mockito.when(ticketService.getTicket(ticketId)).thenReturn(ticket);

    ResponseEntity<Ticket> ticket = ticketController.getTicket(ticketId);

    Assert.assertSame(ticket.getBody().getId(), ticketId);
  }

  @Test
  public void getTickets() {
    Mockito.when(ticketService.getTickets()).thenReturn(List.of(ticket));

    ResponseEntity<List<Ticket>> ticket = ticketController.getTickets();

    Assert.assertSame(ticket.getBody().size(), 1);
  }

  @Test
  public void getTicketStatus() {
    Mockito.when(ticketService.getTicketStatus(ticketId)).thenReturn(false);

    ResponseEntity<Boolean> status = ticketController.getTicketStatus(ticketId);

    Assert.assertFalse(status.getBody());
  }

  @Test
  public void amendTicket() {
    Mockito.when(ticketService.amendTicket(ticketId, size)).thenReturn(ticket);

    ResponseEntity<Ticket> ticket = ticketController.amendTicket(ticketId, size);

    Assert.assertSame(ticket.getBody().getId(), ticketId);
  }


}
