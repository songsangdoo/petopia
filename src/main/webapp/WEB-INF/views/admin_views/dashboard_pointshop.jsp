<%@page import="com.petopia.pointshop.model.PointShopTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/default_bar/admin_header.jsp"%>
<%
	ArrayList<PointShopTO> itemLists = (ArrayList) request.getAttribute( "itemLists" );
	
	String selectCheck = "";
	String selectAllOk = "";
	String selectSkinOk = "";
	String selectBadgeOk = "";
	
	if( request.getAttribute( "category" ) != null ) {
		selectCheck = (String) request.getAttribute( "category" );
		if( selectCheck.equals( "0" ) ) {
			selectBadgeOk = "selected";
		} else if ( selectCheck.equals( "1" )) {
			selectSkinOk = "selected";
		} else {
			selectAllOk = "selected";
		}
	}
	
	int cpage = 1;
	if ( request.getParameter( "cpage" ) != null ) {
		cpage = Integer.parseInt( request.getParameter( "cpage" ) );
	}

	int recordsPerpage = 7;
	int totalRecords = itemLists.size();
	int totalPage = ( totalRecords - 1 ) / recordsPerpage + 1;

	int startNum = ( cpage - 1 ) * recordsPerpage;

	int pagePerBlock = 5;

	int badgeCount = itemLists.size();

	StringBuilder sbHtml = new StringBuilder();

	//Add the table header (thead) before the loop
	sbHtml.append( "<table>" );
	sbHtml.append( "		<tr class='titlePart' align='center'>" );
	sbHtml.append( "    		<th class='titleItemSelect'>선택</th>" );
	sbHtml.append( "    		<th class='titleItemCategory'>분류</th>" );
	sbHtml.append( "    		<th class='titleItemImage'>이미지</th>" );
	sbHtml.append( "    		<th class='titleItemName'>상품명</th>" );
	sbHtml.append( "    		<th class='titleItemContent'>상품 설명</th>" );
	sbHtml.append( "    		<th class='titleItemPrice'>판매가</th>" );
	sbHtml.append( "    		<th>" );
	sbHtml.append( "    		</th>" );
	sbHtml.append( "		</tr>" );

	for ( int i = startNum; i < itemLists.size() && i < startNum + recordsPerpage; i++ ) {
		String ps_seq = itemLists.get(i).getPs_seq();
		String ps_cate = itemLists.get(i).getPs_cate();
		String ps_name = itemLists.get(i).getPs_name();
		String ps_content = itemLists.get(i).getPs_content();
		String ps_img = itemLists.get(i).getPs_img();
		String ps_dt = itemLists.get(i).getPs_dt();
		String ps_price = itemLists.get(i).getPs_price();

		sbHtml.append( "<tr class='itemPart' align='center'>" );
		// Add the checkbox button here
		sbHtml.append( "	<td>" );
		sbHtml.append( "		<input type='checkbox' name='itemCheckbox' value='" + ps_seq + "' />" );
		sbHtml.append( "	</td>" );
		if( ps_cate.equals( "0" ) ) {
			sbHtml.append( "	<td>뱃지</td>" );
		} else {
			sbHtml.append( "	<td>스킨</td>" );
		}
		sbHtml.append( "	<td align='center'>" );
		sbHtml.append( "		<div class='itemImage'>" );
		sbHtml.append( "			<a href='view.do?seq=" + ps_seq + "'>" );
		if( ps_cate.equals( "0" ) ) {
			sbHtml.append( "                    <img src='/images/point_shop_badge/" + ps_img + "' border='0' width='50' height='50' />" );			
		} else {
			sbHtml.append( "                    <img src='/images/point_shop_skin/" + ps_img + "' border='0' width='100' height='50' />" );			
		}
		sbHtml.append( "			</a>");
		sbHtml.append( "		</div>");
		sbHtml.append( "	</td>" );
		sbHtml.append( "	<td>" );
		sbHtml.append( "		<div class='itemName'>" );
		sbHtml.append( "			<strong>" + ps_name + "</strong>" );
		sbHtml.append( "		</div>" );
		sbHtml.append( "	</td>" );
		sbHtml.append( "    <td>" + ps_content + "</td>" );
		sbHtml.append( "    <td>" + ps_price + "p</td>" );
		sbHtml.append( "    <td>" );
		sbHtml.append( "        <button type='button' class='itemModify' onclick='itemModify(\"" + ps_seq + "\", \"" + ps_cate + "\", \"" + ps_name + "\", \"" + ps_content + "\", \"" + ps_img + "\", \"" + ps_price + "\")'>상품 정보 수정</button>");
		sbHtml.append( "    </td>");
		sbHtml.append( "</tr>");
	}

	//Close the tbody and table after the loop
	sbHtml.append( "</table>" );

	int startBlock = cpage - (cpage - 1) % pagePerBlock;
	int endBlock = startBlock + pagePerBlock - 1;

	if (endBlock >= totalPage) {
		endBlock = totalPage;
	}

	StringBuilder pageSbHtml = new StringBuilder();

	pageSbHtml.append( "<nav>" );
	pageSbHtml.append( "<ul class='pagination justify-content-center'>" );
	if ( cpage == 1 ) {
		pageSbHtml.append( "<li class='page-item'><a class='page-link'> &lt; </a></li>" );
	} else {
		pageSbHtml.append( "<li class='page-item'><a class='page-link' href='master_pointshop.do?cpage=" + (cpage - 1) + "'> &lt; </a></li>" );
	}
	
	for (int i = startBlock; i <= endBlock; i++) {
		if (i == cpage) {
			pageSbHtml.append( "<li class='page-item'><a class='page-link'>" + i + "</a></li>" );
		} else {
			pageSbHtml.append( "<li class='page-item'><a class='page-link' href='master_pointshop.do?cpage=" + i + "'>" + i + "</a></li>" );
		}
	}

	if ( cpage == totalPage ) {
		pageSbHtml.append( "<li class='page-item'><a class='page-link'> &gt; </a></li>" );
	} else {
		pageSbHtml.append( "<li class='page-item'><a class='page-link' href='master_pointshop.do?cpage=" + (cpage + 1) + "'> &gt; </a></li>" );
	}
	
	pageSbHtml.append( "</ul>" );
	pageSbHtml.append( "</nav>" );
	
	StringBuilder buttonSbHtml = new StringBuilder();
	
	buttonSbHtml.append( "<div>" );
	buttonSbHtml.append( "    <button id='addunit' type='button' data-bs-toggle='modal' data-bs-target='#itemAddModal' class='btn btn-primary'>상품 추가</button>" );
	buttonSbHtml.append( "    <button id='delunit' type='button' class='btn btn-danger'>상품 삭제</button>" );
	buttonSbHtml.append( "</div>" );
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="./assets/css/adminpage/admin_pointshop.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.14.0/Sortable.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="assets/js/dashboardjs/app.js"></script>
<script src="assets/js/dashboardjs/additional.js"></script>

<script>
	$(document).ready(function() {
		$('#delunit').on('click', function() {
			const checkedValues = $( 'input[name="itemCheckbox"]:checked' ).map( function() {
				return this.value;
			}).get();

			if( checkedValues.length === 0 ) {
				alert( "삭제할 상품을 선택해 주세요." );
			} else {
				const confirmed = confirm( "선택한 상품은 총 " + checkedValues.length + "개 입니다. 삭제하시겠습니까?" );
				if (confirmed) {
					$.ajax({
						url: 'deleteItem.do',
						type: 'POST',
						contentType: 'application/json',
						data: JSON.stringify( checkedValues ),
						success: function( response ) {
							location.reload()
						},
						error: function(xhr, textStatus, errorThrown) {
							location.reload()
						}
					});
					alert( "삭제가 완료되었습니다." );
				} else {
					alert( "삭제가 취소되었습니다." );
				}
			}
		});
	
	$( '#menuSelect' ).on( 'change', function() {
		const selectedValue = $(this).val();
		window.location.href = "http://localhost:8080/master_pointshop.do?selectedValue=" + selectedValue;
	});
	
});

	function addItemImage(event) {
		var itemImage = document.getElementById( "itemImage" );
		
		itemImage.innerHTML = "";
		
		var reader = new FileReader();
		
		reader.onload = function(event) {
			var img = document.createElement( "img" );
			
			img.setAttribute( "src", event.target.result );
			img.setAttribute("style", "display:inline-block; margin: 2px; padding: 2px")
		
			itemImage.appendChild( img );
		};
		
		reader.readAsDataURL(event.target.files[0]);
	}
	
	function addItemCardImage(event) {
		var itemImage = document.getElementById( "itemCardImage" );
		
		itemCardImage.innerHTML = "";
		
		var reader = new FileReader();
		
		reader.onload = function(event) {
			var img = document.createElement( "img" );
			
			img.setAttribute( "src", event.target.result );
			img.setAttribute("style", "display:inline-block; margin: 2px; padding: 2px")
		
			itemCardImage.appendChild( img );
		};
		
		reader.readAsDataURL(event.target.files[0]);
	}
	
	function modifyItemImage(event) {
		var itemImagemodify = document.getElementById( "itemImage_modify" );
		
		itemImagemodify.innerHTML = "";
		
		var reader = new FileReader();
		
		reader.onload = function(event) {
			var img = document.createElement( "img" );
			
			img.setAttribute( "src", event.target.result );
			img.setAttribute( "style", "display:inline-block; margin: 2px; padding: 2px" )
		
			itemImagemodify.appendChild( img );
		};
		
		reader.readAsDataURL(event.target.files[0]);
	}
	
	function modifyItemCardImage(event) {
		var itemCardImageModify = document.getElementById( "itemCardImage_modify" );
		
		itemCardImageModify.innerHTML = "";
		
		var reader = new FileReader();
		
		reader.onload = function(event) {
			var img = document.createElement( "img" );
			
			img.setAttribute( "src", event.target.result );
			img.setAttribute("style", "display:inline-block; margin: 2px; padding: 2px")
		
			itemCardImageModify.appendChild( img );
		};
		
		reader.readAsDataURL(event.target.files[0]);
	}
	
	
	function skinInput() {
		cardImagePart.style.display = '';
		itemCardImage.style.display = '';
	}
	
	function badgeInput() {
		cardImagePart.style.display = 'none';
		itemCardImage.style.display = 'none';
	}
	
	function itemAddOk() {
		const radioButton = document.querySelector( 'input[name="itemRadioButton"]:checked' );
		const addItemName = document.getElementById( "addItemName" );
		const addItemContent = document.getElementById( "addItemContent" );
		const addItemPrice = document.getElementById( "addItemPrice" );
		const addItemUpload = document.getElementById( "addItemUpload" );
		const addItemCardUpload = document.getElementById( "addItemCardUpload" );
		
		const contentValue = addItemContent.value.trim();
		const nameValue = addItemName.value.trim();
		const priceValue = addItemPrice.value.trim();
		const uploadValue = addItemUpload.value.trim();
		const cardUploadValue = addItemCardUpload.value.trim();
		
		if ( !radioButton ) {
			alert( '분류를 선택해주세요.' );
			return false;
		} else if ( nameValue === '' ) {
			alert( '상품명을 입력해주세요.' );
		} else if ( nameValue.length > 30 ) {
			alert( '상품명은 30자 이내로 작성해주세요.' );
		} else if ( contentValue === '' ) {
			alert( '상품 설명을 입력해주세요.' );
		} else if ( priceValue === '' ) {
			alert( '가격을 입력해주세요.' );
		} else if ( isNaN(priceValue) ) {
		    alert( '상품 가격은 숫자만 입력 가능합니다.' );
		} else if ( uploadValue === '' ){
			alert( '리스트 이미지를 업로드 해 주세요.' );
		} else if ( radioButton.value === 'skin' && cardUploadValue === '' ) {
		    alert( '프로필카드용 이미지를 업로드 해 주세요.' );
		} else {
			alert( '상품 등록이 완료되었습니다.' );
		}
	}
	
	function itemModifyOk() {
		const radioButton = document.querySelector( 'input[name="modifyItemRadioButton"]:checked' );
		const modifyItemName = document.getElementById( "modifyItemName" );
		const modifyItemContent = document.getElementById( "modifyItemContent" );
		const modifyItemPrice = document.getElementById( "modifyItemPrice" );
		const modifyItemUpload = document.getElementById( "modifyItemUpload" );
		const modifyItemCardUpload = document.getElementById( "modifyItemCardUpload" );
		
		const contentValue = modifyItemContent.value.trim();
		const nameValue = modifyItemName.value.trim();
		const priceValue = modifyItemPrice.value.trim();
		
		const selectedFile = modifyItemUpload.files[0];
		const selectedCardFile = modifyItemCardUpload.files[0];
		
		if ( nameValue === '' ) {
			alert( '상품명을 입력해주세요.' );
		} else if ( nameValue.length > 30 ) {
			alert( '상품명은 30자 이내로 작성해주세요.' );
		} else if ( contentValue === '' ) {
			alert( '상품 설명을 입력해주세요.' );
		} else if ( priceValue === '' ) {
			alert( '가격을 입력해주세요.' );
		} else if ( isNaN(priceValue) ) {
		    alert( '상품 가격은 숫자만 입력 가능합니다.' );
		} else if ( radioButton.value === 'skin' ) { 
			if ( (selectedFile && !selectedCardFile) || (!selectedFile && selectedCardFile) ) {
				alert( '리스트 이미지와 프로필 카드 이미지는 함께 수정 등록하셔야 합니다.' );
			}	
		} else {
			alert( '상품 수정이 완료되었습니다.' );
		}
	}
	
	function itemModify( ps_seq, ps_cate, ps_name, ps_content, ps_img, ps_price ) {
		
		$( '#itemModifyModal' ).modal( 'show' );
		document.getElementById( "hiddenSeq" ).value = String( ps_seq );
		document.getElementById( "hiddenCate" ).value = String( ps_cate );
		document.getElementById( "modifyItemName" ).value = String( ps_name );
		document.getElementById( "modifyItemContent" ).value = String( ps_content );
		document.getElementById( "modifyItemPrice" ).value = String( ps_price );
		
		const itemImageModify = document.getElementById( 'itemImage_modify' );
		const itemCardImageModify = document.getElementById( 'itemCardImage_modify' );
		const imgTag = document.createElement( 'img' );
		const imgTagCard = document.createElement( 'img' );
		
		itemImageModify.textContent = '';
		itemCardImageModify.textContent = '';
		
		if( ps_cate === '0' ) {
			document.getElementById( 'modifyBadge' ).checked = true;
			const baseSrc = '/images/point_shop_badge/';
			imgTag.src = baseSrc + ps_img;
			itemImageModify.appendChild( imgTag );
		} else {
			document.getElementById( 'modifySkin' ).checked = true;
			cardModifyImagePart.style.display = '';
			itemCardImage_modify.style.display = '';
			
			const baseSrc = '/images/point_shop_skin/';
			imgTag.src = baseSrc + ps_img;
			itemImageModify.appendChild( imgTag );
			
			const ps_card_img = ps_img.replace( "_listSample", "" );
			imgTagCard.src = baseSrc + ps_card_img;
			itemCardImageModify.appendChild( imgTagCard );
		}
	}
	
	
</script>

</head>
<body>
	<div class="itemAdminTitle">상품 관리</div>
	<div class="itemAdmin" align="center">
		<div class="tableBorder">
			<div>
				<form action='master_pointshop.do' method='post'>
					<div class="selectAndSearchPart">
						<div class="selectPart">
							<div class="selectTitle">
								상품 분류 : 
							</div>
							<select id='menuSelect'>
								<option value='2' <%= selectAllOk %>>전체</option>
								<option value='1' <%= selectSkinOk %>>스킨</option>
								<option value='0' <%= selectBadgeOk %>>뱃지</option>
							</select>
						</div>
						<div class="searchPart">
							<div class="searchTitle">
								상품명 검색 : 
							</div>
							<input type="text" name="searchItem" class="form-control searchItem" placeholder="검색어를 입력하세요" >
					  		<button class="searchButton" type="submit">검색하기</button>
					  	</div>
				  	</div>
				</form>
			</div>
			<div>
				<%= sbHtml %>
			</div>
			<div class="itemAdminBottom">
				<div class="pagePart">
					<%= pageSbHtml %>
				</div>
				<div class="buttonPart" align="right">
					<%= buttonSbHtml %>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 상품 추가 Modal -->
	<div class="modal fade" id="itemAddModal">
		<div class="modal-dialog" style="top : 350px; transform: translate(-50%, -50%);">
			<div class="modal-content" style="width: 650px; padding: 20px; background-color: fff; border: 1px solid black;">
				<form action='master_pointshop_write_ok.do' method='post' onsubmit="return itemAddOk()" enctype="multipart/form-data">
					<div class="itemAddPage">
						<div class="itemAddModityTitle" align="center">
							상품 추가
						</div>
						<div class="addModifyCategory">
							<div class="addModifyTitle">
								상품 분류
							</div>
							<div class="addModifyContent">
								<input type="radio" name="itemRadioButton" value="skin" onclick='skinInput()'> 스킨　
								<input type="radio" name="itemRadioButton" value="badge" onclick='badgeInput()'> 뱃지
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
								상품 이름
							</div>
							<div class="addModifyContent">
								<input type="text" name="addItemName" id="addItemName" placeholder=" 30자 이내로 작성해주세요.">
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
								상품 설명
							</div>
							<div class="addModifyContent">
								<input type="text" name="addItemContent" id="addItemContent">
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
								상품 가격
							</div>
							<div class="addModifyContent">
								<input type="text" class="addInputPrice" name="addItemPrice" id="addItemPrice"> P
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
								리스트 이미지
							</div>
							<div class="addModifyContent">
								<input type="file" name="addItemUpload" id="addItemUpload" onchange="addItemImage(event)" value="" accept="image/*">
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
							</div>
						</div>
						<div id="itemImage">
						</div>
						
						<div class="addModifyIndex" id="cardImagePart" style='display: none;'>
							<div class="addModifyTitle">
								프로필카드 이미지
							</div>
							<div class="addModifyContent">
								<input type="file" name="addItemCardUpload" id="addItemCardUpload" onchange="addItemCardImage(event)" value="" accept="image/*">
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
							</div>
						</div>
						<div id="itemCardImage" style='display: none;'>
						</div>
					</div>
				<!-- Modal footer -->
				<div class="modalButton" style="display : flex; justify-content: right; padding-top : 20px;">
					<div class="modal-footer" style="border: none; padding: 0px;">
						<button type="submit" class="btn btn-primary" data-bs-dismiss="modal" style="border: 1px solid black;">입력완료</button>
					</div>
					<div class="modal-footer" style="border: none; padding: 0px;">
						<button type="button" class="btn btn-danger" data-bs-dismiss="modal" style="border: 1px solid black;">닫기</button>
					</div>
				</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- 상품 수정 Modal -->
	<div class="modal fade" id="itemModifyModal">
		<div class="modal-dialog" style="top : 350px; transform: translate(-50%, -50%);">
			<div class="modal-content" style="width: 650px; padding: 20px; background-color: fff; border: 1px solid black;">
				<form action='master_pointshop_modify_ok.do' method='post' onsubmit="return itemModifyOk()" enctype="multipart/form-data">
					<div class="itemModifyPage">
						<div class="itemAddModityTitle" align="center">
							상품 수정
						</div>
						<input type="hidden" name="hiddenSeq" id="hiddenSeq">
						<input type="hidden" name="hiddenCate" id="hiddenCate">
						<div class="addModifyCategory">
							<div class="addModifyTitle">
								상품 분류
							</div>
							<div class="addModifyContent">
								<input type="radio" name="modifyItemRadioButton" id="modifySkin" value="skin" disabled> 스킨　
								<input type="radio" name="modifyItemRadioButton" id="modifyBadge" value="badge" disabled> 뱃지
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
								상품 이름
							</div>
							<div class="addModifyContent">
								<input type="text" name="modifyItemName" id="modifyItemName">
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
								상품 설명
							</div>
							<div class="addModifyContent">
								<input type="text" name="modifyItemContent" id="modifyItemContent">
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
								상품 가격
							</div>
							<div class="addModifyContent">
								<input type="text" class="modifyInputPrice" name="modifyItemPrice" id="modifyItemPrice"> P
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
								리스트 이미지
							</div>
							<div class="addModifyContent">
								<input type="file" name="modifyItemUpload" id="modifyItemUpload" onchange="modifyItemImage(event)" value="" accept="image/*">
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
							</div>
						</div>
						<div id="itemImage_modify">
						</div>
						
						<div class="addModifyIndex" id="cardModifyImagePart" style='display: none;'>
							<div class="addModifyTitle">
								프로필카드 이미지
							</div>
							<div class="addModifyContent">
								<input type="file" name="modifyItemCardUpload" id="modifyItemCardUpload" onchange="modifyItemCardImage(event)" value="" accept="image/*">
							</div>
						</div>
						<div class="addModifyIndex">
							<div class="addModifyTitle">
							</div>
						</div>
						<div id="itemCardImage_modify" style='display: none;'>
						</div>
					</div>
					
				<!-- Modal footer -->
				<div class="modalButton" style="display : flex; justify-content: right; padding-top : 20px;">
					<div class="modal-footer" style="border: none; padding: 0px;">
						<button type="submit" class="btn btn-primary" data-bs-dismiss="modal" style="border: 1px solid black;">입력완료</button>
					</div>
					<div class="modal-footer" style="border: none; padding: 0px;">
						<button type="button" class="btn btn-danger" data-bs-dismiss="modal" style="border: 1px solid black;">닫기</button>
					</div>
				</div>
				</form>
			</div>
		</div>
	</div>
	
	
</body>
<%@ include file="/WEB-INF/views/default_bar/admin_footer.jsp"%>

</html>