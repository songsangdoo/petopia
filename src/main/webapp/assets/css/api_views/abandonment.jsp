<%@page import="java.util.ArrayList"%>
<%@page import="com.petopia.api.model.AbandonmentTO"%>
<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
 	StringBuilder sb = new StringBuilder();
 
	ArrayList<AbandonmentTO> lists = (ArrayList)request.getAttribute("lists");
	ArrayList<AbandonmentTO> count = (ArrayList)request.getAttribute("count");
	String seq = (String)request.getAttribute("seq");	
	String kind = "";
	if(request.getAttribute("kind") != null ){
		kind += "&kind="+ request.getAttribute("kind");
	}
	int cpage = 1;
	if (request.getAttribute("cpage") != null) {
		cpage = Integer.parseInt(request.getParameter("cpage"));
	}

	String partParam = "";
	int recordPerPage = 16;	
	int totalRecord = lists.size();
	int totalPages = ((totalRecord -1) / recordPerPage) + 1;
	int blockPerPage = 5;

	
	//시작블록 = cpage - (cpage -1) % blockPerPage
	//끝블록 = cpage - (cpage -1) % blockPerPage + blockperpage - 1
	
	int skip = (cpage - 1) * recordPerPage; 


	if(seq == null){	
		sb.append("<div class='categories'>");
		sb.append("<ul>");
		sb.append("<li id='all'>");
		sb.append("<a href='/abandonment.do'>전체</a>");
		sb.append("</li>");
		sb.append("<li id='dog'>");
		sb.append("<a href='/abandonment.do?kind=개'>개</a>");
		sb.append("</li>");
		sb.append("<li id='cat'>");
		sb.append("<a href='abandonment.do?kind=고양이'>고양이</a>");
		sb.append("</li>");
		sb.append("<li id='etc'>");
		sb.append("<a href='abandonment.do?kind=기타'>기타 동물</a>");
		sb.append("</li>");
		sb.append("</ul>");
		sb.append("</div>");
        sb.append("<div class='row'>");
	 	for(int i = skip; i < recordPerPage + skip; i++){
	 		/*
	        sb.append("<div class='card content'>" + to.getTITLE() + "</div>\n");
	 		*/
	 		if(i < totalRecord){
		 		AbandonmentTO to = lists.get(i);

		        sb.append("<div class='col-sm-6 col-lg-3 mb-2 mt-2'>");
		        

	    		sb.append("<a href='/abandonment.do?cpage="+cpage+"&seq="+to.getDESERTIONNO()+kind+"' style='color:black;'><div class='candidate-list candidate-grid'>");
		 		sb.append("<div class='candidate-list-image'>");
		 		sb.append("<img class='img-fluid' style ='height:500px;' src='"+to.getPOPFILE()+"' alt=''>");
		 		sb.append("</div>");
		 		sb.append("<div class='candidate-list-details'>");

		 		sb.append( "<div class='candidate-list-info'>");
		 		sb.append("<div class='candidate-list-title'>");
		 		sb.append("<h5 style='margin-top:10px'><strong>"+to.getKINDCD()+"</strong></h5>");
		 		sb.append("</div>");
		 		sb.append( "<div class='candidate-list-option'>");
		 		sb.append("<ul style='flex-direction: column;'>");
		 		sb.append("<li>발견장소 : "+to.getHAPPENPLACE()+"</li>");
		 		sb.append("</ul>");
		 		sb.append("</div>");
		 		sb.append( "</div>");
		 		sb.append("<div style='border-top:1px solid #eeeeee; padding-top:3px;'>");
		 		if(to.getSEXCD().equals("M")){
		 			sb.append("<div class='token token--blue'>수컷</div>");
		 		} else if(to.getSEXCD().equals("F")){
		 			sb.append("<div class='token token--pink'>암컷</div>");
		 		} else if(to.getSEXCD().equals("Q")){
		 			sb.append("<div class='token'>미상</div>");		 			
		 		}
		 		sb.append("<div class='token'>"+to.getWEIGHT()+"</div>");
		 		sb.append("<div class='token'>"+to.getAGE()+"</div>");
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

				sb.append("<li class='page-item'> <a class='page-link' href='/abandonment.do?cpage="+ (startBlock - blockPerPage) +kind+  "'>Prev</a></li>");
			}
		
		// 페이지 블록 만들기
		for(int i = startBlock; i <= endBlock; i++){
			 if(i == cpage){
				sb.append("<li class='page-item active'> <a class='page-link' href='#'>"+i+"</a> </li>");
				} else{
				sb.append("<li class='page-item'> <a class='page-link' href='/abandonment.do?cpage="+ i + kind+ "" +  "'>"+i+"</a></li>");
				}
			}
		
		// 다음페이지 이동 만들기
		if(endBlock == totalPages){
			sb.append("<li class='page-item'> <a class='page-link' href='#'>Next</a></li>");
			} else {
			sb.append("<li class='page-item'> <a class='page-link' href='/abandonment.do?cpage="+ (endBlock + 1) + kind+ "'>Next</a></li>");
			}

		sb.append("</ul>");
		sb.append("</div>");
 		sb.append("</div>");
	} else if(seq != null){
	 	for(AbandonmentTO to : lists){
	 		if(to.getDESERTIONNO().equals(seq)){
	 	        sb.append("<div class='row'>");
	 			sb.append("<div class='col-xl-12'>");
	 			sb.append("<!-- Account details card-->");
	 			sb.append("<div class='card mb-4'>");
	 			sb.append("<div class='card-header'><h4>유기동물 특징</h4></div>");
	 			sb.append("<div class='card-body'>");
	 	        sb.append("<div class='row'>");
	 			sb.append("<div class='col-xl-4'>");
	 			sb.append("<!-- Profile picture card-->");
	 			sb.append("<div class='card mb-4 mb-xl-0'>");
	 			sb.append("<img class='img-fluid' style ='height:400px;' src='"+to.getPOPFILE()+"' alt=''>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("<div class='col-xl-8'>");
	 			sb.append("<div class='row gx-3 mb-3'>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>품종</h5></label>");
	 			sb.append("<p>"+to.getKINDCD()+"</p>");
	 			sb.append("</div>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>성별</h5></label>");
	 			sb.append("<p>");
		 		if(to.getSEXCD().equals("M")){
		 			sb.append("수컷");
		 		} else if(to.getSEXCD().equals("F")){
		 			sb.append("암컷");
		 		} else if(to.getSEXCD().equals("Q")){
		 			sb.append("미상");		 			
		 		}
		 		sb.append("</p>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("<div class='row gx-3 mb-3'>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>나이</h5></label>");
	 			sb.append("<p>"+to.getAGE()+"</p>");
	 			sb.append("</div>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>색상</h5></label>");
	 			sb.append("<p>"+to.getCOLORCD()+"</p>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("<div class='row gx-3 mb-3'>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>무게</h5></label>");
	 			sb.append("<p>"+to.getWEIGHT()+"</p>");
	 			sb.append("</div>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>중성화여부</h5></label>");
	 			sb.append("<p>");
		 		if(to.getNEUTERYN().equals("Y")){
		 			sb.append("중성화 완료");
		 		} else if(to.getNEUTERYN().equals("N")){
		 			sb.append("중성화 안되어있음");
		 		} else if(to.getNEUTERYN().equals("U")){
		 			sb.append("미상");		 			
		 		}
		 		sb.append("</p>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("<div class='mb-3'>");
	 			sb.append("<label class='small mb-1' for='inputEmailAddress'><h5>특징</h5></label>");
	 			sb.append("<p>"+to.getSPECIALMARK()+"</p>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("</div>");
		 		sb.append("</div>");

	 	        sb.append("<div class='row'>");
	 			sb.append("<div class='col-xl-12'>");
	 			sb.append("<!-- Account details card-->");
	 			sb.append("<div class='card mb-4'>");
	 			sb.append("<div class='card-header'><h4>구조 정보</h4></div>");
	 			sb.append("<div class='card-body'>");
	 	        sb.append("<div class='row'>");
	 	        
	 			sb.append("<div class='col-xl-12'>");
	 			sb.append("<div class='row gx-3 mb-3'>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>접수 일시</h5></label>");
	 			sb.append("<p>"+to.getHAPPENDT().substring(0, 4)+"년 " + to.getHAPPENDT().substring(4, 6) +"월 " +to.getHAPPENDT().substring(6, 8)+  "일</p>");
	 			sb.append("</div>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>발견 장소</h5></label>");
	 			sb.append("<p>"+to.getHAPPENPLACE()+"</p>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			
	 			sb.append("<div class='row gx-3 mb-3'>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>보호센터명</h5></label>");
	 			sb.append("<p>"+ to.getCARENM() +"</p>");
	 			sb.append("</div>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>보호센터 전화번호</h5></label>");
	 			sb.append("<p>"+to.getCARETEL()+"</p>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			
	 			sb.append("<div class='row gx-3 mb-3'>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>관할기관</h5></label>");
	 			sb.append("<p>"+ to.getORGNM() +"</p>");
	 			sb.append("</div>");
	 			sb.append("<div class='col-md-6'>");
	 			sb.append("<label class='small mb-1'><h5>담당 연락처</h5></label>");
	 			sb.append("<p>"+to.getOFFICETEL()+"</p>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			
	 			sb.append("<div class='mb-3'>");
	 			sb.append("<label class='small mb-1' for='inputEmailAddress'><h5>보호장소</h5></label>");
	 			sb.append("<p>"+to.getCAREADDR()+"</p>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("</div>");
	 			sb.append("</div>");
		 		sb.append("</div>");


	 	}
		
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
				if(param[0] == "kind"){
					if(param[1]=="전체"){
				    	$("#all").addClass("active");
				    } else if(param[1]=="개"){
				    	$("#dog").addClass("active");		    	
				    } else if(param[1]=="고양이"){
				    	$("#cat").addClass("active");		    	
				    } else if(param[1]=="기타"){
				    	$("#etc").addClass("active");		    	
				    } 
				} else if(searchParams.size <2) {
			    	$("#all").addClass("active");						
				}
			} 
		} else {
	    	$("#all").addClass("active");				
		}
	});
</script>
</head>

<body>

<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
<main id="main">
	<section id="contact" class="contact">		
		<div class="section-title">
			<h2>유기동물 찾기</h2>
		</div>
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