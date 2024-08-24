package com.ftbootcamp.eticketservice.utils;

import com.ftbootcamp.eticketservice.client.user.constants.RoleNameConstants;
import com.ftbootcamp.eticketservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketservice.dto.request.PassengerTicketRequest;
import com.ftbootcamp.eticketservice.entity.Ticket;

import java.util.List;

public class MessageUtils {

    public static String generateTicketInfoMessage(UserDetailsResponse user, Ticket ticket) {
        StringBuilder infoMessage = new StringBuilder();
        String instanceOf = user.getInstanceOf();

        infoMessage.append("************ Your ticket has been created successfully. ************\n");

        infoMessage.append("Buyer information:\n");

        infoMessage.append("Email: ").append(user.getEmail()).append("\n");
        if (user.getPhoneNumber() != null) {
            infoMessage.append("Phone number: ").append(user.getPhoneNumber()).append("\n");
        }
        if (instanceOf.equals(RoleNameConstants.CORPORATE_USER_ROLE_NAME)) {
            infoMessage.append("Company Name: ").append(user.getCompanyName()).append("\n");
        }
        if (instanceOf.equals(RoleNameConstants.INDIVIDUAL_USER_ROLE_NAME)) {
            infoMessage.append("Name: ").append(user.getFirstName()).append(" ")
                    .append(user.getLastName()).append("\n");
        }

        infoMessage.append("************************************\n");

        infoMessage.append("Your ticket information:\n");

        infoMessage.append(ticket.getTrip().getDepartureTime()).append(" - ")
                .append(ticket.getTrip().getArrivalTime()).append("\n");
        infoMessage.append(ticket.getTrip().getDepartureCity()).append(" -> ")
                .append(ticket.getTrip().getArrivalCity()).append("\n");
        infoMessage.append("Ticket No: ").append(ticket.getId()).append("\n");
        infoMessage.append("Seat No: ").append(ticket.getSeatNo()).append("\n");
        infoMessage.append("Price: ").append(ticket.getPrice()).append("\n");

        infoMessage.append("************************************\n");

        return infoMessage.toString();
    }


    public static String generateMultipleTicketInfoMessage(UserDetailsResponse buyer, List<Ticket> tickets,
                                                     List<PassengerTicketRequest> passengerTicketRequests) {

        StringBuilder infoMessage = new StringBuilder();
        String instanceOf = buyer.getInstanceOf();
        double totalPrice = 0;

        infoMessage.append("Your tickets have been created successfully.\n");
        infoMessage.append("Buyer information:\n");
        infoMessage.append("Email: ").append(buyer.getEmail()).append("\n");

        if (buyer.getPhoneNumber() != null) {
            infoMessage.append("Phone number: ").append(buyer.getPhoneNumber()).append("\n");
        }

        if(instanceOf.equals(RoleNameConstants.CORPORATE_USER_ROLE_NAME)){
            infoMessage.append("Company Name: ").append(buyer.getCompanyName()).append("\n");
        }

        if(instanceOf.equals(RoleNameConstants.INDIVIDUAL_USER_ROLE_NAME)){
            infoMessage.append("Name: ").append(buyer.getFirstName()).append(" ")
                    .append(buyer.getLastName()).append("\n");
        }

        infoMessage.append("Your tickets information:\n");

        for (int i = 0; i < passengerTicketRequests.size(); i++) {
            Ticket ticket = tickets.get(i);
            totalPrice += ticket.getPrice();

            infoMessage.append("************ Ticket ").append(i + 1).append(" ************\n");
            infoMessage.append(ticket.getTrip().getDepartureTime()).append(" - ")
                    .append(ticket.getTrip().getArrivalTime()).append("\n");
            infoMessage.append(ticket.getTrip().getDepartureCity()).append(" -> ")
                    .append(ticket.getTrip().getArrivalCity()).append("\n");
            infoMessage.append("Ticket No: ").append(ticket.getId()).append("\n");
            infoMessage.append("Seat No: ").append(ticket.getSeatNo()).append("\n");
            infoMessage.append("Price: ").append(ticket.getPrice()).append("\n");

            infoMessage.append("Passenger information:\n");
            infoMessage.append("Name: ").append(passengerTicketRequests.get(i).getPassengerFirstName()).append(" ")
                    .append(passengerTicketRequests.get(i).getPassengerLastName()).append("\n");
            infoMessage.append("Gender: ").append(passengerTicketRequests.get(i).getGender()).append("\n");

            infoMessage.append("************************************\n");
        }

        infoMessage.append("Total Price: ").append(totalPrice).append("\n");

        return infoMessage.toString();

    }
}
