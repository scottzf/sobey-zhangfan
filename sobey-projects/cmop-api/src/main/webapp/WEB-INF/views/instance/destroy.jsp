<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Instance Destroy Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="form-group">
			<label for="vmName" class="col-sm-2 control-label">VMName</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="vmName" name="vmName"
					placeholder="虚拟机名称">
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-primary">Destroy</button>
			</div>
		</div>
	</form>

</body>
</html>
