<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript"> 

$(function(){

	$("#backBtn").on("click",function(e){
		e.preventDefault();
		window.location.href = '/up/myInfo.do';
	});
});
</script>

<section>
	<h2></h2>
	<h2></h2>
	<h2></h2>
	<h2>Check Your Password</h2>
	<form action="/up/myPage.do" method="post">
		<div class="tbl">
			<div class="board noticeDetail signup">
				<ul>
					<li class="th">Input Your Password</li>
						<li class="td"><input type="password" name="password" required>
					</li>
				</ul>
			</div>
		</div>
		<div class="listBtn">
			<button id="checkBtn">Check</button>
			<button id="backBtn">Back</button>
		</div>
	</form>
</section>
