<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>포인트샵 가이드</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

	<link rel="stylesheet" type="text/css" href="./css/point_shop_info.css">
</head>

<body>
     <jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	<section>
		<div class="pointshopGuideView">
			<div class="viewLeft"></div>
			<div class="viewCenter">
				<div class="pointshopGuideTitle" align="center">포인트 가이드</div>
				<div class="subTitle">Q. 포인트란?</div>
				<div class="flex">
					<div><img src="/images/point_shop_guide/point_intro.png" style="padding-left : 20px;"></div>
					<div class="rightTextAlign">
						포인트는 펫토피아 사이트 내의 활동으로부터 얻을 수 있는 고유 화폐입니다. <br>
						누적된 포인트로 사이트 내의 <span class="impactBlue">등급</span>을 올릴 수도 있고, <br>
						프로필 카드를 커스터마이징 하는데 필요한 <span class="impactBlue">뱃지</span>나 <span class="impactBlue">스킨</span>을 구입할 수 있습니다.
					</div>
				</div>
				<div class="subTitle">Q. 누적 포인트? 잔여 포인트?</div>
				<div style="padding-left : 20px;">
					기본적으로 포인트 획득 시 <span class="impactBlue">누적 포인트, 잔여 포인트</span>가 동시에 부여되며 같은 양만큼 증가합니다. <br><br>
					<span class="impactViolet">누적 포인트 :</span> 특정 활동으로 인해 포인트가 계속 증가하며, 일정 수치 달성시 사이트 내 등급이 상승합니다. <br>
					<span class="impactViolet">잔여 포인트 :</span> 포인트의 증가폭은 누적 포인트와 동일하나, 포인트 샵에서 아이템을 구매할 수 있는 재화로 활용할 수 있습니다.
				</div>
				<div class="flex" style="height : 200px;">
					<div>
						<div class="subTitle" style="padding-top : 50px;">Q. 포인트 습득 방법</div>
						<div style="padding-left : 20px;">
							일부 특정 게시판을 제외한 대다수의 커뮤니티 게시판에서 <br>
							글 작성 시 하루 3회까지 일일 포인트를 획득할 수 있습니다.
						</div>
					</div>
					<div style="padding : 50px 0 0 160px;"><img src="/images/point_shop_guide/point_increase.png" class="bigImage"></div>
				</div>
				<div style="height : 80px;">
				</div>
			</div>
			<div class="viewRight"></div>
    	</div>
	</section>
	<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>