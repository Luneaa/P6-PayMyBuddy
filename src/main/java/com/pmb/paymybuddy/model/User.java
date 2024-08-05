package com.pmb.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "userconnections",
                joinColumns = @JoinColumn (name = "id_user"),
                inverseJoinColumns = @JoinColumn (name = "id_user_connected_to"))
    private List<User> connections;
}
