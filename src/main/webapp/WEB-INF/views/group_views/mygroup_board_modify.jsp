
<%@page import="com.petopia.group.board.model.GroupBoardTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
	int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	if( userData != null){
		int	m_seq = Integer.parseInt(userData.getM_seq());
	}else {
		out.println("<script>");
		out.println("alert('제한된 접근입니다');");
		out.println("location.href='index.do'");
		out.println("</script>");
	}
	
	GroupBoardTO data = (GroupBoardTO)request.getAttribute("data");
	
%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>펫토피아</title>
<meta content="" name="description">
<meta content="" name="keywords">
  
  <script type="text/javascript"> 
  
  window.onload = function() {
		document.getElementById( 'submit1' ).onclick = function() {
			if ( document.wfrm.subject.value.trim() == '' ) {
				alert( '제목을 입력해주세요' );
				return false;
			}
			if ( document.wfrm.content.value.trim() == '' ) {
				alert( '내용을 입력해주세요' );
				return false;
			}
			if ( document.wfrm.uploads.value.trim() == '' ) {
				alert( '파일을 첨부해주세요' );
				return false;
			} 
			
			let check = confirm('글 작성을 완료하시겠습니까?');
			
			if(check != true){
				return false;
			}
			
			document.wfrm.submit();
		}
	}
  
  function setRepThumbnail(event) {
		var rep_img_container = document.getElementById("rep-image-container");
		
		rep_img_container.innerHTML = "";
		
		var reader = new FileReader();
		
		reader.onload = function(event) {
		var img = document.createElement("img");
			
		img.setAttribute("src", event.target.result);
		img.setAttribute("height", "150px");
		img.setAttribute("style", "display:inline-block; margin: 2px; padding: 2px")
		
		rep_img_container.appendChild(img);
		};
		
		reader.readAsDataURL(event.target.files[0]);
	}
  	


  </script>
  
  
</head>


<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	
	<main id="main">
		<section id="img-write">
		
		<div class="container mt-3">

		<div class="section-title">
			<h2>글 수정</h2>
		</div>
		
		<form action="mygroup_board_modify_ok.do" method="post" name="wfrm" enctype="multipart/form-data">
		<input type="hidden" name="gr_seq" value="<%= gr_seq %>">
		<input type="hidden" name="grb_seq" value="<%= grb_seq %>">
		<div class="input-group mb-3">
			<span class="input-group-text">제 목</span>
			<input type="text" class="form-control" name="subject" value="<%= data.getGR_SUBJECT()%>">
		</div>

		<div class="input-group mb-3">
			<span class="input-group-text">내 용</span>
			<textarea class="form-control" name="content" rows="10" style="resize: none;"><%= data.getGR_CONTENT()%></textarea>
		</div>
		
		<div class="input-group mb-3">
			  <label class="input-group-text" for="inputGroupFile01">파일 첨부</label>
			  <input type="file" id="rep-img" onchange="setRepThumbnail(event)" class="form-control" name="uploads" value="파일첨부" multiple="multiple" />
		</div>
		
		<!-- image-thumbnail -->
		<div id="image-container"></div>
		
		<div class="d-flex flex-row-reverse">
		<div class="p-2">
		<input class="btn btn-outline-primary" type="submit" value="수정하기" id='wbtn'>
		<input class="btn btn-outline-primary" type="button" value="돌아가기" onclick="history.back()">
		</div>
		</div>
		</form>
		
		</div>
		</section>
	</main>

	<!-- <div id="right-nav" class="r-navbar">
	
	<!-- End #main -->

<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>

</html>