<%@page import="com.petopia.pointshop.model.InventoryTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.petopia.pointshop.model.GradeTO"%>
<%@page import="com.petopia.pointshop.model.PointShopTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	PointShopTO skin = (PointShopTO)request.getAttribute( "skinChecks" );
	GradeTO userGrade = (GradeTO)request.getAttribute( "gradeChecks" );
	ArrayList<InventoryTO> inventoryChecks = (ArrayList) request.getAttribute("inventoryChecks");
	
	String ps_img = skin.getPs_img().replace("_listSample", "");
	System.out.println( "경로 테스트 : " + skin.getPs_img() );
	System.out.println( "경로 테스트2 : " + ps_img );
	
	String nickname = "닉네임";
	String totalPoint = "0";
	String grade_img = "grade1_green.png";
	String grade_cnt = "펫토피아 주민";
	String memberPoint = "0";
	String userComment = "로그인 후 한줄평을 작성해주세요."; 
	
	String[] psSeqArray = null;
	
	if( userData != null ) {
		nickname = userData.getM_nickname();
		totalPoint = userData.getM_totalPoint();
		grade_cnt = userGrade.getGrade_cnt();
		grade_img = userGrade.getGrade_img();
		memberPoint = userData.getM_point();
		userComment = "노견을 키우고 있습니다. 나도 늙고, 개도 늙었다..";
		
		psSeqArray = new String[inventoryChecks.size()];
		for (int i = 0; i < inventoryChecks.size(); i++) {
		    psSeqArray[i] = inventoryChecks.get(i).getPs_seq();
		}
	}
	
	StringBuilder sbSkinSwitchButton = new StringBuilder();
	StringBuilder sbSkinBuyButton = new StringBuilder();
	StringBuilder sbMyPoint = new StringBuilder();
	
	if( userData != null ) {
		if( psSeqArray.length != 0 ) {
			boolean isPurchased = false;
			for ( String items : psSeqArray ) {
			    if ( items.equals( skin.getPs_seq() ) ) {
			    	sbSkinBuyButton.append("<button class='skinBuyButtonNoLogin' onclick='skinBuy' disabled><strong>이미 구매한 상품입니다.</strong></button>");
					isPurchased = true;
					break;
			    }
			}
			if (!isPurchased) {
				sbSkinBuyButton.append("<button class='skinBuyButton' onclick='skinBuy(" + skin.getPs_price() + ", " + skin.getPs_seq() + ")'>구매하기</button>");
			}
		} else {
			  // 배열이 비어있는 경우 구매하기 버튼 생성
			  sbSkinBuyButton.append("<button class='skinBuyButton' onclick='skinBuy(" + skin.getPs_price() + ", " + skin.getPs_seq() + ")'>구매하기</button>");
		}
		sbMyPoint.append( "<div class='my_point'>내 포인트 : " + memberPoint + "p</div>" );
	} else {
		sbSkinBuyButton.append( "<button class='skinBuyButtonNoLogin' onclick='skinBuy' disabled><strong>구매 불가 : 로그인 필요</strong></button>" );
		sbMyPoint.append( "<div class='my_point'></div>" );
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="./css/profile_card.css">
<link rel="stylesheet" type="text/css" href="./css/skin_view.css">
<script>
	function saleSkin() {
		alert("판매 상품 스킨 버튼을 클릭했습니다!");
	}
	
	function mySkin() {
		alert("내 스킨 버튼을 클릭했습니다!");
	}
	
	function skinBuy( ps_price, ps_seq ) {
		
		var memberPoint = <%= memberPoint %>;
		
		if ( memberPoint < ps_price ) {
			alert( "보유 포인트가 부족합니다." );

		} else {
			var buy = confirm( "선택하신 스킨의 가격은 " + ps_price + "p입니다. 구매하시겠습니까?" );
			if( buy ){
				memberPoint = memberPoint - ps_price;
				alert ( "구매가 완료되었습니다." );
				window.location.href = "http://localhost:8080/item_buy.do?ps_seq="+ps_seq+"&memberPoint="+memberPoint;
			} else {
				alert ( "구매를 취소하였습니다." );
			}
		}
	}
	
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
<section>
<div style="padding-top : 100px;"></div>

<div class="container">
	<div class="left"></div>
	<div class="middle">
		<div class="page_subject">스킨 미리보기</div>
		<div class="skin_view_page">
			<div class="profile_card">
				<div class="skin_part" style="background-image: url('/images/point_shop_skin/<%= ps_img %>')">
					<div class="image_part">
						<img class="profile_image" src="images/profile_img/no_image.png">
					</div>
					<div class="profile_part">
						<div class="nickname">
							<span class="nickname_text"><strong><%=nickname %></strong></span>
						</div>
						<div class="introduce">
							<span class="nickname_text2"><strong><%= userComment %></strong></span>
						</div>
					</div>
				</div>
				<div class="info_part">
					<div class="pet_part" style="height : 175px; padding : 65px 0 0 65px;">
						PETOPIA
					</div>
					<div class="spec_part">
						<div class="grade">
							<div class="grade_name_part">
								<%= grade_cnt %>
							</div>
							<div class="grade_image_part">
								<img class="grade_image" src="images/grade/<%= grade_img %>">
							</div>
						</div>
						<div class="point_and_badge_part">
							<div class="badge_part">
								<div class="badge_title_part">
									보유 뱃지
								</div>
								<div class="badge_image_part">
									뱃지 이미지
								</div>
							</div>
							<div class="point_part">
								<div class="point_title_part">
									누적 포인트
								</div>
								<div class="point_amount_part">
									<%=totalPoint %>p
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="skin_info">
				<div class="skin_name"><%= skin.getPs_name() %></div>
				<div class="skin_price">가격 : <%= skin.getPs_price() %>p</div>
				<%= sbMyPoint %>
				<div class="skin_content"><%= skin.getPs_content() %></div>
				<div class="skin_buy"><%= sbSkinBuyButton %></div>
			</div>
		</div>
		<div class="skin_view_bottom">
			<div class="skinViewButtonArea">
				<%= sbSkinSwitchButton %>
			</div>
			<div class="goListButtonArea" align="right">
				<a href="point_shop_skin.do" class="goListButton">목록으로</a>
			</div>
		</div>
</div>
</div>
<div style="height : 150px;"></div>
</section>
<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>
</html>