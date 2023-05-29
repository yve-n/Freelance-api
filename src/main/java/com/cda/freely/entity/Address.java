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

import java.util.Objects;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.Company.class,Views.Address.class ,Views.User.class})
    @Column(name = "id_address", nullable = false)
    private Long id;

    @Column(name = "address", length = 40, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Company.class,Views.Address.class ,Views.User.class})
    private String address;

    @Column(name = "zip_code", length = 32, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Address.class})
    private String zipCode;

    @Column(name = "city", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Address.class })
    private String city;

    @Column(name = "country", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JsonView({Views.Address.class })
    private String country;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_company")
    @JsonView({Views.Address.class })
    @JsonIgnoreProperties("addresses")
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Address)) return false;
//        Address address = (Address) o;
//        return Objects.equals(getId(), address.getId());
//    }
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId());
//    }
}
