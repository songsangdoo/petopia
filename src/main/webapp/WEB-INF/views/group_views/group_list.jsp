<%@page import="com.petopia.group.model.GroupHashTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@page import="com.petopia.group.model.GroupTO"%>
<%@page import="com.petopia.group.model.GroupDAO"%>
<%@page import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>

<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
    <%
	    request.setCharacterEncoding("utf-8");
		String liValue = request.getParameter("searchStr");
    
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		
		
		String member_seq = "";
		
		if( userData != null){
			member_seq = userData.getM_seq();
		}
		System.out.println("m_seq = "+member_seq);
		
   		GroupDAO dao =new GroupDAO();
		ArrayList<GroupTO> lists = (ArrayList)request.getAttribute("lists");
		//ArrayList<GroupHashTO> lists2 = (ArrayList)request.getAttribute("lists2");
		
		int totalRecode = lists.size();

		StringBuffer liHtml = new StringBuffer();
		if(liValue!=null){
			if(liValue.equals("강아지")){
				liHtml.append("<li id ='all'>전체</li>");
				liHtml.append("<li id ='dog' class='liValue'>강아지</li>");
				liHtml.append("<li id ='cat'>고양이</li>");
			}else if(liValue.equals("고양이")){
				liHtml.append("<li id ='all'>전체</li>");
				liHtml.append("<li id ='dog'>강아지</li>");
				liHtml.append("<li id ='cat' class='liValue'>고양이</li>");
			}else{
				liHtml.append("<li id ='all' class='liValue'>전체</li>");
				liHtml.append("<li id ='dog'>강아지</li>");
				liHtml.append("<li id ='cat'>고양이</li>");
			}
		}
		else{
			liHtml.append("<li id ='all' class='liValue'>전체</li>");
			liHtml.append("<li id ='dog'>강아지</li>");
			liHtml.append("<li id ='cat'>고양이</li>");
		}
		
		int cpage = 1;
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}
		
		
		StringBuffer sbHtml = new StringBuffer();
		StringBuffer stHtml = new StringBuffer();
		String hash ="aa";
		int count = 1;
		int be_seq = 0;
		int flag1 = 0;
		int flag2 = 0;
		
		/* 첫 사이클 be_seq =0 gr_seq 15 -> else 로 이동 만약에 gr_hash_cnt<=1 이면 (여기 들어오면 for 사실상 끝) 
				
		if(be_seq == gr_seq){
			hash += has_content;
			count++;
			if(count == gr_hash_cnt){
				//gr_hash_cnt의 최대치일 때 append
				
			}
		}else {
			if(gr_hash_cnt<=1){
				//1이거나 0일때 여기서 append
				//여기서 append
				//한개니깐 끝 여기 들어온 이상 끝
			}else{//gr_hash_cnt가 2개 이상이라는 뜻 그러면 count =1로 만들고 be_seq랑 맞지 않는 다는거는 gr_seq가 달라졌다는 걸 의미
			// 그니깐 새로 count ==1 이 되고 hash에 첫번째 has_content를 넣어준다.
			// be_seq도 gr_seq로 만들어 준다.
				count = 1;
				hash = has_content; 
				be_seq = gr_seq;
			}
		} */
		
		
		for(GroupTO to : lists){
				int gr_seq = Integer.parseInt(to.getGR_SEQ());
				String m_seq = to.getM_SEQ();
				String gr_name = to.getGR_NAME();
				String gr_admin = to.getGR_ADMIN();
				String gr_inwon = to.getGR_INWON();
				String gr_explan = to.getGR_EXPLAN();
				String gr_date = to.getGR_DATE();
				String dt_formatted = gr_date.substring(0, 4) + "-" + gr_date.substring(4, 6) + "-" + gr_date.substring(6, 8);
				String has_content = to.getHAS_CONTENT();
				int gr_hash_cnt = to.getGR_HASH_CNT();
				String gr_filename = to.getGR_FILENAME();
				String gr_produce = to.getGR_PRODUCE();
				System.out.println("\nhashtag 이지롱  "+has_content);
				if(be_seq == gr_seq){
					hash += has_content;
					count++;
					if(count == gr_hash_cnt){
						//gr_hash_cnt의 최대치일 때 append
						if(gr_produce.equals("0") && member_seq.equals(m_seq)){
							++flag1;
							if (flag1 % 3 == 1) {
								stHtml.append("<div class='row'>");
							}
						
							stHtml.append("<div class='col-4'>");
							stHtml.append("<div class='post-slide'>");
							stHtml.append("<div class='post-img'>");
							stHtml.append("<img src='../../upload/" + gr_filename + "' style='height:15rem;'> <a href='group_view.do?gr_seq="+ gr_seq +"' class='over-layer'><i");
							stHtml.append("class='fa fa-link'></i></a>");
							stHtml.append("</div>");
							stHtml.append("<div class='post-content'>");
							stHtml.append("<h3 class='post-title'>");
							stHtml.append("<a href='group_view.do?gr_seq="+ gr_seq +"'>" + gr_name + "</a>");
							stHtml.append("</h3>");
							stHtml.append("<p class='post-description'style='height:50px; width : 100%;'>" + gr_explan + "</p>");
							stHtml.append("<p class='post-description' style='text-align : right; font-size : 9px; font-color : #A9A9A9;'>" + hash + "</p>");
							stHtml.append("<span class='post-date'><i class='fa fa-clock-o'></i>" + gr_date + "&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;인원수 " + gr_inwon + "</span>"); 
							stHtml.append("<a href='group_view.do?gr_seq="+ gr_seq +"' class='read-more'>read more</a>");
							stHtml.append("</div>");
							stHtml.append("</div>");
							stHtml.append("</div>");

							if (flag1 % 3 == 0) {
								stHtml.append("</div><br>");
							}
						}else if(gr_produce.equals("1")){
							++flag2;
							if (flag2 % 3 == 1) {
								sbHtml.append("<div class='row'>");
							}
							sbHtml.append("<div class='col-4 filter-dog'>");
							sbHtml.append("<div class='post-slide'>");
							sbHtml.append("<div class='post-img'>");
							sbHtml.append("<img src='../../upload/" + gr_filename + "' style='height:15rem;'> <a href='group_view.do?gr_seq="+ gr_seq +"' class='over-layer'><i");
							sbHtml.append("class='fa fa-link'></i></a>");
							sbHtml.append("</div>");
							sbHtml.append("<div class='post-content'>");
							sbHtml.append("<h3 class='post-title'>");
							sbHtml.append("<a href='group_view.do?gr_seq="+ gr_seq +"'>" + gr_name + "</a>");
							sbHtml.append("</h3>");
							sbHtml.append("<p class='post-description' style='height:50px; width : 100%;'>" + gr_explan + "</p>");
							sbHtml.append("<p class='post-description' style='text-align : right; font-size : 9px; font-color : #A9A9A9;'>" + hash + "</p>");
							sbHtml.append("<span class='post-date'><i class='fa fa-clock-o'></i>" + gr_date + "&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;인원 수 " + gr_inwon + "</span>"); 
							sbHtml.append("<a href='group_view.do?gr_seq="+ gr_seq +"' class='read-more'>read more</a>");
							sbHtml.append("</div>");
							sbHtml.append("</div>");
							sbHtml.append("</div>");
							
							if (flag2 % 3 == 0) {
								sbHtml.append("</div><br>");
							}
						}// else if 
						System.out.println(hash);
					}
				}else {
					if(gr_hash_cnt<=1){
						//1이거나 0일때 여기서 append
						if(gr_produce.equals("0") && member_seq.equals(m_seq)){
							++flag1;
							if (flag1 % 3 == 1) {
								stHtml.append("<div class='row'>");
							}
						
							stHtml.append("<div class='col-4'>");
							stHtml.append("<div class='post-slide'>");
							stHtml.append("<div class='post-img'>");
							stHtml.append("<img src='../../upload/" + gr_filename + "' style='height:15rem;'> <a href='group_view.do?gr_seq="+ gr_seq +"' class='over-layer'><i");
							stHtml.append("class='fa fa-link'></i></a>");
							stHtml.append("</div>");
							stHtml.append("<div class='post-content'>");
							stHtml.append("<h3 class='post-title'>");
							stHtml.append("<a href='group_view.do?gr_seq="+ gr_seq +"'>" + gr_name + "</a>");
							stHtml.append("</h3>");
							stHtml.append("<p class='post-description' style='height:50px; width : 100%;'>" + gr_explan + "</p>");
							stHtml.append("<p class='post-description' style='text-align : right; font-size : 9px; font-color : #A9A9A9;'>" + has_content + "</p>");
							stHtml.append("<span class='post-date'><i class='fa fa-clock-o'></i>" + gr_date + "&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;인원수 " + gr_inwon + "</span>"); 
							stHtml.append("<a href='group_view.do?gr_seq="+ gr_seq +"' class='read-more'>read more</a>");
							stHtml.append("</div>");
							stHtml.append("</div>");
							stHtml.append("</div>");

							if (flag1 % 3 == 0) {
								stHtml.append("</div><br>");
							}
						}else if(gr_produce.equals("1")){
							++flag2;
							if (flag2 % 3 == 1) {
								sbHtml.append("<div class='row'>");
							}
							sbHtml.append("<div class='col-4'>");
							sbHtml.append("<div  style='height : 500px;' class='post-slide'>");
							sbHtml.append("<div class='post-img'>");
							sbHtml.append("<img src='../../upload/" + gr_filename + "' style='height:15rem;'> <a href='group_view.do?gr_seq="+ gr_seq +"' class='over-layer'><i");
							sbHtml.append("class='fa fa-link'></i></a>");
							sbHtml.append("</div>");
							sbHtml.append("<div class='post-content'>");
							sbHtml.append("<h3 class='post-title'>");
							sbHtml.append("<a href='group_view.do?gr_seq="+ gr_seq +"'>" + gr_name + "</a>");
							sbHtml.append("</h3>");
							sbHtml.append("<div class='post-description' style='height : 50px; width : 100%;'>" + gr_explan + "</div>");
							sbHtml.append("<p class='post-description' style='text-align : right; font-size : 9px; font-color : #A9A9A9;'>" + has_content + "</p>");
							sbHtml.append("<span class='post-date'><i class='fa fa-clock-o'></i>" + gr_date + "&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;인원 수 " + gr_inwon + "</span>"); 
							sbHtml.append("<a href='group_view.do?gr_seq="+ gr_seq +"' class='read-more'>read more</a>");
							sbHtml.append("</div>");
							sbHtml.append("</div>");
							sbHtml.append("</div>");
							
							if (flag2 % 3 == 0) {
								sbHtml.append("</div><br>");
							}
							System.out.println(hash);
						}// else if 
					}else{
						count = 1;
						hash = has_content; 
						be_seq = gr_seq;
					}
				}
				
			}
		
		
		if(flag1 % 3 != 0){
			stHtml.append("</div><br>");
		}
		if(flag2 % 3 != 0){
			sbHtml.append("</div><br>");
		}
		
		
		//페이지
		
		/* StringBuilder pageSbHtml = new StringBuilder();
	
		int startBlock = cpage - (cpage - 1) % pagePerBlock;
		int endBlock = startBlock + pagePerBlock - 1;
		
		if(endBlock >= totalPage){
			endBlock = totalPage;
		}
		
		pageSbHtml.append("<nav>");
		pageSbHtml.append("<ul class='pagination justify-content-center'>");
		if(cpage == 1){
			pageSbHtml.append("<li class='page-item'><a class='page-link'> &lt; </a></li>");
		}else{
			pageSbHtml.append("<li class='page-item'><a class='page-link' href='album_list.do?cpage=" + (cpage - 1) + "'> &lt; </a></li>");
		}
		for(int i = startBlock; i <= endBlock; i++){
			if(i == cpage){
				pageSbHtml.append("<li class='page-item'><a class='page-link'>" + i + "</a></li>");
			} else{
				pageSbHtml.append("<li class='page-item'><a class='page-link' href='album_list.do?cpage=" + i + "'>" + i + "</a></li>");
			}
		}
		
		if(cpage == totalPage){
			pageSbHtml.append("<li class='page-item'><a class='page-link'> &gt; </a></li>");
		}else{
			pageSbHtml.append("<li class='page-item'><a class='page-link' href='album_list.do?cpage=" + (cpage + 1) + "'> &gt; </a></li>");
		}
		pageSbHtml.append("</ul>");
		pageSbHtml.append("</nav>"); */
			
		
		
		
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script type="text/javascript">

window.onload = function() {
	document.getElementById( 'all' ).onclick = function() {
		
		document.wfrm.submit();
	}
	document.getElementById( 'dog' ).onclick = function() {
		document.wfrm.option[2].selected = true;
		document.getElementById('searchStr').value = '강아지';
		document.wfrm.submit();
	}
	document.getElementById( 'cat' ).onclick = function() {
		document.wfrm.option[2].selected = true;
		document.getElementById('searchStr').value = '고양이';
		document.wfrm.submit();
	}
}

</script>
<style type="text/css">

.parent {display: flex;}
.right-bottom {margin-left: auto;}
.abc li{
	 cursor: pointer;
    display: inline-block;
    margin: 10px 5px;
    font-size: 15px;
    font-weight: 500;
    line-height: 1;
    color: #444444;
    transition: all 0.3s;
    padding: 8px 20px;
    border-radius: 50px;
    font-family: "Poppins", sans-serif;	
} 

.abc li:hover{
	background: #47b2e4;
    color: #fff;
}
.abc .liValue{
	background: #47b2e4;
    color: #fff;
}
.box-flex{
 	display: flex;
    justify-content: flex-start;
}
.left-box {
   
}
.middle-box{
}

.right-box {
	margin-left: auto;
}

</style>
<link href="assets/css/writeFooter.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	<% if( userData != null){%>
			<jsp:include page="/WEB-INF/views/default_bar/sidebar.jsp"/>
	<% }%>
	
	
	<section id="portfolio" class="portfolio">
		<div class="container">
				<div class = "section-title"><h2>동아리 목록</h2></div>
					
							<div class="container" style='margin-bottom : -10px; margin-top : -20px;'>
							<div align ="center">
								<ul class="justify-content-center abc">
									<%=liHtml %>
								</ul>
							</div>
							<div class="box-flex">
							<div class="left-box" style='text-align:left; margin-top: 12px;'>		
									<input type="button" class="btn btn-outline-primary" value="MY동아리" onclick="location.href='mygroup_list.do?'" />	
							</div>
							<div class="middle-box" style='text-align:left; margin-top: 12px; margin-left: 5px;'>
								<input type="button" class="btn btn-outline-primary" value="동아리 신청" onclick="location.href='group_write.do?'" />
							</div>
							<div align = "right" class="container right-box" style='margin-right: 0; padding-right: 0;'>
								<form action="group_list.do" method="post" name="wfrm">
								<div align="right" class="container w-50 mt-3" style='margin-right: 0;padding-right: 0; width: 40%!important;'>
									<div style='z-index : 1;' class="input-group" >
									<div style='width: 100px; z-index : 2;'>
										<select class="form-select" style="border-radius: 0px;" name="option">
						  					<option value="subject" selected>제목</option>
						  					<option value="content">내용</option>
						  					<option value="hashtag">해쉬 태그</option>
										</select>
									</div>
								  <input type="text" class="form-control" placeholder="검색어를 입력하세요" aria-label="Recipient's username" aria-describedby="button-addon2" id ="searchStr" name="searchStr" style="border:1px solid #dee2e6; border-radius: 0px; color:black;">
								  <button class="btn btn-outline-secondary" type="submit" style="border:1px solid #dee2e6; border-radius: 0px; color:black;">검색하기</button>
							</div>
						</div>
					</form>
					</div>
					</div>		
					</div>
				<%if(userData==null){}
					else {%>	
				
				<%} %>
				<%=sbHtml %>
				
				<%if(stHtml.length()>1){ %>
				<div class = "section-title"><h2>동아리 승인대기</h2></div>
				<%=stHtml %>
			<%} %>
			<div style= 'margin-top : 50px;'></div>
			
				
				<hr class="container w-80">
				
		</div>
	</section>
	
			
	<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>
</html>