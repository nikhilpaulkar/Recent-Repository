package com.app.config;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.app.entities.EmailCredentialEntity;
import com.app.repositories.EmailCredentialRepository;
import com.app.utils.CacheOperation;

@Configuration
public class EmailConfig {

	@Value("${mail.env}")
	private String mailEnv;

	@Autowired
	private CacheOperation cache;

	@Autowired
	private EmailCredentialRepository emailCredentialRepository;

	@EventListener(ApplicationReadyEvent.class)
	@Bean
	@PostConstruct
	public JavaMailSender getJavaMailSender() {

		System.out.println("mailEnv " + mailEnv);
		EmailCredentialEntity emailCred = getEmailCred();
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(emailCred.getHost());
		mailSender.setPort(emailCred.getPort());
		mailSender.setUsername(emailCred.getUsername());
		mailSender.setPassword(emailCred.getPassword());
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", emailCred.getSmtpAuth());
		props.put("mail.smtp.starttls.enable", emailCred.getTlsEnable());
		props.put("mail.debug", "true");
		return mailSender;

	}

	@Bean
	public ResourceBundleMessageSource emailMessageSource() {

		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("mailMessages");
		return messageSource;

	}

	public EmailCredentialEntity getEmailCred() {

		EmailCredentialEntity emailDetail = new EmailCredentialEntity();

		if (!cache.isKeyExist("emailConfig", "emailConfig")) {

			System.out.println("in If");
			emailDetail = emailCredentialRepository.findByEnv(mailEnv);
			cache.addInCache("emailConfig", "emailConfig", emailDetail);

		} else {

			System.out.println("in else");
			emailDetail = (EmailCredentialEntity) cache.getFromCache("emailConfig", "emailConfig");

		}

		return emailDetail;

	}

}
