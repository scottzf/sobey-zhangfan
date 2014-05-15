<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Esg Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="form-group">
			<label for="aclNumber" class="col-sm-2 control-label">AclNumber</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="aclNumber"
					name="aclNumber" placeholder="aclNumber 3000起" value="3000">
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
			<label for="desc" class="col-sm-2 control-label">Description</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="desc" name="desc"
					placeholder="说明" value="Esg的测试">
			</div>
		</div>

		<div class="form-group clone">

			<label for="Permit" class="col-sm-2 control-label">Permit</label>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="permitsSource"
					placeholder="Source">
			</div>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="permitsSourceNetMask"
					placeholder="SourceNetMask">
			</div>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="permitsDestination"
					placeholder="Destination">
			</div>

			<div class="col-sm-2">
				<input type="text" class="form-control"
					name="permitsDestinationNetMask" placeholder="NetMask">
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

			<label for="Deny" class="col-sm-2 control-label">Deny</label>


			<div class="col-sm-2">
				<input type="text" class="form-control" name="denysSource"
					placeholder="Source">
			</div>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="denysSourceNetMask"
					placeholder="SourceNetMask">
			</div>

			<div class="col-sm-2">
				<input type="text" class="form-control" name="denysDestination"
					placeholder="Destination">
			</div>

			<div class="col-sm-2">
				<input type="text" class="form-control"
					name="denysDestinationNetMask" placeholder="NetMask">
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
