<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Tenants Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>创建租户页面</h3>
			<blockquote>
				<p>SobeyCloud为每个租户提供了相同账号和密码的VPN默认账户。</p>
			</blockquote>
		</div>

		<div class="form-group">
			<label for="email" class="col-sm-2 control-label">邮箱</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="email" name="email"
					placeholder="用户名" value="">
			</div>
		</div>

		<div class="form-group">
			<label for="email" class="col-sm-2 control-label">密码</label>
			<div class="col-sm-4">
				<input type="password" class="form-control" id="password"
					name="password" placeholder="密码" value="">
			</div>
		</div>


		<div class="form-group">
			<label for="company" class="col-sm-2 control-label">Company</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="company" name="company"
					placeholder="company" value="">
			</div>
		</div>

		<div class="form-group">
			<label for="phone" class="col-sm-2 control-label">Phone</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="phone" name="phone"
					placeholder="phone" value="">
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
