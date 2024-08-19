package com.ftbootcamp.eticketservice.controller;

import com.ftbootcamp.eticketservice.dto.request.TicketBuyRequest;
import com.ftbootcamp.eticketservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketservice.dto.response.TicketResponse;
import com.ftbootcamp.eticketservice.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@Tag(name = "Ticket API V1", description = "Ticket API for ticket operations")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/buy")
    @Operation(summary = "Buy ticket", description = "Buy ticket with given ticket information")
    public GenericResponse<TicketResponse> buyTicket(@RequestBody TicketBuyRequest request) {
        return GenericResponse.success(ticketService.buyTicket(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by id", description = "Get ticket by id")
    public GenericResponse<TicketResponse> getTicketById(@PathVariable Long id) {
        return GenericResponse.success(ticketService.getTicketById(id), HttpStatus.OK);
    }

    @GetMapping("/trips/{tripId}/all")
    public GenericResponse<List<TicketResponse>> getAllTicketsByTripId(@PathVariable Long tripId) {
        return GenericResponse.success(ticketService.getAllTickets(tripId), HttpStatus.OK);
    }

    @GetMapping("/trips/{tripId}/not-sold")
    public GenericResponse<List<TicketResponse>> getNotSoldTicketsByTripId(@PathVariable Long tripId) {
        return GenericResponse.success(ticketService.getNotSoldTickets(tripId), HttpStatus.OK);
    }

}
