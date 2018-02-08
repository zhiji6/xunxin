﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
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
						<h5>认证信息管理修改</h5>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="modal inmodal fade" tabindex="-1" role="dialog"  aria-hidden="true"></div>
		<form action="api/auth/authentication_manager_examine" method="post" enctype="application/x-www-form-urlencoded">
		  <div class="form-group">
		    <label for="exampleInputEmail1">认证信息</label>
		    <input type="text" class="form-control Readonly" id="exampleInputEmail1" placeholder="认证信息" style="width:50%;" Readonly value="${authentication.authInfo}">
		  	<input type="hidden" name="id" value="${authentication.id}">
		  </div>
		  <div class="form-group">
		    <label for="exampleInputPassword1">认证类型</label>
		     <select class="form-control" name="authType" id="authType" value="" disabled="disabled"
                                    ajax--url="api/auth/query_dict?type=authType" ajax--column="ID,TEXT" ajax-- style="width:150px;" Readonly>
                                    <option value="" >--选择认证类型--</option>
             </select> 
		  </div>
		  <div class="form-group">
		  <label for="exampleInputEmail1">认证状态</label>
		    <select class="form-control" name="authState" id="authState"  
                                      ajax-- style="width:150px;">
                                    <option value="">--选择认证状态--</option>
        							<option value="0">--审核通过--</option>
        							<option value="0">--审核不通过--</option>
            </select>
		  </div>
		  <div class="form-group">
		  <label for="exampleInputEmail1">附加信息</label>
		    <img src="http://p2.ifengimg.com/a/2017_50/8ddee390f9c2127_size41_w690_h460.jpg" class="img-responsive" alt="图片不存在" style="width:50%;">
		  </div>
		  <div class="form-group">
		  <label for="exampleInputEmail1">备注</label>
		    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="备注" style="width:50%;" Readonly value="${authentication.authRemark}">
		  </div>
		   <div class="form-group">
		  <label for="exampleInputEmail1">用户名</label>
		    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="用户名" style="width:50%;" Readonly value="${authentication.nickName}">
		  </div>
		  <button type="submit" class="btn btn-default">Submit</button>
		</form>

	<!-- 全局js -->
	<system:jsFooter/>
<script type="text/javascript">
		$(document).ready(function (){
	
				$("#authType").createOption();
				//var type = "${authentication.authType}";
			$("#authType option[value='${authentication.authType}']").attr("selected","selected"); 
			});
</script>
</body>
</html>