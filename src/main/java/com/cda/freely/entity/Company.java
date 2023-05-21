package com.cda.freely.entity;

import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    @JsonView(Views.UserDetails.class)
    private Long id;

    @Column(name = "name", length = 50,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView(Views.UserDetails.class)
    private String name;

    @Column(name = "siret", unique = true, length = 20)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView(Views.UserDetails.class)
    private String siret;

    @Column(name = "number", length = 12,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView(Views.UserDetails.class)
    private String number;

    @Column(name = "logo")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView(Views.UserDetails.class)
    private String logo;

    @Column(name = "tva")
    @JdbcTypeCode(SqlTypes.TINYINT)
    @JsonView(Views.UserDetails.class)
    private Boolean tva;

    @Column(name = "siren", unique = true, length = 20, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView(Views.UserDetails.class)
    private String siren;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user",nullable = false)
    private User user;

    @JsonView(Views.UserDetails.class)
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @Column(name = "company_state")
    @Enumerated(EnumType.STRING)
    @JsonView(Views.UserDetails.class)
    private Status companyState;


    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
//        if (addresses != null) {
//            addresses.forEach(address -> address.setCompany(this));
//        }
//        for(Address address : addresses) {
//            address.setCompany(this);
//        }
        this.addresses = addresses;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User id_user) {
        this.user = id_user;
    }

}
