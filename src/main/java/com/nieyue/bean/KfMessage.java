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
    private Integer kfMessageId;

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
     * 客服消息更新时间
     */
    @ApiModelProperty(value="客服消息更新时间",example="客服消息更新时间")
    private Date updateDate;



}
