<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Storage Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="form-group">
			<label for="volumeName" class="col-sm-2 control-label">VolumeName</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="volumeName"
					name="volumeName" placeholder="卷名" value="sobey">
			</div>
		</div>

		<div class="form-group">
			<label for="volumeSize" class="col-sm-2 control-label">VolumeSize</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="volumeSize"
					name="volumeSize" placeholder="卷大小(MB)" value="20">
			</div>
		</div>

		<div class="form-group">
			<label for="volumeSize" class="col-sm-2 control-label">ClientIPaddress</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="clientIPaddress"
					name="clientIPaddress" placeholder="客户端IP" value="10.10.2.81">
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-primary">Create</button>
			</div>
		</div>
	</form>

</body>
</html>
