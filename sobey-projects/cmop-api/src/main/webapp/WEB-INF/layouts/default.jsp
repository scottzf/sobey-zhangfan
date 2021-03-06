<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="cmop v2.0">
<meta name="author" content="liukai">

<!-- title Tag`s Template -->
<title>CMOP v2.0 &mdash; <sitemesh:title /></title>

<!-- Le css styles ==================================================== -->
<link rel="stylesheet" 	href="${ctx}/static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"	href="${ctx}/static/bootstrap/css/sticky-footer-navbar.css">
<link rel="stylesheet"	href="${ctx}/static/jquery-ui/css/jquery-ui-1.10.4.custom.min.css">
<link rel="stylesheet"	href="${ctx}/static/jquery-timepicker-addon/jquery-ui-timepicker-addon.min.css">
<link rel="stylesheet" href="${ctx}/static/jqplot/jquery.jqplot.min.css">

<style>
.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
.ui-timepicker-div dl { text-align: left; }
.ui-timepicker-div dl dt { float: left; clear:left; padding: 0 0 0 5px; }
.ui-timepicker-div dl dd { margin: 0 10px 10px 45%; }
.ui-timepicker-div td { font-size: 90%; }
.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }

.ui-timepicker-rtl{ direction: rtl; }
.ui-timepicker-rtl dl { text-align: right; padding: 0 5px 0 0; }
.ui-timepicker-rtl dl dt{ float: right; clear: right; }
.ui-timepicker-rtl dl dd { margin: 0 45% 10px 10px; }
</style>

<!-- Le javascript -->
<script src="${ctx}/static/jquery/jquery-1.11.0.min.js"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/static/jquery-ui/js/jquery-ui-1.10.4.custom.min.js"></script>
<script src="${ctx}/static/jquery-ui/js/jquery.ui.datepicker-zh-CN.js"></script>

<script src="${ctx}/static/jquery-timepicker-addon/jquery-ui-timepicker-addon.min.js"></script>
<script src="${ctx}/static/jquery-timepicker-addon/jquery-ui-timepicker-zh-CN.js"></script>
<script src="${ctx}/static/jqplot/jquery.jqplot.min.js"></script>
<script src="${ctx}/static/jqplot/excanvas.min.js"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<!-- head Tag`s Template -->
<sitemesh:head />

</head>
<body>

	<%@ include file="/WEB-INF/layouts/navbar.jsp"%>

	<!-- Begin page content -->
	<div class="container">

		<!--[if lt IE 7]>
       		<p class="chromeframe">您的浏览器不被支持！试试其他的：<a href="http://www.google.com/chrome">Google 浏览器</a>、<a href="http://firefox.com.cn/">火狐浏览器</a></p>
     	<![endif]-->

		<c:if test="${not empty message}">
			<div class="alert alert-info fade in"><button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>${message }</div>
		</c:if>

		<sitemesh:body />

	</div>

	<div class="footer">
		<div class="container">
			<p class="text-muted">Copyright &copy; 2014 Sobey</p>
		</div>
	</div>

</body>
</html>
