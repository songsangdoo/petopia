<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");
    int flag = (int) request.getAttribute("flag");
    out.println("<script>");
    System.out.print("시그널 : " + flag);
    if (flag == 0) {
    	out.println("alert('계정 삭제에 성공햇습니다.')");
        response.sendRedirect("index.do");
    } else if (flag == 1) {
        out.println("alert('계정 삭제에 실패햇습니다.')");
        out.println("history.back()");
    } else {
    	out.println("alert('계정 삭제에 실패햇습니다.");
    	out.println("history.back()");
    }
    out.println("</script>");
%>

