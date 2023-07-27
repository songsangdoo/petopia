<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	int ms_seq = Integer.parseInt(request.getParameter("ms_seq"));
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
</script>
<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>
</head>
<body>
<script type="text/javascript">
<% 
	if(request.getAttribute("cmt_del_flag") != null){
		int cmt_del_flag = (Integer)request.getAttribute("cmt_del_flag");
		
		if(cmt_del_flag == 0){%>
			Swal.fire('댓글을 삭제했습니다', '', 'success').then(function(){location.href = 'missing_view.do?ms_seq=<%= ms_seq %>';});
		<% }else{%>
			Swal.fire('댓글을 삭제에 실패했습니다', '', 'error').then(function(){location.href = 'missing_view.do?ms_seq=<%= ms_seq %>';});
		<% }
	}
	
	if(request.getAttribute("cmt_write_flag") != null){
		int cmt_write_flag = (Integer)request.getAttribute("cmt_write_flag");
		
		if(cmt_write_flag == 0){%>
			Swal.fire('댓글을 작성했습니다', '', 'success').then(function(){location.href = 'missing_view.do?ms_seq=<%= ms_seq %>';});
		<% }else{%>
			Swal.fire('댓글을 작성에 실패했습니다', '', 'success').then(function(){location.href = 'missing_view.do?ms_seq=<%= ms_seq %>';});
		<% }
	}
%>
</script>
</body>
</html>