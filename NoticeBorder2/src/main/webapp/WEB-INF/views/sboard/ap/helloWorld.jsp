<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript"> 
	// Onload : ��泥� 泥댄�� �ㅼ��
	$(function(){ 
		$("#allCheck").click(function(){
			if($("#allCheck").prop("checked")) {
				$("input[name=box]").prop("checked",true);
			}
			else {
				$("input[name=box]").prop("checked",false);
			}
		})

	})


	$(document).ready(function(){
		$('#boardTable').show();
		$('#userTable').hide();
		$("#changeTableBtn").on("click",function(){
			$('#boardTable').toggle();
			$('#userTable').toggle();
		});

		// Global Variable : ��硫� 蹂�寃� �ы��, ����
		var formObj = $("form[name='mainForm']");
		var formObj3 = $("form[name='mainForm23']");
		var formObj2 = $("form[name='uploadForm']");
		var formObj4 = $("form[name='uploadForm2']");
		$("#backBtn").on("click",function(){
			window.location.href = '/up/NoticeBoard.do';
		});
		
		$("#cambodia").on("click",function(e){
			e.preventDefault();
			window.location.href = '/ap/cambodia.do';
		});
		// Excel Upload : List Grid
	    $("#uploadBtn").on("click", function(){	
    		formObj2.attr("action", "/ap/getListAddExcel.do");
    		formObj2.submit();
	    });
		// Excel Upload : List Grid
	    $("#uploadCambodiaBtn").on("click", function(){	
    		formObj4.attr("action", "/ap/getListAddExcelCambodia.do");
    		formObj4.submit();
	    });

		// Delete : List Grid
	    $("#deleteBtn").on("click", function(){
	    	
			var param = "";
	    	$("input[name=box]:checked").each(function() {
	    		if(param == "")
	    			param += $(this).val();
	    		else
	    			param += ","+$(this).val();
	    	});
	    	if(param=="")
	    		return;
	    	$("#delBnoArray").val(param);
	        formObj.attr("action", "/ap/getListDelete.do");   
	        formObj.submit();     
	    	
	    });
	    
		// Excel Download
	    $("#excelBtn").on("click", function(){

	        formObj.attr("action", "/ap/getListExcel.do");   
	        formObj.submit();     
	    	
	    });
   	    
	    //Insert || Update : List 
	    $("#createBtn").on("click", function(){
	    	
	    	if($("#bno").val()){

	    		formObj.attr("action", "/ap/getListModify.do");
	    		formObj.submit();
	    	}
	    	else{
	    		alert("List");
	    		$("#bno").val(0);
	    		formObj.attr("action", "/ap/getListAdd.do");
	    		formObj.submit();
	    	}
		});
		
		
		
		
		
		
	    $("#createBtn2").on("click", function(){
	    	formObj3.attr("action", "/ap/userModify.do");
	    	formObj3.submit();

		});
		$("#allCheck2").click(function(){
			if($("#allCheck2").prop("checked")) {
				$("input[name=box2]").prop("checked",true);
			}
			else {
				$("input[name=box2]").prop("checked",false);
			}
		})
	    $("#deleteBtn2").on("click", function(){
	    	
			var param = "";
	    	$("input[name=box2]:checked").each(function() {
	    		if(param == "")
	    			param += $(this).val();
	    		else
	    			param += ","+$(this).val();
	    	});
	    	if(param=="")
	    		return;
	    	$("#delBnoArray2").val(param);
	        formObj3.attr("action", "/ap/userDelete.do");   
	        formObj3.submit();     
	    	
	    });

	});
	
	function read123(bno, title, content ,writer){
		$("#title").val(title);
		$("#content").val(content);
		$("#writer").val(writer);
		$("#bno").val(bno);	
	}
	function read1234(writer, enalbed, auth){
		$("#writer2").val(writer);
		$("#enabled").val(enalbed);
		$("#auth").val(auth);
	}

	

</script>

	<button id="backBtn">NoticeBoard</button>
	<button id="changeTableBtn">ChangeTable</button>
<div id="boardTable">
	<table border="1" id="table_List" >
			<tr>
				<th><input type="checkbox" id="allCheck"></th>
				<th>bno</th>
				<th>title</th>
				<th>content</th>
				<th>writer</th>
				<th>viewcnt</th>
				<th>regdate</th>
			</tr>

			<c:forEach items="${lists}" var="list">
				<tr>
					<td><input type="checkbox" name="box" value="${list.bno}"> </td>
					<td><c:out value="${list.bno}" /></td>
					<td>
						<a href = "javascript:read123('${list.bno}','${list.title}','${list.content}','${list.writer}')"> 
							<c:out value="${list.title}"/>
						</a> 
					</td>
					<td><c:out value="${list.content}" /></td>
					<td><c:out value="${list.writer}" /></td>
					<td><c:out value="${list.viewcnt}" /></td>
					<td><c:out value="${list.regdate}" /></td>
				</tr>
			</c:forEach>

	</table >
		
	<form name="mainForm" method="POST">
		<div>
			<div>
				<input type="text" id="title" name="title" placeholder="Title" required>
				<input type="text" id="content" name="content" placeholder="Content" required>
				<input type="text" id="writer" name="writer" placeholder="Writer" value="admin" required>
				<input type="hidden" id="bno" name="bno" />
				<input type="hidden" id="delBnoArray" name="delBnoArray" />
					
			</div>
			<div>
				<button type="submit" id="createBtn" >Save</button>
			</div>
		</div>
	</form>

	<div>

		<button type="submit" id="deleteBtn" >Delete</button>
		<button type="submit" id="excelBtn" >Download</button>	
	</div>

    <form name="uploadForm" method="POST" enctype="Multipart/form-data">
		<div>
    		<input type="file" name="fileName1" />	
    		<button type="submit" id="uploadBtn">Upload</button>
        </div>
    </form>
    <div>
	    <form action="/ap/getListAjax.do" method="post">
			<input type="submit" value="Ajax">
	
		</form>
  	</div>
</div>   


<div id="userTable">
	<table border="1">
			<tr>
				<th><input type="checkbox" id="allCheck2"></th>
				<th>writer</th>
				<th>mailaddress</th>
				<th>enabled</th>
				<th>auth</th>
			</tr>

			<c:forEach items="${userList}" var="list2">
				<tr>
					<td><input type="checkbox" name="box2" value="${list2.writer}"> </td>
					<td>
						<a href = "javascript:read1234('${list2.writer}','${list2.enabled}','${list2.auth}')"> 
							<c:out value="${list2.writer}"/>
						</a> 
					</td>
					<td><c:out value="${list2.mailAddress}" /></td>
					<td><c:out value="${list2.enabled}" /></td>
					<td><c:out value="${list2.auth}" /></td>
				</tr>
			</c:forEach>

	</table >
		
	<form name="mainForm23" method="POST">
		<div>
			<div>
				<input type="text" id="writer2" name="writer" placeholder="writer" required readonly="readonly">
				<input type="text" id="enabled" name="enabled" placeholder="enabled" required>
				<input type="text" id="auth" name="auth" placeholder="auth" value="admin" required>
				<input type="hidden" id="delBnoArray2" name="delBnoArray2" />
			</div>
			<div>
				<button type="submit" id="createBtn2" >Save</button>
			</div>
		</div>
	</form>

	<div>

		<button type="submit" id="deleteBtn2" >Delete</button>
		<button id="cambodia">cambodia_excel</button>
		
	</div>
	<div>
		<form name="uploadForm2" method="POST" enctype="Multipart/form-data">
			<div>
	    		<input type="file" name="fileName2" />	
	    		<button type="submit" id="uploadCambodiaBtn">UploadCam</button>
	        </div>
	    </form>
	</div>

</div>  

