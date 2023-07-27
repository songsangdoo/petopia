<%@page import="com.petopia.pointshop.model.PointShopTO"%>
<%@page import="com.petopia.pointshop.model.InventoryTO"%>
<%@page import="com.petopia.mypage.model.MypageProfileCardTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.petopia.mypage.model.MyPagePetTO"%>
<%@page import="com.petopia.pointshop.model.GradeTO"%>
<%@page import="com.petopia.mypage.model.MyPageTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/default_bar/header.jsp"%>
<%
	request.setCharacterEncoding("utf-8");

	MypageProfileCardTO profileCardInfo = (MypageProfileCardTO) request.getAttribute( "profileCardInfo" );
	ArrayList<PointShopTO> skinPointShopList = (ArrayList<PointShopTO>) request.getAttribute( "skinPointShopList" );
	ArrayList<PointShopTO> badgePointShopList = (ArrayList<PointShopTO>) request.getAttribute( "badgePointShopList" );
	GradeTO gradeCheck = (GradeTO) request.getAttribute("gradeChecks");
	GradeTO nextGradeCheck = (GradeTO) request.getAttribute("nextGradeCheck");
	ArrayList<MyPagePetTO> lists = (ArrayList<MyPagePetTO>) request.getAttribute("petList");
	
	ArrayList<String> myBadgeImgList = (ArrayList<String>) request.getAttribute( "myBadgeImgList" );
	String mySkinImg = (String) request.getAttribute( "mySkinImg" );
	mySkinImg = mySkinImg.replace( "_listSample", "" );
	
	ArrayList<MyPagePetTO> petCheckLists = (ArrayList<MyPagePetTO>) request.getAttribute( "petCheckLists" );
	
	StringBuilder sbPetList = new StringBuilder();
	

	if( petCheckLists != null ) {
		for( int i=0; i<petCheckLists.size(); i++ ) {
			if( petCheckLists.get(i) != null ) {
				String petGender = petCheckLists.get(i).getP_gender();
				String petName = petCheckLists.get(i).getP_name();
				String petAge = petCheckLists.get(i).getP_age();
				
				if ( petGender.equals( "암" ) ) {
					sbPetList.append( "<div class='petList' style='background-image: url(\"/images/wback.png\")'>" );
					sbPetList.append( "	<div style='width:10%;'><img src ='/images/gender_w.png'></div>" );
					sbPetList.append( "	<div style='width:60%;'><strong>" + petName + "</strong></div>" );
					sbPetList.append( "	<div style='width:30%;'><strong>" + petAge + "살</strong></div>" );
					sbPetList.append( "</div>" );
				} else {
					sbPetList.append( "<div class='petList' style='background-image: url(\"/images/mback.png\")'>" );
					sbPetList.append( "	<div style='width:10%;'><img src ='/images/gender_m.png'></div>" );
					sbPetList.append( "	<div style='width:60%;'><strong>" + petName + "</strong></div>" );
					sbPetList.append( "	<div style='width:30%;'><strong>" + petAge + "살</strong></div>" );
					sbPetList.append( "</div>" );
				}
			}
		}
	}

	
	
	String userPassword = userData.getM_password();
	
	String myComment = profileCardInfo.getPro_comment();
	String myImage = profileCardInfo.getPro_img();
	
	StringBuilder sbBadgeList = new StringBuilder();
	String badgeImg = "";
	
	if( myBadgeImgList != null ) {
		for (int i = 0; i < myBadgeImgList.size(); i++) {
			badgeImg = myBadgeImgList.get(i);
			sbBadgeList.append( "<img src='/images/point_shop_badge/" + badgeImg + "' border='0' width='30' height='30' style='margin : 0 2px 0 2px;'/>" );
		}
	}
	
	int cpage = 1;
	if (request.getParameter("cpage") != null) {
		cpage = Integer.parseInt(request.getParameter("cpage"));
	}

	int recordsPerpage = 9;
	int totalRecords = skinPointShopList.size();
	int totalPage = (totalRecords - 1) / recordsPerpage + 1;
	
	int startNum = (cpage - 1) * recordsPerpage;
	
	int pagePerBlock = 5;
	
	StringBuilder sbBadgeInven = new StringBuilder();
	StringBuilder sbSkinInven = new StringBuilder();
	StringBuilder pageSbHtml = new StringBuilder();
	StringBuilder pageSbHtml2 = new StringBuilder();
	
	String ps_seq = "";
	String ps_name = "";
	String ps_img = "";
	String ps_content = "";
	
	int badgeCount = badgePointShopList.size();
	int skinCount = skinPointShopList.size();
	
	// 뱃지 장착
	
	if( badgePointShopList.size() != 0 ) {
		for (int i = startNum; i < badgePointShopList.size() && i < startNum + recordsPerpage; i++) {
			ps_seq = badgePointShopList.get(i).getPs_seq();
			ps_name = badgePointShopList.get(i).getPs_name();
			ps_img = badgePointShopList.get(i).getPs_img();
			
			if (i % 3 == 0) {
	            sbBadgeInven.append("<div class='invenBadgePart'>");
	        }

	        sbBadgeInven.append("<div class='invenBadgeItem'>");
	        sbBadgeInven.append("    <input type='checkbox' name='badgeCheckbox' value='" + ps_seq + "' />");
	        sbBadgeInven.append("    <img src='/images/point_shop_badge/" + ps_img + "' border='0' width='50' height='50' />");
	        sbBadgeInven.append("    <div class='badgeName'>" + ps_name + "</div>");
	        sbBadgeInven.append("</div>");

	        if ((i + 1) % 3 == 0 || i == badgePointShopList.size() - 1) {
	            sbBadgeInven.append("</div>");
	        }
		}
		
		int startBlock = cpage - (cpage - 1) % pagePerBlock;
		int endBlock = startBlock + pagePerBlock - 1;

		if (endBlock >= totalPage) {
			endBlock = totalPage;
		}


		pageSbHtml.append( "<nav>" );
		pageSbHtml.append( "<ul class='pagination justify-content-center'>" );
		if ( cpage == 1 ) {
			pageSbHtml.append( "<li class='page-item'><a class='page-link'> &lt; </a></li>" );
		} else {
			pageSbHtml.append( "<li class='page-item'><a class='page-link' href='#'> &lt; </a></li>" );
		}
		
		for (int i = startBlock; i <= endBlock; i++) {
			if (i == cpage) {
				pageSbHtml.append( "<li class='page-item'><a class='page-link'>" + i + "</a></li>" );
			} else {
				pageSbHtml.append( "<li class='page-item'><a class='page-link' href='#'>" + i + "</a></li>" );
			}
		}

		if ( cpage == totalPage ) {
			pageSbHtml.append( "<li class='page-item'><a class='page-link'> &gt; </a></li>" );
		} else {
			pageSbHtml.append( "<li class='page-item'><a class='page-link' href='#'> &gt; </a></li>" );
		}
		
		pageSbHtml.append( "</ul>" );
		pageSbHtml.append( "</nav>" );
	}
	
	// 스킨 장착

	if( skinPointShopList.size() != 0 ) {
		for (int i = startNum; i < skinPointShopList.size() && i < startNum + recordsPerpage; i++) {
			ps_seq = skinPointShopList.get(i).getPs_seq();
			ps_name = skinPointShopList.get(i).getPs_name();
			ps_img = skinPointShopList.get(i).getPs_img();
			
			if (i % 3 == 0) {
	            sbSkinInven.append("<div class='invenSkinPart'>");
	        }

			sbSkinInven.append("<div class='invenSkinItem'>");
			sbSkinInven.append("    <input type='radio' name='skinRadio' value='" + ps_seq + "' />");
			sbSkinInven.append("    <img src='/images/point_shop_skin/" + ps_img + "' border='0' width='100' height='50' />");
			sbSkinInven.append("    <div class='skinName'>" + ps_name + "</div>");
			sbSkinInven.append("</div>");

	        if ((i + 1) % 3 == 0 || i == skinPointShopList.size() - 1) {
	        	sbSkinInven.append("</div>");
	        }
		}
		
		int startBlock = cpage - (cpage - 1) % pagePerBlock;
		int endBlock = startBlock + pagePerBlock - 1;

		if (endBlock >= totalPage) {
			endBlock = totalPage;
		}


		pageSbHtml2.append( "<nav>" );
		pageSbHtml2.append( "<ul class='pagination justify-content-center'>" );
		if ( cpage == 1 ) {
			pageSbHtml2.append( "<li class='page-item'><a class='page-link'> &lt; </a></li>" );
		} else {
			pageSbHtml2.append( "<li class='page-item'><a class='page-link' href='#'> &lt; </a></li>" );
		}
		
		for (int i = startBlock; i <= endBlock; i++) {
			if (i == cpage) {
				pageSbHtml2.append( "<li class='page-item'><a class='page-link'>" + i + "</a></li>" );
			} else {
				pageSbHtml2.append( "<li class='page-item'><a class='page-link' href='#'>" + i + "</a></li>" );
			}
		}

		if ( cpage == totalPage ) {
			pageSbHtml2.append( "<li class='page-item'><a class='page-link'> &gt; </a></li>" );
		} else {
			pageSbHtml2.append( "<li class='page-item'><a class='page-link' href='#'> &gt; </a></li>" );
		}
		
		pageSbHtml2.append( "</ul>" );
		pageSbHtml2.append( "</nav>" );
	}
	
%>
<link href="assets/css/mypage/mypage.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="./css/profile_card.css">
<style>
.white-text-with-black-border {
    color: white;
    -webkit-text-stroke: 1px black; /* Chrome, Safari, Opera */
    text-stroke: 1px black; /* Standard syntax */
}
</style>
<script src="https://kit.fontawesome.com/105bc65088.js" crossorigin="anonymous"></script>
<script>

function modifyOk() {
    var inputName = document.getElementById("myName").value;
    var inputEmail = document.getElementById("myEmail").value;
    var inputPhone = document.getElementById("myPhone").value;
    var inputAddress1 = document.getElementById("myAddress1").value;
    var inputAddress2 = document.getElementById("myAddress2").value;

    // 유효성 검사
    if ( inputName.trim() === "" ) {
        alert( "변경할 이름을 입력하세요." );
    } else if ( inputName.length > 20 ) {
        alert( "이름은 20자 이내로 작성해주세요." );
    } else if (inputEmail.trim() === "") {
        alert("이메일을 입력하세요.");
    } else if (inputPhone.trim() === "") {
        alert("전화번호를 입력하세요.");
    } else if (inputAddress1.trim() === "") {
        alert("주소를 입력하세요.");
    } else if (inputAddress2.trim() === "") {
        alert("상세 주소를 입력하세요.");
    } else {
        document.getElementById( "form1" ).submit();
        alert( "회원 정보가 성공적으로 변경되었습니다." );
    }
};

function badgeNo() {

	const checkboxes = document.querySelectorAll( 'input[name="badgeCheckbox"]' );

    // 선택한 checkbox 태그들의 체크 상태를 모두 해제합니다.
    checkboxes.forEach((checkbox) => {
        checkbox.checked = false;
    });
}

function badgeOk() {

    const checkboxes = document.querySelectorAll( 'input[name="badgeCheckbox"]' );

    let atLeastOneChecked = false;

	 	// 체크된 체크박스의 개수를 세는 방법
	    const checkedCount = Array.from(checkboxes).filter((checkbox) => checkbox.checked).length;
	
	    // 체크된 체크박스의 개수가 5개 이상인 경우 알람을 표시하고 폼 제출을 막습니다.
	    if (checkedCount > 5) {
	        alert("뱃지는 최대 5개까지만 선택할 수 있습니다.");
	        return false;
	    }
    
            // 하나 이상의 체크박스가 선택되었는지 확인합니다.
            checkboxes.forEach((checkbox) => {
                if (checkbox.checked) {
                    atLeastOneChecked = true;
                    document.getElementById( "equipBadge" ).submit();
                }
            });

            // 하나도 선택되지 않았으면 경고 메시지를 표시하고 폼 제출을 막습니다.
            if (!atLeastOneChecked) {
            	const confirmResult = confirm("뱃지가 선택되지 않았습니다. 뱃지 착용을 모두 해제하시겠습니까?");
               if( confirmResult ) {
            	   document.getElementById( "equipBadge" ).submit();
               } else {
            	   return false;
               }
            } 
};

function skinOk() {

    const radiobox = document.querySelectorAll( 'input[name="skinRadio"]' );

	 	const checkedCount = Array.from(radiobox).filter((radio) => radio.checked).length;
	
	 	if ( checkedCount === 0 ) {
	        alert( "장착할 스킨을 선택해 주세요." );
	        return false;
	    }
	 	document.getElementById("equipSkin").submit();
};

function skinNo() {
	
	const confirmResult = confirm( "현재 착용 중인 스킨을 해제하시겠습니까?" );
	 	if ( confirmResult ) {
	 		document.getElementById( "equipSkin" ).submit();
	    } else {
	    	return false;
	    }
};

function petOk() {

    const checkboxes = document.querySelectorAll( 'input[name="petCheckBox"]' );

    let atLeastOneChecked = false;

	 	// 체크된 체크박스의 개수를 세는 방법
	    const checkedCount = Array.from(checkboxes).filter((checkbox) => checkbox.checked).length;

	    if (checkedCount > 3) {
	        alert("펫은 최대 3마리까지 프로필 카드에 등록할 수 있습니다.");
	        return false;
	    }
    
            // 하나 이상의 체크박스가 선택되었는지 확인합니다.
            checkboxes.forEach((checkbox) => {
                if (checkbox.checked) {
                    atLeastOneChecked = true;
                    document.getElementById( "petCard" ).submit();
                }
            });

            // 하나도 선택되지 않았으면 경고 메시지를 표시하고 폼 제출을 막습니다.
            if (!atLeastOneChecked) {
            	const confirmResult = confirm("펫이 선택되지 않았습니다. 프로필카드 연결을 해제하시겠습니까?");
               if( confirmResult ) {
            	   document.getElementById( "petCard" ).submit();
               } else {
            	   return false;
               }
            } 
};


function passwordModifyOk() {
	
	var password = '<%= userData.getM_password() %>';
	var oldPassword = document.getElementById("oldPassword").value;
	var newPassword = document.getElementById("newPassword").value;
	var newPasswordConfirm = document.getElementById("newPasswordConfirm").value;

	// 유효성 검사
	if ( oldPassword.trim() === "" ) {
		alert( "현재 비밀번호를 입력해주세요." );
	} else if ( oldPassword !== password ) {
		alert( "현재 비밀번호가 아닙니다." );
	} else if ( newPassword.trim() === "" ) {
		alert( "새 비밀번호를 입력해주세요." );
	} else if ( newPassword.length > 30 ) {
		alert( "비밀번호가 너무 깁니다." );
	} else if ( newPasswordConfirm.trim() === "" ) {
		alert( "비밀번호 확인을 입력해주세요." );
	} else if ( newPassword !== newPasswordConfirm ) {
		alert( "새로 설정한 비밀번호가 비밀번호 확인과 다릅니다." );
	} else {
		document.getElementById( "form2" ).submit();
		alert( "비밀번호가 성공적으로 변경되었습니다." );
	}
};

function modifyCancel() {
	window.location.href = "http://localhost:8080/mypage.do";
}

</script>
</head>

<body>

	<main>
		<!-- ======= mypage 시작 ======= -->
		<section id="mypage">
			<div class="mypageTitle">
				<img class="titleImage" src="/images/titlesole.png" />마이 페이지
			</div>
			<div class="container">
				<div class="row">
					<div class="col-lg-3">
						<div class="profile-card-4">
							<div class="card z-depth-3">
								<div class="profile-content">
									<div class="profile-card">
										<div class="profile-firstinfo" align="center">
											<img src="images/profile_img/<%= profileCardInfo.getPro_img() %>" />
											<div class="profile-profileinfo">
												<h2>
													<strong><%=userData.getM_nickname()%></strong>
												</h2>
											</div>
										</div>
									</div>
								</div>
								<div class="gradeName" align="center">
									<strong><%=gradeCheck.getGrade_cnt()%></strong>
								</div>
								<div align="center">
									<img class="myGradeImage" src="images/grade/<%=gradeCheck.getGrade_img()%>" />
								</div>
								<div class="card-body">
									<ul class="list-group shadow-none">
										<li class="list-group-item">
											<div class="list-icon">
												<i class="fas fa-coins"></i>
											</div>
											<div class="list-details">
												<span><%=userData.getM_point()%></span> <small>잔여포인트</small>
											</div>
										</li>
										<li class="list-group-item">
											<div class="list-icon">
												<i class="fas fa-coins"></i>
											</div>
											<div class="list-details">
												<span><%=userData.getM_totalPoint() %> / <%=nextGradeCheck.getGrade_point()%></span> <small>누적포인트 / 다음 등급까지</small>
											</div>
										</li>
									</ul>
								</div>
								<div class="userDate" align="center">
									가입일 :
									<%=userData.getM_date()%>
								</div>
								<div class="card-footer text-center"></div>
							</div>
						</div>
					</div>

					<div class="col-lg-9">
						<div class="card z-depth-3">
							<ul class="nav nav-pills nav-pills-primary nav-justified mt-1" role="tablist">
								<li class="nav-item" role="presentation"><a href="javascript:void();" class="nav-link active show" id="userinfo-tab" data-bs-toggle="tab" data-bs-target="#profile" type="button" role="tab" aria-controls="userinfo" aria-selected="true"><i class="icon-user"></i> <span class="hidden-xs">내 정보 관리</span></a></li>
								<li class="nav-item" role="presentation"><a href="javascript:void();" class="nav-link" id="myreview-tab" data-bs-toggle="tab" data-bs-target="#profileCard" type="button" role="tab" aria-controls="myreview" aria-selected="false"><i class="icon-envelope-open"></i> <span class="hidden-xs">프로필 카드 관리</span></a></li>
								<li class="nav-item" role="presentation"><a href="javascript:void();" class="nav-link" id="myreview-tab" data-bs-toggle="tab" data-bs-target="#petcard" type="button" role="tab" aria-controls="myreview" aria-selected="false"><i class="icon-note"></i> <span class="hidden-xs">나의 펫 관리</span></a></li>
								<li class="nav-item" role="presentation"><a href="javascript:void();" class="nav-link" id="myreview-tab" data-bs-toggle="tab" data-bs-target="#withdrawal" type="button" role="tab" aria-controls="myreview" aria-selected="false"><i class="icon-note"></i> <span class="hidden-xs">회원 탈퇴</span></a></li>
							</ul>
							<div class="tab-content p-3">
								<div class="tab-pane active show" id="profile">
									<div id="myProfileView">
										<div class="myProfileViewTitle" align="center">
											내 정보 보기
											<hr class="myProfileHr">
										</div>

										<div class="myProfileViewContent">
											<div class="contentDirection">
												<div class="myProfileViewContentLeft">
													<div class="profileLine">
														<div class="profileAttribute" align="center">아이디</div>
														<div class="profileValue"><%=userData.getM_id()%></div>
													</div>
													<div class="profileLine">
														<div class="profileAttribute" align="center">닉네임</div>
														<div class="profileValue"><%=userData.getM_nickname()%></div>
													</div>
													<div class="profileLine">
														<div class="profileAttribute" align="center">나 이</div>
														<div class="profileValue"><%=userData.getM_age()%></div>
													</div>
													<div class="profileLine">
														<div class="profileAttribute" align="center">이메일</div>
														<div class="profileValue"><%=userData.getM_email()%></div>
													</div>
												</div>
												<div class="myProfileViewContentRight">
													<div class="profileLine">
														<div class="profileAttribute" align="center">이 름</div>
														<div class="profileValue"><%=userData.getM_name()%></div>

													</div>
													<div class="profileLine">
														<div class="profileAttribute" align="center">성 별</div>
														<div class="profileValue"><%=userData.getM_gender()%></div>
													</div>
													<div class="profileLine">
														<div class="profileAttribute" align="center">전화번호</div>
														<div class="profileValue"><%=userData.getM_phone()%></div>
													</div>
													<div class="profileLine"></div>
												</div>
											</div>
											<div class="myAddressPart">
												<div class="profileAttribute" align="center">주 소</div>
												<div class="profileValue"><%=userData.getM_addr()%></div>
											</div>
											<div class="myProfileViewContentDown" align="center">
												<hr class="myProfileHr">
												<div class="passwordNotice">
													<div>
														내 정보 수정 시 <span style="color: blue; font-weight: bold;">비밀번호 확인</span>이 필요합니다.<br>
													</div>
													아래 <span style="color: blue; font-weight: bold;">'회원 정보 수정'</span> 버튼을 누르시면 <br> 비밀번호 확인 절차를 진행하실 수 있습니다.
												</div>
												<div class="buttonArea">
													<button type='button' data-bs-toggle='modal' data-bs-target='#profileModal' class="profileModifyButton">회원 정보 수정</button>
												</div>
											</div>
										</div>
									</div>
									<div id="userInfoModify"></div>
								</div>
								<div class="tab-pane profilePage" id="profileCard">
									<div class="myProfileViewTitle" align="center">내 프로필 카드</div>
									<div>
										<div class="cardAndButton">
											<div class="profile_card" align="center">
												<div class="skin_part" style="background-image: url('/images/point_shop_skin/<%= mySkinImg %>')">
													<div class="image_part">
														<img class="profile_image" src="images/profile_img/<%= profileCardInfo.getPro_img() %>">
													</div>
													<div class="profile_part">
														<div class="nickname">
															<span class="nickname_text"><strong><%=userData.getM_nickname()%></strong></span>
														</div>
														<div class="introduce">
															<span class="nickname_text2"><strong><%= profileCardInfo.getPro_comment() %></strong></span>
														</div>
													</div>
												</div>
												<div class="info_part">
													<div class="pet_part">
														<%= sbPetList %>
													</div>
													<div class="spec_part">
														<div class="grade">
															<div class="grade_name_part">
																<%=gradeCheck.getGrade_cnt()%>
															</div>
															<div class="grade_image_part">
																<img class="grade_image" src="images/grade/<%=gradeCheck.getGrade_img()%>">
															</div>
														</div>
														<div class="point_and_badge_part">
															<div class="badge_part">
																<div class="badge_title_part">보유 뱃지</div>
																<div class="badge_image_part"><%= sbBadgeList %></div>
															</div>
															<div class="point_part">
																<div class="point_title_part">누적 포인트</div>
																<div class="point_amount_part">
																	<%=userData.getM_totalPoint()%>p
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="cardButtonPart">
												<div class="cardEdit"><button type="button" class="cardEditButton" onclick='showInputCard()'>프로필 카드 편집</button></div>
												<div class="invenView"><button type="button" data-bs-toggle='modal' data-bs-target='#showInvenModal' class="cardEditButton">인벤토리 보기</button></div>
											</div>
										</div>
										
										<div id="secretPart" style='display: none;'>
										<form action="profileEdit.do" method="post" enctype="multipart/form-data">
											<div class="cardEditView" id="cardEditView">
												<div class="cardEditTitlePart">
												 	프로필 카드 편집
												</div>
												<div class="cardEditImagePart" id="cardEditImagePart">
													<img class="cardEditImage" id="previewImage" src="images/profile_img/<%= profileCardInfo.getPro_img() %>">
												</div>
												<div class="cardEditContentPart">
													<div class="imageSelect"><strong>이미지　:　</strong><input type="file" name="editCardImage" id="editCardImage" accept="image/*"></div>
													<div class="commentSelect"><strong>코멘트　:　</strong><input type="text" class="editCardComment" name="editCardComment" id="editCardComment" placeholder=" 코멘트는 20자 이내로 작성해 주세요."></div>
													<div class="saveButtonPart" align="right"><button type="submit" class="saveButton">저장하기</button></div>
												</div>
											</div>
										</form>
										</div>
										
									</div>
								</div>
								<div class="tab-pane" id="petcard">
								<form action="petCard.do" id="petCard" method="post">
									<% if(lists.size() > 0 ) { %>
										<% for( int i=0; i<lists.size(); i++ ) { %>
									<div class="row" style="border-radius : 10px; padding-top : 7px; height:120px; margin: 0 50px 10px 50px; border: 1px solid black; background-color: #f0f0f0; box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.3);">
										<div class="col-lg-1 text-center" style="margin-top: 40px; "> 
											<input class="form-check-input" name="petCheckBox" type="checkbox" value="<%= lists.get(i).getP_seq() %>" id="flexCheckDefault">
										</div>
										<div class="col-lg-2"> 
											<%
												String imgUrl;
												if (lists.get(i) == null || lists.get(i).getP_img() == null) {
												    imgUrl = "https://via.placeholder.com/300x300";
												} else {
												    imgUrl = lists.get(i).getP_img().chars().filter(ch -> ch == '-').count() != 4
												            ? "https://via.placeholder.com/300x300"
												            : "/images/profile_pet/" + lists.get(i).getP_img();
												}
											%>
											<img style="border: white 2px solid; box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.3)" class="img-thumbnail-front" src="<%=imgUrl %>">
										</div>
										<div class="col-lg-9">
											<div class="row">
												<div class="col-lg-4" style="margin: 0; padding-top: 15px">
													<div><strong>이름</strong> : <%=lists.get(i).getP_name() %></div>
													<div style="margin-top:20px;"><strong>생일</strong> : <%=lists.get(i).getP_birth() %></div>
												</div>
												<div class="col-lg-3" style="margin: 0; padding-top: 15px">
													<div><strong>나이</strong> : <%=lists.get(i).getP_age() %></div>
													<div style="margin-top:20px;"><strong>종류</strong> : <%=lists.get(i).getP_species() %></div>
												</div>
												<div class="col-lg-2" style="margin: 0; padding-top: 15px">
													<span><strong>성별</strong> : <%=lists.get(i).getP_gender().equals("암") ? "<img src='../../images/gender_w.png'>" : "<img src='../../images/gender_m.png'>" %></span>
												</div>
												<div class="col-lg-2">
													<input type="button" class="btn btn-danger" value="삭제" style="margin-top: 35px;" onclick="deletePet(<%=lists.get(i).getP_seq() %>)">
												</div>
												
											</div>
										</div>
									</div>
									<% } %>
								<% } else { %>
									<div style='padding-top : 200px; margin-bottom: 200px; text-align: center; font-weight: bold;'><a>등록된 펫 정보가 없습니다.</a></div>
								<% } %>
								</form>
									<div class='text-center'>
										<button id="petConnection" type='button' class='btn btn-primary' onclick="petOk()" style='margin-top: 30px;'>펫 정보 연결</button>
										<button id="petModalOpen" type='button' class='btn btn-success' style='margin-top: 30px;'>펫 정보 추가</button>
									</div>
								</div>
								<div class="tab-pane" id="withdrawal">
									<div class="myProfileViewContentDown" align="center" >
									<div class="myProfileViewTitle" align="center" style="margin-bottom : 150px;">
											회원 탈퇴
										<hr class="myProfileHr">
									</div>
										<div class="passwordNotice">
											<div>
												회원 탈퇴 시 <span style="color: red; font-weight: bold;">계정의 모든 정보가 삭제</span>됩니다.<br>
											</div>
											아래 <span style="color: red; font-weight: bold;">'회원 탈퇴'</span> 버튼을 누르시면 <br> 비밀번호 확인 절차 이후 회원 탈퇴 절차가 진행됩니다.
										</div>
										<div class="buttonArea">
											<button class="btn btn-danger btn-lg active" type='button' data-bs-toggle='modal' data-bs-target='#withdrawalmodal' class="profilewithdrawalButton">회원 탈퇴</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<!-- ======= mypage 끝 ======= -->
	</main>
	<!-- <div id="right-nav" class="r-navbar">
	
	<!-- End #main -->

	<!-- The Modal -->
	<div class="modal fade" id="profileModal">
		<div class="modal-dialog" style="left: 120px; top: 400px; transform: translate(-50%, -50%);">
			<div class="modal-content" style="width: 500px; padding: 20px; background-color: fff; border: 1px solid black;">
				<div class="passwordCheck">
					<div class="passwordCheckTitle">내 정보 수정 - 비밀번호 확인</div>
					<div class="input-group mb-3">
						<span class="input-group-text">비밀번호 입력</span> <input type="password" class="form-control" id="myprofilePassword">
					</div>
					<div class="input-group mb-3">
						<span class="input-group-text">비밀번호 확인</span> <input type="password" class="form-control" id="myprofilePasswordConfirm">
					</div>
				</div>
				<!-- Modal footer -->
				<div class="modalButton">
					<div class="modal-footer" style="border: none; padding: 0px;">
						<button type="button" class="btn btn-primary" data-bs-dismiss="modal" style="border: 1px solid black;" onclick="checkPassword()">입력완료</button>
					</div>
					<div class="modal-footer" style="border: none; padding: 0px;">
						<button type="button" class="btn btn-danger" data-bs-dismiss="modal" style="border: 1px solid black;">닫기</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="petFormModal">
		<div class="modal-dialog" style="right: 50px; top: 550px; transform: translate(-50%, -50%);">
			<div class="modal-content" style="width: 900px; height: 450px; padding: 20px; background-color: fff; border: 1px solid black;">
				<div class="modal-body">
					<form id="petForm" action="/addPetInfo.do" method="post" enctype="multipart/form-data">
						<div class="row">
							<!-- 이미지 등록 -->
							<div class="col-4">
								<div class="text-center mt-5">
									<img src="https://via.placeholder.com/300x300" alt="이미지 미리보기" class="rounded-circle img-thumbnail""><br> <label for="uploadImage" class="btn btn-primary mt-3">이미지 등록</label> 
									<input type="file" id="uploadImage" name="uploads" value="" accept="image/*" style="display: none;">
									<span class="white-text-with-black-border" id="getImageName"></span>
								</div>
							</div>

							<div class="col-8 mt-5">
								<!-- 이름 -->
								<div class="mb-3">
									<label for="name" class="form-label">이름</label> <input type="text" class="" id="name" name="name" placeholder="이름을 입력하세요" required="required">
								</div>

								<!-- 나이, 생일 -->
								<div class="mb-3 row">
									<div class="col">
										<label for="age" class="form-label">나이</label> <input type="text" class="" id="age" name="age" placeholder="나이를 입력하세요" required="required">
									</div>
									<div class="col">
										<label for="birthday" class="form-label">생일</label> <input type="text" class="" id="birthday" name="birthday" placeholder="생일을 입력하세요" required="required">
									</div>
								</div>

								<!-- 종류, 기타 -->
								<div class="mb-3 row">
									<div class="col">
										<label for="type" class="form-label">종류</label>
										<div class="btn-group btn-group-toggle" data-toggle="buttons">
											<label class="btn btn-secondary active"> <input type="radio" name="type" value="강아지" autocomplete="off" checked> 강아지
											</label> <label class="btn btn-secondary"> <input type="radio" name="type" value="고양이" autocomplete="off"> 고양이
											</label>
										</div>
									</div>
									<div class="col">
										<label for="etc" class="form-label">기타</label> <input type="text" class="" id="etc" name="etc" placeholder="기타 정보를 입력하세요">
									</div>
								</div>

								<!-- 성별 -->
								<div class="mb-3">
									<label for="gender" class="form-label">성별</label>
									<div class="btn-group btn-group-toggle" data-toggle="buttons">
										<label class="btn btn-secondary active"> <input type="radio" name="gender" value="암" autocomplete="off" checked> 암
										</label> <label class="btn btn-secondary"> <input type="radio" name="gender" value="수" autocomplete="off"> 수
										</label>
									</div>
								</div>
							</div>

							<div class="col-12 mt-1 text-center">
								<button type="submit" class="btn btn-success">제출</button>
								<button id="closePetInfoModal" type="button" class="btn btn-secondary">취소</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="withdrawalmodal">
	      <div class="modal-dialog" style="left: 120px; top: 400px; transform: translate(-50%, -50%);">
	         <div class="modal-content" style="width: 500px; padding: 20px; background-color: fff; border: 1px solid black;">
	            <form id="withdrow-form" action="/deleteProfile.do" method="POST">
	               <input type="hidden" name="seq" value="<%=userData.getM_seq() %>">
	               <div class="passwordCheck">
	                  <div class="passwordCheckTitle">회원 탈퇴 - 비밀번호 확인</div>
	                  <div class="input-group mb-3">
	                     <span class="input-group-text">비밀번호 입력</span> 
	                     <input type="password" class="form-control" name="DeleteCheckPassword" id="DeleteCheckPassword">
	                  </div>
	                  <div class="input-group mb-3">
	                     <span class="input-group-text">비밀번호 확인</span> 
	                     <input type="password" class="form-control" name="DeleteCheckPasswordConfirm" id="DeleteCheckPasswordConfirm">
	                  </div>
	               </div>
	               <!-- Modal footer -->
	               <div class="modalButton">
	                  <div class="modal-footer" style="border: none; padding: 0px;">
	                     <input id="del-submit-btn" type="submit" class="btn btn-primary" data-bs-dismiss="modal" style="border: 1px solid black;" value="계정삭제">
	                  </div>
	                  <div class="modal-footer" style="border: none; padding: 0px;">
	                     <button type="button" class="btn btn-danger" data-bs-dismiss="modal" style="border: 1px solid black;">닫기</button>
	                  </div>
	               </div>
	            </form>
	         </div>
	      </div>
	   </div>
	
	<%@ include file="/WEB-INF/views/default_bar/footer.jsp"%>
	
	<!-- 인벤토리 Modal -->
	<div class="modal fade" id="showInvenModal">
		<div class="modal-dialog" style="left: 120px; top: 550px; transform: translate(-50%, -50%);">
			<div class="modal-content modalh" style="width: 500px; padding: 20px; background-color: fff; border: 1px solid black;">
				<div class="myInventoryTitle" align="center">내 인벤토리</div>
      <!-- 탭 영역 -->
      <ul class="nav nav-tabs" id="myTab" role="tablist">
        <li class="nav-item" role="presentation">
          <button class="nav-link active" id="tab1-tab" data-bs-toggle="tab" data-bs-target="#tab1" type="button" role="tab" aria-controls="tab1" aria-selected="true">뱃지</button>
        </li>
        <li class="nav-item" role="presentation">
          <button class="nav-link" id="tab2-tab" data-bs-toggle="tab" data-bs-target="#tab2" type="button" role="tab" aria-controls="tab2" aria-selected="false">스킨</button>
        </li>
        <!-- 추가적인 탭 버튼들을 원하는 만큼 추가할 수 있습니다. -->
      </ul>

      <!-- 탭 컨텐츠 영역 -->
      <div class="tab-content" id="myTabContent">
        <div class="tab-pane fade show active" id="tab1" role="tabpanel" aria-labelledby="tab1-tab">
          <!-- 탭1 컨텐츠 -->
          <div align="center">
          	<form action="equip_badge_ok.do" id="equipBadge" method="post">
	          	<%= sbBadgeInven %>
	          	<%= pageSbHtml %>
          	<button type="button" class="equipButton" onclick="badgeOk()">뱃지 장착</button>
          	<button type="button" class="noEquipButton" onclick="badgeNo()">전체 해제</button>
          	</form>
          </div>
        </div>
        <div class="tab-pane fade" id="tab2" role="tabpanel" aria-labelledby="tab2-tab">
          <!-- 탭2 컨텐츠 -->
         <div align="center">
        <form action="equip_skin_ok.do" id="equipSkin" method="post">
          	<%= sbSkinInven %>
	         <%= pageSbHtml2 %>
	     <button type="button" class="equipButton" onclick="skinOk()">스킨 장착</button>
	     <button type="button" class="noEquipButton" onclick="skinNo()">스킨 해제</button>
	     </form>
	     </div>
        </div>
        <!-- 추가적인 탭 컨텐츠들을 원하는 만큼 추가할 수 있습니다. -->
      </div>

      <!-- Modal footer -->
      <div class="modalButton">
        <div class="modal-footer" style="border: none; padding: 0px;">
          <button type="button" class="btn btn-danger" data-bs-dismiss="modal" style="border: 1px solid black;">닫기</button>
        </div>
      </div>
    </div>
  </div>
</div>
</body>

<script type="text/javascript">
	$(document).ready(function() {
	    // ID가 "openPetFormModal"인 버튼이 클릭되면 모달을 엽니다.
	    $('#withdrawalmodal').modal('hide')
	    
	    $("#petModalOpen").on("click", function() {
	    	$("#petFormModal").modal("show");
		});
	    
	    $("#profilewithdrawalButton").on("click", function() {
	    	$("#withdrawalmodal").modal("show");
		});
	    
	    $("#closePetInfoModal").on("click", function() {
	    	$("#petFormModal").modal("hide");
		});
	    
	    $("#uploadImage").on("change", function(event) {
            // 선택된 파일 객체를 가져옵니다.
            var file = event.target.files[0];

            // 파일이 선택되지 않았을 경우 종료
            if (!file) return;

            // FileReader 객체를 생성하여 파일을 읽기 위한 작업 수행
            var reader = new FileReader();

            // 파일 읽기 완료 시 동작할 이벤트 핸들러 등록
            reader.onload = function(e) {
                // 미리보기 이미지 엘리먼트에 파일의 내용을 미리보기로 설정
                $(".img-thumbnail").attr("src", e.target.result);
                var fileName = file.name;
                var extensionIndex = fileName.lastIndexOf('.');
                var extension = fileName.slice(extensionIndex); // 확장자
                var nameWithoutExtension = fileName.slice(0, extensionIndex);

                if (nameWithoutExtension.length > 17) {
                    nameWithoutExtension = nameWithoutExtension.substring(0, 20) + "..";
                }

                var shortenedName = nameWithoutExtension + extension;
                $("#getImageName").text(shortenedName);
            };

            // 파일을 Data URL 형태로 읽기
            reader.readAsDataURL(file);
        });
	    
	    $("#del-submit-btn").click(function (event) {
	        event.preventDefault(); // 기본 폼 제출 동작 막기
			
	        var password = $("#DeleteCheckPassword").val();
	        var confirmPassword = $("#DeleteCheckPasswordConfirm").val();

	        if (password !== confirmPassword) {
	            alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
	        } else {
	            // 비밀번호가 일치하는 경우 폼을 제출
	            $("#withdrow-form").submit();
	            
	        }
	    });

	});
	
	function deletePet(seq) {
        window.location.href = 'deletePetInfo.do?seq=' + seq;
    }
	
	function showInputCard() {
		secretPart.style.display = '';
		document.getElementById( "editCardComment" ).value = String( '<%= myComment %>' );
		
		var previewImage = document.getElementById( 'previewImage' );
		var newImageUrl = 'images/profile_img/<%= myImage %>';
        previewImage.src = newImageUrl;
	}
	
	function checkPassword() {
		var userPassword = '<%= userPassword %>';
		var password = document.getElementById("myprofilePassword").value;
		var confirmPassword = document.getElementById("myprofilePasswordConfirm").value;
		var formDiv = document.getElementById("myProfileView");

		if (password.length === 0) {
			alert("비밀번호를 입력해주세요.");
		} else if (confirmPassword.length === 0) {
			alert("비밀번호 확인을 입력해주세요.");
		} else if (password === userPassword && password === confirmPassword) {
			formDiv.style.display = "none";
			var xhr = new XMLHttpRequest();
			
			xhr.onreadystatechange = function () {
				if (xhr.readyState === 4) {
					if (xhr.status === 200) {
						var contentDiv = document.getElementById("userInfoModify");
						contentDiv.innerHTML = xhr.responseText;
					} else {
						console.error("오류 발생: " + xhr.status);
					}
				}
			};
		xhr.open("GET", "profileModify.do", true);
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhr.send();
		} else if (password === userPassword && password !== confirmPassword) {
			alert("비밀번호가 일치하지 않습니다.");
		} else {
			alert("잘못된 비밀번호입니다.");
		}
	}

	document.getElementById('editCardImage').addEventListener('change', editCardImage);

	function editCardImage(event) {

	    var cardEditImagePart = document.getElementById('cardEditImagePart');

	    cardEditImagePart.innerHTML = "";
	    
	    for(var image of event.target.files) {
			var reader = new FileReader();
			
			reader.onload = function(event) {
				var img = document.createElement("img");
				img.setAttribute("src", event.target.result);
				img.setAttribute("style", "border-radius : 10px; width : 110px; height : 110px;");
				img.setAttribute("name", "editCardImage");
				
				document.getElementById("cardEditImagePart").appendChild(img);
			};
			
			reader.readAsDataURL(image)
		}
	}
	</script>