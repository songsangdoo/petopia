<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
	int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
	if(request.getAttribute("rec_insert") != null){
		int rec_insert = (Integer)request.getAttribute("rec_insert");
		
		if(rec_insert == 0){
			out.println("<script type='text/javascript'>");
			out.println("alert('게시물을 추천했습니다');");
			out.println("</script>");
		}else{
			out.println("<script type='text/javascript'>");
			out.println("alert('게시물 작성자 또는 이미 추천한 회원은 추천할 수 없습니다');");
			out.println("</script>");
		}
	}
	
	if(request.getAttribute("cmt_write_flag") != null){
		int cmt_write_flag = (Integer)request.getAttribute("cmt_write_flag");
		
		if(cmt_write_flag == 0){
			out.println("<script type='text/javascript'>");
			out.println("alert('댓글을 작성했습니다');");
			out.println("</script>");
		}else{
			out.println("<script type='text/javascript'>");
			out.println("alert('댓글 작성에 실패했습니다');");
			out.println("</script>");
		}
	}
	
	if(request.getAttribute("rec_cmt_check_flag") != null){
		int rec_cmt_check_flag = (Integer)request.getAttribute("rec_cmt_check_flag");
		
		if(rec_cmt_check_flag == 0){
			out.println("<script type='text/javascript'>");
			out.println("alert('댓글을 추천했습니다');");
			out.println("</script>");
		}else{
			out.println("<script type='text/javascript'>");
			out.println("alert('댓글 작성자 또는 이미 추천한 회원은 추천할 수 없습니다');");
			out.println("</script>");
		}
	}
	
	if(request.getAttribute("rec_cmt_del_check_flag") != null){
		int rec_cmt_del_check_flag = (Integer)request.getAttribute("rec_cmt_del_check_flag");
		
		if(rec_cmt_del_check_flag == 0){
			out.println("<script type='text/javascript'>");
			out.println("alert('댓글을 삭제했습니다');");
			out.println("</script>");
		}else{
			out.println("<script type='text/javascript'>");
			out.println("alert('댓글 삭제에 실패했습니다');");
			out.println("</script>");
		}
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
	location.href = 'mygroup_board_view.do?grb_seq=<%= grb_seq %>&gr_seq=<%=gr_seq%>';
</script>
</head>
<body>

</body>
</html>