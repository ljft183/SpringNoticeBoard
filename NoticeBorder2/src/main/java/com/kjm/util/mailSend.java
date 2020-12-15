package com.kjm.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class mailSend {

	@Autowired
	private JavaMailSender mailSender;
	
	public int mailSending(String to, String subject, String mailText) throws Exception{
		String from = "jmjm0812@naver.com";
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			System.out.println(from);
			System.out.println(to);
			System.out.println(subject);
			System.out.println(mailText);

			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(mailText);

			mailSender.send(message);
			return 1;

		} catch (Exception e) {

			e.printStackTrace();
			return 0;
		}
	}


}
