# NoticeBoard

## 개발 과정

### Web 개발을 위한 Spring MVC 모델의 게시판 app 구상
### 해당 app에 대한 개발계획서 작성
### app의 기본적인 구성 및 아키텍쳐 작성
### 기본 CRUD 기능 외 주입할 부가적인 기능 및 서비스에 대한 Table 구성
### 관계에 따른 테이블 정의서 작성
### 준비한 프로젝트 기초 설정과 세부 패키징 및 부가 기능에 따른 필요 jar 주입
### 페이지 스타일 구성
### 임시 생성한 게시글에 대한 조회 기능 및 페이징 기능 추가
### 하단 페이징 기능을 위한 로직 일부  첨부
 - 부족한 JS 관련 능력 향상을 위한 front 에서의 logic 구현을 통한 처리
 
 ```
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
 ```
### RestAPI에 대한 학습 
### json 자료형에 대한 이해
### 일반 컨트롤러의 jsonview 기능에 대한 이해
### Ajax를 통한 동적인 페이지 표현에 대한 학습 및 해당 기능 적용
### Spring Framework를 보다 유연하게 사용하기 위해 해당 구조에 대한 재차 학습 실시
### 게시판 상세 기능 추가 및 로그인 기능 추가를 위한 준비
### 게시글 검색 기능 추가
 
 