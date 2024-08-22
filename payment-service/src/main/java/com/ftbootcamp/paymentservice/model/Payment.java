package com.ftbootcamp.paymentservice.model;

import com.ftbootcamp.paymentservice.model.constant.PaymentEntityConstants;
import com.ftbootcamp.paymentservice.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = PaymentEntityConstants.AMOUNT, nullable = false)
    private BigDecimal amount;

    @Column(name = PaymentEntityConstants.USER_EMAIL, nullable = false)
    private String userEmail;

    @Column(name = PaymentEntityConstants.CREATED_DATE_TIME)
    private LocalDateTime createdDateTime;

    public Payment(PaymentType paymentType, BigDecimal amount, String userEmail) {
        this.paymentType = paymentType;
        this.amount = amount;
        this.userEmail = userEmail;
        this.createdDateTime = LocalDateTime.now();
    }
}
