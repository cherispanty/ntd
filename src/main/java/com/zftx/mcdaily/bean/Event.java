package com.zftx.mcdaily.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 事件
 */

@Data
@Accessors(chain = true)
public class Event
{
    Integer id;//事件id
    Integer pointId;//点级id
    String eventName;//事件名
    Integer createUser;//创建人
    String date;//日期
    String time;//时间
}
