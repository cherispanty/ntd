/*
package com.qgwy.template.service.Impl;

import com.qgwy.template.bean.DailyRecord;
import com.qgwy.template.annotation.DataSource;
import com.qgwy.template.mapper.DailyRecordMapper;
import com.qgwy.template.service.DailyRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class DailyRecordServiceImpl implements DailyRecordService {

    @Autowired
    @Lazy
    private DailyRecordMapper dailyRecordMapper;


    @Override
    @DataSource(name="first")
    public List<DailyRecord> getDailyRecord(@Param("dailyRecord") DailyRecord dailyRecord){
        //日志
        StringBuilder info=new StringBuilder().append(this.getClass().getName()).append("||").
                append(Thread.currentThread().getStackTrace()[1].getMethodName()).append("&&参数：DailyRecord{},");
        log.info(info.toString(),dailyRecord.toString());
        return dailyRecordMapper.getDailyRecord(dailyRecord);
    }

    public ArrayList<HashMap<String,Object>> getDailyRecord(Integer userId, String startDate, String endDate) {
        return dailyRecordMapper.getDaily(userId,startDate,endDate);
    }

    */
/**
     * 添加日报
     * @param dailyRecord
     * @return
     *//*

    public String addDailyRecord(@Param("dailyRecord") DailyRecord dailyRecord){
        //日志
        StringBuilder info=new StringBuilder().append(this.getClass().getName()).append("||").
                append(Thread.currentThread().getStackTrace()[1].getMethodName()).append("&&参数：DailyRecord{},");
        log.info(info.toString(),dailyRecord.toString());

        Integer i=dailyRecordMapper.addDailyRecord(dailyRecord);
        if(i>0){
            return "success";
        }else{
            return "fails";
        }
    }

    */
/**
     * 修改日报
     * @param dailyRecord
     * @return
     *//*

    public Integer updateDailyRecord(@Param("dailyRecord") DailyRecord dailyRecord){
        //日志
        StringBuilder info=new StringBuilder().append(this.getClass().getName()).append("||").
                append(Thread.currentThread().getStackTrace()[1].getMethodName()).append("&&参数：DailyRecord{},");
        log.info(info.toString(),dailyRecord.toString());
        return dailyRecordMapper.updateDailyRecord(dailyRecord);
    }

    */
/**
     * 删除日报
     * @param dailyRecord
     * @return
     *//*

    public String deleteDailyRecord(DailyRecord dailyRecord){
        int i=dailyRecordMapper.deleteDailyRecord(dailyRecord);
        if(i>0){
            return "success";
        }else{
            return "fails";
        }
    }
}
*/
