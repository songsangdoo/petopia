<%@page import="com.petopia.board.tip.model.TipBoardTO"%>
<%@page import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@page import="com.petopia.board.album.model.AlbumTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");
	
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	if( userData != null){
		int m_seq = Integer.parseInt(userData.getM_seq());
	}
	
	List<TipBoardTO> datas = (List)request.getAttribute("datas");
	
	int cpage = 1;
	if (request.getParameter("cpage") != null) {
		cpage = Integer.parseInt(request.getParameter("cpage"));
	}
	
	int recordsPerpage = 10;
	
	int totalRecords = datas.size();
	
	int totalPage = (totalRecords - 1) / recordsPerpage + 1;
	
	int startNum = (cpage - 1) * recordsPerpage;
	
	int pagePerBlock = 5;
	
	StringBuilder sbHtml = new StringBuilder();
	
	for (int i = startNum; i < datas.size() && i < startNum + recordsPerpage; i++) {
		String dt = datas.get(i).getTb_dt();
		String dt_formatted = dt.substring(0, 4) + "-" + dt.substring(4, 6) + "-" + dt.substring(6, 8);
		String dt_time = dt.substring(8, 10) + " : " + dt.substring(10, 12);
		
		boolean newIcon = false;
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String nowStr = format.format(now);
		
		int intDt = Integer.parseInt(dt.substring(0, 4) + dt.substring(4, 6) + dt.substring(6, 8));
		int intNow = Integer.parseInt(nowStr);

		if(intNow - intDt < 1){
			newIcon = true;
		}
		
		sbHtml.append("<tr>");
		sbHtml.append("<td>&nbsp;</td>");
		sbHtml.append("<td align='center'>" + datas.get(i).getTb_seq() + "</td>");
		sbHtml.append("<td>");
		sbHtml.append("<a href='tipboard_view.do?tb_seq=" + datas.get(i).getTb_seq() + "' style='color: black;'>");
		sbHtml.append("&nbsp;&nbsp;&nbsp;&nbsp;<strong>" + datas.get(i).getTb_subject() + "</strong>");
		if(datas.get(i).getTb_cmt_cnt() != 0){
			sbHtml.append("&nbsp;&nbsp;<span>[" + datas.get(i).getTb_cmt_cnt() + "]</span>");
		}
		if(newIcon){
			sbHtml.append("&nbsp;&nbsp;<span style='color: red;'>[new]");
		}
		sbHtml.append("</a>");
		sbHtml.append("</td>");
		sbHtml.append("<td align='center'>" + datas.get(i).getM_nickname() + "</td>");
		if(newIcon){
			sbHtml.append("<td align='center'>" + dt_time + "</td>");
		}else{
			sbHtml.append("<td align='center'>" + dt_formatted + "</td>");
		}
		sbHtml.append("<td align='center'>" + datas.get(i).getTb_hit() + "</td>");
		sbHtml.append("<td align='center'>" + datas.get(i).getTb_rec_cnt() + "</td>");
		sbHtml.append("</tr>");
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
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='tipboard_list.do?cpage=" + (cpage - 1) + "'> &lt; </a></li>");
	}
	for(int i = startBlock; i <= endBlock; i++){
		if(i == cpage){
			pageSbHtml.append("<li class='page-item active'><a class='page-link'>" + i + "</a></li>");
		} else{
			pageSbHtml.append("<li class='page-item'><a class='page-link' href='tipboard_list.do?cpage=" + i + "'>" + i + "</a></li>");
		}
	}
	
	if(cpage == totalPage){
		pageSbHtml.append("<li class='page-item'><a class='page-link'> &gt; </a></li>");
	}else{
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='tipboard_list.do?cpage=" + (cpage + 1) + "'> &gt; </a></li>");
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
		<section>
			<div class="container">
				<div class="section-title" data-aos="fade-up">
					<h2>팁 게시판</h2>
				</div>
				<div class="container w-80">
				
				<table class="table table-hover ">
				  <thead>
				  	<tr>
				  		<td></td>	
				  		<th colspan=2 style="vertical-align: middle;"><span >총 <%= datas.size() %> 개</span></th>	
				  		<td colspan=3>
						</td>
				  		<td>
							<div class="dropdown">
							  <a class="btn btn-outline-primary dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="border:none; color: black;">
							    <c:if test='<%= order_flag != null && order_flag.equals("latest") %>'>최신순</c:if>
								<c:if test='<%= order_flag != null && order_flag.equals("rec") %>'>추천순</c:if>
								<c:if test='<%= order_flag != null && order_flag.equals("cmt") %>'>댓글순</c:if>
								<c:if test='<%= order_flag != null && order_flag.equals("hit") %>'>조회수순</c:if>
								<c:if test='<%= order_flag == null %>'>정렬</c:if>
							  </a>
							
							  <ul class="dropdown-menu">
							    <li><a class="dropdown-item" href="tipboard_list.do?order_flag=latest">최신순</a></li>
							    <li><a class="dropdown-item" href="tipboard_list.do?order_flag=rec">추천순</a></li>
							    <li><a class="dropdown-item" href="tipboard_list.do?order_flag=cmt">댓글순</a></li>
							    <li><a class="dropdown-item" href="tipboard_list.do?order_flag=hit">조회수순</a></li>
							  </ul>
							</div>
						</td>
				  	</tr>
				    <tr align="center">
						<th width='3%'>&nbsp;</th>				    
						<th width='8%'>번호</th>				    
						<th>제목</th>				    
						<th width='10%'>작성자</th>				    
						<th width='10%'>작성일</th>				    
						<th width='6%'>조회수</th>				    
						<th width='6%'>추천수</th>				    
				    </tr>
				  </thead>
				  <tbody>
					<%= sbHtml %>
				  </tbody>
				</table>
				</div>
				
				<!-- 버튼 -->
				<c:if test="<%= userData != null %>">
				<div align="right">
					<input type="button" value="글 쓰기" class="btn btn-outline-primary" style="cursor: pointer; border:none;" onclick="location.href='tipboard_write.do'" />&nbsp;&nbsp;&nbsp;&nbsp;
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
			<form action="tipboard_list.do" method="post">
			<div class="container w-50">
			<div class="input-group mb-3">
			<div  style="width: 100px;">
				<select class="form-select" style="border-radius: 0px;"  name="option">
	  				<option value="subject" selected>제목</option>
	  				<option value="content">내용</option>
	  				<option value="writer">작성자</option>
				</select>
			</div>
			  <input type="text" class="form-control" placeholder="검색어를 입력하세요" aria-label="Recipient's username" aria-describedby="button-addon2" name="searchStr" style="border:1px solid #dee2e6; border-radius: 0px; color:black;">
			  <button class="btn btn-outline-secondary" type="submit" style="border:1px solid #dee2e6; border-radius: 0px; color:black;">검색하기</button>
			</div>
			</div>
			</form>
			<!-- // 검색 -->
			
		</section>
		<!-- End Portfolio Section -->
	</main>

	<!-- <div id="right-nav" class="r-navbar">
	
	<!-- End #main -->

<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>

</html>