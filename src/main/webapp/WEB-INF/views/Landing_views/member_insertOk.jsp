<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
int flag =  Integer.parseInt(request.getParameter("flag"));
%>

<script>
	int flag = 0
	if(flag != 0){
		
		alert("회원가입 실패");
		history.back();
		
	}else if(flag == 1){
		alert('회원가입 성공');
		location.href='index.do';
	}
	
</script>






