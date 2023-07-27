<%@page import="com.petopia.mypage.model.MyPagePetTO"%>
<%@page import="com.petopia.pointshop.model.PointShopTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.petopia.mypage.model.MypageProfileCardTO"%>
<%@page import="com.petopia.pointshop.model.GradeTO"%>
<%@page import="com.petopia.board.tip.model.TipBoardCommentTO"%>
<%@page import="com.petopia.board.tip.model.TipBoardFileTO"%>
<%@page import="com.petopia.board.tip.model.TipBoardTO"%>
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
	
	TipBoardTO data = (TipBoardTO)request.getAttribute("data");
	List<TipBoardFileTO> file_list = (List)request.getAttribute("file_list");
	List<TipBoardCommentTO> cmt_list = (List)request.getAttribute("cmt_list");
	
	int w_seq = data.getM_seq();
	
	boolean isWriter = false;
	
	if(w_seq == m_seq){
		isWriter = true;
	}
	
	String dt = data.getTb_dt();
	String dt_formatted = String.format("%s-%s-%s %s:%s:%s", dt.substring(0, 4), dt.substring(4, 6), dt.substring(6, 8), dt.substring(8, 10), dt.substring(10, 12), dt.substring(12, 14));
	data.setTb_dt(dt_formatted);
	 
	int tb_seq = data.getTb_seq();
	
	StringBuilder sbFileHtml = new StringBuilder();
	
	if(file_list.size() != 0) {
		for(int i = 0; i < file_list.size(); i++){
			if(i == 0){
				sbFileHtml.append("<div class='carousel-item active'>");
			}else{
				sbFileHtml.append("<div class='carousel-item'>");
			}
			sbFileHtml.append("<img src='../../upload/" + file_list.get(i).getTb_file_img_path() + "' class='d-block' width='100%' style='border-radius: 17px;'>");
			sbFileHtml.append("</div>");	
		}
	}

	int cmt_cpage = 1;
	if (request.getParameter("cmt_cpage") != null) {
		cmt_cpage = Integer.parseInt(request.getParameter("cmt_cpage"));
	}

	int recordsPerpage = 5;

	int totalRecords = cmt_list.size();

	int totalPage = (totalRecords - 1) / recordsPerpage + 1;

	int startNum = (cmt_cpage - 1) * recordsPerpage;

	int pagePerBlock = 5;
	
	StringBuilder sbCmtHtml = new StringBuilder();
	
	List<GradeTO> grade_info_datas = (List)request.getAttribute("grade_info_datas");
	List<MemberTO> m_info_datas = (List)request.getAttribute("m_info_datas");
	
	for(int i = startNum; i < cmt_list.size() && i < startNum + recordsPerpage; i++){
		System.out.println(cmt_list.size());
		boolean isCmtWriter = m_seq == cmt_list.get(i).getM_seq() ? true: false;
		String cmt_dt = cmt_list.get(i).getTb_cmt_dt();
		String cmt_dt_formatted = String.format("%s-%s-%s %s:%s:%s", cmt_dt.substring(0, 4), cmt_dt.substring(4, 6), cmt_dt.substring(6, 8), cmt_dt.substring(8, 10), cmt_dt.substring(10, 12), cmt_dt.substring(12, 14));
		
		MemberTO cmt_w_info = null;
		
		if(m_info_datas != null){
			for(int j = 0; j < m_info_datas.size(); j++){
				if(m_info_datas.get(j).getM_seq().equals(String.valueOf(cmt_list.get(i).getM_seq()))){
					cmt_w_info = m_info_datas.get(j);
					
					break;
				}
			}
		}
		
		GradeTO cmt_w_grade = null;
		
		if(grade_info_datas != null){
			for(int j = 0; j < grade_info_datas.size(); j++){
				if(grade_info_datas.get(j).getGrade_seq().equals(cmt_w_info.getGrade_seq())){
					cmt_w_grade = grade_info_datas.get(j);
					
					break;
				}
			}
		}
		
		sbCmtHtml.append("<tr>");
		sbCmtHtml.append("<td width='100%'>");
		sbCmtHtml.append("<div class='text-black d-flex justify-content-between' style='font-size: 20px;'>");
		sbCmtHtml.append("<strong><img class='grade_image' src='images/grade/" + cmt_w_grade.getGrade_img() + "' style='width:20px; height: auto;'>&nbsp;&nbsp;<span style='vertical-align: middle;'>" + cmt_list.get(i).getM_nickname() + "</span>&nbsp;<small><span style='vertical-align: middle;'>(" + cmt_dt_formatted + ")</span></small></strong>");
		sbCmtHtml.append("<div class='cmt-btn-group' align='right'>");
		sbCmtHtml.append("<button type='button' class='btn btn-outline-primary' onclick='rec_up(" + tb_seq + ", " + cmt_list.get(i).getTb_cmt_seq() + ")'><img src='../../images/free-icon-thumb-up-8279617.png'>&nbsp;&nbsp;&nbsp;&nbsp;<span>" + cmt_list.get(i).getTb_cmt_rec_cnt() + "</span></button>");
		sbCmtHtml.append("&nbsp;");
		sbCmtHtml.append("<button type='button' class='btn btn-outline-danger' onclick='no_rec_up(" + tb_seq + ", " + cmt_list.get(i).getTb_cmt_seq() + ")'><img src='../../images/free-icon-thumb-down-8279616.png'>&nbsp;&nbsp;&nbsp;&nbsp;<span>" + cmt_list.get(i).getTb_cmt_no_rec_cnt() + "</span></button>");
		if(isCmtWriter || isManager){
			sbCmtHtml.append("&nbsp;");
			sbCmtHtml.append("<button type='button' class='btn btn-outline-danger' onclick='cmt_del(" + cmt_list.get(i).getTb_cmt_seq() + ", " + tb_seq + ")'>삭	제</button>");
		}
		sbCmtHtml.append("</div>");
		sbCmtHtml.append("</div>");
		sbCmtHtml.append("<br>");
		sbCmtHtml.append("<div><p>" + cmt_list.get(i).getTb_cmt_content() + "</p></div>");
		sbCmtHtml.append("</td>");
		sbCmtHtml.append("<td>");
		sbCmtHtml.append("</td>");
		sbCmtHtml.append("</tr>");
	}
	
	StringBuilder cmt_pageSbHtml = new StringBuilder();

	int startBlock = cmt_cpage - (cmt_cpage - 1) % pagePerBlock;
	int endBlock = startBlock + pagePerBlock - 1;

	if(endBlock >= totalPage){
		endBlock = totalPage;
	}
	cmt_pageSbHtml.append("<div>");
	cmt_pageSbHtml.append("<nav>");
	cmt_pageSbHtml.append("<ul class='pagination justify-content-center'>");
	if(cmt_cpage == 1){
		cmt_pageSbHtml.append("<li class='page-item'><a class='page-link'> &lt; </a></li>");
	}else{
		cmt_pageSbHtml.append("<li class='page-item'><a class='page-link' href='tipboard_view.do?tb_seq=" + tb_seq + "&cmt_cpage=" + (cmt_cpage - 1) + "'> &lt; </a></li>");
	}
	for(int i = startBlock; i <= endBlock; i++){
		if(i == cmt_cpage){
			cmt_pageSbHtml.append("<li class='page-item'><a class='page-link'>" + i + "</a></li>");
		} else{
			cmt_pageSbHtml.append("<li class='page-item'><a class='page-link' href='tipboard_view.do?tb_seq=" + tb_seq + "&cmt_cpage=" + i + "'>" + i + "</a></li>");
		}
	}

	if(cmt_cpage == totalPage){
		cmt_pageSbHtml.append("<li class='page-item'><a class='page-link'> &gt; </a></li>");
	}else{
		cmt_pageSbHtml.append("<li class='page-item'><a class='page-link' href='tipboard_view.do?tb_seq=" + tb_seq + "&cmt_cpage=" + (cmt_cpage + 1) + "'> &gt; </a></li>");
	}
	cmt_pageSbHtml.append("</ul>");
	cmt_pageSbHtml.append("</nav>");
	cmt_pageSbHtml.append("</div>");
	
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

<script type="text/javascript">
window.onload = function(){
	const cbtn = document.getElementById('cbtn');
	
	cbtn.onclick = function(){
		if(<%= userData == null%>){
			Swal.fire('로그인을 먼저 해주세요', '', 'warning');
			return false;
		}
		
		if(document.cfrm.tb_cmt_content.value.trim() == ''){
			Swal.fire('댓글 내용을 입력해주세요', '', 'warning');
			return false;
		}
		
		Swal.fire({
			  title: '댓글 작성을 완료하시겠습니까?',
			  text: '',
			  icon: 'question',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Ok'
			}).then((result) => {
			  if (result.isConfirmed) {
			      document.cfrm.submit();
			  }else{
				  return false;
			  }
		});
	}
	
	const recBtn = document.getElementById('recBtn');
	
	recBtn.onclick = function(){
		if(<%= userData == null%>){
			Swal.fire('로그인을 먼저 해주세요', '', 'warning');
			return false;
		}
		
		location.href='tipboard_view_rec.do?tb_seq=<%= tb_seq %>';
	}

};

function cmt_del(cmt_seq, tb_seq, m_seq){
	Swal.fire({
		  title: '댓글을 삭제하시겠습니까?',
		  text: '',
		  icon: 'question',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Ok'
		}).then((result) => {
		  if (result.isConfirmed) {
			  location.href = 'tipboard_view_cmt_del.do?tb_seq=' + tb_seq + '&tb_cmt_seq=' + cmt_seq;
		  }else{
			  return false;
		  }
	});
	  
}

function post_del(tb_seq){
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
			  location.href='tipboard_delete_ok.do?tb_seq=' + tb_seq;
		  }else{
			  return false;
		  }
	});
	  
}

function rec_up(seq, cmt_seq){
	if(<%= userData == null %>){
		Swal.fire('로그인을 먼저 해주세요', '', 'warning');
		return false;
	}
	
	location.href='tipboard_view_cmt_rec.do?tb_seq=' + seq + '&&tb_cmt_seq=' + cmt_seq;
}

function no_rec_up(seq, cmt_seq){
	if(<%= userData == null %>){
		Swal.fire('로그인을 먼저 해주세요', '', 'warning');
		return false;
	}
	
	location.href='tipboard_view_cmt_no_rec.do?tb_seq=' + seq + '&&tb_cmt_seq=' + cmt_seq;
}
</script>  
<link href="assets/css/mypage/mypage.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="./css/profile_card.css">  
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
						<h4><%= data.getTb_subject() %></h4>
						<div class="d-flex justify-content-end text-black">
						  <div class="p-2 bd-highlight">작성일 : <%= data.getTb_dt() %></div>
						  <div class="p-2 bd-highlight">조회수 : <%= data.getTb_hit() %></div>
						  <div class="p-2 bd-highlight">추천수 : <%= data.getTb_rec_cnt() %></div>
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
							<p class="lead"><%= data.getTb_content() %></p>
							</div>
							
						<br>					
						
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
					<div class='cmt-btn-group' align="right">
						<button type="button" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='tipboard_list.do'" >목	록</button>
						<c:if test="<%= !isWriter %>">
						<button type='button' class="btn btn-outline-primary" id = 'recBtn'>추 천</button>
						</c:if>
						<c:if test="<%= isWriter %>">
							<button type="button" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='tipboard_modify.do?tb_seq=<%= tb_seq %>'">수    정</button>
						</c:if>
						<c:if test="<%= isWriter || isManager %>">
							<button type="button" class="btn btn-outline-danger" style="cursor: pointer;" onclick="post_del(<%= tb_seq %>)">삭	제</button>
						</c:if>
					</div>
					<!-- 글 수정, 삭제버튼 -->
					
					<br><br>
					
					<!-- comment -->
					<table class="table">
					<tr>
					<td><strong>댓글수</strong> &nbsp;&nbsp; <%= data.getTb_cmt_cnt() %>&nbsp;&nbsp;&nbsp;&nbsp;<a href="tipboard_view.do?tb_seq=<%= tb_seq %>&&order_flag=latest">최신순</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="tipboard_view.do?tb_seq=<%= tb_seq %>&&order_flag=rec">추천순</a></td>
					<td></td>
					</tr>
					
					<!-- 댓글 리스트 -->
					<%= sbCmtHtml %>
					<!-- // 댓글 리스트 -->
					
					</table>
					
					<!-- comment 페이지네이션 -->
					<%= cmt_pageSbHtml %>
					<!-- // comment 페이지네이션 -->
					
					<!-- 댓글 작성 -->
						<div class="container w-80 mt-2">
							<form action="tipboard_view_cmt_write.do" method="post" name="cfrm">
							<input type="hidden" value="<%= tb_seq %>" name="tb_seq">
							<input type="hidden" value="<%= m_seq %>" name="m_seq">
							<table class="table">
							<tr>
								<td>
									<div class="input-group mb-3">
										<span class="input-group-text">내 용</span>
										<textarea class="form-control" name="tb_cmt_content" rows="5" style="resize: none;"></textarea>
									</div>
									<div align="right">
										<input type="button" class="btn btn-outline-primary" value="댓글 등록" id="cbtn"/>
									</div>
								</td>
							</tr>
							</table>
							</form>
						</div>
					<!-- // 댓글 작성 -->
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