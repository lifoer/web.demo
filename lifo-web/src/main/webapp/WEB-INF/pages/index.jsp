<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>lifo</title>
	<link rel="stylesheet" type="text/css" href="/staticfile/css/index.css">
</head>
<body>
	<div id="d" >
		<div id="logo">
			<img alt="lifo" src="/staticfile/image/logo.png">
		</div>
		<div id="se">
			<nobr>
				<form action="/index.html" method="post" >
					<input type="text" name="word" value="${word}" id="se_in">
					<input type="submit"  value="lifo" id="se_bu">
				</form>
			</nobr>
			
		</div>
		<div id="d_1">
		<script type="text/javascript">
		<c:if test="${empty pageInfo.list}">
			alert("什么也没搜到！");
		</c:if>
		</script>
		<table>
		<c:forEach items="${pageInfo.list}" var="book">
				<tr>
					<td><img src="${book.img}" width="160px" height="160px"></td>
					<td>
						书名：${book.name} <br> 
						评论：${book.comment} <br>
						价格：¥${book.price}<br>
						简介：${book.des}
					</td>
				</tr>
		</c:forEach>
		</table>
		</div>
	</div>
	<div>
		<ul>
			<li>
				当前页码:${pageInfo.pageNum}&emsp;总页数:${pageInfo.pages }&emsp;总结果:${pageInfo.total}&emsp;  
		        <form method="post" action="/index.html" id="sale">
                	<input type="hidden" name = "word" value="${word}"/>
                	<input type="hidden" name="sale" value="true"/>
                	<a onclick="document.getElementById('sale').submit();" class="page">销量优先&emsp;</a>
	            </form>
	            <a class="page" href="/view.html">搜索可视化&emsp;</a>  	
		        <c:if test="${pageInfo.hasPreviousPage}">  
	                <form method="post" action="/index.html" id="before">
	                	<input type="hidden" name="pageNum" value="${pageInfo.pageNum-1}"/>
	                	<input type="hidden" name="word" value="${word}"/>
	                	<input type="hidden" name="sale" value="${sale}"/>
	                	<a onclick="document.getElementById('before').submit();" class="page">上一页&emsp;</a>
	                </form>  	
		        </c:if>
		         <c:if test="${pageInfo.hasNextPage}">  
	                <form method="post" action="/index.html" id="after">
	                	<input type="hidden" name="pageNum" value="${pageInfo.pageNum+1}"/>
	                	<input type="hidden" name="word" value="${word}"/>
	                	<input type="hidden" name="sale" value="${sale}"/>
	                	<a onclick="document.getElementById('after').submit();" class="page">下一页&emsp;</a>
	                </form>  	
		        </c:if>
	        </li> 
		</ul>
		
	</div>
</body>
</html>   
