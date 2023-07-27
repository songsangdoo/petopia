<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	int q_seq = Integer.parseInt(request.getParameter("q_seq"));
	
%>
<!DOCTYPE html>
<html>
<head>
<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>
<meta charset="UTF-8">
</head>
<body>
<script type="text/javascript">
<%	

if(request.getAttribute("ans_select_flag") != null){
	int ans_select_flag = (Integer)request.getAttribute("ans_select_flag");
	
	if(ans_select_flag == 0){%>
		Swal.fire('답글을 채택했습니다', '', 'success').then(function(){location.href = 'question_view.do?q_seq=<%= q_seq %>';});
	<%}else{ %>
		Swal.fire('답글 채택에 실패했습니다', '', 'error').then(function(){location.href = 'question_view.do?q_seq=<%= q_seq %>';});
	<%}
}

if(request.getAttribute("ans_del_flag") != null){
	int ans_del_flag = (Integer)request.getAttribute("ans_del_flag");
	if(ans_del_flag == 0){ %>
		Swal.fire('답글을 삭제했습니다', '', 'success').then(function(){location.href = 'question_view.do?q_seq=<%= q_seq %>';});
	<%}else{ %>
		Swal.fire('답글 삭제에 실패했습니다', '', 'error').then(function(){location.href = 'question_view.do?q_seq=<%= q_seq %>';});
	<%}
}

%>
</script>
</body>
</html>