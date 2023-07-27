<%@page import="com.petopia.pointshop.model.GradeTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  
  <link rel="stylesheet" type="text/css" href="./css/profile_card.css">
	<script>
	function checkPassword() {
		  var password = document.getElementById("password").value;
		  var confirmPassword = document.getElementById("confirmPassword").value;
		  var formDiv = document.getElementById("form");

		  if (password == confirmPassword) {
		    // 비밀번호가 일치하면 입력 폼을 숨기고 콘텐츠를 로드
		    formDiv.style.display = "none";

		    var xhr = new XMLHttpRequest();
		    xhr.onreadystatechange = function() {
		      if (xhr.readyState === 4) {
		        if (xhr.status === 200) {
		          var contentDiv = document.getElementById("content");
		          contentDiv.innerHTML = xhr.responseText;
		        } else {
		          console.error("오류 발생: " + xhr.status);
		        }
		      }
		    };

		    xhr.open("GET", "your_other_jsp_file.do", true);
		    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		    xhr.send();
		  } else {
		    alert("비밀번호가 일치하지 않습니다.");
		  }
		}
</script>
</head>
<body>

<div id="form">
    <form>
      비밀번호: <input type="password" id="password"><br>
      비밀번호 확인: <input type="password" id="confirmPassword" oninput="checkPassword()"><br>
    </form>
  </div>

<div id="content">
</div>
</body>
</html>
