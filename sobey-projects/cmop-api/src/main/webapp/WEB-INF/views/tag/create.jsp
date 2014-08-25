<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Tag Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>Tag创建页面</h3>
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
			<label for="domianName" class="col-sm-2 control-label">Tag名称</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="name" name="name">
			</div>
		</div>

		<div class="form-group">
			<label for="tenantsId" class="col-sm-2 control-label">租户</label>
			<div class="col-sm-4">
				<select id="tagType" name="tagType" class="form-control">
					<option value="45">上级标签</option>
					<option value="46">子标签</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="tenantsId" class="col-sm-2 control-label">标签</label>
			<div class="col-sm-4">
				<select id="parentTag" name="parentTag" class="form-control">
					<c:forEach var="item" items="${tagList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>




		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Create</button>
			</div>
		</div>
	</form>



</body>
</html>
