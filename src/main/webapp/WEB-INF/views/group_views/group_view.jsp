
<%@page import="com.petopia.group.model.GroupHashTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@page import="com.petopia.group.model.GroupTO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
  <%
  	
  	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
  
  	String member_seq = "";
  	String grade = "";
  	Boolean flag = null;
  	if( userData != null){
		member_seq = userData.getM_seq();
		grade = userData.getGrade_seq();
	  	flag = (Boolean)request.getAttribute("flag");
  	}
  	String group_seq = request.getParameter("gr_seq");
  	GroupTO to = new GroupTO();
  	to = (GroupTO)request.getAttribute("to"); 
  	
  	String gr_name = to.getGR_NAME();
  	String gr_admin = to.getGR_ADMIN();
  	String gr_inwon = to.getGR_INWON();
  	String gr_filename = to.getGR_FILENAME();
  	String gr_explan = to.getGR_EXPLAN();
  	String gr_date = to.getGR_DATE();
  	String gr_produce = to.getGR_PRODUCE();
	String m_seq = to.getM_SEQ();
	String m_nickname = to.getM_NICKNAME();
	int jg_grade = to.getJG_GRADE();
	
	ArrayList<GroupHashTO> lists = (ArrayList)request.getAttribute("lists");
	String hashlist ="";
	for(GroupHashTO ghto : lists){
		hashlist += ghto.getHAS_CONTENT();
	}
	
  %>  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title> 

<link href="assets/css/group_view.css" rel="stylesheet">

<link href="assets/css/writeFooter.css" rel="stylesheet">  
</head>
<body>
<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
<section>
<div class="magin"></div>	
<div class="container db-social">
    <div class="jumbotron jumbotron-fluid"></div>
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-xl-11">
                <div class="widget head-profile has-shadow">
                    <div class="widget-body pb-0">
                        <div class="row d-flex align-items-center">
                            <div class="col-xl-4 col-md-4 d-flex justify-content-lg-start justify-content-md-start justify-content-center">
                                <ul>
                                    <li>
                                        <div class="counter"><%=gr_inwon %></div>
                                        <div class="heading">인원 수</div>
                                    </li>
                                    <li>
                                        <div class="counter"><%=gr_date %></div>
                                        <div class="heading">신청 날짜</div>
                                    </li>
                                   <!--  <li>
                                        <div class="counter">216</div>
                                        <div class="heading">Offline</div>
                                    </li> -->
                                </ul>
                            </div>
                            <div class="col-xl-4 col-md-4 d-flex justify-content-center">
                                <div class="image-default">
                                    <img class="rounded-circle" src="../../upload/<%=gr_filename %>	" alt="...">
                                </div>
                                <div class="infos">
                                    <h2><%=gr_name %></h2>
                                    <div class="location"><%=m_nickname %></div>
                                </div>
                            </div>
                           <!-- <div class="col-xl-4 col-md-4 d-flex justify-content-lg-end justify-content-md-end justify-content-center">
                                <div class="follow">
                                    <a class="btn btn-shadow" href="#"><i class="la la-user-plus"></i>Add Friend</a>
                                    <div class="actions dark">
                                        <div class="dropdown">
                                            <button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="dropdown-toggle">
                                                <i class="la la-ellipsis-h"></i>
                                            </button>
                                            <div class="dropdown-menu" x-placement="bottom-start" style="display: none; position: absolute; will-change: transform; top: 0px; left: 0px; transform: translate3d(0px, 35px, 0px);">
                                                <a href="#" class="dropdown-item">
                                                Report
                                                </a>
                                                <a href="#" class="dropdown-item">
                                                Block
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div> -->
                            
                            <div class="col-xl-4 col-md-4 d-flex justify-content-lg-end justify-content-md-end justify-content-center">
                                <div class="follow">
                                    <% 
                                    if(userData!=null){
										if(!grade.equals("1")){//관리자가 아닐때
											if(flag == null){ // 동아리 참여가 없을때
												out.println("<input type='button' class='btn btn-outline-primary'  class='action' value = '가입신청하기'style='cursor: pointer;' onclick='location.href=`join_group_write.do?gr_seq="+group_seq +"`'/>");
											}else if(flag == false){// 동아리 신청한 상태
												if(m_seq.equals(member_seq) && gr_produce.equals("0")){//m_seq는 동아리 장의 seq, member_seq는 로그인 유저의 seq // 동아리장
													out.println("<input type='button' class='btn btn-outline-primary'  class='action' value = '동아치신청 대기중'style='cursor: pointer;' disabled />");
												}else{ //동아리장이 아닐때
													out.println("<input type='button' class='btn btn-outline-primary'  class='action' value = '동아리가입 대기중'style='cursor: pointer;' disabled />");
												}
											}
											else{// 동아리 가입 완료되었을때
												if(m_seq.equals(member_seq) && gr_produce.equals("1")){//m_seq는 동아리 장의 seq, member_seq는 로그인 유저의 seq // 동아리장
													out.println("<input type='button' class='btn btn-outline-primary'  class='action' value = '동아리 게시판가기'style='cursor: pointer;' onclick='location.href=`mygroup_board_list.do?gr_seq="+group_seq +"`'d />");
													out.println("<input type='button' class='btn btn-outline-primary'  class='action' value = '동아리 수정'style='cursor: pointer;' onclick='location.href=`group_modify.do?gr_seq="+group_seq +"`'/>");
												}else{ //동아리장이 아닐때
													out.println("<input type='button' class='btn btn-outline-primary'  class='action' value = '동아리 게시판가기'style='cursor: pointer;' onclick='location.href=`mygroup_board_list.do?gr_seq="+group_seq +"`'d />");
												}
											}
										}else{//관리자일때
											if(gr_produce.equals("1")){
												out.println("<input type='button' class='btn btn-outline-primary'  class='action' value = '승인완료'style='cursor: pointer;' disabled />");
											}else{//관리자가 승인 안했을때
												out.println("<input type='button' class='btn btn-outline-primary'  value='삭제' style='cursor: pointer;' onclick='location.href=`group_produce_delete.do?gr_seq="+group_seq +"`' />");
												out.println("<input type='button' class='btn btn-outline-primary'  value='수락' style='cursor: pointer;' onclick='location.href=`group_produce.do?gr_seq="+group_seq+"`' />");
											}
										}
                                    }else{//로그인 안했을때
										out.println("<input type='button' class='btn btn-outline-primary'  class='action' value = '로그인 후 가입가능'style='cursor: pointer;' disabled />");
                                    }
									%>
                                </div>
                            </div>
                            <div  style="border-top: 1px solid #d7dadb;"></div>
                             <div class="infos" >
                                	<h2><%=gr_explan %></h2>
                             </div>
                             <p class='post-description' style='text-align : right'>  <%=hashlist %> </p>
                            <%-- <div>
									<% 
										if(member_seq == null || !member_seq.equals("1")){
											if(flag == false){
												out.println("<input type='button' class='action' value = '신청'style='cursor: pointer;' onclick='location.href=`join_group_write.do?gr_seq="+group_seq +"`'/>");
											}
											else if(flag == true){
												out.println("<input type='button' class='action' value = '가입완료'style='cursor: pointer;' disabled />");
											}
										}else{
											out.println("<input type='button' value='삭제' style='cursor: pointer;' onclick='location.href=`group_produce_delete.do?gr_seq="+group_seq +"`' />");
											out.println("<input type='button' value='수락' style='cursor: pointer;' onclick='location.href=`group_produce.do?gr_seq="+group_seq+"`' />");
										}
									%>
							</div> --%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</section>
	<jsp:include page="../default_bar/footer.jsp"/>
</body>
</html>