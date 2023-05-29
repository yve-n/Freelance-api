package com.cda.freely.entity;

import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "company")
public class Company {
    public enum Status {
        ACTIVE, INACTIVE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_company", nullable = false)
    @JsonView({Views.User.class,Views.Company.class ,Views.Address.class})
    private Long id;

    @Column(name = "name", length = 50,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.User.class,Views.Company.class ,Views.Address.class})
    private String name;

    @Column(name = "siret", unique = true, length = 20)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Company.class })
    private String siret;

    @Column(name = "number", length = 12,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Company.class })
    private String number;

    @Column(name = "logo")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Company.class })
    private String logo;

    @Column(name = "tva")
    @JdbcTypeCode(SqlTypes.TINYINT)
    @JsonView({Views.Company.class })
    private Boolean tva;

    @Column(name = "siren", unique = true, length = 20, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Company.class })
    private String siren;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user",nullable = false)
    @JsonView({Views.Company.class })
    private User user;

    @OneToMany(mappedBy = "company", cascade = { CascadeType.PERSIST, CascadeType.MERGE } ,orphanRemoval = true)
    @JsonView({Views.Company.class, Views.User.class })
    private List<Address> addresses = new ArrayList<>();

    @Column(name = "company_state")
    @Enumerated(EnumType.STRING)
    @JsonView({Views.Company.class })
    private Status companyState;

}
