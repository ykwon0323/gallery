<%@page import="com.hk1.gallery.dto.GalleryDto"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<%response.setContentType("text/html;charset=utf-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>갤러리 정보 수정</title>
<style>

.main{
position: absolute;
top:150px;
}
</style>
</head>
<body>
<%
 GalleryDto galleryDto =(GalleryDto)request.getAttribute("galleryDto");
%>
<div class="header">
<jsp:include page="/WEB-INF/views/manager/managerHeader.jsp" />
</div>

<div class="main">

   <h1>갤러리 정보 수정</h1>
   <form action="managerupdateGallery.do" method="post" enctype="multipart/form-data">
   <table border="1">
   
   <tr>
      <th>갤러리 번호</th>
      <td><input type="text" name="g_no" value="<%=galleryDto.getG_no()%>" readonly="readonly"/></td>
   </tr>
   
   <tr>
      <th>소유주 번호</th>
      <td><input type="text" name="m_no" value="<%=galleryDto.getM_no()%>" readonly="readonly"/></td>
   </tr>
   
   <tr>
      <th>갤러리 이름</th>
      <td><input type="text" name="g_name" value="<%=galleryDto.getG_name()%>"/></td>
   </tr>
   
   <tr>
      <th>갤러리 주소</th>
      <td><input type="text" name="g_adress" value="<%=galleryDto.getG_adress()%>"/></td>
   </tr>
   
   <tr>
      <th>전화번호</th>
      <td><input type="text" name="g_tel" value="<%=galleryDto.getG_tel()%>"/></td>
   </tr>
   
   <tr>
      <th>내부이미지1</th>
      <td>
<!--       <input type="file" name="file"> -->
<!--       <input type="button" onclick="check()"> -->
<!--       <div id="img1"><input type="file" name="g_img1" id="g_img1"/> -->
<!--       <input type="button" value="삭제" onclick="change()"/> -->
      <input multiple="multiple"  type="file" placeholder="내부이미지1" name="file"/>
      <a href="galleryupload/<%=galleryDto.getG_img1()%>" id="atag1"><c:out value="${galleryDto.g_img1}"/></a>
<!--       </div> -->
      </td>
   </tr>
   
   <tr>
      <th>내부이미지2</th>
      <td><input type="file" name="file">
<!--       <input type="file" name="g_img2" /> -->
      <a href="galleryupload/<%=galleryDto.getG_img2()%>"><c:out value="${galleryDto.g_img2}"/></a>
      </td>
   </tr>
   
   <tr>
      <th>내부이미지3</th>
      <td><input type="file" name="file">
<%--       <input type="file" name="g_img3" value="<%=galleryDto.getG_img3()%>"/> --%>
      <a href="galleryupload/<%=galleryDto.getG_img3()%>"><c:out value="${galleryDto.g_img3}"/></a>
      </td>
   </tr>
   
   <tr>
      <th>내부이미지4</th>
      <td><input type="file" name="file">
<%--       <input type="file" name="g_img4" value="<%=galleryDto.getG_img4()%>"/> --%>
      <a href="galleryupload/<%=galleryDto.getG_img4()%>"><c:out value="${galleryDto.g_img4}"/></a>
      </td>
   </tr>
   
   <tr>
      <th>갤러리 소개</th>
      <td><input type="text" name="g_intro" value="<%=galleryDto.getG_intro()%>"/></td>
   </tr>
   
   <tr>
      <th>승인상태</th>
      <td>
         <select name="g_state">
            <option value="<%=galleryDto.getG_state()%>">현재등급:
            <%if(galleryDto.getG_state()==1){%>
               미승인
            <%}else if(galleryDto.getG_state()==3){%>
               승인
            <%}%>
            </option>
            <option value="1">미승인</option>
            <option value="3">승인</option>
         </select>
      </td>
   </tr>
   
   </table>
   <input type="submit"/>
   </form>
   </div>

<div class="tail">
<jsp:include page="/WEB-INF/views/tail.jsp" />
</div>
   
   <script type="text/javascript">
//    function change(){
//       document.getElementById("g_img1").value = "";
//       var div=document.createElement('div');
//        var addFiletag=
//          '<input type="file" name="g_img1"/>'
//           div.innerHTML = addFiletag;
//           document.getElementById('field').appendChild(div);
//    }
   
   

   </script>
   
   
</body>
</html>