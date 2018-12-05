package com.zftx.mcdaily.controller;

import com.zftx.mcdaily.bean.Monthly;
import com.zftx.mcdaily.bean.User;
import com.zftx.mcdaily.bean.Weekly;
import com.zftx.mcdaily.service.MonthlyService;
import com.zftx.mcdaily.service.WeeklyService;
import com.zftx.mcdaily.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * 月报
 */
@Controller
public class MonthlyController {

    @Autowired
    private MonthlyService monthlyService;

    @RequestMapping(value = "/monthly")
    public String table(HttpSession session){
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "monthly";
    }

    /**
     * 查询 月报
     * @param monthly
     * @param session
     * @return
     */
    @RequestMapping(value = "/getMonthly",method = RequestMethod.GET)
    @ResponseBody
    public R getWeekly(Monthly monthly, HttpSession session){
        //获取用户信息
        User user = (User) session.getAttribute("user");
        if(user!=null && user.getId()!=null&&monthly.getUserId()==null){
            monthly.setUserId(user.getId());
        }

        ArrayList<HashMap<String, Object>> list =monthlyService.getMonthly(monthly);

        if(list !=null &&list.size()>0) {
            return R.ok("数据获取成功").put("data", list).put("fullName",user.getFullName()).put("userId",monthly.getUserId());
        }else {
            return R.error("数据获取失败").put("fullName",user.getFullName()).put("userId",monthly.getUserId());
        }
    }


    /**
     * 删除  月报
     * @param monthly
     * @return
     */
    @RequestMapping(value = "/deleteMonthly")
    @ResponseBody
    public R deleteMonthly(Monthly monthly){
        if(monthly.getId()!=null&&monthly.getId()!=0) {
            String str = monthlyService.deleteMonthly(monthly);
            if ("success".equals(str)) {
                return R.ok("删除成功");
            } else {
                return R.error("删除失败");
            }
        }else{
            return R.error("参数有误!");
        }
    }

    @RequestMapping(value = "/addmonthly")
    @ResponseBody
    public R addmonthly(Monthly monthly){
        if(monthly!=null){
            String result=monthlyService.addmonthly(monthly);
            if ("success".equals(result)) {
                return R.ok("新增成功");
            } else {
                return R.error("新增失败");
            }
        }else{
            return R.error("参数有误!");
        }
    }

}
