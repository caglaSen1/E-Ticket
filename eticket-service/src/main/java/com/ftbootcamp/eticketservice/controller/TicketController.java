package com.ftbootcamp.eticketservice.controller;

import com.ftbootcamp.eticketservice.dto.request.TicketBuyRequest;
import com.ftbootcamp.eticketservice.dto.request.TicketMultipleBuyRequest;
import com.ftbootcamp.eticketservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketservice.dto.response.TicketGeneralStatisticsResponse;
import com.ftbootcamp.eticketservice.dto.response.TicketResponse;
import com.ftbootcamp.eticketservice.entity.enums.TicketCondition;
import com.ftbootcamp.eticketservice.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @Operation(summary = "Buy a ticket", description = "Buyer is the active user.")
    public GenericResponse<Void> buyTicket(@Valid @RequestBody TicketBuyRequest request,
                                           @RequestHeader("Authorization") String token) {
        ticketService.takePaymentOfTicket(request, token);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }

    @PostMapping("/buy-multiple")
    @Operation(summary = "Buy more than one ticket", description = "Buyer is the active user.")
    public GenericResponse<Void> buyMultipleTicket(@Valid @RequestBody TicketMultipleBuyRequest request,
                                                   @RequestHeader("Authorization") String token) {
        ticketService.takePaymentOfMultipleTickets(request, token);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }

    @GetMapping("/admin-panel/{id}")
    @Operation(summary = "Get ticket by id", description = "Get ticket by id. Only admin can get ticket by id.")
    public GenericResponse<TicketResponse> getTicketById(@PathVariable Long id) {
        return GenericResponse.success(ticketService.getTicketById(id), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/all")
    @Operation(summary = "Get all tickets", description = "Get all tickets. Only admin can get all tickets.")
    public GenericResponse<List<TicketResponse>> getAllTickets() {
        return GenericResponse.success(ticketService.getAllTickets(), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/buyer/all/{email}")
    @Operation(summary = "Get all tickets of buyer with email", description = "Only admin can get all tickets by email.")
    public GenericResponse<List<TicketResponse>> getAllTicketsOfBuyerByEmail(@PathVariable String email) {
        return GenericResponse.success(ticketService.getAllTicketsOfBuyerByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/buyer/profile/all/")
    @Operation(summary = "Get all tickets of buyer with email", description = "Only buyer can get all tickets.")
    public GenericResponse<List<TicketResponse>> getAllTicketsOfBuyer(@RequestHeader("Authorization") String token) {
        return GenericResponse.success(ticketService.getAllTicketsOfBuyer(token), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/all/by-condition")
    @Operation(summary = "Get all tickets by condition", description = "Only admin can get all tickets by condition.")
    public GenericResponse<List<TicketResponse>> getAllTicketsByCondition(@RequestParam TicketCondition condition) {
        return GenericResponse.success(ticketService.getAllByCondition(condition), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/trips/{tripId}/all")
    @Operation(summary = "Get all tickets by trip id", description = "Only admin can get all tickets by trip id.")
    public GenericResponse<List<TicketResponse>> getAllTicketsByTripId(@PathVariable Long tripId) {
        return GenericResponse.success(ticketService.getAllTicketsByTripId(tripId), HttpStatus.OK);
    }

    @GetMapping("/trips/{tripId}/all-available")
    @Operation(summary = "Get all available tickets by trip id", description = "Company and Indiviual users can get all " +
            "available tickets by trip id.")
    public GenericResponse<List<TicketResponse>> getAvailableTicketsByTripId(@PathVariable Long tripId) {
        return GenericResponse.success(ticketService.getAllAvailableTicketsByTripId(tripId), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/statistics")
    @Operation(summary = "Get general statistics", description = "Only admin can get general statistics.")
    public GenericResponse<TicketGeneralStatisticsResponse> getGeneralTicketStatistics() {
        return GenericResponse.success(ticketService.getGeneralTicketStatistics(), HttpStatus.OK);
    }
}
