package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 模板数据
 * @author yy
 *
 */
@ApiModel(value="模板数据",description="模板数据")
@TableName("template_data_tb")
public class TemplateData implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 模板数据id
     */
    @ApiModelProperty(value="模板数据id",example="模板数据id")
    @TableId("template_data_id")
    private Long templateDataId;

    /**
     * 模板数据名称，对应{{name.DATA}}中的name
     */
    @ApiModelProperty(value="模板数据名称，对应{{name.DATA}}中的name",example="模板数据名称，对应{{name.DATA}}中的name")
    private String name;
    /**
     * 模板数据值，对应{{name.DATA}}的转换值
     */
    @ApiModelProperty(value="模板数据值，对应{{name.DATA}}的转换值",example="模板数据值，对应{{name.DATA}}的转换值")
    private String value;
    /**
     * 模板数据值得颜色
     */
    @ApiModelProperty(value="模板数据值得颜色",example="模板数据值得颜色")
    private String color;
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
     *模板消息id外键
     */
    @ApiModelProperty(value="模板消息id外键",example="模板消息id外键")
    private Long templateMessageId;
	public Long getTemplateDataId() {
		return templateDataId;
	}
	public void setTemplateDataId(Long templateDataId) {
		this.templateDataId = templateDataId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
	public Long getTemplateMessageId() {
		return templateMessageId;
	}
	public void setTemplateMessageId(Long templateMessageId) {
		this.templateMessageId = templateMessageId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
