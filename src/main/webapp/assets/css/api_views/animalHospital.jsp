<%@page import="com.petopia.model.MemberTO"%>
<%@page import="com.petopia.api.model.HospitalTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URI"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <% 
    StringBuilder sb = new StringBuilder();
	ArrayList<HospitalTO> lists = (ArrayList)request.getAttribute("lists");
	String dong = (String)request.getAttribute("dong");
	int count = 0;
	if(lists != null){
	 	for(HospitalTO to : lists){
	 		sb.append(" <div class='media text-muted pt-3 addr'>");
	 		sb.append("<p class='media-body pb-3 mb-0 small lh-125 border-bottom border-gray'>");
	 		sb.append("<strong class='d-block text-gray-dark'>"+to.getBUSINESS_NAME()+"</strong>");
	 		sb.append("도로명 주소 : "+to.getSTREET_ADDR().replaceAll(dong, "<spen style='color:red;'>" +dong+ "</spen>") +"");
	 		sb.append("</p>");
	 		sb.append("</div>");
			count++;
	 	}
	}
	
	String kakaoJsKey = (String)request.getAttribute("kakao_js_key");
 %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>펫토피아</title>
<meta content="" name="description">
<meta content="" name="keywords">
<style type="text/css">
	body{background:#f5f5f5}
	.text-white-50 { color: rgba(255, 255, 255, .5); }
	.bg-blue { background-color:#00b5ec; }
	.border-bottom { border-bottom: 1px solid #e5e5e5; }
	.box-shadow { box-shadow: 0 .25rem .75rem rgba(0, 0, 0, .05); }
	.scroll {
		height: 500px;
		overflow:scroll;		
	    -ms-overflow-style: none; /* 인터넷 익스플로러 */
	    scrollbar-width: none; /* 파이어폭스 */
		}
	.scroll::-webkit-scrollbar {
	    display: none; /* 크롬, 사파리, 오페라, 엣지 */
		}
</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){


		$("#dongForm").submit(function() {
			let dong = this.dong.value;
			if(dong.length < 2) {
				alert("검색어가 너무 짧습니다.\n2글자 이상 검색해주세요.");
				return false;
			}
		});
  			
		$('.addr').on('click', function(){
			let address = $(this).find("p")[0].innerText.replace("도로명 주소 : ", "").split("\n")[1].split(",")[0];

			console.log( $(this).find("p")[0].innerText.replace("도로명 주소 : ", "").split("\n")[1].split(",")[0])
			let serviceName = $(this).find("strong")[0].innerText;
			  $.ajax({
			        url : "/animalHospital.do",
			        type : "POST",
			        data : {"STREET_ADDR" : address + serviceName},
			        dataType: 'JSON',
			        success : function (data) {
			        	let y = data.documents[0].y;
			        	let x = data.documents[0].x;
						var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
						var options = { //지도를 생성할 때 필요한 기본 옵션
							center: new kakao.maps.LatLng(y, x), //지도의 중심좌표.
							level: 2 //지도의 레벨(확대, 축소 정도)
						};

						var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
						var markerPosition  = new kakao.maps.LatLng(y, x);

						// 마커를 생성합니다
						var marker = new kakao.maps.Marker({
						    position: markerPosition
						});

						// 마커가 지도 위에 표시되도록 설정합니다
						marker.setMap(map);

			            }
			        })
		})	
	});
</script>
</head>

<body>

     <jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>


	</section>
	<!-- End Hero -->

	<main id="main">


		<!-- ======= Contact Section ======= -->
		<section id="contact" class="contact">
			<div class="container" data-aos="fade-up">

				<div class="section-title">
					<h2>동물병원 찾기</h2>
				</div>

				<div class="row">					
					<div class=" d-flex align-items-stretch">
						<form action="animalHospital.do" method="get" class="php-email-form" id="dongForm">
							<div class="form-group">
								<label for="name">동이름 검색</label> <input type="text"
									class="form-control" name="dong" id="dong" placeholder="동이름을 검색해주세요" required>
							</div>
							<div class="text-center">
								<button type="submit">Send Message</button>
							</div>			
						</form>
					</div>
				</div>									
					<div>
						<div id="map" style="float: left; width: 40%; height: 600px;"></div>
						<div style="float: left; width: 60%; ">
					  <div class="p-3 bg-white rounded box-shadow" style=" min-height: 600px;">
					    <h6 class="border-bottom border-gray pb-2 mb-0">동물병원 목록(<%=count %>)</h6>
					    <div  class ="scroll">
						<%=sb %>
						</div>
					  </div>
											
						</div>
					</div>
			</div>
		</section>
		<!-- End Contact Section -->
		

	</main>
<footer>

<hr>
    
<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
	</footer>
	<!-- End Footer -->
</body>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=<%=kakaoJsKey%>"></script>
	<script type="text/javascript">
	let y = 33.450701;
	let x = 126.570667;
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
		center: new kakao.maps.LatLng(y, x), //지도의 중심좌표.
		level: 2 //지도의 레벨(확대, 축소 정도)
	};

	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
	var markerPosition  = new kakao.maps.LatLng(y, x);

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
	    position: markerPosition
	});

	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);
	</script>

	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/owl-carousel/1.3.3/owl.carousel.min.js'></script>
	<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>


	<!-- Template Main JS File -->
	<script src="assets/js/main.js"></script>
	<script src="assets/js/project.js"></script>
	
	<script type="text/javascript">
	</script>

</html>