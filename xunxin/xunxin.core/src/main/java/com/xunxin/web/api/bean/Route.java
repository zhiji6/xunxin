package com.xunxin.web.api.bean;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月2日 -- 上午1:25:06
 * @Version 1.0
 * @Description	后台管理系统  RouteBus 接口总线
 */
public class Route {

	public static final String PATH = "/api";
	
	public static final String LOGIN = "/login";
	public static final String INDEX = "/index";
	public static final String MAIN = "/main";
	public static final String INCLUDE = "/include";
	public static final String LOGOUT = "/logout";
	
	//系统
	public class Admin {
		public static final String PATH = "/admin";
		
		public static final String LOGIN_TO_INDEX = "/loginToIndex";
	}
	
	//认证中心
	public class Auth {
		public static final String PATH = "/auth";
		
		public static final String AUTHENTICATION_MANAGER = "/authentication_manager";		//认证信息管理
		public static final String PROFESSION_AUTHENTICATION = "/profession_authentication";		//职业认证
		public static final String EDUCATION_AUTHENTICATION = "/education_authentication";		//学历认证
		
		public static final String AUTHENTICATION_MANAGER_LIST = "/authentication_manager_list";	//认证信息管理列表
		
		public static final String AUTHENTICATION_MANAGER_TO_EDIT = "/authentication_manager_to_edit";	//认证信息去修改
		
		public static final String AUTHENTICATION_MANAGER_EXAMINE = "/authentication_manager_examine";	//认证信息去审核
		
		public static final String  QUERY_DICT= "/query_dict";	//查找字典列表
	}
	
	//系统
	public class System {
		public static final String PATH = "/system";
		
		public static final String MENU_TREE = "/menu_tree";	//获取菜单树
		public static final String MENU_SUB_TREE = "/getSubTree";	//获取菜单树
		public static final String MENU_MANAGER = "/menu_manager";		//菜单管理
		public static final String SYSTEM_MONITOR = "/system_monitor";	//系统监控
		public static final String SYSTEM_API_MANAGER = "/system_api_manager";	//接口管理
		public static final String SYSTEM_MANAGER = "/system_manager";	//系统管理
		public static final String SHOW_API_DOCUMENTS = "/show_api_documents";	//系统接口列表
		public static final String SYSTEM_API_ADD = "/system_api_add";	   //接口新增
		public static final String SYSTEM_API_EDIT = "/system_api_edit";	//接口修改
		public static final String SYSTEM_API_VIEW = "/system_api_view";	//浏览接口
		public static final String ADD_API_DOCUMENT = "/add_api_document";		//新增接口
		public static final String EDIT_API_DOCUMENT = "/edit_api_document";	//编辑接口
		public static final String USER_MANAGER = "/user_manager";		//角色管理
		public static final String ADMIN_LIST = "/admin_list";		//系统管理人员列表
		public static final String TO_ADMIN_ADD = "/to_admin_add";		//系统管理新增页
		public static final String ADMIN_VIEW = "/admin_view";		//系统管理详情
		public static final String ADMIN_ADD = "/admin_add";		//系统管理新增
		public static final String ADMIN_EDIT = "/admin_edit";		//系统管理修改
		public static final String ADMIN_DELETE = "/admin_delete";		//删除管理员
	}
	
	//Excel文件导入导出
	public class ExcelPoi {
		public static final String PATH = "/excelPoi";
		
		public static final String IMPORT_QUESTIONVO_EXCEL = "/import_questionVO_excel";	//导入QA问题的Excel
	}
	
	
	//财务
	public class Payment {
		public static final String PATH = "/pay";
		
		public static final String ACCOUNT_RECHARGE_MANAGER = "/account_recharge_manager";	//充值记录管理
		public static final String ACCOUNT_CONSUME_MANAGER = "/account_consume_manager";	//消费记录管理
		public static final String CONSUME_LIST = "/consume_list";	    //交易明细管理
		public static final String RECHARGE_LIST = "/recharge_list";	//平台充值记录
		
		public static final String ALI_PAY = "/ali_pay";		//支付宝APP支付
		public static final String ALI_PAY_H = "/ali_pay_h";		//支付宝APP支付
		public static final String ALI_PAY_NOTIFY = "/alipay_notify";	//支付宝异步通知
		public static final String ALI_PAY_QUERY = "/alipay_query";		//支付宝订单查询
		public static final String ALI_PAY_TRANSFER = "/alipay_transfer";	//支付宝单笔提现
		public static final String ALI_SCAN_PAY = "/ali_scan_pay";		//支付宝扫码付
		public static final String WEIXIN_PAY = "/weixin_pay";			//微信APP支付
		public static final String WEIXIN_PAY_NOTIFY = "/weixin_notify";	//微信异步通知
		public static final String WEIXIN_PAY_QUERY = "/weixin_query";		//微信订单查询

		public static final String ORDER_VIEW = "/order_view";		//订单详情
		public static final String ORDER_EDIT = "/order_edit";		//订单修改
		public static final String ORDER_DELETE = "/order_delete";		//订单删除
		
		public static final String WEIXIN_PAY_H = "/weixin_pay_h";			//微信h5支付
		public static final String WEIXIN_PAY_NOTIFY_H = "/weixin_notify_h";	//微信h5异步通知

		
		public static final String VERIFY_ORDER = "/verify_order";		//验证IOS内购订单
		
	}
	

	//数据中心
	public class Data {
		public static final String PATH = "/data";
		
		public static final String FINDALL = "/findAll";
		public static final String PLATFORM_MUTUALITY_LIST = "/platform_Mutuality_list";	//平台中心
		public static final String DATA_CENTER = "/data_center";	//数据中心
	}
	
	
	//积分中心
	public class Exp {
		public static final String PATH = "/exp";
		
		public static final String USER_EXP_MANAGER = "/userExp_manager";	//用户积分管理
		public static final String USER_EXP_RUNNING = "/userExp_running";	//用户流水管理
	}
	
	
	//QA管理中心
	public class QA {
		public static final String PATH = "/qa";
		public static final String PUBLISH_QA_TO_REPERTORY = "/publish_qa_to_repertory";	//编辑添加 Q&A(新增)
		public static final String UPDATE_QA_TO_REPERTORY = "/update_qa_to_repertory";	//编辑修改 Q&A(修改)
		public static final String DELETE_QA_TO_REPERTORY = "/delete_qa_to_repertory";	//删除 Q&A(修改)
		public static final String QA_HOT_TOPIC = "/qa_hot_topic";	//提升为热点话题
		public static final String FULL_QA_LIST = "/full_qa_list";	//Q&A列表
		public static final String ADD_ANSWER = "/add_answer";	//添加观点
		public static final String UEDITOR_LOADING = "/ueditor_loading";	//UEditor编辑器初始化
		public static final String QA_VIEW = "/qa_view";	//Q&A新增
		public static final String SPIDER_LINKED_URL = "/spider_linked_url";	//获取链接摘要 TODO 爬虫
		public static final String QA_ADD = "/qa_add";	//Q&A新增
		public static final String QA_EDIT = "/qa_edit";	//Q&A修改
		public static final String QA_DELETE = "/qa_delete";	//Q&A删除
		public static final String QA_AUDIT_MANAGER = "/qa_audit_manager";	//Q&A审核
		public static final String QA_EXCEL_IMPORT = "/qa_excel_import";	//跳转Q&A上传Excel页
		public static final String UPLOAD_EXCEL = "/upload_excel";		//Q&A上传Excel
		public static final String IMPORT_EXCEL_DATEBASE = "/import_excel_database";	//导入上传Excel的数据
		public static final String FULL_QA_SECTION_LIST = "/full_qa_section_list";		//获取板块列表
		public static final String ADD_TAG = "/add_tag";		//获取板块列表
		public static final String QA_QUESTION_MANAGER = "/qa_question_manager";		//获取板块列表
	    public static final String QA_AUDIT_MANAGER_LIST = "/qa_audit_manager_list";	//Q&A审核列表
		
		public static final String QA_AUDIT_MANAGER_TO_EXAMINE = "/qa_audit_manager_to_examine";	//Q&A审核列表
		
		public static final String QA_AUDIT_MANAGER_EXAMINE = "/qa_audit_manager_examine";	//Q&A审核
	}
	
	
	//留言管理中心
	public class Message {
		public static final String PATH = "/message";
		
		public static final String MESSAGE_MANAGER = "/message_manager";	//留言列表
		
		public static final String MESSAGE_MANAGER_LIST = "/message_manager_list";	//留言列表
		
		public static final String MESSAGE_MANAGER_DELETE = "/message_manager_delete";	//留言列表
		public static final String MESSAGE_INTERACTION = "/message_interaction";	//留言互动
		
		public static final String EMPATHY_CIRCLE_LIST = "/empathy_circle_list";	//共情圈列表
		
		public static final String EMPATHY_CIRCLE_TO_EDIT = "/empathy_circle_to_edit";	//共情圈查看详情
		
		public static final String EMPATHY_CIRCLE_EDIT = "/empathy_circle_list_edit";	//共情圈查看详情
		
		public static final String EMPATHY_CIRCLE_REPLY_DELETE = "/empathy_circle_reply_delete";	//共情圈留言删除
		
	}
	
	//留言管理中心
	public class Info {
		public static final String PATH = "/info";
		
		public static final String SYSTEM_INFO_MANAGER = "/system_info_manager";	//系统消息管理(推送)
		public static final String USER_INFO_MANAGER = "/user_info_manager";	//用户消息管理(推送)
		public static final String USER_FEEDBACK = "/user_feedback";		//用户反馈管理
		public static final String EASEMOB_USERS_MANAGER = "/easemob_users_manager";	//用户体系管理(环信) 
		
		public static final String EASEMOB_USERS_MANAGER_LIST = "/easemob_users_manager_list"; //用户体系列表
		
		public static final String EASEMOB_USERS_MANAGER_TO_EDIT = "/easemob_users_manager_to_edit"; //用户体系去编辑
		
		public static final String EASEMOB_USERS_MANAGER_RESTRUCTURE = "/easemob_users_manager_restructure"; //用户体系去编辑
		
		public static final String EASEMOB_CHAT_MESSAGES_MANAGER = "/easemob_chat_messages_manager";	//用户聊天记录管理(环信)
		
		public static final String EASEMOB_CHAT_MESSAGES_MANAGER_LIST = "/easemob_chat_messages_manager_list";	//用户聊天记录列表
		public static final String EASEMOB_CHAT_FILES_MANAGER = "/";	//文件管理(环信)
	}
	
	//用户管理
	public class User {
		public static final String PATH = "/user";
		
		public static final String USER_MANAGER = "/user_manager";		//用户列表
		public static final String USER_ADD = "/user_add";		//用户列表
		public static final String ADD_EXP = "/add_exp";		//用户充能
		public static final String USER_ADD_EXP = "/user_add_exp";		//用户充能
		public static final String USER_EDIT = "/user_view";		//用户列表
		public static final String USER_LIST = "/user_list";		   //用户列表
		public static final String USER_VIEW = "/user_view";		   //用户详情
		public static final String USER_ONLINE = "/user_online";		//活跃用户管理
		public static final String USER_BLACK = "/user_black";			//黑名单管理
		public static final String VOLUTEER_MANAGER = "/volunteer_manager";		//志愿者管理
		public static final String USER_PAY = "/user_pay";	//跳转用户支付页面
		public static final String WEIXIN_PAY_RETURN = "/weixin_pay_return"; //微信h5支付返回
	}
	
	//公共sdk api
	public class Common {
		public static final String PATH = "/util";
		
		public static final String LOGIN_SECURITY_CODE = "/login-security-code";
		public static final String HELLO = "/hello";
		public static final String SEND_REGISTER_SMS = "/send_register_sms";
		public static final String SEND_FORGOT_SMS = "/send_forgot_sms";
	}
	
	
}
