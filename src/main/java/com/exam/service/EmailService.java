package com.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendScoreEmail(String username, String quizName, int score, int total) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(username);
        message.setSubject("Your Quiz Score for " + quizName);
        message.setText(
        	    "Dear " + username + ",\n\n" +
        	    "Congratulations on completing the quiz titled \"" + quizName + "\".\n\n" +
        	    "We are pleased to inform you that your score is " + score + " out of " + total + ".\n\n" +
        	    "Thank you for your participation.\n\n" +
        	    "Best regards,\n" +
        	    "Exam Management Team"
        	);


        mailSender.send(message);
    }
}
