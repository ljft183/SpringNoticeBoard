<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
var g_count =1;
var deleteFileArr="";
$(function(){
	
	var num = "${vo.bno}"
    	num *=1;
	
	$("#backBtn").on("click",function(e){
		e.preventDefault();
		window.location.href = '/up/NoticeBoard.do';
	});
	$("a[name='deleteFile']").on("click",function(e){
		e.preventDefault();

		var deleteFileNo= $(this).attr('value');
        if(deleteFileArr==""){
        	deleteFileArr += "" + deleteFileNo;
        }
        else{
        	deleteFileArr += "," + deleteFileNo;
        }
        console.log(deleteFileArr);
        document.getElementById("deleteFileListID").value = deleteFileArr;
        
		fn_fileDelete($(this));

	});
	
	$("a[name='delete']").on("click",function(e){
		e.preventDefault();
		fn_fileDelete($(this));
	});
    $("#fileAddBtn").on("click",function(e){
        e.preventDefault();

        fn_fileAdd();
    })
	
});
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
</script>

<section>
	<div class="tbl">
		<form action="/up/PostUpdate.do" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	
			<div class="board noticeDetail signup">
				<ul>
					<li class="th">Title</li>
					<li class="td">${vo.bno}
					</li>
				</ul>	
				<ul>
					<li class="th">Title</li>
					<li class="td"><input type="text" name="title"
						value="${vo.title}" required>
					</li>
				</ul>
				<ul>
					<li class="th">Writer</li>
					<li class="td" >${vo.writer}<input type="hidden" name="writer"
						value="${vo.writer}"></li>
				</ul>
				<ul>
					<li class="th">Content</li>
					<li class="td">
						<textarea name="content" maxlength="1000" rows="25" cols="80" required>${vo.content}</textarea>
					</li>
				</ul>
			</div>
			<div class="board noticeDetail signup">
				<c:forEach items="${list}" var="list">
					<ul id="fileLine" class="boardFile">
						<li>File</li>
						<li id="fileList"><a href="/up/fileDownload.do?bno=${list.bno}&fileno=${list.fileno}">${list.filename}</a>(${list.filesize}Byte)
						</li>
						<li><a href="#this" name="deleteFile" class="btn" value="${list.fileno}">Delete</a></li>
						
					</ul>
				</c:forEach>
			</div>
			<div id="fileDiv" class="board noticeDetail signup">
				<p>첨부파일은 100MB를 넘길 수 없습니다.</p>
				<ul class="boardFile">
					<li><input type="file" name="file_0" />	</li>
					<li><a href="#this" name="delete" class="btn">Delete</a></li>
				</ul>
			</div>
			<input type="hidden" name="bno" value = "${vo.bno}">
			<input type="hidden" name="deleteFileList" id="deleteFileListID" value="">
			<div class="listBtn">
				<button id="fileAddBtn">File Add</button>
				<button type="submit">Update</button>
				<button id="backBtn">Cancel</button>
			</div>
		</form>
	</div>
<h3></h3>




</section>
