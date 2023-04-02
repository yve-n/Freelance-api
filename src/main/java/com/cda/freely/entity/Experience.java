package com.cda.freely.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
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


    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

}
