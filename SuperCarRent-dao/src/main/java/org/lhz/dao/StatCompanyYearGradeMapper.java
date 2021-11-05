package org.lhz.dao;

import org.lhz.entity.StatEntity;

import java.util.List;

public interface StatCompanyYearGradeMapper {
    List<Double> queryCompanyYearGradeStaticList(String year);
}
