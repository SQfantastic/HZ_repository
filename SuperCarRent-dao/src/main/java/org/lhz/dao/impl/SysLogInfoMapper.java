package org.lhz.dao.impl;

import org.lhz.entity.SysLogInfo;
import org.lhz.vo.SysLogInfoVo;

import java.util.List;

public interface SysLogInfoMapper {

    List<SysLogInfo> findAllSysLoInfoList(SysLogInfoVo sysLogInfoVo);

    int addLogInfo(SysLogInfoVo sysLogInfoVo);

    int deleteLogInfo(Integer id);//删除该登录信息
}
