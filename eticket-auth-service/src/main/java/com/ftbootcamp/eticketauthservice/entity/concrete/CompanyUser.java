package com.ftbootcamp.eticketauthservice.entity.concrete;

import com.ftbootcamp.eticketauthservice.entity.abstracts.User;
import com.ftbootcamp.eticketauthservice.entity.constant.UserEntityConstants;
import com.ftbootcamp.eticketauthservice.entity.enums.StatusType;
import com.ftbootcamp.eticketauthservice.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "company_users")
public class CompanyUser extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = UserEntityConstants.COMPANY_NAME, nullable = false)
    private String companyName;

    @Column(name = UserEntityConstants.TAX_NUMBER, nullable = false)
    private Long taxNumber;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    public CompanyUser(String email, String phoneNumber, String password, String companyName, Long taxNumber) {
        super(email, phoneNumber, password);
        this.companyName = companyName;
        this.taxNumber = taxNumber;
        this.userType = UserType.STANDARD;
        this.statusType = StatusType.WAITING_APPROVAL;
    }
}
