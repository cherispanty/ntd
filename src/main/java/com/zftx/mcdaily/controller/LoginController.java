package com.zftx.mcdaily.controller;

import com.zftx.mcdaily.bean.*;
import com.zftx.mcdaily.service.*;
import com.zftx.mcdaily.util.MD5;
import com.zftx.mcdaily.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@SessionAttributes(value={"user","try"})
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 访问登录页
     * @return
     */
    @RequestMapping(value = "/login")
    public String Login(){
        return "login";
    }

    @RequestMapping(value = "/table")
    public String table(HttpSession session)
    {
        if (session.getAttribute("user") == null)
            return "login";
        return "table";
    }


    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping(value = "/userLogin",method = RequestMethod.GET)
    @ResponseBody
    public R login(HttpSession session, User user, Model model){
        session.setAttribute("user",null);
        user.setPassword(MD5.md5(user.getPassword(), user.getUserName()));
        List<User> list = userService.getUser(user);
        model.addAttribute("user",list.get(0));
        if (list != null && list.size() > 0) {
            log.info(this.getClass()+" || "+Thread.currentThread().getStackTrace()[1].getMethodName()+" ## "+"参数："+user+" message:登录成功");
            return R.ok().put("message", "登录成功");
        } else {
            log.info(this.getClass()+" || "+Thread.currentThread().getStackTrace()[1].getMethodName()+" ## "+"参数："+user+" message:登录失败");
            return R.error().put("message", "登录失败");
        }
    }


    /**
     * 注册，初始化调用（第一阶段内部使用初始化数据接口，主要是为了MD5 加密密码）
     * @param
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public R registers(String userName,String password,String fullName,String email,String phone,String birthplace,String birthday,String position,String hobby,String motto){
//        user.setPassword(MD5.md5(user.getPassword(),user.getUserName()));
        String result = userService.insertUser(new User().setPassword(MD5.md5(password,userName)).setUserName(userName).setFullName(fullName).setEmail(email).setPhone(phone).
        setBirthplace(birthplace).setBirthday(birthday).setPosition(position).setHobby(hobby).setMotto(motto));
        if(result.equals("success")){
            return  R.ok("注册成功").put("result",result);
        }else{
            return R.error("注册失败").put("result",result);
        }
    }
}
