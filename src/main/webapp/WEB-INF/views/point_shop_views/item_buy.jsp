<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding( "utf-8" );
	String itemCategory = (String)request.getAttribute( "itemCategory" );
	String ps_seq = (String)request.getAttribute( "ps_seq" );
	
	 if( itemCategory.equals("0") ) {
		response.sendRedirect( "point_shop_badge.do" );
	 } else {
	 	response.sendRedirect( "skin_view.do?ps_seq=" + ps_seq );
	 }
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
Hello
</body>
</html>