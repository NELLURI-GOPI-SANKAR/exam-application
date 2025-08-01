package com.exam.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quizName;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;

    // Constructors, Getters, Setters
    public QuizEntity() {}

    public QuizEntity(String quizName) {
        this.quizName = quizName;
    }

    public Long getId() { return id; }
    public String getQuizName() { return quizName; }
    public void setQuizName(String quizName) { this.quizName = quizName; }
    public List<QuestionEntity> getQuestions() { return questions; }
    public void setQuestions(List<QuestionEntity> questions) { this.questions = questions; }
}
