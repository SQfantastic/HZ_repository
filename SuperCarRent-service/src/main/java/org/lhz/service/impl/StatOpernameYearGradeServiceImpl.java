package org.lhz.service.impl;

import org.lhz.dao.StatOpernameYearGradeMapper;
import org.lhz.dao.impl.StatOpernameYearGradeMapperImpl;
import org.lhz.entity.StatEntity;
import org.lhz.service.StatOpernameYearGradeService;

import java.util.List;

public class StatOpernameYearGradeServiceImpl implements StatOpernameYearGradeService {
    //注入dao层对象
    private StatOpernameYearGradeMapper statOpernameYearGradeMapper = new StatOpernameYearGradeMapperImpl();
    @Override
    public List<StatEntity> queryAllOpernameYearGrade(String year) {
        return statOpernameYearGradeMapper.queryAllOpernameGrade(year);
    }
}
