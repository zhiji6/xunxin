package com.xunxin.vo.qa;

import java.util.Date;

import org.mongodb.framework.pojo.GeneralBean;
/**
 * 题库，保存于mongodb数据库
 * @author rambo
 *
 */
public class QuestionVO extends GeneralBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 204184354874044213L;
	
	/** 问题名称  */
	private String name;
	
	/** 问题ID  */
	private String indexNo;

	/** 问题内容  */
	private String content;

	/** 答案列表（json格式，如{a:"答案1",b:"答案2",c:"答案3"}）  */
	private String answers;
	
	/** 标签列表（json格式，如{a:"标签1",b:"标签2",c:"标签3"}）  */
	private String tags;
	
	/** 图片列表（json格式，如{a:"图片1",b:"图片2",c:"图片3"}）  */
	private String img_urls;
	
	/** 外链记录id）  */
	private String linked_recordId;
	
	/**
	 * 问题状态: 
	 * 	0:未审核  1:审核通过 2:驳回处理 3:其他 
	 */
	private short status;
	
	
	/**
	 * 问题状态: 
	 * 	1:第一次审核  2:第二次审核  
	 */
	private short examineNum;
	/**
	 * 问题类型: 
	 * 	0:新闻 1:模块1 2:模块2 ...
	 * 
	 * 可以分热点科技 财经 社会 体育 娱乐 历史 人文 游戏 时尚家庭 两性 音乐 影视 医药 生物 心理学  职场 摄影 美食 法律 动漫 法律 健身 家具 政治  房地产  自然      等等  
	 * ps:数据在数据字段配置
	 */
	private short type;
	
	/** 编辑人类型：（0：用户，1：后台编辑 后台编辑所提问题无需审核）  */
	private short editorType;

	/** 新闻标题  */
	private String title;

	/** 问题发布人id  */
	private int userID;

	/** 问题审核人id  */
	private String auditorID;
	
	/** 是否热点话题  */
	private int isHot;

	/** 问题发布时间，审核通过，正式推送  */
	private Date releaseTime;
	/** 编辑器中HTML文本  */
	private String HtmlContent;
	//发布人昵称
	private String nickName;
	//板块名称
	private String typeName;
	private String $ne;
	/** 备注  */
	private String remark;
	//置顶次数
	private Integer topNum=0;
	
	public Integer getTopNum() {
		return topNum;
	}
	public void setTopNum(Integer topNum) {
		this.topNum = topNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAuditorID() {
		return auditorID;
	}
	public void setAuditorID(String auditorID) {
		this.auditorID = auditorID;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	
	public short getEditorType() {
		return editorType;
	}
	public void setEditorType(short editorType) {
		this.editorType = editorType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public short getExamineNum() {
		return examineNum;
	}
	public void setExamineNum(short examineNum) {
		this.examineNum = examineNum;
	}
	public String getLinked_recordId() {
		return linked_recordId;
	}
	public void setLinked_recordId(String linked_recordId) {
		this.linked_recordId = linked_recordId;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getImg_urls() {
		return img_urls;
	}
	public void setImg_urls(String img_urls) {
		this.img_urls = img_urls;
	}
	@Override
	public String toString() {
		return "name:"+name+",content:"+content+",answers:"+answers;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String get$ne() {
		return $ne;
	}
	public void set$ne(String $ne) {
		this.$ne = $ne;
	}
    public int getIsHot() {
        return isHot;
    }
    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }
    public String getIndexNo() {
        return indexNo;
    }
    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }
    public String getHtmlContent() {
        return HtmlContent;
    }
    public void setHtmlContent(String htmlContent) {
        HtmlContent = htmlContent;
    }
	
	
	
	
}
