package com.tyd.cet.utils;

/**
 * @Author TangYaD
 * @Date 2020/12/8 13:10
 * @Version 1.0
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 绩查询工具类
 *
 * @Author TangYaD
 * @Date 2020/12/8 13:10
 */
public class ResultQueryTool {

    public static final String C = "CET";
    public static final String IMG_URL = "http://cet.neea.edu.cn/imgs/";
    public static final String QUERY_URL = "http://cache.neea.edu.cn/cet/query";
    public static final String QUERYncte_URL = "http://ntcecachecloud.neea.cn/ntce/query";
    public static final String REFERER = "http://cet.neea.edu.cn/cet";
    public static final String NCTE = "http://ncte.neea.edu.cn/ncte";

    /**
     * 获取四六级考试成绩
     *
     * @param ticketNumber 准考证号
     * @param name         姓名
     * @param request      请求头
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, Object> resultInquiry(String ticketNumber, String name, HttpServletRequest request) {
        if (StringUtils.isBlank(ticketNumber) || StringUtils.isBlank(name) || null == request) {
            throw new NullPointerException("参数错误。");
        }
        Map<String, Object> map = new HashMap<>();
        // 获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String idCode = idCode(ticketNumber);
        String data = idCode + "," + ticketNumber + "," + name;
        // 拼接url
        String url = QUERY_URL + "?data=" + data;
        HttpGet httpGet = new HttpGet(url);
        // 设置请求头
        httpGet.addHeader("Referer", REFERER);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                // 正则表达式，用于匹配()里面的文字
                String skh = "(?<=\\()[^\\)]+";
                Pattern pattern = Pattern.compile(skh);
                Matcher matcher = pattern.matcher(resultString);
                boolean is = matcher.find();
                if (is) {
                    // 把Json数据转为map
                    map = JSONObject.parseObject(matcher.group());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalError(e.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        return map;

    }

    /**
     * 方法描述：获取教师资格证考试成绩
     *
     * @Param: [ticketNumber, name, request]
     * @return: java.util.List<java.lang.Object>
     * @Author: 唐ya
     * @Date: 2020/12/10 15:16
     */
    public static List<Object> NTCEquery(String ticketNumber, String name, HttpServletRequest request) {
        if (StringUtils.isBlank(ticketNumber) || StringUtils.isBlank(name) || null == request) {
            throw new NullPointerException("参数错误。");
        }
        List<Object> list = new ArrayList<>();
        // 获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String idCode = idCode(ticketNumber);
        String data = idCode + "," + ticketNumber + "," + name;
        // 拼接url
        String url = QUERYncte_URL + "?data=" + data;
        HttpGet httpGet = new HttpGet(url);
        // 设置请求头
        httpGet.addHeader("Referer", NCTE);
//        httpGet.addHeader("Cookie", cookie);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                // 正则表达式，用于匹配()里面的文字
                String skh = "(?<=\\()[^\\)]+";
                Pattern pattern = Pattern.compile(skh);
                Matcher matcher = pattern.matcher(resultString);
                boolean is = matcher.find();
                if (is) {
                    // 把Json数据转为list
                    list = JSON.parseArray(matcher.group());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalError(e.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }


        return list;

    }

    /**
     * 通过准考证号获取data值的前缀
     *
     * @param ticketNumber 准考证号
     * @return
     */
    private static String idCode(String ticketNumber) {

        if (ticketNumber.length() < 18) {
            String substring = ticketNumber.substring(9, 10);
            // todo 这个前缀可能每年会变
            if ("1".equals(substring)) {
                return "CET4_203_DANGCI";
            } else {
                return "CET6_203_DANGCI";
            }
        } else {
            return "NTCE_BS";
        }

    }


}
