package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 签到
 * @author yy
 *
 */
@ApiModel(value="签到",description="签到")
@TableName("sign_tb")
public class Sign implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 签到id
     */
    @ApiModelProperty(value="签到id",example="签到id")
    @TableId("sign_id")
    private Long signId;

    /**
     * 连续天数
     */
    @ApiModelProperty(value="连续天数",example="连续天数")
    private Integer dayNumber;
    /**
     * 总积分
     */
    @ApiModelProperty(value="总积分",example="总积分")
    private Double integral;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",example="创建时间")
    private Date createDate;
    /**
     * 签到更新时间
     */
    @ApiModelProperty(value="更新时间",example="更新时间")
    private Date updateDate;
    /**
     * 公众号id外键
     */
    @ApiModelProperty(value="公众号id外键",example="公众号id外键")
    private Long subscriptionId;
    /**
     * 公众号
     */
    @ApiModelProperty(value="公众号",example="公众号")
    @TableField(exist=false)
    private Subscription subscription;
    /**
     * 任务人id外键
     */
    @ApiModelProperty(value="任务人id外键",example="任务人id外键")
    private Long accountId;
    /**
     * 任务人
     */
    @ApiModelProperty(value="任务人",example="任务人")
    @TableField(exist=false)
    private Account account;
	public Long getSignId() {
		return signId;
	}
	public void setSignId(Long signId) {
		this.signId = signId;
	}
	public Integer getDayNumber() {
		return dayNumber;
	}
	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}
	public Double getIntegral() {
		return integral;
	}
	public void setIntegral(Double integral) {
		this.integral = integral;
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
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public Subscription getSubscription() {
		return subscription;
	}
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}

}
