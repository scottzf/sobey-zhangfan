<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>VPNUser Change Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="form-group">
			<label for="firewallPolicyId" class="col-sm-2 control-label">FirewallPolicyId</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="firewallPolicyId"
					name="firewallPolicyId" placeholder="Firewall Policy Id"
					value="2000">
			</div>
		</div>

		<div class="form-group">
			<label for="vlanId" class="col-sm-2 control-label">VlanId</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="vlanId" name="vlanId"
					placeholder="Vlan Id" value="80">
			</div>
		</div>

		<div class="form-group">
			<label for="netMask" class="col-sm-2 control-label">NetMask</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="netMask" name="netMask"
					placeholder="子网掩码" value="255.255.255.0">
			</div>
		</div>

		<div class="form-group">
			<label for="vpnUser" class="col-sm-2 control-label">VPN User</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="vpnUser" name="vpnUser"
					placeholder="VPN账户名" value="sobey_vpn">
			</div>
		</div>

		<div class="form-group">
			<label for="vpnPassword" class="col-sm-2 control-label">VPN
				Password</label>
			<div class="col-sm-4">
				<input type="password" class="form-control" id="vpnPassword"
					name="vpnPassword" placeholder="VPN密码" value="123456">
			</div>
		</div>


		<div class="form-group clone">
			<label for="segments" class="col-sm-2 control-label">网段</label>


			<div class="col-sm-2">
				<input type="text" class="form-control" name="segments"
					placeholder="网段">
			</div>

			<div class="col-sm-1">
				<button type="button" class="btn btn-default clone">Add
					Policy</button>
			</div>
			<div class="col-sm-1">
				<button type="button" class="btn btn-warning clone">Delete
					Policy</button>
			</div>
		</div>

		<div class="form-group clone">
			<label for="ipaddress" class="col-sm-2 control-label">IP地址</label>


			<div class="col-sm-2">
				<input type="text" class="form-control" name="ipaddress"
					placeholder="IP地址">
			</div>

			<div class="col-sm-1">
				<button type="button" class="btn btn-default clone">Add
					Policy</button>
			</div>
			<div class="col-sm-1">
				<button type="button" class="btn btn-warning clone">Delete
					Policy</button>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Change</button>
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
