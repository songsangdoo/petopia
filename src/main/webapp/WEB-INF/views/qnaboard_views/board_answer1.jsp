<%@page import="com.petopia.board.qna.model.QnABoardFileTO"%>
<%@page import="java.util.List"%>
<%@page import="com.petopia.board.qna.model.QnABoardTO"%>
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
	
	QnABoardTO data = (QnABoardTO)request.getAttribute("data");
	List<QnABoardFileTO> file_list = (List)request.getAttribute("file_list");
	
	StringBuilder sbFileHtml = new StringBuilder();
	
	sbFileHtml.append("<div class='d-flex justify-content-left'>");
	sbFileHtml.append("<div id='carouselExampleFade' class='carousel slide carousel-fade'>");
	sbFileHtml.append("<div class='carousel-inner'>");
	
	for(int i = 0; file_list != null && i < file_list.size(); i++){
		if(i == 0){
			sbFileHtml.append("<div class='carousel-item active'>");
		}else{
			sbFileHtml.append("<div class='carousel-item'>");
		}
		sbFileHtml.append("<img src='../../upload/" + file_list.get(i).getQa_file_img_path() + "' class='d-block' width='100%' style='border-radius: 17px;'/>");
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
	
	int qa_seq = data.getQa_seq();
%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>펫토피아</title>
<meta content="" name="description">
<meta content="" name="keywords">
  
  <script type="text/javascript">
  	
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
			<h2>QNA</h2>
		</div>
		
		<div class="input-group mb-3">
			<span class="input-group-text">질 문</span>
			<div class="form-control" style="resize: none;">
			<div>
			<strong>
			[<%= data.getQa_category_name() %>]
			<br><br>
			<%= data.getQa_subject() %>
			</strong>
			</div>
			<br>
			<div>
			<%= data.getQa_content() %>
			</div>
			<br>
			<%= sbFileHtml %>
			</div>
		</div>
		
		<form action="qnaboard_answer_ok.do" method="post" name="wfrm" enctype="multipart/form-data">
		<input type="hidden" name="qa_seq" value="<%= qa_seq %>">
		<div class="input-group mb-3">
			<span class="input-group-text">답 변</span>
			<textarea class="form-control" name="content" rows="10" style="resize: none;"></textarea>
		</div>
		
		<div class="d-flex flex-row-reverse">
		<div class="p-2">
		<input type="button" value="목    록" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='qnaboard_list.do'" />
		<input class="btn btn-outline-primary" type="button" value="답변하기" id='wbtn'>
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