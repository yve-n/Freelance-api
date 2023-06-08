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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "user")
public class User {
    public enum Status {
        PENDING, APPROVED, DECLINED,DELETE
    }
    public enum Availability {
        YES, NO
    }

    public enum Role {
        ROLE_USER,
        ROLE_ADMIN
    }

    public enum Gender {
        MALE,
        FEMALE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    @JsonView({Views.User.class, Views.Company.class, Views.Family.class,
            Views.Tag.class,Views.Contact.class,
    Views.Experience.class,Views.Skill.class,Views.Achievement.class,
    Views.Service.class,Views.Training.class,Views.History.class})
    private Long id;
    @Column(name = "first_name", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.User.class})
    private String firstName;

    @Column(name = "lastName", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.User.class})
    private String lastName;

    @Column(name = "email", unique = true,length = 50,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.User.class, Views.Company.class, Views.Family.class,
            Views.Tag.class,Views.Contact.class,
            Views.Experience.class,Views.Skill.class,Views.Achievement.class,
            Views.Service.class,Views.Training.class,Views.History.class})
    private String email;

    @Column(name = "password",nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.User.class})
    private String password;

    @Column(name = "profile_pic")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.User.class})
    private String profilePic;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at",nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    @JsonView({Views.User.class})
    private Date createdAt;

    @Column(name = "role", length = 10,nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonView({Views.User.class})
    private Role role;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonView({Views.User.class})
    private Gender gender;

    @Column(name = "user_account_state", nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonView({Views.User.class, Views.History.class})
    private Status userAccountState;

    @Column(name = "user_availability")
    @Enumerated(EnumType.STRING)
    @JsonView({Views.User.class})
    private Availability userAvailability;

    @Column(name = "token_number")
    @JsonView({Views.User.class})
    private int tokenNumber;

    @ManyToOne
    @JoinColumn(name = "id_family")
    @JsonView({Views.User.class})
    private Family family;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "user_tag",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_tag"))
    @JsonView({Views.User.class})
    private List<Tag> tags = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    @JsonView({Views.User.class})
    private List<Company> companies = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({Views.User.class})
    private List<Contact> contacts = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({Views.User.class})
    private List<Experience> experiences = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({Views.User.class})
    private List<History> histories = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({Views.User.class})
    private List<Service> services = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({Views.User.class})
    private List<Skill> skills = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({Views.User.class})
    private List<Training> trainings = new ArrayList<>();
}
