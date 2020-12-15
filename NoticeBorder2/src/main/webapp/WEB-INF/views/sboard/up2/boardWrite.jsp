<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="/resources/ckeditor/ckeditor.js"></script>
<script type="text/javascript">

$(function(){
	CKEDITOR.replace("editor",{
		fileUploadUrl : "$(pageCoun,requset.contextPath}/admin/fileupload.do"
	});
	var g_count =1;
	$("#backBtn").on("click",function(e){
		e.preventDefault();
		window.location.href = '/up/NoticeBoard.do';
	});

});

</script>

<section>
	<div class="tbl">
		<form action="/up/PostWrite.do" method="post">
	
			<div class="board noticeDetail signup">
				<ul>
					<li class="th">Writer</li>
					<li class="td">
						<sec:authorize access="isAuthenticated()">
							<sec:authentication property="principal.username" var="writer" />
							<div id="user_id">${writer}</div>
							<input type="hidden" name="writer" value="${writer}"/>
						</sec:authorize>
					</li>
				</ul>
				<ul>
					<li class="th">Content</li>
					<li class="td">
						<textarea name="editor" id="editor1" rows="10" cols="80"></textarea>
					</li>
				</ul>
			</div>
			
			<div class="listBtn">
				<button type="submit">Create</button>
				<button id="backBtn">Cancel</button>
			</div>
		</form>
	</div>
	<h3></h3>
</section>