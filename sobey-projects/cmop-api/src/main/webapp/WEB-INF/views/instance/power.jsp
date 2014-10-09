<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>ECS Power Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>ECS电源管理页面</h3>
			<blockquote>
				<p>可根据具体需要对云主机（ECS）进行电源的管理：开机、关机。</p>
			</blockquote>
		</div>

		<div class="form-group">
			<label for="ecsId" class="col-sm-2 control-label">ECS</label>
			<div class="col-sm-4">
				<select id="ecsId" name="ecsId" class="form-control">
					<c:forEach var="item" items="${ecsList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="operation" class="col-sm-2 control-label">操作</label>
			<div class="col-sm-4">
				<select id="operation" name="operation" class="form-control">
					<option value="poweron">开机</option>
					<option value="poweroff">关机</option>
				</select>
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
