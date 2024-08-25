package com.ftbootcamp.eticketauthservice.model;

import com.ftbootcamp.eticketauthservice.model.constant.EntityConstants;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = EntityConstants.NAME, nullable = false)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}

