package org.lhz.service;

import org.lhz.entity.StatEntity;

import java.util.List;

public interface StatCompanyYearGradeService {
    List<Double> queryCompanyYearGradeStaticList(String year);
}
