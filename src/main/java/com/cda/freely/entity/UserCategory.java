package com.cda.freely.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "user_category")
public class UserCategory {
    @Id
    @Column(name = "id_user", nullable = false)
    private Integer id_user;
    @Id
    @Column(name = "id_category", nullable = false)
    private Integer id_category;


    @ManyToMany
    private Collection<Category> categories = new ArrayList<>();

    @ManyToMany
    private Collection<User> users = new ArrayList<>();

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    public void setCategories(Collection<Category> categories) {
        this.categories = categories;
    }


}
