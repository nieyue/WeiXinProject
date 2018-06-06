package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客服消息文章
 * @author yy
 *
 */
@ApiModel(value="客服消息文章",description="客服消息文章")
@TableName("kf_article_tb")
public class KfArticle implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 客服消息文章id
     */
    @ApiModelProperty(value="客服消息文章id",example="客服消息文章id")
    @TableId("kf_article_id")
    private Long kfArticleId;

    /**
     * 图文消息的标题
     */
    @ApiModelProperty(value="图文消息的标题",example="图文消息的标题")
    private String title;
    /**
     * 图文消息的描述
     */
    @ApiModelProperty(value="图文消息的描述",example="图文消息的描述")
    private String description;
    /**
     * 图文消息被点击后跳转的链接
     */
    @ApiModelProperty(value="图文消息被点击后跳转的链接",example="图文消息被点击后跳转的链接")
    private String url;
    /**
     *图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
     */
    @ApiModelProperty(value="图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80",example="图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80")
    private String picurl;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",example="创建时间")
    private Date createDate;
    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间",example="更新时间")
    private Date updateDate;
    /**
     * 客服消息id外键
     */
    @ApiModelProperty(value="客服消息id外键",example="客服消息id外键")
    private Long kfMessageId;
	public Long getKfArticleId() {
		return kfArticleId;
	}
	public void setKfArticleId(Long kfArticleId) {
		this.kfArticleId = kfArticleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
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
	public Long getKfMessageId() {
		return kfMessageId;
	}
	public void setKfMessageId(Long kfMessageId) {
		this.kfMessageId = kfMessageId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
