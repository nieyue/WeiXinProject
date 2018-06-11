package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 公众号
 * @author yy
 *
 */
@ApiModel(value="公众号",description="公众号")
@TableName("subscription_tb")
public class Subscription implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 公众号id
     */
    @ApiModelProperty(value="公众号id",example="公众号id")
    @TableId("subscription_id")
    private Long subscriptionId;

    /**
     * 公众号名
     */
    @ApiModelProperty(value="公众号名",example="公众号名")
    private String name;
    /**
     * 公众号应用id
     */
    @ApiModelProperty(value="公众号应用id",example="公众号应用id")
    private String appid;
    /**
     * 公众号秘钥
     */
    @ApiModelProperty(value="公众号秘钥",example="公众号秘钥")
    private String appsecret;
    /**
     * 微信接口令牌
     */
    @ApiModelProperty(value="微信接口令牌",example="微信接口令牌")
    private String token;
    /**
     * 公众号原始id
     */
    @ApiModelProperty(value="公众号原始id",example="公众号原始id")
    private String ghid;
    /**
     * 公众号二维码
     */
    @ApiModelProperty(value="公众号二维码",example="公众号二维码")
    private String imgAddress;
    /**
     * 公众号商户id
     */
    @ApiModelProperty(value="公众号商户id",example="公众号商户id")
    private String mchid;
    /**
     * 公众号商户秘钥key
     */
    @ApiModelProperty(value="公众号商户秘钥key",example="公众号商户秘钥key")
    private String mchkey;
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
     * 账户id外键
     */
    @ApiModelProperty(value="账户id外键",example="账户id外键")
    private Long accountId;
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getGhid() {
		return ghid;
	}
	public void setGhid(String ghid) {
		this.ghid = ghid;
	}
	public String getImgAddress() {
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getMchkey() {
		return mchkey;
	}
	public void setMchkey(String mchkey) {
		this.mchkey = mchkey;
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
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
