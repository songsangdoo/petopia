<%@page import="com.petopia.board.question.model.QuestionFileTO"%>
<%@page import="com.petopia.board.question.model.QuestionTO"%>
<%@page import="java.util.List"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	int m_seq = 0;
	
	if( userData != null){
		m_seq = Integer.parseInt(userData.getM_seq());
	}
	
	QuestionTO data = (QuestionTO)request.getAttribute("data");
	List<QuestionFileTO> file_list = (List)request.getAttribute("file_list");
	
	StringBuilder sbFileHtml = new StringBuilder();
	
	sbFileHtml.append("<div class='d-flex justify-content-left' style='width: 400px;'>");
	sbFileHtml.append("<div id='carouselExampleFade' class='carousel slide carousel-fade'>");
	sbFileHtml.append("<div class='carousel-inner'>");
	
	for(int i = 0; file_list != null && i < file_list.size(); i++){
		if(i == 0){
			sbFileHtml.append("<div class='carousel-item active'>");
		}else{
			sbFileHtml.append("<div class='carousel-item'>");
		}
		sbFileHtml.append("<img src='../../upload/" + file_list.get(i).getQ_file_img_path() + "' class='d-block' width='100%' style='border-radius: 17px;'/>");
		sbFileHtml.append("</div>");	
	}
	
	sbFileHtml.append("</div>");
	sbFileHtml.append("<button class='carousel-control-prev' type='button' data-bs-target='#carouselExampleFade' data-bs-slide='prev'>");
	sbFileHtml.append("<span class='carousel-control-prev-icon' aria-hidden='true'></span>");
	sbFileHtml.append("<span class='visually-hidden'>Previous</span>");
	sbFileHtml.append("</button>");
	sbFileHtml.append("<button class='carousel-control-next' type='button' data-bs-target='#carouselExampleFade' data-bs-slide='next'>");
	sbFileHtml.append("<span class='carousel-control-next-icon' aria-hidden='true'></span>");
	sbFileHtml.append("<span class='visually-hidden'>Next</span>");
	sbFileHtml.append("</button>");
	sbFileHtml.append("</div>");
	sbFileHtml.append("</div>");
	
	int q_seq = data.getQ_seq();
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
			if(document.wfrm.content.value.trim() == ''){
				Swal.fire('내용을 입력해주세요', '', 'warning');
				return false;
			}
			
			Swal.fire({
			  title: '작성을 완료하시겠습니까?',
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
		if(userData == null){
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
			<h2>질문 게시판</h2>
		</div>
		
		<div class="input-group mb-3">
			<span class="input-group-text">질 문</span>
			<div class="form-control" style="resize: none;">
			<div>
			<strong>
			<%= data.getQ_subject() %>
			</strong>
			</div>
			<br>
			<div>
			<%= data.getQ_content() %>
			</div>
			<br>
			<%= sbFileHtml %>
			</div>
		</div>
		
		<form action="question_ans_write_ok.do" method="post" name="wfrm" enctype="multipart/form-data">
		<input type="hidden" name="q_seq" value="<%= q_seq %>">
		<div class="input-group mb-3">
			<span class="input-group-text">답 변</span>
			<textarea class="form-control" name="content" rows="10" style="resize: none;"></textarea>
		</div>
		
		<div class="input-group mb-3">
		  <label class="input-group-text" for="inputGroupFile01">이미지</label>
		  <input type="file" id="imgs" onchange="setThumbnail(event)" class="form-control" name="uploads" value="" accept="image/*" multiple>
		</div>
		
		<!-- image-thumbnail -->
		<div id="image-container"></div>
		
		<div class="d-flex flex-row-reverse">
		<div class="p-2">
		<input class="btn btn-outline-primary" type="button" value="답변하기" id='wbtn'>
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