<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Storage Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>卷创建页面</h3>
		</div>

		<div class="form-group">
			<label for="volumeName" class="col-sm-2 control-label">卷名</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="volumeName"
					name="volumeName" placeholder="卷名" value="sobey">
			</div>
		</div>

		<div class="form-group">
			<label for="volumeSize" class="col-sm-2 control-label">卷大小</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="volumeSize"
					name="volumeSize" placeholder="卷大小(MB)" value="20">
			</div>
		</div>

		<div class="form-group">
			<label for="volumeSize" class="col-sm-2 control-label">挂载目标IP</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="clientIPaddress"
					name="clientIPaddress" placeholder="客户端IP" value="10.10.100.1">
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Create</button>
			</div>
		</div>
	</form>

</body>
</html>
