<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- ======= Footer ======= -->
	<footer id="footer">
		<div class="container footer-bottom clearfix">
			<div class="copyright">
				&copy; Copyright <strong><span>Petopia</span></strong>. All Rights Reserved
			</div>
			<div class="credits">
				<!-- All the links in the footer should remain intact. -->
				<!-- You can delete the links only if you purchased the pro version. -->
				<!-- Licensing information: https://bootstrapmade.com/license/ -->
				<!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/arsha-free-bootstrap-html-template-corporate/ -->
				Designed by <a href="https://bootstrapmade.com/">Bootstrap</a>
			</div>
		</div>
	</footer>
	<!-- End Footer -->

	<div id="loader-wrapper">
	  <div id="preloader">
	    <span></span>
	    <span></span>
	    <span></span>
	    <span></span>
	    <span></span>
	  </div>
	</div>

	<!-- Vendor JS Files -->
	<script src="assets/vendor/aos/aos.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="assets/vendor/glightbox/js/glightbox.min.js"></script>
	<script src="assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
	<script src="assets/vendor/swiper/swiper-bundle.min.js"></script>
	<script src="assets/vendor/waypoints/noframework.waypoints.js"></script>
	<script src="assets/vendor/php-email-form/validate.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://unpkg.com/ionicons@5.2.3/dist/ionicons.js"></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/owl-carousel/1.3.3/owl.carousel.min.js'></script>
	<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/glidejs@2.1.0/src/modules/Bullets.min.js"></script>

	<!-- Template Main JS File -->
	<script src="assets/js/main.js"></script>
	<script src="assets/js/project.js"></script> 
	<script src="assets/js/hero.js"></script>
	<script src="assets/js/menu.js"></script>

	<script type="text/javascript">
	 var toggleElement = document.querySelector('.toggle');
	  var isToggleOn = toggleElement.classList.contains('toggle-on');
	  

		$('.toggle').click(function(e) {
			var toggleElement = document.querySelector('.toggle');
			var isToggleOn = !toggleElement.classList.contains('toggle-on');

			e.preventDefault();
			

			if (isToggleOn) {
				setTimeout(function() {
				window.location.href = 'login.do';
				}, 50); 
			} else {
				$(this).toggleClass('toggle-on');
				setTimeout(function() {
				window.location.href = 'logout.do';
				}, 230); 
			}
		});

		$(window).on('scroll', function() {
			var header = $('.header_area');
			var scrollPosition = $(window).scrollTop();

			if (scrollPosition > 0) {
				header.addClass('scroll');
			} else {
				header.removeClass('scroll');
			}
		});
		
		 const config = {
		    	    type: 'carousel',
		    	    startAt: 0,
		    	    perView: 4,
		    	    gap: 32,
		    	    breakpoints: {
		    	      1280: {
		    	        perView: 3,
		    	      },
		    	      1024: {
		    	        perView: 2,
		    	      },
		    	      768: {
		    	        perView: 1,
		    	      }
		    	    }
		    	  };

		 
	</script>
</body>
</html>