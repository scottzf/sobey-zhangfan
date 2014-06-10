<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Storage Mount Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>卷挂载页面</h3>
		</div>

		<div class="form-group">
			<label for="volumeName" class="col-sm-2 control-label">卷名</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="volumeName"
					name="volumeName" placeholder="卷名" value="sobey">
			</div>
		</div>

		<div class="form-group">
			<label for="clientIPaddress" class="col-sm-2 control-label">挂载目标IP</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="clientIPaddress"
					name="clientIPaddress" placeholder="客户端IP" value="10.10.100.1">
			</div>
		</div>

		<div class="form-group">
			<label for="netAppIPaddress" class="col-sm-2 control-label">NetApp服务器IPaddress</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="netAppIPaddress"
					name="netAppIPaddress" placeholder="Netapp IP" value="10.10.2.34"
					readonly="readonly">
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Mount</button>
			</div>
		</div>
	</form>

</body>
</html>
