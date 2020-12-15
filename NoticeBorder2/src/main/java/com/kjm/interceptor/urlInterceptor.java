package com.kjm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class urlInterceptor extends HandlerInterceptorAdapter { //미구현 보류 상태
	@Override
	public boolean preHandle(
			HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		String urlCheck = request.getRequestURI();
		if(urlCheck.contains("\\[") || 
			urlCheck.contains("]") || 
			urlCheck.contains("{") || 
			urlCheck.contains("}") ||
			urlCheck.contains("|") || 
			urlCheck.contains("{") || 
			urlCheck.contains("}")) 
		{
			System.out.println("urlIntercepotr - preHandle - urlError");
			response.sendRedirect("/up/error.do");
			return false;
		}
		else {
			
			return true;
		}
	}


}
