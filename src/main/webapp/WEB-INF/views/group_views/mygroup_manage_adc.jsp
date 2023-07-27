<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	request.setCharacterEncoding("utf-8");	
 	int flag = (Integer)request.getAttribute("flag");
 	int user_grade =Integer.parseInt(request.getParameter("user_grade"));
 	String gr_seq = request.getParameter("gr_seq");
 	out.println("<script type='text/javascript'>");
	if(flag == 0){ 
		out.println("alert('동아리멤버 등급을 승격되었습니다');");
		out.println("location.href='mygroup_manage.do?jg_grade="+user_grade+"&gr_seq="+gr_seq+"';");
	}else{
		out.println("alert('동아리멤버 등급을 승격 실패했습니다');");
		out.println("history.back();");
	}
	out.println("</script>");
 
 %>