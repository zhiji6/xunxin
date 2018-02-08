<%@ page language="java" contentType="text/html; charset=UTF-8"
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
                        <h5>用户充能 </h5>
                        <div class="ibox-tools">
                            
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form id="ArticleRotationForm" class="ArticleRotationForm" method="post" class="form-horizontal">
                        	<input type="hidden" name="id" id="id" value="${pd.id}"/>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">积分值</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="userExp" id="userExp">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="button" onclick="save();">确定</button>
                                    <button class="btn btn-white" type="button" onclick="goBack();">取消</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>	
	</div>	
	<div id="myModal" class="modal inmodal fade" tabindex="-1" role="dialog"  aria-hidden="true"></div>
		<!-- 全局js -->
    <system:jsFooter/>
    <script src="hplus/js/plugins/jeditable/jquery.jeditable.js"></script>
	
    <!-- 自定义js -->
    <script src="hplus/js/content.min.js"></script>
    
    <script type="text/javascript">
  //表单ID
    var formId = "#ArticleRotationForm";
  
	//判断编码是否存在
	function save(){
		var userExp = $.trim($("#userExp").val());
		var id = $("#id").val();
		$.ajax({
			type: "POST",
			url: 'api/user/user_add_exp?tm=' + new Date().getTime(),
	    	data: {"userExp":userExp,"id":id},
			dataType:'json',
			cache: false,
			success: function(data){
				 if(data.meta.message == 'ok'){
					 layer.msg("充能成功!");
					 goBack();
				 }else{
					 layer.msg("充能失败!");
				 }
			}
		});
	}
	
	function goBack(){
		this.location.href="<%=basePath%>api/user/user_manager";
	}
	
	
	
</script>
	</body>
</html>

