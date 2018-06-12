package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 收货信息
 * @author yy
 *
 */
@ApiModel(value="收货信息",description="收货信息")
@TableName(value="receipt_info_tb")
public class ReceiptInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 收货信息id
	 */
	@ApiModelProperty(value="收货信息id",example="收货信息id")
	@TableId(value="receipt_info_id")
	private Long receiptInfoId;
	
	/**
	 * 收货信息姓名
	 */
	@ApiModelProperty(value="收货信息姓名",example="收货信息姓名")
	private String name;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value="手机号",example="手机号")
	private String phone;
	/**
	 * 省
	 */
	@ApiModelProperty(value="省",example="省")
	private String province;
	/**
	 * 市
	 */
	@ApiModelProperty(value="市",example="市")
	private String city;
	/**
	 * 区
	 */
	@ApiModelProperty(value="区",example="区")
	private String area;
	/**
	 * 收货地址
	 */
	@ApiModelProperty(value="收货地址",example="收货地址")
	private String address;
	/**
	 * 默认为0不是，1是
	 */
	@ApiModelProperty(value="默认为0不是，1是",example="默认为0不是，1是")
	private Integer isDefault;
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
	 *微信openid,外键
	 */
	@ApiModelProperty(value="微信openid,外键",example="微信openid,外键")
	private Integer openid;
	/**
	 * 账户Id
	 */
	@ApiModelProperty(value="账户Id",example="账户Id")
	private Integer accountId;
	public Long getReceiptInfoId() {
		return receiptInfoId;
	}
	public void setReceiptInfoId(Long receiptInfoId) {
		this.receiptInfoId = receiptInfoId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
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
	public Integer getOpenid() {
		return openid;
	}
	public void setOpenid(Integer openid) {
		this.openid = openid;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
