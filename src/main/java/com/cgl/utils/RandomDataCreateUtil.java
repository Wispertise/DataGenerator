package com.cgl.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:随机数据生成工具
 * @author: SunnyDeer
 * @time: 2023/3/8
 */
@Component
public class RandomDataCreateUtil {

    //电话号码前缀
    public static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    //邮箱后缀
    public static final String[] email_suffix = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");


    public static String character = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static int MaxLen = 20;//邮箱前缀最大长度
    public static int MinLen = 6;//邮箱前缀最小长度


    //生成随机数
    public static int RandomNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    //生成随机邮箱
    public List<String> FakeEmail(int num) {
        List<String> results = new ArrayList<String>();
        for (int j = 0; j < num; j++) {
            int length = RandomNum(MinLen, MaxLen);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < length; i++) {
                int number = (int) (Math.random() * character.length());
                sb.append(character.charAt(number));
            }
            sb.append(email_suffix[(int) (Math.random() * email_suffix.length)]);
            results.add(sb.toString());
        }
        return results;

    }


    //随机生成电话号码
    public List<String> FakeTel(int num) {
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < num; i++) {
            int index = RandomNum(0, telFirst.length - 1);
            String first = telFirst[index];
            String second = String.valueOf(RandomNum(1, 888) + 10000).substring(1);
            String third = String.valueOf(RandomNum(1, 9100) + 10000).substring(1);
            results.add(first + second + third);
        }
        return results;
    }

    //随机性别
    public List<String> FalkeSex(int num) {
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < num; i++) {
            int index = RandomNum(0, 1);
            if (index != 0) results.add("男");
            else results.add("女");
        }
        return results;

    }

    //生成月份
    public List<String> FakeMonths(String start, String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> mounthList = new ArrayList<>();
        Date parse = null;

        try {
            parse = sdf.parse(start);
            Date parse2 = sdf.parse(end);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(parse);
            //转为周一
            int year = c1.get(Calendar.YEAR);
            int month = c1.get(Calendar.MONTH);
            c1.set(year, month, 1, 0, 0, 0);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(parse2);
            int weekYear2 = c2.get(Calendar.YEAR);
            int weekOfYear2 = c2.get(Calendar.WEEK_OF_YEAR);
            c2.setWeekDate(weekYear2, weekOfYear2, Calendar.SUNDAY);
            while (true) {
                int tempMonth = c1.get(Calendar.MONTH);
                String date = c1.getWeekYear() + "-" + ((tempMonth + 1) <= 9 ? "0" + (tempMonth + 1) : tempMonth + 1);
                System.out.println(date);

                mounthList.add(date);
                //下一个月<结束日期
                c1.set(Calendar.MONTH, tempMonth + 1);
                if (c1.getTimeInMillis() >= c2.getTimeInMillis()) {
                    break;
                }
            }
        } catch (ParseException e) {
            System.out.println(e);
            mounthList = null;
        }
        return mounthList;
    }

    //生成start开始的日期
    //num:数据个数
    //isRandom:是否为随机
    public static List<String> FakeDay(String start, int num, boolean isRandom) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long startTime = null;
        List<String> dateList = new ArrayList<String>();
        try {
            startTime = sdf.parse(start).getTime();
//            System.out.println("sdf.parse(start)"+sdf.parse(start));
//            System.out.println("startTime"+startTime);
            //        Long endTime = sdf.parse(end).getTime();
            Long oneDay = 1000 * 60 * 60 * 24L;

            Long time = startTime;
            int amount = 0;
            while (amount <= num) {
                Date d = new Date(time);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date = df.format(d);
                dateList.add(date);
                System.out.println(date);
                if (isRandom) {
                    time += oneDay * RandomNum(10, 50);
                } else {
                    time += oneDay;
                }
                amount++;
            }

            if (isRandom) {
                Collections.shuffle(dateList);
            }
            return dateList;
        } catch (ParseException e) {
            System.out.println(e);
            return null;
        }
    }


    //生成数字
    public List<String> FakeNum(int start, int num, boolean isRandom) {

        List<String> result = new ArrayList<>();
        int x = start;
        for (int i = 0; i < num; i++) {
            if (isRandom) {
                result.add(String.valueOf(RandomNum(0, 10000000)));
            } else {
                result.add(String.valueOf(x));
                x++;
            }
        }
        return result;
    }
    //生成字符串
    //length用户要求产生字符串的长度
    public  List<String> FakeString(int length, int num){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        List<String> result = new ArrayList<String>();
        for(int i = 0;i<num;i++){
            Random random=new Random();
            StringBuffer sb=new StringBuffer();
            for(int j=0;j<length;j++){
                int number=random.nextInt(62);
                sb.append(str.charAt(number));
            }
            result.add(sb.toString());
    }
        return result;
    }
}
