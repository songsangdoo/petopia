<%@page import="com.petopia.mypage.model.MyPagePetTO"%>
<%@page import="com.petopia.pointshop.model.PointShopTO"%>
<%@page import="com.petopia.mypage.model.MypageProfileCardTO"%>
<%@page import="com.petopia.pointshop.model.GradeTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.petopia.board.question.model.QuestionAnswerFileTO"%>
<%@page import="com.petopia.board.question.model.QuestionAnswerTO"%>
<%@page import="com.petopia.board.question.model.QuestionFileTO"%>
<%@page import="com.petopia.board.question.model.QuestionTO"%>
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
	
	MypageProfileCardTO profileCardInfo = (MypageProfileCardTO) request.getAttribute( "profileCardInfo" );
	ArrayList<PointShopTO> skinPointShopList = (ArrayList<PointShopTO>) request.getAttribute( "skinPointShopList" );
	ArrayList<PointShopTO> badgePointShopList = (ArrayList<PointShopTO>) request.getAttribute( "badgePointShopList" );
	ArrayList<String> myBadgeImgList = (ArrayList<String>) request.getAttribute( "myBadgeImgList" );
	String mySkinImg = (String) request.getAttribute( "mySkinImg" );
	mySkinImg = mySkinImg.replace( "_listSample", "" );
	
	ArrayList<MyPagePetTO> petCheckLists = (ArrayList<MyPagePetTO>) request.getAttribute( "petCheckLists" );
	
	StringBuilder sbPetList = new StringBuilder();
	

	if( petCheckLists != null ) {
		for( int i=0; i<petCheckLists.size(); i++ ) {
			if( petCheckLists.get(i) != null ) {
				String petGender = petCheckLists.get(i).getP_gender();
				String petName = petCheckLists.get(i).getP_name();
				String petAge = petCheckLists.get(i).getP_age();
				
				if ( petGender.equals( "암" ) ) {
					sbPetList.append( "<div class='petList' style='background-image: url(\"/images/wback.png\")'>" );
					sbPetList.append( "	<div style='width:10%;'><img src ='/images/gender_w.png'></div>" );
					sbPetList.append( "	<div style='width:60%;'><strong>" + petName + "</strong></div>" );
					sbPetList.append( "	<div style='width:30%;'><strong>" + petAge + "살</strong></div>" );
					sbPetList.append( "</div>" );
				} else {
					sbPetList.append( "<div class='petList' style='background-image: url(\"/images/mback.png\")'>" );
					sbPetList.append( "	<div style='width:10%;'><img src ='/images/gender_m.png'></div>" );
					sbPetList.append( "	<div style='width:60%;'><strong>" + petName + "</strong></div>" );
					sbPetList.append( "	<div style='width:30%;'><strong>" + petAge + "살</strong></div>" );
					sbPetList.append( "</div>" );
				}
			}
		}
	}
	
	String badgeImg = "";
	
	StringBuilder sbBadgeList = new StringBuilder();
	if( myBadgeImgList != null ) {
		for (int i = 0; i < myBadgeImgList.size(); i++) {
			badgeImg = myBadgeImgList.get(i);
			sbBadgeList.append( "<img src='/images/point_shop_badge/" + badgeImg + "' border='0' width='30' height='30' />" );
		}
	}
	
	int m_seq = 0;
	
	if( userData != null){
		m_seq = Integer.parseInt(userData.getM_seq());
	}
	
	boolean isManager = false;
	if(userData != null && userData.getGrade_seq().equals("1")){
		isManager = true;
	}
	
	QuestionTO data = (QuestionTO)request.getAttribute("data");
	List<QuestionFileTO> file_list = (List)request.getAttribute("file_list");
	
	int w_seq = data.getM_seq();
	
	boolean isWriter = false;
	
	if(m_seq == w_seq){
		isWriter = true;
	}
	
	String dt = data.getQ_dt();
	String dt_formatted = String.format("%s-%s-%s %s:%s:%s", dt.substring(0, 4), dt.substring(4, 6), dt.substring(6, 8), dt.substring(8, 10), dt.substring(10, 12), dt.substring(12, 14));
	data.setQ_dt(dt_formatted);
	 
	int q_seq = data.getQ_seq();
	
	StringBuilder sbFileHtml = new StringBuilder();
	
	if(file_list.size() != 0) {
		for(int i = 0; i < file_list.size(); i++){
			if(i == 0){
				sbFileHtml.append("<div class='carousel-item active'>");
			}else{
				sbFileHtml.append("<div class='carousel-item'>");
			}
			sbFileHtml.append("<img src='../../upload/" + file_list.get(i).getQ_file_img_path() + "' class='d-block' width='100%' style='border-radius: 17px;'>");
			sbFileHtml.append("</div>");	
		}
	}
	
	QuestionAnswerTO selected_ans = (QuestionAnswerTO)request.getAttribute("selected_ans");
	
	List<QuestionAnswerTO> ans_list = (List)request.getAttribute("ans_list");
	List<QuestionAnswerFileTO> ans_file_list = (List)request.getAttribute("ans_file_list");
	
	List<MemberTO> m_info_datas = (List)request.getAttribute("m_info_datas");
	List<GradeTO> grade_info_datas = (List)request.getAttribute("grade_info_datas");
	
	StringBuilder sbAnsHtml = new StringBuilder();
	
	if(selected_ans != null){
		List<QuestionAnswerFileTO> ans_file_datas = new ArrayList();
		
		if(ans_file_list != null){
			for(int j = 0; j < ans_file_list.size(); j++){
				if(ans_file_list.get(j).getQ_ans_seq() == selected_ans.getQ_ans_seq()){
					ans_file_datas.add(ans_file_list.get(j));
				}
			}
		}
		

		MemberTO ans_w_info = null;
		
		if(m_info_datas != null){
			for(int j = 0; j < m_info_datas.size(); j++){
				if(m_info_datas.get(j).getM_seq().equals(String.valueOf(selected_ans.getM_seq()))){
					ans_w_info = m_info_datas.get(j);
					
					break;
				}
			}
		}
		
		GradeTO ans_w_grade = null;
		
		if(grade_info_datas != null){
			for(int j = 0; j < grade_info_datas.size(); j++){
				if(grade_info_datas.get(j).getGrade_seq().equals(ans_w_info.getGrade_seq())){
					ans_w_grade = grade_info_datas.get(j);
					
					break;
				}
			}
		}
		
		String ans_dt = selected_ans.getQ_ans_dt();
		String ans_dt_formatted = String.format("%s-%s-%s %s:%s:%s", ans_dt.substring(0, 4), ans_dt.substring(4, 6), ans_dt.substring(6, 8), ans_dt.substring(8, 10), ans_dt.substring(10, 12), ans_dt.substring(12, 14));
		
		sbAnsHtml.append("<table class='table'>");
		
		sbAnsHtml.append("<tr class='table-primary'>");
		sbAnsHtml.append("<td>");
		sbAnsHtml.append("<div align='left'>");
		sbAnsHtml.append("&nbsp;&nbsp;&nbsp;&nbsp;<span style='font-size: 24px; vertical-align: middle; color:#008cff; font-style: italic;'><strong>질문자 채택</strong></span>&nbsp;&nbsp;&nbsp;<img src='../../images/titlesole.png' style='width:30px;'>");
		sbAnsHtml.append("</div>");
		sbAnsHtml.append("<br>");
		sbAnsHtml.append("<div >");
		sbAnsHtml.append("<strong>");
		sbAnsHtml.append("&nbsp;&nbsp;&nbsp;&nbsp;<img class='grade_image' src='images/grade/" + ans_w_grade.getGrade_img() + "' style='width:24px; height: auto;'>&nbsp;&nbsp;<span style='font-size: 24px; vertical-align: middle;'>" + ans_w_info.getM_nickname() + "</span>&nbsp;&nbsp;<span style='vertical-align: middle;'><small>(" + ans_dt_formatted + ")</small></span>");
		sbAnsHtml.append("<br>");
		sbAnsHtml.append("</div>");
		sbAnsHtml.append("</td>");
		sbAnsHtml.append("</tr>");
		sbAnsHtml.append("<tr class='table-primary'>");
		sbAnsHtml.append("<td>");

		
		sbAnsHtml.append("<div class='container'>");
		sbAnsHtml.append("<br>");
		if(ans_file_datas.size() != 0) {
			sbAnsHtml.append("<div class='container'>");
			sbAnsHtml.append("<div class='carousel slide carousel-fade'>");
			sbAnsHtml.append("<div class='carousel-inner'>");
			
			for(int k = 0; k < ans_file_datas.size(); k++){
				if(k == 0){
					sbAnsHtml.append("<div class='carousel-item active'>");
				}else{
					sbAnsHtml.append("<div class='carousel-item'>");
				}
				sbAnsHtml.append("<img src='../../upload/" + ans_file_datas.get(k).getQ_ans_file_img_path() + "' class='d-block' width='100%' style='border-radius: 17px;'>");
				sbAnsHtml.append("</div>");	
			}
			
			sbAnsHtml.append("</div>");
			sbAnsHtml.append("<button class='carousel-control-prev' type='button' data-bs-target='#carouselExampleFade' data-bs-slide='prev'>");
			sbAnsHtml.append("<span class='carousel-control-prev-icon' aria-hidden='true'></span>");
			sbAnsHtml.append("<span class='visually-hidden'>Previous</span>");
			sbAnsHtml.append("</button>");
			sbAnsHtml.append("<button class='carousel-control-next' type='button' data-bs-target='#carouselExampleFade' data-bs-slide='next'>");
			sbAnsHtml.append("<span class='carousel-control-next-icon' aria-hidden='true'></span>");
			sbAnsHtml.append("<span class='visually-hidden'>Next</span>");
			sbAnsHtml.append("</button>");
			sbAnsHtml.append("</div>");
			sbAnsHtml.append("</div>");
		}
		
		sbAnsHtml.append("<br>");
		
		sbAnsHtml.append("<div class='container' style='width: 700px; word-break:break-all;'>");
		sbAnsHtml.append("<p class='lead'>" + selected_ans.getQ_ans_content() + "</p>");
		sbAnsHtml.append("</div>");
		
		sbAnsHtml.append("<br>");
		
		sbAnsHtml.append("</div>");
		
		sbAnsHtml.append("<div class='ans-btn-group' align='right'>");
		int ans_w_seq = selected_ans.getM_seq();
		
		if(isManager){
			sbAnsHtml.append("<button type='button' class='btn btn-outline-danger' style='cursor: pointer;' onclick='ans_del(" + selected_ans.getQ_ans_seq() + ", " + q_seq + ")'>삭	제</button>&nbsp;");
		}
		
		sbAnsHtml.append("</div>");
		

		sbAnsHtml.append("</td>");
		sbAnsHtml.append("</tr>");
		sbAnsHtml.append("</table>");
	}
	
	if(ans_list != null){
		for(int i = 0; i < ans_list.size(); i++){
			List<QuestionAnswerFileTO> ans_file_datas = new ArrayList();
			
			if(ans_file_list != null){
				for(int j = 0; j < ans_file_list.size(); j++){
					if(ans_file_list.get(j).getQ_ans_seq() == ans_list.get(i).getQ_ans_seq()){
						ans_file_datas.add(ans_file_list.get(j));
					}
				}
			}
			
			MemberTO ans_w_info = null;
			
			if(m_info_datas != null){
				for(int j = 0; j < m_info_datas.size(); j++){
					if(m_info_datas.get(j).getM_seq().equals(String.valueOf(ans_list.get(i).getM_seq()))){
						ans_w_info = m_info_datas.get(j);
						
						break;
					}
				}
			}
			
			GradeTO ans_w_grade = null;
			
			if(grade_info_datas != null){
				for(int j = 0; j < grade_info_datas.size(); j++){
					if(grade_info_datas.get(j).getGrade_seq().equals(ans_w_info.getGrade_seq())){
						ans_w_grade = grade_info_datas.get(j);
						
						break;
					}
				}
			}
			
			String ans_dt = ans_list.get(i).getQ_ans_dt();
			String ans_dt_formatted = String.format("%s-%s-%s %s:%s:%s", ans_dt.substring(0, 4), ans_dt.substring(4, 6), ans_dt.substring(6, 8), ans_dt.substring(8, 10), ans_dt.substring(10, 12), ans_dt.substring(12, 14));
			
			sbAnsHtml.append("<table class='table'>");
			
			sbAnsHtml.append("<tr class='table-light'>");
			sbAnsHtml.append("<td>");
			sbAnsHtml.append("<strong>");
			sbAnsHtml.append("&nbsp;&nbsp;&nbsp;&nbsp;<img class='grade_image' src='images/grade/" + ans_w_grade.getGrade_img() + "' style='width:20px; height: auto;'>&nbsp;&nbsp;<span style='font-size: 24px; vertical-align: middle;'>" + ans_w_info.getM_nickname() + "</span>&nbsp;&nbsp;<span style='vertical-align: middle;'><small>(" + ans_dt_formatted + ")</small></span>");
			sbAnsHtml.append("<br>");
			sbAnsHtml.append("</td>");
			sbAnsHtml.append("</tr>");
			sbAnsHtml.append("<tr class='table-light'>");
			sbAnsHtml.append("<td>");
			
			sbAnsHtml.append("<div class='container'>");
			sbAnsHtml.append("<br>");
			
			if(ans_file_datas.size() != 0) {
				sbAnsHtml.append("<div class='container'>");
				sbAnsHtml.append("<div class='carousel slide carousel-fade'>");
				sbAnsHtml.append("<div class='carousel-inner'>");
				
				for(int k = 0; k < ans_file_datas.size(); k++){
					if(k == 0){
						sbAnsHtml.append("<div class='carousel-item active'>");
					}else{
						sbAnsHtml.append("<div class='carousel-item'>");
					}
					sbAnsHtml.append("<img src='../../upload/" + ans_file_datas.get(k).getQ_ans_file_img_path() + "' class='d-block' width='100%' style='border-radius: 17px;'>");
					sbAnsHtml.append("</div>");	
				}
				
				sbAnsHtml.append("</div>");
				sbAnsHtml.append("<button class='carousel-control-prev' type='button' data-bs-target='#carouselExampleFade' data-bs-slide='prev'>");
				sbAnsHtml.append("<span class='carousel-control-prev-icon' aria-hidden='true'></span>");
				sbAnsHtml.append("<span class='visually-hidden'>Previous</span>");
				sbAnsHtml.append("</button>");
				sbAnsHtml.append("<button class='carousel-control-next' type='button' data-bs-target='#carouselExampleFade' data-bs-slide='next'>");
				sbAnsHtml.append("<span class='carousel-control-next-icon' aria-hidden='true'></span>");
				sbAnsHtml.append("<span class='visually-hidden'>Next</span>");
				sbAnsHtml.append("</button>");
				sbAnsHtml.append("</div>");
				sbAnsHtml.append("</div>");
			}
			
			
			sbAnsHtml.append("<br>");
			sbAnsHtml.append("<div class='container' style='width: 700px; word-break:break-all;'>");
			sbAnsHtml.append("<p class='lead'>" + ans_list.get(i).getQ_ans_content() + "</p>");
			sbAnsHtml.append("</div>");
			sbAnsHtml.append("</div>");
			
			sbAnsHtml.append("<br>");
			sbAnsHtml.append("<div class='ans-btn-group' align='right'>");
			int ans_w_seq = ans_list.get(i).getM_seq();
			
			if(data.getQ_select_check().equals("N") && isWriter && m_seq != ans_w_seq){
				sbAnsHtml.append("<button type='button' class='btn btn-outline-primary' style='cursor: pointer;' onclick='select_ans(" + q_seq + ", " + ans_list.get(i).getQ_ans_seq() + ")'>채택하기</button>&nbsp;");
			}
			
			if(m_seq == ans_w_seq){
				sbAnsHtml.append("<button type='button' class='btn btn-outline-primary' style='cursor: pointer;' onclick='location.href=\"question_ans_modify.do?q_seq=" + q_seq + "&q_ans_seq=" + ans_list.get(i).getQ_ans_seq() + "\"'>수    정</button>&nbsp;");
			}
			
			if(m_seq == ans_w_seq || isManager){
				sbAnsHtml.append("<button type='button' class='btn btn-outline-danger' style='cursor: pointer;' onclick='ans_del(" + ans_list.get(i).getQ_ans_seq() + ", " + q_seq + ")'>삭	제</button>&nbsp;");
			}
			
			sbAnsHtml.append("</div>");
			

			sbAnsHtml.append("</td>");
			sbAnsHtml.append("</tr>");
			sbAnsHtml.append("</table>");
		}
	}
	
	// 프로필 카드
	
	MemberTO w_info = (MemberTO)request.getAttribute("w_info");
	
	GradeTO w_gradeChecks = (GradeTO)request.getAttribute("w_gradeChecks");
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
<link href="assets/css/mypage/mypage.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="./css/profile_card.css">  
<script type="text/javascript">

function ans_del(ans_seq, q_seq){
	Swal.fire({
		  title: '답글을 삭제하시겠습니까?',
		  text: '',
		  icon: 'question',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Ok'
		}).then((result) => {
		  if (result.isConfirmed) {
			  location.href = 'question_ans_del.do?q_seq=' + q_seq + '&q_ans_seq=' + ans_seq;
		  }else{
			  return false;
		  }
		});
	  
}

function post_del(q_seq){
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
			  location.href='question_delete_ok.do?q_seq=' + q_seq;
		  }else{
			  return false;
		  }
		});
	  
}

function select_ans(q_seq, q_ans_seq){
	Swal.fire({
		  title: '답글을 채택하시겠습니까?',
		  text: '',
		  icon: 'question',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Ok'
		}).then((result) => {
		  if (result.isConfirmed) {
			  location.href='question_ans_select.do?q_seq=' + q_seq + '&q_ans_seq=' + q_ans_seq; 
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
					<table class="table">
					<tr>
						<td class="text-center">
						<h4><%= data.getQ_subject() %></h4>
						<div class="d-flex justify-content-end text-black">
						  <div class="p-2 bd-highlight">작성일 : <%= data.getQ_dt() %></div>
						  <div class="p-2 bd-highlight">조회수 : <%= data.getQ_hit() %></div>
						</div>
						</td>
					</tr>
					<tr>
						<td id="info-content">
							<br>
							<div class="container">
							<div id="carouselExampleFade" class="carousel slide carousel-fade" data-bs-ride='carousel'>
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
							<br>
							<div class="container" style="width: 700px; word-break:break-all;">
							<p class="lead"><%= data.getQ_content() %></p>
							</div>
							
							<br><br><br><br>			
							
							<!-- 프로필카드 -->
							<div class="container profile_card" style='width:650px; border : 1px solid gray; border-radius : 12px; padding : 0px; background-color: #f8f9fa;'>
							<div class="skin_part" style="background-image: url('/images/point_shop_skin/<%= mySkinImg %>')">
								<div class="image_part">
									<img class="profile_image" src="images/profile_img/<%= profileCardInfo.getPro_img() %>">
								</div>
								<div class="profile_part">
									<div class="nickname">
										<span class="nickname_text"><strong><%= w_info.getM_nickname() %></strong></span>
									</div>
									<div class="introduce">
										<span class="nickname_text2"><strong><%= profileCardInfo.getPro_comment() %></strong></span>
									</div>
								</div>
							</div>
							<div class="info_part">
								<div class="pet_part"><%= sbPetList %></div>
								<div class="spec_part">
									<div class="grade">
										<div class="grade_name_part">
											<%= w_gradeChecks.getGrade_cnt() %>
										</div>
										<div class="grade_image_part">
											<img class="grade_image" src="images/grade/<%= w_gradeChecks.getGrade_img() %>">
										</div>
									</div>
									<div class="point_and_badge_part">
										<div class="badge_part">
											<div class="badge_title_part">보유 뱃지</div>
											<div class="badge_image_part"><%= sbBadgeList %></div>
										</div>
										<div class="point_part">
											<div class="point_title_part">누적 포인트</div>
											<div class="point_amount_part">
											<%= w_info.getM_totalPoint() %>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
							<!-- 프로필카드 -->
								
						</td>
					</tr>			
					</table>
					
					<!-- 글 수정, 삭제버튼 -->
					<div class='ans-btn-group' align="right">
						<button type="button" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='question_list.do'" >목	록</button>
						<c:if test='<%= userData != null && !isWriter && data.getQ_select_check().equals("N") %>'>
							<button type="button" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='question_ans_write.do?q_seq=<%= q_seq %>'">답변하기</button>
						</c:if>
						<c:if test='<%= data.getQ_select_check().equals("N") && isWriter %>'>
							<button type="button" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='question_modify.do?q_seq=<%= q_seq %>'">수 정</button>
						</c:if>
						<c:if test="<%= isWriter || isManager %>">
							<button type="button" class="btn btn-outline-danger" style="cursor: pointer;" onclick="post_del(<%= q_seq %>)">삭 제</button>
						</c:if>
					</div>
					<!-- 글 수정, 삭제버튼 -->
					
					<br><br>
					
					<!-- 답글 -->
					<table class="table">
					<tr>
					<td><strong>답글수</strong> &nbsp;&nbsp; <%= data.getQ_ans_cnt() %></td>
					<td></td>
					</tr>
					
					</table>
					
					<%= sbAnsHtml %>
					<!-- // 답글 -->
					
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