<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>PortGroup Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>创建分布式端口组</h3>
		</div>

		<div class="form-group">
			<label for="datacenter" class="col-sm-2 control-label">数据中心</label>
			<div class="col-sm-4">
				<select id="datacenter" name="datacenter" class="form-control">
					<option value="xa">西安</option>
					<option value="cd">成都</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="vmName" class="col-sm-2 control-label">Vlan Id</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="vlanId" name="vlanId"
					placeholder="vlanId" value="100">
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
