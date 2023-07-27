<%@page import="com.petopia.model.MemberTO"%>
<%@page import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="../../css/board_write.css">
</head>
<body>
<script type="text/javascript">
<%
if( userData == null){%>
Swal.fire('제한된 접근', '정상적인 경로를 이용해주세요', 'error').then(function(){history.back();});
<%}else{
	int flag = (Integer)request.getAttribute("flag");
	int file_flag = (Integer)request.getAttribute("file_flag");
	int cmt_flag = (Integer)request.getAttribute("cmt_flag");
	
	if(flag != 0){%>
	Swal.fire('게시물 삭제에 실패했습니다', '', 'error').then(function(){history.back();});
	<%}else{ %>
	Swal.fire('게시물을 삭제했습니다', '', 'success').then(function(){location.href='freeboard_list.do';});
	<%}
}
%>
</script>
</body>
</html>
