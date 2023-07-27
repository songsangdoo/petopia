<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	int m_seq = 0;
	
	if( userData != null && userData.getGrade_seq().equals("1")){
		m_seq = Integer.parseInt(userData.getM_seq());
	}
	
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
  			})
  			
  		}
  		
  	};
  </script>
  
</head>


<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	<script type="text/javascript">
	<%
		if(userData == null || !userData.getGrade_seq().equals("1")){
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
			<h2>FAQ</h2>
		</div>
		
		<form action="freq_write_ok.do" method="post" name="wfrm" enctype="multipart/form-data">
		<input type="hidden" name="m_seq" value="<%= m_seq %>">
		
		<div class='row'>
			
			<div class='col-4'>
				<div class="input-group mb-3">
				  <label class="input-group-text" for="category">분 류</label>
				  <select class="form-select" name="category" id='category'>
				    <option value="1" selected>회원관련</option>
				    <option value="2">커뮤니티</option>
				    <option value="3">사이트 이용</option>
				    <option value="4">이벤트/포인트</option>
				  </select>
				</div>			
			</div>
			
		</div>
		
		<div class="input-group mb-3">
			<span class="input-group-text">제 목</span>
			<input type="text" class="form-control" name="subject">
		</div>
			
		<div class="input-group mb-3">
			<span class="input-group-text">내 용</span>
			<textarea class="form-control" name="content" rows="10" style="resize: none;"></textarea>
		</div>
		
		<div class="d-flex flex-row-reverse">
		<div class="p-2">
		<input type="button" value="목    록" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='freq_list.do'" />
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