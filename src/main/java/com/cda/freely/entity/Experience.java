package com.cda.freely.entity;

import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @JsonView({Views.User.class,Views.Experience.class,Views.Achievement.class})
    private Long id;

    @Column(name = "title", length = 100,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.User.class,Views.Experience.class,Views.Achievement.class})
    private String title;

    @Lob
    @Column(name = "description", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Experience.class})
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "started_at", nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    @JsonView({Views.Experience.class})
    private Date startedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "ended_at",nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    @JsonView({Views.Experience.class})
    private Date endedAt;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonView({Views.Experience.class})
    private User user;

    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({Views.Experience.class})
    private Collection<Achievement> achievements = new ArrayList<>();

}
