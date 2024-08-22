package com.ftbootcamp.eticketservice.controller;

import com.ftbootcamp.eticketservice.dto.request.TicketBuyRequest;
import com.ftbootcamp.eticketservice.dto.request.TicketMultipleBuyRequest;
import com.ftbootcamp.eticketservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketservice.dto.response.TicketGeneralStatisticsResponse;
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
    @Operation(summary = "Buy ticket for logged in user", description = "Buy ticket for logged in user")
    public GenericResponse<Void> buyTicket(@RequestBody TicketBuyRequest request) {
        ticketService.takePaymentOfTicket(request);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }

    @PostMapping("/buy-multiple")
    @Operation(summary = "Buy ticket", description = "Buy ticket with given ticket information")
    public GenericResponse<Void> buyMultipleTicket(@RequestBody TicketMultipleBuyRequest request) {
        ticketService.takePaymentOfMultipleTicket(request);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by id", description = "Get ticket by id")
    public GenericResponse<TicketResponse> getTicketById(@PathVariable Long id) {
        return GenericResponse.success(ticketService.getTicketById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all tickets", description = "Get all tickets")
    public GenericResponse<List<TicketResponse>> getAllTickets() {
        return GenericResponse.success(ticketService.getAllTickets(), HttpStatus.OK);
    }

    @GetMapping("/all-available")
    public GenericResponse<List<TicketResponse>> getAllAvailableTickets() {
        return GenericResponse.success(ticketService.getAllAvailableTickets(), HttpStatus.OK);
    }

    @GetMapping("/all-expired")
    public GenericResponse<List<TicketResponse>> getAllExpiredTickets() {
        return GenericResponse.success(ticketService.getAllExpiredTickets(), HttpStatus.OK);
    }

    @GetMapping("/all-sold")
    public GenericResponse<List<TicketResponse>> getAllSoldTickets() {
        return GenericResponse.success(ticketService.getAllSoldTickets(), HttpStatus.OK);
    }

    @GetMapping("/trips/{tripId}/all")
    public GenericResponse<List<TicketResponse>> getAllTicketsByTripId(@PathVariable Long tripId) {
        return GenericResponse.success(ticketService.getAllTicketsByTripId(tripId), HttpStatus.OK);
    }

    @GetMapping("/trips/{tripId}/all-available")
    public GenericResponse<List<TicketResponse>> getAvailableTicketsByTripId(@PathVariable Long tripId) {
        return GenericResponse.success(ticketService.getAllAvailableTicketsByTripId(tripId), HttpStatus.OK);
    }

    @GetMapping("/statistics")
    @Operation(summary = "Get general statistics", description = "Get general statistics")
    public GenericResponse<TicketGeneralStatisticsResponse> getGeneralTicketStatistics() {
        return GenericResponse.success(ticketService.getGeneralTicketStatistics(), HttpStatus.OK);
    }

}
