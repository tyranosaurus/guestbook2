<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <!-- 이걸 선언해줘야 JSTL 중 core를 사용가능 prefix는 name에 해당 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> <!-- JSTL 중 fomat 를 사용가능 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  <!-- JSTL 중 function 를 사용가능 -->

<%@page import="java.util.List"%>
<%@page import="com.estsoft.db.MySQLWebDBConnection"%>
<%@page import="com.estsoft.guestbook.dao.GuestBookDao"%>
<%@page import="com.estsoft.guestbook.vo.GuestBookVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String newLine = "\r\n"; // 잘못된 방법, 지워야함
	pageContext.setAttribute("newLine", "\r\n");
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록</title> <!-- 웹브라우저 탭에 출력되는 부분  -->
</head> 
<body>
	<form action="/guestbook2/gb?a=add" method="post">
	<table border=1 width=500>
		<tr>
			<td>이름</td>   <td><input type="text" name="name"></td>
			<td>비밀번호</td>   <td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td colspan=4><textarea name="content" cols=60 rows=5></textarea></td>
		</tr>
		<tr>
			<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
		</tr>
	</table>
	</form>
		<%
		//int count = list.size();
		//for(GuestBookVo vo : list)
		//{
		
		%>
	<c:set var="count" value="${fn:length(list) }"/>
	<c:forEach items="${list }" var="vo" varStatus="status">
	
	<br>
	<table border=1 width=500>
		<tr>
			<td>[${status.count } : ${status.index } : ${count-status.index }]</td>
			<td>${vo.name }</td>
			<td>${vo.regDate }</td>
			<td><a href="/guestbook2/gb?a=deleteform&no=${vo.no }">삭제</a></td>
		</tr>
		<tr>
			<td colspan=4>
			${fn:replace(vo.message, newLine, "<br/>") }
			</td>
		</tr>
	</table>
	<br>
	</c:forEach>
		<%
		//count--;
		//};
		%>



</body>
</html>