<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script 	src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/assets/css/charisma-app.css"
	rel="stylesheet">
	
<link
	href="${pageContext.request.contextPath}/assets/template/css/plugins/sweetalert/sweetalert.css"
	rel="stylesheet">
 <style type="text/css">
    /*
 *  Usage:
 *
      <div class="sk-fading-circle">
        <div class="sk-circle1 sk-circle"></div>
        <div class="sk-circle2 sk-circle"></div>
        <div class="sk-circle3 sk-circle"></div>
        <div class="sk-circle4 sk-circle"></div>
        <div class="sk-circle5 sk-circle"></div>
        <div class="sk-circle6 sk-circle"></div>
        <div class="sk-circle7 sk-circle"></div>
        <div class="sk-circle8 sk-circle"></div>
        <div class="sk-circle9 sk-circle"></div>
        <div class="sk-circle10 sk-circle"></div>
        <div class="sk-circle11 sk-circle"></div>
        <div class="sk-circle12 sk-circle"></div>
      </div>
 *
 */
.sk-fading-circle {
  margin: 40px auto;
  width: 40px;
  height: 40px;
  position: relative; }
  .sk-fading-circle .sk-circle {
    width: 100%;
    height: 100%;
    position: absolute;
    left: 0;
    top: 0; }
  .sk-fading-circle .sk-circle:before {
    content: '';
    display: block;
    margin: 0 auto;
    width: 15%;
    height: 15%;
    background-color: #333;
    border-radius: 100%;
    -webkit-animation: sk-circleFadeDelay 1.2s infinite ease-in-out both;
            animation: sk-circleFadeDelay 1.2s infinite ease-in-out both; }
  .sk-fading-circle .sk-circle2 {
    -webkit-transform: rotate(30deg);
        -ms-transform: rotate(30deg);
            transform: rotate(30deg); }
  .sk-fading-circle .sk-circle3 {
    -webkit-transform: rotate(60deg);
        -ms-transform: rotate(60deg);
            transform: rotate(60deg); }
  .sk-fading-circle .sk-circle4 {
    -webkit-transform: rotate(90deg);
        -ms-transform: rotate(90deg);
            transform: rotate(90deg); }
  .sk-fading-circle .sk-circle5 {
    -webkit-transform: rotate(120deg);
        -ms-transform: rotate(120deg);
            transform: rotate(120deg); }
  .sk-fading-circle .sk-circle6 {
    -webkit-transform: rotate(150deg);
        -ms-transform: rotate(150deg);
            transform: rotate(150deg); }
  .sk-fading-circle .sk-circle7 {
    -webkit-transform: rotate(180deg);
        -ms-transform: rotate(180deg);
            transform: rotate(180deg); }
  .sk-fading-circle .sk-circle8 {
    -webkit-transform: rotate(210deg);
        -ms-transform: rotate(210deg);
            transform: rotate(210deg); }
  .sk-fading-circle .sk-circle9 {
    -webkit-transform: rotate(240deg);
        -ms-transform: rotate(240deg);
            transform: rotate(240deg); }
  .sk-fading-circle .sk-circle10 {
    -webkit-transform: rotate(270deg);
        -ms-transform: rotate(270deg);
            transform: rotate(270deg); }
  .sk-fading-circle .sk-circle11 {
    -webkit-transform: rotate(300deg);
        -ms-transform: rotate(300deg);
            transform: rotate(300deg); }
  .sk-fading-circle .sk-circle12 {
    -webkit-transform: rotate(330deg);
        -ms-transform: rotate(330deg);
            transform: rotate(330deg); }
  .sk-fading-circle .sk-circle2:before {
    -webkit-animation-delay: -1.1s;
            animation-delay: -1.1s; }
  .sk-fading-circle .sk-circle3:before {
    -webkit-animation-delay: -1s;
            animation-delay: -1s; }
  .sk-fading-circle .sk-circle4:before {
    -webkit-animation-delay: -0.9s;
            animation-delay: -0.9s; }
  .sk-fading-circle .sk-circle5:before {
    -webkit-animation-delay: -0.8s;
            animation-delay: -0.8s; }
  .sk-fading-circle .sk-circle6:before {
    -webkit-animation-delay: -0.7s;
            animation-delay: -0.7s; }
  .sk-fading-circle .sk-circle7:before {
    -webkit-animation-delay: -0.6s;
            animation-delay: -0.6s; }
  .sk-fading-circle .sk-circle8:before {
    -webkit-animation-delay: -0.5s;
            animation-delay: -0.5s; }
  .sk-fading-circle .sk-circle9:before {
    -webkit-animation-delay: -0.4s;
            animation-delay: -0.4s; }
  .sk-fading-circle .sk-circle10:before {
    -webkit-animation-delay: -0.3s;
            animation-delay: -0.3s; }
  .sk-fading-circle .sk-circle11:before {
    -webkit-animation-delay: -0.2s;
            animation-delay: -0.2s; }
  .sk-fading-circle .sk-circle12:before {
    -webkit-animation-delay: -0.1s;
            animation-delay: -0.1s; }

@-webkit-keyframes sk-circleFadeDelay {
  0%, 39%, 100% {
    opacity: 0; }
  40% {
    opacity: 1; } }

@keyframes sk-circleFadeDelay {
  0%, 39%, 100% {
    opacity: 0; }
  40% {
    opacity: 1; } }

  </style>
<!-- jQuery -->
<script
	src="${pageContext.request.contextPath}/assets/js/jquery/jquery.min.js"></script>

<script
	src="${pageContext.request.contextPath}/assets/plugs/jquery-validation-1.14.0/jquery.validate.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/plugs/jquery-validation-1.14.0/messages_zh.js"></script>
<script>

function toInit() {
	
	swal({
		title : "您确定要对数据进行初始化吗？",
		text : "初始化后部分数据将无法恢复，请谨慎操作！",
		type : "warning",
		showCancelButton : true,
		confirmButtonColor : "#DD6B55",
		confirmButtonText : "是的，我要初始化！",
		cancelButtonText : "让我再考虑一下…",
		closeOnConfirm : true,
		closeOnCancel : false
	}, function(a) {
		if (a) {
			var accountName = $('#accountName').val();
			var passWord = $('#passWord').val();
			if (accountName != "" && passWord != "") {
				$.ajax({
					type : "POST",
					url : "toInit",
					data : "accountName=" + accountName + "&passWord=" + passWord,
					dataType : "text",
					beforeSend : function() {
						$("#init").show();
					},
					success : function(data) {
						$("#msg").show().text(data);
					},
				 	complete : function() {
						$("#init").hide();
					}, 
					error : function(data) {
						console.info("error: " + data.responseText);
					}
				});

			}
			
			
			//swal("删除成功！","您已经永久删除了这条信息。","success")
		} else {
			swal("已取消", "您取消了删除操作！", "error")
		}
	})
	
	
	
}













	/* function toInit() {
		var accountName = $('#accountName').val();
		var passWord = $('#passWord').val();
		if (accountName != "" && passWord != "") {
			$.ajax({
				type : "POST",
				url : "toInit",
				data : "accountName=" + accountName + "&passWord=" + passWord,
				dataType : "text",
				beforeSend : function() {
					$("#init").show();
				},
				success : function(data) {
					$("#msg").show().text(data);
				},
			 	complete : function() {
					$("#init").hide();
				}, 
				error : function(data) {
					console.info("error: " + data.responseText);
				}
			});

		}
	} */

	$.validator.setDefaults({
	/* submitHandler: function() {
	} */
	});

	$().ready(
			function() {
				// 提交时验证表单
				var validator = $("#aaa").validate(
						{
							errorPlacement : function(error, element) {
								// Append error within linked label
								$(element).closest("form").find(
										"label[for='" + element.attr("id")
												+ "']").append(error);
							},
							errorElement : "span",
							messages : {
								accountName : {
									required : "账号不能为空",
									minlength : " (不能少于 3 个字母)"
								},
								passWord : {
									required : "密码不能为空",
									minlength : " (字母不能少于 5 个且不能大于 12 个)",
									maxlength : " (字母不能少于 5 个且不能大于 12 个)"
								}
							}
						});

				$(".cancel").click(function() {
					validator.resetForm();
				});
			});
</script>
<!-- The fav icon -->
<link rel="shortcut icon" href="img/favicon.ico">
<style>
input.error {
	border: 10px solid red;
}

label.error {
	padding-left: 16px;
	padding-bottom: 2px;
	font-weight: bold;
	color: #EA5200;
}
</style>
</head>
<body>
	<div class="ch-container">
		<div class="row">

			<div class="row">
				<div class="col-md-12 center login-header">
					<h2>系统初始化</h2>
				</div>
				<!--/span-->
			</div>
			<!--/row-->
			<div class="row">
				<div class="well col-md-5 center login-box">
					
					<c:if test="${not empty rtmsg }">
						<div class="alert alert-info" >${rtmsg }</div>
					</c:if>
					<div class="alert alert-info" id="msg" style="display: none;"></div>
					<form class="form-horizontal" action="#" method="post" id="aaa">
						<fieldset>
							<div class="input-group input-group-lg">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-user red"></i></span> <input type="text"
									style="height: 54px;" class="form-control"
									placeholder="AccountName" value="" name="accountName"
									id="accountName" required>
							</div>
							<label for="accountName"></label>
							<div class="clearfix"></div>
							<br>

							<div class="input-group input-group-lg">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-lock red"></i></span> <input
									type="password" class="form-control" value=""
									placeholder="Password" name="passWord" id="passWord" required>
							</div>
							<label for="passWord"></label>
							<div class="clearfix"></div>

							<div class="clearfix"></div>
							<div id="init" style="display: none;">
								<div class="sk-fading-circle">
									<div class="sk-circle1 sk-circle"></div>
									<div class="sk-circle2 sk-circle"></div>
									<div class="sk-circle3 sk-circle"></div>
									<div class="sk-circle4 sk-circle"></div>
									<div class="sk-circle5 sk-circle"></div>
									<div class="sk-circle6 sk-circle"></div>
									<div class="sk-circle7 sk-circle"></div>
									<div class="sk-circle8 sk-circle"></div>
									<div class="sk-circle9 sk-circle"></div>
									<div class="sk-circle10 sk-circle"></div>
									<div class="sk-circle11 sk-circle"></div>
									<div class="sk-circle12 sk-circle"></div>
								</div>
								正在初始化中。。
							</div>

							<p class="center col-md-5">

								<button type="button" class="btn btn-primary"
									onclick="return toInit();">一键初始化</button>
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


<!-- Sweet alert -->
<script
	src="${pageContext.request.contextPath}/assets/template/js/plugins/sweetalert/sweetalert.min.js"></script>
</body>
</html>