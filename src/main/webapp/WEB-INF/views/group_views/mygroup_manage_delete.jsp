<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" %>
 <%
 	request.setCharacterEncoding("utf-8");	
 	int flag = (Integer)request.getAttribute("flag");
 	int user_grade =Integer.parseInt(request.getParameter("user_grade"));
 	String gr_seq = request.getParameter("gr_seq");
 	out.println("<script type='text/javascript'>");
	if(flag == 0){
		out.println("alert('동아리 멤버 승인을 기각했습니다.');");
		out.println("location.href='mygroup_manage.do?gr_seq="+gr_seq+"&jg_grade="+user_grade+"';");
	}else{
		out.println("alert('동아리 멤버 승인기각에 실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
 
 %>