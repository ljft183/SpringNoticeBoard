<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="headWrap">

	<ul class="menu">
		<li><a href="/up/NoticeBoard.do">Home</a></li>
		<sec:authorize access="hasAnyRole('USER2','ADMIN')">
			<li><a href="/up2/NoticeBoard2.do">Board2</a></li>
		</sec:authorize>

		<li></li>
		<li><a href="/up/myInfo.do">myPage</a></li>
		<li><a href="/logout">LogOut</a></li>
	</ul>

</div>
