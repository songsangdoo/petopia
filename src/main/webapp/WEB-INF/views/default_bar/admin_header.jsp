<%@page import="com.petopia.admin.model.AdminTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.Inet6Address"%>
<%@page import="java.net.InetAddress"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	MemberTO liveUser = (MemberTO) session.getAttribute("loginMember");
	String activeMenu = (String) request.getAttribute("activeMenu");
	int currentPage = 1;
	int itemsPerPage = 10;

	String pageParam = request.getParameter("page");
	if (pageParam != null) {
	    currentPage = Integer.parseInt(pageParam);
	}

	StringBuilder userSb = new StringBuilder();
	ArrayList<AdminTO> memberList = (ArrayList<AdminTO>) request.getAttribute("userList");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
    <meta name="author" content="AdminKit">
    <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="shortcut icon" href="assets/dashimg/icons/icon-48x48.png" />

    <link rel="canonical" href="https://demo-basic.adminkit.io/" />

    <title>Petopia Management Page</title>

    <link href="assets/css/dashboardcss/app.css" rel="stylesheet">
    <link href="assets/css/dashboardcss/additional.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
	<script>
	$(document).ready(function() {
	    var userinfoButtons = document.getElementsByClassName("userinfo");
	    
	    $('#search-form').submit(function(event) {
            event.preventDefault(); // 기본 제출 동작 방지

            // 폼 데이터 가져오기
            var filter = $('#getUserFilter').val();
            var keyword = $('#search').val();

            // 폼 데이터 출력 (테스트용)
            console.log('Filter:', filter);
            console.log('Keyword:', keyword);

            // URL 및 파라미터 설정
            var url = '/master_userboard.do';
            var params = '?filter=' + filter + '&keyword=' + keyword;
            var fullURL = url + params;

            // 페이지 이동
            window.location.href = fullURL;
        });
		
	    // userinfo 버튼 클릭 이벤트 리스너 등록
	    for (var i = 0; i < userinfoButtons.length; i++) {
	        userinfoButtons[i].addEventListener("click", function() {

	            // 클릭된 버튼의 부모 요소인 <td> 선택
	            // <td> 요소에서 <tr> 요소를 찾아 선택
	            // <tr> 요소 내의 모든 span 요소 선택
	            // span 요소 안의 값을 가져오기
	            var parentTd = this.parentNode;
	            var parentTr = parentTd.parentNode;
	            var spanElements = parentTr.getElementsByTagName("span");
	            var value = spanElements[0].innerText.replace(/^.{2}/, ''); // {2} 칸만큼 글자 삭제하는 정규식
	            $.ajax({
	                url: "/getUserinfo.do",
	                type: "GET",
	                data: {
	                    value: value
	                },
	                success: function(result) {
	                	
	                	const writeValue = 300;
	                    const pointValue = parseInt(result.m_point);
	                    const totalPointValue = parseInt(result.m_totalPoint);
	                    
	                    setTimeout(function() {
		                    $({ someValue: 0 }).animate({ someValue: writeValue }, {
		                        duration: 3000,
		                        easing: 'swing', // can be anything
		                        step: function () { // called on every step
		                            // Update the element's text with rounded-up value:
		                            $('#viewWrite').text(commaSeparateNumber(Math.ceil(this.someValue)));
		                    		console.log('실행1', this.someValue)
		                        }
		                    });
		                    $({ someValue: 0 }).animate({ someValue: pointValue }, {
		                        duration: 3000,
		                        easing: 'swing', // can be anything
		                        step: function () { // called on every step
		                            // Update the element's text with rounded-up value:
		                            $('#viewPoint').text(commaSeparateNumber(Math.ceil(this.someValue)));
		                            console.log('실행2' , this.someValue)
		                        }
		                    });
		                    $({ someValue: 0 }).animate({ someValue: totalPointValue }, {
		                        duration: 3000,
		                        easing: 'swing', // can be anything
		                        step: function () { // called on every step
		                            // Update the element's text with rounded-up value:
		                            $('#viewTotalPoint').text(commaSeparateNumber(Math.ceil(this.someValue)));
		                            console.log('실행3' , this.someValue)
		                        }
		                    });
	                    }, 10);
	                	          
	                    var proImgUrl = result.proImg;
	                                       
	                    $("#viewName").text(result.m_name); 
	                    $("#viewNickName").text(result.m_nickname); 
	                    $("#viewGrade").text(result.grade_seq);
	                    $("#viewAddr").text(result.m_addr); 
	                    $("#viewEmail").text(result.m_email); 
	                    $("#viewPhone").text(result.m_phone); 
	                    
	                    if (proImgUrl) {
	                        var img = $("<img>").attr("src", proImgUrl).attr("alt", "Admin").addClass("rounded-circle").attr("width", "150");
	                        $("#viewImg").attr("src", proImgUrl || defaultImgUrl);
	                    } else {
	                        var defaultImgUrl = "https://png.pngtree.com/png-clipart/20220117/original/pngtree-cartoon-hand-drawn-default-avatar-png-image_7127563.png";
	                        var defaultImg = $("<img>").attr("src", defaultImgUrl).attr("alt", "Admin").addClass("rounded-circle").attr("width", "150");
	                        $("#viewImg").attr("src", proImgUrl || defaultImgUrl);
	                    }
	                },
	                error: function(xhr, status, error) {
	                    console.error(error);
	                }
	            });
	        });
	    }
	});
	</script>
</head>

<body>
	<div class="wrapper">
		<nav id="sidebar" class="sidebar js-sidebar">
			<div class="sidebar-content js-simplebar">
				<a class="sidebar-brand" href="/master_dashboard.do">
          <span class="align-middle">관리자페이지</span>
        </a>

				<ul class="sidebar-nav">
					<li class="sidebar-header">
						유저관리
					</li>

					<%-- <li class="sidebar-item <%= (activeMenu.equals("totalactive")) ? "active" : "" %>">
						<a class="sidebar-link" href="/master_dashboard.do">
              <i class="align-middle" data-feather="sliders"></i> <span class="align-middle">종합 정보</span>
            </a>
					</li> --%>

					<li class="sidebar-item <%= (activeMenu.equals("useractive")) ? "active" : "" %>">
						<a class="sidebar-link" href="/master_userboard.do">
              				<i class="align-middle" data-feather="user"></i> <span class="align-middle">유저 관리</span>
            			</a>
					</li>
					<%-- <li class="sidebar-item <%= (activeMenu.equals("productactive")) ? "active" : "" %>">
						<a class="sidebar-link" href="/master_product.do">
              				<i class="align-middle" data-feather="user-plus"></i> <span class="align-middle">배너 관리</span>
            			</a>
					</li> --%>
					<li class="sidebar-item <%= (activeMenu.equals("pointshopactive")) ? "active" : "" %>">
						<a class="sidebar-link" href="/master_pointshop.do">
              				<i class="align-middle" data-feather="user-plus"></i> <span class="align-middle">상품 관리</span>
            			</a>
					</li>
					<li class="sidebar-item <%= (activeMenu.equals("groupactive")) ? "active" : "" %>">
						<a class="sidebar-link" href="/master_group.do">
              				<i class="align-middle" data-feather="book"></i> <span class="align-middle">동아리 관리</span>
            			</a>
					</li>
					<li class="sidebar-item <%= (activeMenu.equals("")) ? "active" : "" %>">
						<a class="sidebar-link" href="/index.do">
              				<i class="align-middle" data-feather="user-plus"></i> <span class="align-middle">펫토피아 메인</span>
            			</a>
					</li>
				</ul>
			</div>
		</nav>
		
		<div class="main">
			<nav class="navbar navbar-expand navbar-light navbar-bg">
				<a class="sidebar-toggle js-sidebar-toggle">
          <i class="hamburger align-self-center"></i>
        </a>

				<div class="navbar-collapse collapse">
					<ul class="navbar-nav navbar-align">
						<li class="nav-item dropdown">
							<a class="nav-icon dropdown-toggle" href="#" id="alertsDropdown" data-bs-toggle="dropdown">
								<div class="position-relative">
									<i class="align-middle" data-feather="bell"></i>
									<span class="indicator">4</span>
								</div>
							</a>
							<div class="dropdown-menu dropdown-menu-lg dropdown-menu-end py-0" aria-labelledby="alertsDropdown">
								<div class="dropdown-menu-header">
									4 New Notifications
								</div>
								<div class="list-group">
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<i class="text-danger" data-feather="alert-circle"></i>
											</div>
											<div class="col-10">
												<div class="text-dark">계정상태</div>
												<div class="text-muted small mt-1">Restart server 12 to complete the update.</div>
												<div class="text-muted small mt-1">30m ago</div>
											</div>
										</div>
									</a>
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<i class="text-warning" data-feather="bell"></i>
											</div>
											<div class="col-10">
												<div class="text-dark">알람 구현중입니다</div>
												<div class="text-muted small mt-1">알람 구현중입니다</div>
												<div class="text-muted small mt-1">Now</div>
											</div>
										</div>
									</a>
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<i class="text-primary" data-feather="home"></i>
											</div>
											<div class="col-10">
												<div class="text-dark"><%=request.getRemoteAddr() %></div>
												<div class="text-muted small mt-1">5h ago</div>
											</div>
										</div>
									</a>
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<i class="text-success" data-feather="user-plus"></i>
											</div>
											<div class="col-10">
												<div class="text-dark">동아리</div>
												<div class="text-muted small mt-1">동아리</div>
												<div class="text-muted small mt-1">Now</div>
											</div>
										</div>
									</a>
								</div>
								<div class="dropdown-menu-footer">
									<a href="#" class="text-muted">Show all notifications</a>
								</div>
							</div>
						</li>
						<li class="nav-item dropdown">
							<a class="nav-icon dropdown-toggle d-inline-block d-sm-none" href="#" data-bs-toggle="dropdown">
                <i class="align-middle" data-feather="settings"></i>
              </a>

							<a class="nav-link dropdown-toggle d-none d-sm-inline-block" href="#" data-bs-toggle="dropdown">
                <img src="assets/dashimg/avatars/avatar.jpg" class="avatar img-fluid rounded me-1" alt="Charles Hall" /> <span class="text-dark"><%=liveUser.getM_nickname()%></span>
              </a>
							<div class="dropdown-menu dropdown-menu-end">
								<a class="dropdown-item" href="pages-profile.html"><i class="align-middle me-1" data-feather="user"></i> Profile</a>
								<a class="dropdown-item" href="#"><i class="align-middle me-1" data-feather="pie-chart"></i> Analytics</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="index.html"><i class="align-middle me-1" data-feather="settings"></i> Settings & Privacy</a>
								<a class="dropdown-item" href="#"><i class="align-middle me-1" data-feather="help-circle"></i> Help Center</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#">Log out</a>
							</div>
						</li>
					</ul>
				</div>
			</nav>