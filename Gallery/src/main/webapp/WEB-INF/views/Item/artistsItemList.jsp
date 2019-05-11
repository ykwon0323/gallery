<%@page import="com.hk1.gallery.dto.MemberDto"%>
<%@page import="com.hk1.gallery.dto.ItemDto"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=utf-8"); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
MemberDto loginMember = (MemberDto)session.getAttribute("loginMember"); 

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/litera/bootstrap.min.css" rel="stylesheet" integrity="sha384-D/7uAka7uwterkSxa2LwZR7RJqH2X6jfmhkJ0vFPGUtPyBMF2WMq9S+f9Ik5jJu1" crossorigin="anonymous">
		<script src="artistItemList/js/jquery-1.11.0.min.js" type="text/javascript"></script>
	<script>window.jQuery || document.write('<script src="artistItemList/js/jquery-1.11.0.min.js"><\/script>')</script>
	<script src="artistItemList/src/js/jquery.brickfolio.js"></script>
	<script>
		jQuery(function($){
			$('.brickfolio').brickfolio();
		});
	</script>
	<title>나의작품목록(작품수정)</title>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>jQuery plugin for better grid layouts - especially for portfolio pages</title>
	<link rel="stylesheet" type="text/css" href="artistItemList/css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="artistItemList/css/htmleaf-demo.css">
	<link rel="stylesheet" type="text/css" href="artistItemList/css/demo.css">
	<link href="artistItemList/dist/jquery.brickfolio.min.css" rel="stylesheet">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
	<script
    src="https://code.jquery.com/jquery-3.3.1.js"
    integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
    crossorigin="anonymous"></script>

<script>

jQuery(document).ready(function($) {

$(".scroll").click(function(event){            

event.preventDefault();

$('html,body').animate({scrollTop:$(this.hash).offset().top}, 500);

});

});

</script>





<style type="text/css">
#artistItemList_updateitemForm_div {
	display: none;
}

div#artistItemList h4 {
	width: 25%;
	font-weight: bold;
	padding-bottom: 10px;
	border-bottom: 3px solid rgb(26, 26, 26);
	color: rgb(50, 50, 50);
}

.heading-section span {
	font-size: 16px;
	display: block;
	margin-bottom: 5px;
	text-transform: uppercase;
	color: #e2c0bb;
	letter-spacing: 2px;
	text-align: center !important;
	margin: 0;
	padding: 0;
}

.heading-section h2 {
	font-size: 40px;
	font-weight: 600;
	line-height: 1.2;
	color: inherit;
	margin-top: 8px;
	margin-inline-start: 0px;
	margin-inline-end: 0px;
	text-align: center !important;
}

.heading-section p {
	font-family: -apple-system, BlinkMacSystemFont;
	font-size: 1rem;
	font-weight: 400;
	line-height: 1.5;
	color: #212529;
}
</style>
<script type="text/javascript">

	



 	function updateitemForm(i_no){
 		

 		
 		$.ajax({
 			url : "selectItem.do",
 			trditional:true,
 			data : {"i_no": i_no},
 			datatype : "jason",
 			success : function(data){
 				var i_img = data["item"].i_img;
 				var i_name = data["item"].i_name;
 				var i_explain = data["item"].i_explain;
 				var i_price = data["item"].i_price;
 				var i_no = data["item"].i_no;
 	
 		 		
 		 		document.getElementById("artistItemList_updateitemForm_i_img").src = "itemupload/"+i_img;
 		 		document.getElementById("artistItemList_updateitemForm_input_i_name").value= i_name;
 		 		document.getElementById("artistItemList_updateitemForm_input_i_explain").value= i_explain;
 		 		document.getElementById("artistItemList_updateitemForm_input_i_price").value= i_price;
 		 		document.getElementById("artistItemList_updateitemForm_input_i_no").value= i_no;
 		 		document.getElementById("artistItemList_updateitemForm_i_img_nameTag").value= i_img; 
 				
 				$("#artistItemList_updateitemForm_div").slideToggle();
 				
 			
 			},
 			error : function(request,status,error){
 				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
 			}
 		});
 		
}
 </script>

</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp" />
<hr>
	<script
    src="https://code.jquery.com/jquery-3.3.1.js"
    integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
    crossorigin="anonymous"></script>
    
    <div class="artistItemList_container" style="width: 1200px; margin: 0 auto;">
<div id="artistItemList" style="text-align: center;" >
	<div class="row justify-content-center pb-5" >
          <div class="col heading-section text-center ftco-animate" style="z-index:;">
          	<span class="subheading">My Item</span>
            <h2 class="mb-4">방구석 갤러리</h2>
            <p>Best Item in the room</p>
          </div>
        </div>
        	
        	<h4 >등록된 작품</h4>
		<c:choose>
		<c:when test="${empty myItemList}">
			<h3>등록하신 작품이 없습니다.</h3>
		</c:when>
		<c:otherwise>
			<div class="container">
			<div class="row">
			<c:forEach var ="item" items="${myItemList}">
			
			<div class="col-3">
			<div class="brickfolio" style="height: 150px;">
				<div class="bf-item" style="">
					<img  src="itemupload/${item.i_img}" >
					<h5 style="font-weight: bold;">작품명: ${item.i_name}</h5>
					<h6>작품가격: ${item.i_price}</h6><br/>
					<div style="float: right;"><a href="#target1">
						<input  style="font-size: 13px;" class="btn btn-outline-secondary btn-sm" type="button" value="작품수정" class="scroll" onclick="javascript:updateitemForm(${item.i_no});"></a><br/>
						<input style="font-size: 13px;" class="btn btn-outline-danger btn-sm" type="button" value="작품삭제" onclick="deleteitem.do?i_no=${item.i_no}"><br/>
					</div>
				</div>
			</div>
			</div>
			
			</c:forEach>
			</div>
			</div>
		
		
		</c:otherwise>
		</c:choose>
       </div>
       	
       	<div id="artistItemList_updateitemForm_div" style="width: 1200px;">
       	
       	<hr>
       	<h4 style="width: 25%;
			font-weight: bold;
			padding-bottom: 10px;
			border-bottom: 3px solid rgb(26, 26, 26);
			color: rgb(50, 50, 50);" >작품 수정</h4>
			<form action="artistUpdateitem.do" method="post" >
			<div style="text-align: center;">
			<img alt="작품이미지" id="artistItemList_updateitemForm_i_img" width="50%">
			<br>
			</div>
			
			<div style="margin-left: 170px;">
			<span >작품명 &nbsp&nbsp&nbsp : </span>
			<input style="margin-top: 15px; type="text" name="i_name" id="artistItemList_updateitemForm_input_i_name" >
			<br>
			
			<span>작품가격 : </span>
			<input style="margin-top: 15px; margin-bottom: 15px; type="text" name="i_price" id="artistItemList_updateitemForm_input_i_price">
			<br>
			<span>작품설명 : </span>
			<textarea  rows="20" cols="100" name="i_explain" id="artistItemList_updateitemForm_input_i_explain"></textarea>
			
			<input type="hidden" name="i_img" id="artistItemList_updateitemForm_i_img_nameTag">
			<input type="hidden" name="a_no" value="<%=loginMember.getM_no()%>">
			<input type="hidden" name="i_no" id="artistItemList_updateitemForm_input_i_no" >
			<input type="submit" value="수정완료" class="btn btn-outline-secondary">
			</div>
			</form>
	
	</div>
		
       
        </div>
        <div id="target1"></div>
	
	<jsp:include page="/WEB-INF/views/tail.jsp" />	
	
</body>
</html>