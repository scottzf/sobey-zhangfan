<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Elb Delete Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="form-group">
			<label for="domianName" class="col-sm-2 control-label">VIP</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="vip"
					name="vip" placeholder="虚拟IP">
			</div>
		</div>

		<div class="form-group clone">
			<label for="domianName" class="col-sm-2 control-label">Policy</label>
			<div class="col-sm-2">
				<input type="text" class="form-control" name="publicIPs"
					placeholder="IP地址">
			</div>

			<div class="col-sm-2">
				<select name="protocols" class="form-control">
					<option value="HTTP">HTTP</option>
					<option value="SSL">HTTPS</option>
				</select>
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
