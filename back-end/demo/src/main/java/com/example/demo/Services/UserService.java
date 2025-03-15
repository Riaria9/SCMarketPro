package com.example.demo.Services;
import org.springframework.stereotype.Service;
import com.example.demo.Respositories.OTPRepository;
import com.example.demo.Respositories.UserRepository;
import com.example.demo.Tables.Verification;
import com.example.demo.Tables.User;
import java.io.IOException;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
@Service
public class UserService 
{
    private final UserRepository userRepository;
    private OTPRepository otpRepository;
    private static final int OTP_LENGTH = 6;
    @Autowired private JavaMailSender emailSender;

    @Autowired public UserService(UserRepository userRepository, OTPRepository otpRepository) 
    {
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }

    public User findById(Long userId) 
    {
        return userRepository.findById(userId).orElse(null);
    }

    public Iterable<User> findAll()
    {

        return userRepository.findAll();
    }

    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }
    //registration method
    public User registUser(User user){
        return userRepository.save(user);
    }

    public String generateOTP() {
        return RandomStringUtils.randomNumeric(OTP_LENGTH);
    }
    public void saveOtp(String email, String otp) {
        Verification otpEntity = otpRepository.findByEmail(email)
        .orElse(new Verification());
        otpEntity.setEmail(email);
        otpEntity.setOTP(otp);
        
        otpRepository.save(otpEntity);
    }
    
    public void sendSimpleMessage(String to, String subject, String text) {
        Email fromEmail = new Email("edwardzh@usc.edu");        
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            
        }
    }

    public boolean validateOtp(String email, String otp) {
        Optional<Verification> otpEntity = otpRepository.findByEmail(email);
        if (otpEntity.isPresent()) {
            return otpEntity.get().getOTP().equals(otp);
        }
        return false;
    }
    
}