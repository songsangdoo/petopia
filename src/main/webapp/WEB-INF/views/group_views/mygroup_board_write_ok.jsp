<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
		request.setCharacterEncoding("utf-8");
		int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
		int flag = (Integer)request.getAttribute("flag");
		int file_flag = (Integer)request.getAttribute("file_flag");
		
		System.out.println("파일 추가 : " + file_flag);
		
		out.println("<script>");
		if(flag != 0){
			out.println("alert('글쓰기 실패');");
			out.println("history.back();");
		}else{
			out.println("alert('글쓰기 성공');");
			out.println("location.href='mygroup_board_list.do?gr_seq="+gr_seq+"';");
		}
		out.println("</script>");
			 
 %>
