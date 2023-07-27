<%@page import="com.petopia.mypage.model.MypageProfileCardTO"%>
<%@page import="com.petopia.pointshop.model.InventoryTO"%>
<%@page import="com.petopia.pointshop.model.GradeTO"%>
<%@page import="com.petopia.pointshop.model.PointShopTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	ArrayList<PointShopTO> skinLists = (ArrayList)request.getAttribute( "skinLists" );
	ArrayList<InventoryTO> inventoryChecks = (ArrayList) request.getAttribute("inventoryChecks");
	GradeTO userGrade = (GradeTO) request.getAttribute( "gradeChecks" );
	
	MypageProfileCardTO profileCardInfo = (MypageProfileCardTO) request.getAttribute( "profileCardInfo" );
	ArrayList<PointShopTO> skinPointShopList = (ArrayList<PointShopTO>) request.getAttribute( "skinPointShopList" );
	ArrayList<PointShopTO> badgePointShopList = (ArrayList<PointShopTO>) request.getAttribute( "badgePointShopList" );
	ArrayList<String> myBadgeImgList = (ArrayList<String>) request.getAttribute( "myBadgeImgList" );
	String mySkinImg = (String) request.getAttribute( "mySkinImg" );
	mySkinImg = mySkinImg.replace( "_listSample", "" );
	
	String badgeImg = "";
	
	StringBuilder sbBadgeList = new StringBuilder();
	if( myBadgeImgList != null ) {
		for (int i = 0; i < myBadgeImgList.size(); i++) {
			badgeImg = myBadgeImgList.get(i);
			sbBadgeList.append( "<img src='/images/point_shop_badge/" + badgeImg + "' border='0' width='30' height='30' />" );
		}
	}
	
	int cpage = 1;
	if (request.getParameter("cpage") != null) {
		cpage = Integer.parseInt(request.getParameter("cpage"));
	}

	int recordsPerpage = 6;
	int totalRecords = skinLists.size();
	int totalPage = (totalRecords - 1) / recordsPerpage + 1;
	
	int startNum = (cpage - 1) * recordsPerpage;
	
	int pagePerBlock = 5;
	
	StringBuilder sbTopHtml = new StringBuilder();
	StringBuilder sbHtml = new StringBuilder();
	
	String grade_cnt = "";
	String grade_img = "";
	String nickname = "";
	String totalPoint = "";
	
	if( userData != null ) {
		nickname = userData.getM_nickname();
		totalPoint = userData.getM_totalPoint();
		grade_cnt = userGrade.getGrade_cnt();
		grade_img = userGrade.getGrade_img();
	}
	String memberPoint = "";
	
	if( userData != null) {
		int m_seq = Integer.parseInt(userData.getM_seq());
		nickname = userData.getM_nickname();
		memberPoint = userData.getM_point();
		
		sbTopHtml.append("<div id='shop_member_info'>");
		sbTopHtml.append("	<div class='info_top'>");
		sbTopHtml.append("		<span class='name_point'>" + nickname + "</span> 님의 현재 잔여 포인트는 <span class='name_point'>"
		+ memberPoint + "P</span> 입니다.");
		sbTopHtml.append("	</div>");
		sbTopHtml.append("	<div>");
		sbTopHtml.append("		<button type='button' class='btn btn-primary' data-bs-toggle='modal' data-bs-target='#profileModal'>내 프로필 카드 보기</button>");
		sbTopHtml.append("	</div>");
		sbTopHtml.append("	<div class='mypage_sign'>");
		sbTopHtml.append("		* 마이 페이지에서 프로필 카드를 편집할 수 있습니다.");
		sbTopHtml.append("	</div>");
		sbTopHtml.append("</div>");

	} else {
		sbTopHtml.append( "<div id='shop_member_info'>" );
		sbTopHtml.append( "	<p style='font-size:25px;'> 포인트샵을 이용하시려면 로그인이 필요합니다.</p>" );
		sbTopHtml.append( "	<p> 로그인 하시면 보유하고 계신 현재 포인트를 확인하실 수 있습니다. </p>" );
		sbTopHtml.append( "</div>" );
	}
	
	int skinCount = skinLists.size();
	int count = 0;
	
	for (int i = startNum; i < skinLists.size() && i < startNum + recordsPerpage; i++) {
		String ps_seq = skinLists.get(i).getPs_seq();
		String ps_cate = skinLists.get(i).getPs_cate();
		String ps_name = skinLists.get(i).getPs_name();
		String ps_content = skinLists.get(i).getPs_content();
		String ps_img = skinLists.get(i).getPs_img();
		String ps_dt = skinLists.get(i).getPs_dt();
		String ps_price = skinLists.get(i).getPs_price();
		
		if (count % 2 == 0) {
			sbHtml.append( "<tr>" );
		}
		count++;
		sbHtml.append( "<td>" );
		sbHtml.append( "	<a href='skin_view.do?ps_seq=" + ps_seq + "'>" );
		sbHtml.append( "		<div class='skinGoods_part'>" );
		sbHtml.append( "			<div class='skinImage'>" );
		sbHtml.append( "				<img class='skinImageFile' src='/images/point_shop_skin/" + ps_img + "' border='0' />" );
		sbHtml.append( "			</div>" );
		sbHtml.append( "			<div class='skinInfo_part'>" );
		sbHtml.append( "				<div class='skinName'>" ); 
		sbHtml.append( "					<strong>" + ps_name + "</strong>" );
		sbHtml.append( "				</div>" );
		sbHtml.append( "				<div class='skinPrice' style='text-align: left;'><strong>가격 : " + ps_price + "p</strong></div>" );
		sbHtml.append( "				<div class='skinContent'>" + ps_content + "<div>" );
		sbHtml.append( "			</div>" );
		sbHtml.append( "		</div>" );
		sbHtml.append( "	</a>" );
		sbHtml.append( "</td>" );
		if (count % 2 == 0) {
			sbHtml.append( "</tr>" );
		} else if ( (count % 2 == 1) && ( i == skinCount-1 ) ){
			sbHtml.append( "<td style='width:650px;'></td></tr>" );
		}
	}
	
	StringBuilder adminRegistration = new StringBuilder();
	
	if( userData != null ) {
		String grade = "";
		grade = userData.getGrade_seq();
		if ( grade.equals("1") ) {
			adminRegistration.append( "<div class='align_right'>" );
			adminRegistration.append( "<button type='button' class='btn_write btn_txt01' style='cursor: pointer;' onclick=location.href='item_reg.do'>관리자 상품 등록</button>" );
			adminRegistration.append( "</div>" );
		} else {
			adminRegistration.append( "<div style='height:30px;'>" );
			adminRegistration.append( "</div>" );
		}
	} else {
		adminRegistration.append( "<div style='height:30px;'>" );
		adminRegistration.append( "</div>" );
	}
	
	int startBlock = cpage - (cpage - 1) % pagePerBlock;
	int endBlock = startBlock + pagePerBlock - 1;
	
	if(endBlock >= totalPage){
		endBlock = totalPage;
	}
	
	StringBuilder pageSbHtml = new StringBuilder();
	
	pageSbHtml.append("<nav>");
	pageSbHtml.append("<ul class='pagination justify-content-center'>");
	if(cpage == 1){
		pageSbHtml.append("<li class='page-item'><a class='page-link'> &lt; </a></li>");
	}else{
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='point_shop_skin.do?cpage=" + (cpage - 1) + "'> &lt; </a></li>");
	}
	for(int i = startBlock; i <= endBlock; i++){
		if(i == cpage){
			pageSbHtml.append("<li class='page-item'><a class='page-link'>" + i + "</a></li>");
		} else {
			pageSbHtml.append("<li class='page-item'><a class='page-link' href='point_shop_skin.do?cpage=" + i + "'>" + i + "</a></li>");
		}
	}
	
	if(cpage == totalPage){
		pageSbHtml.append("<li class='page-item'><a class='page-link'> &gt; </a></li>");
	}else{
		pageSbHtml.append("<li class='page-item'><a class='page-link' href='point_shop_skin.do?cpage=" + (cpage + 1) + "'> &gt; </a></li>");
	}
	pageSbHtml.append("</ul>");
	pageSbHtml.append("</nav>");

	
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>스킨 상점</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

	<link rel="stylesheet" type="text/css" href="./css/point_shop_skin.css">
	<link rel="stylesheet" type="text/css" href="./css/profile_card.css">
	
	<script>
		function openProfileCard() {
			
			var width = 675;
			var height = 375;
			
			var left = (screen.width / 2) - (width / 2);
			var top = (screen.height / 2) - (height / 2);
			
	        var url = "http://localhost:8080/profile_card_preview.do";
	        var windowName = "popupWindow";
	        var windowFeatures = "width=" + width + ",height=" + height + ",left=" + left + ",top=" + top;
	
	        window.open(url, windowName, windowFeatures);
	    }
		
		function openInventory() {
			
			var width = 670;
			var height = 420;
			
			var left = (screen.width / 2) - (width / 2);
			var top = (screen.height / 2) - (height / 2);
			
	        var url = "http://localhost:8080/inventory_preview.do";
	        var windowName = "popupWindow";
	        var windowFeatures = "width=" + width + ",height=" + height + ",left=" + left + ",top=" + top;
	
	        window.open(url, windowName, windowFeatures);
	    }
    </script>
</head>

<body>
<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>

<section>
<div style=" padding-top : 30px;">
<!-- 상단 회원 프로필과 잔여 포인트량 텍스트 출력 영역 -->
<%=sbTopHtml %>

<hr>

<div class="container">
	<div class="left"></div>
	
	<div class="middle">
	
	<!-- 앨범 게시판 샘플 -->
	<div class="contents1">
		<div class="con_title">
			<div style="margin: 0px; text-align: left;">
				<p style="font-size:40px;"><strong>스킨샵</strong></p>
				<p style="font-size:16px;">다양한 스킨을 포인트로 구매할 수 있습니다.</p>
			</div>
			<div style="margin: 0px; text-align: right; display: flex; align-items: flex-end;">
				 &gt; 포인트샵 &gt; <strong>&nbsp;스킨샵</strong>
			</div>
		</div> 
		<div class="contents_sub">	
			<div class="board_top">
				<div class="bold">
					<p>판매 중인 스킨 갯수 : <span class="txt_orange"><%= skinCount %></span>개</p>
				</div>
			</div>	
		
		<!-- 게시판 내용 시작 -->
		<table class="board_list">
			<tr>
				<td class='last2'>
					<div class='skinBoard'>
					
						<table class='boardT'>
							<%= sbHtml %>
						</table>
						
					</div>
				</td>
			</tr>
		</table>
		<!-- 게시판 내용 끝 -->	

	</div>
	
	<div class="right"></div>
</div>

		<%= adminRegistration %>
	</div>
</div>
<!--//하단 디자인 -->
</div>

<%= pageSbHtml %>
</section>

	<!-- The Modal -->
	<div class="modal fade" id="profileModal">
		<div class="modal-dialog" style="left : 120px; top : 400px; transform: translate(-50%, -50%);">
			<div class="modal-content" style="width: 700px; padding: 20px; background-color: rgba(0, 0, 0, 0); border: none;">
				<div class="profile_card">
					<div class="skin_part" style="background-image: url('/images/point_shop_skin/<%= mySkinImg %>')">
						<div class="image_part">
							<img class="profile_image" src="images/profile_img/<%= profileCardInfo.getPro_img() %>">
						</div>
						<div class="profile_part">
							<div class="nickname">
								<span class="nickname_text"><strong><%= nickname %></strong></span>
							</div>
							<div class="introduce">
								<span class="nickname_text2"><strong><%= profileCardInfo.getPro_comment() %></strong></span>
							</div>
						</div>
					</div>
					<div class="info_part">
						<div class="pet_part" style="height: 175px;">반려동물 목록 버튼</div>
						<div class="spec_part">
							<div class="grade">
								<div class="grade_name_part"><%= grade_cnt %></div>
								<div class="grade_image_part">
									<img class="grade_image" src="images/grade/<%= grade_img %>" >
								</div>
							</div>
							<div class="point_and_badge_part">
								<div class="badge_part">
									<div class="badge_title_part">보유 뱃지</div>
									<div class="badge_image_part"><%= sbBadgeList %></div>
								</div>
								<div class="point_part">
									<div class="point_title_part">누적 포인트</div>
									<div class="point_amount_part"><%= totalPoint %></div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer" style="border: none; padding-top: 5px;">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal" style="border: 1px solid black">프로필 카드 닫기</button>
				</div>

			</div>
		</div>
	</div>

<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>


</body>