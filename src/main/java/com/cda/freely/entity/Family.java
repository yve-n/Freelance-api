package com.cda.freely.entity;


import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "family")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_family", nullable = false)
    @JsonView({Views.User.class,Views.Tag.class })
    private Long id;


    @Column(name = "name", length = 100,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.User.class, Views.Tag.class })
    private String name;

    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Tag.class })
    private String description;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({Views.Family.class })
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({Views.Family.class })
    private List<Tag> tags = new ArrayList<>();

    @Override
    public String toString() {
        return "Family{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
