<%@page import="com.petopia.model.MemberTO"%>
<%@page import="com.petopia.group.model.MyGroupTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.petopia.group.model.MyGroupDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	
    request.setCharacterEncoding("utf-8");
	
	MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	
	
	String member_seq = "";
	
	if( userData != null){
		member_seq = userData.getM_seq();
	}
	
	 MyGroupDAO dao =new MyGroupDAO();
	ArrayList<MyGroupTO> lists = (ArrayList)session.getAttribute("mygroupList");
    int count=0;
	StringBuffer stHtml = new StringBuffer();
	for(MyGroupTO to : lists){
		
		String gr_seq = to.getGR_SEQ();
		String m_seq = to.getM_SEQ();
		String gr_name = to.getGR_NAME();
		
		stHtml.append("<li><a href='mygroup_board_list.do?gr_seq="+gr_seq+"'><strong>");
		stHtml.append(gr_name);
		stHtml.append("</strong></a></li>");
		count++;
		if(count==3){
			break;
		}
		
	}//for 
	
    %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.floating {
	background-color:#f9f9f9;
	border:2px solid #6297e3;
	position: fixed; 
	right: 37%; 
	top: 150px; 
	margin-right: -570px;
	text-align:center;
	width:130px;
	border-radius: 8px;
	-webkit-border-radius: 8px;
	z-index : 4;
}

ol.numbered {
  border-left: 3px solid  #6297e3;
  counter-reset: numbered-list;
  margin-left: 20px;
  position: relative;
  padding: 0;
  display: block;
}

ol.numbered li {
  font-size: 16px;
  line-height: 1.2;
  margin-bottom: 30px;
  padding-left: 13px;
  position: relative;
  width: 100%;
  height: 20px;
  margin-top : 50px;
}
ol.numbered li:last-child {
  border-left: 3px solid white;
  margin-left: -3px;
  margin-bottom: 0px;
}
ol.numbered li:before {
  border: 3px solid white;
  border-radius: 50%;
  color: white;
  content: "";
  display: block;
  font-weight: bold;
  width: 30px;
  height: 30px;
  margin-top: -0.5em;
  line-height: 30px;
  position: absolute;
  left: -16.48px;
  text-align: center;
  background-color: white;
  border-color: #6297e3;
  border-width: 5px;
  border-style: solid;
  border-radius: 50%;
}
strong{
 color: #444444;
 margin-left: 4px;
}
h6{
	margin: -30px;
	color : #444444;
	line-height : 85%;
}
h4{
	font-size : 20px;
	margin-top : -40px;
	margin-bottom: -30px;
}

.title {
  text-align: center;
}

.title h4 {
  font-weight: bold;
  text-transform: uppercase;
  color: #37517e;
}
		
</style>
</head>
<body>
		<div class ="floating">
		<h4 class="title"><strong>MY 동아리</strong></h4>
		<ol class="numbered">
		<%=stHtml %>
		</ol>
		<h6>.<br>.<br>.</h6><br>
		<input type="button" class="btn btn-outline-primary" 
			style='margin-bottom: 10px; color: #444444; margin-top: 2px;border:none; 
    			--bs-btn-hover-bg: whtie;
    			--bs-btn-hover-border-color: white;			
			    --bs-btn-active-bg: #fff;
			    --bs-btn-active-border-color: #fff;'
    		value="더보기" onclick="location.href='mygroup_list.do?'" />
		</div>
</body>
</html>