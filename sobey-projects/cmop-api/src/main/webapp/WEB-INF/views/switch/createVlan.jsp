<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Vlan Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="form-group">
			<label for="vlanId" class="col-sm-2 control-label">VlanId</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="vlanId" name="vlanId"
					placeholder="Vlan Id">
			</div>
		</div>

		<div class="form-group">
			<label for="gateway" class="col-sm-2 control-label">Gateway</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="gateway" name="gateway"
					placeholder="网关">
			</div>
		</div>

		<div class="form-group">
			<label for="netMask" class="col-sm-2 control-label">NetMask</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="netMask" name="netMask"
					placeholder="子网掩码">
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
