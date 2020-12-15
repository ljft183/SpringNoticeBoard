<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){

	$("#userEdit").on("click",function(){	
		window.location.href = '/up/CheckPass.do';
	});
	$("#backBtn").on("click",function(){	
		window.location.href = '/up/NoticeBoard.do';
	});
})
</script>
<section>
	<h2></h2>
	<h2></h2>
    <h2>User Info</h2>
    <div class="tbl">
        <div class="board noticeDetail">
            <ul>
                <li class="th">User ID</li>
                <li class="td">${uvo.writer}</li>
            </ul>
            <ul>
                <li class="th">Mail Address</li>
                <li class="td">${uvo.mailAddress}</li>
            </ul>
            <ul>
                <li class="th">User Level</li>
                <li class="td">${uvo.auth}</li>
            </ul>
            <ul>
                <li class="th">Exp</li>
                <li class="td">${uvo.exp}</li>
            </ul>

            
        </div>
    </div>
    <h2></h2>
    <div class="listBtn">
        <button id="userEdit">Update</button>
        <button id="backBtn">Back</button>
    </div>
</section>