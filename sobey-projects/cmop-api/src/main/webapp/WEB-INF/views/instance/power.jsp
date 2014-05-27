<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Instance Power Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>虚拟机电源管理页面</h3>
		</div>

		<div class="form-group">
			<label for="datacenter" class="col-sm-2 control-label">Date
				Center</label>
			<div class="col-sm-4">
				<select id="datacenter" name="datacenter" class="form-control">
					<option value="xa">西安</option>
					<option value="cd">成都</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="vmName" class="col-sm-2 control-label">VMName</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="vmName" name="vmName"
					placeholder="虚拟机名称" value="Sobey">
			</div>
		</div>

		<div class="form-group">
			<label for="operation" class="col-sm-2 control-label">Operation</label>
			<div class="col-sm-4">
				<select id="operation" name="operation" class="form-control">
					<option value="poweron">开机</option>
					<option value="poweroff">关机</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Power</button>
			</div>
		</div>
	</form>

</body>
</html>
