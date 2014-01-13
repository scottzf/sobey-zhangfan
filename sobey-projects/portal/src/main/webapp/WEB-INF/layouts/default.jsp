<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="cmop v2.0">
<meta name="author" content="liukai">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- title Tag`s Template -->
<title>cmop v2.0  &mdash; <sitemesh:title /></title>

<!-- Le css styles ==================================================== -->
<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css">

<style type="text/css">
/* Sticky footer styles
-------------------------------------------------- */
html,body {
	height: 100%;
	/* The html and body elements cannot have any padding or margin. */
}

/* Wrapper for page content to push down footer */
#wrap {
	min-height: 100%;
	height: auto;
	/* Negative indent footer by its height */
	margin: 0 auto -60px;
	/* Pad bottom by footer height */
	padding: 0 0 60px;
}

/* Set the fixed height of the footer here */
#footer {
	height: 60px;
	background-color: #f5f5f5;
}

/* Custom page CSS
-------------------------------------------------- */
/* Not required for template or sticky footer method. */
#wrap>.container {
	padding: 60px 15px 0;
}

.container .text-muted {
	margin: 20px 0;
}

#footer>.container {
	padding-left: 15px;
	padding-right: 15px;
}

code {
	font-size: 80%;
}
</style>


<!-- Le javascript -->
<script src="${ctx}/static/jquery/jquery-1.10.2.min.js"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/static/jquery-validation/jquery.validate.js"></script>
<script src="${ctx}/static/jquery-validation/messages_zh.js"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
	<script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->

<!-- head Tag`s Template -->
<sitemesh:head />

</head>
<body>

	<div id="wrap">

		<%@ include file="/WEB-INF/layouts/navbar.jsp"%>

		<div class="container">

			<!--[if lt IE 7]>
        		<p class="chromeframe">您的浏览器不被支持！试试其他的：<a href="http://www.google.com/chrome">Google 浏览器</a>、<a href="http://firefox.com.cn/">火狐浏览器</a>、<a href="http://www.apple.com.cn/safari/">Safari</a></p>
	     	<![endif]-->

			<c:if test="${not empty message}"><div class="alert alert-info fade in"><button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>${message }</div></c:if>

			<sitemesh:body />

		</div>

	</div>

	<footer id="footer">
		<div class="container">
			<p class="text-muted">Copyright &copy; 2014 Sobey</p>
		</div>
	</footer>

</body>
</html>