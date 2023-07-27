<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	int ab_seq = Integer.parseInt(request.getParameter("ab_seq"));
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
	if(request.getAttribute("rec_insert") != null){
		int rec_insert = (Integer)request.getAttribute("rec_insert");
		
		if(rec_insert == 0){ %>
		Swal.fire('게시물을 추천했습니다', '', 'success').then(function(){location.href='album_view.do?ab_seq=<%= ab_seq%>';});
		<%}else{%>
		Swal.fire('게시물 작성자 또는 이미 추천한 회원은 추천할 수 없습니다', '', 'warning').then(function(){history.back();});
		<%}
	}
	
	if(request.getAttribute("cmt_write_flag") != null){
		int cmt_write_flag = (Integer)request.getAttribute("cmt_write_flag");
		
		if(cmt_write_flag == 0){ %>
		Swal.fire('댓글을 작성했습니다', '', 'success').then(function(){location.href='album_view.do?ab_seq=<%= ab_seq%>';});
		<%}else{%>
		Swal.fire('댓글을 작성에 실패했습니다', '', 'error').then(function(){location.href='album_view.do?ab_seq=<%= ab_seq%>';});
		<%}
	}
	
	if(request.getAttribute("rec_cmt_check_flag") != null){
		int rec_cmt_check_flag = (Integer)request.getAttribute("rec_cmt_check_flag");
		
		if(rec_cmt_check_flag == 0){%>
		Swal.fire('댓글을 추천했습니다', '', 'success').then(function(){location.href='album_view.do?ab_seq=<%= ab_seq%>';});
		<%}else{%>
		Swal.fire('댓글 작성자 또는 이미 추천한 회원은 추천할 수 없습니다', '', 'warning').then(function(){history.back();});
		<%}
	}
	
	if(request.getAttribute("rec_cmt_del_check_flag") != null){
		int rec_cmt_del_check_flag = (Integer)request.getAttribute("rec_cmt_del_check_flag");
		
		if(rec_cmt_del_check_flag == 0){%>
		Swal.fire('댓글을 삭제했습니다', '', 'success').then(function(){location.href='album_view.do?ab_seq=<%= ab_seq%>';});
		<%}else{%>
		Swal.fire('댓글을 삭제에 실패했습니다', '', 'error').then(function(){location.href='album_view.do?ab_seq=<%= ab_seq%>';});
		<%}
	}%>
	
</script>
</body>
</html>