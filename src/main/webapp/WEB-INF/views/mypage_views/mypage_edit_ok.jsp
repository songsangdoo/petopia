<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding( "utf-8" );
	int flag = (int) request.getAttribute( "flag" );
	
	if( flag == 1 ) {
		response.sendRedirect( "mypage.do" );
	} else {
		System.out.println( "페이지 이동 실패" );
	}

%>
