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
						<h5>用户详情: ${pd.nickName}</h5>
					</div>
					<div class="ibox-content">
						<form id="HospitalExpertLibsForm" name="HospitalExpertLibsForm" class="form-horizontal" method="post">
							<div class="form-group">
								<label class="col-sm-2 control-label">手机号：</label>
							    <div class="col-sm-2">
							        <input type="text" class="form-control required" name="phone" id="phone" value="${pd.phone}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">真实姓名：</label>
							    <div class="col-sm-2">
							        <input type="text" class="form-control required" name="name" id="name" value="${pd.name}"  disabled="true" "  />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">用户昵称：</label>
							    <div class="col-sm-2">
							        <input type="text" class="form-control required" name="nickName" id="nickName" value="${pd.nickName}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">性别：</label>
							    <div class="col-sm-2">
							        <input type="text" class="form-control required" name="gender" id="gender" value="${pd.gender}"  disabled="true" "  />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">年龄：</label>
							    <div class="col-sm-2">
							        <input type="text" class="form-control required" name="age" id="age" value="${pd.age}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">性取向：</label>
							    <div class="col-sm-2">
							        <input type="text" class="form-control required" name="sexualOrientation" id="sexualOrientation" value="${pd.sexualOrientation}"  disabled="true" "  />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">现居地：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="address" id="address" value="${pd.address}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">身高：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="height" id="height" value="${pd.height}"  disabled="true" "  />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">职业：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="profession" id="profession" value="${pd.profession}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">行业：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="trade" id="trade" value="${pd.trade}"  disabled="true" "  />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">职位：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="position" id="position" value="${pd.position}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">收入：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="income" id="income" value="${pd.income}"  disabled="true" "  />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">学历：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="education" id="education" value="${pd.education}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">毕业院校：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="williamsCollege" id="williamsCollege" value="${pd.williamsCollege}"  disabled="true" "  />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">情感状态：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="relationshipStatus" id="relationshipStatus" value="${pd.relationshipStatus}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">交友方式：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="makeFriendWay" id="makeFriendWay" value="${pd.makeFriendWay}"  disabled="true" "  />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">独白：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <textarea type="text" class="form-control required" name="introduce" id="introduce" disabled="true" style="width:800px;height:100px">${pd.introduce}</textarea> 
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">居住条件：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="housingConditions" id="housingConditions" value="${pd.housingConditions}"  disabled="true" "  />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">交通工具：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="trafficTools" id="trafficTools" value="${pd.trafficTools}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">体重：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="weight" id="weight" value="${pd.weight}"  disabled="true" "  />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">民族：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="nation" id="nation" value="${pd.nation}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">国籍：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="nationality" id="nationality" value="${pd.nationality}"  disabled="true" "  />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">籍贯：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="nativePlace" id="nativePlace" value="${pd.nativePlace}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">户籍：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="censusRegister" id="censusRegister" value="${pd.censusRegister}"  disabled="true" "  />
								</div>
							</div>
							
							
							<div class="form-group">
								<label class="col-sm-2 control-label">家中排行：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="familyOrder" id="familyOrder" value="${pd.familyOrder}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">有无子女：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="haveChild" id="haveChild" value="${pd.haveChild}"  disabled="true" "  />
								</div>
							</div>
							
							
							<div class="form-group">
								<label class="col-sm-2 control-label">宗教信仰：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="religiousBelief" id="religiousBelief" value="${pd.religiousBelief}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">星座：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="constellation" id="constellation" value="${pd.constellation}"  disabled="true" "  />
								</div>
							</div>
							
							
							<div class="form-group">
								<label class="col-sm-2 control-label">生肖：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="zodiac" id="zodiac" value="${pd.zodiac}"  disabled="true" "  />
								</div>
								<label class="col-sm-2 control-label">血型：</label>
								<div class="col-sm-2" style="word-break:break-all;">
							        <input type="text" class="form-control required" name="blood" id="blood" value="${pd.blood}"  disabled="true" "  />
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