package com.qgwy.template.controller;


import com.qgwy.template.mapper.DailyRecordMapper;
import com.qgwy.template.service.DailyRecordService;
import com.qgwy.template.util.DynamicDataSource;
import com.qgwy.template.util.R;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@Slf4j
public class TestController {

   /* @Autowired
    @Lazy
    private DailyRecordService dailyRecord;*/

    @Autowired
    private DynamicDataSource dataSource;

    @Autowired
    @Lazy
    private DailyRecordMapper dailyRecordMapper;

    @RequestMapping("/show")
    @ResponseBody
    public R show() {

        System.out.println(dataSource.getClass());
        try {
            final Connection connection = dataSource.getConnection();
            System.out.println(dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<HashMap<String, Object>> daily = dailyRecordMapper.getDaily(92, "20190601", "20190705");

        return R.ok().put("data",daily);
    }

    @RequestMapping("/index")
    public String toIndex() {
        return "login";
    }

    @RequestMapping("/login")
    public String login() {
        return "jsp/login";
    }


    @RequestMapping("thyme/index")
    public String thymeIndex(){
        return "html/index";
    }

    //@RequestMapping(value = "show2",produces = "application/json; charset=utf-8")
    @RequestMapping(value = "show2")
    @ResponseBody
    public String show2()
    {
        log.info("hello {},welcome!","fei哥6543");
        return "好像没什么反应并不是很对";
    }
    @RequestMapping(value = "show3")
    @ResponseBody
    public R json()
    {
        return R.ok().put("data","有木有乱码");
    }


    @RequestMapping("jsp/index")
    public String jspIndex(){
        System.out.println("好像OK，是不是");
        //System.out.println(111121212);
        return "jsp/index2";
    }


}
