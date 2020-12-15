package com.kjm.controller.userPage2;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoticeController2 {
	@RequestMapping(value="/up2/NoticeBoard2.do")	
	public ModelAndView NoticeBoard2(Principal principal)throws Exception {					
		ModelAndView mv = new ModelAndView();
		String username = principal.getName();
		mv.setViewName("/sboard/up2/detail");
		return mv;
	}
}
