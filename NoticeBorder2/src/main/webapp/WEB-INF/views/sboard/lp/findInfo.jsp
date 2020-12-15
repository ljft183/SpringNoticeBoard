<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function(){
		idSection();
		
		$("#backBtn").on("click",function(){
			window.location.href = '/lp/logIn.do';
		});
		
		$("#findID").on("click",function(){
			var mailaddress = $("#findIDMail").val();
			//console.log(mailaddress);
			$.ajax({
				type: "GET"
				    , url: "/lp/findID.do"
		    	    , data :{
				       mailaddress : mailaddress
				      }
					, datatype: 'json'
			        , error : function(){
			            alert('error');
			        }
				    , success: function(res) {
				    	console.log(res);
				    	var result = res.result;
						var answer = res.answer;
						console.log(answer);
				    	if(result==0){
				    		alert(answer);
				    		window.location.href = '/lp/findInfo.do';
				    	}
				    	else{
				    		alert(answer);
				    		window.location.href = '/lp/logIn.do';
				    	}
				    }	
			});
		});
		
		$("#findPW").on("click",function(){
			var id = $("#findPWID").val();
			var mailaddress = $("#findPWMail").val();
			//console.log(mailaddress);
			$.ajax({
				type: "GET"
				    , url: "/lp/findPassword.do"
		    	    , data :{
				       mailaddress : mailaddress,
				       id : id
				      }
					, datatype: 'json'
			        , error : function(){
			            alert('error');
			        }
				    , success: function(res) {
				    	console.log(res);
				    	var result = res.result;
						var answer = res.answer;
						console.log(answer);
				    	if(result==0){
				    		alert(answer);
				    		window.location.href = '/lp/findInfo.do';
				    	}
				    	else{
				    		alert(answer);
				    		window.location.href = '/lp/logIn.do';
				    	}
				    }	
			});
		});
		

		
	});

	function idSection(){
		$("#idSection").show();
		$("#passwordSection").hide();
	}
	
	function passwordSection(){
		$("#idSection").hide();
		$("#passwordSection").show();
	}
</script>

<section id="idSection">
	<ul class="findAcount">
		<li class="on"><a href="javascript:idSection()">Find ID</a></li>
		<li><a href="javascript:passwordSection()">Find Password</a></li>
	</ul>
	<h3>Insert Your Info</h3>
	
		<fieldset class="findId" >
			<input id="findIDMail" type="text" placeholder="Enter your E-mail" required>
			<ul>
				<li></li>
			</ul>	
			<p class="noticeText">If the E-mail is corrected, ID will be emailed to you.</p>
		</fieldset>

	<div class="listBtn">

		<button id="findID" class=" btn01">Send</button>	

		<button id="backBtn">Cancel</button>
			
	</div>
</section>


<section id="passwordSection">
	<ul class="findAcount">
		<li><a href="javascript:idSection()">Find ID</a></li>
		<li class="on"><a href="javascript:passwordSection()">Find Password</a></li>
	</ul>
	<h3>Insert Your Info</h3>
	<fieldset class="findId" >
		<input id="findPWID" type="text" placeholder="Enter your ID" required>
		<h2></h2>
		<input id="findPWMail" type="text" placeholder="Enter your E-mail" required>
		<ul>
			<li></li>
		</ul>
		<p class="noticeText">If the E-mail and ID is corrected, Temporary Password will be emailed to you.</p>
	</fieldset>

	<div class="listBtn">
		<button id="findPW" class=" btn01">Send</button>

		<button id="backBtn">Cancel</button>
	</div>
</section>
