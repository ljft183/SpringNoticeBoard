<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<script type="text/javascript">
$(function(){
	$("#checkBtn").hide();
	$("#backBtn").on("click",function(e){
		e.preventDefault();
		window.location.href = '/lp/logIn.do';
	});
	$("#writer").on("change keyup paste", function() {
		$("#checkBtn").hide();
	});

	$("#idCheck").on("click",function(e){
		e.preventDefault();
		if($("#writer").val()){
			
		
			$.ajax({
				type: "POST"
			    , url: "/lp/idCheck.do"
			    , data :{
			       writer : $("#writer").val(), 
			      }
				, datatype: 'json'
		        , error : function(){
		            alert('error');
		        }
			    , success: function(data) {
					var result = data.checkIdResult;
					if(result=="Y"){
						$("#checkBtn").show();
						alert("이용 가능한 아이디입니다.")
					}
					else{
						alert("이미 존재하는 아이디입니다.");
					}
			    }
			});
		}
		else{
			alert("아이디를 먼저 입력해주세요.");
		}
	});
});
</script>

<section>
<h2></h2>
	
	<h2></h2>
	<h2></h2>
	<h2>Sign Up</h2>

	<div class="tbl">
		<form action="/lp/signUp.do" method="post">
			<div class="board noticeDetail signup">
				<ul>
					<li class="th">ID</li>
					<li class="td disB">
						<input type="text" id="writer" name="writer" placeholder="Please enter your ID " required>
						<button id="idCheck">중복체크</button>
					</li>
				</ul>
				<ul>
					<li class="th">Password</li>
					<li class="td"><input type="password" name="password" placeholder="Please enter your ID" required></li>
				</ul>
				<ul>
					<li class="th">MailAddress</li>
					<li class="td"><input type="text" name="mailAddress" placeholder="Please enter your Address" required></li>
				</ul>
	
			</div>
			<div class="listBtn">
				<button type="submit" id="checkBtn">Regist</button>
				<button id="backBtn">Cancel</button>
			</div>
		</form>

	</div>


</section>

