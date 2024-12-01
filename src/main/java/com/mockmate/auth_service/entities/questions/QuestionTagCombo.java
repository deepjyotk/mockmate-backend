package com.mockmate.auth_service.entities.questions;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name = "question_tags_combo_table")
public class QuestionTagCombo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionTagComboId;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @ToString.Exclude
    private Question question;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    @ToString.Exclude
    private QTag tag;

    public QuestionTagCombo(Long questionTagComboId, Question question, QTag tag) {
        this.questionTagComboId = questionTagComboId;
        this.question = question;
        this.tag = tag;
    }

    public QuestionTagCombo() {
    }

    public Long getQuestionTagComboId() {
        return this.questionTagComboId;
    }

    public Question getQuestion() {
        return this.question;
    }

    public QTag getTag() {
        return this.tag;
    }

    public void setQuestionTagComboId(Long questionTagComboId) {
        this.questionTagComboId = questionTagComboId;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setTag(QTag tag) {
        this.tag = tag;
    }
}