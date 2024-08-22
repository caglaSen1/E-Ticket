package com.ftbootcamp.paymentservice.repository;

import com.ftbootcamp.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
