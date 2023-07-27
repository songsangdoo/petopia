<%@page import="com.petopia.model.MemberTO"%>
<%@page import="com.petopia.board.missing.model.MissingTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");

	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	MissingTO data = (MissingTO)request.getAttribute("data");

	int w_seq = data.getM_seq();
	
	String[] missingLocArr = data.getMs_missing_loc().split("&&&");
%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>
<title>펫토피아</title>
<meta content="" name="description">
<meta content="" name="keywords">

  <script type="text/javascript">
  	function setRepThumbnail(event) {
  		var rep_img_container = document.getElementById("rep-image-container");
  		
  		rep_img_container.innerHTML = "";
  		
  		var reader = new FileReader();
  		
  		reader.onload = function(event) {
  			var img = document.createElement("img");
  			
  			img.setAttribute("src", event.target.result);
			img.setAttribute("height", "150px");
			img.setAttribute("style", "display:inline-block; margin: 2px; padding: 2px")
			
			rep_img_container.appendChild(img);
  		};
  		
  		reader.readAsDataURL(event.target.files[0]);
  	}
  
  	function setThumbnail(event) {
  		var img_container = document.getElementById("image-container");	
  		
  		img_container.innerHTML = "";
  		
		for(var image of event.target.files) {
			var reader = new FileReader();
			
			reader.onload = function(event) {
				var img = document.createElement("img");
				img.setAttribute("src", event.target.result);
				img.setAttribute("height", "150px");
				img.setAttribute("style", "display:inline-block; margin: 2px; padding: 2px")
				
				document.getElementById("image-container").appendChild(img);
			};
			
			reader.readAsDataURL(image)
		}
		
  	}
  	
  	window.onload = function(){
  		const wbtn = document.getElementById('wbtn');
  		
  		wbtn.onclick = function(){
  			if(document.wfrm.pet_name.value.trim() == ''){
  				Swal.fire('반려동물의 이름을 입력해주세요', '', 'warning');
  				return false;
  			}
  			if(document.wfrm.pet_age.value.trim() == ''){
  				Swal.fire('반려동물의 나이를 확인해주세요', '', 'warning');
  				return false;
  			}
  			if(document.wfrm.missing_date.value == ''){
  				Swal.fire('실종된 날짜를 확인해주세요', '', 'warning');
				return false;
  			}
  			if(document.wfrm.missingLoc.value.trim() == ''){
  				Swal.fire('실종된 장소를 입력해주세요', '', 'warning');
  				return false;
  			}
  			if(document.wfrm.subject.value.trim() == ''){
  				Swal.fire('제목을 입력해주세요', '', 'warning');
  				return false;
  			}
  			if(document.wfrm.content.value.trim() == ''){
  				Swal.fire('반려동물의 특징을 적어주세요', '', 'warning');
  				return false;
  			}
  			
  			Swal.fire({
    			  title: '수정을 완료하시겠습니까?',
    			  text: '',
    			  icon: 'question',
    			  showCancelButton: true,
    			  confirmButtonColor: '#3085d6',
    			  cancelButtonColor: '#d33',
    			  confirmButtonText: 'Ok'
    			}).then((result) => {
    			  if (result.isConfirmed) {
  			      document.wfrm.submit();
    			  }else{
    				  return false;
    			  }
    		});
  			
  		}
  		
  	};
  	
  </script>
  
</head>


<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	<script type="text/javascript">
	<%
	if( userData != null && userData.getM_seq().equals(String.valueOf(w_seq))){
		int	m_seq = Integer.parseInt(userData.getM_seq());
	}else {
	%>
	Swal.fire('제한된 접근', '정상적인 경로를 이용해주세요', 'error').then(function(){history.back();});
	<%		
		}
	%>
	</script>
	<main id="main">
		<section id="img-write">
		
		<div class="container w-75 mt-3">

		<div class="section-title">
			<h2>실 종 신 고</h2>
		</div>
		
		<form action="missing_modify_ok.do" method="post" name="wfrm" enctype="multipart/form-data">
		<input type="hidden" name="ms_seq" value=<%= data.getMs_seq() %>>
		<div class="row">
		
		<div class="input-group mb-3">
			<span class="input-group-text">이 름</span>
			<input type="text" class="form-control" name="pet_name" value=<%= data.getMs_pet_name() %>>
		</div>
		
		</div>	
		
		<div class="row">
		<div class="col-4">
			<div class="input-group mb-3">
			  <label class="input-group-text" for="pet_gender">성 별</label>
			  <select class="form-select" id="pet_gender" name="pet_gender">
			  <c:if test='<%= data.getMs_pet_gender().equals("M") %>'>
			    <option value="M" selected>Male</option>
			    <option value="F">Female</option>
			  </c:if>
			  <c:if test='<%= data.getMs_pet_gender().equals("F") %>'>
			    <option value="M">Male</option>
			    <option value="F" selected>Female</option>
			  </c:if>
			  </select>
			</div>
		</div>
		
		<div class="col-4">
		<div class="input-group mb-3">
			<span class="input-group-text">나 이</span>
			<input type="number" class="form-control" name="pet_age" value=<%= data.getMs_pet_age() %> min="0">
		</div>
		</div>
		
		<div class="col-4">
		
		<div class="input-group mb-3">
		  <label class="input-group-text" for="open_phone">연락처 공개</label>
		  <select class="form-select" id="open_phone" name="open_phone">
		  	<c:if test='<%= data.getMs_open_phone().equals("Y") %>'>
			    <option value="Y" selected>YES</option>
			    <option value="N">NO</option>
		  	</c:if>
		  	<c:if test='<%= data.getMs_open_phone().equals("N") %>'>
			    <option value="Y">YES</option>
			    <option value="N" selected>NO</option>
		  	</c:if>
		  </select>
		</div>
		</div>
		</div>
		
		<div class="row">
		
		<div class="input-group mb-3">
			<span class="input-group-text">실종 날짜</span>
			<input type="date" class="form-control" name="missing_date" value=<%= data.getMs_missing_date() %>>
		</div>	
		
		</div>
		
		<div class="row">
		<div class="input-group mb-1" id="ms-loc">
		  <span class="input-group-text">실종 지역</span>
		  <input type="text" name="missingLoc"  class="form-control" placeholder="클릭하시면 주소 검색 페이지가 열립니다" value="<%= missingLocArr[0] %>">
		</div>
		<div class="input-group mb-3">
		  <input type="text" name="missingLoc2"  class="form-control" placeholder="상세주소를 입력해주세요" value='<%= (missingLocArr.length == 2? missingLocArr[1]: "") %>'>
		</div>
		</div>
				
		<!-- 다음 우편번호 api -->
		
		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<script>
		
			document.getElementById("ms-loc").onclick = function(){
				
			    new daum.Postcode({
			        oncomplete: function(data) {
						let fullAddr = '';
						let extraAddr = '';

						if(data.userSelectedType === 'R'){
							fullAddr = data.roadAddress;
						}else{
							fullAddr = data.jibunAddress;
						}
						
						if(data.userSelectedType === 'R'){
							if(data.bname !== ''){
								extraAddr += data.bname;
							}
							if(data.buildingName !== ''){
								extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName); 
							}
							
							fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
						}
						
						document.wfrm.missingLoc.value = fullAddr;
			        }
			    }).open();
			}
		</script>
		
		<!-- // 다음 우편번호 api -->

		<div class="input-group mb-3">
			<span class="input-group-text">제 목</span>
			<input type="text" class="form-control" name="subject" value="<%= data.getMs_subject()%>">
		</div>
		
		<div class="input-group mb-3">
			<span class="input-group-text">내 용</span>
			<textarea class="form-control" name="content" style="resize: none;" rows="10"><%= data.getMs_content().replaceAll("<br>", "\n") %></textarea>
		</div>
		
		<div class="input-group mb-3">
		  <label class="input-group-text" for="inputGroupFile01">대표 이미지</label>
		  <input type="file" id="rep-img" onchange="setRepThumbnail(event)" class="form-control" name="rep_upload" value="" accept="image/*">
		</div>

		<!-- rep-image-thumbnail -->
		<div id="rep-image-container"></div>
		
		<br>
		
		<div class="input-group mb-3">
		  <label class="input-group-text" for="inputGroupFile01">이미지</label>
		  <input type="file" id="imgs" onchange="setThumbnail(event)" class="form-control" name="uploads" value="" accept="image/*" multiple>
		</div>
		
		<!-- image-thumbnail -->
		<div id="image-container"></div>
		
		<div class="d-flex flex-row-reverse">
		<div class="p-2">
		<input class="btn btn-outline-primary" type="button" value="수정하기" id='wbtn'>
		<input type="button" value="돌아가기" class="btn btn-outline-primary" style="cursor: pointer;" onclick="history.back()" />
		</div>
		</div>
		</form>
		
		</div>
		</section>
	</main>

	<!-- <div id="right-nav" class="r-navbar">
	
	<!-- End #main -->

<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>

</html>