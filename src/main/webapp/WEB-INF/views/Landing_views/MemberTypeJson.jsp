<%@page import="java.io.Console"%>
<%@page import="com.petopia.admin.model.AdminTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@ page contentType="application/json; charset=UTF-8" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>

<%
  
  AdminTO AdminTO = (AdminTO)request.getAttribute("result");
  // ObjectMapper를 사용하여 MemberTO 객체를 JSON 형식으로 변환
  
  ObjectMapper mapper = new ObjectMapper();
  String json = mapper.writeValueAsString(AdminTO);
  System.out.println(json);
%>

<%= json %>