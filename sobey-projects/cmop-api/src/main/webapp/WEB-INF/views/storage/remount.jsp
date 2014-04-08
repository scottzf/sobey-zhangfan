<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Storage Remount Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="form-group">
			<label for="volumeName" class="col-sm-2 control-label">VolumeName</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="volumeName"
					name="volumeName" placeholder="卷名">
			</div>
		</div>

		<div class="form-group clone">

			<label for="before" class="col-sm-2 control-label">Before</label>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="beforeClientIPaddress"
					placeholder="更改前的客户端IP">
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

			<label for="after" class="col-sm-2 control-label">After</label>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="afterClientIPaddress"
					placeholder="更改后的客户端IP">
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
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-primary">Remount</button>
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
