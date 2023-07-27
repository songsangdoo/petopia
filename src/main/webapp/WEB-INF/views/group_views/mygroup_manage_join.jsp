<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%
 	request.setCharacterEncoding("utf-8");
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
  	String member_seq = userData.getM_seq();
 	int flag = (Integer)request.getAttribute("flag");
 	int flag2 = (Integer)request.getAttribute("flag2");
 	String gr_seq = request.getParameter("gr_seq");

 	int user_grade =Integer.parseInt(request.getParameter("user_grade"));
 	out.println("<script type='text/javascript'>");
 	if(flag == 0 && flag2 ==0){
		out.println("alert('동아리 가입승인되었습니다.');");
		out.println("location.href='mygroup_manage.do?gr_seq="+gr_seq+"&jg_grade="+user_grade+"';");
	}else{
		out.println("alert('동아리 가압승인실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
   
 %>