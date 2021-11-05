package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import org.lhz.entity.StatEntity;
import org.lhz.service.StatOpernameYearGradeService;
import org.lhz.service.impl.StatOpernameYearGradeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/stat/opername")
public class StatOpernameYearGradeController extends HttpServlet {
        //注入service层对象
        private StatOpernameYearGradeService statOpernameYearGradeService= new StatOpernameYearGradeServiceImpl();


        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            String method = req.getParameter("method");
            switch(method){
                case"loadOpernameYearGradeStatic":
                    loadOpernameYearGradeStatic(req,resp);
                    break;
            }
        }

        /**
         * Infor: 查询所有的操作员年业绩
         * @param req
         * @param resp
         * @return : void
         * @author : LHZ
         * @date : 2021/11/3 0:23
         */
        private void loadOpernameYearGradeStatic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String year = req.getParameter("year");
            List<StatEntity> entityList = statOpernameYearGradeService.queryAllOpernameYearGrade(year);
            Map<String,Object> map=new HashMap<>();
            List<String> names=new ArrayList<>();
            List<Double> values=new ArrayList<>();
            for(StatEntity base:entityList) {
                names.add(base.getName());
                values.add(Double.valueOf(base.getValue()));
            }
            map.put("name",names);
            map.put("value",values);
            //输出数据
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(JSON.toJSONString(map));

        }
}
