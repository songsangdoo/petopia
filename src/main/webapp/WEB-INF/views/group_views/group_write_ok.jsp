<%@page import="org.springframework.web.multipart.MultipartRequest"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

 <%
 	request.setCharacterEncoding("utf-8");
 
 	int flag = (Integer)request.getAttribute("flag");
 	out.println("<script type='text/javascript'>");
	if(flag == 0){
		out.println("alert('신청되었습니다.');");
		out.println("location.href='group_list.do?';");
	}else{
		out.println("alert('신청에 실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
 
 %>