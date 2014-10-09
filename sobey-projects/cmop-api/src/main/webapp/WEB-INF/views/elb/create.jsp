<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>ELB Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>创建ELB页面</h3>
			<blockquote>
				<p>负载均衡器（Elastic Load Balancer）
					可以将来自多个公网地址的访问流量分发到多台云主机上，并支持自动检测并隔离不可用的云主机， 从而提高业务的服务能力和可用性。
					同时，你还可以随时通过添加或删减云主机来调整你的服务能力，而且这些操作不会影响业务的正常访问。</p>
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

		<div class="form-group clone">
			<label for="ecsIds" class="col-sm-2 control-label">ECS</label>
			<div class="col-sm-2">
				<select id="ecsIds" name="ecsIds" class="form-control">
					<c:forEach var="item" items="${ecsList}">
						<option value="${item.id}">${item.description}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-sm-2">
				<select name="protocols" class="form-control">
					<option value="37">HTTP</option>
					<option value="97">HTTPS</option>
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
