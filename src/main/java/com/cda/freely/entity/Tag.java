package com.cda.freely.entity;

import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.*;
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
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag", nullable = false)
    @JsonView({Views.Family.class , Views.User.class, Views.Tag.class, Views.FamilyWithoutUsers.class})
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Family.class, Views.User.class,Views.Tag.class, Views.FamilyWithoutUsers.class})
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_family")
    @JsonView({Views.Tag.class })
    private Family family;
    @ManyToMany(mappedBy = "tags", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonView({Views.Tag.class })
    private List<User> users = new ArrayList<>();


}
