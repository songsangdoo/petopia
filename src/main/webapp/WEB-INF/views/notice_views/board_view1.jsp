<%@page import="com.petopia.board.notice.model.NoticeFileTO"%>
<%@page import="com.petopia.board.notice.model.NoticeTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@page import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>
<%@page import="com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");
	
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	int m_seq = 0;
	
	boolean isManager = false;
	
	if(userData != null){
		m_seq = Integer.parseInt(userData.getM_seq());
		isManager = (userData.getGrade_seq().equals("1")? true: false);
	}
	
	NoticeTO data = (NoticeTO)request.getAttribute("data");
	List<NoticeFileTO> file_list = (List)request.getAttribute("file_list");
	
	String dt = data.getN_dt();
	String dt_formatted = String.format("%s-%s-%s %s:%s:%s", dt.substring(0, 4), dt.substring(4, 6), dt.substring(6, 8), dt.substring(8, 10), dt.substring(10, 12), dt.substring(12, 14));
	data.setN_dt(dt_formatted);
	 
	int n_seq = data.getN_seq();
	
	StringBuilder sbFileHtml = new StringBuilder();
	
	if(file_list.size() != 0) {
		for(int i = 0; i < file_list.size(); i++){
			if(i == 0){
				sbFileHtml.append("<div class='carousel-item active'>");
			}else{
				sbFileHtml.append("<div class='carousel-item'>");
			}
			sbFileHtml.append("<img src='../../upload/" + file_list.get(i).getN_file_img_path() + "' class='d-block' width=100% style='border-radius: 17px;'>");
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

<!-- Favicons -->
<link href="assets/img/favicon.png" rel="icon">
<link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Jost:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Nunito:wght@400;700&display=swap"
	rel="stylesheet">

<!-- Vendor CSS Files -->
<link href="assets/vendor/aos/aos.css" rel="stylesheet">
<link href="assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="assets/vendor/bootstrap-icons/bootstrap-icons.css"
	rel="stylesheet">
<link href="assets/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet">
<link href="assets/vendor/glightbox/css/glightbox.min.css"
	rel="stylesheet">
<link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
<link href="assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
<link rel='stylesheet'
	href='https://cdnjs.cloudflare.com/ajax/libs/owl-carousel/1.3.3/owl.carousel.min.css'>
<link rel='stylesheet'
	href='https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>

<!-- Template Main CSS File -->
<link href="assets/css/new.css" rel="stylesheet">


<!-- =======================================================
  * Template Name: Arsha
  * Updated: May 30 2023 with Bootstrap v5.3.0
  * Template URL: https://bootstrapmade.com/arsha-free-bootstrap-html-template-corporate/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
<script type="text/javascript">
function post_del(n_seq){
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
			  location.href='notice_delete_ok.do?n_seq=' + n_seq;
		  }else{
			  return false;
		  }
	});
	  
}
</script>  
</head>


<body>

	<!-- 헤더 -->
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	<!-- // 헤더 -->
	
	<main id="main">
		<section id="img-view">
		
		<div class="container mt-3"> 
			<div>	
			<!--게시판-->
				<div class='container' id="board-view" style='width:800px;'>
					<table class="table">
					<tr>
						<td class="text-center">
						<h4><%= data.getN_subject() %></h4>
						<div class="d-flex justify-content-end text-black">
						  <div class="p-2 bd-highlight">작성자 : 관리자</div>
						  <div class="p-2 bd-highlight">작성일 : <%= data.getN_dt() %></div>
						  <div class="p-2 bd-highlight">조회수 : <%= data.getN_hit() %></div>
						</div>
						</td>
					</tr>
					<tr>
						<td id="info-content">
							<br>
							<div class="container">
							<div id="carouselExampleFade" class="carousel slide carousel-fade">
							  <div class="carousel-inner">
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
							<div class="container" style="width: 700px; word-break:break-all;">
							<p class="lead"><%= data.getN_content() %></p>
							</div>
							<br>
						</td>
					</tr>			
					</table>
					
					<!-- 글 수정, 삭제버튼 -->
					<div class='cmt-btn-group' align="right">
						<button type="button" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='notice_list.do'" >목	록</button>
						<c:if test="<%= isManager %>">
							<button type="button" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='notice_modify.do?n_seq=<%= n_seq %>'">수    정</button>
						</c:if>
						<c:if test="<%= isManager %>">
							<button type="button" class="btn btn-outline-danger" style="cursor: pointer;" onclick="post_del(<%= n_seq %>)">삭	제</button>
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