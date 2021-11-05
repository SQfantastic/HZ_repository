package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import org.lhz.service.StatCompanyYearGradeService;
import org.lhz.service.impl.StatCompanyYearGradeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/stat/company")
public class StatCompanyYearGradeController extends HttpServlet {
    //注入service对象
    private StatCompanyYearGradeService statCompanyYearGradeService = new StatCompanyYearGradeServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        //根据请求参数分别处理请求
        switch(method) {
            case "loadCompanyYearGradeStatic":
                loadCompanyYearGradeStatic(req, resp);
                break;
        }
    }

    /**
     * Infor:导入公司年度业绩
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/2 15:08
     */
    private void loadCompanyYearGradeStatic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收请求参数,年份
        String year = req.getParameter("year");
        List<Double> list = statCompanyYearGradeService.queryCompanyYearGradeStaticList(year);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) {
                list.set(i,0.0);
            }
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(list));

    }
}
