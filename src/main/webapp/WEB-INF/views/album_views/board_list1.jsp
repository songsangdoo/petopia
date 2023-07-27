<%@page import="com.petopia.model.MemberTO"%>
<%@page import="com.petopia.board.album.model.AlbumTO"%>
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
	
	List<AlbumTO> datas = (List)request.getAttribute("datas");
	
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
		sbHtml.append("<div class='col-4 portfolio-item'>");
		sbHtml.append("<a href='album_view.do?ab_seq=" + datas.get(i).getAb_seq() + "'>");
		sbHtml.append("<div class='portfolio-img'>");
		sbHtml.append("<img src='../../upload/" + datas.get(i).getAb_rep_img_path() + "' class='img-fluid' style='height:24rem; width:410px; object-fit: cover;'>");
		sbHtml.append("</div>");
		sbHtml.append("<div class='portfolio-info'>");
		sbHtml.append("<h4>" + datas.get(i).getAb_subject() + "</h4>");
		sbHtml.append("<br>");
		sbHtml.append("<p>조회수 : " + datas.get(i).getAb_hit() + "&nbsp;&nbsp;&nbsp;&nbsp;추천수 : " + datas.get(i).getAb_rec_cnt() + "</p>");
		sbHtml.append("</div>");
		sbHtml.append("</a>");
		sbHtml.append("</div>");
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
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='album_list.do?cpage=" + (cpage - 1) + "'> &lt; </a></li>");
	}
	for(int i = startBlock; i <= endBlock; i++){
		if(i == cpage){
			pageSbHtml.append("<li class='page-item active'><a class='page-link'>" + i + "</a></li>");
		} else{
			pageSbHtml.append("<li class='page-item'><a class='page-link' href='album_list.do?cpage=" + i + "'>" + i + "</a></li>");
		}
	}
	
	if(cpage == totalPage){
		pageSbHtml.append("<li class='page-item'><a class='page-link'> &gt; </a></li>");
	}else{
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='album_list.do?cpage=" + (cpage + 1) + "'> &gt; </a></li>");
	}
	pageSbHtml.append("</ul>");
	pageSbHtml.append("</nav>");
	
	String order_flag = (String)request.getAttribute("order_flag");

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
		<!-- ======= Portfolio Section ======= -->
		<section id="portfolio" class="portfolio">
			<div class="container w-75" data-aos="fade-up">
				<div class="section-title">
					<h2>앨범 게시판</h2>
				</div>
				<div align = 'right'>
				<div class="dropdown">
				  <a class="btn btn-outline-primary dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="border:none; color: black;">
					  <c:if test='<%= order_flag != null && order_flag.equals("latest") %>'>최신순</c:if>
					  <c:if test='<%= order_flag != null && order_flag.equals("rec") %>'>추천순</c:if>
					  <c:if test='<%= order_flag != null && order_flag.equals("cmt") %>'>댓글순</c:if>
					  <c:if test='<%= order_flag != null && order_flag.equals("hit") %>'>조회수순</c:if>
					  <c:if test='<%= order_flag == null %>'>정렬</c:if>
				  </a>
				
				  <ul class="dropdown-menu">
				    <li><a class="dropdown-item" href="album_list.do?order_flag=latest">최신순</a></li>
				    <li><a class="dropdown-item" href="album_list.do?order_flag=rec">추천순</a></li>
				    <li><a class="dropdown-item" href="album_list.do?order_flag=cmt">댓글순</a></li>
				    <li><a class="dropdown-item" href="album_list.do?order_flag=hit">조회수순</a></li>
				  </ul>
				</div>
				</div>
				<br>
				<div class='row portfolio-container' data-aos='fade-up' data-aos-delay='200'>
				<%= sbHtml %>
				</div><br>
				<hr class="container w-80">
				
				<!-- 버튼 -->
				<c:if test="<%= userData != null %>">
					<div align="right">
						<input type="button" value="글 쓰기" class="btn btn-outline-primary h5" style="cursor: pointer; border: none;" onclick="location.href='album_write.do'" />&nbsp;&nbsp;&nbsp;&nbsp;
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