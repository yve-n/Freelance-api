package com.cda.freely.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Long id;
    @Column(name = "first_name", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String firstName;

    @Column(name = "lastName", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String lastName;

    @Column(name = "email", unique = true,length = 50,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String email;

    @Column(name = "password",nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String password;

    @Column(name = "profile_pic")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String profilePic;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at",nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date createdAt;

    @Column(name = "role", length = 10,nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "user_account_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status userAccountState;

    @Column(name = "user_availability")
    @Enumerated(EnumType.STRING)
    private Availability userAvailability;

    @Column(name = "token_number")
    private int tokenNumber;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "users"})
    @ManyToOne
    @JoinColumn(name = "id_family")
    private Family family;

    @ManyToMany
    @JoinTable(
            name = "user_tag",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_tag"))

    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Company> companies = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Service> services = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Training> trainings = new ArrayList<>();

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }


}
