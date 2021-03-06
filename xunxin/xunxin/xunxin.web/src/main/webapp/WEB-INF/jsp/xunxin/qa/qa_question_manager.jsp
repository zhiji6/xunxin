﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<system:header/>
<!-- jsp文件头和头部 -->
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight" >
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>Q&A管理</h5>
					</div>
					<div class="ibox-content">
						<div id="toolbar" class="btn-group">
							<div class="pull-left form-inline form-group">
							    <input type="text" id="platform_name" name="platform_name" class="form-control" placeholder="问题名称" />
							    <select class="form-control" name="status" id="status" value="${pd.status}"
                                    ajax--url="api/qa/full_qa_status" ajax--column="ID,TEXT" style="width:140px;">
                                    <option value="">--选择Q&A状态--</option>
                                    <option value="0"> 未审核 </option>
                                    <option value="1"> 审核通过 </option>
                                    <option value="2"> 驳回处理 </option>
                                    <option value="3"> 其他 </option>
                     			</select>
							    <select class="form-control" name="sectionName" id="sectionName" value="${pd.sectionName}"
                                    ajax--url="api/qa/full_qa_section_list" ajax--column="ID,TEXT" style="width:140px;">
                                    <option value="">--选择板块--</option>
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
							    <button type="button" class="btn btn-default btn-danger" onclick="importExcel();">
							       	 导入
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
			url: 'api/qa/full_qa_list?tm=' + new Date().getTime(),
			toolbar:'#toolbar',
			method:'post', 
	 	 	/* responseHandler: function (res) {
 	        	res.rows = res.list;
 	        	delete res.list;
 	        	return res;
 	        }, */
			columns: [{
		        field: 'id',
		        visible: false,
		        halign: 'center'
		    }, {
                field: 'sectionName',
                title: '板块',
                align: 'center',
                halign: 'center',
                width: '10%',
            }, {
		        field: 'name',
		        title: '问题名称',
		        align: 'center',
		        halign: 'center',
		        width: '15%',
		        formatter: formatNAMEFun
		    }, {
		        field: 'userID',
		        title: '发布人',
		        align: 'center',
		        halign: 'center',
		        width: '10%'
		    }, {
		        field: 'releaseTime',
		        title: '上线时间',
		        //visible: false,
		        align: 'center',
		        halign: 'center',
		        width: '15%',
		        formatter:formatDataTime
		    }, {
		        field: 'remark',
		        title: '备注',
		        align: 'center',
		        halign: 'center',
		        width: '25%'
		    }, {
		        field: 'status',
		        title: '话题状态',
		        //visible: false,
		        align: 'center',
		        halign: 'center',
		        width: '5%',
		        formatter:formatStatus
		    }]
		};
		$(document).ready(function (){
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
			}
			//初始化下拉菜单
			$("#sectionName").createOption();
			//初始化及查询生成表格内容
			table = $(tableId).bootstrapTable(tableColumns);
		});
		//查询刷新表格
		function searchRefreshTable(){
			//销毁表格
			$(tableId).bootstrapTable('destroy');
			$(tableId).bootstrapTable(tableColumns);
		}
		//导出Excel
		function toExport(){
			$(tableId).bootstrapTable('exportTable', {
				type : 'excel'
			});
		}
		//跳转到新增页面
		function toAdd(){
			window.location.href = "<%=basePath%>api/qa/qa_add";
		}
		//跳转到编辑页面
		function toEdit(){
			var ids = getBstCheckedId('id');
			if(!(ids.length == 1)){
				layer.msg('请只选中一条信息再进行编辑。');
				return false;
			}
			window.location.href = "<%=basePath%>api/qa/qa_edit?id=" + ids[0];
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
						url: '<%=basePath%>api/qa/delete_qa_to_repertory?tm=' +  new Date().getTime(),
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
				layer.full(
					layer.open({
						type: 2,
						title: '信息浏览',//窗体标题
						area: ['600px', '600px'],//整个窗体宽、高，单位：像素px
						fix: false,//窗体位置不固定
						maxmin: true,//最大、小化是否显示
						scrollbar: true,//整体页面滚动条是否显示 
						content: ['/api/qa/qa_view?id=' + id],//URL地址
						closeBtn: 1,//显示关闭按钮
						btn: ['关闭']
				    })
				);
			}else{
				layer.msg("系统未获取到数据主键，请重新选择数据！");
			}
		}
		//导入
		function importExcel(){
			layer.full(layer.open({
				type: 2,
				title: '导入Excel文件',//窗体标题
				area: ['800px', '480px'],//整个窗体宽、高，单位：像素px
				fix: false,//窗体位置不固定
				maxmin: true,//最大、小化是否显示
				scrollbar: true,//整体页面滚动条是否显示
				shadeClose: true, //点击遮罩关闭
				content: ['/api/qa/qa_excel_import', 'no'],//URL地址
				closeBtn: 1,//显示关闭按钮
				btn: ['关闭']
			}));
		}
		
		
		//操作
		function formatNAMEFun(value, row, index){
			var format_v = "<button type=\"button\" class=\"btn btn-link\" onclick=\"toView('"+row.id+"');\">" + row.name + "</button>";
			return format_v;
		}
		//时间
		function formatDataTime(value, row, index){
			var d = new Date(row.releaseTime);  
		    var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-')   
		            + '  ' + [ d.getHours(), d.getMinutes(), d.getSeconds() ].join(':');  
		    return dformat;  
		}
		//话题状态 	/**	 * 问题状态: * 	0:未审核  1:审核通过 2:驳回处理 3:其他  	 */
		function formatStatus(value, row, index){
			var d = '';  
		    if(row.status == 0){ d = '未审核'; }
		    if(row.status == 1){ d = '审核通过'; }
		    if(row.status == 2){ d = '驳回处理'; }
		    if(row.status == 3){ d = '其他'; }
		    return d;  
		}
	</script>
	
</body>
</html>