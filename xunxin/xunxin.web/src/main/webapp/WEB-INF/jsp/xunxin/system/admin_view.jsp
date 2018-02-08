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
						<h5>管理员详情: ${pd.nickName}</h5>
					</div>
					<div class="ibox-content">
                        <form id="qaDetailsForm" name="qaDetailsForm" class="form-horizontal" method="post">
                            <input type="hidden" name="id" id="id" value="${pd.id}" />
                            <div class="form-group">
                                <label class="col-sm-2 control-label">账号名：</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control required" name="adminName" id="adminName" value="${pd.adminName}"  disabled="true" "  />
                                </div>
                                <label class="col-sm-2 control-label">角色：</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control required" name="roleId" id="roleId" value="${pd.roleId}"  disabled="true" "  />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">性别：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="gender" id="gender" disabled="true" >${pd.gender}</div>     
                                </div>
                                <label class="col-sm-2 control-label">昵称：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="nickName" id="nickName" disabled="true" >${pd.nickName}</div>     
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">注册时间：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="createTime" id="createTime" disabled="true" >${pd.createTime}</div>     
                                </div>
                                <label class="col-sm-2 control-label">上次登录时间：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="lastLoginTime" id="lastLoginTime" disabled="true" >${pd.lastLoginTime}</div>     
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">上次登录IP：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="lastLoginIp" id="lastLoginIp" disabled="true" >${pd.lastLoginIp}</div>     
                                </div>
                                <label class="col-sm-2 control-label">是否冻结：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="ishiden" id="ishiden" disabled="true" >${pd.ishiden}</div>     
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