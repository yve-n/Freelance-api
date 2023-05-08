package com.cda.freely.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * @UserTag classe réprésentant la table de liaison entre User et Tag
 * composée de 2 manyToOne vers User et Tag
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_tag")
public class UserTag {
    @EmbeddedId
    private KeyUserTag idUserTag;

    /**
     * @KeyUserTag classe réprésentant la clé composée de la table de liaison
     * la clé se compose de id_user et id_tag
     * */
    @Embeddable
    public static class KeyUserTag implements Serializable {
        @Column(name = "id_user", nullable = false)
        Integer idUser;
        @Column(name = "id_tag", nullable = false)
        Integer idTag;
    }

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @MapsId("idTag")
    @JoinColumn(name = "id_tag")
    private Tag tag;

}
