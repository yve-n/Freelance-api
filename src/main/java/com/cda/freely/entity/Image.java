package com.cda.freely.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image", nullable = false)
    private Long id_image;


    @Column(name = "url", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_achieve", nullable = false)
    private Achievement id_achieve;

    public Achievement getId_achieve() {
        return id_achieve;
    }

    public void setId_achieve(Achievement id_achieve) {
        this.id_achieve = id_achieve;
    }

    public Image(Long id_image, String url, Achievement id_achieve) {
        this.id_image = id_image;
        this.url = url;
        this.id_achieve = id_achieve;
    }

    public Image() {

    }

    public Long getId() {

        return id_image;
    }

    public void setId(Long id) {

        this.id_image = id;
    }
    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }


}