package com.mockmate.auth_service.entities.questions;

import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "question_company_db")
public class QuestionCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Renamed from question_id to a unique ID

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @ToString.Exclude
    private Question question;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @ToString.Exclude
    private Company company;

    private Integer frequencyAsked;

    private LocalDateTime lastAskedDate;

    public QuestionCompany(Long id, Question question, Company company, Integer frequencyAsked, LocalDateTime lastAskedDate) {
        this.id = id;
        this.question = question;
        this.company = company;
        this.frequencyAsked = frequencyAsked;
        this.lastAskedDate = lastAskedDate;
    }

    public QuestionCompany() {
    }

    public Long getId() {
        return this.id;
    }

    public Question getQuestion() {
        return this.question;
    }

    public Company getCompany() {
        return this.company;
    }

    public Integer getFrequencyAsked() {
        return this.frequencyAsked;
    }

    public LocalDateTime getLastAskedDate() {
        return this.lastAskedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setFrequencyAsked(Integer frequencyAsked) {
        this.frequencyAsked = frequencyAsked;
    }

    public void setLastAskedDate(LocalDateTime lastAskedDate) {
        this.lastAskedDate = lastAskedDate;
    }
}