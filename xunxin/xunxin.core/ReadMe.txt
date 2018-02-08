1.转盘游戏的算法


2.用户匹配规则

rz@xunxinkeji.cn           XXKj.0000 认证邮箱

*** 新闻媒体网站排行榜  ***


*********************************************************************************
	PC端的新闻
*********************************************************************************
	人民网  				|				http://people.com.cn/
	电视猫 				|		 		http://www.tvmao.com/
	央视网  				|				http://www.cctv.com/
	新华网				|				http://xinhuanet.com/
	中国网	 			|				http://www.china.com.cn/
	红网					|				http://www.rednet.cn/index.html	
	今日头条 			|				https://www.toutiao.com/
	腾讯新闻 			|				http://news.qq.com/
	搜狐新闻				|				http://news.sohu.com/
	网易新闻				|				http://news.163.com/
	百度新闻				|				http://news.baidu.com/
	凤凰新闻				|				http://news.ifeng.com/
	新浪新闻				|				http://news.sina.com.cn/
*********************************************************************************

-------------------------------------------------------------------------------------------------

*********************************************************************************
	APP端的新闻
*********************************************************************************
	今日头条 			|				https://www.toutiao.com/
	腾讯新闻 			|				http://news.qq.com/
	搜狐新闻				|				http://news.sohu.com/
	凤凰新闻				|				http://news.ifeng.com/
	新浪新闻				|				http://news.sina.com.cn/
	豆瓣 				|				http://douban.com.cn/
	知乎 				|				http://zhihu.com.cn/
	其他 				|				http://zhihu.com.cn/
*********************************************************************************

        if(linked_url.contains("people")){
            return "人民网";
        }
        if(linked_url.contains("tvmao")){
            return "电视猫";
        }
        if(linked_url.contains("cctv")){
            return "央视网";
        }
        if(linked_url.contains("xinhua")){
            return "新华网";
        }
        if(linked_url.contains("china")){
            return "中国网";
        }
        if(linked_url.contains("rednet")){
            return "红网";
        }
        if(linked_url.contains("toutiao")){
            return "今日头条";
        }
        if(linked_url.contains("qq")){
            return "腾讯新闻";
        }
        if(linked_url.contains("sohu")){
            return "搜狐新闻";
        }
        if(linked_url.contains("163")){
            return "网易新闻";
        }
        if(linked_url.contains("baidu")){
            return "百度新闻";
        }
        if(linked_url.contains("ifeng")){
            return "凤凰新闻";
        }
        if(linked_url.contains("sina")){
            return "新浪新闻";
        }
        if(linked_url.contains("douban")){
            return "豆瓣";
        }
        if(linked_url.contains("zhihu")){
            return "知乎";
        }
        
        return "其他";
    }