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

import java.util.HashSet;
import java.util.Set;
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
    @JsonView(Views.UserDetails.class)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView(Views.UserDetails.class)
    private String name;

    @JsonView({Views.UserDetailsPlus.class })
    @ManyToOne
    @JoinColumn(name = "id_family")
    private Family family;
    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<User> users = new HashSet<>();

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

}
