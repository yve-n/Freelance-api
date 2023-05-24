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

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contact", nullable = false)
    @JsonView({Views.User.class,Views.Contact.class})
    private Long id;

    @Column(name = "name", length = 50,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.User.class,Views.Contact.class})
    private String name;

    @Column(name = "email", length = 50,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Contact.class})
    private String email;

    @Column(name = "number", length = 12)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Contact.class})
    private String number;

    @Lob
    @Column(name = "message", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Contact.class})
    private String message;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    @JdbcTypeCode(SqlTypes.DATE)
    @JsonView({Views.Contact.class})
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonView({Views.Contact.class})
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }






}
