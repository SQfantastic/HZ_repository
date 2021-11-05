package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import org.lhz.entity.StatEntity;
import org.lhz.service.StatCustomerAreaService;
import org.lhz.service.impl.StatCustomerAreaServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/stat/customer")
public class StatCustomerAreaController extends HttpServlet {
    //注入service层对象
    private StatCustomerAreaService statCustomerAreaService = new StatCustomerAreaServiceImpl();


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        switch(method){
            case"loadCustomerAreaStatic":
                loadCustomerAreaStatic(req,resp);
                break;
        }
    }

    /**
     * Infor: 导入用户地区管理
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/2 19:57
     */
    private void loadCustomerAreaStatic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<StatEntity> entityList = statCustomerAreaService.queryCustomerAreaStatic();

        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(entityList));
    }
}
