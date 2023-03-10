package com.cda.freely.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long id_user;

    @Column(name = "first_name", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String first_name;

    @Column(name = "last_name", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String last_name;

    @Column(name = "email", unique = true,length = 50,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String email;

    @Column(name = "password", length = 50,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String password;

    @Column(name = "profile_pic")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String profile_pic;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at",nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date created_at;

    @Column(name = "role", length = 10,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "id_family")
    private Family id_family;



    @OneToMany(mappedBy = "id_user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Achievement> achievements = new ArrayList<>();

    @OneToMany(mappedBy = "id_user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Company> companies = new ArrayList<>();

    @OneToMany(mappedBy = "id_user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "id_user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(mappedBy = "id_user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "id_user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Service> services = new ArrayList<>();

    @OneToMany(mappedBy = "id_user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();
    @OneToMany(mappedBy = "id_user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Training> trainings = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status id_status;
    public User() {
    }
    public User(Long id, String first_name, String last_name, String email, String password, String profile_pic, Date created_at, String role, Gender gender, Family id_family) {
        this.id_user = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.profile_pic = profile_pic;
        this.created_at = created_at;
        this.role = role;
        this.gender = gender;
        this.id_family = id_family;
    }

    public Status getId_status() {
        return id_status;
    }

    public void setId_status(Status id_status) {
        this.id_status = id_status;
    }

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

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public Family getId_family() {
        return id_family;
    }

    public void setId_family(Family id_family) {
        this.id_family = id_family;
    }
    public Long getId() {

        return id_user;
    }
    public void setId(Long id) {

        this.id_user = id;
    }
    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}