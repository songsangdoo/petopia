<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");
    int flag = (int) request.getAttribute("flag");
    out.println("<script>");
    System.out.print("시그널 : " + flag);
    if (flag == 0) {
        response.sendRedirect("mypage.do");
    } else if (flag == 1) {
        out.println("alert('반려동물을 4마리 이상 등록하실수 없습니다.')");
        out.println("history.back()");
    } else if (flag == 2) {
    	out.println("alert('등록 실패')");
    	out.println("history.back()");
    } else {
    	out.println("alert('등록 실패')");
    	out.println("history.back()");
    }
    out.println("</script>");
%>

