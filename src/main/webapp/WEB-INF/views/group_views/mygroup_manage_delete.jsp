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
		out.println("alert('���Ƹ� ��� ������ �Ⱒ�߽��ϴ�.');");
		out.println("location.href='mygroup_manage.do?gr_seq="+gr_seq+"&jg_grade="+user_grade+"';");
	}else{
		out.println("alert('���Ƹ� ��� ���αⰢ�� �����߽��ϴ�.');");
		out.println("history.back();");
	}
	out.println("</script>");
 
 %>