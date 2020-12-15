package com.kjm.controller.loginPage;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kjm.service.BoardService;
import com.kjm.util.CaptchaUtil;
import com.kjm.vo.UserVO;

@Controller
public class loginController {
	
	@Inject
	private BoardService service;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	//로그인 페이지 호출, 로그아웃, 로그인 실패 시 예외 처리	
	@RequestMapping("/lp/logIn.do")	
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model){

		if (error != null) {
			System.out.println("Invalid username and password");
			return "/sboard/lp/loginError";
		}else if (logout != null) {
			System.out.println("You have been logged out successfully");
			return "/sboard/lp/logout";
		}

		
		return "/sboard/lp/login";
		

	}
	
	//회원가입을 위해 사용자 captcha를 통한 검증 페이지 오픈 
	@RequestMapping("/lp/initSignUp.do")	
	public ModelAndView initSignUp()throws Exception {
		ModelAndView mv = new ModelAndView();
		
		Date d1 = null;	
		Date d2 = null;
		Date d3 = null;
		
		Date currentTime = new Date();
		SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
		
		d1 = f.parse("11:59:30");	//11:59:30~12:02:00 까지 회원가입 불가능 처리(captcha 비교를 위해 다운로드 된 이미지 파일 삭제 scheduler)
		d2 = f.parse("12:02:00");
		d3 = f.parse(SimpleDateFormat.format(currentTime));
		
		if((d1.compareTo(d3)<0) && (d2.compareTo(d3)>0)) {
			mv.setViewName("/sboard/lp/inspectionTime");
			return mv;
		}
		else {
		
			
			CaptchaUtil captcha = new CaptchaUtil();	//캡차 관련 util 선언
			
	        String clientId = "RIRfMmWbksfLXDThFUJ_"; //애플리케이션 클라이언트 아이디값";
	        String clientSecret = "fd8mK2WCV0"; //애플리케이션 클라이언트 시크릿값";
	
	        String code = "0"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
	        String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;
	        
	        Map<String, String> requestHeaders = new HashMap<>();
	        requestHeaders.put("X-Naver-Client-Id", clientId);
	        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
	        
	        String key = captcha.getKey(apiURL, requestHeaders);
	
	        JSONParser josnParser = new JSONParser();
	        Object obj = josnParser.parse( key );
	        JSONObject jsonObj = (JSONObject) obj;
	        String apiURL2 = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + jsonObj.get("key");
	        String filename = captcha.getImage(apiURL2,requestHeaders);
	
	
	        mv.addObject("key", jsonObj.get("key"));
	        mv.addObject("filename", filename);
	        mv.setViewName("/sboard/lp/captcha");
	        return mv;
		}
	}
	
	//이미지의 key와 사용자가 입력한 key 값 비교
	@RequestMapping("/lp/checkCaptcha.do")	
	public String checkCaptcha(HttpServletRequest req)throws Exception {	
		CaptchaUtil captcha = new CaptchaUtil();
		
        String clientId = "RIRfMmWbksfLXDThFUJ_"; //애플리케이션 클라이언트 아이디값";
        String clientSecret = "fd8mK2WCV0"; //애플리케이션 클라이언트 시크릿값";

	    String code = "1"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
	    String key = (String)req.getParameter("key"); // 캡차 키 발급시 받은 키값
	    String value = (String)req.getParameter("value"); // 사용자가 입력한 캡차 이미지 글자값
	    String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + key + "&value=" + value;

	    Map<String, String> requestHeaders = new HashMap<>();
	    requestHeaders.put("X-Naver-Client-Id", clientId);
	    requestHeaders.put("X-Naver-Client-Secret", clientSecret);
	    String responseBody = captcha.getCaptchaResult(apiURL, requestHeaders);
	    
        JSONParser josnParser = new JSONParser();
        Object obj = josnParser.parse(responseBody);
        JSONObject jsonObj = (JSONObject) obj;
	    if((boolean) jsonObj.get("result")) {
	    	 return "/sboard/lp/signUp";
	    }
	    else
	    	return "redirect:/lp/initSignUp.do";	    	
		
	}

	//captcha 서비스 인증 성공 시 회원가입 페이지 오픈
	@RequestMapping("/lp/signUp.do")	
	public String signUp(UserVO uvo)throws Exception {
		if(uvo==null) {
			return "redirect:/lp/logIn.do";
		}
		uvo.setEnabled(1);
		String endcodedPassword = bcryptPasswordEncoder.encode(uvo.getPassword());
		uvo.setPassword(endcodedPassword);
		service.signUp(uvo);
		
		return "redirect:/lp/logIn.do"; // login.jsp(Custom Login Page)
	}
	
	//아이디 비밀번호 찾기 페이지 호출	
	@RequestMapping(value="/lp/findInfo.do")	
	public ModelAndView findInfo()throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/sboard/lp/findInfo");
		return mv;
	}
	
}
