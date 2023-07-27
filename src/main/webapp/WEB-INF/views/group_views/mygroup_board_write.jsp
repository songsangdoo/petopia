<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   <%
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	String member_seq = userData.getM_seq();
	String gr_seq = request.getParameter("gr_seq");
  
  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
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
<link href="assets/css/writeFooter.css" rel="stylesheet">  
</head>
<body>

<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	<section id="img-write">
			
			<div class="container w-75 mt-3">
	
			<div class="section-title">
				<h2>동아리 게시판 글쓰기</h2>
			</div>
			
			<form action="mygroup_board_write_ok.do" method="post" name="wfrm" enctype="multipart/form-data">
			<input type="hidden" name ="gr_seq" value=<%=gr_seq%>>
			<div class="input-group mb-3">
				<span class="input-group-text">제 목</span>
				<input type="text" class="form-control" name="subject" value=""  maxlength="25" />
			</div>
	
			<div class="input-group mb-3">
				<span class="input-group-text">내 용</span>
				<textarea class="form-control" name="content" rows="10" style="resize: none;"></textarea>
			</div>
			
			<div class="input-group mb-3">
			  <label class="input-group-text" for="inputGroupFile01">파일 첨부</label>
			  <input type="file" id="rep-img" onchange="setRepThumbnail(event)" class="form-control" name="uploads" value="파일첨부" multiple="multiple" />
			</div>
	
			<!-- rep-image-thumbnail -->
			<div id="rep-image-container"></div>
			
			<br>
			
			<!-- <div class="input-group mb-3">
			  <label class="input-group-text" for="inputGroupFile01">이미지</label>
			  <input type="file" id="imgs" onchange="setThumbnail(event)" class="form-control" name="uploads" value="" accept="image/*" multiple>
			</div>
			
			image-thumbnail
			<div id="image-container"></div> -->
			
			<div class="d-flex flex-row-reverse">
			<div class="p-2">
			<input type="button" value="목 록" class="btn btn-outline-primary" style="cursor: pointer"; onclick="location.href='mygroup_board_list.do?gr_seq=<%=gr_seq %>'" />
			<input type="button" name="submit1" id="submit1" class="btn btn-outline-primary" value="제 출" />
			</div>
			</div>
			</form>
			
			</div>
		</section>
<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>
</html>