package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.lhz.entity.BunsBusCustomer;
import org.lhz.entity.BunsBusRent;
import org.lhz.entity.SysUser;
import org.lhz.service.BunsCarRentalService;
import org.lhz.service.impl.BunsCarRentalServiceImpl;
import org.lhz.vo.BunsBusRentVo;
import utils.DataGridView;
import utils.SysTips;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/business/rent")
public class BunsCarRentalServlet extends HttpServlet {
    private BunsCarRentalService bunsCarRentalService = new BunsCarRentalServiceImpl();
    private HashMap<Object, Object> map = new HashMap<>();
    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        设置编码格式
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        switch (method) {
            case "checkIdNum":
                checkIdNum(req, resp);
                break;
            case "rentSingle":
                rentSingle(req, resp);
                break;
            case "addRentSingle":
                addRentSingle(req, resp);
                break;
            case "checkRentSingle":
                checkRentSingle(req, resp);
                break;
            case "updateRentSingle":
                updateRentSingle(req, resp);
                break;
            case "delRentSingle":
                delRentSingle(req, resp);
                break;
//            case "returnDate":
//                returnDate(req, resp);
//                break;
        }
    }

    //    根据身份证号查询客户是否存在
    protected void checkIdNum(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idNumber = req.getParameter("identity");
        int code = -1;
        HashMap<String, Object> map = new HashMap<>();
        if (idNumber != null) {
            BunsBusCustomer bunsBusCustomer = new BunsBusCustomer();
            BunsBusCustomer busCustomer1 = bunsCarRentalService.checkIdNum(bunsBusCustomer);
            if (busCustomer1 != null) {  //  返回的客户实体类不为空，即存在该客户
                map.put("code", SysTips.CODE_SUCCESS);  //  code >= 0
            } else {
                map.put("code", SysTips.CODE_ERROR); //  code < 0
            }
        } else {
            map.put("code", SysTips.CODE_ERROR); //  code < 0
        }
//        响应数据到页面前端
//        设置响应头
        resp.setContentType("application/json;charset=utf-8");
//        获取输出流
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
        writer.close();
    }

    //    生成出租单
    protected void rentSingle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        获取当前系统日期
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd"); //  将日期格式设为yyyyMMdd，填入出租单号
//        生成出租单号“CZ_当前日期_10000~99999之间的随机数“
        Boolean aBoolean = false;
        String singleNo = null;
        do {
            singleNo = "CZ_" + dateFormat.format(date.getTime()) + "_" + (int) Math.floor(Math.random() * 89999 + 10000);
            aBoolean = bunsCarRentalService.checkSingleNo(singleNo);
        } while (aBoolean);

//        生成开始时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date.getTime());

        String identity = req.getParameter("identity"); //  身份证号
        String carnumber = req.getParameter("carnumber");   //  车牌号
        String opername = ((SysUser) req.getSession().getAttribute("user")).getRealname();//  操作员
        String price = req.getParameter("price");   //  出租价格
        map.put("identity", identity);
        map.put("carnumber", carnumber);
        map.put("opername", opername);
        map.put("price", price);
        map.put("begindate", time);
        map.put("rentid", singleNo);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }

    //    出租单保存到数据库
    protected void addRentSingle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
//        抓取出租单数据
        String returndate = req.getParameter("returndate"); //  结束时间
        String begindate = req.getParameter("begindate");   //  开始时间
        String rentid = req.getParameter("rentid"); //  租车单号
        String identity = req.getParameter("identity"); //  身份证号
        String carnumber = req.getParameter("carnumber");   //  车牌号
        String opername = req.getParameter("opername"); //  操作人
        BunsBusRent bunsBusRent = new BunsBusRent();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = simpleDateFormat.parse(begindate);
        Date returnDate = simpleDateFormat.parse(returndate);
//            获取当前时间
        Date time = simpleDateFormat.parse(simpleDateFormat.format(new Date().getTime()));
//            将数据保存到出租单实体类
        bunsBusRent.setBegindate(beginDate);
        bunsBusRent.setReturndate(returnDate);
        bunsBusRent.setRentid(rentid);
        bunsBusRent.setIdentity(identity);
        bunsBusRent.setCarnumber(carnumber);
        bunsBusRent.setOpername(opername);
        bunsBusRent.setCreatetime(time);
        Boolean aBoolean = bunsCarRentalService.addRentSingle(bunsBusRent);
        if (aBoolean) {
            map.put("msg", SysTips.ADD_SUCCESS);
        } else {
            map.put("msg", SysTips.ADD_ERROR);
        }
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));

    }

//    模糊查询出租单
    protected void checkRentSingle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
//        获取请求参数
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        String rentid = req.getParameter("rentid");
        String identity = req.getParameter("identity");
        String carnumber = req.getParameter("carnumber");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String rentflag = req.getParameter("rentflag");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Integer rentFlag = null;
        Date startData = null;
        Date endData = null;
        if (rentflag != null){
            rentFlag = Integer.parseInt(rentflag);
        }
        if (startTime != null && !"".equals(startTime)){
            startData = simpleDateFormat.parse(startTime);
        }
        if (endTime != null && !"".equals(endTime)){
            endData = simpleDateFormat.parse(endTime);
        }
        BunsBusRentVo bunsBusRentVo = new BunsBusRentVo();
        bunsBusRentVo.setRentid(rentid);
        bunsBusRentVo.setIdentity(identity);
        bunsBusRentVo.setCarnumber(carnumber);
        bunsBusRentVo.setStartTime(startData);  //  查询的开始时间
        bunsBusRentVo.setEndTime(endData);   //  查询的结束时间
        bunsBusRentVo.setRentflag(rentFlag);
        bunsBusRentVo.setPage(page);
        bunsBusRentVo.setLimit(limit);
//        调用service层方法
        List<BunsBusRent> bunsBusRents = bunsCarRentalService.checkRentSingle(bunsBusRentVo);
        Long total = bunsCarRentalService.total();
//        设置分页参数
        DataGridView dataGridView = new DataGridView(total,bunsBusRents);
//        写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));
    }

//    修改出租单数据
    protected void updateRentSingle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
//        抓取出租单数据
        String returndate = req.getParameter("returndate"); //  结束时间
        String begindate = req.getParameter("begindate");   //  开始时间
        String rentid = req.getParameter("rentid"); //  租车单号
        String carnumber = req.getParameter("carnumber");
        double price = Double.parseDouble(req.getParameter("price"));   //  租金
        String opername = ((SysUser) req.getSession().getAttribute("user")).getRealname(); //  操作人
        BunsBusRent bunsBusRent = new BunsBusRent();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = simpleDateFormat.parse(begindate);
        Date returnDate = simpleDateFormat.parse(returndate);
        System.out.println("servlet:"+begindate);
//            获取当前时间
        Date time = simpleDateFormat.parse(simpleDateFormat.format(new Date().getTime()));
//            将数据保存到出租单实体类
        bunsBusRent.setBegindate(beginDate);
        bunsBusRent.setReturndate(returnDate);
        bunsBusRent.setRentid(rentid);
        bunsBusRent.setCarnumber(carnumber);
        bunsBusRent.setPrice(price);
        bunsBusRent.setOpername(opername);
        bunsBusRent.setCreatetime(time);
        Boolean aBoolean = bunsCarRentalService.updateRentSingle(bunsBusRent);
        if (aBoolean) {
            map.put("msg", SysTips.UPDATE_SUCCESS);
        } else {
            map.put("msg", SysTips.UPDATE_ERROR);
        }
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }

//    删除出租单
    protected void delRentSingle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
        String rentid = req.getParameter("rentid");
        Boolean aBoolean = bunsCarRentalService.delRentSingle(rentid);
        if (aBoolean){
            map.put("msg",SysTips.DELETE_SUCCESS);
        }else {
            map.put("msg",SysTips.DELETE_ERROR);
        }
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }
}
