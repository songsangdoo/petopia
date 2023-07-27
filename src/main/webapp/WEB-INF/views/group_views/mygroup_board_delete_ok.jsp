<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	int flag = (Integer)request.getAttribute("flag");
	int file_flag = (Integer)request.getAttribute("file_flag");
	int cmt_flag = (Integer)request.getAttribute("cmt_flag");
	int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
	
	System.out.println("파일 삭제 : " + file_flag);
	System.out.println("댓글 삭제 : " + cmt_flag);
	
	out.println("<script>");
	if(flag != 0){
		out.println("alert('글 삭제 실패');");
		out.println("history.back();");
	}else{
		out.println("alert('글 삭제 성공');");
		out.println("location.href='mygroup_board_list.do?gr_seq="+gr_seq+"';");
	}
	
	out.println("</script>");
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../../css/board_write.css">
</head>

<body>

</body>
</html>