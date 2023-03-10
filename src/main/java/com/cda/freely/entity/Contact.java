package com.cda.freely.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contact", nullable = false)
    private Long id_contact;

    @Column(name = "name", length = 50,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @Column(name = "email", length = 50,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String email;

    @Column(name = "number", length = 12)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String number;

    @Lob
    @Column(name = "message", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String message;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    @JdbcTypeCode(SqlTypes.DATE)
    private Date created_at;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;

    public Contact() {
    }

    public Contact(Long id, String name, String email, String number, String message, Date created_at, User id_user) {
        this.id_contact = id;
        this.name = name;
        this.email = email;
        this.number = number;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id_contact;
    }

    public void setId(Long id) {
        this.id_contact = id;
    }

}