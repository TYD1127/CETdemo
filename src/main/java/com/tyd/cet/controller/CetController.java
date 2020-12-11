package com.tyd.cet.controller;

/**
 * @Author TangYaD
 * @Date 2020/12/8 13:16
 * @Version 1.0
 */


import com.tyd.cet.utils.ResultQueryTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 绩查询Controller
 */
@Controller
@RequestMapping(value = "/cet")
public class CetController {
    /**
     * 获取四六级成绩接口
     *
     * @param ticketNumber 准考证号
     * @param name         姓名
     * @param request      请求头
     * @return
     */
    @RequestMapping(value = "/cet4")
    @ResponseBody
    public Map<String, Object> CetScoreQuery(String ticketNumber, String name, HttpServletRequest request) {
        Map<String, Object> map = ResultQueryTool.resultInquiry(ticketNumber, name, request);
        System.out.println(map);
        return map;
    }

    /**
     * 教师资格证成绩接口
     *
     * @Param: [证件号:zjh, 姓名:uname, 请求头:request]
     * @return: java.util.List<java.lang.Object>
     * @Author: 唐ya
     * @Date: 2020/12/10 14:39
     */
    @RequestMapping(value = "/ntce")
    @ResponseBody
    public List<Object> NTCEquery(String zjh, String uname, HttpServletRequest request) {
        List<Object> list = ResultQueryTool.NTCEquery(zjh, uname, request);
        System.out.println(list);
        return list;
    }

}
