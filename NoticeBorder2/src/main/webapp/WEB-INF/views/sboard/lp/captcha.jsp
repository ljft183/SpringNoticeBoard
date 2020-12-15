<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
$(function(){
	$("#backBtn").on("click",function(e){
		e.preventDefault();
		window.location.href = '/lp/logIn.do';
	});
	$("#changeBtn").on("click",function(e){
		e.preventDefault();
		window.location.href = '/lp/initSignUp.do';
	});
});
</script>

<h3></h3>
<section id="pageSet">

	<ul class="guide">
		<li><img src='/lp/getImage.do?filename=${filename}' alt=""></li>
	</ul>
	<form action="/lp/checkCaptcha.do" method="post">
		<fieldset class="search">
			<input type="text" class="searchText" name="value" placeholder="KEY"> 
			<input type="hidden" name="key" value="${key}">
		</fieldset>
		<div class="listBtn">
			<button type="submit" class="btn btn01" >Check</button>
			<button class="btn btn01" id="changeBtn">Change Image</button>
			<button class="btn btn01" id="backBtn">Cancel</button>	
		</div>
	</form>

</section>

