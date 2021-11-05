package org.lhz.dao;

import org.lhz.entity.StatEntity;

import java.util.List;

public interface StatOpernameYearGradeMapper {
    List<StatEntity> queryAllOpernameGrade(String year);
}
