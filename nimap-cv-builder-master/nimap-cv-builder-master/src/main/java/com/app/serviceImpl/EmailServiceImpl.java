package com.app.serviceImpl;

import java.io.File;
import java.net.URL;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.app.serviceIntf.EmailServiceIntf;

@Service("emailServiceImpl")
public class EmailServiceImpl implements EmailServiceIntf {

	@Autowired
	private JavaMailSender emailSender;

	@Value("${secure-file.cv-dir}")
	private URL templateLocation;

	@Override
	public void sendSimpleMessage(String emailTo, String subject, String text) {

		SimpleMailMessage message = new SimpleMailMessage();
		// message.setFrom("vinayghadigaonkar@nimapinfotech.com");
		message.setTo(emailTo);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);

	}

	@Override
	public void sendMessageWithAttachment() {

		try {

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// helper.setFrom("vinayghadigaonkar@nimapinfotech.com");
			helper.setTo("vghadigaonkar89@gmail.com");
			helper.setSubject("TEST SUBJECT");
			helper.setText("TEST TEXT");
			File tempDirectory = new File(templateLocation.getPath());
			File outputFile = new File(tempDirectory, "12345" + ".pdf");
			FileSystemResource file = new FileSystemResource(outputFile);
			helper.addAttachment("Invoice", file);
			emailSender.send(message);

		} catch (MessagingException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

}