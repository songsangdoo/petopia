<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding( "utf-8" );
	int flag = (int) request.getAttribute( "flag" );
	
	System.out.println( "flag값 : " + flag );
	
	if( flag == 1 ) {
		response.sendRedirect( "mypage.do" );
		System.out.println( "스킨 장착 완료 (성공!)" );
	} else {
		System.out.println( "페이지 이동 실패" );
	}

%>
