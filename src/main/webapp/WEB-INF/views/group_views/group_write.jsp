<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
  <%
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
 	 String member_seq = "";
	if( userData != null){
	      member_seq = userData.getM_seq();
	}else {
	      out.println("<script>");
	      out.println("alert('제한된 접근입니다');");
	      out.println("history.back();");
	      out.println("</script>");
	}
	
  
  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	var index = 0;
	
	window.onload = function() {
		document.getElementById( 'submit1' ).onclick = function() {
			if ( document.wfrm.gr_name.value.trim() == '' ) {
				alert( '동아리명을 입력해주세요' );
				return false;
			}
			if ( document.wfrm.gr_explan.value.trim() == '' ) {
				alert( '신청인을 입력해주세요' );
				return false;
			}
			if ( document.wfrm.upload.value.trim() == '' ) {
				alert( '사진파일을 첨부해주세요' );
				return false;
			}
			
			
			document.wfrm.submit();
		}
		
		document.getElementById( 'plus' ).onclick = function() {
			if(index == 4){
				alert("5개 까지만 됩니다.");
				return;
			}
	        const wfm = document.getElementById("wfm");
	        const newP = document.createElement('div');
	        newP.className = 'input-group mb-3';
	        newP.innerHTML = "<span class='input-group-text'>해쉬 태그</span><input type='text' name='hash' class='form-control' value=''  maxlength='10' placeholder='ex)#강아지  (10글자 제한)' /> <input type='button' class='btn btn-outline-primary' style ='width : 150px;'  value='삭제' onclick='remove(this)'>";
	     
	       
	        wfm.appendChild(newP);
	        
	        const currentDiv = document.getElementById("plustext1");
	        wfm.insertBefore(newP, currentDiv);
	        index++;
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
	
    const remove = (obj) => {
        document.getElementById('wfm').removeChild(obj.parentNode);
        index--;
    }
    
</script>


<link href="assets/css/writeFooter.css" rel="stylesheet">
        
  
</head>
<body>

<section id="img-write">
		<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
		<div class="container w-75 mt-3">

		<div class="section-title">
			<h2>동아리 신청서</h2>
		</div>
		
		<form action="group_write_ok.do" method="post" name="wfrm" enctype="multipart/form-data" id="wfm">
		
		<div class="input-group mb-3">
			<span class="input-group-text">동아리 이름</span>
			<input type="text" class="form-control" name="gr_name" value=""  maxlength="10" />
		</div>

		<div class="input-group mb-3">
			<span class="input-group-text">동아리 소개글</span>
			<textarea class="form-control" name="gr_explan" rows="10" style="resize: none;"></textarea>
		</div>
		
		<div class="input-group mb-3" >
			<span class="input-group-text">해쉬 태그</span>
			<input type="text" name="hash" class="form-control" name='hash' value=""  maxlength="10" placeholder="ex)#강아지  (10글자 제한)" />
			<input type="button" id="plus" onclick="add_textbox()" class="btn btn-outline-primary" name="plus" value="해쉬태그 추가" style ="width : 150px;"/>
		</div>
		
		<div id ="plustext1" class="input-group mb-3">
		  <label class="input-group-text" for="inputGroupFile01">대표이미지</label>
		  <input type="file" id="rep-img" onchange="setRepThumbnail(event)" class="form-control" name="upload" value="파일첨부" accept="image/*"/>
		
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
		<input type="button" value="목 록" class="btn btn-outline-primary" style="cursor: pointer"; onclick="location.href='grouplist.do'" />
		<input type="button" name="submit1" id="submit1" class="btn btn-outline-primary" value="제 출" />
		</div>
		</div>
		</form>
		
		</div>
		</section>
<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>
</html>