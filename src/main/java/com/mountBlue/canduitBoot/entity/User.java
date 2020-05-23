package com.mountBlue.canduitBoot.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @NotNull
    @Size(min = 2, max = 10, message = "Name cannot exceed 10 characters")
    private String name;

    @Column(name = "email")
    @NotEmpty(message = "Email field cannot be empty")
    @Email(regexp = "^(.+)@(.+)$", message = "Invalid email address")
    private String email;

    @Column(name = "password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#_$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain A letter, Special Character, No space and length must be 8")
    private String password;

    private boolean isEnabled;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Authorities authorities;

    public User() {
    }

    public User(@NotNull @Size(min = 2, max = 10, message = "Name cannot exceed 10 characters") String name,
                @NotEmpty(message = "Email field cannot be empty") @Email(regexp = "^(.+)@(.+)$", message = "Invalid " +
                        "email address") String email, @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#_$%^&+=])" +
            "(?=\\S+$).{8,}$",
            message = "Password must contain A letter, Special Character, No space and length must be 8") String password,
                boolean isEnabled, Authorities authorities) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.authorities = authorities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Authorities getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Authorities authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", authorities=" + authorities +
                '}';
    }
}
