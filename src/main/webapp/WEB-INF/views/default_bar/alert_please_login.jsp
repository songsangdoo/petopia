<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
out.println("<script>");
out.println("alert('제한된 접근입니다');");
out.println("history.back();");
out.println("</script>");

%>