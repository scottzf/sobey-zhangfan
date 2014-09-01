<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Zabbix Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>ECS监控</h3>
		</div>

		<div class="form-group">
			<label for="" ipaddress"" class="col-sm-2 control-label">Ipaddress</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="ipaddress"
					name="ipaddress" placeholder="ECS IPAddress">
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-4">
				<p class="form-control-static">${CPU_load_avg1}</p>
			</div>

			<div class="col-sm-4">
				<p class="form-control-static">${CPU_load_avg5}</p>
			</div>

			<div class="col-sm-4">
				<p class="form-control-static">${CPU_load_avg15}</p>
			</div>

			<div class="col-sm-4">
				<p class="form-control-static">${traffic_in}</p>
			</div>

			<div class="col-sm-4">
				<p class="form-control-static">${traffic_out}</p>
			</div>

			<div class="col-sm-4">
				<p class="form-control-static">${Free_disk_space_on}</p>
			</div>

			<div class="col-sm-4">
				<p class="form-control-static">${Total_disk_space_on}</p>
			</div>

			<div class="col-sm-4">
				<p class="form-control-static">${Available_memory}</p>
			</div>

			<div class="col-sm-4">
				<p class="form-control-static">${check_readk}</p>
			</div>

			<div class="col-sm-4">
				<p class="form-control-static">${check_writek}</p>
			</div>

			<div class="col-sm-4">
				<p class="form-control-static">${check_tps}</p>
			</div>

		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Refresh</button>
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
