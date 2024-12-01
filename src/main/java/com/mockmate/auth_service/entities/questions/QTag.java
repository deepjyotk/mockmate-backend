package com.mockmate.auth_service.entities.questions;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "qtags_table")
public class QTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(length = 500)
    @NotNull
    private String questionTagText;

    public QTag(Long tagId, String questionTagText) {
        this.tagId = tagId;
        this.questionTagText = questionTagText;
    }

    public QTag() {
    }

    public Long getTagId() {
        return this.tagId;
    }

    public String getQuestionTagText() {
        return this.questionTagText;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public void setQuestionTagText(String questionTagText) {
        this.questionTagText = questionTagText;
    }
}