package com.kjm.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class LoginUtil {
	public String randomPassword() {
		
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i<10;i++) {
			int index = rand.nextInt(3);
			switch(index) {
			case 0:
				sb.append((char)(rand.nextInt(26)+97));
				break;
			case 1:
				sb.append((char)(rand.nextInt(26)+65));
				break;
			case 2:
				sb.append(rand.nextInt(10));
				break;
			}
		}
		
		String tempPassword = sb.toString();
		return tempPassword;
		
	}
}
