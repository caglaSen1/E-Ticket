package com.ftbootcamp.eticketservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftbootcamp.eticketservice.dto.request.TicketBuyRequest;
import com.ftbootcamp.eticketservice.service.TicketService;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TicketController.class)
@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    /*
    @Test
    void buyTicket() throws Exception {
        //given


        //when
        mockMvc.perform(post("/api/v1/tickets/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(prepareTicketBuyRequest()))

        //then
               // .andExpect(status().isCreated());
    }*/


    @Test
    void buyTickset() throws Exception {

        //given
        TicketBuyRequest ticketBuyRequest = prepareTicketBuyRequest();
        Mockito.doNothing().when(ticketService).takePaymentOfTicket(prepareTicketBuyRequest());

        //when
        mockMvc.perform(post("/api/v1/tickets/buy"));

        //then -> assertion
        verify(ticketService).takePaymentOfTicket(prepareTicketBuyRequest());
    }

    @Test
    void getTicketById() throws Exception {
        //given
        Mockito.when(ticketService.getTicketById(1L)).thenReturn(null);

        //when
        mockMvc.perform(post("/api/v1/tickets/1"));

        //then -> assertion
        verify(ticketService).getTicketById(1L);
    }

    private TicketBuyRequest prepareTicketBuyRequest() {
        TicketBuyRequest ticketBuyRequest = new TicketBuyRequest();
        ticketBuyRequest.setTicketId(1L);
        ticketBuyRequest.setUserId(1L);
        return ticketBuyRequest;
    }


}