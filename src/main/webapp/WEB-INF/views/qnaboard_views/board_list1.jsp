<%@page import="java.util.ArrayList"%>
<%@page import="com.petopia.board.qna.model.QnABoardFileTO"%>
<%@page import="com.petopia.board.qna.model.QnABoardTO"%>
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
	
	List<QnABoardTO> datas = (List)request.getAttribute("datas");
	List<QnABoardFileTO> file_datas = (List)request.getAttribute("file_datas");
	
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
		String dt = datas.get(i).getQa_dt();
		String dt_formatted = dt.substring(0, 4) + "-" + dt.substring(4, 6) + "-" + dt.substring(6, 8);
		String dt_time = dt.substring(8, 10) + " : " + dt.substring(10, 12);
		
		List<QnABoardFileTO> file_list = new ArrayList<>();

		for(int j = 0; j < file_datas.size(); j++){
			if(file_datas.get(j).getQa_seq() == datas.get(i).getQa_seq()){
				file_list.add(file_datas.get(j));
			}
		}
		
		sbHtml.append("<tr id='qna_subject" + i + "'>");
		sbHtml.append("<td>&nbsp;</td>");
		if(datas.get(i).getQa_answer_check().equals("Y")){
			sbHtml.append("<td align='center'><strong><span style='color: #0d6efd;'>[답변완료]</span></strong></td>");
		}else{
			sbHtml.append("<td align='center'><strong><span style='color: #dc3545;'>[답변예정]</span></strong></td>");
		}
		if(datas.get(i).getQa_secret().equals("Y")){
			sbHtml.append("<td colspan='3' onclick='clickSecret(" + i + ", " + datas.get(i).getQa_password() + ")'>");
			sbHtml.append("&nbsp;&nbsp;&nbsp;&nbsp;<strong>" + datas.get(i).getQa_subject() + "</strong>");
			sbHtml.append("&nbsp;&nbsp;<span><img src='../../images/qna_secret_small.png' style='vertical-align: baseline;'></span>");
		}else{
			sbHtml.append("<td colspan='3' onclick='clickSubject(" + i + ")'>");
			sbHtml.append("&nbsp;&nbsp;&nbsp;&nbsp;<strong>" + datas.get(i).getQa_subject() + "</strong>");
		}
		sbHtml.append("</td>");
		sbHtml.append("<td align='center'>" + datas.get(i).getM_nickname() + "</td>");
		sbHtml.append("<td align='center'>" + dt_formatted + "</td>");
		sbHtml.append("</tr>");
		
		sbHtml.append("<tr id='qna_content" + i + "'  style='display: none;'>");
		
		sbHtml.append("<td align='center' colspan='2'><strong>내  용</strong></td>");
		sbHtml.append("<td colspan='6'>");
		sbHtml.append("<div class='container'>");
		sbHtml.append("<p><strong>" + datas.get(i).getQa_category_name() + "</strong><br><br>" + datas.get(i).getQa_content() + "</p>");
		sbHtml.append("</div>");

		sbHtml.append("<br>");
		
		sbHtml.append("<div class='d-flex justify-content-left'>");
		sbHtml.append("<div id='carouselExampleFade' class='carousel slide carousel-fade'>");
		sbHtml.append("<div class='carousel-inner'>");
		
		for(int k = 0; file_list != null && k < file_list.size(); k++){
			if(k == 0){
				sbHtml.append("<div class='carousel-item active'>");
			}else{
				sbHtml.append("<div class='carousel-item'>");
			}
			sbHtml.append("<img src='../../upload/" + file_list.get(k).getQa_file_img_path() + "' class='d-block' width='100%' style='border-radius: 17px;'>");
			sbHtml.append("</div>");	
		}
		
		sbHtml.append("</div>");
		sbHtml.append("<button class='carousel-control-prev' type='button' data-bs-target='#carouselExampleFade' data-bs-slide='prev'>");
		sbHtml.append("<span class='carousel-control-prev-icon' aria-hidden='true'></span>");
		sbHtml.append("<span class='visually-hidden'>Previous</span>");
		sbHtml.append("</button>");
		sbHtml.append("<button class='carousel-control-next' type='button' data-bs-target='#carouselExampleFade' data-bs-slide='next'>");
		sbHtml.append("<span class='carousel-control-next-icon' aria-hidden='true'></span>");
		sbHtml.append("<span class='visually-hidden'>Next</span>");
		sbHtml.append("</button>");
		sbHtml.append("</div>");
		sbHtml.append("</div>");
		
		if(userData != null && datas.get(i).getM_seq() == Integer.parseInt(userData.getM_seq())){
			sbHtml.append("<div class='cmt-btn-group' align='right'>");
			if(datas.get(i).getQa_answer_check().equals("N")){
				sbHtml.append("&nbsp;<button type='button' class='btn btn-outline-primary' style='cursor: pointer;' onclick='location.href=\"qnaboard_modify.do?qa_seq=" + datas.get(i).getQa_seq() + "\"'>수    정</button>");
			}
			sbHtml.append("&nbsp;<button type='button' class='btn btn-outline-danger' style='cursor: pointer;' onclick='post_del(" + datas.get(i).getQa_seq() + ")'>삭	제</button>");
			sbHtml.append("</div>");
		}else if(userData != null && userData.getGrade_seq().equals("1")){
			sbHtml.append("<div class='cmt-btn-group' align='right'>");
			sbHtml.append("<button type='button' class='btn btn-outline-primary' style='cursor: pointer;' onclick='location.href=\"qnaboard_answer.do?qa_seq=" + datas.get(i).getQa_seq() + "\"'>답변하기</button>");
			sbHtml.append("&nbsp;<button type='button' class='btn btn-outline-danger' style='cursor: pointer;' onclick='post_del(" + datas.get(i).getQa_seq() + ")'>삭	제</button>");
			sbHtml.append("</div>");
		}
		
		sbHtml.append("</td>");
		
		sbHtml.append("</tr>");
		
		if(datas.get(i).getQa_answer_check().equals("Y")){
			String answer_dt = datas.get(i).getQa_answer_dt();
			String answer_dt_formatted = dt.substring(0, 4) + "-" + dt.substring(4, 6) + "-" + dt.substring(6, 8);
			
			sbHtml.append("<tr class='table-secondary' id='qna_answer" + i + "' style='display: none;'>");
			sbHtml.append("<td align='center' colspan = '2'><strong>답  변</strong></td>");
			sbHtml.append("<td colspan='4'>");
			sbHtml.append("<div class='container'>");
			sbHtml.append("<p>" + datas.get(i).getQa_answer_content() + "</p>");
			sbHtml.append("<br>");
			sbHtml.append("</div>");
			sbHtml.append("</td>");
			sbHtml.append("<td>" + answer_dt_formatted + "</td>");
			sbHtml.append("</tr>");
		}
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
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='qnaboard_list.do?cpage=" + (cpage - 1) + "'> &lt; </a></li>");
	}
	for(int i = startBlock; i <= endBlock; i++){
		if(i == cpage){
			pageSbHtml.append("<li class='page-item active'><a class='page-link'>" + i + "</a></li>");
		} else{
			pageSbHtml.append("<li class='page-item'><a class='page-link' href='qnaboard_list.do?cpage=" + i + "'>" + i + "</a></li>");
		}
	}
	
	if(cpage == totalPage){
		pageSbHtml.append("<li class='page-item'><a class='page-link'> &gt; </a></li>");
	}else{
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='qnaboard_list.do?cpage=" + (cpage + 1) + "'> &gt; </a></li>");
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
		
		var answerRow = document.getElementById('qna_answer' + index);
		
		if(answerRow != null){
			if(answerRow.style.display == 'none'){
				answerRow.style.display = '';
			}else{
				answerRow.style.display = 'none';
			}
		}
	}
	
	function clickSecret(index, password) {
		var contentRow = document.getElementById('qna_content' + index);
		var answerRow = document.getElementById('qna_answer' + index);
		
		if(contentRow.style.display == 'none'){
			
			if(<%= userData != null && userData.getGrade_seq().equals("1") %>){
				clickSubject(index);
			}else {
				var passwordCheck = prompt('비밀번호를 입력하세요');
				
				if(passwordCheck == null){
					return false;
				}
				
				if(passwordCheck == password){
					clickSubject(index);
				}else if(passwordCheck != password){
					alert('비밀번호를 확인해주세요');
				}
			}
			
		}else{
			clickSubject(index);
		}
	}
	
	function post_del(qa_seq){
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
				  location.href='qnaboard_delete_ok.do?qa_seq=' + qa_seq;
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
					<h2>QNA</h2>
				</div>
				<div class="container w-80">
				
				<table class="table table-hover ">
				  <thead>
				  	<tr>
				  		<th colspan='2' style="vertical-align: middle;"><span >총 <%= datas.size() %> 개</span></th>	
				  		<td colspan=4>
						</td>
				  		<td align='right'>
							<div class="dropdown">
							  <a class="btn btn-outline-primary dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="border:none; color: black;">
							  <c:if test='<%= order_flag != null && order_flag.equals("all") %>'>전체보기</c:if>
							  <c:if test='<%= order_flag != null && order_flag.equals("answer_yet") %>'>답변예정</c:if>
							  <c:if test='<%= order_flag != null && order_flag.equals("no_secret") %>'>비밀글 제외</c:if>
							  <c:if test='<%= order_flag != null && order_flag.equals("myList") && userData != null %>'>내가 쓴 글 보기</c:if>
							  <c:if test='<%= order_flag == null %>'>정렬</c:if>
							  </a>
							
							  <ul class="dropdown-menu">
							    <li><a class="dropdown-item" href="qnaboard_list.do?order_flag=all">전체보기</a></li>
							    <li><a class="dropdown-item" href="qnaboard_list.do?order_flag=answer_yet">답변예정</a></li>
							    <li><a class="dropdown-item" href="qnaboard_list.do?order_flag=no_secret">비밀글 제외</a></li>
							    <c:if test="<%= userData != null %>">
								    <li><a class="dropdown-item" href="qnaboard_list.do?order_flag=myList">내가 쓴 글 보기</a></li>
							    </c:if>
							  </ul>
							</div>
						</td>
				  	</tr>
				    <tr align="center">
						<th width='3%'></th>				    
						<th width='8%'>답변여부</th>				    
						<th colspan='3'>제목</th>				    
						<th width='10%'>작성자</th>				    
						<th width='10%'>작성일</th>				    
				    </tr>
				  </thead>
				  <tbody>
					<%= sbHtml %>
				  </tbody>
				</table>
				</div>
				
				<!-- 버튼 -->
				<c:if test='<%= userData != null %>'>
				<div align="right">
					<input type="button" value="작성하기" class="btn btn-outline-primary" style="cursor: pointer; border:none;" onclick="location.href='qnaboard_write.do'" />&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				</c:if>	
				<!-- 버튼 끝 -->	
				
			</div>
			
			<br>
			
			<!--=== 페이지네이션 ===-->
			<%= pageSbHtml %>
			<!--=== 페이지네이션 끝===-->
			
			<br>
			
		</section>
		<!-- End Portfolio Section -->
	</main>

	<!-- <div id="right-nav" class="r-navbar">
	
	<!-- End #main -->

<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>

</html>