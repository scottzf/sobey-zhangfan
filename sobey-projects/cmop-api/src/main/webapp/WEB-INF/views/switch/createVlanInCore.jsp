<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Core Vlan Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>核心交换机创建Vlan页面</h3>
		</div>

		<div class="form-group">
			<label for="vlanId" class="col-sm-2 control-label">Vlan Id</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="vlanId" name="vlanId"
					placeholder="Vlan Id" value="100">
			</div>
		</div>

		<div class="form-group">
			<label for="gateway" class="col-sm-2 control-label">网关</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="gateway" name="gateway"
					placeholder="网关" value="10.10.100.254">
			</div>
		</div>

		<div class="form-group">
			<label for="netMask" class="col-sm-2 control-label">子网掩码</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="netMask" name="netMask"
					placeholder="子网掩码" value="255.255.255.0">
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
