<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Instance Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>创建虚拟机页面</h3>
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
			<label for="vmName" class="col-sm-2 control-label">虚拟机名称</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="vmName" name="vmName"
					placeholder="虚拟机名" value="Sobey">
			</div>
		</div>

		<div class="form-group">
			<label for="operation" class="col-sm-2 control-label">规格</label>
			<div class="col-sm-4">
				<select id="vmTemplateOS" name="vmTemplateOS" class="form-control">
					<option value="CnetOS6.5">CnetOS6.5 4GB CPUx2</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="description" class="col-sm-2 control-label">虚拟机备注</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="description"
					name="description" placeholder="备注" value="CMOP v2.0 Demo">
			</div>
		</div>

		<div class="form-group">
			<label for="ipaddress" class="col-sm-2 control-label">Ipaddress</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="ipaddress"
					name="ipaddress" placeholder="ip地址" value="10.10.100.1">
			</div>
		</div>

		<div class="form-group">
			<label for="gateway" class="col-sm-2 control-label">Vlan</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="vlanId" name="vlanId"
					placeholder="vlanId" value="100">
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
			<label for="subNetMask" class="col-sm-2 control-label">子网掩码</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="subNetMask"
					name="subNetMask" placeholder="子网掩码" value="255.255.255.0">
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
