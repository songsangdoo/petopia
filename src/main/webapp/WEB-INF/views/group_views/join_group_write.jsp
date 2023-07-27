<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%
	MemberTO userData = (MemberTO) session.getAttribute("loginMember"); 
	
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script type="text/javascript">
       window.onload = function() {
		document.getElementById( 'submit1' ).onclick = function() {
			
			if ( document.wfrm.jg_explain.value.trim() == '' ) {
				alert( '신청서를 입력해주세요' );
				return false;
			} 
			document.wfrm.submit();
		}
	}	
</script>
<link href="assets/css/writeFooter.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	<%-- <form action="join_group_write_ok.do" method="post" name="wfrm" enctype="multipart/form-data"> 쓰기 수정전
		<input type="hidden" name="m_seq" value="<%=request.getParameter("m_seq") %>">
		<input type="hidden" name="gr_seq" value="<%=request.getParameter("gr_seq") %>">
		<div>	
			<div>
				<table>
				<tr>
					<th>신청인</th>
					<td colspan="3"><input type="text" name="jg_writer" value=""  maxlength="10" /></td>
				</tr>
				<tr>
					<th>신청서</th>
					<td colspan="3"><textarea name="jg_explain"></textarea></td>
				</tr>
				</table>
			</div>
			<div>
				<div>
					<input type="button" id="submit1" value="신청" />
				</div>
			</div>
		</div>
	</form> --%>
	
	<section id="img-write">
		
		<div class="container w-75 mt-3">

		<div class="section-title">
			<h2>동아리 신청서</h2>
		</div>
		
		<form action="join_group_write_ok.do" method="post" name="wfrm" enctype="multipart/form-data">
		<input type="hidden" name="gr_seq" value="<%=request.getParameter("gr_seq") %>">
		<div class="input-group mb-3">
			<span class="input-group-text">신청인</span>
			<input type="text"class="form-control" name="jg_writer" value="<%=userData.getM_nickname() %>"  maxlength="10" disabled/>
		</div>

		<div class="input-group mb-3">
			<span class="input-group-text">동아리신청 지원서</span>
			<textarea class="form-control" name="jg_explain" rows="10" style="resize: none;"></textarea>
		</div>
		
		<div class="d-flex flex-row-reverse">
		<div class="p-2">
		<input type="button" class="btn btn-outline-primary" id="submit1" value="신청" />
		</div>
		</div>
		</form>
		
		</div>
		</section>
	
	<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>
</html>