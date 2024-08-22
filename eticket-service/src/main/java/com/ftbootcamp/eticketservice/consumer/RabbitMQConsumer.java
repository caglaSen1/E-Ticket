package com.ftbootcamp.eticketservice.consumer;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftbootcamp.eticketservice.client.payment.dto.request.PaymentGenericRequest;
import com.ftbootcamp.eticketservice.dto.request.TicketMultipleBuyRequest;
import com.ftbootcamp.eticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    private final TicketService ticketService;

    @RabbitListener(queuesToDeclare = @Queue("${rabbitmq.send.payment.queue}"))
    public void handleAfterPayment(PaymentGenericRequest<?> request) {

        if ("TicketMultipleBuyRequest".equals(request.getPaymentObjectType())) {
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert the paymentObject into TicketMultipleBuyRequest
            TicketMultipleBuyRequest ticketRequest = objectMapper.convertValue(
                    request.getPaymentObject(), TicketMultipleBuyRequest.class);

            // Now you can handle the request with the ticketService
            ticketService.createTicketsAfterPayment(ticketRequest);
        }
    }
}