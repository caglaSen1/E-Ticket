package com.ftbootcamp.eticketuserservice.model;

import com.ftbootcamp.eticketuserservice.model.constant.UserEntityConstants;
import com.ftbootcamp.eticketuserservice.model.enums.Gender;
import com.ftbootcamp.eticketuserservice.model.enums.StatusType;
import com.ftbootcamp.eticketuserservice.model.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = UserEntityConstants.EMAIL, nullable = false)
    private String email;

    @Column(name = UserEntityConstants.PASSWORD, nullable = false)
    private String password;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = UserEntityConstants.CREATED_DATE)
    private LocalDateTime createdDate;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.userType = UserType.STANDARD;
        this.statusType = StatusType.WAITING_APPROVAL;
        this.createdDate = LocalDateTime.now();
    }
}
