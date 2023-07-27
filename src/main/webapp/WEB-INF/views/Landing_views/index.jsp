<%@page import="com.petopia.board.album.model.AlbumTO"%>
<%@page import="com.petopia.board.information.model.InformationTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	List<InformationTO> info_datas = (List)request.getAttribute("info_datas");
	List<AlbumTO> album_datas = (List)request.getAttribute("album_datas");
	
	StringBuilder sbInfo = new StringBuilder();
	if(info_datas != null){
		for(int i = 0; i < info_datas.size(); i++){
			String dt = info_datas.get(i).getInfo_dt();
			String dt_formatted = dt.substring(0, 4) + "-" + dt.substring(4, 6) + "-" + dt.substring(6, 8);

			sbInfo.append("<div class='post-slide'>");
			sbInfo.append("<div class='post-img'>");
			sbInfo.append("<img src='../../upload/" + info_datas.get(i).getInfo_rep_img_path() + "' style='height:20rem; object-fit: cover;'> <a href='information_view.do?info_seq=" + info_datas.get(i).getInfo_seq() + "' class='over-layer'><i");
			sbInfo.append("class='fa fa-link'></i></a>");
			sbInfo.append("</div>");
			sbInfo.append("<div class='post-content'>");
			sbInfo.append("<h3 class='post-title'>");
			sbInfo.append("<a href='information_view.do?info_seq=" + info_datas.get(i).getInfo_seq() + "'>" + info_datas.get(i).getInfo_subject() + "</a>");
			sbInfo.append("</h3>");
			sbInfo.append("<p class='post-description'> 조회수 : " + info_datas.get(i).getInfo_hit() + "&nbsp;&nbsp;&nbsp;&nbsp;추천수 : " + info_datas.get(i).getInfo_rec_cnt() + "</p>");
			sbInfo.append("<span class='post-date'><i class='fa fa-clock-o'></i>" + dt_formatted + "</span>"); 
			sbInfo.append("</div>");
			sbInfo.append("</div>");
		}
	}
	
	StringBuilder sbAlbum = new StringBuilder();
	
	if(album_datas != null){
		for(int i = 0; i < album_datas.size(); i++){
			sbAlbum.append("<div class='col-4 portfolio-item'>");
			sbAlbum.append("<a href='album_view.do?ab_seq=" + album_datas.get(i).getAb_seq() + "'>");
			sbAlbum.append("<div class='portfolio-img'>");
			sbAlbum.append("<img src='../../upload/" + album_datas.get(i).getAb_rep_img_path() + "' class='img-fluid' style='height:24rem; width:410px; object-fit: cover;'>");
			sbAlbum.append("</div>");
			sbAlbum.append("<div class='portfolio-info'>");
			sbAlbum.append("<h4>" + album_datas.get(i).getAb_subject() + "</h4>");
			sbAlbum.append("<br>");
			sbAlbum.append("<p>조회수 : " + album_datas.get(i).getAb_hit() + "&nbsp;&nbsp;&nbsp;&nbsp;추천수 : " + album_datas.get(i).getAb_rec_cnt() + "</p>");
			sbAlbum.append("</div>");
			sbAlbum.append("</a>");
			sbAlbum.append("</div>");
		}
	}
%>
<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
<link href="https://fonts.googleapis.com/css?family=Rajdhani&display=swap" rel="stylesheet">
<link rel="stylesheet" href="assets/css/hero.css" />
<!-- start of hero -->
	<section class="hero-slider hero-style" id="hero-section">
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<div class="slide-inner slide-bg-image" data-background="/images/index/dog_ears_grass_66514_1920x1080.jpg">
						<div class="container">
							<div data-swiper-parallax="300" class="slide-title">
								<h2>Love and tails are entwined</h2>
							</div>
							<div data-swiper-parallax="400" class="slide-text">
								<p><strong>and Petopia is also with us..</strong></p>
							</div>
							<div class="clearfix"></div>
							<div data-swiper-parallax="500" class="slide-btns">
								<a href="member_insert.do" class="theme-btn-s2"><strong>지금 가입하기</strong></a>
							</div>
						</div>
					</div>
					<!-- end slide-inner -->
				</div>
				<!-- end swiper-slide -->

				<div class="swiper-slide">
					<div class="slide-inner slide-bg-image" data-background="/images/index/cat_muzzle_fluffy_116131_1920x1080.jpg">
						<div class="container">
							<div data-swiper-parallax="300" class="slide-title">
								<h2>Unconditional Love and..</h2>
							</div>
							<div data-swiper-parallax="400" class="slide-text">
								<p><strong>무조건적인 사랑, 그리고 펫토피아</strong></p>
							</div>
							<div class="clearfix"></div>
							<div data-swiper-parallax="500" class="slide-btns">
								<a href="member_insert.do" class="theme-btn-s2"><strong>지금 가입하기</strong></a>
							</div>
						</div>
					</div>
					<!-- end slide-inner -->
				</div>
				<!-- end swiper-slide -->
			</div>
			<!-- end swiper-wrapper -->

			<!-- swipper controls -->
			<div class="swiper-pagination"></div>
			<div class="swiper-button-next"></div>
			<div class="swiper-button-prev"></div>
		</div>
</section>

	<main id="main">
		<!-- ======= 바로가기 섹션 ======= -->
    <section id="contact" class="contact">
        <div class="section-title">
            <h2>펫토피아 컨텐츠</h2>
        </div>
        <div class="container mt-5">
            <div class="row">
                <!-- 첫 번째 컬럼 -->
                <div class="col-lg-6" style="height: 200px; padding-right: 15px; padding-left: 15px;">
                    <a href="guide.do"> <img src="/images/index/serviceguide.png" alt="Image 1" style="width: 100%; height: 100%; object-fit: cover;">
                    </a>
                </div>
                <!-- 두 번째 컬럼 -->
                <div class="col-lg-6" style="height: 200px; padding-right: 15px; padding-left: 15px;">
                    <a href="trip.do"> <img src="/images/index/trip.png" alt="Image 1" style="width: 100%; height: 100%; object-fit: cover;">
                    </a>
                </div>
            </div>
            <div class="row" style="margin-top: 30px">
                <!-- 세 번째 컬럼 -->
                <div class="col-lg-4" style="height: 200px; padding-right: 15px; padding-left: 15px;">
                    <a href="animalHospital.do"> <img src="/images/index/hospital.png" alt="Image 1" style="width: 100%; height: 100%; object-fit: cover;">
                    </a>
                </div>
                <!-- 네 번째 컬럼 -->
                <div class="col-lg-4" style="height: 200px; padding-right: 15px; padding-left: 15px;">
                    <a href="group_list.do"> <img src="/images/index/group.png" alt="Image 2" style="width: 100%; height: 100%; object-fit: cover;">
                    </a>
                </div>
                <!-- 다섯 번째 컬럼 -->
                <div class="col-lg-4" style="height: 200px; padding-right: 15px; padding-left: 15px;">
                    <a href="abandonment.do"> <img src="/images/index/abandonment.png" alt="Image 3" style="width: 100%; height: 100%; object-fit: cover;">
                    </a>
                </div>
            </div>

        </div>
    </section>
    <!-- End Contact Section -->

		<!-- ======= Services Section ======= -->
		<section id="services" class="services section-bg" data-aos="fade-up">
			<div class="section-title">
				<h2>반려동물 지식정보</h2>
			</div>
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-12">
						<div id="news-slider" class="owl-carousel">
							<%= sbInfo %>
						</div>
					</div>
				</div>
			</div>
		</section>
		<!-- End Services Section -->

		<!-- ======= Portfolio Section ======= -->
		<section id="portfolio" class="portfolio">
			<div class="container" data-aos="fade-up">
				
				<div class="section-title">
					<h2>펫토피아 앨범 최신글</h2>
				</div>

				<div class="row portfolio-container" data-aos="fade-up"
					data-aos-delay="200">

					<%= sbAlbum %>

				</div>

			</div>
		</section>
		<!-- End Portfolio Section -->
		<!-- End Team Section -->
	</main>

	<!-- <div id="right-nav" class="r-navbar">
	
	<!-- End #main -->

	<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>