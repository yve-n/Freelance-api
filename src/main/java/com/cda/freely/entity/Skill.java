package com.cda.freely.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_skill", nullable = false)
    private Long id_skill;

    @Column(name = "name", length = 40,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;

    public Skill() {
    }

    public Skill(Long id_skill, String name, String description, User id_user) {
        this.id_skill = id_skill;
        this.name = name;
        this.description = description;
        this.id_user = id_user;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id_skill;
    }

    public void setId(Long id) {
        this.id_skill = id;
    }

}