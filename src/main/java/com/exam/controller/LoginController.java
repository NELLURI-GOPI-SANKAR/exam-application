package com.exam.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.exam.entity.QuestionEntity;
import com.exam.entity.QuizEntity;
import com.exam.repository.QuizRepository;
import com.exam.service.EmailService;
import com.exam.service.QuizService;
import com.exam.service.ResultService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
    private QuizService quizService;
	@Autowired
    private ResultService resultService;
	@Autowired
    private EmailService emailService;
	@Autowired
	private QuizRepository quizRepository;
	

    @GetMapping("/")
    public String showLoginForm() {
        return "login"; 
    }

    
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session) {
    	session.setAttribute("username", username);
        if ("admin".equalsIgnoreCase(username) && "admin123".equals(password)) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/student/dashboard";
        }
    }

    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {
        List<QuizEntity> quizzes = quizService.getAllQuizzes(); // A method in QuizService
        model.addAttribute("quizzes", quizzes);
        return "admin_dashboard";
    }
    
    
    
    @PostMapping("/admin/deleteQuiz/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuizById(id);
        return "redirect:/admin/dashboard"; // Refresh the dashboard after deletion
    }



    
    @GetMapping("/student/dashboard")
    public String studentDashboard(Model model,HttpSession session) {
    	String username = (String) session.getAttribute("username");
    	model.addAttribute("user",username);
        model.addAttribute("quizzes", quizRepository.findAll());
        return "student_dashboard";
    }
    
    
    @GetMapping("/admin/create-quiz")
    public String showCreateQuizForm() {
        return "create_quiz";
    }

    
    @PostMapping("/admin/create-quiz")
    public String handleCreateQuiz(@RequestParam String quizName,
                                   @RequestParam int numberOfQuestions,
                                   Model model) {
        model.addAttribute("quizName", quizName);
        model.addAttribute("numberOfQuestions", numberOfQuestions);
        return "add_questions";
    }
    
    

    @PostMapping("/admin/save-quiz")
    public String saveQuiz(@RequestParam String quizName,
                           @RequestParam int numberOfQuestions,
                           @RequestParam Map<String, String> allParams) {
        quizService.saveQuiz(quizName, numberOfQuestions, allParams);
        return "redirect:/admin/dashboard";
    }
    
    
    
    @GetMapping("/student/quiz/{quizId}")
    public String startQuiz(@PathVariable Long quizId, Model model) {
        Optional<QuizEntity> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isPresent()) {
            model.addAttribute("quiz", quizOptional.get());
            return "start_quiz"; // next page
        } else {
            return "redirect:/student/dashboard"; // fallback if quiz not found
        }
    }
    
    
    @PostMapping("/submitQuiz")
    public String submitQuiz(@RequestParam Map<String, String> allParams, HttpSession session, Model model) {
        int score = 0;
        String quizName = allParams.get("quizName"); // make sure this field is actually submitted in form!
        String username = (String) session.getAttribute("username");

        List<QuestionEntity> questions = quizService.getQuizByName(quizName);

        for (int i = 0; i < questions.size(); i++) {
            QuestionEntity q = questions.get(i);
            String submitted = allParams.get("answers[" + i + "]");
            if (submitted != null && String.valueOf(q.getCorrectOption()).equalsIgnoreCase(submitted)) {
                score++;
            }
        }
        
        emailService.sendScoreEmail(username, quizName, score, questions.size());
        resultService.saveResult(username, quizName, score);
        
        model.addAttribute("score", score);
        model.addAttribute("total", questions.size());
        return "score";
    }





    
}
