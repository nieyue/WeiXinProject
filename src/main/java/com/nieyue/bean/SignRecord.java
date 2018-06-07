package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 签到记录
 * @author yy
 *
 */
@ApiModel(value="签到记录",description="签到记录")
@TableName("sign_record_tb")
public class SignRecord implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 签到记录id
     */
    @ApiModelProperty(value="签到记录id",example="签到记录id")
    @TableId("sign_record_id")
    private Long signRecordId;
    /**
     * 奖励积分
     */
    @ApiModelProperty(value="奖励积分",example="奖励积分")
    private Double integral;
    /**
     * 签到时间
     */
    @ApiModelProperty(value="签到时间",example="签到时间")
    private Date signDate;
    /**
     * 任务人id外键
     */
    @ApiModelProperty(value="任务人id外键",example="任务人id外键")
    private Long accountId;
	public Long getSignRecordId() {
		return signRecordId;
	}
	public void setSignRecordId(Long signRecordId) {
		this.signRecordId = signRecordId;
	}
	public Double getIntegral() {
		return integral;
	}
	public void setIntegral(Double integral) {
		this.integral = integral;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
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

}
