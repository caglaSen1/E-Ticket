package com.ftbootcamp.eticketuserservice.entity.concrete;

import com.ftbootcamp.eticketuserservice.entity.constant.RoleEntityConstants;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

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

    @Column(name = RoleEntityConstants.NAME, nullable = false)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}

