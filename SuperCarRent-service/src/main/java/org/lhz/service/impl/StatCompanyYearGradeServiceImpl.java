package org.lhz.service.impl;

import org.lhz.dao.StatCompanyYearGradeMapper;
import org.lhz.dao.impl.StatCompanyYearGradeMapperImpl;
import org.lhz.entity.StatEntity;
import org.lhz.service.StatCompanyYearGradeService;

import java.util.List;

public class StatCompanyYearGradeServiceImpl implements StatCompanyYearGradeService {
    //注入dao层对象
    private StatCompanyYearGradeMapper statCompanyYearGradeMapper =new StatCompanyYearGradeMapperImpl();

    @Override
    public List<Double> queryCompanyYearGradeStaticList(String year) {
        return statCompanyYearGradeMapper.queryCompanyYearGradeStaticList(year);
    }
}
