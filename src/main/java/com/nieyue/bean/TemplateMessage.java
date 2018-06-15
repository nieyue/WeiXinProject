package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 模板消息
 * @author yy
 *
 */
@ApiModel(value="模板消息",description="模板消息")
@TableName("template_message_tb")
public class TemplateMessage implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 模板消息id
     */
    @ApiModelProperty(value="模板消息id",example="模板消息id")
    @TableId("template_message_id")
    private Long templateMessageId;

    /**
     * 模板id
     */
    @ApiModelProperty(value="模板id",example="模板id")
    private String teamplateId;
    /**
     * 模板标题（必填），与公众号一致
     */
    @ApiModelProperty(value="模板标题（必填），与公众号一致",example="模板标题（必填），与公众号一致")
    private String title;
    /**
     * 模板内容（可选），与公众号一致，只做展示
     */
    @ApiModelProperty(value="模板内容（可选），与公众号一致，只做展示",example="模板内容（可选），与公众号一致，只做展示")
    private String content;
    /**
     * 模板跳转链接
     */
    @ApiModelProperty(value="模板跳转链接",example="模板跳转链接")
    private String url;
    /**
     * 小程序的appid，要求小程序的appid需要与公众号有关联关系
     */
    @ApiModelProperty(value="小程序的appid，要求小程序的appid需要与公众号有关联关系",example="小程序的appid，要求小程序的appid需要与公众号有关联关系")
    private String appid;
    /**
     * 小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar
     */
    @ApiModelProperty(value="小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar",example="小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar")
    private String pagepath;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",example="创建时间")
    private Date createDate;
    /**
     * 模板消息更新时间
     */
    @ApiModelProperty(value="模板消息更新时间",example="模板消息更新时间")
    private Date updateDate;
    /**
     * 公众号id外键
     */
    @ApiModelProperty(value="公众号id外键",example="公众号id外键")
    private Long subscriptionId;
	public Long getTemplateMessageId() {
		return templateMessageId;
	}
	public void setTemplateMessageId(Long templateMessageId) {
		this.templateMessageId = templateMessageId;
	}
	public String getTeamplateId() {
		return teamplateId;
	}
	public void setTeamplateId(String teamplateId) {
		this.teamplateId = teamplateId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPagepath() {
		return pagepath;
	}
	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
