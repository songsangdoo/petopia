<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>Arsha Bootstrap Template - Index</title>
<meta content="" name="description">
<meta content="" name="keywords">
</head>

<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>

	<!-- ======= Hero Section ======= -->
	<div id="hero"></div>
	<!-- End Hero -->

	<main id="main">
	
	
		<div class="container w-75">

		<div class="section-title">
			<h2>글 작성</h2>
		</div>
		
		<form action="missing_write_ok.do" method="post" name="wfrm" enctype="multipart/form-data">
		<div class="input-group mb-3">
			<span class="input-group-text">이 름</span>
			<input type="text" class="form-control" name="subject" placeholder="반려동물의 이름을 적어주세요">
		</div>
		
		
		<div class="input-group mb-3">
			<span class="input-group-text">지 역</span>
			<input type="text" class="form-control" name="l_location" placeholder="잃어버린 지역을 적어주세요">
		</div>
		
		<div class="input-group mb-3">
			<span class="input-group-text">실종 날짜</span>
			<input type="date" class="form-control" name="l_date">
		</div>

		<div class="input-group mb-3">
			<span class="input-group-text">특 징</span>
			<textarea class="form-control" name="content" rows="10" placeholder="쉽게 알아볼 수 있는 반려동물의 특징을 자세히 적어주세요"></textarea>
		</div>
		
		<div class="input-group mb-3">
		  <label class="input-group-text" for="inputGroupFile01">대표 이미지</label>
		  <input type="file" class="form-control" name="rep_upload" value="" accept="image/*">
		</div>
		
		<div class="input-group mb-3">
		  <label class="input-group-text" for="inputGroupFile01">이미지</label>
		  <input type="file" class="form-control" name="uploads" value="" accept="image/*" multiple>
		</div>
		
		<div class="d-flex flex-row-reverse">
			<div class="p-2"><input class="btn btn-primary" type="submit" value="글 쓰기"></div>
		</div>
		</form>
		
		</div>
	</main>

	<!-- <div id="right-nav" class="r-navbar">
	
	<!-- End #main -->

<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>

</html>