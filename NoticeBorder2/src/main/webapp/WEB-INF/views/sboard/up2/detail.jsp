<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript"> 

$(function(){

	$("#commentBtn").on("click",function(){
		$("#CommentShow").toggle();
	});


</script>

<section>
	<div class="board noticeDetail">
		<ul>
			<li class="th">No.</li>
			<li class="td">${vo.bno}</li>
			<li class="th">View Count</li>
			<li class="td">${vo.viewcnt}</li>
			<li class="th">create Time</li>
			<li class="td">${vo.regdate}</li>
		</ul>
		<ul>
			<li class="th">Title</li>
			<li class="td">${vo.title}</li>
			<li class="th">Wirter</li>
			<li class="td">${vo.writer}</li>
		</ul>
		<ul>
			<li class="th">Content</li>
			<li class="td"><textarea readonly maxlength="1000" rows="25" cols="80">${vo.content}</textarea></li>
		</ul>
	</div>
	<div class="board noticeDetail signup">
		<c:forEach items="${fileList}" var="list" >
			<ul id="fileLine" class="boardFile">
				<li>File</li>
				<li id="fileList">
			        
                        <a href="/up/fileDownload.do?bno=${list.bno}&fileno=${list.fileno}">${list.filename}</a>     &emsp;    (${list.filesize}Byte)
                   
				</li>
			</ul>
		 </c:forEach> 	
	</div>
	
	<div id="CommentShow">
		<div class="board noticeBoardComment" id="NoticeComment">
		</div>

	</div>
	<div class="board noticeDetail signup listBtn">	
		<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal.username" var="username" />
			<ul class="commentList">
				<li class="th">Comment</li>
				<li class="td">
					<input placeholder="Max Comment Size 50" size="50" type="text" id="comment" required>
					<input type="hidden" id="writer" value="${username}">
					<input type="hidden" id="bno" value="${vo.bno}">
				</li>
				<li>
					<button id="commentWriteBtn">save</button>
				</li>
		
			</ul>
		</sec:authorize>
	</div>		
	<div class="listBtn">

		<form method="POST" action="/up/NoticeDelete.do">
			<button type="submit" name="bno" value="${vo.bno}">Delete</button>
		</form>


		<button id="nextBtn">Next</button>

	</div>

</section>