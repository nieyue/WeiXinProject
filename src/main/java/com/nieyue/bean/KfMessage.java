package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客服消息
 * @author yy
 *
 */
@ApiModel(value="客服消息",description="客服消息")
@TableName("kf_message_tb")
public class KfMessage implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 客服消息id
     */
    @ApiModelProperty(value="客服消息id",example="客服消息id")
    @TableId("kf_message_id")
    private Long kfMessageId;

    /**
     * 消息类型，text文本消息，image图片消息，voice语音消息，video视频消息，music音乐消息，news图文消息（外链），mpnews图文消息（微信链接），wxcard发送卡券，miniprogrampage小程序
     */
    @ApiModelProperty(value="消息类型，text文本消息，image图片消息，voice语音消息，video视频消息，music音乐消息，news图文消息（外链），mpnews图文消息（微信链接），wxcard发送卡券，miniprogrampage小程序",example="消息类型，text文本消息，image图片消息，voice语音消息，video视频消息，music音乐消息，news图文消息（外链），mpnews图文消息（微信链接），wxcard发送卡券，miniprogrampage小程序")
    private String msgtype;
    /**
     * 文本消息内容
     */
    @ApiModelProperty(value="文本消息内容",example="文本消息内容")
    private String content;
    /**
     * 发送的图片/语音/视频/图文消息（点击跳转到图文消息页）的媒体ID
     */
    @ApiModelProperty(value="发送的图片/语音/视频/图文消息（点击跳转到图文消息页）的媒体ID",example="发送的图片/语音/视频/图文消息（点击跳转到图文消息页）的媒体ID")
    private String mediaId;
    /**
     * 缩略图/小程序卡片图片的媒体ID，小程序卡片图片建议大小为520*416
     */
    @ApiModelProperty(value="缩略图/小程序卡片图片的媒体ID，小程序卡片图片建议大小为520*416",example="缩略图/小程序卡片图片的媒体ID，小程序卡片图片建议大小为520*416")
    private String thumbMediaId;
    /**
     * 视频消息/音乐消息/小程序卡片的标题
     */
    @ApiModelProperty(value="视频消息/音乐消息/小程序卡片的标题",example="视频消息/音乐消息/小程序卡片的标题")
    private String title;
    /**
     * 视频消息/音乐消息的描述
     */
    @ApiModelProperty(value="视频消息/音乐消息的描述",example="视频消息/音乐消息的描述")
    private String description;
    /**
     * 音乐链接
     */
    @ApiModelProperty(value="音乐链接",example="音乐链接")
    private String musicurl;
    /**
     * 高品质音乐链接，wifi环境优先使用该链接播放音乐
     */
    @ApiModelProperty(value="高品质音乐链接，wifi环境优先使用该链接播放音乐",example="高品质音乐链接，wifi环境优先使用该链接播放音乐")
    private String hqmusicurl;
    /**
     * 卡卷id
     */
    @ApiModelProperty(value="卡卷id",example="卡卷id")
    private String cardId;
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
     * 客服消息更新时间
     */
    @ApiModelProperty(value="客服消息更新时间",example="客服消息更新时间")
    private Date updateDate;
    /**
     * 公众号id外键
     */
    @ApiModelProperty(value="公众号id外键",example="公众号id外键")
    private Long subscriptionId;
	public Long getKfMessageId() {
		return kfMessageId;
	}
	public void setKfMessageId(Long kfMessageId) {
		this.kfMessageId = kfMessageId;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
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
	public String getMusicurl() {
		return musicurl;
	}
	public void setMusicurl(String musicurl) {
		this.musicurl = musicurl;
	}
	public String getHqmusicurl() {
		return hqmusicurl;
	}
	public void setHqmusicurl(String hqmusicurl) {
		this.hqmusicurl = hqmusicurl;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
