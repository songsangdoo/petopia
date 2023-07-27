<%@page import="com.petopia.board.qna.model.QnABoardTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");

	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	int m_seq = 0;
	
	QnABoardTO data = (QnABoardTO)request.getAttribute("data");
	
	int w_seq = data.getM_seq();
	
	int qa_seq = data.getQa_seq();
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
  			
  			if(document.wfrm.secret.value == 'Y'){
  				if(document.wfrm.password.value.trim() == ''){
	  				Swal.fire('비밀번호를 입력해주세요', '', 'warning');
  					return false;
  				}
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
  	
function changeSecret(){
	const secret = document.getElementById('secret');
	
	const password = document.getElementById('password');
	
	if(secret.value == 'Y'){
		password.style.display = '';
	}
	
	if(secret.value == 'N'){
		password.style.display = 'none';
	}
}
  </script>
  
</head>


<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	<script type="text/javascript">
	<%
	if( userData != null && userData.getM_seq().equals(String.valueOf(w_seq))){
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
			<h2>QNA</h2>
		</div>
		
		<form action="qnaboard_modify_ok.do" method="post" name="wfrm" enctype="multipart/form-data">
		<input type="hidden" name="qa_seq" value="<%= qa_seq %>">

			
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
			
			<div class='col-2'>
				<div class="input-group mb-3">
				  <label class="input-group-text" for="secret">비밀글</label>
				  <select class="form-select" name="secret" id='secret'  onchange='changeSecret()'>
					<c:if test='<%= data.getQa_secret().equals("Y") %>'>
					    <option value="Y" selected>YES</option>
					    <option value="N">NO</option>
					</c:if>
					<c:if test='<%= data.getQa_secret().equals("N") %>'>
					    <option value="Y">YES</option>
					    <option value="N" selected>NO</option>
					</c:if>
				  </select>
				</div>			
			</div>
			
			<div class='col-6'>
			<c:if test='<%= data.getQa_secret().equals("Y") %>'>
			<div class="input-group mb-3"  id='password' >
			<span class="input-group-text">비밀번호</span>
			<input type="password" class="form-control" name="password">
			</div>
			</c:if>
			<c:if test='<%= data.getQa_secret().equals("N") %>'>
			<div class="input-group mb-3"  id='password' style='display: none;'>
			<span class="input-group-text">비밀번호</span>
			<input type="password" class="form-control" name="password">
			</div>
			</c:if>
			</div>
			
			</div>
			
			<div class="input-group mb-3">
				<span class="input-group-text">제 목</span>
				<input type="text" class="form-control" name="subject" value="<%= data.getQa_subject() %>">
			</div>
			
		<div class="input-group mb-3">
			<span class="input-group-text">내 용</span>
			<textarea class="form-control" name="content" rows="10" style="resize: none;"><%= data.getQa_content() %></textarea>
		</div>
		
		<div class="input-group mb-3">
		  <label class="input-group-text" for="inputGroupFile01">이미지</label>
		  <input type="file" id="imgs" onchange="setThumbnail(event)" class="form-control" name="uploads" value="" accept="image/*" multiple>
		</div>
		
		<!-- image-thumbnail -->
		<div id="image-container"></div>
		
		<div class="d-flex flex-row-reverse">
		<div class="p-2">
		<input type="button" value="목    록" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='qnaboard_list.do'" />
		<input class="btn btn-outline-primary" type="button" value="작성하기" id='wbtn'>
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