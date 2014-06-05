<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Storage Change Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>允许卷挂载配置页面</h3>
		</div>

		<div class="form-group">
			<label for="volumeName" class="col-sm-2 control-label">卷名</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="volumeName"
					name="volumeName" placeholder="卷名" value="sobey">
			</div>
		</div>

		<div class="form-group clone">

			<label for="before" class="col-sm-2 control-label">更改前的客户端IP</label>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="beforeClientIPaddress"
					value="10.10.100.1" placeholder="更改前的客户端IP">
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

			<label for="after" class="col-sm-2 control-label">更改后的客户端IP</label>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="afterClientIPaddress"
					value="10.10.100.2" placeholder="更改后的客户端IP">
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
