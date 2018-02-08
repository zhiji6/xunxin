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
                        <h5>订单管理</h5>
                    </div>
                    <div class="ibox-content">
                        <div id="toolbar" class="btn-group">
                            <div class="pull-left form-inline form-group">
                                <input type="text" id="orderNo" name="orderNo" class="form-control" placeholder="订单号" />
                                <select class="form-control" name="orderState" id="orderState" value="${pd.orderState}"
                                    ajax--url="api/qa/full_qa_status" ajax--column="ID,TEXT" style="width:140px;">
                                    <option value="">--订单状态--</option>
                                    <option value="0"> 已到账 </option>
                                    <option value="1"> 未到账 </option>
                                </select>
                                <button type="button" class="btn btn-default btn-primary" onclick="bstQuery();">
                                                                    查询
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
            url: 'api/pay/recharge_list?tm=' + new Date().getTime(),
            toolbar:'#toolbar',
            method:'post', 
            columns: [{
                field: 'id',
                visible: false,
                halign: 'center'
            }, {
                field: 'orderNo',
                title: '订单编号',
                align: 'center',
                halign: 'center',
                width: '15%',
                formatter: formatNAMEFun
            }, {
                field: 'nickName',
                title: '用户昵称',
                align: 'center',
                halign: 'center',
                width: '15%'
            }, {
                field: 'thirdPayId',
                title: '交易类型',
                align: 'center',
                halign: 'center',
                width: '10%',
                formatter: formatStatus
            }, {
                field: 'orderBefore',
                title: '订单交易前',
                align: 'center',
                halign: 'center',
                width: '20%'
            }, {
                field: 'tradeAmount',
                title: '交易金额',
                //visible: false,
                align: 'center',
                halign: 'center',
                width: '15%'
            }, {
                field: 'orderEnd',
                title: '订单交易后',
                align: 'center',
                halign: 'center',
                width: '15%'
                //formatter: formatlastLoginTime
            }, {
                field: 'orderTime',
                title: '下单时间',
                align: 'center',
                halign: 'center',
                width: '20%'
            }, {
                field: 'orderAccountingTime',
                title: '到账时间',
                align: 'center',
                halign: 'center',
                width: '20%'
            }, {
                field: 'remark',
                title: '备注',
                align: 'center',
                halign: 'center',
                width: '20%'
            }, {
                field: 'orderState',
                title: '订单状态',
                //visible: false,
                align: 'center',
                halign: 'center',
                width: '10%',
                formatter: formatHiden
            }]
        };
        $(document).ready(function (){
            var msg = "";
            if(msg != null && msg != ""){
                if(msg == '200'){
                    layer.msg("成功编辑代理商信息", {time:3000});
                } else if(msg == 'successEdit'){
                    layer.msg("成功编辑代理商信息");
                }else{
                    layer.msg("代理商信息编辑失败！");
                }
            }
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
        //跳转到编辑页面
        function toEdit(){
            var ids = getBstCheckedId('id');
            if(!(ids.length == 1)){
                layer.msg('请只选中一条信息再进行编辑。');
                return false;
            }
            window.location.href = "<%=basePath%>api/pay/order_edit?id=" + ids[0];
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
                        url: '<%=basePath%>api/pay/order_delete?tm=' +  new Date().getTime(),
                        data: {
                            IDS: idsStr
                        },
                        dataType: 'json',
                        //beforeSend: validateData,
                        cache: false,
                        success: function(data) {
                            if (data.meta.message == 'ok') {
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
                        title: '管理员详情',//窗体标题
                        area: ['600px', '600px'],//整个窗体宽、高，单位：像素px
                        fix: false,//窗体位置不固定
                        maxmin: true,//最大、小化是否显示
                        scrollbar: true,//整体页面滚动条是否显示 
                        content: ['/api/pay/order_view?id=' + id],//URL地址
                        closeBtn: 1,//显示关闭按钮
                        btn: ['关闭']
                    })
                );
            }else{
                layer.msg("系统未获取到数据主键，请重新选择数据！");
            }
        }

        //操作
        function formatNAMEFun(value, row, index){
            var format_v = "<button type=\"button\" class=\"btn btn-link\" onclick=\"toView('"+row.id+"');\">" + row.orderNo + "</button>";
            return format_v;
        }
        //时间
        function formatDataTime(value, row, index){
            var d = new Date(row.createTime);  
            var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-')   
                    + '  ' + [ d.getHours(), d.getMinutes(), d.getSeconds() ].join(':');  
            return dformat;  
        }
        //时间
        function formatlastLoginTime(value, row, index){
            var d = new Date(row.lastLoginTime);  
            var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-')   
                    + '  ' + [ d.getHours(), d.getMinutes(), d.getSeconds() ].join(':');  
            return dformat;  
        }
        //话题状态  /**  * 问题状态: *  0:未审核  1:审核通过 2:驳回处理 3:其他    */
        function formatStatus(value, row, index){
            var d = '';  
            if(row.thirdPayId == 19){ d = '微信'; }
            if(row.thirdPayId == 18){ d = '支付宝'; }
            return d;  
        }
        //是否冻结  /**  * 状态: *  0:已到账 | 1:未到账      */
        function formatHiden(value, row, index){
            var d = '';  
            if(row.orderState == 0){ d = '已到账'; }
            if(row.orderState == 1){ d = '未到账'; }
            return d;  
        }
        
        
    </script>
</body>
</html>