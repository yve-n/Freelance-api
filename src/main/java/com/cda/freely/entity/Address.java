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
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address", nullable = false)
    private Long id_address;

    @Column(name = "address", length = 40, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String address;

    @Column(name = "zip_code", length = 32, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String zip_code;

    @Column(name = "city", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String city;

    @Column(name = "country", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String country;

    @ManyToOne
    @JoinColumn(name = "id_company")
    private Company id_company;

    public Company getId_company() {
        return id_company;
    }

    public void setId_company(Company id_company) {
        this.id_company = id_company;
    }

}
