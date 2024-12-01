package com.mockmate.auth_service.entities.questions;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(length = 500, unique = true, nullable = false)
    private String companyName;

    public Company(Long companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Company() {
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}