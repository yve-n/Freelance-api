package com.cda.freely.entity;

import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @JsonView({Views.Achievement.class, Views.Experience.class,Views.Image.class})
    private Long id;

    @Column(name = "title", nullable = false,length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Achievement.class, Views.Experience.class,Views.Image.class})
    private String title;

    @Lob
    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Achievement.class})
    private String description;

    @Column(name = "client", length = 60)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Achievement.class})
    private String client;

    @Temporal(TemporalType.DATE)
    @Column(name = "achieve_date" , nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    @JsonView({Views.Achievement.class})
    private Date achieveDate;


    @OneToMany(mappedBy = "achieve", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({Views.Achievement.class})
    private List<Image> images = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_experience")
    @JsonView({Views.Achievement.class})
    private Experience experience;

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
