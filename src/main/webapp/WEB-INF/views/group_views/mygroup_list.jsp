
<%@page import="com.petopia.model.MemberTO"%>
<%@page import="com.petopia.group.model.MyGroupTO"%>
<%@page import="com.petopia.group.model.MyGroupDAO"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
    
 <%
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		String m_grade = "";
		if( userData != null){
			m_grade = userData.getGrade_seq();
		}
   		MyGroupDAO dao =new MyGroupDAO();
		ArrayList<MyGroupTO> lists = (ArrayList)request.getAttribute("lists");
		
		int totalRecode = lists.size();
		System.out.println("asdf     "+totalRecode+"        asdf");
	
		StringBuffer sbHtml = new StringBuffer();
		StringBuffer stHtml = new StringBuffer();
		String hash ="aa";
		int count = 1;
		int be_seq = 0;
		int flag1 = 0;
		
		for(MyGroupTO to : lists){
				
				String gr_seq = to.getGR_SEQ();
				String m_seq = to.getM_SEQ();
				String gr_name = to.getGR_NAME();
				String gr_admin = to.getGR_ADMIN();
				String gr_explan = to.getGR_EXPLAN();
				String gr_filename = to.getGR_FILENAME();
				String gr_admin_seq = to.getGR_ADMIN_SEQ();
				String gr_inwon = to.getGR_INWON();
				String gr_date = to.getGR_DATE();
				String has_content = to.getHAS_CONTENT();
				int gr_hash_cnt = to.getGR_HASH_CNT();
				int jg_grade = to.getJG_GRADE();	
	
				if(be_seq == Integer.parseInt(gr_seq)){
					hash += has_content;
					count++;
					if(count == gr_hash_cnt){
						//gr_hash_cnt의 최대치일 때 append
						++flag1;
						if (flag1 % 3 == 1) {
							stHtml.append("<div class='row'>");
						}
					
						stHtml.append("<div class='col-4'>");
						stHtml.append("<div class='post-slide'>");
						stHtml.append("<div class='post-img'>");
						stHtml.append("<img src='../../upload/" + gr_filename + "' style='height:15rem;'> <a href='mygroup_board_list.do?gr_seq="+ gr_seq +"' class='over-layer'><i");
						stHtml.append("class='fa fa-link'></i></a>");
						stHtml.append("</div>");
						stHtml.append("<div class='post-content'>");
						stHtml.append("<h3 class='post-title'>");
						stHtml.append("<a href='group_view.do?gr_seq="+ gr_seq +"'>" + gr_name + "</a>");
						stHtml.append("</h3>");
						stHtml.append("<p class='post-description' style='height:50px; width : 100%;' >" + gr_explan + "</p>");
						stHtml.append("<p class='post-description' style='text-align : right; font-size : 9px; font-color : #A9A9A9;'>" + hash + "</p>");
						stHtml.append("<span class='post-date'><i class='fa fa-clock-o'></i>"+gr_date+"&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;인원수 " + gr_inwon + "</span>"); 
						stHtml.append("<p>&nbsp;</p>");
						stHtml.append( " 				<div> " );
						stHtml.append( " 			<a href='mygroup_board_list.do?gr_seq="+ gr_seq +"' class='read-more'>동아리 게시판 이동</a>" );
						stHtml.append( " 				</div> " );
						if(jg_grade < 3 || m_grade.equals("1")){
							stHtml.append( " 				<div> " );
							stHtml.append( " 			<a href='mygroup_manage.do?gr_seq="+ gr_seq +"&jg_grade="+jg_grade+"' class='read-more'>동아리 회원관리</a>");
							stHtml.append( " 				</div> " );														
						}
						stHtml.append("</div>");
						stHtml.append("</div>");
						stHtml.append("</div>");

						if (flag1 % 3 == 0) {
							stHtml.append("</div><br>");
						}
					}
				}else {
					if(gr_hash_cnt<=1){
						//1이거나 0일때 여기서 append
						//여기서 append
						//한개니깐 끝 여기 들어온 이상 끝
						++flag1;
						if (flag1 % 3 == 1) {
							stHtml.append("<div class='row'>");
						}
					
						stHtml.append("<div class='col-4'>");
						stHtml.append("<div class='post-slide'>");
						stHtml.append("<div class='post-img'>");
						stHtml.append("<img src='../../upload/" + gr_filename + "' style='height:15rem;'> <a href='mygroup_board_list.do?gr_seq="+ gr_seq +"' class='over-layer'><i");
						stHtml.append("class='fa fa-link'></i></a>");
						stHtml.append("</div>");
						stHtml.append("<div class='post-content'>");
						stHtml.append("<h3 class='post-title'>");
						stHtml.append("<a href='group_view.do?gr_seq="+ gr_seq +"'>" + gr_name + "</a>");
						stHtml.append("</h3>");
						stHtml.append("<p class='post-description' style='height:50px; width : 100%;' >" + gr_explan + "</p>");
						stHtml.append("<p class='post-description' style='text-align : right; font-size : 9px; font-color : #A9A9A9;'>" + has_content + "</p>");
						stHtml.append("<span class='post-date'><i class='fa fa-clock-o'></i>"+gr_date+"&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;인원수 " + gr_inwon + "</span>"); 
						stHtml.append("<p>&nbsp;</p>");
						stHtml.append( " 				<div> " );
						stHtml.append( " 			<a href='mygroup_board_list.do?gr_seq="+ gr_seq +"' class='read-more'>동아리 게시판 이동</a>" );
						stHtml.append( " 				</div> " );
						if(jg_grade < 3 || m_grade.equals("1")){
							stHtml.append( " 				<div> " );
							stHtml.append( " 			<a href='mygroup_manage.do?gr_seq="+ gr_seq +"&jg_grade="+jg_grade+"' class='read-more'>동아리 회원관리</a>");
							stHtml.append( " 				</div> " );														
						}
						stHtml.append("</div>");
						stHtml.append("</div>");
						stHtml.append("</div>");

						if (flag1 % 3 == 0) {
							stHtml.append("</div><br>");
						}
					}else{//gr_hash_cnt가 2개 이상이라는 뜻 그러면 count =1로 만들고 be_seq랑 맞지 않는 다는거는 gr_seq가 달라졌다는 걸 의미
					// 그니깐 새로 count ==1 이 되고 hash에 첫번째 has_content를 넣어준다.
					// be_seq도 gr_seq로 만들어 준다.
						count = 1;
						hash = has_content; 
						be_seq = Integer.parseInt(gr_seq);
					}
				}
	}//for
	
				if(flag1 % 3 != 0){
					stHtml.append("</div><br>");
				}
		
    %>
    <!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<link href="assets/css/writeFooter.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	<section id="portfolio" class="portfolio">
		<div class="container">
	<div class = "section-title"><h2>내 동아리 목록</h2></div>
	<%
			out.println(stHtml);
			
	%>
	</div>
	</section>
	<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>
</html>