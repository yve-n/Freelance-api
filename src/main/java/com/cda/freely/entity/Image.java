package com.cda.freely.entity;

import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView({Views.Image.class,Views.Achievement.class})
    private Long id;


    @Column(name = "url", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Image.class,Views.Achievement.class})
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_achieve", nullable = false)
    @JsonView({Views.Image.class})
    private Achievement achieve;

    public Achievement getAchieve() {
        return achieve;
    }

    public void setAchieve(Achievement achieve) {
        this.achieve = achieve;
    }



}
