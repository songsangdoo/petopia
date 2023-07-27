<%@page import="com.petopia.group.board.model.GroupBoardCommentTO"%>
<%@page import="com.petopia.group.board.model.GroupBoardFileTO"%>
<%@page import="java.util.List"%>
<%@page import="com.petopia.group.board.model.GroupBoardTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
    <%
	request.setCharacterEncoding("utf-8");
	int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
	
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	int m_seq = 0;
	
	if( userData != null){
		m_seq = Integer.parseInt(userData.getM_seq());
	}
	
	
	GroupBoardTO data = (GroupBoardTO)request.getAttribute("data");
	List<GroupBoardFileTO> file_list = (List)request.getAttribute("file_list");
	List<GroupBoardCommentTO> cmt_list = (List)request.getAttribute("cmt_list");
	
	int w_seq = data.getM_SEQ();
	
	boolean isWriter = false;
	
	if(w_seq == m_seq){
		isWriter = true;
	}
	
	String dt = data.getGR_DATE();
	String dt_formatted = String.format("%s-%s-%s %s:%s:%s", dt.substring(0, 4), dt.substring(4, 6), dt.substring(6, 8), dt.substring(8, 10), dt.substring(10, 12), dt.substring(12, 14));
	data.setGR_DATE(dt_formatted);
	 
	int grb_seq = data.getGRB_SEQ();
	
	StringBuilder sbFileHtml = new StringBuilder();
	StringBuilder stFileHtml = new StringBuilder();
	int count = 1;
	if(file_list.size() != 0) {
		for(int i = 0; i < file_list.size(); i++){
			
			int index = file_list.get(i).getGRB_FILE_IMG_PATH().lastIndexOf(".");
			String extension = file_list.get(i).getGRB_FILE_IMG_PATH().substring(index+1);
			
			stFileHtml.append("<div style='text-align : right; font-size : 10px;'>");
			stFileHtml.append("<a href = '../../upload/" + file_list.get(i).getGRB_FILE_IMG_PATH() + "' download>"+file_list.get(i).getGRB_FILE_IMG_PATH()+"</a>");
			stFileHtml.append("</div>");
			if(i == 0||count==2){
				System.out.println("extension에 대하여 : "+extension);
				if(extension.equals("jpg")||extension.equals("png")||extension.equals("gif")||extension.equals("jpeg")){
					count=0;
					sbFileHtml.append("<div class='carousel-item active'>");
					sbFileHtml.append("<img src='../../upload/" + file_list.get(i).getGRB_FILE_IMG_PATH() + "'  class='d-block' width=600 height=400 style='border-radius: 17px;'>");	
					sbFileHtml.append("</div>");
				}else{
					count++;
				}
			}else{
				System.out.println("extension에 대하여 : "+extension);
				if(extension.equals("jpg")||extension.equals("png")||extension.equals("gif")||extension.equals("jpeg")){
					sbFileHtml.append("<div class='carousel-item'>");
					sbFileHtml.append("<img src='../../upload/" + file_list.get(i).getGRB_FILE_IMG_PATH() + "'  class='d-block' width=600 height=400 style='border-radius: 17px;'>");	
					sbFileHtml.append("</div>");
				}
			}
				
		}
	}

	int cmt_cpage = 1;
	if (request.getParameter("cmt_cpage") != null) {
		cmt_cpage = Integer.parseInt(request.getParameter("cmt_cpage"));
	}

	int recordsPerpage = 5;

	int totalRecords = cmt_list.size();

	int totalPage = (totalRecords - 1) / recordsPerpage + 1;

	int startNum = (cmt_cpage - 1) * recordsPerpage;

	int pagePerBlock = 5;
	
	StringBuilder sbCmtHtml = new StringBuilder();
	
	for(int i = startNum; i < cmt_list.size() && i < startNum + recordsPerpage; i++){
		System.out.println(cmt_list.size());
		boolean isCmtWriter = m_seq == cmt_list.get(i).getM_SEQ() ? true: false;
		String cmt_dt = cmt_list.get(i).getGR_CO_DATE();
		String cmt_dt_formatted = String.format("%s-%s-%s %s:%s:%s", cmt_dt.substring(0, 4), cmt_dt.substring(4, 6), cmt_dt.substring(6, 8), cmt_dt.substring(8, 10), cmt_dt.substring(10, 12), cmt_dt.substring(12, 14));
		
		sbCmtHtml.append("<tr>");
		sbCmtHtml.append("<td width='100%'>");
		sbCmtHtml.append("<div class='text-black d-flex justify-content-between' style='font-size: 22px;'>");
		sbCmtHtml.append("<strong>" + cmt_list.get(i).getM_nickname() + "&nbsp;<small>(" + cmt_dt_formatted + ")</small></strong>");
		sbCmtHtml.append("<div class='cmt-btn-group' align='right'>");
		sbCmtHtml.append("<button type='button' class='btn btn-outline-primary' onclick='rec_up(" + grb_seq + ", " + cmt_list.get(i).getGR_CO_SEQ() + ", " + gr_seq + ")'><img src='../../images/free-icon-thumb-up-8279617.png'>&nbsp;&nbsp;&nbsp;&nbsp;<span>" + cmt_list.get(i).getGR_CO_REC_CNT() + "</span></button>");
		sbCmtHtml.append("&nbsp;&nbsp;");
		sbCmtHtml.append("<button type='button' class='btn btn-outline-danger' onclick='no_rec_up(" + grb_seq + ", " + cmt_list.get(i).getGR_CO_SEQ() + ", " + gr_seq + ")'><img src='../../images/free-icon-thumb-down-8279616.png'>&nbsp;&nbsp;&nbsp;&nbsp;<span>" + cmt_list.get(i).getGR_CO_NO_REC_CNT() + "</span></button>");
		sbCmtHtml.append("&nbsp;&nbsp;");
		if(isCmtWriter || userData.getGrade_seq().equals("1")){
			sbCmtHtml.append("<button type='button' class='btn btn-outline-danger' onclick='cmt_del(" + cmt_list.get(i).getGR_CO_SEQ() + ", " + grb_seq + ", " + gr_seq + ")'>삭	제</button>");
		}
		sbCmtHtml.append("&nbsp;&nbsp;");
		sbCmtHtml.append("</div>");
		sbCmtHtml.append("</div>");
		sbCmtHtml.append("<br>");
		sbCmtHtml.append("<div><p>" + cmt_list.get(i).getGR_CO_CONTENT() + "</p></div>");
		sbCmtHtml.append("</td>");
		sbCmtHtml.append("<td>");
		sbCmtHtml.append("</td>");
		sbCmtHtml.append("</tr>");
	}
	
	StringBuilder cmt_pageSbHtml = new StringBuilder();

	int startBlock = cmt_cpage - (cmt_cpage - 1) % pagePerBlock;
	int endBlock = startBlock + pagePerBlock - 1;

	if(endBlock >= totalPage){
		endBlock = totalPage;
	}
	cmt_pageSbHtml.append("<div>");
	cmt_pageSbHtml.append("<nav>");
	cmt_pageSbHtml.append("<ul class='pagination justify-content-center'>");
	if(cmt_cpage == 1){
		cmt_pageSbHtml.append("<li class='page-item'><a class='page-link'> &lt; </a></li>");
	}else{
		cmt_pageSbHtml.append("<li class='page-item'><a class='page-link' href='mygroup_board_view.do?grb_seq=" + grb_seq + "&cmt_cpage=" + (cmt_cpage - 1) + "&gr_seq=" + gr_seq + "'> &lt; </a></li>");
	}
	for(int i = startBlock; i <= endBlock; i++){
		if(i == cmt_cpage){
			cmt_pageSbHtml.append("<li class='page-item'><a class='page-link'>" + i + "</a></li>");
		} else{
			cmt_pageSbHtml.append("<li class='page-item'><a class='page-link' href='mygroup_board_view.do?grb_seq=" + grb_seq + "&cmt_cpage=" + i + "&gr_seq=" + gr_seq + "'>" + i + "</a></li>");
		}
	}

	if(cmt_cpage == totalPage){
		cmt_pageSbHtml.append("<li class='page-item'><a class='page-link'> &gt; </a></li>");
	}else{
		cmt_pageSbHtml.append("<li class='page-item'><a class='page-link' href='mygroup_board_view.do?grb_seq=" + grb_seq + "&cmt_cpage=" + (cmt_cpage + 1) + "&gr_seq=" + gr_seq + "'> &gt; </a></li>");
	}
	cmt_pageSbHtml.append("</ul>");
	cmt_pageSbHtml.append("</nav>");
	cmt_pageSbHtml.append("</div>");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script type="text/javascript">
		window.onload = function(){
			const cbtn = document.getElementById('cbtn');
			
			cbtn.onclick = function(){
				if(<%= userData == null%>){
					alert('로그인을 먼저 해주세요');
					return false;
				}
				
				if(document.cfrm.gr_co_content.value.trim() == ''){
					alert('댓글 내용을 입력해주세요');
					return false;
				}
				
				var check = confirm('댓글을 작성하시겠습니까?');
				
				if(check != true){
					return false;
				}
			}
			
			const recBtn = document.getElementById('recBtn');
			
			recBtn.onclick = function(){
				if(<%= userData == null%>){
					alert('로그인을 먼저 해주세요');
					return false;
				}
				
				location.href= 'mygroup_board_view_rec.do?grb_seq=<%= grb_seq %>&gr_seq=<%=request.getParameter("gr_seq")%>';
			}
		
		};
		
		function cmt_del(cmt_seq, grb_seq, gr_seq){
			  var check = confirm('댓글을 삭제하시겠습니까?');
			  
			  if(check != true){
				  return false;
			  }
			  
			  location.href = 'mygroup_board_view_cmt_del.do?grb_seq=' + grb_seq + '&gr_co_seq=' + cmt_seq +'&gr_seq=' + gr_seq;
		}
		
		function post_del(grb_seq, gr_seq){
			  var check = confirm('게시물을 삭제하시겠습니까?');
			  
			  if(check != true){
				  return false;
			  }
			  
			  location.href='mygroup_board_delete_ok.do?grb_seq=' + grb_seq +'&gr_seq=' + gr_seq;
		}
		
		function rec_up(seq, cmt_seq, gr_seq){
			
			location.href='mygroup_board_view_cmt_rec.do?grb_seq=' + seq + '&&gr_co_seq=' + cmt_seq +'&gr_seq=' + gr_seq;
		}
		
		function no_rec_up(seq, cmt_seq, gr_seq){
			
			location.href='mygroup_board_view_cmt_no_rec.do?grb_seq=' + seq + '&&gr_co_seq=' + cmt_seq +'&gr_seq=' + gr_seq;
		}
</script>  
</head>
<body>

<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>


	<main id="main">
			<section id="img-view">
			
			<div class="container w-50 mt-3"> 
				<div>	
				<!--게시판-->
					<div id="board-view">
						<table class="table">
						<tr>
							<th class="text-center">
							<span class="display-6 text-black"><%= data.getGR_SUBJECT() %></span>
							<br><br>
							<div class="d-flex justify-content-end text-black">
							  <div class="p-2 bd-highlight">작성자 : <%= data.getM_NICKNAME() %></div>
							  <div class="p-2 bd-highlight">작성일 : <%= data.getGR_DATE() %></div>
							  <div class="p-2 bd-highlight">조회수 : <%= data.getGR_HIT() %></div>
							  <div class="p-2 bd-highlight">추천수 : <%= data.getGR_GOOD() %></div>
							</div>
							</th>
						</tr>
						<tr>
							<td id="info-content">
								<%= stFileHtml %>
								<div class="d-flex justify-content-left">
								<div id="carouselExampleFade" class="carousel slide carousel-fade">
								  <div class="carousel-inner">
								    <%= sbFileHtml %>
								   
								  </div>
								  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
								    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
								    <span class="visually-hidden">Previous</span>
								  </button>
								  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
								    <span class="carousel-control-next-icon" aria-hidden="true"></span>
								    <span class="visually-hidden">Next</span>
								  </button>
								</div>
								</div>
								<div class="container">
								<p class="lead"><%= data.getGR_CONTENT() %></p>
								</div>
							</td>
						</tr>			
						</table>
						
						<!-- 글 수정, 삭제버튼 -->
						<div class='cmt-btn-group' align="right">
							<button type="button" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='mygroup_board_list.do?gr_seq=<%=gr_seq %>'" >목	록</button>
							<%if(isWriter || userData.getGrade_seq().equals("1")){ %>
								<button type="button" class="btn btn-outline-primary" style="cursor: pointer;" onclick="location.href='mygroup_board_modify.do?grb_seq=<%= grb_seq %>&gr_seq=<%=gr_seq%>'">수    정</button>
							
								<button type="button" class="btn btn-outline-danger" style="cursor: pointer;" onclick="post_del(<%= grb_seq %>, gr_seq=<%=gr_seq%>)">삭	제</button>
							<%} %>
						</div>
						<!-- 글 수정, 삭제버튼 -->
						
						<!-- 추천 -->
						<div class="d-flex justify-content-center">
						<div class="card" style="width: 7rem; border:none; cursor:pointer;" id='recBtn'>
						  <img src="../../images/free-icon-like-2048883.png" class="card-img-top">
						  <div class="card-body">
						    <p class="card-text text-center"><span class="text-primary display-7">추천</span></p>
						  </div>
						</div>
						</div> 
						<!-- 추천 -->
						
						<br><br>
						
						<!-- comment -->
						<table class="table">
						<tr>
						<td>
						<strong>댓글수</strong> &nbsp;&nbsp; <%= data.getGR_COMMENT() %>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="mygroup_board_view.do?grb_seq=<%= grb_seq %>&gr_seq=<%=gr_seq %>&&order_flag=latest">최신순</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="mygroup_board_view.do?grb_seq=<%= grb_seq %>&gr_seq=<%=gr_seq %>&&order_flag=rec">추천순</a>
						</td>
						<td></td>
						</tr>
						
						<!-- 댓글 리스트 -->
						<%= sbCmtHtml %>
						<!-- // 댓글 리스트 -->
						
						</table>
						
						<!-- comment 페이지네이션 -->
						<%= cmt_pageSbHtml %>
						<!-- // comment 페이지네이션 -->
						
						<!-- 댓글 작성 -->
							<div class="container w-80 mt-2">
								<form action="mygroup_board_view_cmt_write.do" method="post" name="cfrm">
								<input type="hidden" value="<%= grb_seq %>" name="grb_seq">
								<input type="hidden" value="<%= gr_seq %>" name="gr_seq">
								<table class="table">
								<tr>
									<td>
										<div class="input-group mb-3">
											<span class="input-group-text">내 용</span>
											<textarea class="form-control" name="gr_co_content" rows="5" style="resize: none;"></textarea>
										</div>
										<div align="right">
											<input type="submit" class="btn btn-outline-primary" value="댓글 등록" id="cbtn"/>
										</div>
									</td>
								</tr>
								</table>
								</form>
							</div>
						<!-- // 댓글 작성 -->
					</div>
					<!--//게시판-->
				</div>
			<!-- 하단 디자인 -->
			</div>
					
			</section>
		</main>

<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>
</html>