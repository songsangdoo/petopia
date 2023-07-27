<%@page import="com.petopia.model.MemberTO"%>
<%@page import="com.petopia.group.model.MyGroupTO"%>
<%@page import="com.petopia.group.model.MyGroupDAO"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
 <%
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
		String member_seq = userData.getM_seq();

		String gr_seq = request.getParameter("gr_seq");
		int user_grade = Integer.parseInt(request.getParameter("jg_grade"));
		
   		MyGroupDAO dao =new MyGroupDAO();
		ArrayList<MyGroupTO> lists = (ArrayList)request.getAttribute("lists");
		
		int totalRecode = lists.size();
		StringBuffer stHtml = new StringBuffer();
		
		int currentPage = 1;
		int itemsPerPage = 10;
		int count=1;
		
		int startIndex = (currentPage - 1) * itemsPerPage;
		int endIndex = Math.min(startIndex + itemsPerPage, lists.size());
		for (int i = startIndex; i < endIndex; i++) {
			MyGroupTO to = lists.get(i);
			String m_nickname = to.getM_NICKNAME();
			String jg_explain = to.getJG_EXPLAIN();
			String m_seq = to.getM_SEQ();
			Boolean jg_join = to.isJG_JOIN();
			int jg_grade = to.getJG_GRADE();
			
			stHtml.append( "  <tr> " );
			stHtml.append( " <td><span>"+ count++ +" </span></td> " );
			stHtml.append( "  <td> " );
			stHtml.append( "    <img src='https://bootdey.com/img/Content/avatar/avatar1.png' alt=''> " );
			stHtml.append( "   <a href='#' class='user-link'>"+m_nickname+ " </a> " );/* 
			stHtml.append( "  <span class='user-subhead'>"+to.getGrade_seq() +"</span> " ); */
			stHtml.append( " </td> " );
			stHtml.append( " <td>"+ jg_explain +"</td> " );
			
			if(jg_join == true){
				stHtml.append( " <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; o </td> " );
				if(user_grade == 1){
					if(jg_grade == 2){
						stHtml.append( " <td>동아리 차장</td> " );
						stHtml.append( " <td style='width: 20%;'> " );
						stHtml.append( " 				<a> " );
						stHtml.append( " 					<input type='button' value='강등' class='btn btn-outline-primary h5' style='cursor: pointer;' onclick='location.href=`mygroup_manage_dem.do?gr_seq="+gr_seq+"&m_seq="+m_seq+"&user_grade="+user_grade+"&jg_grade="+jg_grade+"`' /> " );
						stHtml.append( " 				</a> " );
						stHtml.append( "      </td> " );
					}else if(jg_grade == 3){
						stHtml.append( " <td>동아리 부원</td> " );
						stHtml.append( " <td style='width: 20%;'> " );
						stHtml.append( " 				<a> " );
						stHtml.append( " 					<input type='button' value='승급' class='btn btn-outline-primary h5' style='cursor: pointer;' onclick='location.href=`mygroup_manage_adc.do?gr_seq="+gr_seq+"&m_seq="+m_seq+"&user_grade="+user_grade+"&jg_grade="+jg_grade+"`' /> " );
						stHtml.append( " 				</a> " );
						stHtml.append( "      </td> " );
					}else{
						stHtml.append( " <td>동아리 장</td> " );
						stHtml.append( " <td style='width: 20%;'> " );
						stHtml.append( " </td> " );
					}
				}else{
					if(jg_grade == 3){
						stHtml.append( " <td>동아리 부원</td> " );
						stHtml.append( " <td style='width: 20%;'> " );
						stHtml.append( " 				<a> " );
						stHtml.append( " 					<input type='button' value='승급' class='btn btn-outline-primary h5' style='cursor: pointer;' onclick='location.href=`mygroup_manage_adc.do?gr_seq="+gr_seq+"&m_seq="+m_seq+"&user_grade="+user_grade+"&jg_grade="+jg_grade+"`' /> " );
						stHtml.append( " 				</a> " );
						stHtml.append( "      </td> " );
					}else if(jg_grade ==2){
						stHtml.append( " <td>동아리 차장</td> " );
						stHtml.append( " <td style='width: 20%;'> " );
						stHtml.append( " </td> " );

					}else{

						stHtml.append( " <td>동아리 부장</td> " );
						stHtml.append( " <td style='width: 20%;'> " );
						stHtml.append( " </td> " );
					}
				}
			}else{
				stHtml.append( " <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; x</td> " );
				stHtml.append( " <td>승인 대기 중</td> " );
				stHtml.append( " <td style='width: 20%;'> " );
				stHtml.append( " 				<a> " );
				stHtml.append( " 					<input type='button' value='승인' class='btn btn-outline-primary h5' style='cursor: pointer;' onclick='location.href=`mygroup_manage_join.do?gr_seq="+gr_seq+"&m_seq="+m_seq+"&user_grade="+user_grade+"&jg_grade="+jg_grade+"`' /> " );
				stHtml.append( " 				</a> " );
				stHtml.append( " 				<a> " );
				stHtml.append( " 					<input type='button' value='거절' class='btn btn-outline-primary h5' style='cursor: pointer;' onclick='location.href=`mygroup_manage_delete.do?gr_seq="+gr_seq+"&m_seq="+m_seq+"&user_grade="+user_grade+"&jg_grade="+jg_grade+"`' /> " );
				stHtml.append( " 				</a> " );
				stHtml.append( "      </td> " );
			}
			stHtml.append( "   </tr> " );
			
			
			}
					         
					    
		
    %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
    <meta name="author" content="AdminKit">
    <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="shortcut icon" href="assets/dashimg/icons/icon-48x48.png" />

    <link rel="canonical" href="https://demo-basic.adminkit.io/" />

    <title>s</title>

    <link href="assets/css/dashboardcss/app.css" rel="stylesheet">
    <link href="assets/css/dashboardcss/additional.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
    
<link href="assets/css/writeFooter.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>

<section>
	<main class="content">
				<div class="container-fluid p-0">
					<div class="section-title">
					<h2>동아리 회원 관리</h2>
					</div>
					<div class="row" style="padding-right : 175px; padding-left : 175px;">
						<div class="col-lg-12">
							<div class="main-box clearfix">
							    <div class="table-responsive">
							        <table class="table user-list">
							            <thead>
							                <tr>
							                    <th><span>번호</span></th>
							                    <th><span>닉네임</span></th>
							                    <th><span>지원서</span></th>
							                    <th><span>가입 현황</span></th>
							                    <th><span>직급</span></th>

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
</section>
<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>
</html>