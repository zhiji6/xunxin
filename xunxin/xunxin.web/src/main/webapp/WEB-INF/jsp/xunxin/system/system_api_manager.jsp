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
						<h5>API管理</h5>
					</div>
					<div class="ibox-content">
						<div id="toolbar" class="btn-group">
							<div class="pull-left form-inline form-group">
							    <input type="text" id="api_name" name="api_name" class="form-control" placeholder="接口名称" />
							    <select class="form-control" name="api_model" id="api_model" value="${api_model}" style="width:140px;">
                                    <option value="">--接口模块--</option>
                                    <option value="0">--APP--</option>
                                    <option value="1">--BASE--</option>
                                    <option value="2">--WEB--</option>
                     			</select>
								<button type="button" class="btn btn-default btn-primary" onclick="bstQuery();">
							       	查询
							    </button>
							    <button type="button" class="btn btn-default btn-primary" onclick="toAdd();">
							       	新增
							    </button>
							    <button type="button" class="btn btn-default btn-primary" onclick="toEdit();">
									修改
							    </button>
							    <button type="button" class="btn btn-default btn-danger" onclick="toDel();">
							       	 删除
							    </button>
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
			url: 'api/system/show_api_documents?tm=' + new Date().getTime(),
			toolbar:'#toolbar',
			method:'post', 
			columns: [{
		        field: 'id',
		        visible: false,
		        halign: 'center'
		    }, {
		        field: 'api_name',
		        title: '接口名称',
		        align: 'center',
		        halign: 'center',
		        width: '15%',
		        formatter: formatNAMEFun
		    }, {
		        field: 'api_url',
		        title: 'API链接',
		        align: 'center',
		        halign: 'center',
		        width: '25%'
		    }, {
		        field: 'api_model',
		        title: '接口模块',
		        align: 'center',
		        halign: 'center',
		        width: '20%',
		    }, {
		        field: 'api_source',
		        title: 'API来源',
		        align: 'center',
		        halign: 'center',
		        width: '5%'
		    }, {
		        field: 'api_prefix',
		        title: 'API前缀',
		        //visible: false,
		        align: 'center',
		        halign: 'center',
		        width: '5%'
		    }, {
		        field: 'createTime',
		        title: '创建时间',
		        align: 'center',
		        halign: 'center',
		        width: '15%',
		        formatter: formatDataTime
		    }, {
		        field: 'api_state',
		        title: 'API状态',
		        //visible: false,
		        align: 'center',
		        halign: 'center',
		        width: '10%',
		        formatter: formatStatus
		    }]
		};
		$(document).ready(function (){
			
			//初始化下拉菜单
			$("#api_model").createOption();
			//初始化及查询生成表格内容
			table = $(tableId).bootstrapTable(tableColumns);
		});
		//查询刷新表格
		function searchRefreshTable(){
			//销毁表格
			$(tableId).bootstrapTable('destroy');
			$(tableId).bootstrapTable(tableColumns);
		}
		
		//跳转到新增页面
		function toAdd(){
			window.location.href = "<%=basePath%>api/system/system_api_add";
		}
		//跳转到编辑页面
		function toEdit(){
			var ids = getBstCheckedId('id');
			if(!(ids.length == 1)){
				layer.msg('请只选中一条信息再进行编辑。');
				return false;
			}
			window.location.href = "<%=basePath%>api/system/system_api_edit?id=" + ids[0];
		}
		//批量删除数据
		function toDel(){
			var ids = getBstCheckedId('id');
			if((ids.length < 1)){
				layer.msg('请选中信息再进行删除。');
				return false;
			}
			var idsStr = ids.toString();
			layer.confirm('确定删除已选信息吗？', {
				btn: ['确认','取消'],
				shade: false,
				yes: function(index, layero){
					$.ajax({
						type: "POST",
						url: '<%=basePath%>api/system/api_delete?tm=' +  new Date().getTime(),
						data: {
							IDS: idsStr
						},
						dataType: 'json',
						//beforeSend: validateData,
						cache: false,
						success: function(data) {
							if (data.msg == 'success') {
								layer.msg('删除信息成功');
								bstQuery();
							} else {
								layer.msg('删除信息失败');
							}
						}
					});
				}
			});
		}
		//浏览
		function toView(id){
			if(id != null && id != ""){
				layer.full(layer.open({
					type: 2,
					title: '信息浏览',//窗体标题
					area: ['600px', '600px'],//整个窗体宽、高，单位：像素px
					fix: false,//窗体位置不固定
					maxmin: true,//最大、小化是否显示
					scrollbar: true,//整体页面滚动条是否显示
					content: ['/api/system/system_api_view?id=' + id, 'no'],//URL地址
					closeBtn: 1,//显示关闭按钮
					btn: ['关闭']
				}));
			}else{
				layer.msg("系统未获取到数据主键，请重新选择数据！");
			}
		}
		
		//操作
		function formatNAMEFun(value, row, index){
			var format_v = "<button type=\"button\" class=\"btn btn-link\" onclick=\"toView('"+row.id+"');\">" + row.api_name + "</button>";
			return format_v;
		}
		//时间
		function formatDataTime(value, row, index){
			var d = new Date(row.createTime);  
		    var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-')   
		            + '  ' + [ d.getHours(), d.getMinutes(), d.getSeconds() ].join(':');  
		    return dformat;  
		}
		//话题状态 	/**	 * 问题状态: * 	0:未审核  1:审核通过 2:驳回处理 3:其他  	 */
		function formatStatus(value, row, index){
			var d = '';  
		    if(row.api_state == 0){ d = '不可用'; }
		    if(row.api_state == 1){ d = '可用'; }
		    if(row.api_state == 2){ d = '废弃'; }
		    return d;  
		}
	</script>
</body>
</html>