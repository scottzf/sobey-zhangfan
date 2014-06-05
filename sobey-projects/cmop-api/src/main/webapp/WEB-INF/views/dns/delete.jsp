<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Dns Delete Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>域名解绑页面</h3>
		</div>

		<div class="form-group">
			<label for="domianName" class="col-sm-2 control-label">域名</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="domianName"
					name="domianName" placeholder="域名" value="cmoptest.sobeycache.com">
			</div>
		</div>

		<div class="form-group clone">
			<label for="domianName" class="col-sm-2 control-label">公网IP</label>
			<div class="col-sm-2">
				<input type="text" class="form-control" name="publicIPs"
					placeholder="公网IP地址" value="119.6.200.204">
			</div>

			<div class="col-sm-2">
				<select name="protocols" class="form-control">
					<option value="HTTP">HTTP</option>
					<option value="SSL">HTTPS</option>
				</select>
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
