<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="wrap main">
	<form action="<c:url value='/login'/>" method="post">
		<fieldset>
			<h2>Login</h2>
			<input name="writer" type="text" placeholder="Username" value="jm0812"
				required> <input name="password" type="password"
				placeholder="Password" value="1234" required>
			<!-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />  -->
			<button class="btn btn01" id="logInBtn">LOGIN</button>
			<a href="/lp/findInfo.do">FIND ID &amp; Password</a> 
			<a href="/lp/initSignUp.do">SIGN UP</a>
		</fieldset>
	</form>
</div>
