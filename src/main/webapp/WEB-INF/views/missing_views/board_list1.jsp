<%@page import="com.petopia.model.MemberTO"%>
<%@page import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>
<%@page import="com.petopia.board.missing.model.MissingTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	request.setCharacterEncoding("utf-8");


	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	System.out.print(userData);
	
	if( userData != null){
		int m_seq = Integer.parseInt(userData.getM_seq());
	}

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
		
		String[] missing_loc_arr = datas.get(i).getMs_missing_loc().split("&&&");
		String missing_loc = missing_loc_arr[0];
		
		if (count % 3 == 1) {
			sbHtml.append("<div class='row' data-aos='fade-up' data-aos-delay='200'>");
		}
		sbHtml.append("<div class='col-4' style='height: '>");
		sbHtml.append("<div class='card'>");
		sbHtml.append("<a href='missing_view.do?ms_seq=" + datas.get(i).getMs_seq() + "'>");
		sbHtml.append("<img src='../../upload/" + datas.get(i).getMs_rep_img_path() + "' class='card-img-top' style='height: 20rem; object-fit: cover;'>");
		sbHtml.append("<ul class='list-group list-group-flush'>");
		sbHtml.append("<li class='list-group-item'>" + datas.get(i).getMs_pet_name() + "&nbsp;&nbsp;[&nbsp;");
		if(datas.get(i).getMs_pet_gender().equals("M")){
			sbHtml.append("<img src='../../images/gender_m.png' />&nbsp;] </li>");
		}else if(datas.get(i).getMs_pet_gender().equals("F")){
			sbHtml.append("<img src='../../images/gender_w.png' />&nbsp;] </li>");
		}
		sbHtml.append("<li class='list-group-item'>실종 날짜 <br><b>" + datas.get(i).getMs_missing_date() + "</b></li>");
		sbHtml.append("<li class='list-group-item'>실종 지역 <br><b>" + missing_loc + "</b></li>");
		sbHtml.append("</ul>");
		sbHtml.append("</a>");
		sbHtml.append("</div>");
		sbHtml.append("</div>");
	
		if (count % 3 == 0) {
			sbHtml.append("</div><br><br>");
		}
	}
	
	if (count % 3 != 0) {
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
			pageSbHtml.append("<li class='page-item active'><a class='page-link'>" + i + "</a></li>");
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
	
	<main id="main">
		<section id="portfolio" class="portfolio">
			<div class="container w-75" data-aos="fade-up">
				<div class="section-title">
					<h2>실종 신고</h2>
				</div>
				<%= sbHtml %>
			
				<hr class="container w-80">

				<!-- 버튼 -->	
				<c:if test="<%= userData != null %>">
				<div align="right">
					<input type="button" value="실종 신고" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='missing_write.do'" />
				</div>
				</c:if>
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

<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>

</html>