<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%--引入jstl--%>
<!DOCTYPE html>
<html>
<head>
<title>result</title>
<script src="/js/jquery-1.2.6.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$.get("/query/${productId}", function(data) {
			$("#result").html(data);
		});
	})
</script>
</head>
<body>
	<h1>没抢到，换个姿势再来一遍!!!</h1>
	<div id="result"></div>
	<br/>
	<h3>
		<a href="/seckill">点击此处跳转到抢购页面</a><br />
		<br />
	</h3>
</body>
</html>
