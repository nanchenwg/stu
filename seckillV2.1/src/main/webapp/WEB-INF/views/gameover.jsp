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
	<h1>活动已经结束,请留意下次活动!!!</h1>
	<div id="result"></div>
	<h3>
		<a href="/seckill">应该还有您需要的吧</a><br />
		<br />

	</h3>
</body>
</html>
