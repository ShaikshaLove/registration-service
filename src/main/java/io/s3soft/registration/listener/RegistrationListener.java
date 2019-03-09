package io.s3soft.registration.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import io.s3soft.registration.event.OnRegistrationEvent;
import io.s3soft.registration.model.User;
import io.s3soft.registration.service.IUserService;
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationEvent> {

    @Autowired
    private IUserService service;
    
    @Autowired 
    private JavaMailSenderImpl sender;
    
	@Override
	public void onApplicationEvent(OnRegistrationEvent event) {
		this.confirmRegistration(event);

	}

	private void confirmRegistration(OnRegistrationEvent event) {
         User user=event.getUser();
         String appUrl=event.getAppUrl();
         UUID uuid=UUID.randomUUID();
         String token=uuid.toString().replaceAll("-","");
         service.createVerificationToken(user, token);
         System.out.println("Registration Listener"+appUrl);
         System.out.println(service.getVerificationToken(token));
         
         
         String reciepentAddress=user.getEmail();
         String subject="S3food.in Registration Conformation";
         String conformationUrl=appUrl+"/users/conformRegistration?token="+token+"&&firstName="+user.getFirstName();
         String message="Dear, "+user.getFirstName().toUpperCase()+"  "+user.getLastName()+"   "
         		+ "Please activate your account by clicking this  link ";
         
         SimpleMailMessage mailMessage=new SimpleMailMessage();
         mailMessage.setTo(reciepentAddress);
         mailMessage.setSubject(subject);
         mailMessage.setText(message+" "+"https://s3food-users.herokuapp.com"+conformationUrl);
         sender.send(mailMessage);
         
	}

}
