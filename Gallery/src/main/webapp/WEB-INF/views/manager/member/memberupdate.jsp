<%@page import="com.hk1.gallery.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<%response.setContentType("text/html;charset=utf-8"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>

.main{
position: absolute;
top:150px;
}
</style>
</head>
<body>
<div class="header">
<jsp:include page="/WEB-INF/views/manager/managerHeader.jsp" />
</div>


<%
    MemberDto memberDto=(MemberDto)request.getAttribute("memberDto");
%>
<div class="main">
<h1>회원 정보 수정</h1>
   <form action="managerupdateMember.do" method="post">
   <input type="hidden" name="m_no" value="<%=memberDto.getM_no()%>"/>
   
   <table border="1">
   
      <col width="150px;">
      <col width="400px;">
   
   <tr>
   <th>아이디</th>
   <td><input readonly="readonly" type="text" name="m_id" value="<%=memberDto.getM_id()%>"/></td>
   </tr>
   
   <tr>
   <th>이름</th>
   <td><input type="text" name="m_name" value="<%=memberDto.getM_name()%>"/></td>
   </tr>
   
   <tr>
   <th>주소</th>
   <td><input type="text" name="m_address" 
   style="width:300px" value="<%=memberDto.getM_address()%>"/></td>
   </tr>
   
   <tr>
   <th>전화번호</th>
   <td><input type="text" name="m_tel" value="<%=memberDto.getM_tel()%>"/></td>
   </tr>
   
   
   <tr>
   <th>회원 등급</th>
      <td>
<%--       <input type="text" name="m_grade" value="<%=memberDto.getM_grade()%>"/> --%>
      <select name="m_grade">
      <option value="<%=memberDto.getM_grade()%>">
      <%if(memberDto.getM_grade()==0){%>
         관리자(0)
      <%}else if(memberDto.getM_grade()==1){%>
         회원(1)
      <%}else if(memberDto.getM_grade()==2){%>
         작가(2)
      <%}else if(memberDto.getM_grade()==3){%>
         갤러리주(3)
      <%}else if(memberDto.getM_grade()==4){%>
         작가 승인대기(4)
      <%}else if(memberDto.getM_grade()==5){%>
         갤러리주 승인대기(5)
      <%}else if(memberDto.getM_grade()==6){%>
         작가, 갤러리주(6)
      <%}else if(memberDto.getM_grade()==7){%>
         작가(대기),갤러리주(7)
      <%}else if(memberDto.getM_grade()==8){%>
         작가,갤러리주(대기)(8)
      <%}else if(memberDto.getM_grade()==9){%>
         작가(대기),갤러리주(대기)(9)
      <%}%>
      </option>
      <option value="1">회원(1)</option>
      <option value="0">관리자(0)</option>
      <option value="2">작가(2)</option>
      <option value="3">갤러리주(3)</option>
      <option value="4">작가 승인대기(4)</option>
      <option value="5">갤러리주 승인대기(5)</option>
      <option value="6">작가, 갤러리주(6)</option>
      <option value="7">작가(대기),갤러리주(7)</option>
      <option value="8">작가,갤러리주(대기)(8)</option>
      <option value="9">작가(대기),갤러리주(대기)(9)</option>
   </select>
      </td>
   </tr>

   
   </table>
   
   
   <input type="submit">
   
   </form>
   </div>
   <div class="tail">
<jsp:include page="/WEB-INF/views/tail.jsp" />
</div>
</body>
</html>