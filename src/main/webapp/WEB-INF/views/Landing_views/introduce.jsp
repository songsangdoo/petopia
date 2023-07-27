<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<% 
    MemberTO userData = (MemberTO) session.getAttribute("loginMember");
    System.out.print(userData);
%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link href="assets/css/add.css" rel="stylesheet">
<title>펫토피아</title>
<meta content="" name="description">
<meta content="" name="keywords">

</head>

<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	
	<main id="main">
		<!-- ======= Contact Section ======= -->

		<section id="contact" class="contact">
			<div class="container-fluid" id="header">
				<!-- hero section -->
				<div class="container" id="hero">
					<div class="row justify-content-end">
						<div class="col-lg-6 hero-img-container">
							<a href="#">
								<div class="hero-img">
									<img src="assets/img/introps/hero-img.png">
								</div>
							</a>
						</div>

						<div class="col-lg-9">
							<div class="hero-title">
								<a href="#">
									<h1>반려동물 커뮤니티:</h1>
									<h1 class="typed-text-output d-inline">
										</h2>
										<div class="typed-text d-none">반려동물을 위한, 더 나은 세상을
											만들어갑시다, 사랑하는 동물들을 위한 커뮤니티, 반려동물과 함께하는 삶, 즐겁고 의미있게, 펫토피아 : PET + UTOPIA</div>
								</a>
							</div>
						</div>

						<div class="col-lg-6">
							<div class="hero-meta">
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- hero ends -->

			<!-- Article Grid -->


			<!-- More articles button -->

			<div class="container text-center pb-3 mb-5">

			</div>
		</section>
		<!-- End Contact Section -->
	</main>

	<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>

	<div id="preloader"></div>
	<a href="#"
		class="back-to-top d-flex align-items-center justify-content-center"><i
		class="bi bi-arrow-up-short"></i></a>

	<!-- Vendor JS Files -->
	<script src="assets/vendor/aos/aos.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="assets/vendor/glightbox/js/glightbox.min.js"></script>
	<script src="assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
	<script src="assets/vendor/swiper/swiper-bundle.min.js"></script>
	<script src="assets/vendor/waypoints/noframework.waypoints.js"></script>
	<script src="assets/vendor/php-email-form/validate.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="assets/js/sidemenu.js"></script>
	<script src="https://unpkg.com/ionicons@5.2.3/dist/ionicons.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/typed.js/2.0.12/typed.min.js"></script>
	<script src="lib/wow/wow.min.js"></script>
	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/owl-carousel/1.3.3/owl.carousel.min.js'></script>
	<script type="text/javascript"
		src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

	<!-- Template Main JS File -->
	<script src="assets/js/main.js"></script>
	<script src="assets/js/project.js"></script>
	<script src="assets/js/typing.js"></script>
</body>

</html>