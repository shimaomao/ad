package com.planx.advertise.system.mail;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.planx.advertise.system.exception.ApplicationException;

@Service
public class MailServiceImpl implements MailService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Value("${server.website.name}") 
	private String nickName;
	
    @Value("${spring.mail.username}") 
    private String sendFrom;
    
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Override
	public void sendTemplateMail(String template, String recipient, String subject, Map<String, Object> params) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		Context context = new Context();
		context.setVariables(params);
		try {
			helper = new MimeMessageHelper(message, true);
			String emailContent= templateEngine.process(template, context);
			helper.setText(emailContent, true);
			helper.setTo(recipient);
			helper.setFrom(new InternetAddress(nickName+" <"+sendFrom+">"));
			helper.setSubject(subject);
		} catch (MessagingException e) {
			log.error("MessagingException", e);
			throw new ApplicationException("MessagingException", e);
		}
		try {
			mailSender.send(message);
		} catch (MailException e) {
			log.error("MailException", e);
			throw new ApplicationException("send mail error", e);
		}
	}
	
	@Override
	public void sendHtmlMail(String recipient, String subject, String emailContent) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setText(emailContent, true);
			helper.setTo(recipient);
			helper.setFrom(new InternetAddress(nickName+" <"+sendFrom+">"));
			helper.setSubject(subject);
		} catch (MessagingException e) {
			log.error("MessagingException", e);
			throw new ApplicationException("MessagingException", e);
		}
		try {
			mailSender.send(message);
		} catch (MailException e) {
			log.error("MailException", e);
			throw new ApplicationException("send mail error", e);
		}
	}
}
