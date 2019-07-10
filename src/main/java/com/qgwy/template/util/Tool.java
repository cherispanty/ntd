package com.qgwy.template.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 工具类
 * 
 * @author qiu
 * 
 */
public class Tool
{
    private Tool(){}

    //日历
    private static Calendar calendar = Calendar.getInstance();

    /**
     * *年
     * @return
     */
    public static int getYear()
    {
        return calendar.get(Calendar.YEAR);
    }

    /**
     * *月
     * @return
     */
    public static int getMonth()
    {
        return calendar.get(Calendar.MONTH)+1;
    }

    /**
     * *日
     * @return
     */
    public static int getToday()
    {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * *时
     * @return
     */
    public static int getHour()
    {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * *分
     * @return
     */
    public static int getMinute()
    {
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * *秒
     * @return
     */
    public static int getSecond()
    {
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 字符串是否为空
     * @return true为空 false不为空
     */
    public static boolean isEmpty(String s)
    {
        return (s == null || s.trim().isEmpty());
    }

    /**
     * *验证手机号
     * @param phone
     * @return ture 是手机号  false 不是手机号
     */
    public static boolean checkPhone(String phone)
    {
        if(isEmpty(phone))
            return false;
        String regex = "^((13[0-9])|(14[4,7])|(15[^4,\\D])|(17[0-9])|(18[0-9]))(\\d{8})$";
        return phone.matches(regex);
    }

    /**
     * *隐藏手机号
     * @param phone
     * @return 不是手机号原样返回   是手机号返回，例子:137****7020
     */
    public static String phoneHide(String phone)
    {
        if(checkPhone(phone))
        {
            StringBuilder p = new StringBuilder();
            p.append(phone.substring(0, 3));
            p.append("****");
            p.append(phone.substring(7));
            return p.toString();
        }
        //不是手机号原样返回
        return phone;
    }

    /**
     * *验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        if(isEmpty(email))
            return false;
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return email.matches(regex);
    }

    /**
     * 获取当前日期（yyyymmdd）
     * @return
     */
    public static  String getNowDate(){
        String mouth=getMonth()< 10 ? "0" +getMonth() : getMonth()+"";
        String day= getToday() < 10 ? "0" +getToday() :getToday()+"";
        String nowDate=getYear()+""+mouth+day;
        return nowDate;
    }

    /**
     * 第N天后日期
     * @param sdate
     * @param i
     * @return
     * @throws ParseException
     */
    public static String getFutureDate(String sdate,int i)throws ParseException {
        String pattern = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date today = c.getTime();
        c.add(Calendar.DAY_OF_YEAR, i);
        Date today_plus = c.getTime();
        return  sdf.format(today_plus);
    }

    /**
     * 第N天前日期
     * @param sdate
     * @param i
     * @return
     * @throws ParseException
     */
    public static String getOldDate(String sdate,int i)throws ParseException {
        String pattern = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date today = c.getTime();
        c.add(Calendar.DAY_OF_YEAR, -i);
        Date today_plus = c.getTime();
        return  sdf.format(today_plus);
    }

    /**
     * 月 mm
     * @param month
     * @return
     */
    public static String getmm(String month){
        month=Integer.parseInt(month)<10?'0'+month:month;
        return month;
    }

    /**
     * 日 dd
     * @param day
     * @return
     */
    public static String getdd(String day){
        day=Integer.parseInt(day)<10?'0'+day:day;
        return day;
    }

   /* *//**
     * 截取文件的格式后缀
     * @param srcInputStream
     * @return
     * @throws IOException
     *//*
    public static List<String> getImageFormat(InputStream srcInputStream) throws IOException {
        List<String> formatNameList = new ArrayList<>();

        // 获取ImageInputStream 对象
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(srcInputStream);
        // 获取ImageReader对象的迭代器
        Iterator<ImageReader> iterator = ImageIO.getImageReaders(imageInputStream);

        // 如果能获得ImageReader对象则说明流中含有图片文件
        while(iterator.hasNext()) {
            // ImageReader对象的getFormatName()方法可以获得图片格式
            formatNameList.add(iterator.next().getFormatName());
        }
        return formatNameList;
    }*/
}
