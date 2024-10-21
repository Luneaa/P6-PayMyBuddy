package com.pmb.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Entity representing a user
 */
@Data
@Entity
@Table(name = "users")
public class User {

    /**
     * ID of the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    /**
     * Username of the user
     */
    @Column(name = "username")
    private String username;

    /**
     * Email of the user
     */
    @Column(name = "email")
    private String email;

    /**
     * Encrypted password of the user
     */
    @Column(name = "password")
    private String password;

    /**
     * List of users the user is connected to
     */
    @ManyToMany
    @JoinTable(name = "userconnections",
                joinColumns = @JoinColumn (name = "id_user"),
                inverseJoinColumns = @JoinColumn (name = "id_user_connected_to"))
    private List<User> connections;
}
