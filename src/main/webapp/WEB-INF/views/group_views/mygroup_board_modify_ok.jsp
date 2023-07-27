<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
	int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
	int flag = (Integer)request.getAttribute("flag");
	int file_flag = (Integer)request.getAttribute("file_flag");
	
	out.println("<script>");
	if(file_flag != 0){
		out.println("alert('파일 업로드 실패');");
		out.println("history.back();");
		if(flag != 0){
			out.println("alert('글쓰기 실패');");
			out.println("history.back();");
		}
	}else{
		out.println("alert('글쓰기 수정 성공');");
		out.println("location.href='mygroup_board_view.do?grb_seq=" + grb_seq + "&gr_seq="+gr_seq+"';");
	}
	out.println("</script>");
%>