<%@page import="com.petopia.model.MemberTO"%>
<%@page import="com.petopia.board.information.model.InformationTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	int info_seq = Integer.parseInt(request.getParameter("info_seq"));
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	int m_seq = 0;
	
	InformationTO data = (InformationTO)request.getAttribute("data");
%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>펫토피아</title>
<meta content="" name="description">
<meta content="" name="keywords">
<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>
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
  			if(document.wfrm.subject.value.trim() == ''){
  				Swal.fire('제목을 입력해주세요', '', 'warning');
  				return false;
  			}
  			if(document.wfrm.content.value.trim() == ''){
  				Swal.fire('내용을 입력해주세요', '', 'warning');
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
  			})
  			
  		}
  		
  	};
</script>
</head>

<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	<script type="text/javascript">
	<%
	if( userData != null && userData.getGrade_seq().equals("1")){
		 m_seq = Integer.parseInt(userData.getM_seq());
	}else {
	%>
	Swal.fire('제한된 접근', '정상적인 경로를 이용해주세요', 'error').then(function(){history.back();});
	<%		
		}
	%>
	</script>
	<main id="main">
		<section>
		<div class="container w-75 mt-3">

		<div class="section-title">
			<h2>지식정보</h2>
		</div>
		
		<form action="information_modify_ok.do" method="post" name="wfrm" enctype="multipart/form-data">
		<input type="hidden" name="info_seq" value=<%= info_seq %>>
		<div class="input-group mb-3">
			<span class="input-group-text">제 목</span>
			<input type="text" class="form-control" name="subject" value="<%= data.getInfo_subject() %>">
		</div>

		<div class="input-group mb-3">
			<span class="input-group-text">내 용</span>
			<textarea class="form-control" name="content" rows="10" style="resize: none;"><%= data.getInfo_content().replaceAll("<br>", "\n") %></textarea>
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
			<input class="btn btn-outline-primary" type="button" value="돌아가기" onclick="history.back()">
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