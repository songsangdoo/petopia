<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
	<div class="card mb-4">
		<div class="card-header" align="center"><strong>회원 정보 수정</strong></div>
		<div style="height : 550px; padding : 50px 150px 30px 150px;">
			<form id="form1" action="mypageModify.do" method="post">
				<!-- Form Group (username)-->
				<div class="row gx-3 mb-3">
					<div class="col-md-6">
						<div class="input-group mb-3">
							<span class="input-group-text">아이디</span>
							<input type="text" class="form-control" placeholder="<%= userData.getM_id() %>" style="background-color : #e3e3e3;" readonly>
						</div>
					</div>
					<!-- Form Group (first name)-->
					<div class="col-md-6">
						<div class="input-group mb-3">
							<span class="input-group-text">닉네임</span>
							<input type="text" id="myNickname" class="form-control" placeholder="<%= userData.getM_nickname() %>" style="background-color : #e3e3e3;" readonly>
						</div>
					</div>
				</div>
					<!-- Form Group (last name)-->
					<div class="input-group mb-3">
						<span class="input-group-text">이　름</span>
						<input type="text" id="myName" name="name" class="form-control" placeholder="변경할 이름을 입력해주세요.">
					</div>
				<!-- Form Row        -->
				<div class="row gx-3 mb-3">
					<!-- Form Group (organization name)-->
					<div class="col-md-6">
						<div class="input-group mb-3">
							<span class="input-group-text">나　이</span>
							<input type="text" id="myAge" class="form-control" placeholder="<%= userData.getM_age() %>" style="background-color : #e3e3e3;" readonly>
						</div>
					</div>
					<!-- Form Group (location)-->
					<div class="col-md-6">
						<div class="input-group mb-3">
							<span class="input-group-text">성　별</span>
							<input type="text" id="myGender" class="form-control" placeholder="<%= userData.getM_gender() %>" style="background-color : #e3e3e3;" readonly>
						</div>
					</div>
				</div>
				<!-- Form Group (email address)-->
				<div class="mb-3">
					<div class="input-group mb-3">
						<span class="input-group-text">이메일</span>
						<input type="text" id="myEmail" name="email" class="form-control" placeholder="변경할 이메일 주소를 입력해주세요.">
					</div>
				</div>
				<!-- Form Group (email address)-->
				<div class="mb-3">
					<div class="input-group mb-3">
						<span class="input-group-text">연락처</span>
						<input type="text" id="myPhone" name="phone" class="form-control" placeholder="000-000-0000 형식으로 입력해주세요.">
					</div>
				</div>
				<div class="mb-3">
					<div class="input-group mb-3">
						<span class="input-group-text">주　소</span>
						<input type="text" id="myAddress1" name="address1" class="form-control" placeholder="변경할 주소를 입력해주세요.">
					</div>
				</div>
				<div class="mb-3">
					<div class="input-group mb-3">
						<span class="input-group-text">상세주소</span>
						<input type="text" id="myAddress2" name="address2" class="form-control" placeholder="상세주소를 입력해주세요.">
					</div>
				</div>
				<!-- Save changes button-->
				<div class="profileModifyButtons">
					<div class="buttonLeft">
						<button type='button' data-bs-toggle='modal' data-bs-target='#passwordModify' class="passwordModifyButton">비밀변호 변경</button>
					</div>
					<div class="buttonRight">
						<button class="btn btn-primary" type="button" onclick="modifyOk()" style="border: 1px solid gray;">저장</button>
						<button class="btn btn-danger float-right" type="button" onclick="modifyCancel()" style="border: 1px solid gray;">취소</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<!-- The Modal -->
	<div class="modal fade" id="passwordModify">
		<div class="modal-dialog" style="left : 120px; top : 400px; transform: translate(-50%, -50%);">
			<div class="modal-content" style="width: 500px; padding: 20px; background-color: fff; border: 1px solid black;">
				<form id="form2" action="passwordModify.do" method="post">
				<div class="passwordCheck">
					<div class="passwordCheckTitle">내 정보 수정 - 비밀번호 변경</div>
						<div class="input-group mb-3">
							<span class="input-group-text">현재 비밀번호</span>
							<input type="password" class="form-control" name="oldPassword" id="oldPassword">
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">변경 비밀번호</span>
							<input type="password" class="form-control" name="newPassword" id="newPassword">
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">비밀번호 확인</span>
							<input type="password" class="form-control" name="newPasswordConfirm" id="newPasswordConfirm">
						</div>
					</div>
				<!-- Modal footer -->
				<div class="modalButton">
					<div class="modal-footer" style="border: none; padding: 0px;">
						<button type="button" class="btn btn-primary" onclick="passwordModifyOk()" style="border: 1px solid gray;" >입력완료</button>
					</div>
					<div class="modal-footer" style="border: none; padding: 0px;">
						<button type="button" class="btn btn-danger" data-bs-dismiss="modal" style="border: 1px solid gray;">취소</button>
					</div>
				</div>
				</form>
			</div>
		</div>
	</div>
	
</body>
</html>