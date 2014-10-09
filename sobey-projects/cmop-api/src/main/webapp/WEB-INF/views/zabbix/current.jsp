<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>ECS Monitor Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>ECS监控</h3>
			<blockquote>
				<p>云监控针对云主机进行监控的服务使他能获得云主机最新的状态信息。</p>
			</blockquote>
		</div>

		<div class="form-group">
			<label for="tenantsId" class="col-sm-2 control-label">ECS列表</label>
			<div class="col-sm-4">
				<select id="ecsId" name="ecsId" class="form-control">
					<c:forEach var="item" items="${ecsList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
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
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</div>
	</form>
</body>
</html>
