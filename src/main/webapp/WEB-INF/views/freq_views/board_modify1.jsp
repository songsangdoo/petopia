<%@page import="com.petopia.board.freq.model.QnABoardFreqTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");

	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	int m_seq = 0;
	
	QnABoardFreqTO data = (QnABoardFreqTO)request.getAttribute("data");
	
	int qa_freq_seq = data.getQa_freq_seq();
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
		<section id="img-write">
		
		<div class="container w-75 mt-3">

		<div class="section-title">
			<h2>faq</h2>
		</div>
		
		<form action="freq_modify_ok.do" method="post" name="wfrm" enctype="multipart/form-data">
		<input type="hidden" name="qa_freq_seq" value="<%= qa_freq_seq %>">

		<div class='row'>
			
			<div class='col-4'>
				<div class="input-group mb-3">
				  <label class="input-group-text" for="category">분류</label>
				  <select class="form-select" name="category" id='category'>
				  	<c:if test='<%= data.getQa_category_name().equals("회원관련")%>'>
				    <option value="1" selected>회원관련</option>
				    <option value="2">커뮤니티</option>
				    <option value="3">사이트 이용</option>
				    <option value="4">이벤트/포인트</option>
				    <option value="5">기타</option>
				  	</c:if>
							
				  	<c:if test='<%= data.getQa_category_name().equals("커뮤니티")%>'>
				    <option value="1">회원관련</option>
				    <option value="2" selected>커뮤니티</option>
				    <option value="3">사이트 이용</option>
				    <option value="4">이벤트/포인트</option>
				    <option value="5">기타</option>
				  	</c:if>
							
				  	<c:if test='<%= data.getQa_category_name().equals("사이트 이용")%>'>
				    <option value="1">회원관련</option>
				    <option value="2">커뮤니티</option>
				    <option value="3" selected>사이트 이용</option>
				    <option value="4">이벤트/포인트</option>
				    <option value="5">기타</option>
				  	</c:if>
							
				  	<c:if test='<%= data.getQa_category_name().equals("이벤트/포인트")%>'>
				    <option value="1">회원관련</option>
				    <option value="2">커뮤니티</option>
				    <option value="3">사이트 이용</option>
				    <option value="4" selected>이벤트/포인트</option>
				    <option value="5">기타</option>
				  	</c:if>
							
				  	<c:if test='<%= data.getQa_category_name().equals("기타")%>'>
				    <option value="1">회원관련</option>
				    <option value="2">커뮤니티</option>
				    <option value="3">사이트 이용</option>
				    <option value="4">이벤트/포인트</option>
				    <option value="5" selected>기타</option>
				  	</c:if>
							
				  </select>
				</div>			
			</div>
			
		</div>
	
		<div class="input-group mb-3">
			<span class="input-group-text">제 목</span>
			<input type="text" class="form-control" name="subject" value="<%= data.getQa_freq_subject()%>">
		</div>
			
		<div class="input-group mb-3">
			<span class="input-group-text">내 용</span>
			<textarea class="form-control" name="content" rows="10" style="resize: none;"><%= data.getQa_freq_content().replaceAll("<br>", "\n") %></textarea>
		</div>
		
		<div class="d-flex flex-row-reverse">
		<div class="p-2">
		<input type="button" value="목 록" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='freq_list.do'" />
		<input class="btn btn-outline-primary" type="button" value="수정하기" id='wbtn'>
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