<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	System.out.print(userData);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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
<link rel="stylesheet" href="assets/css/hero.css" />
<link rel="stylesheet" href="assets/css/menu.css" />
<link href="assets/css/new.css" rel="stylesheet"> 
</head>
<body>
	<!-- ======= Header 시작 ======= -->
	<header class="header_area ">
		<div class="main_header_area animated">
			<div class="container">
				<nav id="navigation1" class="navigation">
					<!-- Logo Area Start -->
					<div class="nav-header">
						<a class="nav-brand" href="index.do"><strong>PETOPIA</strong></a>
						<div class="nav-toggle"></div>
					</div>
					<div class="nav-menus-wrapper">
						<ul class="nav-menu align-to-right">
							<li><a href="#">펫토피아</a>
										<ul class="nav-dropdown">
											<li><a href="introduce.do">펫토피아란?</a></li>
											<li><a href="guide.do">서비스 가이드</a></li>
										</ul></li>
							<li><a href="#">콘텐츠</a>
								<ul class="nav-dropdown">
											<li><a href="notice_list.do">공지사항</a></li>
											<li><a href="trip.do">반려 여행</a></li>
											<li><a href="animalHospital.do">동물병원 찾기</a></li>
											<li><a href="abandonment.do">유기동물 찾기</a></li>
										</ul></li>
							<li><a href="information_list.do">지식정보</a></li>
							<li><a href="#">커뮤니티</a>
								<div class="megamenu-panel">
									<div class="megamenu-lists">
										<ul class="megamenu-list list-col-5">
											<li class="megamenu-list-title"><a href="#">자유 주제</a></li>
											<li><a href="freeboard_list.do">자유게시판</a></li>
											<li><a href="album_list.do">엘범게시판</a></li>
										</ul>
										<ul class="megamenu-list list-col-5">
											<li class="megamenu-list-title"><a href="index-minimal.html#">유저 소통</a></li>
											<li><a href="question_list.do">질문게시판</a></li>
											<li><a href="tipboard_list.do">팁게시판</a></li>
										</ul>
										<ul class="megamenu-list list-col-5">
											<li class="megamenu-list-title"><a href="missing_list.do">실종신고</a></li>
											<li><a href="missing_list.do">실종신고 게시판</a></li>
										</ul>
									</div>
								</div></li>
							<li><a href="group_list.do">동아리</a></li>
							<li><a href="point_shop_info.do">포인트샵</a>
										<ul class="nav-dropdown">
											<li><a href="point_shop_info.do">포인트란?</a></li>
											<li><a href="point_shop_badge.do">뱃지샵</a></li>
											<li><a href="point_shop_skin.do">스킨샵</a></li>
							</ul></li>
							<li><a href="qnaboard_list.do">QNA</a>
										<ul class="nav-dropdown">
											<li><a href="freq_list.do">자주묻는질문</a></li>
											<li><a href="qnaboard_list.do">QnA</a></li>
							</ul></li>
							<li class="userinfo"><a href="#">
										<img src="https://via.placeholder.com/100" alt="User Image">
    									<span class="user-name"><%=userData == null ? "로그인을 진행해주세요" : userData.getM_nickname() %></span>
										</a>
										<% if(userData != null) { %>
										<ul class="nav-dropdown">
											<% if(userData.getGrade_seq().equals("1")) {%>
											<li><a href="master_userboard.do">관리자페이지</a></li>
											<% } %>
											<li><a href="mypage.do?seq=<%=userData.getM_seq() %>">마이페이지</a></li>
										</ul>
									<% } %>
								</li>
							</ul>
						</div>
					<div class='toggle <%=userData == null ? "" : "toggle-on" %>' id='bl'
					  toggle-div
					  toggle-class= ''
					  toggle-active= ''>
					  <div class='toggle-text-off'>로그인</div>
					  <div class='glow-comp'></div>
					  <div class='toggle-button'></div>
					  <div class='toggle-text-on'>로그아웃</div>
					</div>
				</nav>
			</div>
		</div>
	</header>
	<!-- ======= Header 끝 ======= -->
</body>
</html>