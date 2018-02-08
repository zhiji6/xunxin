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
							    <!-- <input type="text" id="platform_name" name="platform_name" class="form-control" placeholder="平台名称" /> -->
								<select class="form-control" name="status" id="status"  
                                    ajax--url="api/auth/query_dict?type=commentStatus" ajax--column="ID,TEXT" ajax-- style="width:150px;">
                                    <option value="">--选择评论状态--</option>
                     			</select>
                     			<select class="form-control" name="isDelete" id="isDelete"  
                                    ajax--url="api/auth/query_dict?type=isDelete" ajax--column="ID,TEXT" ajax-- style="width:150px;">
                                    <option value="">--选择删除状态--</option>
                     			</select>
								
								<button type="button" class="btn btn-default btn-primary" onclick="bstQuery();">
							       	查询
							    </button>
<!-- 							    <button type="button" class="btn btn-default btn-primary" onclick="toAdd();">
							       	新增
							    </button>
							    <button type="button" class="btn btn-default btn-primary" onclick="toEdit();">
									修改
							    </button> -->
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
			url: 'api/message/message_manager_list?tm=' + new Date().getTime(),
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
		        field: 'reployName',
		        title: '被回复者',
		        align: 'center',
		        halign: 'center',
		        width: '10%',
		    }, {
		        field: 'content',
		        title: '评论内容',
		        align: 'center',
		        halign: 'center',
		        width: '15%'
		    },  {
		        field: 'identificationNum',
		        title: '点赞数',
		        align: 'center',
		        halign: 'center',
		        width: '5%'
		    },{
		        field: 'status',
		        title: '是否置顶',
		        align: 'left',
		        halign: 'center',
		        width: '5%',
		        formatter:formatStatus
		    }, {
		        field: 'questionName',
		        title: '问题内容',
		        //visible: false,
		        align: 'center',
		        halign: 'center',
		        width: '15%'
		    }, {
		        field: 'createDate',
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
			$("#status").createOption();
			$("#isDelete").createOption();
			table = $(tableId).bootstrapTable(tableColumns);
		});
		//时间
		function formatDataTime(value, row, index){
			var d = new Date(row.createDate);  
			if(d != null){
				var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-')   
	            + '  ' + [ d.getHours(), d.getMinutes(), d.getSeconds() ].join(':');  
	    		return dformat;
			}else{
				return"";
			}
		}
		//状态修改

		function formatDelete(value, row, index){
			var d = '';  
		    if(row.isDelete != null && row.isDelete == false){ d = '未删除'; }
		    else if(row.isDelete != null && row.isDelete == true){ d = '已删除'; }
		    return d;  
		}
		
		function formatStatus(value, row, index){
			var d = '';  
			 if(row.status != null && row.status == 'Y'){ d = '已置顶'; }
		    else if(row.status != null && row.status == 'N'){ d = '未置顶'; }
		    return d;  
		}
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
			window.location.href = "<%=basePath%>agent/toAdd";
		}
		//跳转到编辑页面
		function toEdit(){
			var ids = getBstCheckedId('btSelectItem');
			if(!(ids.length == 1)){
				layer.msg('请只选中一条信息再进行编辑。');
				return false;
			}
			window.location.href = "<%=basePath%>agent/toEdit?SP_ID=" + ids[0];
		}
		//批量删除数据
		function toDel(){
			var ids = getBstCheckedId('btSelectItem');
			if((ids.length < 1)){
				layer.msg('请选中信息再进行删除。');
				return false;
			}
			var idsStr = ids.toString();
			$.ajax({
				type: "POST",
				contentType:"application/x-www-form-urlencoded",
				url: '<%=basePath%>api/message/message_manager_delete?tm=' +  new Date().getTime(),
				data: {
					IDS: idsStr
				},
				dataType: 'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data) {
					if (data == 'OK') {
						layer.msg('删除信息成功');
						bstQuery();
					} else {
						layer.msg('删除信息失败');
					}
				}
			});
			<%-- layer.confirm('确定删除已选信息吗？', {
				btn: ['确认','取消'],
				shade: false,
				yes: function(index, layero){
					$.ajax({
						type: "POST",
						contentType:"application/x-www-form-urlencoded",
						url: '<%=basePath%>agent/toDelete?tm=' +  new Date().getTime(),
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
			}); --%>
		}
		//获取选中的值
		function getBstCheckedId(param){  
		     var value =""; 
		        $('input:checkbox[name="'+param+'"]:checked').each(function(){
		    	   value+=$(this).val()+",";
		       });
		     return value;
		    } 
		//浏览
		function toView(SP_ID){
			if(SP_ID != null && SP_ID != ""){
				layer.full(layer.open({
					type: 2,
					title: '信息浏览',//窗体标题
					area: ['600px', '600px'],//整个窗体宽、高，单位：像素px
					fix: false,//窗体位置不固定
					maxmin: true,//最大、小化是否显示
					scrollbar: true,//整体页面滚动条是否显示
					content: ['/agent/toView?SP_ID=' + SP_ID, 'no'],//URL地址
					closeBtn: 1,//显示关闭按钮
					btn: ['关闭']
				}));
			}else{
				layer.msg("系统未获取到数据主键，请重新选择数据！");
			}
		}
		//操作
		function formatNAMEFun(value, row, index){
			var format_v = "<button type=\"button\" class=\"btn btn-link\" onclick=\"toView('"+row.SP_ID+"');\">" + row.SP_NAME + "</button>";
			return format_v;
		}
	</script>
</body>
</html>