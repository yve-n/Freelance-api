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
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_skill", nullable = false)
    @JsonView({Views.User.class, Views.Skill.class})
    private Long id;

    @Column(name = "name", length = 40,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.User.class, Views.Skill.class})
    private String name;

    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Skill.class})
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonView({Views.Skill.class})
    private User user;

}
