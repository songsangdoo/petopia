<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<link href='http://fonts.googleapis.com/css?family=Shadows+Into+Light'
	rel='stylesheet' type='text/css'>
<link href="assets/css/login.css" rel="stylesheet">
<!-- Owl Carousel CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Owl Carousel JavaScript -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
<script src="assets/js/login.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		var key = getCookie("key");
		$("#id").val(key);

		// 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
		if ($("#id").val() != "") {
			$("#checkId").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
		}

		$("#checkId").change(function() { // 체크박스에 변화가 있다면,
			if ($("#checkId").is(":checked")) { // ID 저장하기 체크했을 때,
				setCookie("key", $("#id").val(), 9999); // 9999일 동안 쿠키 보관
			} else { // ID 저장하기 체크 해제 시,
				deleteCookie("key");
			}
		});

		// ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
		$("#id").keyup(function() { // ID 입력 칸에 ID를 입력할 때,
			if ($("#checkId").is(":checked")) { // ID 저장하기를 체크한 상태라면,
				setCookie("key", $("#id").val(), 7); // 7일 동안 쿠키 보관
			}
		});
	});
</script>
</head>
<body>
	<!------ Include the above in your HEAD tag ---------->

	<section class="login-block">
		<div class="container">
			<div class="row">
				<div class="col-md-4 login-sec">
					<h2 class="text-center">로그인</h2>
					<form class="login-form" method="POST">
						<div class="form-group">
							<label for="exampleInputEmail1" class="text-uppercase">아이디</label>
							<input id="id" name="id" type="text" class="form-control"
								placeholder="">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1" class="text-uppercase">비밀번호</label>
							<input name="password" type="password" class="form-control"
								placeholder="">
						</div>
						<div class="form-check" style="padding-left : 10px;">
							<label class="form-check-label" style="padding-left : 15px;"> <input id="checkId"
								type="checkbox" class="form-check-input"> <small>아이디
									기억</small>
							</label>
							<button type="submit" class="btn btn-login float-right">로그인</button>
							<a href="/member_insert.do">
								<button type="button" class="btn btn-login float-right lshift">회원가입</button>
							</a>
							 <div style="padding : 40px 40px 0 0;">
							 <button type="button" onclick="kakaoLogin();" style="width : 300px; height : 45px; border : none; background-image: url('/images/kakao_login_medium_wide.png')"></button>
							 </div>
							 <a href="/KLogin.do"></a>
						</div>
					</form>
				</div>
				<div class="col-md-8 banner-sec">
					<div id="carouselExampleIndicators" class="carousel slide"
						data-ride="carousel">
						<ol class="carousel-indicators">
							<li data-target="#carouselExampleIndicators" data-slide-to="0"
								class="active"></li>
							<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
							<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
						</ol>
						<div class="carousel-inner" role="listbox">
							<div class="carousel-item active">
								<img class="d-block img-fluid"
									src="https://images.mypetlife.co.kr/content/uploads/2019/08/09153216/pets-3715733_1920.jpg"
									alt="First slide">
								<div class="carousel-caption d-none d-md-block">
									<div class="banner-text">
										<h2>Petopia</h2>
										<p> 반려동물 과 함께하는 삶 즐겁고 의미있게 더 나은 세상을 만들어갑시다</p>
									</div>
								</div>
							</div>
							<div class="carousel-item">
								<img class="d-block img-fluid"
									src="https://png.pngtree.com/png-clipart/20190119/ourlarge/pngtree-hand-painted-friendship-cat-and-puppy-hand-painted-friendly-cat-puppy-png-image_466479.jpg"
									alt="First slide">
								<div class="carousel-caption d-none d-md-block">
									<div class="banner-text">
										<h2>동아리 서비스</h2>
										<p> 같은 주제를 가지고 동아리를 만들고 정보를 공유하고 함께 즐겨요!</p>
									</div>
								</div>
							</div>
							<div class="carousel-item">
								<img class="d-block img-fluid"
									src="https://images.mypetlife.co.kr/content/uploads/2023/04/18094901/xuan-nguyen-zr0beNrnvgQ-unsplash-768x512-1.jpg"
									alt="First slide">
								<div class="carousel-caption d-none d-md-block">
									<div class="banner-text">
										<h2>반려동물 커뮤니티</h2>
										<p> 반려동물을 키울때 필요한 지식들과 정보공유를 통해 폭넓은 지식을가지고
										동물을 키울수있어요!</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script
		src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script 
		src="https://t1.kakaocdn.net/kakao_js_sdk/2.3.0/kakao.min.js" 
		integrity="sha384-70k0rrouSYPWJt7q9rSTKpiTfX6USlMYjZUtr1Du+9o4cGvhPAWxngdtVZDdErlh" 
		crossorigin="anonymous">
	</script>
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
		<script>
        //772d56ea24e1977a0fca7611c4d4dda4
        window.Kakao.init("772d56ea24e1977a0fca7611c4d4dda4");
		// 초기화 판단여부
		//console.log(Kakao.isInitialized());
		
   		function kakaoLogin() {
           Kakao.Auth.login({
                scope:"profile_nickname,account_email,gender",
                success: function(authObj){
                	//console.log(authObj);
                    Kakao.API.request({
                        url:'/v2/user/me',
                        success: function(res){
                        	// 닉네임 , 이메일 , 성별 , 연령대
                        	var nickname = res.kakao_account.profile.nickname;
                        	var email = res.kakao_account.email;
                        	var gender = res.kakao_account.gender;
                        	//console.log(nickname)
                        	//location.href="KLogin.do";
                        	
                        	$.ajax({
                        		url:"KLogin.do",
                        		data:{
                        			"m_id" : email,
                        			"m_nickname" : nickname,
                        			"m_email" : email,
                        			"m_gender" : gender
                       			},
                        		//sync:false,
                        		
                        		success:function(result){
                        			if(result > 0){
                        				location.href = 'index.do?m_email='+ email ;
                        				console.log("result:" + result);  // 이건 첨만들때 작동하는 result
                        				
                        			}
                        		}
                        	})
                        	
                        	//console.log(nickname,email,gender);
                        	//console.log(res);  // res : Object object?
                            //const kakao_account = res.kakao_account;
                        },
                        fail:function(error){
                        	console.log(error);
                        },
                    })

                },
                fail:function(error){
                	console.log(error);
                },
            })
        }


    </script>
</body>
</html>
