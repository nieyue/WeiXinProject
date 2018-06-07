package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 奖品
 * @author yy
 *
 */
@ApiModel(value="奖品",description="奖品")
@TableName("prize_tb")
public class Prize implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 奖品id
     */
    @ApiModelProperty(value="奖品id",example="奖品id")
    @TableId("prize_id")
    private Long prizeId;
    /**
     * 连续天数
     */
    @ApiModelProperty(value="连续天数",example="连续天数")
    private Integer dayNumber;
    /**
     * 奖品名称
     */
    @ApiModelProperty(value="奖品名称",example="奖品名称")
    private String name;
    /**
     * 奖品数量
     */
    @ApiModelProperty(value="奖品数量",example="奖品数量")
    private Integer number;
    /**
     * 奖品图标
     */
    @ApiModelProperty(value="奖品图标",example="奖品图标")
    private String imgAddress;
    /**
     * 奖品内容
     */
    @ApiModelProperty(value="奖品内容",example="奖品内容")
    private String content;
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
	public Long getPrizeId() {
		return prizeId;
	}
	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}
	public Integer getDayNumber() {
		return dayNumber;
	}
	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getImgAddress() {
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
