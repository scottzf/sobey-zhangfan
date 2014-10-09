<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Eip Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>创建EIP页面</h3>
			<blockquote>
				<p>公网 IP （Elastic IP） 是在互联网上合法的静态 IP 地址。在SobeyCloud系统中，公网 IP
					地址与您的账户而非特定的资源（云主机或ELB）关联。</p>
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
			<label for="isp" class="col-sm-2 control-label">ISP</label>
			<div class="col-sm-4">
				<select name="isp" class="form-control">
					<option value="65">中国联通</option>
					<option value="29">中国电信</option>
				</select>
			</div>
		</div>

		<div class="form-group clone">
			<label for="domianName" class="col-sm-2 control-label">端口策略</label>

			<div class="col-sm-2">
				<select name="protocols" class="form-control">
					<option value="38">tcp</option>
					<option value="96">udp</option>
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
