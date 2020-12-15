<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript"> 
	//onload
	$(document).ready(function(){
		var viewNum = 1;
		var count = "${noticeCount}";
		count *= 1;
		var search = '';
		get_List(viewNum, search);												// Set List
		paging_Set(viewNum, count, search);										// Set PageList
		
		$("#adminBtn").on("click", function(){	
			location.href = "/ap/getList.do";
		});

		
		
		
	    $("#searchBtn").on("click", function(){									// SearchBtn Event
	    	var searchTitle = $("#searchTitle").val();
	    	var searchViewNum =1;
			$.ajax({
				type: "POST"
			    , url: "/up/getSearchCount.do"
	    	    , data :{
			       searchTitle : searchTitle
			      }
				, datatype: 'json'
		        , error : function(){
		            alert('error');
		        }
			    , success: function(data) {
			    	var searchCount = data.searchCount;
			    	console.log(searchCount);
					get_List(searchViewNum, searchTitle);						// Set List
					paging_Set(searchViewNum, searchCount, searchTitle);		// Set PageList
			    }
			});
	    });
	    
	    $("#writeBtn").on("click", function(){	
	    	location.href = "/up/NoticeWrite.do";
	    });
	});

	//List Load
	function get_List(viewNum, search){											
		$.ajax({
			type: "POST"
		    , url: "/up/NoticeList.do"
    	    , data :{
		       pageNo : viewNum,
		       searchText : search
		      }
			, datatype: 'json'
	        , error : function(){
	            alert('error');
	        }
		    , success: function(res) {
		    	var data = res.list;

		    	list_Content(data);

		    }

		});
	}
	
	// ul - NoticeContent append
	function list_Content(data){
    	var str_noticeList = '';
    	$("#NoticeContent").empty();
	   	 $.each(data, function(i){	
	  		str_noticeList += ' <ul>';
	   		str_noticeList += '  <li> '+ data[i].bno +'</li>';	   		
	   		str_noticeList += '  <li><a href="/up/NoticeBoardDetail.do?bno=' +data[i].bno+ '">' + data[i].title +'</a></li>';
	   		str_noticeList += '  <li> '+ data[i].viewcnt +'</li>';
	   		str_noticeList += '  <li> '+ data[i].regdate +'</li>';
	   		str_noticeList += ' </ul>';
	   	 });
	   	$("#NoticeContent").append(str_noticeList);
	}
	
	//Create Paging System & PagingView append
	function paging_Set(viewNum, count, search){
		var viewPageNum = viewNum;
		viewPageNum *= 1;														// Viewing Page Number
		$("#pagingView").empty();
		var pageCount;
		var pageArray = new Array();											// Page Number Array Set
		pageCount = Math.floor((count+9)/10);									
		console.log(pageCount);
		var set_Paging = "";
		set_Paging += '<ul	class="paging" id="ul_page">';
																				// Bundle into 5 page
		if(viewPageNum > 5)														// PrevBtn Set				
			set_Paging += ' <li class="prev"><a href="javascript:paging_Set(' + (viewPageNum - 5) +','+count+',\''+search+'\');">이전</a></li>';
		
		if(pageCount<=5){
			for(var i = 0; i < pageCount; i++){
				pageArray[i]=i+1;
				set_Paging += ' <li class="on"><a href="javascript:get_List(' + pageArray[i] +',\''+search+'\');">' + pageArray[i] + '</a></li>';
			}
		}
		else if(pageCount>5){
			for(var i = 0; i <= Math.floor(pageCount/5); i++){
				pageArray[i] = new Array();
				
				if((i < Math.floor(pageCount/5)) && (i == Math.floor(viewPageNum/5))){
					for(var j = 0; j< 5; j++){
						pageArray[i][j] = (i * 5) + j + 1;
						set_Paging += ' <li class="on"><a href="javascript:get_List(' + pageArray[i][j] +',\''+search+ '\');">' + pageArray[i][j] + '</a></li>';
						
						if((j==4)&&(Math.floor(pageCount)!=pageArray[i][j]))	// NextBtn Set
							set_Paging += ' <li class="next"><a href="javascript:paging_Set(' + (viewPageNum + 5) + ','+count+',\''+search+ '\');">다음</a></li>';
					}
				}
				
				else if((i == Math.floor(pageCount/5)) && (i == Math.floor(viewPageNum/5))){
					for(var j = 0; j< pageCount%5; j++){
						pageArray[i][j] = (i*5) + j + 1;
						set_Paging += ' <li class="on"><a href="javascript:get_List(' + pageArray[i][j] +',\''+search+  '\');">' + pageArray[i][j] + '</a></li>';
					}
				}
			}
		}
		set_Paging += '</ul>'
		$("#pagingView").append(set_Paging);
	}

</script>
<section>
	<fieldset class="search">
		<input type="text" class="searchText" id="searchTitle" placeholder="Title">
		<button id = "searchBtn">검색</button>

	</fieldset>
	<div class="board noticeBoard">
        <ul class="boardTitle">
            <li>NO.</li>
            <li>Title</li>
            <li>View Count</li>
			<li>Creation Time</li>
        </ul>
		<div id="NoticeContent"></div>
    </div>
    <div id="pagingView"></div>
   	<div class="listBtn">
		<sec:authorize access="hasRole('ROLE_ADMIN')">
				<button id="adminBtn">Admin</button>
		</sec:authorize>
		<button id="writeBtn">Write</button>


		
	</div>
	<h3></h3>
</section>
