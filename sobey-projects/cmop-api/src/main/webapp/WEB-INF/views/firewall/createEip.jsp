<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Eip Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="form-group">
			<label for="internetIP" class="col-sm-2 control-label">InternetIP</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="internetIP" readonly="readonly"
					name="internetIP" placeholder="公网IP" value="119.6.200.219">
			</div>
		</div>

		<div class="form-group">
			<label for="isp" class="col-sm-2 control-label">ISP</label>
			<div class="col-sm-4">
				<select name="isp" class="form-control">
					<option value="1">中国联通</option>
					<option value="0">中国电信</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="internetIP" class="col-sm-2 control-label">PrivateIP</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="privateIP"
					name="privateIP" placeholder="内网IP" value="10.0.8.72">
			</div>
		</div>

		<div class="form-group clone">
			<label for="domianName" class="col-sm-2 control-label">Policy</label>

			<div class="col-sm-2">
				<select name="protocolTexts" class="form-control">
					<option value="udp">udp</option>
					<option value="tcp">tcp</option>
				</select>
			</div>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="sourcePorts"
					placeholder="源端口">
			</div>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="targetPorts"
					placeholder="目标端口">
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
				<button type="submit" class="btn btn-primary">Create</button>
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
