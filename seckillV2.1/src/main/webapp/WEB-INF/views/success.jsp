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
	<h1>恭喜您抢购成功!!!</h1>
	<h3>
		<div id="result"></div>
		<br/>
		<a href="/seckill">应该还有您需要的吧</a><br />
		<br /> <a href="javascript:void(0)">点击此处跳转到支付页面</a>
	</h3>
</body>
</html>
