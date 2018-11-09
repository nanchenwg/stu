<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--引入jstl--%>
<!DOCTYPE html>
<%@include file="common/tag.jsp"%>
<html>
<head>
    <title>秒杀列表页</title>
   <%@include file="common/head.jsp"%>
   <script src="/js/jquery-1.2.6.min.js"></script>
   <script type="text/javascript">
   		$(document).ready(function(){
   			$("table").find("tr").each(function(){
   				var tdArr = $(this).children();
   			 	if(tdArr.eq(1).text()== "0"){
   			 		tdArr.eq(5).find('a').attr("href","javascript:void(0)")
   			 		tdArr.eq(5).find('a').css({"background": "gray"});
   			 	}
   			})
   			
   		})
   </script>
</head>
<body>
     <%--页面显示部分--%>
     <div class="container">
         <div class="panel panel-default">
             <div class="panel-heading text-center">
                 <h2>秒杀列表</h2>
             </div>
             <div class="panel-body">
                 <table class="table" table-hover>
                     <thead>
                       <tr>
                           <th>名称</th>
                           <th>库存</th>
                           <th>开始时间</th>
                           <th>结束时间</th>
                           <th>创建时间</th>
                           <th>抢购</th>
                       </tr>
                     </thead>
                     <tbody>
                       <c:forEach var="sk" items="${list}">
                           <tr>
                               <td>${sk.name}</td>
                               <td class="stockNum">${sk.number}</td>
                               <td>
                                   <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                               </td>
                               <td>
                                   <fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                               </td>
                               <td>
                                   <fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                               </td>
                               <td>
                                   <a class="btn btn-info" href="/seckill/${sk.seckillId}">抢购</a>
                               </td>
                           </tr>
                       </c:forEach>
                     </tbody>
                 </table>
             </div>
         </div>
     </div>

</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
