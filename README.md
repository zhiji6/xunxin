# com.xunxin
循心项目进度表
———————————————————————————————————————
一、Client - 客户端，一般指浏览器，浏览器可以通过 HTTP 协议向服务器请求数据。
	-- IOS Client
		
	-- Web Client
		-- 架构 html css js jquery bootstrap
			-- 1.登录 login.html 
		   	-- 2.主页 index.html
		   	  -- 2.1 用户管理
		   	  	-- 2.1.1 用户列表 user_list.html
		   	  	-- 2.1.2 活跃用户管理 user_online.html
		   	  	-- 2.1.3 黑名单管理 user_black.html
		   	  	-- 2.1.4 用户审核 user_check.html;
		   	  -- 2.2 财务管理
		   	  	-- 2.2.1 充值记录管理 account_recharge_list.html		
		   	  -- 2.3 Q&A管理
		   	  	-- 2.3.1 问题管理 qa_question.html
		   	  -- 2.4 留言管理
		   	  	-- 2.4.1 留言列表 message_list.html
		   	  -- 2.5 消息推送
		   	  	-- 2.5.1 消息刘表 info_list.html	
		   	  -- 2.6 数据中心
		   	  	-- 2.6.1 数据中心 data_center.html	
		   	  	-- 2.6.2 平台账户管理 platform_Mutuality_list.html
		   	  -- 2.7 菜单管理
		   	    -- 2.7.1 菜单列表 menu_list.html	
		   	  -- 2.8 系统管理	
		   	  	-- 2.8.1 系统管理 system_managent.html	
		   	  	-- 2.8.2 系统监控 system_monitor.html	

———————————————————————————————————————

二、Server - 服务端，一般指 Web 服务器，可以接收客户端请求，并向客户端发送响应数据。
	-- pc端   http://host:port/api/*/*;
		-- pc View
			-- 登录 http://192.168.1.105/login;
			-- 首页 http://192.168.1.105/index;
			-- 退出 http://192.168.1.105/logout;
		-- pc controller
			-- 后台登录 http://192.168.1.105/api/login;
	-- app端  http://host:port/app-api/*/*;
		-- app controller
			-- 登录 http://192.168.1.105/app-api/login;

_______________________________________________________________________________
三、Business - 业务层， 通过 Web 服务器处理应用程序，如与数据库交互，逻辑运算，调用外部程序等。
-----------------------------------------------------------------------
	-- IOS
		-- 1.注册 register
			-- 1.1注册(接入环信SDK，调用环信API、阿里云短信SDK，同时将用户信息添加到循心平台下) 1
-----------------------------------------------------------------------
		-- 2.登录 login
			-- 2.1 登录(正常)	
			-- 2.2 第三方登录 
					-- QQ 腾讯开放平台 http://open.qq.com/
					-- 微信 微信开放平台 https://open.weixin.qq.com/
					-- 微博 新浪微博开放平台 http://open.weibo.com/
-----------------------------------------------------------------------
		-- 3.找回密码(短信SDK) recovered	
-----------------------------------------------------------------------
		-- 4.个人资料 personal information
			-- 4.1 基本资料 general information
				-- 4.1.1 可更改项(绿色)：6月后可更改一次或付费更改10元/项 
				         不可更改项(红色)：第二次更改时提示用户只有一次免费更改权利，此后联系客服。 个人资料一共29项 完成基本资料显示完成度为50%
				
				-- 4.1.2 真实姓名	realName		
				-- 4.1.3 昵称
					-- 排他性 汉字+个字母+数字 可混用 最多12字母 6个汉字 不允许符号
				-- 4.1.4 性别
					-- 男、女、跨性别者、性别模糊
				-- 4.1.5 年龄
				-- 4.1.6 性取向
					-- 男、女、性别模糊
				-- 4.1.7 身高
				-- 4.1.8 常住地
					-- 地区字典 area dictionary
				-- 4.1.9 职业
					-- 职业字典 profession dictionary
				-- 4.1.10 行业
					-- 行业字典 trade dictionary
				-- 4.1.11 职位
					-- 职位字典 position dictionary
				-- 4.1.12 收入
					-- 收入 区间分段
				-- 4.1.13 学历
					-- 枚举 小学、初中、高中、大专、本科、研究生、硕士、博士、博士后
				-- 4.1.14 毕业院校
					-- 学校字典 school dictionary
				-- 4.1.15 婚姻状态
					-- 枚举 未婚、已婚、分局、同居、离异
				-- 4.1.16 我正在寻找			
					-- 枚举 结婚对象|恋人(强制身份证验证)、知己、其他
			-- 4.2 详细资料	detailed information
				-- 4.2.1 个人独白+户籍完成度 +10% 其他每填写3项+完成10%
				
				-- 4.2.2 自我介绍 
					-- 4.2.2.1 独白  自填 80字以内，非强制项		
					-- 4.2.2.2 住房情况
						-- 枚举 租房、已购房、单位宿舍、与家人同住
					-- 4.2.2.3 自购交通工具	
						-- 自行车、电动车、摩托车、汽车、私人飞机
					-- 4.2.2.4 体重
					-- 4.2.2.5 民族		
						-- 枚举 56个名族
					-- 4.2.2.6 国籍
						-- 枚举 中国、美国、其他
					-- 4.2.2.7 籍贯		
						-- 地区字典 省-市-区
					-- 4.2.2.8 户籍
						-- 地区字典 省-市-区
					-- 4.2.2.9 家中排行
						-- 独生子女、老大、老二、老三、老四、老五及更小、老幺
					-- 4.2.2.10 有无子女
						-- 有小孩、有小孩归自己、有小孩归对方、无小孩	
					-- 4.2.2.11 宗教信仰
						-- 无宗教信仰、大乘佛教显宗、大乘佛教密宗、大乘佛教净宗、小乘佛教、道教、儒教、基督教天主教派、基督教东正教派、基督教新教派、犹太教、伊斯兰教什叶派、
							伊斯兰教逊尼派、印度教、神道教、萨满教、其他教派	
					-- 4.2.2.12 星座 
						-- 枚举 12个星座
					-- 4.2.2.13 属相
						-- 属相 12个属相
					-- 4.2.2.14 血型
						-- A型、B型、AB型、o型、RH型(阳性|阴性)、未知		
			-- 4.3 相册 personal	album	
				-- 限制上传10张照片	
-----------------------------------------------------------------------
						
		-- 5.认证中心
			-- 5.1 手机认证 Phone authentication
				-- 5.1.1 阿里云sdk短信认证
			-- 5.2 邮箱认证 Email authentication
				-- 5.2.1 发送邮件的工具类 SendMailUtil 
				-- 5.2.2 用户于邮箱中获取连接点击，并回调
			-- 5.3 实名认证 Icard authentication	
				-- 	5.3.1 https://market.aliyun.com/products/57000002/cmapi015837.html?spm=5176.730005-57000002.0.0.ajUWdq#sku=yuncode983700006
			-- 5.4 学历认证 Education authentication
				-- 5.4.1 证书编号 + 学信网截图
			-- 5.5 芝麻认证 Sesame authentication
				--	5.5.1 https://market.aliyun.com/products/57000002/cmapi017743.html?spm=5176.730005-57000002.0.0.ajUWdq#sku=yuncode1174300000
			-- 5.6 职业认证 Profession authentication
				-- 5.6.1 名片照片|社保缴费证明	

-----------------------------------------------------------------------
		-- 6.我的
			-- 6.1 我的动态
				-- 6.1.1 时间 + 内容 + 点赞数 
					-- 动态可删除，但不删除源数据
					   Q&A发布  覆盖
					   Q&A答题  覆盖
					   相册照片增删   不覆盖
					   个人资料修改，基本资料和详细资料  不覆盖
					   资料认证项目新增、变更	   不覆盖
					   共情圈发布的消息  不覆盖
					   对共情圈他人的评论  不覆盖
					   使用了广场的摇一摇  覆盖
					   使用了广场的丢绣球  覆盖
					   登上了广场的 排行榜  不覆盖
					   回复了广场的答疑解惑  不覆盖
					   使用了广场的转盘游戏  覆盖
					   使用了广场的爱情速配  覆盖
					   评论被置顶为固定选项  不覆盖
					   覆盖项目记录用户第一次使用为展示时间，重复操作项目更改动态数字。
			-- 6.2 我的Q&A
				-- 6.2.1 总量 500/2W+ 
					-- 6.2.1.1 我参与的
						-- 头像、昵称、问题名称、问题简介、答案选项、回答人数、查看答案比例
					-- 6.2.1.2 我发布的
				-- 6.2.2 收藏 100
					-- 列表、详情、编辑、删除
				-- 6.2.3 话题关注分布情况
					-- 柱状图 分布情况
			-- 6.3 我的关注
				-- 6.3.1 关注我的
					-- 头像、昵称、ID、在线状态、离线时长
				-- 6.3.2 我关注的	
					-- 头像、昵称、ID、在线状态、离线时长
			-- 6.4 我的福利
				-- 6.4.1 签到
			-- 6.5 我的积分 
				-- 6.5.1 提升等级
					-- 上月在线时长、平均在线时长、
				-- 6.5.2 自画像
					-- 初：1/天；中：2/天；高：5/天；
				-- 6.5.3 共情圈照片
					-- 初：10/天；中：20/天；高：30/天；
			-- 6.6 我的钱包
				-- 6.6.1 余额
				-- 6.6.2 充值
					-- 6.6.2.1 金额： 100、50、20、10
					-- 6.6.2.2 支付宝支付 alipay
					-- 6.6.2.3 微信支付	weixinpay
				-- 6.6.3 交易明细
					-- 6.6.3.1 全部
					-- 6.6.3.2 支出|收入
			-- 6.7 我的自画像 
				-- 6.7.1 个人资料(必有+自选模式)
				-- 6.7.2 个人兴趣点 标签
				-- 6.7.3 俩人契合度
			-- 6.8 扫一扫登录 //TODO
			-- 6.9 设置
				-- 6.9.1 消息屏蔽
					-- 6.9.1.1 已屏蔽用户列表
					-- 6.9.1.2 只接收认证较高的用户信息
					-- 6.9.1.3 只接收初级以上用户的信息
				-- 6.9.2 通知设置
					-- 6.9.2.1 消息提醒
					-- 6.9.2.2 免打扰
				-- 6.9.3 给循心好评
				-- 6.9.4 分享循心
					-- 每日首次分享奖励1积分
				-- 6.9.5 清楚缓存
				-- 6.9.6 用户反馈
					-- 图文提交
				-- 6.9.7 关闭资料
					-- 6.9.7.1 清空资料
					-- 6.9.7.1 彻底关闭
				-- 6.9.8 新手指南
				-- 6.9.9 关于循心
					-- 版本号、客服邮箱、微信公众号(二维码)

-----------------------------------------------------------------------


		-- 7.消息
			-- 7.1 好友
				-- 7.1.1 屏蔽
				-- 7.1.2 季报
			-- 7.2 消息
				-- 7.2.1 消息设置
					-- 7.2.1.1 只接收认证度较高的用户消息
					-- 7.2.1.2 只接收初级以上用户的自画像
				-- 7.2.2 聊天记录
					-- 7.2.2.1 语音
					-- 7.2.2.2 文字、表情
					-- 7.2.2.3 自画像
					-- 7.2.2.4 相册
					-- 7.2.2.5 位置
					-- 7.2.2.6 红包
			-- 7.3 此时此刻
				-- 附近的人(条件+)

-----------------------------------------------------------------------

		-- 8.共情圈
-----------------------------------------------------------------------
		-- 9.广场	
			-- 9.1 摇一摇 
			-- 9.2 撒狗粮
			-- 9.3 丢绣球
			-- 9.4 曝光栏
			-- 9.5 人间真情
			-- 9.6 排行榜
			-- 9.7 答疑解疑
			-- 9.8 擦肩而过  
			-- 9.9 转盘游戏
			-- 9.10 爱情速配
			-- 9.11 心灵诊所
			-- 9.12	机构帐号可以发布调研问卷
			-- 9.13 做社区活动 线下交友	

-----------------------------------------------------------------------

		-- 10.主页
			-- 10.1 搜索
			-- 10.2 板块
				-- 10.2.1 板块列表
			-- 10.3 编辑Q&A
			-- 10.4 快速添加观点
			-- 10.5 添加链接
			-- 10.6 分享

-----------------------------------------------------------------------

		-- 11.异常处理
			-- 11.1 注册
				-- 冒用身份由客服处理 只能由客服重启帐号
			-- 11.2 网络中断
				-- 11.2.1 告知已发送 后台继续处理 未成功通知客户重新发布
				-- 11.2.2 答题选择缓存在本地 有网络后同步服务器
			-- 11.3 举报
				-- 11.3.1 在任何版块被举处理流程一致 谁举报谁举证 由客服根据资料处理
 				-- 11.3.2 举报事项
 					-- 11.3.2.1 由后台客服根据规则扣分
    				-- 11.3.2.2 各种托
    				-- 11.3.2.3 营销诈骗
    				-- 11.3.2.4 涉黄赌毒
    				-- 11.3.2.5 虚假资料
    				-- 11.3.2.6 抄袭我的内容
      				-- 11.3.2.7 链接
    				-- 11.3.2.8 辱骂、歧视、挑衅等不友善行为
    				-- 其他

_______________________________________________________________________

四、Data -	数据层，一般由数据库组成。

	-- MYSQL
		-- xunxin_admin
		-- xunxin_admin_menu
		-- xunxin_admin_permission
		-- xunxin_admin_resource
		-- xunxin_data_platform_managent
		-- xunxin_role
		-- xunxin_sys_area
		-- xunxin_sys_city
		-- xunxin_sys_pay_bank
		-- xunxin_sys_pay_thirdpay
		-- xunxin_sys_province
		-- xunxin_user
		-- xunxin_user_account
		-- xunxin_user_account_recharge
	-- mongoDB
		-- xunxin_system_log
