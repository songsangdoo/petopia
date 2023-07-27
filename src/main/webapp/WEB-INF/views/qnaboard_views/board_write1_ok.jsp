<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
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
		if(userData != null){
			int flag = (Integer)request.getAttribute("flag");
			int file_flag = (Integer)request.getAttribute("file_flag");
			if(flag == 0){%>
					Swal.fire('게시물을 작성했습니다', '', 'success').then(function(){location.href='qnaboard_list.do';});
			<%}
		}else{%>
			Swal.fire('제한된 접근', '정상적인 경로를 이용해주세요', 'error').then(function(){history.back();});
		<%
		}
	%>
</script>
</body>
</html>