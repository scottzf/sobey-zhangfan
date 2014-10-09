<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>ECS Destroy Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>销毁ECS页面</h3>
			<blockquote>
				<p>当您不需要更多计算资源能力时，可根据SobeyCloud提供的弹性计算能力对云主机（ECS）回收。</p>
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
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</div>
	</form>

</body>
</html>
