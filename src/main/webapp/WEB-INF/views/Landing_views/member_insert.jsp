<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<link href="assets/css/member_insert.css" rel="stylesheet">

<script
  src="https://code.jquery.com/jquery-3.7.0.js"
  integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
  crossorigin="anonymous"></script>

<script>
function check_phone() {
    var m_phone = $(".phone").val();

    // 전화번호 유효성 검사 정규 표현식
    var phoneRegex = /^010\d{8}$/;

    if (!phoneRegex.test(m_phone)) {
      alert("올바른 전화번호를 입력해주세요. (01012345678 형식)");
      return false;
    }else{
       alert("올바른 전화번호입니다")
       return true;
    }
  return true;  
}
	$(function(){
	 	$("#idDuplicateCheck").on("click",function(){
	 		   
			var m_id = $(".id").val();
			 // 아이디 4글자 미만
			if(m_id.length < 4 ){
				$(".guide").hide();
				$("#idDuplicateCheck").val(0);
				alert("아이디는 4자이상 이여야합니다.");
				return;
			}
			$.ajax({
		         url:"dupId.do",
		         data: {"m_id": m_id},
		         success: function(result){
		        	 console.log(result)
		        	 if(result < 1 ){
		                 $(".guide.error").hide();
		                 $(".guide.ok").show();
		                 $("#idcheck").val(1);
		                 alert("아이디 중복체크 완료")
		                 
		           }else{
		                 $(".guide.ok").hide();
		                 $(".guide.error").show();
		                 $("#idcheck").val(0);
		                 alert("아이디가 중복되었습니다");
		           }
		         }
		   });
	 	})
	 })
	 function passwordConfirm(){
		 var password = document.getElementById("inputPassword");
		 var passwordConfirm = document.getElementById("inputPassword2");
		 var confirmMsg = document.getElementById("confirmMsg");
		 var correctColor = "blue";
		 var wrongColor = "red";
		 
		 if(password.value == passwordConfirm.value){
			 confirmMsg.style.color = correctColor;
			 confirmMsg.innerHTML = "비밀번호 일치"
		 }else{
			 confirmMsg.style.color = wrongColor;
			 confirmMsg.innerHTML = "비밀번호 불일치"
		 }
	 }
	 //이메일체크
	 $(function(){
	 	$("#emailDuplicateCheck").on("click",function(){
			var m_email = $(".email").val();
					$.ajax({
				         url:"dupEmail.do",
				         data: {"m_email": m_email},
				         success: function(result){
				        	 console.log(result)
				        	 if(result < 1 ){
				                 $(".guide.error").hide();
				                 $(".guide.ok").show();
				                 $("#emailCheck").val(1);
				                 alert("이메일 중복체크 완료.")
				                 
				           }else{
				                 $(".guide.ok").hide();
				                 $(".guide.error").show();
				                 $("#emailCheck").val(0);
				                 alert("이메일이 중복되었습니다.");
				           }
				         }
				   });
	 	})
	 })
	 // 닉네임 중복체크
	 $(function(){
		 	$("#nicknameDuplicateCheck").on("click",function(){
		 		   
				var m_nickname = $(".nickname").val();
			
				$.ajax({
			         url:"dupNickname.do",
			         data: {"m_nickname": m_nickname},
			         success: function(result){
			        	 console.log(result)
			        	 if(result < 1 ){
			                 $(".guide.error").hide();
			                 $(".guide.ok").show();
			                 $("#nicknameCheck").val(1);
			                 alert("닉네임 중복체크 완료.")
			                 
			           }else{
			                 $(".guide.ok").hide();
			                 $(".guide.error").show();
			                 $("#nicknameCheck").val(0);
			                 alert("닉네임이 중복되었습니다.");
			           }
			         }
			   });
		 	});
		 });
</script>	 
	 
		

<body>
<div class="row">
<div class="col-md-12">
<form method="post" action="/member_insertOk.do" class="joinForm" onsubmit="DojoinForm_submit(this); return false;">        
<h1> Sign Up </h1>
        

          
          <legend><span class="number">1</span> 아이디 및 비밀번호 설정</legend>

        
			<label for="name">ID:</label>
            <div id="custom-search-input">
                <div class="input-group">
				<input type="text" class="id" id="inputId" name="m_id" placeholder="사용자 아이디">
				<input type="hidden" id="idCheck" value="0">				
                    <span class="input-group-btn col-md-4">
						<button type="button" id="idDuplicateCheck"> check</button>
                    </span>
                </div>
            </div>


          <label for="name">이름:</label>
		  		<input type="text" class="name" id="inputName" name="m_name" placeholder="사용자 이름">
		  		
          <label for="name">비밀번호:</label>		  		
				<input type="password" class="pw" id="inputPassword" name="m_password" placeholder="사용자 비밀번호">
			    <input type="password" class="pw" id="inputPassword2" name="m_password" placeholder="비밀번호 확인" onkeyup="passwordConfirm()">
			    <span id="confirmMsg" style="margin-bottom : 20px;"></span>
			    
          <label for="name">닉네임:</label>

            <div id="custom-search-input">
                <div class="input-group">
		            <input type="text" class="nickname" id="inputNickName" name="m_nickname" placeholder="사용자 닉네임">
				<input type="hidden" id="nicknameCheck" value="0">				
                    <span class="input-group-btn col-md-4">
						<button type="button" id="nicknameDuplicateCheck"> check</button>
                    </span>
                </div>
            </div>
			    
          <legend><span class="number">2</span> 회원 정보 입력</legend>
          <label for="name">Email:</label>

            <div id="custom-search-input">
                <div class="input-group">
		            <input type="text" class="email" id="inputEmail" name="m_email" placeholder="사용자 이메일">
				<input type="hidden" id="emailCheck" value="0">				
                    <span class="input-group-btn col-md-4">
						<button type="button" id="emailDuplicateCheck"> check</button>
                    </span>
                </div>
            </div>
				            
        
      
             <label>주소:</label>    
              <div id="custom-search-input">
               <div class="input-group">
							<input type="text" name="m_addr" class = "addr" size="6" id="inputAddr" placeholder="우편번호" readonly>
				<input type="hidden" id="emailCheck" value="0">				
                    <span class="input-group-btn col-md-4">
			<button type="button" id="postcode" style="display: block; width: 70px">검색</button>
                    </span>
                </div>
            </div>

           					 <input type="text" class="addr" id="inputAddr2" name="m_addr" placeholder="주소" readonly>

            				<input type="text" class="addr" id= "inputAddr3" name="m_addr" placeholder="상세 주소">

           					<input type="text" class="addr" id= "inputAddr4" name="m_addr" placeholder="참고 항목" readonly>

			 <label>전화번호:</label>   
			          <div id="custom-search-input">
                <div class="input-group">
		            <input type="text" class="phone" id="inputPhone" name="m_phone" placeholder="사용자 핸드폰번호">
                    <span class="input-group-btn col-md-4">
						<button type="button" onclick="check_phone()"> check</button>
                    </span>
                </div>
            </div>
            				
            				
            				
          <label>나이:</label>
            <input type="text" class="age" id="inputAge" name="m_age" placeholder="사용자 나이">
            				
            				
          <label>성별:</label>
            <input type="radio" class="form-control" id="inputGender" name="m_gender" placeholder="사용자 성별" value="Male">남성
            <input type="radio" class="form-control" id="inputGender" name="m_gender" placeholder="사용자 성별" value="Female">여성

        <button type="submit" class="btn btn-primary">Sign Up</button>
        
       </form>
        </div>
      </div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	document.getElementById("postcode").onclick = function(){
		new daum.Postcode({
			oncomplete : function(data){
				var addr = '';
				var extraAddr = '';
				
				if(data.userSelectedType === 'R'){
					addr = data.roadAddress
				}else{
					addr = data.jibunAddress;
				}
				
				if(data.userSelectedType === 'R'){
					if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
						extraAddr += data.bname;
					}
					
					if(data.buildingName !== '' && data.apartment === 'Y'){
						extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
					}
					if(extraAddr !== ''){
						extraAddr ='(' + extraAddr + ')' ;
					}
					document.getElementById("inputAddr4").value = extraAddr;
					
				}else {
					document.getElementById("inputAddr4").value = '';
				}
				
				document.getElementById("inputAddr").value = data.zonecode;
				document.getElementById("inputAddr2").value = addr;
				
				document.getElementById("inputAddr3").value = "";
				document.getElementById("inputAddr3").focus();
			}
		}).open();
	}
</script>
</body>
</html>