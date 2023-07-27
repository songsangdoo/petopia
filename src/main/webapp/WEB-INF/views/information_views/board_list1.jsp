<%@page import="com.petopia.model.MemberTO"%>
<%@page import="com.petopia.board.information.model.InformationTO"%>
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

	List<InformationTO> datas = (List) request.getAttribute("datas");
	
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
		String dt = datas.get(i).getInfo_dt();
		String dt_formatted = dt.substring(0, 4) + "-" + dt.substring(4, 6) + "-" + dt.substring(6, 8);
		
		if (count % 3 == 1) {
			sbHtml.append("<div class='row'>");
		}
		
		sbHtml.append("<div class='col-4'>");
		sbHtml.append("<div class='post-slide'>");
		sbHtml.append("<div class='post-img'>");
		sbHtml.append("<img src='../../upload/" + datas.get(i).getInfo_rep_img_path() + "' style='height:20rem; object-fit: cover;'> <a href='information_view.do?info_seq=" + datas.get(i).getInfo_seq() + "' class='over-layer'><i");
		sbHtml.append("class='fa fa-link'></i></a>");
		sbHtml.append("</div>");
		sbHtml.append("<div class='post-content'>");
		sbHtml.append("<h3 class='post-title'>");
		sbHtml.append("<a href='information_view.do?info_seq=" + datas.get(i).getInfo_seq() + "'>" + datas.get(i).getInfo_subject() + "</a>");
		sbHtml.append("</h3>");
		sbHtml.append("<p class='post-description'> 조회수 : " + datas.get(i).getInfo_hit() + "&nbsp;&nbsp;&nbsp;&nbsp;추천수 : " + datas.get(i).getInfo_rec_cnt() + "</p>");
		sbHtml.append("<span class='post-date'><i class='fa fa-clock-o'></i>" + dt_formatted + "</span>"); 
		sbHtml.append("</div>");
		sbHtml.append("</div>");
		sbHtml.append("</div>");

		if (count % 3 == 0) {
			sbHtml.append("</div>");
			sbHtml.append("<br><br>");
		}
	}
	
	if(count % 3 !=0){
		sbHtml.append("</div>");
		sbHtml.append("<br><br>");
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
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='information_list.do?cpage=" + (cpage - 1) + "'> &lt; </a></li>");
	}
	for(int i = startBlock; i <= endBlock; i++){
		if(i == cpage){
			pageSbHtml.append("<li class='page-item active'><a class='page-link'>" + i + "</a></li>");
		} else{
			pageSbHtml.append("<li class='page-item'><a class='page-link' href='information_list.do?cpage=" + i + "'>" + i + "</a></li>");
		}
	}
	
	if(cpage == totalPage){
		pageSbHtml.append("<li class='page-item'><a class='page-link'> &gt; </a></li>");
	}else{
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='information_list.do?cpage=" + (cpage + 1) + "'> &gt; </a></li>");
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
		<!-- ======= board Section ======= -->
		<section id="img-board">
			<div class="container w-75">
				<!-- 게시판 이름 -->
				<div class="section-title">
					<h2>지식정보</h2>
				</div>
				
				<div class="container">
				<div class="d-flex justify-content-end">
					<div class="dropdown">
					  <a class="btn btn-outline-primary dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="border:none; color: black;">
						  <c:if test='<%= order_flag != null && order_flag.equals("latest") %>'>최신순</c:if>
						  <c:if test='<%= order_flag != null && order_flag.equals("rec") %>'>추천순</c:if>
						  <c:if test='<%= order_flag != null && order_flag.equals("hit") %>'>조회수순</c:if>
						  <c:if test='<%= order_flag == null %>'>정렬</c:if>
					  </a>
					
					  <ul class="dropdown-menu">
					    <li><a class="dropdown-item" href="information_list.do?order_flag=latest">최신순</a></li>
					    <li><a class="dropdown-item" href="information_list.do?order_flag=rec">추천순</a></li>
					    <li><a class="dropdown-item" href="information_list.do?order_flag=hit">조회수순</a></li>
					  </ul>
					</div>
				</div>
					<%= sbHtml %>
				</div>
				
				<hr class="container w-80">
				<!-- 버튼 -->	
				<c:if test='<%= userData != null && userData.getGrade_seq().equals("1") %>'>
					<div align="right">
						<input type="button" value="작성하기" class="btn btn-outline-primary h5" style="cursor: pointer; border: none;" onclick="location.href='information_write.do'" />&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</c:if>	
				<!-- 버튼 끝 -->	
			</div>
			
			<br>

			<!--=== 페이지네이션 ===-->
			<%= pageSbHtml %>
			<!--=== 페이지네이션 끝===-->
			
			<br>
			
			<!-- 검색 -->
			<form action="information_list.do" method="post">
			<div class="container w-50">
			<div class="input-group mb-3">
			<div  style="width: 100px;">
				<select class="form-select" style="border-radius: 0px;" name="option">
	  				<option value="subject" selected>제목</option>
	  				<option value="content">내용</option>
				</select>
			</div>
			  <input type="text" class="form-control" placeholder="검색어를 입력해 주세요" aria-label="Recipient's username" aria-describedby="button-addon2" name="searchStr" style="border:1px solid #dee2e6; border-radius: 0px; color:black;">
			  <button class="btn btn-outline-secondary" type="submit" style="border:1px solid #dee2e6; border-radius: 0px; color:black;">검색하기</button>
			</div>
			</div>
			</form>
			<!-- // 검색 -->
			
		</section>
		<!-- End Team Section -->
	</main>

	<!-- <div id="right-nav" class="r-navbar">
	
	<!-- End #main -->

<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>

</html>