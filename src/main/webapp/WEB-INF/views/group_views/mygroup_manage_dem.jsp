<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
 <%
 	request.setCharacterEncoding("utf-8");	
 	int flag = (Integer)request.getAttribute("flag");
 	int user_grade =Integer.parseInt(request.getParameter("user_grade"));
 	String gr_seq = request.getParameter("gr_seq");
 	out.println("<script type='text/javascript'>");
	if(flag == 0){
		out.println("alert('동아리멤버 등급이 강등되었습니다');");
		out.println("location.href='mygroup_manage.do?gr_seq="+gr_seq+"&jg_grade="+user_grade+"';");
	}else{
		out.println("alert('동아리멤버 등급 강등에 실패했습니다');");
		out.println("history.back();");
	}
	out.println("</script>");
 
 %>