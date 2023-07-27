<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   
 <%
 	request.setCharacterEncoding("utf-8");
 	String member_seq = request.getParameter("m_seq");
 	int flag = (Integer)request.getAttribute("flag");
 	out.println("<script type='text/javascript'>");
	if(flag == 0){
		out.println("alert('동아리 승인을 기각했습니다.');");
		out.println("location.href='master_group.do?';");
	}else{
		out.println("alert('동아리 승인기각에 실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
 
 %>