package com.qgwy.template.controller;


import com.qgwy.template.mapper.DailyRecordMapper;
import com.qgwy.template.service.DailyRecordService;
import com.qgwy.template.util.DynamicDataSource;
import com.qgwy.template.util.R;
import io.swagger.annotations.*;
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
@Api(value = "测试类",tags = {"1-测试"})
public class TestController {

   /* @Autowired
    @Lazy
    private DailyRecordService dailyRecord;*/

    @Autowired
    private DynamicDataSource dataSource;

    @Autowired
    private DailyRecordMapper dailyRecordMapper;

    @GetMapping("/show")
    @ResponseBody
    @ApiOperation(value = "1.2-日报查询",notes = "查询选定时候段内某人的所有日报详情")
    @ApiResponses(@ApiResponse(code = 200,message = "请求成功"))
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

    @GetMapping("/index")
    @ApiOperation("测试默认的返回页面")
    public String toIndex() {
        return "login";
    }

    @GetMapping("/login")
    @ApiOperation("测试登陆页面")
    public String login() {
        return "jsp/login";
    }


    @GetMapping("thyme/index")
    @ApiOperation("测试thyme的返回页面")
    public String thymeIndex(){
        return "html/index";
    }

    //@RequestMapping(value = "show2",produces = "application/json; charset=utf-8")
    @GetMapping(value = "show2")
    @ResponseBody
    @ApiOperation("测试文本方式返回")
    public String show2()
    {
        log.info("hello {},welcome!","fei哥5646");
        return "好像没什么反应并不是很对";
    }

    @GetMapping(value = "show3")
    @ResponseBody
    @ApiOperation("测试自定义对象返回")
    public R json()
    {
        return R.ok().put("data","有木有乱码");
    }


    @GetMapping("jsp/index")
    @ApiOperation("测试jsp的返回页面")
    public String jspIndex(){
        System.out.println("好像OK，是不是");
        //System.out.println(111121212);
        return "jsp/index2";
    }


}
