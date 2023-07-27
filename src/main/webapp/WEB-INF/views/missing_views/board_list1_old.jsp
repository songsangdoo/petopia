<%@page import="com.petopia.board.missing.model.MissingTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");

List<MissingTO> datas = (List)request.getAttribute("datas");


int cpage = 1;
if (request.getParameter("cpage") != null) {
	cpage = Integer.parseInt(request.getParameter("cpage"));
}

int recordsPerpage = 9;

int totalRecords = datas.size();

int totalPage = (totalRecords - 1) / recordsPerpage + 1;

int startNum = (cpage - 1) * recordsPerpage;

int pagePerBlock = 5;

StringBuilder sbHtml = new StringBuilder();
int count = 0;
for (int i = startNum; i < datas.size() && i < startNum + recordsPerpage; i++) {
	++count;
	if (count % 4 == 1) {
		sbHtml.append("<div class='row' data-aos='fade-up' data-aos-delay='200'>");
	}
	sbHtml.append("<div class='col-3'>");
	sbHtml.append("<div class='card' style='width: 18rem;'>");
	sbHtml.append("<a href='missing_view.do?mr_seq=" + datas.get(i).getMr_seq() + "'>");
	sbHtml.append("<img src='../../upload/" + datas.get(i).getMr_rep_img() + "' class='card-img-top' style='height: 20rem; object-fit: cover;'>");
	sbHtml.append("<ul class='list-group list-group-flush'>");
	sbHtml.append("<li class='list-group-item'>이름 : " + datas.get(i).getMr_subject() + "</li>");
	sbHtml.append("<li class='list-group-item'>잃어버린 날짜 : " + datas.get(i).getMr_l_date() + "</li>");
	sbHtml.append("<li class='list-group-item'>잃어버린 지역 : " + datas.get(i).getMr_l_location() + "</li>");
	sbHtml.append("</ul>");
	sbHtml.append("</a>");
	sbHtml.append("</div>");
	sbHtml.append("</div>");

	if (count % 4 == 0) {
		sbHtml.append("</div><br><br>");
	}
}

if (count % 4 != 0) {
	sbHtml.append("</div><br><br>");
}


StringBuilder pageSbHtml = new StringBuilder();

int startBlock = cpage - (cpage - 1) % pagePerBlock;
int endBlock = startBlock + pagePerBlock - 1;

if(endBlock >= totalPage){
	endBlock = totalPage;
}

pageSbHtml.append("<nav>");
pageSbHtml.append("<ul class='pagination justify-content-center'>");
if(cpage == 1){
	pageSbHtml.append("<li class='page-item'><a class='page-link'> &lt; </a></li>");
}else{
	pageSbHtml.append("<li class='page-item'><a class='page-link' href='info_list.do?cpage=" + (cpage - 1) + "'> &lt; </a></li>");
}
for(int i = startBlock; i <= endBlock; i++){
	if(i == cpage){
		pageSbHtml.append("<li class='page-item'><a class='page-link'>" + i + "</a></li>");
	} else{
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='info_list.do?cpage=" + i + "'>" + i + "</a></li>");
	}
}

if(cpage == totalPage){
	pageSbHtml.append("<li class='page-item'><a class='page-link'> &gt; </a></li>");
}else{
	pageSbHtml.append("<li class='page-item'><a class='page-link' href='info_list.do?cpage=" + (cpage + 1) + "'> &gt; </a></li>");
}
pageSbHtml.append("</ul>");
pageSbHtml.append("</nav>");

%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>펫토피아</title>
<meta content="" name="description">
<meta content="" name="keywords">

</head>

<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>

	<!-- ======= Hero Section ======= -->
	<section id="hero" class="d-flex align-items-center">
		<div class="container">
			<!-- Swiper -->
			<div class="swiper mySwiper">
				<div class="swiper-wrapper">
					<div
						class="col-lg-12 d-flex flex-column justify-content-center pt-4 pt-lg-0 order-2 order-lg-1 with-background"
						data-aos="fade-up" data-aos-delay="200">
						<div class="image-container">
							<img src="WEB-APP/assets/img/gif/doridori2.gif" alt="이미지 설명">
						</div>
					</div>
					<div class="swiper-slide">
						<div class="row">
							<div
								class="col-lg-6 d-flex flex-column justify-content-center pt-4 pt-lg-0 order-2 order-lg-1"
								data-aos="fade-up" data-aos-delay="200">
								<h1>반려 동물과 함깨하는 커뮤니티</h1>
								<h2>내 자식같은 귀여운 나만의 반려동물에 대해서 이야기를 나누어보아요</h2>
								<div
									class="d-flex justify-content-center justify-content-lg-start">
									<a href="#about" class="btn-get-started scrollto">공식 유튜브</a> <a href="https://www.youtube.com/watch?v=jDDaplaOz7Q"
										class="glightbox btn-watch-video"><i
										class="bi bi-play-circle"></i><span>Watch Video</span></a>
								</div>
							</div>
							<div class="col-lg-6 order-1 order-lg-2 hero-img"
								data-aos="zoom-in" data-aos-delay="200">
								<img src="https://img.freepik.com/premium-photo/golden-retriever-sitting-in-front-of-a-white-wall_191971-19321.jpg?w=900" class="img-fluid animated"
									alt="">
							</div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="row">
							<div
								class="col-lg-12 d-flex flex-column justify-content-center pt-4 pt-lg-0 order-2 order-lg-1"
								data-aos="fade-up" data-aos-delay="200">
								<img src="assets/img/gif/doridori2.gif">
							</div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="row">
							<div
								class="col-lg-12 d-flex flex-column justify-content-center pt-4 pt-lg-0 order-2 order-lg-1"
								data-aos="fade-up" data-aos-delay="200">
								<img src="assets/img/gif/doridori2.gif">
							</div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="row">
							<div
								class="col-lg-12 d-flex flex-column justify-content-center pt-4 pt-lg-0 order-2 order-lg-1"
								data-aos="fade-up" data-aos-delay="200">
								<img src="assets/img/gif/doridori2.gif">
							</div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="row">
							<div
								class="col-lg-12 d-flex flex-column justify-content-center pt-4 pt-lg-0 order-2 order-lg-1"
								data-aos="fade-up" data-aos-delay="200">
								<img src="assets/img/gif/doridori2.gif">
							</div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="row">
							<div
								class="col-lg-12 d-flex flex-column justify-content-center pt-4 pt-lg-0 order-2 order-lg-1"
								data-aos="fade-up" data-aos-delay="200">
								<img src="assets/img/gif/doridori2.gif">
							</div>
						</div>
					</div>
					
				</div>
				<div class="swiper-pagination"></div>
				<div class="swiper-button-next"></div>
				<div class="swiper-button-prev"></div>
			</div>
		</div>

	</section>

	<!-- End Hero -->

	<main id="main">
		<section id="portfolio" class="portfolio">
			<div class="container" data-aos="fade-up">
				<div class="section-title">
					<h2>실종 신고</h2>
				</div>
				<%= sbHtml %>
			
				<hr class="container w-80">

				<!-- 버튼 -->	
				<div align="right">
					<input type="button" value="실종 신고" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='info_write.do'" />
				</div>
				<!-- 버튼 끝 -->	
			</div>
			
			<br>
			
			<!--=== 페이지네이션 ===-->
			<%= pageSbHtml %>
			<!--=== 페이지네이션 끝===-->
			
		</section>
		<!-- End Portfolio Section -->
	</main>

	<!-- <div id="right-nav" class="r-navbar">
	
	<!-- End #main -->

<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
</body>

</html>