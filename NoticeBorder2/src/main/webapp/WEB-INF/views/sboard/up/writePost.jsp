<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
$(function(){
	var g_count =1;
	$("#backBtn").on("click",function(e){
		e.preventDefault();
		window.location.href = '/up/NoticeBoard.do';
	});
	$("a[name='delete']").on("click",function(e){
		e.preventDefault();
		fn_fileDelete($(this));
	});
    $("#fileAddBtn").on("click",function(e){
        e.preventDefault();
        fn_fileAdd();
    })
	
	
    function fn_fileDelete(obj){
        obj.parent().parent().remove();
    }
    function fn_fileAdd(){
    	var str ="";
    	str += '<ul class="boardFile">';
    	str += "<li><input type='file' name='file_"+(g_count++)+"'/></li>";
    	str += "<li><a href='#this' name='delete' class='btn'>Delete</a></li>";
    	str += "</ul> ";
        
        $("#fileDiv").append(str);
         
        $("a[name='delete']").on("click",function(e){
            e.preventDefault();
            fn_fileDelete($(this));         
        })
    }
});

</script>

<section>
	<div class="tbl">
		<form action="/up/PostWrite.do" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	
			<div class="board noticeDetail signup">

				<ul>
					<li class="th">Title</li>
					<li class="td"><input type="text" name="title"
						placeholder="Title" required>
					</li>
				</ul>
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
						<textarea name="content" maxlength="1000" rows="25" cols="80" placeholder="Content" required></textarea>
					</li>
				</ul>
			</div>
			
			<div id="fileDiv" class="board noticeDetail signup">
				<p>첨부파일은 100MB를 넘길 수 없습니다.</p>
				<ul class="boardFile">
					<li><input type="file" name="file_0" />	</li>
					<li><a href="#this" name="delete" class="btn">Delete</a></li>
				</ul>
			</div>
			
			<div class="listBtn">
				<button id="fileAddBtn">File Add</button>
				<button type="submit">Create</button>
				<button id="backBtn">Cancel</button>
			</div>
		</form>
	</div>
	<h3></h3>
</section>
