<%@page import="com.petopia.group.model.GroupTO"%>
<%@page import="com.petopia.group.model.GroupDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/default_bar/admin_header.jsp" %>
<%
			request.setCharacterEncoding("utf-8");
	
			MemberTO userData = (MemberTO) session.getAttribute("loginMember");
			
			String member_seq = "";
			if( userData != null){
				member_seq = userData.getM_seq();
			}
			
	 		GroupDAO dao =new GroupDAO();
			ArrayList<GroupTO> lists = (ArrayList)request.getAttribute("lists");
			
			StringBuffer stHtml = new StringBuffer();
			int startIndex = (currentPage - 1) * itemsPerPage;
			int endIndex = Math.min(startIndex + itemsPerPage, lists.size());
			int count=1;
			
			for (int i = startIndex; i < endIndex; i++) {
				GroupTO to = lists.get(i);
				String gr_seq = to.getGR_SEQ();
				String gr_name = to.getGR_NAME();
				String m_seq = to.getM_SEQ();
				String m_nickname = to.getM_NICKNAME();
				String gr_date = to.getGR_DATE();
				String gr_produce = to.getGR_PRODUCE();
				
				if(gr_produce.equals("0")){
					stHtml.append( "  <tr> " );
					//stHtml.append( " <span style='display:none;'>"+gr_seq+" </span>" );
					stHtml.append( " <td><span>"+ gr_seq +" </span></td> " );
					stHtml.append( "  <td> " );
					stHtml.append( "    <img src='https://bootdey.com/img/Content/avatar/avatar1.png' alt=''> " );
					stHtml.append( "   <a href='#' class='user-link'>"+m_nickname+ " </a> " );/* 
					stHtml.append( "  <span class='user-subhead'>"+to.getGrade_seq() +"</span> " ); */
					stHtml.append( " </td> " );
					stHtml.append( " <td>"+ gr_name +"</td> " );
					stHtml.append( " <td>"+ gr_date +"</td> " );
					stHtml.append( " <td> &nbsp;&nbsp;승인 대기중 </td> " );
					stHtml.append( " <td style='width: 20%;'> " );
					stHtml.append( "<a href='group_view.do?gr_seq="+gr_seq+"' class='table-link'> " );
					stHtml.append( " <span class='fa-stack'> " );
					stHtml.append( " <i class='fa fa-square fa-stack-2x'></i> " );
					stHtml.append( " <i class='fa fa-pencil fa-stack-1x fa-inverse'></i> " );
					stHtml.append( " </span> " );
					stHtml.append( " </a> " );
					stHtml.append( " <a class='table-link danger groupdelete'> " );
					stHtml.append( " <span class='fa-stack'>" );
					stHtml.append( "  <i class='fa fa-square fa-stack-2x'></i>" );
					stHtml.append( " <i class='fa fa-trash-o fa-stack-1x fa-inverse'></i>" );
					stHtml.append( " </span>" );
					stHtml.append( " </a>" );
					stHtml.append( "      </td> " );
					stHtml.append( "   </tr> " );
				}else{
					stHtml.append( "  <tr> " );
					stHtml.append( " <td><span>"+ gr_seq +" </span></td> " );
					stHtml.append( "  <td> " );
					stHtml.append( "    <img src='https://bootdey.com/img/Content/avatar/avatar1.png' alt=''> " );
					stHtml.append( "   <a href='#' class='user-link park'>"+m_nickname+ " </a> " );/* 
					stHtml.append( "  <span class='user-subhead'>"+to.getGrade_seq() +"</span> " ); */
					stHtml.append( " </td> " );
					stHtml.append( " <td>"+ gr_name +"</td> " );
					stHtml.append( " <td>"+ gr_date +"</td> " );
					stHtml.append( " <td> &nbsp;&nbsp;승인 완료 </td> " );
					stHtml.append( " <td style='width: 20%;'> " );
					stHtml.append( "<a href='group_view.do?gr_seq="+gr_seq+"' class='table-link'> " );
					stHtml.append( " <span class='fa-stack'> " );
					stHtml.append( " <i class='fa fa-square fa-stack-2x'></i> " );
					stHtml.append( " <i class='fa fa-pencil fa-stack-1x fa-inverse'></i> " );
					stHtml.append( " </span> " );
					stHtml.append( " </a> " );
					stHtml.append( " <a class='table-link danger groupdelete'> " );
					stHtml.append( " <span class='fa-stack'>" );
					stHtml.append( "  <i class='fa fa-square fa-stack-2x'></i>" );
					stHtml.append( " <i class='fa fa-trash-o fa-stack-1x fa-inverse'></i>" );
					stHtml.append( " </span>" );
					stHtml.append( " </a>" );
					stHtml.append( "      </td> " );
					stHtml.append( "   </tr> " );
				}
				}
%>
<script type="text/javascript">
$(".groupdelete").click(function() {
	console.log('클릭')
})
window.onload = function() {
	var userDeleteButton = document.getElementsByClassName("groupdelete");
	
	for (var i = 0; i < userDeleteButton.length; i++) {
		userDeleteButton[i].addEventListener("click", function() {
			// 클릭된 버튼의 부모 요소인 <td> 선택
			var parentTd = this.parentNode;

			// <td> 요소에서 <tr> 요소를 찾아 선택
			var parentTr = parentTd.parentNode;

			// <tr> 요소 내의 모든 span 요소 선택
			var spanElements = parentTr.getElementsByTagName("span");

			// span 요소 안의 값을 가져오기
			var value = spanElements[0].innerText;
			swal({
				title: "동아리를 삭제하시겟습니까?",
				text: "확인을 누르시면 동아리가 삭제되고 복구하실 수 없습니다.",
				icon: "warning",
				buttons: true,
				dangerMode: true,
			})
				.then((willDelete) => {
					if (willDelete) {
						$.ajax({
							url: "/getGroupDelete.do",
							type: "GET",
							data: { value: value },
							success: function(result) {
							},
							error: function(xhr, status, error) {

								console.error(error);
							}
						});
						swal("동아리가 삭제되었습니다!", {
							icon: "success",
						});
					} else {
						swal("동아리가 삭제되지 않앗습니다.");
					}
				})
		})
	}
	}
</script>

<style>
.park{
	cursor: default;
}
.park:hover{
	color : #387DDD;
	text-decoration: none;
}



</style>
			<main class="content">
				<div class="container-fluid p-0">
					<div class="section-title">
					<h2>동아리 회원 관리</h2>
					</div>
					<div class="row">
						<div class="col-lg-12">
							<div class="main-box clearfix">
							    <div class="table-responsive">
							        <table class="table user-list">
							            <thead>
							                <tr>
							                    <th><span>번호</span></th>
							                    <th><span>닉네임</span></th>
							                    <th><span>동아리 명</span></th>
							                    <th><span>신청 일자</span></th>
							                    <th><span>승인 현황</span></th>

							                    <th>&nbsp;</th>
							                </tr>
							            </thead>
							            <tbody>
							                <%=stHtml %>
							            </tbody>
							        </table>
							    </div>
							    <ul class="pagination pull-right">
								  <%--  <%=pageSbHtml %> --%>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</main>
			
			

	
	<div id="groupdeletemodal" class="modal fade bd-example-modal-lg"
	tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">삭제?</div>
	</div>
</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.14.0/Sortable.min.js"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<script src="assets/js/dashboardjs/app.js"></script>
	<script src="assets/js/dashboardjs/additional.js"></script>