<%@page import="java.util.ArrayList"%>
<%@page import="com.petopia.api.model.TripTO"%>
<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <% 
    StringBuilder sb = new StringBuilder();
 
	ArrayList<TripTO> lists = (ArrayList)request.getAttribute("lists");
	ArrayList<TripTO> count = (ArrayList)request.getAttribute("count");
	String part = (String)request.getAttribute("part");	
	String seq = (String)request.getAttribute("seq");	

	int cpage = 1;
	if (request.getParameter("cpage") != null) {
		cpage = Integer.parseInt(request.getParameter("cpage"));
	}

	String partParam = "";
	if(part != null){
		partParam = "&part="+part;
	}
	int recordPerPage = 20;	
	int totalRecord = lists.size();
	int totalPages = ((totalRecord -1) / recordPerPage) + 1;
	int blockPerPage = 5;
	
	int stand = 0;
	int food = 0;
	int tour = 0;
	int experience = 0;
	int hospital = 0;
    for(TripTO to : count) {
    	switch(to.getPARTNAME()){
    	case "식음료" : food = to.getCOUNT();
    		break;
    	case "숙박" : stand = to.getCOUNT();
			break;
    	case "관광지" : tour = to.getCOUNT();
			break;
    	case "체험" : experience = to.getCOUNT();
			break;
    	case "동물병원" : hospital = to.getCOUNT();
			break;	
    	}
    }
	if(seq == null){		
    	//시작블록 = cpage - (cpage -1) % blockPerPage
    	//끝블록 = cpage - (cpage -1) % blockPerPage + blockperpage - 1
    	
    	int skip = (cpage - 1) * recordPerPage; 

        sb.append("<div class='row'>");
	 	for(int i = skip; i < recordPerPage + skip; i++){
	 		/*
	        sb.append("<div class='card content'>" + to.getTITLE() + "</div>\n");
	 		*/
	 		if(i < totalRecord){
		 		TripTO to = lists.get(i);

		        sb.append("<div class='col-sm-6 col-lg-3 mb-2 mt-2'>");
		        
	    		if(!to.getPARTNAME().equals("동물병원")){
	    		sb.append("<a href='/trip.do?seq="+to.getCONTENTSEQ()+partParam+"' style='color:black;'><div class='candidate-list candidate-grid'>");
		 		sb.append("<div class='candidate-list-image'>");
		 		sb.append("<img class='img-fluid' src='"+to.getIMAGE()+"' alt=''>");
		 		sb.append("</div>");
		 		sb.append("<div class='candidate-list-details'>");
	    		} else{
	    			sb.append("<a href='https://map.kakao.com/?q="+to.getADDRESS()+to.getTITLE()+"' style='color:black;'><div class='candidate-list candidate-grid'>");
	    	 		sb.append("<div class='candidate-list-details' style='border-top:1px solid #eeeeee'>");	
	    		}
		 		sb.append( "<div class='candidate-list-info'>");
		 		sb.append("<div class='candidate-list-title'>");
		 		sb.append("<h5><strong>"+to.getTITLE()+"</strong></h5>");
		 		sb.append("</div>");
		 		sb.append( "<div class='candidate-list-option'>");
		 		sb.append("<ul class='list-unstyled'>");
		 		sb.append("<li><i class='bi bi-phone'></i>"+to.getTEL()+"</li>");
		 		sb.append("<li style='white-space: nowrap;overflow:hidden;text-overflow: ellipsis;'><i class='bi bi-geo-alt'></i>"+to.getADDRESS()+"</li>");
		 		sb.append("</ul>");
		 		sb.append("</div>");
		 		sb.append( "</div>");
		 		sb.append("<div class='candidate-list-favourite-time'>");
		 		sb.append("<span class='candidate-list-time order-1' style='white-space: nowrap;overflow:hidden;text-overflow: ellipsis;'>"
		 			+to.getUSEDTIME()+"</span>");
		 		sb.append("</div>");
		 		sb.append("</div>");
		 		sb.append("</div></a>");
		 		sb.append("</div>");
	 		}
	 	}
 		sb.append("</div>");
					
		int startBlock = cpage - (cpage - 1) % blockPerPage;
		int endBlock = cpage - (cpage - 1) % blockPerPage + blockPerPage - 1;
		if(endBlock >= totalPages){
			endBlock = totalPages;
		}
        sb.append("<div class='row'>");
		sb.append("<div class='col-12 text-center mt-4 mt-sm-5'>");
		sb.append("<ul class='pagination justify-content-center mb-0'>");

		
		// 블록 처음으로 이동 만등기
		if(startBlock == 1){

			sb.append("<li class='page-item'> <a class='page-link'>Prev</a></li>");
			} else {

				sb.append("<li class='page-item'> <a class='page-link' href='/trip.do?cpage="+ (startBlock - blockPerPage) + partParam + "'>Prev</a></li>");
			}
		
		// 페이지 블록 만들기
		for(int i = startBlock; i <= endBlock; i++){
			 if(i == cpage){
				sb.append("<li class='page-item active'> <a class='page-link' href='#'>"+i+"</a> </li>");
				} else{
				sb.append("<li class='page-item'> <a class='page-link' href='/trip.do?cpage="+ i + "" + partParam + "'>"+i+"</a></li>");
				}
			}
		
		// 다음페이지 이동 만들기
		if(endBlock == totalPages){
			sb.append("<li class='page-item'> <a class='page-link' href='#'>Next</a></li>");
			} else {
			sb.append("<li class='page-item'> <a class='page-link' href='/trip.do?cpage="+ (endBlock + 1) + "" + partParam + "'>Next</a></li>");
			}

		sb.append("</ul>");
		sb.append("</div>");
 		sb.append("</div>");
	}  else if(seq != null){
 		for(TripTO to : lists){
 			if(to.getCONTENTSEQ().equals(seq)){
 				String[] arrs = to.getKEYWORD().split(", ");
 				String[] mainArrs = to.getMAINFACILITY().split("-");
 				String[] costArrs = to.getUSEDCOST().split("-");
 				String keywords = "";
 				String mains = "";
				for(String keyword : arrs){
 					keywords += "#" + keyword + " ";
 				}
				for(String main : mainArrs){
					if(main !="") mains += "> " + main + "<br/>";
 				}
 				sb.append("<section class='bg'>");
 				sb.append("<div class='container'>");
 				sb.append("<div class='row'>");
 				sb.append("<div class='col-lg-12 mb-4 mb-sm-5'>");
 				sb.append("<div class='card card-style1 border-0'>");
 				sb.append("<div class='card-body p-1-9 p-sm-2-3 p-md-6 p-lg-7' style='background-color:#eee'>");
 				sb.append("<div class='row align-items-center'>");
 				sb.append("<div class='col-lg-5 mb-4 mb-lg-0' >");
 				sb.append("<img src='"+to.getIMAGE()+"'style='display:flex;width:100%;height:auto; border-radius: 10px;' alt='...'>");
 				sb.append("</div>");
 				sb.append("<div class='col-lg-7 px-xl-10'>");
 				sb.append("<h2 class='h2'><strong>"+to.getTITLE()+"</strong></h2>");
 				sb.append("<ul class='list-unstyled mb-1-9'>");
 				sb.append("<li class='mb-2 mb-xl-3 display-28'><span class='display-26 text-secondary me-2 font-weight-600'>주소: </span>"+to.getADDRESS()+"</li>");
 				sb.append("<li class='mb-2 mb-xl-3 display-28'><span class='display-26 text-secondary me-2 font-weight-600'>전화번호: </span>"+to.getTEL()+"</li>");
 				sb.append("<li class='mb-2 mb-xl-3 display-28'><span class='display-26 text-secondary me-2 font-weight-600'>이용시간: </span>"+to.getUSEDTIME()+"</li>");
 				sb.append("<li class='mb-2 mb-xl-3 display-28'><span class='display-26 text-secondary me-2 font-weight-600'>홈페이지: </span><a href='"+to.getHOMEPAGE()+"'>바로가기</a></li>");
 				sb.append("<li class='display-28'><span class='display-26 text-secondary me-2 font-weight-600'>키워드: </span> "+	keywords +"</li>");
 				sb.append("</div>");
 				sb.append("</div>");
 				sb.append("</div>");
 				sb.append("</div>");
 				sb.append("</div>");
 				sb.append("<div class='col-lg-12 mb-4 mb-sm-5' style='padding-bottom:20px;border-bottom: 1px solid #c1c1c1;'>");
 				sb.append("<div>");
 				sb.append("<span class='section-title text-primary mb-3 mb-sm-4' style='font-size:20px;'>내용</span>");
 				sb.append("<p style='padding: 10px 0px 0px 20px;'>"+to.getCONTENT()+"</p>");
 				sb.append("</div>");
 				sb.append("</div>");
 				sb.append("<div class='col-lg-12 mb-4 mb-sm-5' style='padding-bottom:20px;border-bottom: 1px solid #c1c1c1;'>");
 				sb.append("<div>");
 				sb.append("<span class='section-title text-primary mb-3 mb-sm-4' style='font-size:20px;'>주요시설</span>");
 				sb.append("<p style='padding: 10px 0px 0px 20px;'>"+mains+"</p>");
 				sb.append("</div>");
 				sb.append("</div>");
 				
 				sb.append("<div class='col-lg-12 mb-4 mb-sm-5' style='padding-bottom:20px;border-bottom: 1px solid #c1c1c1;'>");
 				sb.append("<div>");
 				sb.append("<span class='section-title text-primary mb-3 mb-sm-4' style='font-size:20px;'>가격</span>");
 				sb.append("<p style='padding: 10px 0px 0px 20px;'>"+to.getUSEDCOST()+"</p>");
 				sb.append("</div>");
 				sb.append("</div>");
 				
 				sb.append("</div>");
 				sb.append("</div>");
 				sb.append("</div>");
 				sb.append("</div>");
 				sb.append("</div>");
 				sb.append("</section>");
 			}
 		}
 	}

 	int totalData = stand + food + tour + experience + hospital;
	
	String kakaoJsKey = (String)request.getAttribute("kakao_js_key");
 %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>펫토피아</title>
<meta content="Petopia는 애완동물을 위한 온라인 커뮤니티입니다. 펫 관련 정보와 이야기를 함께 공유해보세요."
	name="description">
<meta content="애완동물, 펫, 커뮤니티, 정보, 공유" name="keywords">

<!-- Template Main CSS File -->
<link href="assets/css/update.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<style type="text/css">
	
</style>
  <script type="text/javascript">
	$(document).ready(function(){
		const searchParams = new URLSearchParams(location.search);
		if(searchParams.size != 0){
			for (const param of searchParams) {
				if(param[0] == "part"){
					if(param[1]=="식음료"){
				    	$("#food-data-tab").addClass("select");
				    } else if(param[1]=="숙박"){
				    	$("#stand-data-tab").addClass("select");		    	
				    } else if(param[1]=="관광지"){
				    	$("#tour-data-tab").addClass("select");		    	
				    } else if(param[1]=="체험"){
				    	$("#experience-data-tab").addClass("select");		    	
				    } else if(param[1]=="동물병원"){
				    	$("#hospital-data-tab").addClass("select");		    	
				    } 
				} else if(searchParams.size <2) {
			    	$("#total-data-tab").addClass("select");							
				}
			} 
		} else {
	    	$("#total-data-tab").addClass("select");				
		}
		
		/*
		$('.content').on('click', function(){
			  $.ajax({
			        url : "/trip.do/detail",
			        type : "POST",
			        data : {"TITLE" : this.innerText},
			        dataType: 'JSON',
			        success : function (data) {
			        		let tagArea = document.getElementById('maincontent');
			        		 
			        		let div = document.createElement('div');
			        		tagArea.innerText = "";
			        		if(data.partname != "동물병원"){
				        		tagArea.innerHTML += '<div class="img_box"><img src="' + data.image + '"></div>';
			        		}
			        		tagArea.innerHTML += "<div class='text_box'><span class='area'>("+data.partname+")</span>" +
			        		"<strong>인제스피디움</strong><ul class='list2'><li class='address'> 주소 : "+data.address+"</li>" +
			        		"<li class='text'> 전화번호 : "+data.tel+"</li>"+
			        		"<li class='text'> 시간 : "+data.usedtime+"</li>"+
			        		"<li class='text'>"+data.content+"</li></ul></div>";
			        	
			        		
			            }
			        })
		})
		*/
	});
</script>
</head>

<body>

<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
<main id="main">
	<section id="contact" class="contact">		
		<div class="section-title">
			<h2>반려동물 여행지 찾기</h2>
		</div>
		<div class="container bootstrap snippets bootdey">
			<div class="row social" style="background-color:#ddd; text-align: center; margin-bottom:12px">
				<div class="col-md-2" id="total-data-tab">				
					<a href="trip.do">
						<small class="social-title">통합 데이터</small>
						<h2 class="count"><%= totalData %></h3>
					</a>
				</div>
				<div class="col-md-2" id="food-data-tab">
					<a href="trip.do?part=식음료">
						<div class="panel panel-default facebook-like">
							<div class="panel-body">
								<small class="social-title">식음료</small>
								<h3 class="count"><%=food %></h3>
							</div>
						</div>
					</a>
				</div>				
				<div class="col-md-2" id="stand-data-tab">
					<a href="trip.do?part=숙박">
						<div class="panel panel-default google-plus">
							<div class="panel-body">
								<small class="social-title">숙박</small>
								<h3 class="count"><%=stand%></h3>
							</div>
						</div>
					</a>
				</div>
				<div class="col-md-2" id="tour-data-tab">
					<a href="trip.do?part=관광지">
						<div class="panel panel-default visitor">
							<div class="panel-body">
								<small class="social-title">관광지</small>
								<h3 class="count"><%=tour %></h3>
							</div>
						</div>
					</a>
				</div>
				
				<div class="col-md-2" id="experience-data-tab">
					<a href="trip.do?part=체험">
						<div class="panel panel-default visitor">
							<div class="panel-body">
								<small class="social-title">체험</small>
								<h3 class="count"><%=experience %></h3>
							</div>
						</div>
					</a>
				</div>
								
				<div class="col-md-2" id="hospital-data-tab">
					<a href="trip.do?part=동물병원">
						<div class="panel panel-default visitor">
							<div class="panel-body">
								<small class="social-title">동물병원</small>
								<h3 class="count"><%=hospital %></h3>
							</div>
						</div>
					</a>
				</div>
			</div>
		</div>
		<!-- 
		<div class="container bootstrap snippets bootdey">
			<div class="row r" style="height:600px">
				<div id="maincontent" class="col-md-6 wp-block no-space arrow-right base no-margin">
				</div>
				<div id="rightsearch"
					class="col-md-6 wp-block no-space light no-margin" >
					<div>
						<div id="searchtab" class="col-12 col-md-10 col-lg-8 scroll" style="overflow-x:hidden; overflow-y:scroll; height:600px; width:100%;">						
						
						</div>
					</div>
				</div>
			</div>
		</div>
	 	-->
		<div class="container">
		    <div class="row">
		        <div class="col-lg-12">
		            <%=sb %>
			    </div>
			</div>
		</div>
	</section>
</main>
<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>

	<!-- End Footer -->
	<!-- Vendor JS Files -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/owl-carousel/1.3.3/owl.carousel.min.js'></script>
	<script type="text/javascript"
		src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

	<script type="text/javascript">
	</script>
	<!-- Template Main JS File -->
	<script src="assets/js/main.js"></script>
	<script src="assets/js/project.js"></script>
	<script src="assets/js/update.js"></script>
	
</body>

</html>