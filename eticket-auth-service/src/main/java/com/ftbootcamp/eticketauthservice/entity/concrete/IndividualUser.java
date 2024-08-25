package com.ftbootcamp.eticketauthservice.entity.concrete;

import com.ftbootcamp.eticketauthservice.entity.abstracts.User;
import com.ftbootcamp.eticketauthservice.entity.constant.UserEntityConstants;
import com.ftbootcamp.eticketauthservice.entity.enums.Gender;
import com.ftbootcamp.eticketauthservice.entity.enums.StatusType;
import com.ftbootcamp.eticketauthservice.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "individual_users")
public class IndividualUser extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = UserEntityConstants.FIRST_NAME)
    private String firstName;

    @Column(name = UserEntityConstants.LAST_NAME)
    private String lastName;

    @Column(name = UserEntityConstants.NATIONAL_ID)
    private Long nationalId;

    @Column(name = UserEntityConstants.BIRTH_DATE)
    private LocalDateTime birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    public IndividualUser(String email, String phoneNumber, String password, String firstName, String lastName,
                          Long nationalId, LocalDateTime birthDate, Gender gender){
        super(email, phoneNumber, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
