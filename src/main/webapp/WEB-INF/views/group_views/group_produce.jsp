<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
 <%
 	request.setCharacterEncoding("utf-8");
 	int flag = (Integer)request.getAttribute("flag");
 	int flag2 = (Integer)request.getAttribute("flag2");
 	out.println("<script type='text/javascript'>");
	if(flag == 0 && flag2 ==0){
		out.println("alert('등록승인되었습니다.');");
		out.println("location.href='master_group.do?';");
	}else{
		out.println("alert('등록승인 실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
 
 %>