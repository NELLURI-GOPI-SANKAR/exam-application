package com.exam.service;

import com.exam.entity.QuestionEntity;
import com.exam.entity.QuizEntity;
import com.exam.repository.QuestionRepository;
import com.exam.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;
    
    public List<QuizEntity> getAllQuizzes() {
        return quizRepository.findAll();
    }

    
    public List<QuestionEntity> getQuizByName(String quizName) {
        QuizEntity quiz = quizRepository.findByQuizName(quizName);
        if (quiz != null) {
            return quiz.getQuestions(); 
        }
        return new ArrayList<>();
    }

    
    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }

    
    
    public void saveQuiz(String quizName, int numberOfQuestions, Map<String, String> formData) {
        QuizEntity quiz = new QuizEntity(quizName);
        List<QuestionEntity> questionList = new ArrayList<>();

        for (int i = 0; i < numberOfQuestions; i++) {
            QuestionEntity q = new QuestionEntity();
            q.setText(formData.get("questions[" + i + "].text"));
            q.setOption1(formData.get("questions[" + i + "].option1"));
            q.setOption2(formData.get("questions[" + i + "].option2"));
            q.setOption3(formData.get("questions[" + i + "].option3"));
            q.setOption4(formData.get("questions[" + i + "].option4"));
            q.setCorrectOption(Integer.parseInt(formData.get("questions[" + i + "].correctOption")));
            q.setQuiz(quiz); // 🔗 Link question to the quiz
            questionList.add(q);
        }

        quiz.setQuestions(questionList);
        quizRepository.save(quiz); // ✅ Cascade saves all questions
    }
}
