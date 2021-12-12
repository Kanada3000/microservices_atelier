package com.atelier.serviceclaim.repo.model;

import javax.persistence.*;

@Entity
@Table(name="claims")
public final class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    private StatusName status;
    private long userId;

    public Claim() {
    }

    public Claim(String description, StatusName status, long userId) {
        this.description = description;
        this.status = status;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusName getStatus() {
        return status;
    }

    public void setStatus(StatusName status) {
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
