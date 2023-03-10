package com.cda.freely.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", length = 100,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String title;

    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String description;

    @Column(name = "year", length = 10)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String year;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;

    public Training() {
    }

    public Training(Integer id, String title, String description, String year, User id_user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.year = year;
        this.id_user = id_user;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}