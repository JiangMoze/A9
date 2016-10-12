 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    
<div class="pagination">
	<ul>
		<li>
				<a href="article?curpage=1">首页</a>

		</li>
		<li>
			<c:if test="${pb.curPage<=1}" >
				<a href="">前一页</a>
			</c:if>
			<c:if test="${pb.curPage>1}" >
				<a href="article?curpage=${pb.curPage-1}">前一页</a>
			</c:if>
		</li>
			<c:forEach  var="i" step="1" begin="1" end="${pb.maxPage}">



				<li><a href="article?curpage=${i}">${i}</a></li>
			</c:forEach>
		
		
		
		<li>
			<c:if test="${pb.curPage==pb.maxPage}" >
				下一页
			</c:if>
			<c:if test="${pb.curPage<pb.maxPage}" >
				<a href="article?curpage=${pb.curPage+1}">下一页</a>
			</c:if>

		</li>



		<li><a href="article?curpage=${pb.maxPage}">尾页</a></li>
	</ul>
</div>