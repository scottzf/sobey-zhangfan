<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Instance Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="form-group">
			<label for="datacenter" class="col-sm-2 control-label">Date
				Center</label>
			<div class="col-sm-4">
				<select id="datacenter" name="datacenter" class="form-control">
					<option value="xa">西安</option>
					<option value="cd">成都</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="vmName" class="col-sm-2 control-label">VMName</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="vmName" name="vmName"
					placeholder="虚拟机名" value="Sobey">
			</div>
		</div>

		<div class="form-group">
			<label for="operation" class="col-sm-2 control-label">VMTemplateOS</label>
			<div class="col-sm-4">
				<select id="vmTemplateOS" name="vmTemplateOS" class="form-control">
					<option value="CnetOS6.5">CnetOS6.5</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="description" class="col-sm-2 control-label">Description</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="description"
					name="description" placeholder="备注" value="CMOP v2.0 Demo">
			</div>
		</div>

		<div class="form-group">
			<label for="ipaddress" class="col-sm-2 control-label">Ipaddress</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="ipaddress"
					 name="ipaddress" placeholder="ip地址"
					value="10.10.2.80">
			</div>
		</div>

		<div class="form-group">
			<label for="gateway" class="col-sm-2 control-label">Gateway</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="gateway" name="gateway"
					 placeholder="网关" value="10.10.2.1">
			</div>
		</div>

		<div class="form-group">
			<label for="subNetMask" class="col-sm-2 control-label">SubNetMask</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="subNetMask"
					 name="subNetMask" placeholder="子网掩码"
					value="255.255.255.0">
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
