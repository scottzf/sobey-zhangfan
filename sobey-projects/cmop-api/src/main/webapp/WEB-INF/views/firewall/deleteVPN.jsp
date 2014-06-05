<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>VPNUser Delete Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>回收VPN账号页面</h3>
		</div>

		<div class="form-group">
			<label for="firewallPolicyId" class="col-sm-2 control-label">Firewall
				Policy Id</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="firewallPolicyId"
					name="firewallPolicyId" placeholder="Firewall Policy Id"
					value="2000">
			</div>
		</div>

		<div class="form-group">
			<label for="vlanId" class="col-sm-2 control-label">Vlan Id</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="vlanId" name="vlanId"
					placeholder="Vlan Id" value="100">
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
			<label for="vpnUser" class="col-sm-2 control-label">VPN账号</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="vpnUser" name="vpnUser"
					placeholder="VPN账户名" value="sobey_vpn">
			</div>
		</div>

		<div class="form-group">
			<label for="vpnPassword" class="col-sm-2 control-label">VPN密码</label>

			<div class="col-sm-4">
				<input type="password" class="form-control" id="vpnPassword"
					name="vpnPassword" placeholder="VPN密码" value="123456">
			</div>
		</div>

		<div class="form-group clone">
			<label for="segments" class="col-sm-2 control-label">网段</label>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="segments"
					placeholder="网段" value="10.10.100.0">
			</div>

			<div class="col-sm-1">
				<button type="button" class="btn btn-default clone">Add</button>
			</div>
			<div class="col-sm-1">
				<button type="button" class="btn btn-warning clone">Delete
				</button>
			</div>
		</div>

		<div class="form-group clone">
			<label for="ipaddress" class="col-sm-2 control-label">IP地址</label>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="ipaddress"
					value="10.10.100.1" placeholder="IP地址">
			</div>

			<div class="col-sm-1">
				<button type="button" class="btn btn-default clone">Add</button>
			</div>
			<div class="col-sm-1">
				<button type="button" class="btn btn-warning clone">Delete
				</button>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Delete</button>
			</div>
		</div>
	</form>

	<script>
		$(document).ready(function() {

			$(document).on("click", "button.clone", function() {
				var $this = $(this);
				var $div = $this.closest('div.clone');
				if ($this.hasClass("btn-warning")) {
					$div.remove();
				} else {
					var $clone = $div.clone();
					$clone.find('input[type=text]').val('');
					$div.after($clone);
				}
			});

		});
	</script>

</body>
</html>
