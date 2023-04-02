package com.cda.freely.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }






}
