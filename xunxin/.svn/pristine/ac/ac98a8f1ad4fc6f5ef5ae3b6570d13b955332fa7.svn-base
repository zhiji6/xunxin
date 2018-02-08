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
						<h5>系统平台信息</h5>
					</div>
					<div class="ibox-content">
						<div id="toolbar" class="btn-group">
							<div class="pull-left form-inline form-group">
							    <input type="text" id="platform_name" name="platform_name" class="form-control" placeholder="平台名称" />
								<button type="button" class="btn btn-default btn-primary" onclick="bstQuery();">
							       	查询
							    </button>
							    <!-- <button type="button" class="btn btn-default btn-primary" onclick="toAdd();">
							       	新增
							    </button> -->
							    <button type="button" class="btn btn-default btn-primary" onclick="toEdit();">
									修改
							    </button>
							  <!--   <button type="button" class="btn btn-default btn-danger" onclick="toDel();">
							       	 删除
							    </button> -->
							</div>
						</div>
						<table id="queryTable" data-mobile-responsive="true"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="modal inmodal fade" tabindex="-1" role="dialog"  aria-hidden="true"></div>
	<!-- 全局js -->
	<system:jsFooter/>
	<script type="text/javascript">
	//表格ID
	var tableId = "#queryTable";
	//表格请求及数据
	var tableColumns = {
		url: 'api/message/empathy_circle_list?tm=' + new Date().getTime(),
		toolbar:'#toolbar',
		method:'post', 
		contentType:"application/x-www-form-urlencoded",
		dataType:	"json",
			sidePagination:"server",
			undefinedText:'',
			idField:'id',
		pagination:"true",
		columns: [{
	        field: 'id',
	        visible: false,
	        halign: 'center'
	    }, {
	        field: 'nickName',
	        title: '用户',
	        align: 'center',
	        halign: 'center',
	        width: '10%',
	        //formatter: formatNAMEFun
	    }, {
	        field: 'content',
	        title: '评论内容',
	        align: 'center',
	        halign: 'center',
	        width: '25%'
	    },  {
	        field: 'address',
	        title: '地址',
	        align: 'center',
	        halign: 'center',
	        width: '15%'
	    },  {
	        field: 'likeNum',
	        title: '点赞数',
	        align: 'center',
	        halign: 'center',
	        width: '5%'
	    },{
	        field: 'isVague',
	        title: '是否模糊',
	        align: 'left',
	        halign: 'center',
	        width: '5%',
	        formatter:formatStatus
	    } ,{
	        field: 'createTime',
	        title: '创建时间',
	        align: 'center',
	        halign: 'center',
	        width: '10%',
	        formatter:formatDataTime
	    }, {
	        field: 'isDelete',
	        title: '是否删除',
	        align: 'center',
	        halign: 'center',
	        width: '15%',
	        formatter:formatDelete
	    }]
	};
	$(document).ready(function (){
		
/* 			var pd = "${pd}";
		console.info(pd);
		var msg = "${msg}";
		console.info(msg);
		if(msg != null && msg != ""){
			if(msg == '200'){
				layer.msg("成功编辑代理商信息", {time:3000});
			} else if(msg == 'successEdit'){
				layer.msg("成功编辑代理商信息");
			}else{
				layer.msg("代理商信息编辑失败！");
			}
		} */
		//初始化下拉菜单
		//$("#M_TYPE_ID").createOption();
		//初始化及查询生成表格内容
		//$("#status").createOption();
		//$("#isDelete").createOption();
		table = $(tableId).bootstrapTable(tableColumns);
	});
	function formatDelete(value, row, index){
		var d = '';  
	    if(row.isDelete != null && row.isDelete == false){ d = '未删除'; }
	    else if(row.isDelete != null && row.isDelete == true){ d = '已删除'; }
	    return d;  
	}
	//时间
	function formatDataTime(value, row, index){
		var d = new Date(row.createTime);  
		if(d != null){
			var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-')   
            + '  ' + [ d.getHours(), d.getMinutes(), d.getSeconds() ].join(':');  
    		return dformat;
		}else{
			return"";
		}
	}
	function formatStatus(value, row, index){
		var d = '';  
		 if(row.isVague != null && row.isVague == 0){ d = '不模糊'; }
	    else if(row.isVague != null && row.isVague == 1){ d = '模糊'; }
	    return d;  
	}
	//获取选中的值
	function getBstCheckedId(param){  
	     var value =""; 
	        $('input:checkbox[name="'+param+'"]:checked').each(function(){
	    	   value+=$(this).val()+",";
	       });
	     return value;
	    } 
	//跳转到编辑页面
	function toEdit(){
		var obj = getBstCheckedId('btSelectItem');
		var ids = obj.split(",");
		if(ids == '' || ids.length > 2){
			layer.msg('请只选中一条信息再进行编辑。');
			return false;
		}
		window.location.href = "<%=basePath%>api/message/empathy_circle_to_edit?id="+ids[0];
	}
	</script>
</body>
</html>