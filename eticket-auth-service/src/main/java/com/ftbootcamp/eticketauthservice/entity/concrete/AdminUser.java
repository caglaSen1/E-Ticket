package com.ftbootcamp.eticketauthservice.entity.concrete;

import com.ftbootcamp.eticketauthservice.entity.abstracts.User;
import com.ftbootcamp.eticketauthservice.entity.constant.UserEntityConstants;
import com.ftbootcamp.eticketauthservice.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "admin_users")
public class AdminUser extends User {

    @Column(name = UserEntityConstants.FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = UserEntityConstants.LAST_NAME, nullable = false)
    private String lastName;

    @Column(name = UserEntityConstants.NATIONAL_ID, nullable = false)
    private Long nationalId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public AdminUser(String email, String phoneNumber, String password, String firstName, String lastName,
                     Long national, Gender gender){
        super(email, phoneNumber, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = national;
        this.gender = gender;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
