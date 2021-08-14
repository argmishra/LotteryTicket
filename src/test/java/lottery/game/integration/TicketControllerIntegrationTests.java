package lottery.game.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottery.game.LotteryTicketApplication;
import com.lottery.game.model.Ticket;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = LotteryTicketApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TicketControllerIntegrationTests {

  @Autowired
  private MockMvc mockMvc;

  ObjectMapper mapper = new ObjectMapper();

  @Test
  public void createTicket() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/ticket").param("size", "2").
        contentType(MediaType.APPLICATION_JSON)).
        andExpect(status().isCreated()).andReturn();
    Ticket ticket = mapper.readValue(mvcResult.getResponse().getContentAsString(), Ticket.class);

    Assert.assertSame(1L, ticket.getId());
  }

  @Test
  public void getTicket() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/ticket").param("size", "2").
        contentType(MediaType.APPLICATION_JSON)).
        andExpect(status().isCreated()).andReturn();

    Ticket ticket = mapper.readValue(mvcResult.getResponse().getContentAsString(), Ticket.class);
    Long id = ticket.getId();

    mvcResult = mockMvc.perform(get("/ticket/" + id).contentType(MediaType.APPLICATION_JSON)).
        andExpect(status().isOk()).andReturn();
    ticket = mapper.readValue(mvcResult.getResponse().getContentAsString(), Ticket.class);

    Assert.assertSame(1L, ticket.getId());
  }

  @Test
  public void getTickets() throws Exception {
    mockMvc.perform(post("/ticket").param("size", "2").
        contentType(MediaType.APPLICATION_JSON)).
        andExpect(status().isCreated()).andReturn();

    mockMvc.perform(post("/ticket").param("size", "2").
        contentType(MediaType.APPLICATION_JSON)).
        andExpect(status().isCreated()).andReturn();

    MvcResult mvcResult = mockMvc.perform(get("/ticket").
        contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

    List<Ticket> ticketList = mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

    Assert.assertEquals(2, ticketList.size());
  }

  @Test
  public void getTicketStatus() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/ticket").param("size", "2").
        contentType(MediaType.APPLICATION_JSON)).
        andExpect(status().isCreated()).andReturn();
    Ticket ticket = mapper.readValue(mvcResult.getResponse().getContentAsString(), Ticket.class);

    Long id = ticket.getId();

    mvcResult = mockMvc.perform(get("/ticket/" + id + "/status").contentType(MediaType.APPLICATION_JSON)).
        andExpect(status().isOk()).andReturn();
    Boolean result = mapper.readValue(mvcResult.getResponse().getContentAsString(), Boolean.class);

    Assert.assertEquals(result, false);
  }

  @Test
  public void amendTicket() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/ticket").param("size", "2").
        contentType(MediaType.APPLICATION_JSON)).
        andExpect(status().isCreated()).andReturn();

    Ticket ticket = mapper.readValue(mvcResult.getResponse().getContentAsString(), Ticket.class);
    Long id = ticket.getId();

    mvcResult = mockMvc.perform(put("/ticket/" + id).param("size", "2")
        .contentType(MediaType.APPLICATION_JSON)).
        andExpect(status().isOk()).andReturn();
    ticket = mapper.readValue(mvcResult.getResponse().getContentAsString(), Ticket.class);

    Assert.assertEquals(ticket.getNumbers().size(), 4);
    Assert.assertEquals(ticket.isChecked(), true);
  }

}
