package com.cda.freely.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * @UserCategory classe réprésentant la table de liaison entre User et Category
 * composée de 2 manyToOne vers User et Category
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_category")
public class UserCategory {
    @EmbeddedId
    private KeyUserCategory userCategoryId;

    /**
     * @KeyUserCategory classe réprésentant la clé composée de la table de liaison
     * la clé se compose de id_user et id_category
     * */
    @Embeddable
    public static class KeyUserCategory implements Serializable {
        @Column(name = "id_user", nullable = false)
        Integer idUser;
        @Column(name = "id_category", nullable = false)
        Integer idCategory;
    }

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @MapsId("idCategory")
    @JoinColumn(name = "id_category")
    private Category category;

}
