<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>ECS Monitor Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>ECS历史监控记录</h3>
			<blockquote>
				<p>云监控针对云主机进行监控的服务使他能获得云主机最近10条历史状态信息。</p>
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
			<label for="tenantsId" class="col-sm-2 control-label">监控项</label>
			<div class="col-sm-4">
				<select id="itemKey" name="itemKey" class="form-control">
					<c:forEach var="item" items="${itemList}">
						<option value="${item.name }">${item.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="tenantsId" class="col-sm-2 control-label">监控历史值 </label>
			<div class="col-sm-10">
				<p class="form-control-static">${itemKey }</p>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label"> </label>
			<div class="col-sm-10">
				<c:forEach var="item" items="${historyData }">
				时间:${item.clock}  值: ${item.value}<br>
				</c:forEach>
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
