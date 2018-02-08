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
						<h5>订单详情: ${pd.orderNo}</h5>
					</div>
					<div class="ibox-content">
                        <form id="qaDetailsForm" name="qaDetailsForm" class="form-horizontal" method="post">
                            <input type="hidden" name="id" id="id" value="${pd.id}" />
                            <div class="form-group">
                                <label class="col-sm-2 control-label">订单号：</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control required" name="orderNo" id="orderNo" value="${pd.orderNo}"  disabled="true" "  />
                                </div>
                                <label class="col-sm-2 control-label">用户昵称：</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control required" name="nickName" id="nickName" value="${pd.nickName}"  disabled="true" "  />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">充值类型：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="thirdPayId" id="thirdPayId" disabled="true" >${pd.thirdPayId}</div>     
                                </div>
                                <label class="col-sm-2 control-label">订单交易前：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="orderBefore" id="orderBefore" disabled="true" >${pd.orderBefore}</div>     
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">充值金额：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="tradeAmount" id="tradeAmount" disabled="true" >${pd.tradeAmount}</div>     
                                </div>
                                <label class="col-sm-2 control-label">订单交易后：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="orderEnd" id="orderEnd" disabled="true" >${pd.orderEnd}</div>     
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">下单时间：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="orderTime" id="orderTime" disabled="true" >${pd.orderTime}</div>     
                                </div>
                                <label class="col-sm-2 control-label">到账时间：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="orderAccountingTime" id="orderAccountingTime" disabled="true" >${pd.orderAccountingTime}</div>     
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">备注：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="remark" id="remark" disabled="true" >${pd.remark}</div>     
                                </div>
                                <label class="col-sm-2 control-label">订单状态：</label>
                                <div class="col-sm-2">
                                    <div type="text" class="form-control required" name="orderState" id="orderState" disabled="true" >${pd.orderState}</div>     
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