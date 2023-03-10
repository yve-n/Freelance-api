package com.cda.freely.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_history", nullable = false)
    private Long id_history;

    @Lob
    @Column(name = "description",nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at",nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date created_at;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;

    public History() {
    }

    public History(Long id_history, String description, Date created_at, User id_user) {
        this.id_history = id_history;
        this.description = description;
        this.created_at = created_at;
        this.id_user = id_user;
    }
    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }
    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id_history;
    }

    public void setId(Long id) {
        this.id_history = id;
    }

}