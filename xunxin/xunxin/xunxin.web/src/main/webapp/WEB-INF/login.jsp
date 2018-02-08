<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<meta charset="utf-8">
<title>循心后台管理系统</title>
  <!-- Favicon -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/img/favicon.ico" />
<!-- The styles -->
<link id="bs-css"
	href="${pageContext.request.contextPath}/assets/css/bootstrap-cerulean.min.css"
	rel="stylesheet">

<link
	href="${pageContext.request.contextPath}/assets/css/charisma-app.css"
	rel="stylesheet">

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/plugs/jquery-validation-1.14.0/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugs/jquery-validation-1.14.0/messages_zh.js"></script>
<script>
$.validator.setDefaults({
    /* submitHandler: function() {
    } */
});

function refresh() {  
    var url = "/api/util/login-security-code?nocache=" + new Date().getTime();
    $("#img").attr("src",url);  
}  

$().ready(function() {
	// 提交时验证表单
	var validator = $("#aaa").validate({
		errorPlacement: function(error, element) {
			// Append error within linked label
			$( element )
				.closest( "form" )
					.find( "label[for='" + element.attr( "id" ) + "']" )
						.append( error );
		},
		errorElement: "span",
		messages: {
			name: {
				required: "账号不能为空",
				minlength: " (不能少于 3 个字母)"
			},
			password: {
				required: "密码不能为空",
				minlength: " (字母不能少于 5 个且不能大于 12 个)",
				maxlength: " (字母不能少于 5 个且不能大于 12 个)"
			},
			verifyCode: {
				required: "验证码不能为空",
				minlength: " (不能少于 3 个字母)",
			}
		}
	});

	$(".cancel").click(function() {
		validator.resetForm();
	});
	
   
});
</script>
<style>
/* input.error { border: 10px solid red; }
label.error {
  padding-left: 16px;

  padding-bottom: 2px;

  font-weight: bold;

  color: #EA5200;
}  */
</style>
</head>
<body>
	<div class="ch-container">
		<div class="row">
			<div class="row-md-2" style="width:20%;text-align:left;margin: 0 auto;position:absolute;margin-top:70px;margin-left:150px;">
				<a href="login.jsp"><i>@ 遵循本心，让爱浑然天成</i></a>
				<img src="${pageContext.request.contextPath}/assets/img/xunxin.jpg" >
			</div>
			<div class="row">
				<div class="col-md-3 center login-header">
					<h2>循心后台管理系统</h2>
				</div>
				<!--/span-->
			</div>
			<!--/row-->

			<div class="row">
				<div class="well col-md-3 center login-box">
				
					<c:if test="${not empty msg }">
						<div class="alert alert-info" >
							${msg }
						</div>
					</c:if>
					
					<form class="form-horizontal" action="${pageContext.request.contextPath}/api/admin/loginToIndex" method="post" id="aaa">
						<fieldset>
							<div class="input-group input-group-lg">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-user red"></i></span> <input type="text" style="height:54px;"
									class="form-control" placeholder="AccountName" value="" name="adminName" id="adminName" required >
							</div>
							 <label for="adminName"></label>
							<div class="clearfix"></div>
							<br>

							<div class="input-group input-group-lg">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-lock red"></i></span> <input
									type="password" class="form-control"  value="" placeholder="Password" name="password" id="password" required>
							</div>
							 <label for="password"></label>
							<div class="clearfix"></div>
							<br>
							
							<div class="row-sm-12 input-group input-group-lg">
								<span class="input-group-addon">
									<i class="glyphicon glyphicon-pencil red"></i>
								</span> 
								<input style="width:70%" type="text" class="form-control"  value="" placeholder="请输入验证码" name="verifyCode" id="verifyCode" required>
								<img id="img" tittle="点击刷新验证码" style="border-radius:10px" height="55" width="30%" src="/api/util/login-security-code" onclick="refresh()" />
							</div>
							<label for="verifyCode"></label>
							<div class="clearfix"></div>

							<div class="input-prepend">
								<label class="remember" for="remember"><input
									type="checkbox" id="remember" name="remember"> Remember me</label>
							</div>
							<div class="clearfix"></div>

							<p class="center col-md-5">
								<button type="submit" class="btn btn-primary">登录</button>
							</p>
						</fieldset>
					</form>
				</div>
				<!--/span-->
			</div>
			<!--/row-->
		</div>
		<!--/fluid-row-->

	</div>
	<!--/.fluid-container-->



	
</body>
</html>