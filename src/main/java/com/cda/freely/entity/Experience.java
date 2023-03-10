package com.cda.freely.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
@Table(name = "experience")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_experience", nullable = false)
    private Long id_experience;

    @Column(name = "title", length = 100,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String title;

    @Lob
    @Column(name = "description", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "started_at", nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date started_at;

    @Temporal(TemporalType.DATE)
    @Column(name = "ended_at",nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date ended_at;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;

    public Experience() {
    }

    public Experience(Long id, String title, String description, Date started_at, Date ended_at, User id_user) {
        this.id_experience = id;
        this.title = title;
        this.description = description;
        this.started_at = started_at;
        this.ended_at = ended_at;
        this.id_user = id_user;
    }
    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public Date getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(Date ended_at) {
        this.ended_at = ended_at;
    }

    public Date getStarted_at() {
        return started_at;
    }

    public void setStarted_at(Date started_at) {
        this.started_at = started_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id_experience;
    }

    public void setId(Long id) {
        this.id_experience = id;
    }

}