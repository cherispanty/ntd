package com.zftx.mcdaily.mapper;


import com.zftx.mcdaily.bean.EventDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventDetailMapper {

    /**
     * 查询事件详情
     * @param eventDetail
     * @return
     */
    public List<EventDetail> findAll(@Param("eventDetail") EventDetail eventDetail);

    /**
     * 新增事件详情
     * @param eventDetail
     * @return
     */
    public Integer addEventDetail(@Param("eventDetail") EventDetail eventDetail);

}
