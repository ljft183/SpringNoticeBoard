package com.kjm.controller.loginPage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kjm.service.BoardService;
import com.kjm.util.LoginUtil;
import com.kjm.util.mailSend;
import com.kjm.vo.UserVO;


@RestController
public class logInRestController {
	
	@Inject
	private BoardService service;
	@Autowired 
	mailSend mailSend;
	@Autowired
	LoginUtil loginUtil;
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	//캡차 이미지 리턴
	@RequestMapping(value="/lp/getImage.do", method=RequestMethod.GET)	
	public String getImageWithMediaType(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String filename = (String)req.getParameter("filename");
		String path = "C:/Temp/images/";
		String filePath = path + filename + ".jpg";
		res.setContentType("image/jpeg");
		File imageFile = new File(filePath);
		byte[] image = IOUtils.toByteArray(new FileInputStream(imageFile));
		res.getOutputStream().write(image);
		
		imageFile.delete();
    
		return null;
	}
	
	//id 찾기 시에 회원 mail로 아이디 발송
	@RequestMapping("/lp/findID.do")	
	public Map<String, Object> findID(HttpServletRequest req) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		String subject="-NoticeBoard- Find Your E-mail, Key";
		String contentID="Your ID is ";
		String answer = new String();
		String id = new String();
		String mailaddress = new String();
		
		int result = 0;

		try {
			mailaddress += (String)req.getParameter("mailaddress");
			id = service.findID(mailaddress);
			int i = id.length();
			if(i>0)
				System.out.println("E-mail Check");
			contentID += id;
			
			
			answer = "Success!, Send Your ID";
			result = mailSend.mailSending(mailaddress, subject, contentID);

			map.put("answer", answer);
			map.put("result", result);
			return map;		
			
		}catch(Exception e) {
			answer = "ID Search Fail, Check E-Mail";
			System.out.println("check2");
			map.put("answer", answer);
			map.put("result", result);
			return map;		
		}
		


		

	}
	
	//비밀번호 찾기 시 회원 mail에 임시 비밀번호 발송
	@RequestMapping("/lp/findPassword.do")	
	public Map<String, Object> findPassword(HttpServletRequest req) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		String subject="-NoticeBoard- Find Your Temporary Password";

		String contentPW="Your Temporary Password is ";
		String answer = new String();
		String pw = new String();
		String mailaddress = new String();
		String id = new String();
		
		int result = 0;

		try {
			mailaddress = (String)req.getParameter("mailaddress");
			id = (String)req.getParameter("id");
			
			UserVO checkUvo = new UserVO();
			checkUvo.setWriter(id);
			checkUvo.setmailAddress(mailaddress);
			
			UserVO uvo = service.checkUserInfo(checkUvo);
			
			if(uvo == null || "".equals(uvo)) {
				answer = "Mail do not match ID, Check E-Mail and ID";
				System.out.println("check3");
				map.put("answer", answer);
				map.put("result", result);
				return map;		
			}

			pw = loginUtil.randomPassword();

			contentPW += pw;
			String endcodedPassword = bcryptPasswordEncoder.encode(pw);

			uvo.setPassword(endcodedPassword);
			service.modifyUser(uvo);
			
			
			
			answer = "Success!, Send Your Temporary Password";
			result = mailSend.mailSending(mailaddress, subject, contentPW);

			map.put("answer", answer);
			map.put("result", result);
			return map;		
			
		}catch(Exception e) {
			answer = "Error!!";
			map.put("answer", answer);
			map.put("result", result);
			return map;		
		}
		


	}
	
	//아이디 확인
	@RequestMapping("/lp/idCheck.do")	
	public Map<String,Object> idCheck(HttpServletRequest req)throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String writer = (String)req.getParameter("writer");
		int result = service.idCount(writer);
		if(result==1) {
			map.put("checkIdResult","N");
		}
		else {
			map.put("checkIdResult","Y");
		}
		return map; // login.jsp(Custom Login Page)
	}


}

