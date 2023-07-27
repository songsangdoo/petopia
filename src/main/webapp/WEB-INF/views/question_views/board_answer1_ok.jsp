<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	int q_seq = Integer.parseInt(request.getParameter("q_seq"));
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>
</head>
<body>
<script type="text/javascript">
<%
	if( userData == null){%>
		Swal.fire('제한된 접근', '정상적인 경로를 이용해주세요', 'error').then(function(){history.back();});
	<%}else{
		
		int flag = (Integer)request.getAttribute("flag");
		int file_flag = (Integer)request.getAttribute("file_flag");
		
		if(flag == 1){%>
			Swal.fire('글 작성 실패', '', 'error').then(function(){history.back();});
		<%}else if(flag == 2){%>
			Swal.fire('답글 작성 실패', '이미 해당 게시글의 답변을 작성했습니다', 'error').then(function(){location.href='question_view.do?q_seq=<%= q_seq %>';});
		<%}else {%>
			Swal.fire('답글을 작성했습니다', '', 'success').then(function(){location.href='question_view.do?q_seq=<%= q_seq %>';});
		<%}
	}
%>
</script>
</body>
</html>