package com.cda.freely.entity;

import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_training", nullable = false)
    @JsonView({Views.User.class, Views.Training.class})
    private Integer id;

    @Column(name = "title", length = 100,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.User.class, Views.Training.class})
    private String title;

    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Training.class})
    private String description;

    @Column(name = "year", length = 10)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Training.class})
    private String year;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonView({Views.Training.class})
    private User user;
}
