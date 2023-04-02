package com.cda.freely.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User  implements UserDetails {
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
    @Enumerated(EnumType.STRING)
    private UserRole role ;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "user_account_state")
    @JdbcTypeCode(SqlTypes.TINYINT)
    private Integer user_account_state;

    @Column(name = "user_availability")
    @JdbcTypeCode(SqlTypes.TINYINT)
    private Integer user_availability;

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

    @ManyToMany(mappedBy = "users")
    private Collection<UserCategory> userCategories = new ArrayList<>();

    public Collection<UserCategory> getUserCategories() {
        return userCategories;
    }

    public void setUserCategories(Collection<UserCategory> userCategories) {
        this.userCategories = userCategories;
    }


    // return a list of role
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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


}
