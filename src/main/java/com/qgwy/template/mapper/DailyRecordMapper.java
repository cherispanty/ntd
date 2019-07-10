package com.qgwy.template.mapper;

import com.qgwy.template.bean.DailyRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public interface DailyRecordMapper {

    /**
     * 添加日报
     * @param dailyRecord
     * @return
     */
    public Integer addDailyRecord(@Param("dailyRecord") DailyRecord dailyRecord);

    /**
     * 查询日报
     * @param dailyRecord
     * @return
     */
    public List<DailyRecord> getDailyRecord(@Param("dailyRecord") DailyRecord dailyRecord);

    /**
     * 查询日报
     * @param userId 用户id
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    public ArrayList<HashMap<String,Object>> getDaily(@Param("userId") Integer userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 删除日报
     * @param dailyRecord
     * @return
     */
    public Integer deleteDailyRecord(@Param("dailyRecord") DailyRecord dailyRecord);

    /**
     * 修改日报
     * @param dailyRecord
     * @return
     */
    public Integer updateDailyRecord(@Param("dailyRecord") DailyRecord dailyRecord);
}
