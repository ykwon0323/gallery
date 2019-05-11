<%@page import="com.hk1.gallery.dto.CallendarDto"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.hk1.utils.Util" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<%
	response.setContentType("text/html;charset=utf-8");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<title>달력보기</title>

</head>


<%
	//달력의 날짜를 바꾸기 위해서 전달된 year와 month 값을 받는다
	String pYear= request.getParameter("year");
	String pMonth= request.getParameter("month");	

	Calendar cal = Calendar.getInstance(); //calender 객체는 new를 사용하지 않는다
	int year =cal.get(Calendar.YEAR);  // 현재 년도를 구함
	int month =cal.get(Calendar.MONTH)+1;  //현재 월을 구함 (0~11월)
	
	if(pYear!=null){
		year = Integer.parseInt(pYear);
	}
	
	
	if(pMonth!=null){
		month = Integer.parseInt(pMonth);
		
	}
	
	//달중에 12월을 넘어 갔을 경우 month는 1월로, 년도는 다음 년도로 바꿔준다
	if(month>12){
		
		month=1;
		year++;
	}
	
	//달중에 1월을 뒤로 넘어갔을경우 month는 12월로, 년도는 전년도로 값을 바꿔준다
	if(month<1){
		
		month=12;
		year--;
	}
	
	
	//현재 월의 1일에 대한 요일 구하기 -- 현재 날짜에 대한 정보를 담고 있는 Calender 객체 생성
	//					   -- 3월  1일로 셋팅을 해서 요일을 구함
	
	cal.set(year, month-1, 1); //2019년 3월 1일로 바꿔준다 sys기준 (0~11월)으로 맞춰준다
	
	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);  // 요일을 구함 1일에 대한 요일을 구함 1(일) ~ 7(월)
	int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //월의 마지막날 구하기

	//한달에 대한 일정목록 가져오기
	
	
/*	String id ="kbj";
	String yyyyMM= year+Util.isTwo(String.valueOf(month)); // "201903"
	
 	CalDao dao= new CalDao();
	 List<CalDto> list = dao.getCalViewList(id, yyyyMM);  */
%>
<body>


<h1>달력 일정 보기</h1>


<!-- style="border: 1px splid gray; border-collapse: collapse;" -->
<table border="1">


<!--g_no 로그인 세션값 넣어야 합니다.-->


<caption>
<a href="selectGallery.do?year=<%=year-1%>&month=<%=month%>&g_no=2">◁</a>
<a href="selectGallery.do?year=<%=year%>&month=<%=month-1%>&g_no=2">◀</a>
<span class="y"><%= year %></span>년 <span class="m"><%= month%></span>월
<a href="selectGallery.do?year=<%=year%>&month=<%=month+1%>&g_no=2">▷</a>
<a href="selectGallery.do?year=<%=year+1%>&month=<%=month%>&g_no=2">▶</a>
</caption>
<col width="80px"><col width="80px"><col width="80px"><col width="80px">
<col width="80px"><col width="80px"><col width="80px">



<tr>
<th>일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th>토</th>
</tr>
<tr>

<%	//공백수 풀력하기
	for(int i=0; i<(dayOfWeek-1) ; i++){
		out.print("<td>&nbsp;</td>");
	}

	//일수 출력하기
	
	
	for(int i=1; i<=lastDay ; i++){

		%>
		
		<td style="padding: 0; position:relative; height: 80px;  vertical-align: top;">

			 <a id="cdate_a" class="countview d<%=i%>" style="text-decoration: none;color:<%= Util.fontColor(dayOfWeek,i)%>;" 
			href="#" onclick="javascript:insertformcalendar(<%=year%>,<%=month%>,<%=i%>,this);" >
			
			 <%=i%> </a> 
			
			
			
			<% List<CallendarDto> clist = (List<CallendarDto>)request.getAttribute("clist"); %>
		 	
		 	<%=Util.getCalView(clist, i)%> 
		 				

		 			
			
		 		
		 
		 	 

			
<%-- 			<a href="insertform.do?&year=<%=year%>&date=<%= i %>&lastDay=<%= lastDay %>&month=<%= month %> ">
				<img alt="일정추가" src="img/1.png">
			</a> --%>
		 	
		 	
		</td>
	<%	
	
	// dayOfWeek-1 은 공백수  : (공백수+날짜)%7==토요일
	if((dayOfWeek-1+i)%7==0){ 
	
	%></tr><tr><% 
	
		//out.print("</tr><tr>"); //<tr><td>1</td><td></td></tr><tr>
	}

	}
	
	//달력의 나머지 공백수 출력하기 (rhdqor ㄲㅇ)
	//7-((dayOfWeek-1+lastDay)%7)
	
	for(int i = 0; i<(7-((dayOfWeek-1+lastDay)%7))%7;i++){
		
	
		out.print("<td>&nbsp;</td>");	

	}

%>


</tr>

</table>






</body>

<script type="text/javascript">

	function insertformcalendar(y,m,i,eleA) {
		
		 var eleP=eleA.nextElementSibling;//p태그
		//alert(eleP);
		if(eleP==null){
			window.open("insertformcalendar.do?g_no=2&c_start_y="+y+"&c_start_m="+m+"&c_start_d="+i, 'window팝업', 'width=300, height=300, menubar=no, status=no, toolbar=no');
			
		}else{
			alert("일정을 추가할수없어요");
		} 
	
	}


</script>


</html>