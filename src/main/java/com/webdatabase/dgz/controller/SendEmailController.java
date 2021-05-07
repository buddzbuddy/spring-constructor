package com.webdatabase.dgz.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-email")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SendEmailController {
	
	@Autowired
	private JavaMailSender emailSender;

	@ResponseBody
	@GetMapping(path = "/mail")
	public final boolean sendUserEmail(String email, String username, String password) throws MessagingException{
		return _sendEmail(email, username, password);
	}
	
	private boolean _sendEmail(String email, String username, String password) throws MessagingException {

		if (email == null || email.isEmpty()) {
			return false;
		}
		String mailAddress = "intersoftkgz@gmail.com";


		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		
		helper.addTo(email);
		helper.setFrom(mailAddress);
		helper.setTo(email);
		helper.setSubject("Уведомление логин-пароль Департамент государственных закупок пр МФ КР");
		
		String htmlMsg = "<h4>Уважаемый пользователь. Ваши учетные данные успешно зарегистрированы в систему со след. параметрами:</h4>"
                + "<p>Логин: <b>" + username + "</b></p>"
                + "<p>Пароль: <b>" + password + "</b></p>";

		helper.setText(htmlMsg, true);
		emailSender.send(message);
		
		return true;
	}
}
