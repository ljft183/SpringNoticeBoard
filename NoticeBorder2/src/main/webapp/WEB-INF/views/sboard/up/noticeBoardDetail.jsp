<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript"> 

$(function(){
	$("#CommentShow").hide();
	$("#commentBtn").on("click",function(){
		$("#CommentShow").toggle();
	});
	
	var num = "${vo.bno}"
    	num *=1;
	
	
	list_Comment(num);
	
	$("#commentWriteBtn").on("click",function(){
		
		if($("#comment").val() == ""){
			alert("댓글을 작성바랍니다.");
			return;
		} else {
			
			$.ajax({
				type: "POST"
			    , url: "/up/commentWrite.do"
			    , data :{
			       pageNum : num,
			       writer : $("#writer").val(),
			       comment : $("#comment").val()
			      }
				, datatype: 'json'
		        , error : function(){
		            alert('error');
		        }
			    , success: function(data) {
			    	
			    	console.log(data);
			    	var isSuccess = data.result.isSuccess;
			    	
			    	if(isSuccess == "Y"){
				    	list_Comment(num);	
				    	$("#CommentShow").show();
				    	$("#comment").val("");
			    	}else{
			    		alert("댓글 저장 시 오류가 발생하였습니다. 다시 시도하여 주시기 바랍니다.");
			    	}
			    }
			});
		}
	});
	

	
	$("#listPage").on("click",function(){
		window.location.href = '/up/NoticeBoard.do';
	});
	
	// Call Previous Post 
	$("#prevBtn").on("click",function(){
		$.ajax({
			type: "POST"
		    , url: "/up/prevPage.do"
    	    , data :{
		       pageNum : num
		      }
			, datatype: 'json'
	        , error : function(){
	            alert('error');
	        }
		    , success: function(data) {
		    	var pageNo = data.bno;
		    	pageNo *=1;
		    	if(pageNo==num)
		    		alert("StartPage - > Do not exist Prev Page");
		    	else
		    		location.href = "/up/NoticeBoardDetail.do?bno=" + pageNo;
		    }
		});
	});
	
	// Call Next Post
	$("#nextBtn").on("click",function(){
		$.ajax({
			type: "POST"
		    , url: "/up/nextPage.do"
    	    , data :{
		       pageNum : num
		      }
			, datatype: 'json'
	        , error : function(){
	            alert('error');
	        }
		    , success: function(data) {
		    	var pageNo = data.bno;
		    	pageNo *=1;
		    	if(pageNo==num)
		    		alert("EndPage - > Do not exist Next Page");
		    	else
		    		location.href = "/up/NoticeBoardDetail.do?bno=" + pageNo;
		    }
		});
	});
	
})

	function fn_deleteAttachFile(commentno, bno, i){
		var result = confirm("댓글을 삭제하시겠습니까?");
		
		if(result){
			$("a[name='delete_"+i+"']").parent().parent().remove();

			$.ajax({
				type: "POST"
			    , url: "/up/commentDelete.do"
			    , data :{
			       commentno : commentno,
			       bno : bno
			      }
				, datatype: 'json'
		        , error : function(){
		            alert('error');
		        }
			    , success: function(data) {
					alert("댓글 삭제 완료");
			    }
			});			
		}
	}
	


function list_Comment(num){
	var username = $('#writer').val();
	console.log(num);
	
	$.ajax({
		type: "POST"
	    , url: "/up/getCommentList.do"
	    , data :{
	    	pageNum : num
	      }
		, datatype: 'json'
        , error : function(){
            alert('error');
        }
	    , success: function(res) {
	    	var data = res.list;
	    	
	    	$("#NoticeComment").empty();
	    	var str_noticeList = "";
	    	
	    	$.each(data, function(i){	
				
	      		str_noticeList += '  <ul>';
	       		str_noticeList += '   <li> '+ data[i].commentno +'</li>';	   		
	       		str_noticeList += '   <li>' + data[i].comment + '</li>';
	       		str_noticeList += '   <li> '+ data[i].writer +'</li>';
	       		str_noticeList += '   <li> '+ data[i].regdate +'</li>';
	       		if(username==data[i].writer){
	       			str_noticeList += '   <li> <a href="javascript:fn_deleteAttachFile('+data[i].commentno+','+data[i].bno+','+i+')" value='+data[i].commentno+' name="delete_'+i+'" class="btn">Delete</a></li>'
	       		}
	       		else{
	       			str_noticeList += '   <li></li>'
	       		}
	       		str_noticeList += '  </ul>';
	       	 });

	    	$("#NoticeComment").append(str_noticeList);
	    }
	    

	    
	    
	});
	
	
	
	

}


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
		<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal.username" var="username" />

			<button id="prevBtn">Prev</button> 

			<c:set var="username" value="${username}" />
			<c:set var="writer" value="${vo.writer}" />

			<c:if test = "${username eq writer}">
				<form method="POST" action="/up/NoticeUpdate.do">
					<button type="submit" name="bno" value="${vo.bno}">Update</button>
				</form>
			</c:if>
			<button id="commentBtn">Comment</button>
			<button id="listPage">List</button>
			
			<c:if test = "${username eq writer}">
				<form method="POST" action="/up/NoticeDelete.do">
					<button type="submit" name="bno" value="${vo.bno}">Delete</button>
				</form>
			</c:if>
		
			<button id="nextBtn">Next</button>
			
		</sec:authorize>
	</div>

</section>