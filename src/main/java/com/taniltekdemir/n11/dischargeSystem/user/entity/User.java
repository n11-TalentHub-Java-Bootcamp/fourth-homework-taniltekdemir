package com.taniltekdemir.n11.dischargeSystem.user.entity;

import com.taniltekdemir.n11.dischargeSystem.gen.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "USR_USER")
@Data
public class User implements BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "username")
    private String username;
}
