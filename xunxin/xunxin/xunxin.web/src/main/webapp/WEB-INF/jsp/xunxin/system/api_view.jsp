<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<system:header/>
<!-- jsp文件头和头部 -->
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>接口详情: ${pd.api_name}</h5>
					</div>
					<div class="ibox-content">
						<form id="HospitalExpertLibsForm" name="HospitalExpertLibsForm" class="form-horizontal" method="post">
							<div class="form-group">
								<label class="col-sm-2 control-label">接口名称：</label>
							    <div class="col-sm-4">
									${pd.api_name}
								</div>
								<label class="col-sm-2 control-label">API链接：</label>
							    <div class="col-sm-4">
									${pd.api_url}
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">接口模块：</label>
							    <div class="col-sm-4">
									${pd.api_model}
								</div>
								<label class="col-sm-2 control-label">API来源：</label>
							    <div class="col-sm-4">
									${pd.api_source}
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">API前缀：</label>
							    <div class="col-sm-4">
									${pd.api_prefix}
								</div>
								<label class="col-sm-2 control-label">创建时间：</label>
							    <div class="col-sm-4">
									${pd.createTime}
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">API状态：</label>
								<div class="col-sm-10" style="word-break:break-all;">
									${pd.api_state}
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
	<!-- 自定义js -->
	<script type="text/javascript">
		$(document).ready(function(){
		});
	</script>
</body>
</html>