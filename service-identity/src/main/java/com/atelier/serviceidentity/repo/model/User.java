package com.atelier.serviceidentity.repo.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="users")
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;
    private String phone;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private UserType userType = UserType.CLIENT;

    public User() {
    }

    public User(String name, String phone, String email, String password, UserType userType) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.userType = userType;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
