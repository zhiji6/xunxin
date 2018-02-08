<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF//include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<system:header/>
<!-- jsp文件头和头部 -->
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>邮箱认证</h5>
					</div>
					<div class="ibox-content">
						<form id="ArticleForm" name="BankAccountForm" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">认证结果:</label>
						    <div class="col-sm-4">
						        <input type="text" class="form-control required" name="msg" id="msg" value="${pd.msg}"  disabled="true" style="width:800px;"  />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 全局js -->
   <system:jsFooter/>
<script src="hplus/js/plugins/jeditable/jquery.jeditable.js"></script>
<!-- 自定义js -->
<script src="hplus/js/content.min.js"></script>
<script type="text/javascript">
	//表单ID
	var formId = "#ArticleForm";
    
</script>
</body>
</html>