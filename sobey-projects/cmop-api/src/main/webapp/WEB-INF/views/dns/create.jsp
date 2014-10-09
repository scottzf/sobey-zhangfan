<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>DNS Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>DNS绑定页面</h3>
			<blockquote>
				<p>在SobeyCloud系统中，您可以将申请到的域名分配到任意EIP ，并随时可以解绑、再分配到其他EIP。</p>
			</blockquote>
		</div>

		<div class="form-group">
			<label for="tenantsId" class="col-sm-2 control-label">租户</label>
			<div class="col-sm-4">
				<select id="tenantsId" name="tenantsId" class="form-control">
					<c:forEach var="item" items="${tenantsList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="domianName" class="col-sm-2 control-label">域名</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="domianName"
					name="domianName" placeholder="域名" value="cmoptest.sobeycache.com">
			</div>
		</div>

		<div class="form-group clone">
			<label for="eipIds" class="col-sm-2 control-label">EIP</label>
			<div class="col-sm-2">
				<select id="eipIds" name="eipIds" class="form-control">
					<c:forEach var="item" items="${eipList}">
						<option value="${item.id}">${item.description}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-sm-2">
				<select name="protocols" class="form-control">
					<option value="39">HTTP</option>
					<option value="59">HTTPS</option>
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
				<button type="submit" class="btn btn-primary">Submit</button>
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
