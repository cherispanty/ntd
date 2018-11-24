package com.zftx.mcdaily.controller;

import com.zftx.mcdaily.bean.Type;
import com.zftx.mcdaily.bean.User;
import com.zftx.mcdaily.service.TypeService;
import com.zftx.mcdaily.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/getType",method = RequestMethod.GET)
    @ResponseBody
    public R getType(Type type){
        List<Type> typeList = typeService.getType(type);
        if(typeList.size()>0&&typeList != null){
            return R.ok("数据获取成功").put("data",typeList);
        }else {
            return R.error("获取数据失败");
        }
    }

    /**
     * 添加类型
     * @param type
     * @return
     */
    @RequestMapping(value = "/addType",method = RequestMethod.POST)
    @ResponseBody
    public R addType(HttpSession session,Type type){
        User user = (User) session.getAttribute("user");
        type.setCreateUser(user.getId());
        System.out.println("用户ID+++++++++++++++++++++》》》》："+user.getId());
        String result = typeService.insertType(type);
        if("success".equals(result)){
            return R.ok("添加成功").put("result",result);
        }else {
            return R.error("添加失败");
        }
    }

    /**
     * 修改类型
     */
    @RequestMapping(value = "/updateType",method = RequestMethod.PUT)
    @ResponseBody
    public R updateType(HttpSession session,Type type){
        User user = (User) session.getAttribute("user");
        type.setCreateUser(user.getId());
        System.out.println("用户ID+++++++++++++++++++++》》》》："+user.getId());
        String result = typeService.updateType(type);
        if ("success".equals(result)){
            return  R.ok("添加成功").put("result",result);
        }else {
            return  R.error("添加失败");
        }
    }
}
