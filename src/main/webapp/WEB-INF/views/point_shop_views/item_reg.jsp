<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form role="form" method="post" autocomplete="off">

<div class="inputArea"> 
 <label>1차 분류</label>
 <select class="category1">
  <option value="">전체</option>
 </select>

 <label>2차 분류</label>
 <select class="category2" name="cateCode">
  <option value="">전체</option>
 </select>
</div>

<div class="inputArea">
 <label for="gdsName">상품명</label>
 <input type="text" id="gdsName" name="gdsName" />
</div>

<div class="inputArea">
 <label for="gdsPrice">상품가격</label>
 <input type="text" id="gdsPrice" name="gdsPrice" />
</div>

<div class="inputArea">
 <label for="gdsStock">상품수량</label>
 <input type="text" id="gdsStock" name="gdsStock" />
</div>

<div class="inputArea">
 <label for="gdsDes">상품소개</label>
 <textarea rows="5" cols="50" id="gdsDes" name="gdsDes"></textarea>
</div>

<div class="inputArea">
 <button type="submit" id="register_Btn" class="btn btn-primary">등록</button>
</div>

</form>
</body>
</html>