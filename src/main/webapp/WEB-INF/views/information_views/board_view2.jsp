<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	int info_seq = Integer.parseInt(request.getParameter("info_seq"));
	
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
	if(request.getAttribute("rec_insert") != null){
		int rec_insert = (Integer)request.getAttribute("rec_insert");
		
		if(rec_insert == 0){ %>
		Swal.fire('게시물을 추천했습니다', '', 'success').then(function(){location.href = 'information_view.do?info_seq=<%= info_seq %>';});
		<%}else{%>
		Swal.fire('게시물 작성자 또는 이미 추천한 회원은 추천할 수 없습니다', '', 'warning').then(function(){history.back();});
		<%}
	}
%>
</script>
</body>
</html>