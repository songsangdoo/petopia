<%@page import="com.petopia.model.MemberTO"%>
<%@page import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>
<%@page import="com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter"%>
<%@page import="com.petopia.board.information.model.InformationFileTO"%>
<%@page import="java.util.List"%>
<%@page import="com.petopia.board.information.model.InformationTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");
	
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	int m_seq = 0;
	
	if( userData != null){
		m_seq = Integer.parseInt(userData.getM_seq());
	}
	
	boolean isManager = false;
	if(userData != null && userData.getGrade_seq().equals("1")){
		isManager = true;
	}
	
	InformationTO data = (InformationTO)request.getAttribute("data");
	List<InformationFileTO> file_list = (List)request.getAttribute("file_list");
	
	int w_seq = data.getM_seq();
	
	boolean isWriter = false;
	
	if(w_seq == m_seq){
		isWriter = true;
	}
	
	String dt = data.getInfo_dt();
	String dt_formatted = String.format("%s-%s-%s %s:%s:%s", dt.substring(0, 4), dt.substring(4, 6), dt.substring(6, 8), dt.substring(8, 10), dt.substring(10, 12), dt.substring(12, 14));
	data.setInfo_dt(dt_formatted);
	
	int info_seq = data.getInfo_seq();
	
	StringBuilder sbFileHtml = new StringBuilder();
	
	if(file_list.size() != 0) {
		for(int i = 0; i < file_list.size(); i++){
			sbFileHtml.append("<div class='carousel-item'>");
			sbFileHtml.append("<img src='../../upload/" + file_list.get(i).getInfo_file_img_path() + "' class='d-block' width='100%' style='border-radius: 17px;'>");
			sbFileHtml.append("</div>");	
		}
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
	const recBtn = document.getElementById('recBtn');
	
	recBtn.onclick = function(){
		if(<%= userData == null%>){
			Swal.fire('로그인을 먼저 해주세요', '', 'warning');
			return false;
		}
		
		location.href='information_view_rec.do?info_seq=<%= info_seq %>';
	}

};

function post_del(info_seq){
	Swal.fire({
		  title: '게시물을 삭제하시겠습니까?',
		  text: '',
		  icon: 'question',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Ok'
		}).then((result) => {
		  if (result.isConfirmed) {
			  location.href='information_delete_ok.do?info_seq=' + info_seq;
		  }else{
			  return false;
		  }
	});
	  
}
	
</script>

</head>


<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	
	<main id="main">
		<section id="img-view">
		
		<div class="container mt-3"> 
			<div>	
			<!--게시판-->
				<div class='container' id="board-view" style='width:800px;'>
					<table class="table" >
					<tr>
						<td class="text-center" >
						<h4><%= data.getInfo_subject() %></h4>
						<div class="d-flex justify-content-end text-black">
						  <div class="p-2 bd-highlight">작성일 : <%= data.getInfo_dt() %></div>
						  <div class="p-2 bd-highlight">조회수 : <%= data.getInfo_hit() %></div>
						  <div class="p-2 bd-highlight">추천수 : <%= data.getInfo_rec_cnt() %></div>
						</div>
						</td>
					</tr>
					<tr>
						<td id="info-content">
							<div class="container">
							<div id="carouselExampleFade" class="carousel slide carousel-fade">
							  <div class="carousel-inner">
							    <div class="carousel-item active">
							      <img src="../../upload/<%= data.getInfo_rep_img_path() %>" class="d-block" width='100%' style="border-radius: 17px;">
							    </div>
							    <%= sbFileHtml %>
							  </div>
							  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
							    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
							    <span class="visually-hidden">Previous</span>
							  </button>
							  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
							    <span class="carousel-control-next-icon" aria-hidden="true"></span>
							    <span class="visually-hidden">Next</span>
							  </button>
							</div>
							</div>
							<div class="container board-info" style="width: 700px; word-break:break-all;">
							<p class="lead"><%= data.getInfo_content() %></p>
							</div>
						</td>
					</tr>			
					</table>
					
					<!-- 글 수정, 삭제버튼 -->
					<div class='cmt-btn-group' align="right">
						<button type="button" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='information_list.do'" >목	록</button>
						<c:if test="<%= !isManager %>">
						<button type='button' class="btn btn-outline-primary" id = 'recBtn'>추 천</button>
						</c:if>
						<c:if test="<%= isWriter || isManager %>">
							<button type="button" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='information_modify.do?info_seq=<%= info_seq %>'">수    정</button>
						</c:if>
						<c:if test="<%= isWriter || isManager %>">
							<button type="button" class="btn btn-outline-danger" style="cursor: pointer;" onclick="post_del(<%= info_seq %>)">삭	제</button>
						</c:if>
					</div>
					<!-- 글 수정, 삭제버튼 -->
					
					<br><br>
					
				</div>
				<!--//게시판-->
			</div>
		<!-- 하단 디자인 -->
		</div>
				
		</section>
	</main>
	<!-- <div id="right-nav" class="r-navbar">
	
	<!-- End #main -->

<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>

</html>