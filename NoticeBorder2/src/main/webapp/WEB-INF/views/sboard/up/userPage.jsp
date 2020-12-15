<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<script type="text/javascript">
$(function(){

	$("#backBtn").on("click",function(e){
		e.preventDefault();
		window.location.href = '/up/myInfo.do';
	});
})
</script>

<section>
	<h2></h2>
	<h2>User Info Modify</h2>
	<form action="/up/userModify.do" method="POST">
		<div class="tbl">
			<div class="board noticeDetail signup">
				<ul>
					<li class="th">ID</li>
					<li class="td">${uvo.writer}
					<input type="hidden" name="writer" value="${uvo.writer}"></li>
					
				</ul>
				<ul>
					<li class="th">Input New Password</li>
						<li class="td"><input type="password">
					</li>
				</ul>
				<ul>
					<li class="th">Input Check Password</li>
						<li class="td"><input name="password" type="password">
					</li>
				</ul>
				<ul>
					<li class="th">Email</li>
						<li class="td"><input name="mailAddress" type="text" value="${uvo.mailAddress}">
					</li>
				</ul>
			</div>
		</div>
		<div class="listBtn">
			<button type="submit">Save</button>
			<button id="backBtn">Back</button>
		</div>
	</form>
</section>
