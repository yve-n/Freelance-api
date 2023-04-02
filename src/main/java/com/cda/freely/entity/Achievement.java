package com.cda.freely.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_achieve", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false,length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String title;

    @Lob
    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String description;

    @Column(name = "client", length = 60)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String client;

    @Temporal(TemporalType.DATE)
    @Column(name = "achieve_date" , nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date achieve_date;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;

    @OneToMany(mappedBy = "id_achieve", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }




}
