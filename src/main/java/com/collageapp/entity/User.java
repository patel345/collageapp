package com.collageapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;
//spring security use of user
//step no 1 use of spring security
@Data
@Entity
@Table(name="users",uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
@UniqueConstraint(columnNames = {"email"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    private String username;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="user_roles",
                  joinColumns=@JoinColumn(name = "user_id",referencedColumnName = "id"),
                 inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Role> roles;
}
