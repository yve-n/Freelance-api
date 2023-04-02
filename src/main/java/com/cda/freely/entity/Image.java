package com.cda.freely.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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



}
