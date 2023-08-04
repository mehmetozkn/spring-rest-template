package com.resttemplate.REST.Template.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User  {

    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_gen", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "surname", length = 100)
    private String surname;

}
