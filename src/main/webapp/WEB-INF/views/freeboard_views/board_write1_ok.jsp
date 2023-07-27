<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	MemberTO userData = (MemberTO) session.getAttribute("loginMember");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>
</head>
<body>
<script type="text/javascript">
	<%
		if(userData != null){
			int flag = (Integer)request.getAttribute("flag");
			int file_flag = (Integer)request.getAttribute("file_flag");
			int upPointCheck = (Integer)request.getAttribute("upPointCheck");
			int upGradeCheck = (Integer)request.getAttribute("upGradeCheck");
			
			System.out.println(upGradeCheck + " upGradeCheck");
			
			if(flag == 0){
				if(upPointCheck == 0){
				%>
				<%
					switch(upGradeCheck){
					case 1: %>
						Swal.fire('게시물을 작성했습니다', '포인트 10점 획득', 'success').then(function(){
							Swal.fire('[펫토피아 기사]로 승급했습니다', '', 'success').then(function(){location.href='freeboard_list.do';});
						})
					<%
					break;
					case 2: %>
						Swal.fire('게시물을 작성했습니다', '포인트 10점 획득', 'success').then(function(){
							Swal.fire('[펫토피아 장로]로 승급했습니다', '', 'success').then(function(){location.href='freeboard_list.do';});
						});
					<%
					break;
					case 3: %>
						Swal.fire('게시물을 작성했습니다', '포인트 10점 획득', 'success').then(function(){
							Swal.fire('[명예 펫토피아]로 승급했습니다', '', 'success').then(function(){location.href='freeboard_list.do';});
						});
					<%
					break;
					case 4: %>
						Swal.fire('게시물을 작성했습니다', '포인트 10점 획득', 'success').then(function(){
							Swal.fire('[펫토피아 로열스타]로 승급했습니다', '', 'success').then(function(){location.href='freeboard_list.do';});
						});	
					<%
					break;
					default: %>
						Swal.fire('게시물을 작성했습니다', '포인트 10점 획득', 'success').then(function(){location.href='freeboard_list.do';});
					<%
					}
				}else{
				%>					
					Swal.fire('게시물을 작성했습니다', '게시물 작성 포인트는 하루 최대 3회까지 획득할 수 있습니다', 'success').then(function(){location.href='freeboard_list.do';});
				<%
				}
			}
		}else{%>
			Swal.fire('제한된 접근', '정상적인 경로를 이용해주세요', 'error').then(function(){history.back();});
		<%
		}
	%>
	
</script>
</body>
</html>