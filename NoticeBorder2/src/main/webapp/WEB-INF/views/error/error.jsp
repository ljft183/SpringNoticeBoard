<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/css/base.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/sub.css" />
<title>NoticeBoard</title>
<script src="http://code.jquery.com/jquery-3.3.1.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>


<script type="text/javascript"> 
$(function(){
	$("#toMain").on("click",function(){
		window.location.href = '/up/NoticeBoard.do';
	});
});
</script>
<meta charset="UTF-8">
<title>Error Page</title>
</head>
<body>
	<section>
		<h2></h2>
		<h2></h2>
		<h2></h2>
		<h2></h2>
		<h2>error occured</h2>
		<div class="listBtn">	
			<button id="toMain">Go to Main</button>
		</div>
	</section>



</body>
</html>