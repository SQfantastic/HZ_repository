package org.lhz.service;

import org.lhz.entity.StatEntity;

import java.util.List;

public interface StatOpernameYearGradeService {
    List<StatEntity> queryAllOpernameYearGrade(String year);
}
