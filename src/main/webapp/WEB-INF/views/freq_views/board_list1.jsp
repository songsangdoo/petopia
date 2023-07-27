<%@page import="com.petopia.board.freq.model.QnABoardFreqTO"%>
<%@page import="java.util.ArrayList"%>
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
	
	List<QnABoardFreqTO> datas = (List)request.getAttribute("datas");
	
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
		
		sbHtml.append("<tr id='qna_subject" + i + "'>");
		sbHtml.append("<td align='center'>&nbsp;</td>");
		sbHtml.append("<td align='center'><strong>" + datas.get(i).getQa_category_name() + "</strong></td>");
		sbHtml.append("<td colspan='4' onclick='clickSubject(" + i + ")'>");
		sbHtml.append("&nbsp;&nbsp;&nbsp;&nbsp;<strong> Q.&nbsp;&nbsp;" + datas.get(i).getQa_freq_subject() + "</strong>");
		sbHtml.append("</td>");
		sbHtml.append("<td align='center'><strong>관리자</strong></td>");
		sbHtml.append("</tr>");
		
		sbHtml.append("<tr class='table-secondary' id='qna_content" + i + "'  style='display: none;'>");
		
		sbHtml.append("<td align='center' colspan='2' style='vertical-align: middle;'><strong>답 변</strong></td>");
		sbHtml.append("<td colspan='5'>");
		sbHtml.append("<div class='container'>");
		sbHtml.append("<br>");
		sbHtml.append("<p>" + datas.get(i).getQa_freq_content() + "</p>");
		sbHtml.append("</div>");
		sbHtml.append("<br>");
		
		if(userData != null && userData.getGrade_seq().equals("1")){
			sbHtml.append("<div class='cmt-btn-group' align='right'>");
			sbHtml.append("&nbsp;<button type='button' class='btn btn-outline-primary' style='cursor: pointer;' onclick='location.href=\"freq_modify.do?qa_freq_seq=" + datas.get(i).getQa_freq_seq() + "\"'>수    정</button>");
			sbHtml.append("&nbsp;<button type='button' class='btn btn-outline-danger' style='cursor: pointer;' onclick='post_del(" + datas.get(i).getQa_freq_seq() + ")'>삭	제</button>");
			sbHtml.append("</div>");
		}
		
		sbHtml.append("</td>");
		
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
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='freq_list.do?cpage=" + (cpage - 1) + "'> &lt; </a></li>");
	}
	for(int i = startBlock; i <= endBlock; i++){
		if(i == cpage){
			pageSbHtml.append("<li class='page-item active'><a class='page-link'>" + i + "</a></li>");
		} else{
			pageSbHtml.append("<li class='page-item'><a class='page-link' href='freq_list.do?cpage=" + i + "'>" + i + "</a></li>");
		}
	}
	
	if(cpage == totalPage){
		pageSbHtml.append("<li class='page-item'><a class='page-link'> &gt; </a></li>");
	}else{
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='freq_list.do?cpage=" + (cpage + 1) + "'> &gt; </a></li>");
	}
	pageSbHtml.append("</ul>");
	pageSbHtml.append("</nav>");
	
	String order_flag =	(String)request.getAttribute("order_flag");
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

	function clickSubject(index) {
		var contentRow = document.getElementById('qna_content' + index);
		
		if(contentRow.style.display == 'none'){
			contentRow.style.display = '';
		}else{
			contentRow.style.display = 'none';
		}
	}
	
	
	function post_del(qa_freq_seq){
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
				  location.href='freq_delete_ok.do?qa_freq_seq=' + qa_freq_seq;
			  }else{
				  return false;
			  }
		});
		  
	}
</script>
</head>
<body>
	<!-- 헤더 -->
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	<!-- // 헤더 -->
	
	<main id="main">
		<section>
			<div class="container " data-aos="fade-up">
				<div class="section-title">
					<h2>FAQ</h2>
				</div>
				<div class="container">
				
				<table class="table table-hover ">
				  <thead>
				  	<tr>
				  		<th colspan=2 style="vertical-align: middle;"><span >총 <%= datas.size() %> 개</span></th>	
				  		<td colspan=4>
				  		<td >
				  		<div class="dropdown">
							  <a class="btn btn-outline-primary dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="border:none; color: black;">
							  <c:if test='<%= order_flag != null && order_flag.equals("all") %>'>전체보기</c:if>
							  <c:if test='<%= order_flag != null && order_flag.equals("member") %>'>회원관련</c:if>
							  <c:if test='<%= order_flag != null && order_flag.equals("community") %>'>커뮤니티</c:if>
							  <c:if test='<%= order_flag != null && order_flag.equals("website") %>'>사이트 이용</c:if>
							  <c:if test='<%= order_flag != null && order_flag.equals("eventAndPoint") %>'>이벤트/포인트</c:if>
							  <c:if test='<%= order_flag == null %>'>정렬</c:if>
							  </a>
							
							  <ul class="dropdown-menu">
							    <li><a class="dropdown-item" href="freq_list.do?order_flag=all">전체보기</a></li>
							    <li><a class="dropdown-item" href="freq_list.do?order_flag=member">회원관련</a></li>
							    <li><a class="dropdown-item" href="freq_list.do?order_flag=community">커뮤니티</a></li>
							    <li><a class="dropdown-item" href="freq_list.do?order_flag=website">사이트 이용</a></li>
							    <li><a class="dropdown-item" href="freq_list.do?order_flag=eventAndPoint">이벤트/포인트</a></li>
							  </ul>
							</div>
				  		</td>
				  	</tr>
				    <tr align="center">
						<th width="3%"></th>
						<th width='10%'>분 류</th>				    
						<th colspan='3'>자주 묻는 질문</th>				    
						<th width='10%'></th>				    
						<th width='10%'>작성자</th>				    
				    </tr>
				  </thead>
				  <tbody>
					<%= sbHtml %>
				  </tbody>
				</table>
				</div>
				
				<!-- 버튼 -->
				<c:if test='<%= userData != null && userData.getGrade_seq().equals("1") %>'>
				<div align="right">
					<input type="button" value="글 쓰기" class="btn btn-outline-primary" style="cursor: pointer; border:none;" onclick="location.href='freq_write.do'" />&nbsp;&nbsp;&nbsp;&nbsp;
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
			<form action="freq_list.do" method="post">
			<div class="container w-50">
			<div class="input-group mb-3">
			<div  style="width: 100px;">
				<select class="form-select" style="border-radius: 0px;" name="option">
	  				<option value="subject" selected>제목</option>
	  				<option value="content">내용</option>
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