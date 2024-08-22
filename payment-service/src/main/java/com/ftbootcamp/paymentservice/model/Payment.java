package com.ftbootcamp.paymentservice.model;

import com.ftbootcamp.paymentservice.model.constant.PaymentEntityConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = PaymentEntityConstants.AMOUNT, nullable = false)
    private BigDecimal amount;

    @Column(name = PaymentEntityConstants.CREATED_DATE_TIME)
    private LocalDateTime createdDateTime;

    @Column(name = PaymentEntityConstants.PAYMENT_STATUS)
    private PaymentType paymentType;

    @Column(name = PaymentEntityConstants.USER_EMAIL, nullable = false)
    private String userEmail;

    public Payment(BigDecimal amount, String userEmail) {
        this.amount = amount;
        this.createdDateTime = LocalDateTime.now();
        this.paymentType = PaymentType.NOT_PAID;
        this.userEmail = userEmail;
    }
}
