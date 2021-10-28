package org.lhz.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/stat")
public class PageControllerStat extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch(method){
            case"toCustomerAreaStatPage":
                toCustomerAreaStatPage(req,resp);
                break;
            case"toCompanyYearGradeStatPage":
                toCompanyYearGradeStatPage(req,resp);
                break;
            case"toOpernameYearGradeStatPage":
                toOpernameYearGradeStatPage(req,resp);
                break;
        }
    }

    private void toOpernameYearGradeStatPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/view/stat/opernameYearGradeStat.jsp").forward(req,resp);
    }

    private void toCompanyYearGradeStatPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/view/stat/companyYearGradeStat.jsp").forward(req,resp);
    }

    private void toCustomerAreaStatPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/view/stat/customerAreaStat.jsp").forward(req,resp);
    }
}
